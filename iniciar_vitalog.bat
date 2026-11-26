@echo off
setlocal

set "PROJECT_DIR=%~dp0"
if "%PROJECT_DIR:~-1%"=="\" set "PROJECT_DIR=%PROJECT_DIR:~0,-1%"
set "NODE_IMAGE=node:22.23.5"
set "MAVEN_IMAGE=maven:3.9.16-eclipse-temurin-21"
if not defined SERVER_PORT set "SERVER_PORT=8080"
if not defined MONGODB_URI set "MONGODB_URI=mongodb://localhost:27017/vitalog"

where java >nul 2>nul
if errorlevel 1 (
    echo Java 21 is required. Install or add it to PATH.
    exit /b 1
)

set "DOCKER=docker"
where docker >nul 2>nul
if errorlevel 1 (
    if exist "%ProgramFiles%\Docker\Docker\resources\bin\docker.exe" (
        set "DOCKER=%ProgramFiles%\Docker\Docker\resources\bin\docker.exe"
    ) else (
        echo Docker CLI was not found. Start or install Docker Desktop.
        exit /b 1
    )
)

"%DOCKER%" info >nul 2>nul
if errorlevel 1 (
    echo Docker Engine is not running. Start Docker Desktop and try again.
    exit /b 1
)

powershell -NoProfile -Command "if (Test-NetConnection -ComputerName 127.0.0.1 -Port 27017 -InformationLevel Quiet) { exit 0 } else { exit 1 }"
if errorlevel 1 (
    echo MongoDB is not reachable at localhost:27017. Start its existing container first.
    exit /b 1
)

echo Checking frontend dependencies...
"%DOCKER%" run --rm --mount "type=bind,source=%PROJECT_DIR%\frontend,target=/workspace" --mount "type=volume,source=vitalog-node-modules,target=/workspace/node_modules" --mount "type=volume,source=vitalog-npm-cache,target=/root/.npm" --workdir /workspace %NODE_IMAGE% node /workspace/ensure-dependencies.js
if errorlevel 1 goto failed

echo Running frontend tests...
"%DOCKER%" run --rm --mount "type=bind,source=%PROJECT_DIR%\frontend,target=/workspace" --mount "type=volume,source=vitalog-node-modules,target=/workspace/node_modules" --workdir /workspace -e CI=true %NODE_IMAGE% npm test -- --watchAll=false
if errorlevel 1 goto failed

echo Building frontend...
"%DOCKER%" run --rm --mount "type=bind,source=%PROJECT_DIR%\frontend,target=/workspace" --mount "type=volume,source=vitalog-node-modules,target=/workspace/node_modules" --workdir /workspace %NODE_IMAGE% npm run build
if errorlevel 1 goto failed

if not exist "%PROJECT_DIR%\backend\src\main\resources\static" mkdir "%PROJECT_DIR%\backend\src\main\resources\static"
robocopy "%PROJECT_DIR%\frontend\build" "%PROJECT_DIR%\backend\src\main\resources\static" /MIR /NFL /NDL /NJH /NJS /NC /NS
if errorlevel 8 goto failed

echo Testing and packaging backend...
"%DOCKER%" run --rm --mount "type=bind,source=%PROJECT_DIR%\backend,target=/workspace" --mount "type=volume,source=vitalog-maven-cache,target=/root/.m2" --workdir /workspace -e MONGODB_URI=mongodb://host.docker.internal:27017/vitalog_test %MAVEN_IMAGE% mvn -B clean verify
if errorlevel 1 goto failed

echo Starting Vitalog at http://localhost:%SERVER_PORT% ...
start "Vitalog Backend - Tomcat logs" /D "%PROJECT_DIR%" java -jar "backend\target\vitalog-0.1.0-SNAPSHOT.jar"
powershell -NoProfile -Command "$uri = 'http://localhost:%SERVER_PORT%'; $deadline = (Get-Date).AddSeconds(60); while ((Get-Date) -lt $deadline) { try { $response = Invoke-WebRequest -Uri $uri -TimeoutSec 2 -UseBasicParsing; if ($response.StatusCode -eq 200) { Start-Process $uri; exit 0 } } catch { Start-Sleep -Seconds 1 } }; exit 1"
if errorlevel 1 (
    echo Vitalog did not respond within 60 seconds. Check the backend console.
    exit /b 1
)

echo Vitalog is running at http://localhost:%SERVER_PORT%
exit /b 0

:failed
echo A build or test step failed. Review the output above.
exit /b 1
