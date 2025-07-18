import os
import django
import sys

# Setup Django environment
sys.path.append("..")
os.environ.setdefault("DJANGO_SETTINGS_MODULE", "sistema_domotico.settings")
django.setup()

from models.models import Switch, Sensor, Reloj, Regla

def crear_dispositivos():

    # Antes limpiamos la base de datos
    Switch.objects.all().delete()
    Sensor.objects.all().delete()
    Reloj.objects.all().delete()

    switch = Switch.objects.create(id=1, nombre="Switch_motor")
    print(f"Switch creado con ID: {switch.id} y estado {switch.state}")

    sensor = Sensor.objects.create(id=1,
        nombre="Sensor_temperatura_motor", valor=250, unidad_de_medida="Celsius")
    print(
        f"Sensor creado con ID: {sensor.id} y valor {sensor.valor} {sensor.unidad_de_medida}")

    reloj = Reloj.objects.create(id=1, nombre="Reloj_motor")
    print(f"Reloj creado con ID: {reloj.id} y tiempo {reloj.tiempo}")

    regla = Regla.objects.create(id=1, nombre="Regla_motor", condicion="sensor_temperatura_motor > 270",
                                 accion="ON switch_motor", sensor_asociado=sensor, switch_asociado=switch)
    print(f"Regla creada con ID: {regla.id} y condicion {regla.condicion}")

    switch.save()
    sensor.save()
    reloj.save()
    regla.save()


if __name__ == "__main__":
    crear_dispositivos()
