import paho.mqtt.client as mqtt
import os
import sys
import django

sys.path.append("..")
os.environ.setdefault('DJANGO_SETTINGS_MODULE', 'sistema_domotico.settings')
django.setup()


from models.models import Switch, Sensor, Evento


class Controller:

    def __init__(self):
        self.client = mqtt.Client()
        self.client.on_connect = self.on_connect
        self.client.connect('localhost', 1883, 60)

        self.client.loop_forever()
        self.client.disconnect()

    def on_connect(self, client, userdata, flags, rc):
        print("Conectado con codigo de resultado: " + str(rc))

        topic_rule_engine = "home/rule_engine"
        
        # Cola donde recibe los mensajes del motor de reglas
        self.client.message_callback_add(topic_rule_engine, self.on_rule_engine_message)
        self.client.subscribe(topic_rule_engine)

        self.topic_rule_engine_send = topic_rule_engine + '/send'

        for switch in Switch.objects.all():

            topic = f"home/switch/{switch.nombre}/{switch.id}"

            # Cola donde recibe los mensajes de los interruptores
            self.client.message_callback_add(topic, self.on_switch_message)
            self.client.subscribe(topic)

        for sensor in Sensor.objects.all():

            topic = f"home/sensor/{sensor.nombre}/{sensor.id}"

            # Cola donde recibe los mensajes de los sensores
            self.client.message_callback_add(topic, self.on_sensor_message)
            self.client.subscribe(topic)

    def on_switch_message(self, client, userdata, msg):

        msg = (str)(msg.payload.decode())

        switch_nombre = msg.split('/')[0]
        if switch_nombre == 'FAIL':
            switch_id = msg.split('/')[1]
            accion = msg.split('/')[2]
            print(f"Error al cambiar el estado del interruptor con id {switch_id}")
            self.client.publish(f"home/switch/{switch_nombre}/{switch_id}/set", accion)
            return

        state = msg.split('/')[1]

        print(f"Mensaje recibido del interruptor: {switch_nombre} con estado {state}")

    def on_sensor_message(self, client, userdata, msg):

        msg = msg.payload.decode()
        partes = msg.split('/')

        sensor = partes[0]
        valor = partes[1]

        print(f"Mensaje recibido del sensor: {sensor} con valor {valor}")

        self.enviar_evento_a_rule_engine(sensor, valor)

    def on_rule_engine_message(self, client, userdata, msg):

        msg = msg.payload.decode()
        partes = msg.split('/')

        switch_id = partes[0]
        accion = partes[1]

        print(f"Mensaje recibido del motor de reglas: {switch_id} con accion {accion}")
        
        switch = Switch.objects.get(id=switch_id)
        topic = f"home/switch/{switch.nombre}/{switch_id}/set"

        self.client.publish(topic, accion)

    def enviar_evento_a_rule_engine(self, sensor, valor):

        evento = Evento.objects.create(sensor=sensor, valor=valor)

        message = f"{evento.sensor.id}/{evento.valor}"

        self.client.publish(self.topic_rule_engine_send, message)


if __name__ == '__main__':
    try:
        controller = Controller()
    except KeyboardInterrupt:
        print("Saliendo...")
        sys.exit(0)
