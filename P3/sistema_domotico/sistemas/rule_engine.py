import paho.mqtt.client as mqtt
import os
import sys
import django

sys.path.append("..")
os.environ.setdefault('DJANGO_SETTINGS_MODULE', 'sistema_domotico.settings')
django.setup()


from models.models import Switch, Sensor, Regla, Evento


class RuleEngine:

    def __init__(self, host, port):
        self.client = mqtt.Client()
        self.client.on_connect = self.on_connect
        self.client.connect(host, port)

        self.client.loop_forever()

    def on_connect(self, client, userdata, flags, rc):
        print("Conectado con codigo de resultado "+str(rc))

        topic = "home/rule_engine/send"
        self.topic_controller = "home/rule_engine"

        # Se suscribe a la cola donde recibe los mensajes del controlador
        self.client.message_callback_add(topic, self.on_message)
        self.client.subscribe(topic)

    def on_message(self, client, userdata, msg):
        # Se recibe un evento

        # Se obtiene el id del sensor y el valor del mensaje
        sensor_id, valor = msg.payload.decode().split('/')

        self.evaluar_reglas(sensor_id, valor)

    def evaluar_reglas(self, sensor_id, valor):

        # Se obtienen las reglas asociadas al sensor
        reglas = Regla.objects.filter(sensor_asociado__id=sensor_id)
        for regla in reglas:
            if self.comprobar_condicion(regla, valor):
                self.ejecutar_accion(sensor_id, regla)

    def comprobar_condicion(self, regla, valor):
        # Se obtiene la condicion de la regla
        condicion = (str)(regla.condicion)

        # Se evalua la condicion
        if condicion.find("==") != -1 and valor == condicion.split("==")[1]:
            self.ejecutar_accion(regla)
        elif condicion.find(">") != -1 and valor > condicion.split(">")[1]:
            self.ejecutar_accion(regla)
        elif condicion.find("<") != -1 and valor < condicion.split("<")[1]:
            self.ejecutar_accion(regla)
        else:
            return 

    def ejecutar_accion(self, sensor_id, rule):  
        # Se ejecuta la accion de la regla
        accion = rule.accion.split(' ')[0]

        if accion == 'ON' or accion == 'OFF':
            switch_id = rule.switch_asociado.id
            message = f"{switch_id}/{accion}"
            self.client.publish(self.topic_controller, message)


def main(host, port):
    RuleEngine(host, port)


if __name__ == '__main__':
    import argparse
    parser = argparse.ArgumentParser()
    parser.add_argument(
        '--host', default='localhost', help='MQTT broker host')
    parser.add_argument(
        '--port', default='1889', type=int, help='MQTT broker port')
    args = parser.parse_args()

    main(args.host, args.port)
