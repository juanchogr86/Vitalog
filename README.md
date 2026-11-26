# Vitalog

Monorepositorio con backend Spring Boot, frontend React y la documentación proporcionada. Incluye la estructura inicial y el flujo de registro e inicio de sesión con correo y contraseña de RU-01.

## Decisiones iniciales

- Java: Eclipse Temurin 21.0.13, detectado en el equipo.
- Backend: Spring Boot 3.5.16 y Maven 3.9.16, seleccionados para trabajar con Java 21.
- Frontend: Create React App (`react-scripts` 5.0.1) con React y React DOM 18.3.1.
- Node.js: 22.23.5, versión LTS compatible con Create React App.
- MongoDB: se usa el contenedor existente. Al verificarlo, la imagen era `mongo:8.0` y el servidor reportó MongoDB 8.0.38. La versión que Vitalog debería fijar en su propia configuración queda pendiente de confirmación; este proyecto no crea ni modifica otro contenedor.
- Create React App está obsoleto y ya no es la opción recomendada por React para proyectos nuevos; se conserva porque fue indicado explícitamente para este proyecto.

Las versiones de Spring Boot, Maven, React y Node son una base inicial elegida para este scaffold, no requisitos funcionales. La URI de MongoDB se puede cambiar mediante `MONGODB_URI`. El backend no guarda credenciales ni secretos.
El archivo `frontend/.npmrc` conserva la resolución de peers heredada que necesita CRA 5; no se agregó TypeScript al frontend JavaScript.

El registro guarda el correo normalizado y una contraseña con hash BCrypt en la colección MongoDB `users`; la contraseña original no se almacena ni se devuelve al frontend. Debe tener entre 8 y 72 caracteres e incluir mayúscula, minúscula, número y carácter especial. El email tiene un índice único. El registro inicia sesión y permite acceder a “Bienvenido a vitalog”; también se puede volver a iniciar sesión y cerrar sesión. La sesión usa una cookie HttpOnly y protección CSRF. El login con Google, mencionado en RU-01, queda pendiente de configurar las credenciales OAuth del proyecto.

## Estructura

```text
Vitalog/
├── backend/
│   ├── src/main/java/com/vitalog/
│   │   ├── config/
│   │   ├── controller/
│   │   ├── dto/
│   │   ├── model/
│   │   ├── repository/
│   │   ├── service/
│   │   └── VitaLogApplication.java
│   ├── src/main/resources/application.properties
│   ├── src/test/java/com/vitalog/
│   │   ├── AuthIntegrationTests.java
│   │   └── VitaLogApplicationTests.java
│   └── pom.xml
├── frontend/
│   ├── public/index.html
│   ├── src/
│   │   ├── pages/            # Formulario de acceso y bienvenida
│   │   ├── services/         # Cliente de autenticación
│   │   ├── utils/
│   │   ├── App.js
│   │   ├── App.test.js
│   │   ├── index.css
│   │   └── index.js
│   ├── package.json
│   ├── package-lock.json
│   └── .npmrc
├── docs/                 # Documentación proporcionada, conservada sin cambios
├── .gitignore
└── iniciar_vitalog.bat
```

## Iniciar el proyecto

Con Docker Desktop y el contenedor MongoDB disponibles, ejecuta desde Windows:

```bat
iniciar_vitalog.bat
```

El script comprueba las dependencias de frontend contra `package.json`, `package-lock.json` y `.npmrc`, y solo vuelve a instalarlas si esos archivos cambian. Las dependencias y la caché npm se conservan en volúmenes Docker para evitar reinstalarlas en la carpeta compartida con Windows. Después ejecuta las pruebas y compila el frontend; lo incorpora a los recursos estáticos, prueba y empaqueta el backend, lo inicia en `http://localhost:8080` y abre esa dirección en el navegador. Los logs de Tomcat aparecen en una ventana titulada `Vitalog Backend - Tomcat logs`. Usa imágenes Docker con versiones fijas para Node.js y Maven, por lo que no requiere instalarlos en Windows. Requiere Java 21 local y MongoDB accesible en `localhost:27017`.

Variables opcionales para la conexión y el puerto:

```bat
set MONGODB_URI=mongodb://localhost:27017/vitalog
set SERVER_PORT=8080
```

## Ejecución por separado

Para desarrollar el frontend con el servidor de Create React App, después de haber ejecutado el `.bat` una vez:

```bat
cd frontend
docker run --rm -it -p 3000:3000 -e HOST=0.0.0.0 -v "%CD%:/workspace" -w /workspace node:22.23.5 npm start
```

Para ejecutar el backend empaquetado:

```bat
set SERVER_PORT=8080
set MONGODB_URI=mongodb://localhost:27017/vitalog
java -jar backend\target\vitalog-0.1.0-SNAPSHOT.jar
```

Pruebas y compilación del backend:

```bat
docker run --rm -v "%CD%\backend:/workspace" -v vitalog-maven-cache:/root/.m2 -w /workspace -e MONGODB_URI=mongodb://host.docker.internal:27017/vitalog_test maven:3.9.16-eclipse-temurin-21 mvn -B clean verify
```

No se han creado requisitos de usuario ni historias de usuario.
