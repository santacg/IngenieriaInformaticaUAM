import pika
import time
import random
from .config import RABBITMQ_SERVER


class Repartidor:
    def __init__(self):
        self.pedidos_pendientes = []
        self.connection = pika.BlockingConnection(
            pika.ConnectionParameters(host=RABBITMQ_SERVER))
        self.channel = self.connection.channel()
        self.channel.queue_declare(
            queue=QUEUE_NAME, durable=True, auto_delete=False)

    def empezar_reparto(self):
        def callback(ch, method, properties, body):
            print(f"Recibido pedido: {body.decode()}")
            pedido_id = body.decode().split(',')[0]
            self.pedidos_pendientes.append(pedido_id)
            self.procesar_pedido(pedido_id, ch, method)

        self.channel.basic_consume(
            queue=QUEUE_NAME, on_message_callback=callback, auto_ack=False)
        print('El repartidor está esperando órdenes. Para salir presione CTRL+C')
        self.channel.start_consuming()

    def procesar_pedido(self, pedido_id, ch, method):
        # Simulación del proceso de entrega con reintento
        if self.intentar_entregar():
            ch.basic_ack(delivery_tag=method.delivery_tag)
            print(f"Pedido {pedido_id} entregado con éxito.")
            self.pedidos_pendientes.remove(pedido_id)
        else:
            print(f"Fallo al entregar pedido {pedido_id}, se reintentara.")

    def intentar_entregar(self):
        # Simulación con un cierto umbral de éxito
        time.sleep(random.randint(5, 10))
        return random.random() < 0.8

    def stop(self):
        self.connection.close()
        print("Repartidor parado y conexion cerrada")
