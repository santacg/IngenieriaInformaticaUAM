import pika
import pickle
import uuid
from .config import RABBITMQ_SERVER
from .robot import Robot
from .repartidor import Repartidor
from .cliente import Cliente
from .pedido import Pedido


class Controlador:
    def __init__(self):
        self.clientes = {} 
        self.pedidos = [] 

        self.connection = pika.BlockingConnection(
            pika.ConnectionParameters(host=RABBITMQ_SERVER))
        self.channel = self.connection.channel()

        self.channel.queue_declare(queue='2323_04_controlador_robots', durable=False, auto_delete=True)
        self.channel.queue_declare(queue='2323_04_controlador_repartidores', durable=False, auto_delete=True)
        self.channel.queue_declare(queue='2323_04_controlador_clientes', durable=False, auto_delete=True)


    def iniciar_controlador(self):
        self.channel.basic_consume(queue='2323_04_controlador_clientes', on_message_callback=self.cliente_callback)
        print("Esperando peticion RPC de los clientes...")

        self.channel.start_consuming()


    def get_cliente_uuid(self, nombre_usuario):
        if self.clientes.get(nombre_usuario) != None:
            return None

        return str(uuid.uuid4())


    def cliente_callback(self, ch, method, props, body):
        # Hay que decodificar a formato de string sino no funciona
        body = body.decode('utf-8')
        
        if body.find("REGISTRAR") != -1:
            response = self.registrar_cliente(body)
        elif body.find("AÑADIR_PEDIDO") != -1:
            response = self.realizar_pedido(body)
        else:
            return

        print(response)
        ch.basic_publish(exchange='',
                     routing_key=props.reply_to,
                     properties=pika.BasicProperties(correlation_id = \
                                                         props.correlation_id),
                     body=str(response))
        ch.basic_ack(delivery_tag=method.delivery_tag)


    def registrar_cliente(self, body):
        nombre_usuario = body[26:] 

        cliente_id = self.get_cliente_uuid(nombre_usuario)
        if cliente_id is None:
            response = f"REGISTRAR ERROR"
        else:
            response = f"REGISTRADO nombre_usuario: {nombre_usuario} cliente_id: {cliente_id}"
            self.clientes[nombre_usuario] = cliente_id

        return response


    def realizar_pedido(self, body):
        inicio_cliente_id = body.find("cliente_id: ")
        cliente_id = body[inicio_cliente_id : body.find(" ", inicio_cliente_id)]
        productos_ids = body[body.find("productos_ids: "):].strip()

        productos_ids = productos_ids.split(',')
        productos_ids = [int(id.strip()) for id in productos_ids]

        print(cliente_id)
        print(productos_ids)

        new_pedido = Pedido(cliente_id, productos_ids)
        self.pedidos.append(new_pedido)

        response = f"PEDIDO_AÑADIDO {new_pedido.pedido_id}"

        return response


    def guardar_estado(self):
        with open('state.pkl', 'wb') as f:
            pickle.dump((self.clientes, self.pedidos), f)

    def cargar_estado(self):
        try:
            with open('state.pkl', 'rb') as f:
                self.clientes, self.pedidos = pickle.load(f)
        except FileNotFoundError:
            self.clientes = {}
            self.pedidos = []

    def close(self):
        self.connection.close()

