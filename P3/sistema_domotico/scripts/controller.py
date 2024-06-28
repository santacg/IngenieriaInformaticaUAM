import paho.mqtt.client as mqtt
import os
import sys
import django

sys.path.append("..")
os.environ.setdefault('DJANGO_SETTINGS_MODULE', 'sistema_domotico.settings')
django.setup()

from models.models import Switch, Sensor, Evento, Reloj

topic_str = "home/"


class Controller:

    def __init__(self):
        self.client = mqtt.Client()
        self.client.on_connect = self.on_connect
        self.client.connect('localhost', 1883, 60)

        self.client.loop_forever()
        self.client.disconnect()

    def on_connect(self, client, userdata, flags, rc):
        print("Conectado con codigo de resultado: " + str(rc))

        topic_rule_engine = topic_str + "rule_engine" 
        
        # Cola donde recibe los mensajes del motor de reglas
        self.client.message_callback_add(topic_rule_engine, self.on_rule_engine_message)
        self.client.subscribe(topic_rule_engine)

        self.topic_rule_engine_send = topic_rule_engine + '/send'

        for switch in Switch.objects.all():

            topic = topic_str + f"switch/{switch.id}" 

            # Cola donde recibe los mensajes de los interruptores
            self.client.message_callback_add(topic, self.on_switch_message)
            self.client.subscribe(topic)

        for sensor in Sensor.objects.all():

            topic = topic_str + f"sensor/{sensor.id}" 

            # Cola donde recibe los mensajes de los sensores
            self.client.message_callback_add(topic, self.on_sensor_message)
            self.client.subscribe(topic)

        for reloj in Reloj.objects.all():

            topic = topic_str + f"clock/{reloj.id}" 

            # Cola donde recibe los mensajes de los relojes
            self.client.message_callback_add(topic, self.on_clock_message)
            self.client.subscribe(topic)

    def on_switch_message(self, client, userdata, msg):

        msg = str(msg.payload.decode())

        partes = msg.split('/')

        if "FAIL" in msg:
            switch_id = partes[1]
            accion = partes[2]

            print(f"Error al cambiar al estado {accion} del interruptor con id {switch_id}")
            print("Reintentando...")
            self.enviar_mensaje_a_switch(switch_id, accion)
            return
        elif "ALREADY_SET" in msg:
            switch_id = partes[1]
            state = partes[2]

            print(f"El interruptor con id {switch_id} ya está en estado {state}")
            return

        switch_id = partes[0]
        state = partes[1]

        print(f"Cambio de estado del switch {switch_id} a estado {state}")

    def on_sensor_message(self, client, userdata, msg):

        msg = str(msg.payload.decode())

        partes = msg.split('/')

        sensor_id = partes[0]
        valor = partes[1]

        print(f"Mensaje recibido del sensor: {sensor_id} con valor {valor}")

        sensor = Sensor.objects.get(id=sensor_id)
        self.enviar_evento_a_rule_engine(sensor, valor)

    def on_clock_message(self, client, userdata, msg):

        msg = str(msg.payload.decode())

        partes = msg.split('/')

        reloj_id = partes[0]
        tiempo = partes[1]

        print(f"Mensaje recibido del reloj: {reloj_id} actualizado a {tiempo}")


    def on_rule_engine_message(self, client, userdata, msg):

        msg = str(msg.payload.decode())
        partes = msg.split('/')

        switch_id = partes[0]
        accion = partes[1]

        print(f"Mensaje recibido del motor de reglas: interruptor {switch_id} con accion {accion}")

        self.enviar_mensaje_a_switch(switch_id, accion)

    def enviar_mensaje_a_switch(self, switch_id, accion):

        self.client.publish(f"home/switch/{switch_id}/set", accion)

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
