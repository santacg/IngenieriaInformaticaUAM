Sala.java: Hemos implementado esta clase pensando que el gestor obtendría las características físicas de una sala y luego establecería una división
con los parametros físicos que eligiese, quedando la sala inicial como sala padre de esta subdivisión y manteniendo la consistencia entre la división 
y la sala principal.

Diagrama de clases: Se han obviado los getters, setters, equals() y toString() del diagrama de clases para facilitar su visualización.

Hay métodos en los que se asume que solo serán llamados por ciertos roles de usuario del sistema, por ejemplo, solo el gestor llamará a los métodos para añadir empleados obra o 
solo los clientes registrados llamarán a los métodos de busqueda avanzada.
Estos métodos se mostrarán con la implementación gráfica unicamente a los roles que correspondan, por tanto solo es necesario comprobar EN EL CASO DEL GESTOR si el rol esta logeado o no.