import pika
import time
import random
from .config import RABBITMQ_SERVER, P_ALMACEN


class Robot:
    def __init__(self):
        self.connection = pika.BlockingConnection(
            pika.ConnectionParameters(host=RABBITMQ_SERVER))
        self.channel = self.connection.channel()

        self.channel.queue_declare(
            queue="2323_04_controlador_robots_produccion", durable=False, auto_delete=True)
        self.channel.queue_declare(
            queue="2323_04_controlador_robots_consumo", durable=False, auto_delete=True)

    def iniciar_robot(self):
        self.channel.basic_qos(prefetch_count=1)
        self.channel.basic_consume(queue="2323_04_controlador_robots_produccion",
                                   on_message_callback=self.mover_producto,
                                   auto_ack=False)

        print("Robot empieza a escuchar mensajes...")
        self.channel.start_consuming()

    def mover_producto(self, ch, method, properties, body):
        body = body.decode('utf-8')
        print(body)

        pedido_id = body[6:]

        encontrado = self.buscar_producto()
        if encontrado:
            response = f"MOVIDO {pedido_id}"
        else:
            response = f"NO_MOVIDO {pedido_id}"

        ch.basic_publish(exchange='',
                         routing_key='2323_04_controlador_robots_consumo',
                         body=response)
        ch.basic_ack(delivery_tag=method.delivery_tag)

    def buscar_producto(self):
        time.sleep(random.randint(5, 10))
        # P_ALMACEN de probabilidad de éxito
        return random.randint(0, 100) < P_ALMACEN

    def detener(self):
        self.channel.close()
        self.connection.close()
        print("Robot detenido y conexión cerrada.")
