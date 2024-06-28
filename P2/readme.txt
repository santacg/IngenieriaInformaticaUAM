Para probar la aplicación lo mejor es ejecutar primero el controlador (launch_controlador.py), luego añadir robots (launch_robot.py) y
repartidores (launch_repartidor.py), y finalmente se puede o ejecutar los clientes (launch_cliente.py) o manualmente simular un cliente 
a través del script command_line.py en el que se ha optado por una interfaz visual sencilla en vez de hacer uso de comandos. 
El controlador al final muestra sus clientes y pedidos, se puede ver que si el sistema se deja ejecutando una cantidad de 
tiempo suficiente todos los pedidos acaban entregados.

El simulador funciona correctamente pero la velocidad es muy alta como para 
poder analizar el funcionamiento del sistema.

Para poder ejecutar se necesitan instalar las librerias presentes en requirements.txt (solo hacen falta pika y pickle).

Para borrar el estado del controlador, basta con borrar el archivo state.pk1