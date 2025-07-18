import paho.mqtt.client as mqtt
import argparse
import sys
import os
import random
import django

sys.path.append("..")
os.environ.setdefault('DJANGO_SETTINGS_MODULE', 'sistema_domotico.settings')
django.setup()

from models.models import Switch

topic_str = "home/"


class DummySwitch(object):

    def __init__(self, host, port, probability, id):
        self.probability = probability
        self.client = mqtt.Client()
        self.client.connect(host, port)

        # Busca el interruptor en la base de datos
        try:
            self.switch = Switch.objects.get(id=id)
            self.topic = topic_str + f"switch/{self.switch.id}"
            self.topic_set = self.topic + '/set'

            # Configura las suscripciones y callbacks
            self.client.message_callback_add(
                self.topic_set, self.on_set_message)
            self.client.subscribe(self.topic_set)

            print(
                f"Switch inicializado: {self.switch.nombre} con estado {self.switch.state} e id {self.switch.id}")

        except Switch.DoesNotExist:
            print(f"No se ha encontrado switch con ID: {id}")
            self.client.disconnect()
            return

        self.client.loop_forever()
        self.client.disconnect()

    def on_set_message(self, client, userdata, msg):

        # Cambia el estado del interruptor si el mensaje es 'ON' o 'OFF'
        accion = str(msg.payload.decode())

        if accion in ['ON', 'OFF']:
            if self.switch.state == accion:
                print(f"El interruptor ya está en estado {accion}")
                self.informar_controlador(f"ALREADY_SET/{self.switch.id}/{accion}")
                return
            # Cambia el estado del interruptor con una probabilidad dada
            if random.random() <= self.probability:
                self.switch.state = accion
                self.switch.save()
                print(f"Cambio de estado de switch a: {self.switch.state}")
                # Informa al controlador del cambio de estado
                self.informar_controlador(f"{self.switch.id}/{self.switch.state}")
            else:
                # Informa al controlador que el cambio de estado falló
                self.informar_controlador(f"FAIL/{self.switch.id}/{accion}")

    def informar_controlador(self, message):
        # Publica el mensaje en el topic del controlador
        self.client.publish(self.topic, message)


def main(host, port, probability, id):
    DummySwitch(host, port, probability, id)


if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument('--host', default='localhost', help='MQTT broker host')
    parser.add_argument('--port', default=1883, type=int,
                        help='MQTT broker port')
    parser.add_argument('--probability', default=0.9, type=float,
                        help='Probability of successful state change')
    parser.add_argument('id', type=str, help='ID of the switch')
    args = parser.parse_args()

    try:
        main(args.host, args.port, args.probability, args.id)
    except KeyboardInterrupt:
        print("Saliendo...")
        sys.exit(0)
