import django
import paho.mqtt.client as mqtt
import time
import threading
import argparse
import sys
import os

sys.path.append("..")
os.environ.setdefault('DJANGO_SETTINGS_MODULE', 'sistema_domotico.settings')
django.setup()

from models.models import Sensor

topic_str = "home/"


class DummySensor:
    def __init__(self, host, port, minimum, maximum, increment, interval, sensor_id):
        self.client = mqtt.Client()
        self.client.connect(host, port)

        # Configura el sensor con los valores dados por comando
        self.min = minimum
        self.max = maximum
        self.increment = increment
        self.interval = interval

        try:
            self.sensor = Sensor.objects.get(id=sensor_id)
            self.topic = topic_str + f"sensor/{self.sensor.id}"

            print(
                f"Sensor inicializado: {self.sensor.nombre} con valor: {self.sensor.valor} e id: {self.sensor.id}")

            # Inicia un hilo para simular cambios en el valor del sensor
            self.value_thread = threading.Thread(
                target=self.simular_valores)
            self.value_thread.daemon = True
            self.value_thread.start()

        except Sensor.DoesNotExist:
            print(f"No se ha encontrado sensor con ID: {sensor_id}")
            self.client.disconnect()
            return

        self.client.loop_forever()
        self.client.disconnect()

    def publicar_valor(self):
        print(f"Informando de valor de sensor: {self.sensor.valor}")
        self.client.publish(self.topic, str(
            f"{self.sensor.id}/{self.sensor.valor}"))

    def simular_valores(self):
        current_value = self.min
        if self.increment > 0:  # Incrementando valores
            while current_value <= self.max:
                self.sensor.valor = current_value
                self.sensor.save()
                self.publicar_valor()
                time.sleep(self.interval)
                current_value += self.increment
        else:  # Decrementando valores (increment es negativo)
            while current_value >= self.max:
                self.sensor.valor = current_value
                self.sensor.save()
                self.publicar_valor()
                time.sleep(self.interval)
                current_value += self.increment

def main(host, port, min, max, increment, interval, sensor_id):
    DummySensor(host, port, min, max, increment, interval, sensor_id)


if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument('--host', default='localhost', help='MQTT broker host')
    parser.add_argument('--port', default=1883, type=int,
                        help='MQTT broker port')
    parser.add_argument('--min', default=20, type=int,
                        help='Minimum sensor value')
    parser.add_argument('--max', default=30, type=int,
                        help='Maximum sensor value')
    parser.add_argument('--increment', default=1,
                        type=int, help='Value increment')
    parser.add_argument('--interval', default=1, type=int,
                        help='Interval in seconds')
    parser.add_argument('sensor_id', type=str, help='Sensor ID')
    args = parser.parse_args()

    try:
        main(args.host, args.port, args.min, args.max,
             args.increment, args.interval, args.sensor_id)
    except KeyboardInterrupt:
        print("Saliendo...")
        sys.exit(0)
