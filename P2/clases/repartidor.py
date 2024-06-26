import pika
import time
import random
from .config import RABBITMQ_SERVER, P_ENTREGA


class Repartidor:
    def __init__(self):
        self.connection = pika.BlockingConnection(
            pika.ConnectionParameters(host=RABBITMQ_SERVER))
        self.channel = self.connection.channel()

        self.channel.queue_declare(
            queue="2323_04_controlador_repartidores_produccion", durable=False, auto_delete=True)
        self.channel.queue_declare(
            queue="2323_04_controlador_repartidores_consumo", durable=False, auto_delete=True)

    def iniciar_repartidor(self):
        self.channel.basic_qos(prefetch_count=1)
        self.channel.basic_consume(queue="2323_04_controlador_repartidores_produccion",
                                   on_message_callback=self.realizar_entrega,
                                   auto_ack=False)

        print("Repartidor empieza a escuchar mensajes...")
        self.channel.start_consuming()

    def realizar_entrega(self, ch, method, properties, body):
        body = body.decode('utf-8')
        print(body)

        pedido_id = body[8:]

        ch.basic_publish(exchange='',
                         routing_key='2323_04_controlador_repartidores_consumo',
                         body=f"EN_ENTREGA {pedido_id}")

        entregado = False
        for _ in range(2):
            entregado = self.intentar_entrega()
        
        if entregado == True:
            response = f"ENTREGADO {pedido_id}"
        else:
            response = f"NO_ENTREGADO {pedido_id}"

        ch.basic_publish(exchange='',
                         routing_key='2323_04_controlador_repartidores_consumo',
                         body=response)
        ch.basic_ack(delivery_tag=method.delivery_tag)

    def intentar_entrega(self):
        time.sleep(random.randint(10, 20))
        # P_ENTREGA de probabilidad de éxito
        return True if random.randint(0, 100) < P_ENTREGA else False 

    def close(self):
        self.channel.close()
        self.connection.close()
        print("Repartidor detenido y conexión cerrada.")

