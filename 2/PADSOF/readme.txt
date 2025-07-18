# Nota media: 8.5


IMPORTANTE, Tareas.java: Se han implementado tareas automáticas que se ejecutan al abrir la aplicación. 
Estas verifican las fechas de inicio y fin de las exposiciones. 
Si la fecha de inicio coincide con la fecha actual y la exposición está en estado de PUBLICADA, 
las obras en las salas de exposición correspondientes cambian su estado a EXPUESTA. 
De manera similar, si la fecha de fin es la actual y la exposición está en cualquier estado equivalente a PUBLICADA (todos excepto EN_CREACIÓN), 
las obras se retiran de dichas salas y cambian su estado a ALMACENADA.

SalaExposicion.java: Esta implementación permite distinguir entre las salas físicas y las salas de exposición. 
Se ha incluido un panel que muestra estas salas y las obras que contienen. 
Dado que solo se pueden añadir salas enteras a las exposiciones, si una sala está subdividida, 
sus subsalas también se añadirán como parte de la exposición.

Sala.java: Hemos desarrollado esta clase con la idea de que el gestor pueda obtener las características 
físicas de una sala y establecer divisiones con los parámetros físicos que elija. 
La sala original actúa como sala padre de cualquier subdivisión, manteniendo la consistencia entre la subdivisión y la sala principal.

Actividades.java: La implementación de base no permite que haya dos exposiciones en la misma fecha en la misma sala, con lo cual no 
si una actividad coincide con una sala, siempre será con unícamente una.

Creación de exposiciones: Para crear exposiciones, primero se añade una exposición y luego se asignan salas físicas a esta en el panel de salas. 
Posteriormente, en el panel de obras, se añaden obras que deben estar en estado de ALMACENADA o EXPUESTA a las salas de exposición previamente asignadas. 
En cada paso, se verifica que los recursos de la exposición (obras y salas) no se compartan con otras exposiciones en fechas coincidentes.

Modificación de exposiciones: Es posible cambiar el estado de las obras expuestas, en concreto pueden cambiar a RETIRIDA, PRESTADA Y RESTAURACION, en cualquiera
de estos casos la obra se eliminara de la sala de exposición de todas las exposiciones. 
No se pueden eliminar las obras de las salas de exposición de exposiciones que no esten en creación (si la exposición termina, el sistema elimina
las obras de las salas de exposición automáticamente) ya que una vez publicada la exposición no se puede modificar. 
Por otro lado, tampoco se pueden añadir nuevas obras a salas de exposición de exposiciones ya publicadas.

Para usar la GUI hemos implementado un mainVisual que crea una situación inicial de la aplicación;

-- CentroPrado (Tiene todos los elementos posibles)
Contraseña gestor: gestionPrado
Contraseña empleado: empleadoPrado

NumSS empleado1: 789 
NumSS empleado2: 987
NumSS empleado3: 123
NumSS empleado4: 321
NumSS empleado5: 654

-- CentroSofia (Casi vacio)
Contraseña gestor: gestionSofia
Contraseña empleado: empleadoSofia

-- Expofy
NIF y contraseña cliente1: 123 - Carlos123
NIF y contraseña cliente2: 456 - Ana456



