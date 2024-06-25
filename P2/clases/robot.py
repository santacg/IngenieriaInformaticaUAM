import pika
import time
import random
from .config import RABBITMQ_SERVER 


class Robot:
    def __init__(self):
        self.disponible = True 
        self.pedido_actual = None
        self.connection = pika.BlockingConnection(
            pika.ConnectionParameters(host=RABBITMQ_SERVER))
        self.channel = self.connection.channel()
        self.channel.queue_declare(
            queue=QUEUE_NAME, durable=True, auto_delete=False)

    def recibir_pedidos(self):
        def callback(ch, method, properties, body):
            if self.disponible:
                print(f"Recibido: {body.decode()}")
                self.procesar_pedido(body.decode())
            else:
                print("El robot no está disponible.")

        self.channel.basic_consume(
            queue=QUEUE_NAME, on_message_callback=callback, auto_ack=False)
        print('El robot está esperando órdenes. Para salir presione CTRL+C')
        self.channel.start_consuming()

    def procesar_pedido(self):
        def callback(ch, method, properties, body):
            if self.disponible:
                print(f"Recibido {body.decode()}")
                self.disponible = False
                success = self.intent_fetch_product()
                if success:
                    ch.basic_ack(delivery_tag=method.delivery_tag)
                    print("Producto encontrado y listo para entrega.")
                    self.reportar_exito(body.decode())
                else:
                    print("Fallo al buscar el producto, se reintentará.")
                    self.reportar_fallo(body.decode())
                self.disponible = True
            else:
                print("El robot no esta disponible")

        self.channel.basic_consume(
            queue=QUEUE_NAME, on_message_callback=callback, auto_ack=False)
        print('El robot está esperando órdenes. Para salir presione CTRL+C')
        self.channel.start_consuming()

    def intent_fetch_product(self):
        # Simula el proceso de búsqueda con una probabilidad de éxito
        time.sleep(random.randint(5, 10))
        return random.random() < 0.9  # 90% de probabilidad de éxito

    def reportar_exito(self, order_info):
        self.channel.basic_publish(exchange='', routing_key='success_queue', body=f"Success:{order_info}")

    def reportar_fallo(self, order_info):
        self.channel.basic_publish(exchange='', routing_key='failure_queue', body=f"Failure:{order_info}")

    def detener(self):
        self.connection.close()
        print("Robot detenido y conexión cerrada.")
