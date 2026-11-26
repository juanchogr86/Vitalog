# Requerimientos de usuario — VitaLog

## Propósito

Este documento describe las funcionalidades que VitaLog ofrecerá a sus usuarios. Se utilizará como punto de partida para definir y desarrollar el producto; el detalle de cada funcionalidad podrá ampliarse durante el diseño y la implementación.

## Descripción del producto

VitaLog es una aplicación para crear hábitos, registrar su cumplimiento y visualizar el progreso. La aplicación distingue entre hábitos «en construcción» y «consolidados» para ayudar a los usuarios a desarrollar rutinas y reconocer sus avances.

## Requerimientos funcionales

### RU-01 — Crear una cuenta e iniciar sesión

El usuario podrá crear una cuenta e iniciar sesión con correo electrónico y contraseña o mediante una cuenta de Google. Su cuenta le permitirá guardar y consultar sus hábitos y su progreso.

### RU-02 — Crear un hábito

El usuario podrá crear un hábito indicando su nombre, frecuencia y, si corresponde, los días de la semana en que desea realizarlo. Los hábitos nuevos se mostrarán como «en construcción».

#### Especificación funcional verificada de RU-02

**Estado general:** implementado para la creación de hábitos diarios y semanales en la sesión iniciada.

**Objetivo y valor**

Permitir al usuario registrar una rutina que desea seguir y definir cuándo debe realizarla. La opción diaria representa una práctica para todos los días; la opción semanal permite elegir los días concretos de ejecución.

**Actor y condición previa**

- **Actor:** usuario con sesión iniciada.
- **Precondición:** el usuario puede acceder a la pantalla «Mis hábitos» y tiene una cuenta activa.

**Datos relevantes**

| Campo | Valor o regla | Estado |
|---|---|---|
| Nombre | Texto obligatorio y visible en la lista. No puede superar 100 caracteres. Los espacios externos se eliminan al guardar. | Implementado |
| Frecuencia | Puede ser **Diaria** o **Semanal**. Diaria es la opción inicial. | Implementado |
| Días semanales | Obligatorios al seleccionar frecuencia semanal. Las opciones son lunes, martes, miércoles, jueves, viernes, sábado y domingo. | Implementado |
| Duración del seguimiento | Duración expresada en días. Debe estar entre 1 y 3650. Si no se informa al crear el hábito, el sistema usa 66 días. | Implementado; dato adicional no definido originalmente en RU-02 |
| Fecha de inicio | Fecha en que se crea el hábito. | Implementado |
| Categoría «en construcción» | Los hábitos nuevos deberían pertenecer a esta categoría. | **Pendiente de aclaración/verificación en esta funcionalidad:** el comportamiento actual muestra los hábitos en una lista única «Hábitos creados» y no expone una categoría o estado de construcción |

**Reglas de negocio**

1. Un usuario debe haber iniciado sesión para crear un hábito.
2. El nombre es obligatorio y no puede exceder 100 caracteres.
3. La frecuencia puede ser diaria o semanal.
4. Para un hábito semanal, el usuario debe seleccionar al menos un día de la semana.
5. Para un hábito diario, no corresponde seleccionar días específicos.
6. La duración debe estar entre 1 y 3650 días. Al no informar una duración, se registra 66 días.
7. Al guardar correctamente, el nuevo hábito se añade a la parte superior de los hábitos creados y se muestran sus datos principales.
8. Después de guardar, el formulario vuelve a su estado inicial: nombre vacío, duración de 66 días, frecuencia diaria y sin días seleccionados.
9. Las fechas válidas admitidas para la frecuencia semanal son los siete días de la semana.

**Flujo funcional feliz**

1. El usuario inicia sesión y accede a «Mis hábitos».
2. Completa el nombre del hábito.
3. Revisa o modifica la duración del seguimiento.
4. Selecciona «Diaria» o «Semanal».
5. Si selecciona «Semanal», marca uno o más días de la semana.
6. Pulsa «Crear hábito».
7. El sistema registra el hábito y muestra el mensaje «Hábito creado correctamente».
8. El hábito aparece en la lista «Hábitos creados» con su nombre, frecuencia y duración.

**Criterios de aceptación**

#### Creación de hábito diario

- **Dado** que el usuario tiene una sesión iniciada y está en «Mis hábitos».
- **Cuando** indica un nombre válido, mantiene una duración válida y selecciona «Diaria».
- **Entonces** el sistema crea el hábito con frecuencia diaria y muestra «Hábito creado correctamente».
- **Entonces** el hábito aparece en la lista «Hábitos creados» como diario y con su duración.

- **Dado** que el usuario crea un hábito diario sin indicar duración.
- **Cuando** guarda el hábito.
- **Entonces** el sistema registra una duración de 66 días.

#### Creación de hábito semanal

- **Dado** que el usuario tiene una sesión iniciada y selecciona «Semanal».
- **Cuando** selecciona al menos un día de la semana y guarda.
- **Entonces** el sistema crea el hábito con la frecuencia semanal y los días seleccionados.
- **Entonces** el hábito aparece en la lista «Hábitos creados» como semanal y con su duración.

- **Dado** que el usuario selecciona «Semanal» y no marca ningún día.
- **Cuando** pulsa «Crear hábito».
- **Entonces** el sistema no crea el hábito y solicita seleccionar al menos un día.

#### Validaciones visibles

- **Dado** que el usuario intenta guardar sin nombre.
- **Cuando** pulsa «Crear hábito».
- **Entonces** el sistema no acepta el registro y solicita completar el nombre.

- **Dado** que el usuario intenta guardar con una duración fuera del rango permitido.
- **Cuando** pulsa «Crear hábito».
- **Entonces** el sistema no acepta el registro y mantiene la duración dentro de los límites visibles de 1 a 3650 días.

**Requerido o propuesto**

- El usuario debe poder crear un hábito indicando nombre y frecuencia.
- Cuando aplique, debe poder indicar los días semanales.
- Los hábitos nuevos deben mostrar inicialmente la categoría «en construcción».

**Implementado**

- Creación de hábitos diarios y semanales.
- Selección de uno o varios días para la frecuencia semanal.
- Duración configurable con valor inicial de 66 días.
- Validación básica de nombre, duración y días semanales.
- Confirmación visible y aparición del hábito recién creado en la lista.
- Persistencia asociada a la cuenta del usuario.

**Pendiente de aclaración o discrepancia**

- RU-02 indica que los hábitos nuevos se mostrarán como «en construcción», pero la interfaz actual no presenta categorías ni un estado «en construcción» para los hábitos. Solo ofrece una lista denominada «Hábitos creados».
- El requisito no menciona la duración del seguimiento. Esta información sí existe en el comportamiento actual y debe confirmarse como parte estable del producto.
- El requisito tampoco define límites para el nombre ni para la duración; la aplicación actual los limita a 100 caracteres y de 1 a 3650 días.

### RU-03 — Consultar hábitos

El usuario podrá consultar sus hábitos organizados en las categorías «en construcción» y «consolidados», e identificar cuáles están programados para el día actual.

### RU-04 — Registrar el cumplimiento de un hábito

El usuario podrá marcar un hábito como completado en una fecha y retirar esa marca si la registró por error. El estado de cumplimiento se reflejará en el progreso del hábito.

### RU-05 — Consolidar un hábito

El usuario podrá cambiar un hábito de «en construcción» a «consolidado». La aplicación mostrará el hábito en la categoría correspondiente y conservará su progreso.

### RU-06 — Consultar el progreso

El usuario podrá consultar un calendario mensual que muestre los días en que completó hábitos. También podrá filtrar la vista para consultar el progreso de un hábito específico.

### RU-07 — Configurar recordatorios

El usuario podrá activar o desactivar recordatorios para un hábito y seleccionar la hora en que desea recibirlos en los días programados. El recordatorio identificará el hábito correspondiente.

### RU-08 — Explorar hábitos sugeridos

El usuario podrá consultar una biblioteca de hábitos sugeridos y seleccionar una sugerencia para crear un hábito a partir de ella.

### RU-09 — Archivar un hábito

El usuario podrá archivar un hábito que ya no desea mantener entre sus hábitos activos. Los hábitos archivados dejarán de aparecer en las listas activas.

### RU-10 — Eliminar un hábito

El usuario podrá eliminar un hábito después de confirmar la acción. Al eliminarlo, el hábito dejará de estar disponible en sus listas y en su historial.

### RU-11 — Completar un hábito desde un recordatorio

Desde un recordatorio, el usuario podrá marcar como completado el hábito asociado. El cumplimiento aparecerá en el progreso del hábito.
