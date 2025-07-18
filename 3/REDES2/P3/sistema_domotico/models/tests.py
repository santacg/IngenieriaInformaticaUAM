import sys
from django.test import TestCase
from models import Sensor, Switch
from ..scripts.dummy_sensor import DummySensor
from ..scripts.dummy_switch import DummySwitch 

# Create your tests here.
class TestDevice(TestCase):

    def setUp(self):
        # Limpia la base de datos antes de cada test
        Sensor.objects.all().delete()
        Switch.objects.all().delete()

    def test_conexion_correcta(self):
        dummy_switch = DummySwitch('localhost', 1883, 0.5, 1)
        pass

    def test_conexion_error(self):
        pass

    def test_lectura_correcta_de_parametros_por_linea_de_comandos(self):
        # Temporalmente reemplaza sys.argv para simular argumentos de línea de comandos
        sys.argv = ["manage.py", "test", "--parametro", "valor"]
        # Aquí escribes el test para verificar que los parámetros se leen correctamente
        pass

    def test_switch_cambia_de_estado(self):
        # Configura un switch y realiza una acción que debería cambiar su estado
        # Verifica que el estado haya cambiado como se esperaba
        pass

    def test_sensor_cambia_de_estado_en_intervalos(self):
        # Configura un sensor con valores min y max
        # Simula el paso del tiempo y verifica que el estado del sensor cambie dentro de los intervalos esperados
        pass