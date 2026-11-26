# Contexto Técnico de Solución - VitaLog

Este documento describe el contexto técnico inicial y las decisiones de arquitectura para el proyecto VitaLog, que consiste en un backend en Java y un frontend en React.

---

## 1. Estructura General del Proyecto (Monorepositorio)

Se utilizará una estructura de monorepositorio para albergar tanto el backend como el frontend bajo un mismo control de versiones. Esto facilita la gestión de proyectos acoplados y permite una futura evolución a estrategias de despliegue más complejas si es necesario.

```
vitalog/
├── 📂 backend/         # Lógica del servidor (API en Java)
├── 📂 frontend/        # Interfaz de usuario (Aplicación en React)
├── 📂 docs/            # Documentación del proyecto (API, arquitectura, etc.)
└── 📜 README.md         # README principal con instrucciones generales
```

---

## 2. Estructura Detallada del Backend (Java/Spring Boot)

El backend se desarrollará utilizando Java con el framework Spring Boot, ideal para la creación de APIs REST robustas y escalables. Se utilizará Maven o Gradle como sistema de construcción.

```
backend/
├── 📦 src/
│   └── 📦 main/
│       ├── 📦 java/com/vitalog/
│       │   ├── 🏢 model/         # Entidades de datos y POJOs (Plain Old Java Objects)
│       │   ├── 🛠️ service/       # Implementación de la lógica de negocio
│       │   ├── ☁️ controller/    # Endpoints de la API REST y manejo de solicitudes HTTP
│       │   ├── 📊 repository/   # Interfaces de acceso a datos (ej. Spring Data JPA)
│       │   └── ✨ VitaLogApplication.java # Clase principal de la aplicación Spring Boot
│       └── 📄 resources/
│           ├── ⚙️ application.properties  # o application.yml para configuración del entorno, base de datos, etc.
│           └── 📁 static/             # Contendrá los archivos estáticos del frontend en la Estrategia 1
└── 📄 pom.xml               # o build.gradle para la gestión de dependencias y el ciclo de vida de la construcción
```

**Justificación:**
*   **Patrón de Capas:** Promueve la separación de responsabilidades y la modularidad, facilitando el mantenimiento y la escalabilidad.
*   **Estándar de la Industria:** Sigue las mejores prácticas y convenciones del ecosistema Spring, lo que facilita la incorporación de nuevos desarrolladores.

---

## 3. Estructura Detallada del Frontend (React)

El frontend se construirá con React, utilizando una estructura basada en componentes que facilita la reutilización y el desarrollo modular de la interfaz de usuario.

```
frontend/
├── 📂 public/              # Archivos estáticos públicos (index.html, favicon.ico, assets)
├── 📂 src/
│   ├── 📂 components/      # Componentes UI reutilizables y genéricos (ej. Button, Card, Modal)
│   ├── 📂 pages/           # Vistas principales de la aplicación que corresponden a las rutas (ej. HomePage, LoginPage, Dashboard)
│   ├── 📂 services/        # Módulos para interactuar con el backend (ej. api.js, authService.js)
│   ├── 📂 hooks/           # Custom React Hooks para encapsular lógica con estado
│   ├── 📂 context/         # Context API de React para gestión de estado global ligero (ej. AuthContext)
│   ├── ⚛️ App.js             # Componente raíz que define el enrutamiento y la estructura principal
│   └── 🏁 index.js           # Punto de entrada de la aplicación React (renderiza App.js)
├── 📄 package.json         # Archivo de configuración de Node.js con dependencias y scripts
└── ⚙️ .env                  # Variables de entorno para el frontend (ej. URL del backend)
```

**Justificación:**
*   **Componentización:** Facilita el desarrollo ágil, la reutilización de código y la mantenibilidad de la UI.
*   **Separación de Preocupaciones:** Distingue claramente entre la UI, la lógica de negocio del cliente y la comunicación con el servidor.

---
## 4. Arquitectura de la Aplicación

VitaLog sigue una **arquitectura cliente-servidor** desacoplada en su lógica pero unificada en su despliegue inicial.

*   **Cliente (Frontend):** Una Aplicación de Página Única (SPA) desarrollada en React. Es responsable de toda la interfaz de usuario, la gestión del estado visual y la interacción con el usuario.
*   **Servidor (Backend):** Una API RESTful desarrollada en Java con Spring Boot. Expone endpoints para que el cliente consuma y manipule los datos. Es responsable de la lógica de negocio, el acceso a datos y la seguridad.

### Flujo de Datos

Un flujo de interacción típico sigue estos pasos:
1.  El usuario interactúa con un componente en la interfaz de React (ej. hace clic en "Marcar como completado").
2.  El componente de React invoca una función de un servicio local (`services/api.js`).
3.  El servicio realiza una petición HTTP (ej. `POST /api/habits/1/complete`) al backend.
4.  El `Controller` de Spring Boot correspondiente recibe la petición.
5.  El `Controller` invoca al `Service` apropiado para ejecutar la lógica de negocio.
6.  El `Service` utiliza el `Repository` para persistir los cambios en la base de datos.
7.  El backend devuelve una respuesta HTTP (ej. `200 OK` con los datos actualizados).
8.  El servicio del frontend recibe la respuesta y actualiza el estado global o local de la aplicación React.
9.  React renderiza de nuevo los componentes afectados para reflejar los nuevos datos.

---

## 5. Componentes Principales de la Aplicación

Basado en las historias de usuario, los componentes funcionales clave son:

### Componentes del Backend
*   **Módulo de Autenticación:** Gestiona el registro (HU01), inicio de sesión y la seguridad de las rutas de la API.
*   **Módulo de Gestión de Hábitos:** Provee la lógica CRUD (Crear, Leer, Actualizar, Eliminar) para los hábitos (HU02, HU10), incluyendo la diferenciación entre "en construcción" y "consolidados" (HU05).
*   **Módulo de Seguimiento:** Registra la finalización de hábitos en fechas concretas (HU04) y provee los datos para las visualizaciones de progreso (HU08).
*   **Módulo de Notificaciones:** Lógica para gestionar y enviar recordatorios a los usuarios (HU06).

### Componentes del Frontend
*   **Páginas de Autenticación:** Componentes para los formularios de registro e inicio de sesión (HU01).
*   **Dashboard/Página Principal:** Muestra la lista de hábitos del día, permitiendo marcarlos como completados (HU03, HU04).
*   **Vista de Calendario/Progreso:** Componente visual que muestra la consistencia y el historial de los hábitos (HU08).
*   **Formularios de Hábitos:** Modales o páginas para crear y editar hábitos, incluyendo nombre, frecuencia y recordatorios (HU02, HU06).
*   **Componentes de UI Genéricos:** Elementos reutilizables como `Button`, `Card`, `Checkbox`, `Modal`, que aseguran una consistencia visual y agilizan el desarrollo.

---

## 6. Compilación y Despliegue (Estrategia Unificada)

El proceso para compilar la aplicación y empaquetarla en un único artefacto desplegable es el siguiente:

1.  **Compilar el Frontend:**
    *   Navegar al directorio del frontend: `cd frontend/`
    *   Instalar las dependencias (solo la primera vez): `npm install`
    *   Ejecutar el script de compilación: `npm run build`. Esto genera una carpeta `build/` con los archivos estáticos optimizados para producción.

2.  **Integrar Frontend en el Backend:**
    *   Los contenidos de la carpeta `frontend/build/` deben ser copiados al directorio `backend/src/main/resources/static/`.
    *   Este paso puede automatizarse con un script para asegurar la consistencia.

3.  **Empaquetar el Backend:**
    *   Navegar al directorio del backend: `cd backend/`
    *   Utilizar Maven para limpiar el proyecto y empaquetarlo en un archivo `.jar`: `mvn clean package`.
    *   El resultado es un archivo ejecutable (ej. `target/vitalog-0.0.1-SNAPSHOT.jar`) que contiene tanto la API de Java como la aplicación de React.

---

## 7. Ejecución de la Aplicación

Una vez que el proyecto ha sido compilado y empaquetado:

1.  **Iniciar MongoDB:**
    *   Antes de iniciar la aplicación, ejecutar MongoDB en un contenedor Docker:
      ```bash
      docker run -d --name mi-mongodb -p 27017:27017 mongo
      ```
    *   El backend se conecta a MongoDB en `localhost:27017`; el contenedor debe estar activo para que las funciones que consultan o guardan datos funcionen.

2.  **Iniciar el Servidor:**
    *   Ejecutar el archivo `.jar` generado con Java:
      ```bash
      java -jar backend/target/vitalog-0.0.1-SNAPSHOT.jar
      ```
    *   Spring Boot iniciará su servidor web embebido (normalmente Tomcat) en un puerto aleatorio disponible, porque `server.port=0` está configurado en `backend/src/main/resources/application.properties`.
    *   Consultar los logs de inicio de Spring Boot para conocer el puerto asignado y abrir `http://localhost:<puerto>`.

3.  **Acceder a la Aplicación:**
    *   Abrir un navegador web y visitar la dirección con el puerto indicado en los logs.
    *   El servidor de Spring Boot servirá el `index.html` del frontend, y la aplicación React tomará el control en el navegador. Las llamadas a la API (ej. `/api/...`) serán gestionadas por los controladores del mismo servidor.

---

## 8. Estrategias de Despliegue a Futuro

Se reconoce que la estrategia unificada, aunque simple, introduce un acoplamiento entre los ciclos de vida del frontend y el backend. A medida que el proyecto crezca, se podrá migrar a una de las siguientes estrategias:

*   **Estrategia Desacoplada:** Desplegar el frontend en un servidor de estáticos (como Nginx, Vercel o Netlify) y el backend de Java en su propio servidor de aplicaciones. Esto permite escalar y actualizar cada parte de forma independiente.
*   **Estrategia Nativa de la Nube:** Contenerizar tanto el frontend como el backend (usando Docker) y orquestarlos con Kubernetes, lo que ofrece máxima escalabilidad, resiliencia y portabilidad.

La estructura de monorepositorio con carpetas separadas (`backend/`, `frontend/`) facilita enormemente una futura migración a cualquiera de estas arquitecturas más avanzadas.