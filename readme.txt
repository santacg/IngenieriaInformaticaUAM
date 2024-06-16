*IMPORTANTE, Tareas.java: Se han implementado unas tareas que se ejecutan cada vez que se abre la aplicación, estas tareas comprueban las fechas de inicio
y fin de las exposiciones, de tal manera que si la fecha de inicio es la fecha actual y la exposición está en estado de PUBLICADA las obras en las salas de exposición de esa exposición concreta pasan al estado de Expuesta, de forma similar
si la fecha de fin es la actual y la exposición está en cualquier estado equivalente a PUBLICADA (todos menos EN_CREACION) las obras se eliminan de esas salas de exposición de esa exposición y pasan al estado de Almacenada. 
Los sorteos se realizan de forma automática cuando llega la fecha con el uso de Tareas.java.
A pesar de haber implementado esta automatización hemos dejado disponibles los métodos manuales de exponer obra y similares, aunque no se deberían usar.

SalaExposicion.java: Es una implementación que permite distinguir las salas físicas asociadas de las salas de la exposición, se ha incluido un panel que permite 
ver estas salas y las obras que contienen. Como en las exposiciones solo se pueden añadir salas enteras, si una sala esta subdividida se añadiran sus subsalas también como 
salas de exposición. 

Sala.java: Hemos implementado esta clase pensando que el gestor obtendría las características físicas de una sala y luego establecería una división
con los parametros físicos que eligiese, quedando la sala inicial como sala padre de esta subdivisión y manteniendo la consistencia entre la división 
y la sala principal.

Creacion de exposiciones: Para crear exposiciones primero se añade una exposición y posteriormente se van asignando salas físicas a la exposición en el panel de salas, luego en el panel de obras se van
añadiendo obras a las distintas salas de exposición previamente asignadas (el sistema comprueba que las obras sean compatibles con la sala de exposición elegida). En cada uno de estos pasos 
se comprueba que los recursos de la exposicion (obras y salas) no se compartan con exposiciones en fechas coincidentes.

Para usar la parte visual hemos implementado un mainVisual;

Contraseña gestor: gestionPrado
NumSS y contraseña empleado1: 789 - empleadoPrado
NIF y contraseña cliente1: 123 - Carlos123
