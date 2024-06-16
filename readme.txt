Obras.java: Las obras solo pasan al estado de expuesta cuando una exposición publicada (o estados equivalentes a publicada) está entre la fecha inicial y final.

SalaExposicion.java: Se ha implementado salas de exposición que se asocian a las salas físicas, esto permite tener distintas exposiciones en creación con las mismas salas físicas asignadas,
con lo cual el gestor deberá cuadrar la utilización de estas salas con las distintas fechas de las exposiciones para que en una misma sala no coincidan dos exposiciones de forma simultánea, aún así,
si el gestor trata de publicar una exposición con obras ya expuestas en otra exposición con las mismas fechas el sistema proporcionará un error.

Exposicion.java, SalaExposicion.java y Tareas.java: Se han implementado unas tareas que se ejecutan cada vez que se abre la aplicación, estas tareas comprueban las fechas de inicio
y fin de las exposiciones, de tal manera que si la fecha de inicio es la fecha actual las obras en las salas de exposición de esa exposición concreta pasan al estado de Expuesta, de forma similar
si la fecha de fin es la actual las obras se eliminan de esas salas de exposición de esa exposición y pasan al estado de Almacenada. 

Sorteos.java: Los sorteos se realizan de forma automática cuando llega la fecha con el uso de Tareas.java.

Sala.java: Hemos implementado esta clase pensando que el gestor obtendría las características físicas de una sala y luego establecería una división
con los parametros físicos que eligiese, quedando la sala inicial como sala padre de esta subdivisión y manteniendo la consistencia entre la división 
y la sala principal.

Para usar la parte visual hemos implementado un mainVisual;

Contraseña gestor: gestionPrado
NumSS y contraseña empleado1: 789 - empleadoPrado
NIF y contraseña cliente1: 123 - Carlos123
