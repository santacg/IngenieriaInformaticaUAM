import uuid
import pika
from .config import RABBITMQ_SERVER

class Cliente:

    def __init__(self):
        self.cliente_id = None  
        self.nombre_usuario = None 

        self.connection = pika.BlockingConnection(
                pika.ConnectionParameters(host=RABBITMQ_SERVER))
        self.channel = self.connection.channel()

        result = self.channel.queue_declare(queue='', durable=False ,auto_delete=True, exclusive=True)
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

        print(self.response)

        if self.response.find("ERROR") != -1:
            return False 
        
        return True


    def on_response(self, ch, method, props, body):
        if self.corr_id == props.correlation_id:
            self.response = str(body)


    def registrar(self, nombre_usuario):
        self.response = None
        self.corr_id = str(uuid.uuid4())

        body_msg = f"REGISTRAR nombre_usuario: {nombre_usuario}"
        
        if self.send_rpc_message(body_msg) == False:
            return False
        
        self.nombre_usuario = nombre_usuario
        self.cliente_id = self.response[self.response.find("cliente_id: "):]

        return True

    
    def realizar_pedido(self, productos_ids):
        if self.cliente_id == None:
            print("No puedes realizar un pedido sin estar registrado")
            return False
        
        self.response = None
        self.corr_id = str(uuid.uuid4())

        body_msg = f"AÑADIR_PEDIDO cliente_id: {self.cliente_id} productos_ids: {productos_ids}"

        if self.send_rpc_message(body_msg) == False:
            return False

        return True


    def __str__(self):
        return f"{self.nombre_usuario} {self.cliente_id}"


