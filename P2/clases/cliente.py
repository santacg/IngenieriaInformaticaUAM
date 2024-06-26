import uuid
import pika
from .config import RABBITMQ_SERVER


class Cliente:

    def __init__(self):
        self.cliente_id = None
        self.nombre_usuario = None
        self.pedidos = []

        self.connection = pika.BlockingConnection(
            pika.ConnectionParameters(host=RABBITMQ_SERVER))
        self.channel = self.connection.channel()

        result = self.channel.queue_declare(
            queue='', durable=False, auto_delete=True, exclusive=True)
        self.callback_queue = result.method.queue

        self.channel.basic_consume(
            queue=self.callback_queue,
            on_message_callback=self.on_response,
            auto_ack=True)

        self.response = None
        self.corr_id = None

    def send_rpc_message(self, body_msg):
        self.channel.basic_publish(
            exchange='',
            routing_key='2323_04_controlador_clientes',
            properties=pika.BasicProperties(
                reply_to=self.callback_queue,
                correlation_id=self.corr_id,
            ),
            body=body_msg)

        while self.response is None:
            self.connection.process_data_events()

        if self.response.find("ERROR") != -1:
            return False

        return True

    def on_response(self, ch, method, props, body):
        if self.corr_id == props.correlation_id:
            self.response = body.decode('utf-8')
            print(self.response)

    def registrar(self, nombre_usuario):
        if self.cliente_id != None:
            print("Ya estás registrado")
            return False

        self.response = None
        self.corr_id = str(uuid.uuid4())

        body_msg = f"REGISTRAR nombre_usuario: {nombre_usuario}"

        if self.send_rpc_message(body_msg) == False:
            return False

        self.nombre_usuario = nombre_usuario
        self.cliente_id = self.response[self.response.find(
            "cliente_id: ") + len("cliente_id: "):]

        return True

    def realizar_pedido(self, productos_ids):
        if self.cliente_id == None:
            print("No puedes realizar un pedido sin estar registrado")
            return False

        self.response = None
        self.corr_id = str(uuid.uuid4())

        body_msg = f"REALIZAR_PEDIDO cliente_id: {(str)(self.cliente_id)} productos_ids: {productos_ids}"

        if self.send_rpc_message(body_msg) == False:
            return False

        inicio_pedido_id = len("PEDIDO_REALIZADO ")
        pedido_id = self.response[inicio_pedido_id:]
        self.pedidos.append(pedido_id)
        return True

    def ver_pedidos(self):
        if self.cliente_id == None:
            print("No puedes ver pedidos sin estar registrado")
            return False

        self.response = None
        self.corr_id = str(uuid.uuid4())

        if self.pedidos == None:
            self.pedidos = " "

        body_msg = f"VER_PEDIDOS {self.pedidos}"

        if self.send_rpc_message(body_msg) == False:
            return False

        return True

    def cancelar_pedido(self, pedido_id):
        if self.cliente_id == None:
            print("No puedes cancelar un pedido sin estar registrado")
            return False

        self.response = None
        self.corr_id = str(uuid.uuid4())

        body_msg = f"CANCELAR_PEDIDO {pedido_id}"

        if self.send_rpc_message(body_msg) == False:
            return False

        return True

    def __str__(self):
        return f"nombre de usuario: {self.nombre_usuario}\nid de cliente: {self.cliente_id}"

    def close(self):
        self.channel.close()
        self.connection.close()
        print("Cliente detenido y conexión cerrada.")
