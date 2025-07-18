import django
import paho.mqtt.client as mqtt
import time
import threading
import argparse
import sys
import os
from datetime import datetime, timedelta

sys.path.append("..")
os.environ.setdefault('DJANGO_SETTINGS_MODULE', 'sistema_domotico.settings')
django.setup()

from models.models import Reloj

topic_str = "home/"


class DummyClock:
    def __init__(self, host, port, start_time, increment, rate, clock_id):
        self.client = mqtt.Client()
        self.client.connect(host, port)

        try:
            self.reloj = Reloj.objects.get(id=clock_id)
            self.topic = topic_str + f"clock/{self.reloj.id}"

            print(
                f"Reloj inicializado: {self.reloj.nombre} con tiempo inicial: {self.reloj.tiempo}")

            if start_time:
                self.time_obj = datetime.strptime(
                    start_time, "%H:%M:%S").time()
            else:
                self.time_obj = datetime.now().time()

            self.increment = increment
            self.rate = rate

            self.time_thread = threading.Thread(target=self.simular_tiempo)
            self.time_thread.daemon = True
            self.time_thread.start()

        except Reloj.DoesNotExist:
            print(f"No se ha encontrado el reloj con ID: {clock_id}")
            self.client.disconnect()
            return

        self.client.loop_forever()

    def publicar_tiempo(self):
        time_str = self.time_obj.strftime("%H:%M:%S")
        print(f"Publicando hora del reloj: {time_str}")

        msg = f"{self.reloj.id}/{time_str}"
        self.client.publish(self.topic, msg)

    def simular_tiempo(self):
        while True:
            next_time = datetime.combine(
                datetime.today(), self.time_obj) + timedelta(seconds=self.increment)
            self.time_obj = next_time.time()
            self.reloj.tiempo = self.time_obj
            self.reloj.save()
            for _ in range(self.rate):
                self.publicar_tiempo()
                time.sleep(1 / self.rate)


def main(host, port, start_time, increment, rate, clock_id):
    DummyClock(host, port, start_time, increment, rate, clock_id)


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description="Dummy Clock Simulator")
    parser.add_argument('--host', default='localhost', help='MQTT broker host')
    parser.add_argument('--port', default=1883, type=int,
                        help='MQTT broker port')
    parser.add_argument('--time', type=str, default=datetime.now().strftime("%H:%M:%S"),
                        help='Start time in HH:MM:SS format')
    parser.add_argument('--increment', type=int, default=1,
                        help='Increment between time updates in seconds')
    parser.add_argument('--rate', type=int, default=1,
                        help='Rate of sending time updates per second')
    parser.add_argument('clock_id', type=str, help='Clock ID')
    args = parser.parse_args()

    try:
        main(args.host, args.port, args.time,
         args.increment, args.rate, args.clock_id)
    except KeyboardInterrupt:
        print("Saliendo...")
        sys.exit(0)
