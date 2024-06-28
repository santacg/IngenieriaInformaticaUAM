import unittest
from unittest.mock import patch, MagicMock
from paho.mqtt.client import MQTTMessage
import sys
import os
import django

sys.path.append("../../")
os.environ.setdefault('DJANGO_SETTINGS_MODULE', 'sistema_domotico.settings')
django.setup()

from scripts.dummy_switch import DummySwitch
from scripts.dummy_sensor import DummySensor
from models.models import Sensor, Switch


class TestDummyDevices(unittest.TestCase):

    def setUp(self):
        # Preparar el cliente MQTT mock para todas las pruebas
        self.mqtt_patch = patch('paho.mqtt.client.Client')
        self.addCleanup(self.mqtt_patch.stop)
        self.mock_mqtt_client = self.mqtt_patch.start()()
        self.mock_mqtt_client.connect = MagicMock()
        self.mock_mqtt_client.loop_forever = MagicMock()
        self.mock_mqtt_client.disconnect = MagicMock()
        self.mock_mqtt_client.subscribe = MagicMock()
        self.mock_mqtt_client.publish = MagicMock()

    def test_connection_to_broker_success(self):
        """Conecta correctamente con el broker."""
        self.mock_mqtt_client.connect.return_value = 0  # Simula una conexión exitosa
        DummySensor('localhost', 1883, 20, 30, 1, 1, '1')
        self.mock_mqtt_client.connect.assert_called_once_with(
            'localhost', 1883)

    def test_connection_to_broker_failure(self):
        """Si no conecta con el broker da error."""
        self.mock_mqtt_client.connect.side_effect = Exception(
            "Connection failed")
        with self.assertRaises(Exception) as context:
            DummySensor('localhost', 1883, 20, 30, 1, 1, '1')
        self.assertTrue("Connection failed" in str(context.exception))

    def test_command_line_parameters_sensor(self):
        """Probar que el sistema lee bien los parámetros por línea de comandos para sensor."""
        sensor = DummySensor('localhost', 1883, 20, 30, 1, 1, '1')
        self.assertEqual(sensor.min, 20)
        self.assertEqual(sensor.max, 30)
        self.assertEqual(sensor.increment, 1)
        self.assertEqual(sensor.interval, 1)

    def test_switch_state_change_on_action(self):
        """(switch) Cambia de estado ante una acción."""
        switch_mock = MagicMock(spec=Switch, state='OFF')
        with patch('models.models.Switch.objects.get', return_value=switch_mock):
            switch = DummySwitch('localhost', 1883, 1.0, '1')
            message = MQTTMessage()
            message.payload = b'ON'
            switch.on_set_message(None, None, message)
            self.assertEqual(switch_mock.state, 'ON')

    def test_sensor_value_changes_within_intervals(self):
        """(sensor) Cambia de estado en intervalos entre min y max."""
        sensor_mock = MagicMock(spec=Sensor, valor=20,
                                nombre='Temperature Sensor')
        with patch('models.models.Sensor.objects.get', return_value=sensor_mock):
            with patch('paho.mqtt.client.Client') as mock_mqtt_client_class:
                mock_mqtt_client = mock_mqtt_client_class.return_value
                mock_mqtt_client.publish = MagicMock()

                sensor = DummySensor('localhost', 1883, 20, 25, 1, 1, '1')
                sensor.simular_valores()

                self.assertTrue(mock_mqtt_client.publish.called)
                self.assertTrue(20 <= sensor_mock.valor <= 25,
                                f"Valor {sensor_mock.valor} fuera de intervalo [20, 25]")


if __name__ == '__main__':
    unittest.main()
