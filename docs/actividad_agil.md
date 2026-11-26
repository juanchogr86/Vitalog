# Proyecto Ágil: Aplicación "VitaLog"

---

## Índice

1.  **Ejercicio 1: Lanzamiento y Visión Inicial del Proyecto**
    1.1. Elevator Pitch
    1.2. Product Box
    1.3. Identificación de Personas
    1.4. Ventajas de la Técnica "Personas"

2.  **Ejercicio 2: Identificación de Historias de Usuario y Priorización**
    2.1. Definición de Historias de Usuario (con Criterios de Aceptación)
    2.2. Ventajas y Obstáculos de las Historias de Usuario
    2.3. Product Backlog Priorizado

3.  **Ejercicio 3: Estimación y Horizontes de Planificación**
    3.1. Estimación Ágil de Requisitos (Planning Poker)
    3.2. Justificación de la Metodología Ágil vs. Tradicional
    3.3. Horizontes de la Planificación Ágil
    3.4. Plan de Lanzamiento (Release Plan)

4.  **Conclusiones**

5.  **Referencias Bibliográficas**

---

## 1. Ejercicio 1: Lanzamiento y Visión Inicial del Proyecto

### 1.1. Elevator Pitch

Para individuos que buscan mejorar su estilo de vida, **VitaLog** es una aplicación de seguimiento de hábitos que va más allá del simple conteo de rachas. A diferencia de otras apps, distinguimos entre hábitos **"en construcción"** y **"consolidados"**. Esto permite a nuestros usuarios construir nuevas rutinas con motivación y, al mismo tiempo, celebrar la disciplina ya obtenida, fomentando un crecimiento personal sostenible y evitando la frustración de "romper la cadena".

### 1.2. Product Box

La técnica del *Product Box* nos ayuda a definir la propuesta de valor de forma concisa y atractiva, como si estuviéramos diseñando la caja de un producto físico para un estante.

```
/------------------------------------------------------\\
|                                                      |
|                      VitaLog                         |
|      _________________________________________       |
|     /                                         \      |
|    |    (Logo: Una semilla brotando de un      |    |
|    |     corazón estilizado)                   |    |
|     \_________________________________________/      |
|                                                      |
|           **Tu Crecimiento, Reconocido.**            |
|                                                      |
|------------------------------------------------------|
|                                                      |
| En el panel frontal, destacamos tres beneficios:     |
|                                                      |
| (*) CONSTRUYE: Crea nuevos hábitos con recordatorios |
|     y seguimiento visual.                            |
|                                                      |
| (*) CONSOLIDA: Reconoce y mantén tus logros para     |
|     una motivación a largo plazo.                    |
|                                                      |
| (*) VISUALIZA: Entiende tu progreso con estadísticas |
|     claras y un calendario de consistencia.          |
|                                                      |
| "¡Deja de romper la cadena, empieza a cultivar tu    |
| jardín de hábitos!"                                  |
|                                                      |
|------------------------------------------------------|
|                                                      |
| En el reverso, listamos las características clave:   |
| - Creación de hábitos personalizables (frecuencia).  |
| - Diferenciación entre hábitos nuevos y consolidados.|
| - Calendario de progreso visual.                     |
| - Recordatorios inteligentes.                        |
| - Archivo de hábitos para experimentar sin presión.  |
| - Biblioteca de sugerencias para inspirarte.         |
|                                                      |
\\------------------------------------------------------/
```

### 1.3. Identificación de Personas

Se han identificado tres perfiles de usuario clave:

*   **Ana, la Profesional Ocupada:** 32 años. Necesita una forma ultra-rápida de registrar sus hábitos y recordatorios que la ayuden a mantener la consistencia en su ajetreada agenda.
*   **Carlos, el Estudiante Disciplinado:** 21 años. Busca cuantificar su progreso con datos y gráficos. Valora la diferenciación entre los hábitos que ya domina y los nuevos que está incorporando a su rutina.
*   **Sofía, la Exploradora del Bienestar:** 28 años. Quiere flexibilidad para probar y descartar hábitos sin sentir que fracasa. Busca inspiración y sugerencias para su viaje de autodescubrimiento.

### 1.4. Ventajas de la Técnica "Personas"

Emplear la técnica de *Personas* en lugar de simplemente listar requisitos abstractos ofrece ventajas significativas:

*   **Empatía y Foco en el Usuario:** Obliga al equipo a ponerse en el lugar del usuario final. Esto conduce a decisiones de diseño y funcionalidad que resuelven problemas reales, en lugar de simplemente añadir características.
*   **Herramienta de Comunicación:** Las personas son un artefacto fácil de entender para todos los stakeholders (desarrolladores, diseñadores, marketing, etc.). Es más fácil discutir sobre "lo que Ana necesita" que sobre un requisito codificado como "REQ-105.2".
*   **Priorización Efectiva:** Ayuda a resolver debates sobre la prioridad de las funcionalidades. Una característica que satisface una necesidad clave de una persona principal (ej. Ana) probablemente tendrá más prioridad que una que es solo "agradable de tener" para una persona secundaria.
*   **Prevención del "Efecto Elástico":** Evita que el producto intente ser "todo para todos". Al diseñar para un conjunto específico de arquetipos, se mantiene el producto enfocado y coherente.

---

## 2. Ejercicio 2: Identificación de Historias de Usuario y Priorización

### 2.1. Definición de Historias de Usuario (con Criterios de Aceptación)

A continuación, se describen las cinco historias de usuario más importantes del *Product Backlog*, incluyendo sus criterios de aceptación para asegurar que la funcionalidad se ha implementado correctamente.

**HU01: Crear cuenta de usuario**
*   **Como** nuevo usuario,
*   **Quiero** poder crear una cuenta con mi email y contraseña, o usando mi cuenta de Google,
*   **Para** guardar mi progreso de forma segura y acceder desde cualquier dispositivo con un navegador web.
    *   **Criterios de Aceptación:**
        1.  Dado que estoy en la pantalla de registro, veo la opción de crear una cuenta con email/contraseña y un botón para "Registrarse con Google".
        2.  Si elijo el registro con email, puedo introducir un email válido y una contraseña de al menos 8 caracteres.
        3.  Si elijo "Registrarse con Google", se inicia el proceso de autenticación de Google.
        4.  Tras un registro exitoso (por cualquiera de los dos métodos), se crea mi cuenta y soy redirigido a la pantalla principal de la app.
        5.  El sistema debe validar que el email (ya sea de Google o introducido manualmente) no esté ya registrado. Si lo está, se debe sugerir iniciar sesión.

**HU02: Crear un nuevo "hábito en construcción"**
*   **Como** usuario,
*   **Quiero** poder crear un nuevo "hábito en construcción" definiendo su nombre y la frecuencia,
*   **Para** empezar a hacerle seguimiento.
    *   **Criterios de Aceptación:**
        1.  Dado que estoy en la pantalla principal, puedo acceder a un botón para "Añadir Hábito".
        2.  En el formulario de creación, puedo escribir un nombre para el hábito.
        3.  Puedo seleccionar la frecuencia (ej. "Diario", "Semanal") y los días específicos (ej. L, M, X).
        4.  Cuando guardo el hábito, aparece en mi lista de "Hábitos en construcción" en la pantalla principal.

**HU04: Marcar un hábito como "completado"**
*   **Como** usuario,
*   **Quiero** poder marcar un hábito como "completado" en un día específico,
*   **Para** registrar mi progreso diario.
    *   **Criterios de Aceptación:**
        1.  Dado que estoy viendo mis hábitos para hoy, cada hábito tiene un control para marcarlo (ej. un checkbox).
        2.  Cuando marco un hábito, su estado visual cambia para reflejar que está completado.
        3.  La acción queda registrada para el día actual.
        4.  Puedo deshacer la marca en el mismo día por si cometí un error.

**HU08: Ver calendario de progreso**
*   **Como** Carlos (el estudiante disciplinado),
*   **Quiero** poder ver un calendario mensual que muestre qué días he completado cada hábito,
*   **Para** tener una vista general de mi consistencia y rachas.
    *   **Criterios de Aceptación:**
        1.  Desde la pantalla principal, puedo navegar a una vista de "Progreso" o "Calendario".
        2.  La vista muestra un calendario del mes actual.
        3.  Para cada día en el calendario, se muestra una indicación visual (ej. un punto de color) por cada hábito completado ese día.
        4.  Puedo seleccionar un hábito específico para filtrar el calendario y ver solo su progreso.

**HU06: Configurar recordatorios para los hábitos**
*   **Como** Ana (la profesional ocupada),
*   **Quiero** poder configurar recordatorios para cada hábito,
*   **Para** no olvidar registrarlos durante un día ajetreado.
    *   **Criterios de Aceptación:**
        1.  Al crear o editar un hábito, tengo la opción de activar recordatorios.
        2.  Puedo establecer una hora específica para la notificación.
        3.  La primera vez que activo un recordatorio, el navegador me solicita permiso para mostrar notificaciones.
        4.  Una vez otorgado el permiso, a la hora establecida en los días programados para el hábito, recibo una notificación push.
        5.  El texto de la notificación me recuerda qué hábito debo completar (ej. "¿Completaste 'Meditar 10 minutos' hoy?").

**HU10: Eliminar un hábito**
*   **Como** usuario,
*   **Quiero** poder eliminar un hábito que ya no quiero seguir,
*   **Para** mantener mi lista de hábitos limpia y enfocada en mis objetivos actuales.
    *   **Criterios de Aceptación:**
        1.  Al ver los detalles de un hábito o en la lista principal, tengo una opción para "Eliminar".
        2.  Antes de la eliminación definitiva, el sistema me pide confirmación (ej. "¿Estás seguro de que quieres eliminar este hábito? Se perderá todo su historial.").
        3.  Si confirmo, el hábito se elimina permanentemente de mi lista y de mi historial.
        4.  Si cancelo, no se realiza ninguna acción.

### 2.2. Ventajas y Obstáculos de las Historias de Usuario

**Ventajas:**
*   **Fomentan la Colaboración:** Su formato simple invita a la conversación entre el equipo de desarrollo, el Product Owner y los stakeholders, asegurando un entendimiento compartido.
*   **Centradas en el Valor:** La estructura "Como..., quiero..., para..." mantiene el enfoque en el valor que se entrega al usuario, no solo en la característica técnica.
*   **Son Incrementales:** Permiten dividir grandes funcionalidades en piezas más pequeñas y manejables, facilitando la planificación de sprints y la entrega de valor continua.
*   **Facilitan la Estimación:** Al ser unidades pequeñas y relativamente autocontenidas, son más fáciles de estimar que los grandes documentos de requisitos tradicionales.

**Posibles Obstáculos:**
*   **Ambigüedad:** Si no se acompañan de Criterios de Aceptación claros y conversaciones, pueden ser demasiado vagas y llevar a implementaciones incorrectas.
*   **Pérdida de Visión Global:** Un backlog con cientos de pequeñas historias puede hacer que se pierda la visión general del producto. Se necesita un buen Product Owner y un "mapa de historias" (*Story Map*) para mitigar esto.
*   **Enfoque Excesivo en la Funcionalidad:** Pueden descuidar los requisitos no funcionales (rendimiento, seguridad, etc.) si no se crean historias de usuario técnicas específicas para ellos.

### 2.3. Product Backlog Priorizado

El backlog se organiza por orden de prioridad, agrupando las historias de usuario que conforman el MVP (Must-Have) y las adiciones posteriores.

1.  **HU01:** Crear cuenta de usuario.
2.  **HU02:** Crear un nuevo "hábito en construcción".
3.  **HU03:** Ver la lista de hábitos de hoy.
4.  **HU04:** Marcar un hábito como "completado".
5.  **HU08:** Ver un calendario de progreso.
6.  **HU05:** Mover un hábito a "consolidado".
7.  **HU06:** Configurar recordatorios para los hábitos.
8.  **HU09:** Diferenciar visualmente hábitos.
9.  **HU10:** Eliminar un hábito.
10. **HU11:** Explorar biblioteca de hábitos sugeridos.
11. **HU07:** Marcar completado desde la notificación.

---

## 3. Ejercicio 3: Estimación y Horizontes de Planificación

### 3.1. Estimación Ágil de Requisitos (Planning Poker)

Se utiliza la técnica de *Planning Poker* para asignar Puntos de Historia (Story Points) a cada requisito. Esta estimación es relativa y mide el esfuerzo combinado (complejidad, incertidumbre, volumen de trabajo). Para este proyecto, se toma como referencia la siguiente equivalencia entre Puntos de Historia y tiempo de desarrollo estimado:

*   **1 Story Point:** De 4 a 8 horas (hasta 1 día de trabajo).
*   **2 Story Points:** De 12 a 16 horas (2 días de trabajo).
*   **3 a 5 Story Points:** De 3 a 5 días (una semana de trabajo).
*   **8 a 13 Story Points:** Más de una semana. Estas historias son consideradas "épicas" y deben ser divididas en tareas más pequeñas antes de iniciar un sprint.

A continuación se detalla la estimación para cada historia:

*   **HU01 (Crear cuenta): 5 SP** - Tarea de complejidad media por la integración con OAuth (Google). Equivale a aproximadamente **una semana de trabajo**.
*   **HU02 (Crear hábito): 5 SP** - Requiere un formulario complejo y lógica de guardado. Equivale a aproximadamente **una semana de trabajo**.
*   **HU03 (Ver hábitos de hoy): 3 SP** - Implica una consulta y presentación simple. Equivale a unos **3-5 días de trabajo**.
*   **HU04 (Marcar hábito completado): 2 SP** - Interacción muy simple en el frontend y backend. Equivale a unos **2 días de trabajo**.
*   **HU08 (Ver calendario de progreso): 13 SP** - La historia más compleja. Con **más de una semana de esfuerzo**, esta historia **debe ser dividida** en historias más pequeñas (ej. una para el backend y otra para el frontend) para poder ser abordada en un sprint.
*   **HU05 (Mover a consolidado): 3 SP** - Lógica de cambio de estado y actualización de UI. Equivale a unos **3-5 días de trabajo**.
*   **HU06 (Configurar recordatorios): 8 SP** - Complejidad media-alta por las APIs del navegador. Con **más de una semana de esfuerzo**, esta historia **debe ser dividida** para su gestión.
*   **HU09 (Diferenciar hábitos): 3 SP** - Esfuerzo centrado en la interfaz de usuario. Equivale a unos **3-5 días de trabajo**.
*   **HU10 (Eliminar hábito): 2 SP** - Interacción simple con confirmación. Equivale a unos **2 días de trabajo**.
*   **HU11 (Biblioteca de hábitos): 8 SP** - Requiere contenido predefinido y una nueva sección. Al estimarse en **más de una semana**, es candidata a ser dividida.
*   **HU07 (Completar desde notificación): 8 SP** - Funcionalidad avanzada que depende de HU06. También requiere ser dividida al estimarse en **más de una semana**.

### 3.2. Justificación de la Metodología Ágil vs. Tradicional

Para un proyecto como **VitaLog**, una **metodología ágil (Scrum/Kanban) es claramente preferible a una tradicional (en cascada)**. A continuación, se presenta una tabla comparativa:

| Característica         | Metodología Ágil                                                                                                  | Metodología Tradicional (Cascada)                                                                                                                                           |
| :--------------------- | :---------------------------------------------------------------------------------------------------------------- | :---------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Incertidumbre**      | Permite adaptación y cambio de rumbo con feedback temprano, ideal para productos nuevos o mercados cambiantes.      | Requiere definición exhaustiva de requisitos al inicio, con alto riesgo de construir el producto equivocado si las necesidades cambian.                                |
| **Entrega de Valor**   | Entrega de un Producto Mínimo Viable (MVP) en semanas, obteniendo feedback del usuario de forma temprana.           | La primera versión del producto se entrega después de meses, retrasando la obtención de feedback real del usuario.                                                       |
| **Flexibilidad**       | Abraza el cambio de requisitos, adaptándose a las necesidades del usuario y del mercado con facilidad.               | Los cambios de requisitos son costosos, disruptivos y difíciles de incorporar una vez avanzada la fase de desarrollo.                                                  |
| **Mitigación de Riesgos** | El desarrollo iterativo reduce el riesgo; los problemas se identifican y resuelven en sprints cortos, limitando su impacto. | Un problema descubierto en fases avanzadas de desarrollo puede poner en peligro todo el cronograma y presupuesto del proyecto debido a la naturaleza secuencial. |
| **Enfoque**            | Centrado en el cliente y en la entrega continua de valor.                                                           | Centrado en el proceso, la documentación y el cumplimiento estricto del plan inicial.                                                                                   |

En resumen, la naturaleza exploratoria del desarrollo de una app de consumo hace que la rigidez de un enfoque tradicional sea un obstáculo, mientras que la flexibilidad y el enfoque en el feedback de un ciclo de vida ágil son una ventaja competitiva fundamental.

### 3.3. Horizontes de la Planificación Ágil

Considerando la nueva referencia de estimación, se hace necesario ajustar la velocidad del equipo. Una historia de 5 SP ya consume una semana, por lo que una velocidad de 15 SP por sprint de dos semanas es inalcanzable. Se propone una **velocidad inicial estimada de 10 SP por sprint**.

*   **Horizonte de Release (Lanzamiento):** Define el objetivo de una versión mayor del producto. Para nosotros, el primer objetivo es lanzar el MVP con un total de **45 SP** (historias Must-Have y Should-Have).
*   **Horizonte de Sprint (Iteración):** Es el ciclo de trabajo principal. Se establecen **sprints de 2 semanas**. El objetivo de cada sprint es entregar un incremento de producto funcional con un esfuerzo aproximado de **10 SP**.
*   **Horizonte Diario (Daily Stand-up):** Es la planificación a corto plazo. El equipo se sincroniza diariamente para ajustar el plan y asegurar que se cumplirá el objetivo del sprint.

### 3.4. Plan de Lanzamiento (Release Plan)

El plan de lanzamiento se reestructura para adaptarse a una velocidad de **10 SP/sprint**, extendiéndose a **5 sprints (10 semanas)**. Este plan asume que las historias de 8 y 13 puntos serán refinadas y divididas por el equipo antes de comenzar los sprints correspondientes.

*   **Sprint 1 (10 SP):** Foco en la creación de la cuenta y los hábitos.
    *   **HU01 (5 SP):** Crear cuenta de usuario.
    *   **HU02 (5 SP):** Crear un nuevo "hábito en construcción".
*   **Sprint 2 (10 SP):** Foco en la interacción diaria y la gestión básica.
    *   **HU03 (3 SP):** Ver la lista de hábitos de hoy.
    *   **HU04 (2 SP):** Marcar un hábito como "completado".
    *   **HU09 (3 SP):** Diferenciar visualmente hábitos.
    *   **HU10 (2 SP):** Eliminar un hábito.
*   **Sprint 3 (8 SP):** Foco en la consolidación y primera parte de la visualización.
    *   **HU05 (3 SP):** Mover un hábito a "consolidado".
    *   **HU08 (Parte 1 - 5 SP):** Se asume una división de la HU08 (13 SP). Se aborda la parte más sencilla primero, por ejemplo, la UI del calendario sin datos.
*   **Sprint 4 (8 SP):** Foco en la lógica de visualización de progreso.
    *   **HU08 (Parte 2 - 8 SP):** Se aborda la parte más compleja de la HU08, como la integración con el backend para mostrar los datos de progreso.
*   **Sprint 5 (8 SP):** Foco en la funcionalidad avanzada de recordatorios.
    *   **HU06 (8 SP):** Configurar recordatorios. Se asume que esta historia se dividirá en tareas más pequeñas que quepan dentro del sprint.

---

## 4. Conclusiones

La aplicación de un marco de trabajo ágil desde las fases más tempranas del proyecto "VitaLog" nos ha permitido transitar desde una idea abstracta hasta un plan de acción concreto y ejecutable. La visión inicial, materializada en el *Elevator Pitch* y el *Product Box*, estableció una base sólida y un lenguaje común para todos los involucrados.

El uso de la técnica de *Personas* fue crucial para empatizar con los usuarios finales, permitiendo derivar *Historias de Usuario* que responden a necesidades reales y no a suposiciones. La posterior priorización y estimación relativa con Puntos de Historia nos proporcionó una hoja de ruta flexible (el *Release Plan*), que permite entregar valor de forma incremental y adaptarse a los cambios inherentes a un producto de software innovador.

Este proceso demuestra que la gestión ágil no es una ausencia de planificación, sino una forma de planificar de manera continua, inteligente y centrada en el valor, mitigando riesgos y maximizando las posibilidades de construir un producto que los usuarios amen.

---

## 5. Referencias Bibliográficas

*   Cohn, M. (2004). *User Stories Applied: For Agile Software Development*. Addison-Wesley Professional.
*   Beck, K., et al. (2001). *Manifesto for Agile Software Development*. Agile Alliance. [https://agilemanifesto.org](https://agilemanifesto.org)
*   Sutherland, J. (2014). *Scrum: The Art of Doing Twice the Work in Half the Time*. Crown Business.

## Anexo: Diagrama de despliegue actual

El siguiente diagrama representa la configuración que existe hoy en el proyecto. El frontend React se compila y se incorpora al JAR de Spring Boot; por ello, no se despliega como un contenedor independiente. Docker Compose ejecuta el backend y MongoDB en contenedores separados.

![Diagrama UML de despliegue actual de VitaLog](./despliegue_vitalog.svg)

La [fuente PlantUML editable](./despliegue_vitalog.puml) acompaña al SVG. Los puertos, la red y el volumen se basan en `docker-compose.yaml`; la inclusión del frontend en el JAR sigue el proceso de `build_and_deploy.sh`.
