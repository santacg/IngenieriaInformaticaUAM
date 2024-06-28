import unittest
from unittest.mock import patch, MagicMock
from paho.mqtt.client import MQTTMessage

import sys
import os
import django

sys.path.append("../../")
os.environ.setdefault('DJANGO_SETTINGS_MODULE', 'sistema_domotico.settings')
django.setup()

from models.models import Sensor, Switch, Evento, Reloj
from scripts.controller import Controller


class TestController(unittest.TestCase):
    def setUp(self):
        self.mqtt_patch = patch('paho.mqtt.client.Client')
        self.MockClient = self.mqtt_patch.start()
        self.mock_client_instance = self.MockClient.return_value
        self.mock_client_instance.connect = MagicMock()
        self.mock_client_instance.loop_forever = MagicMock()
        self.mock_client_instance.disconnect = MagicMock()
        self.mock_client_instance.subscribe = MagicMock()
        self.mock_client_instance.publish = MagicMock()

        self.switch_patch = patch('models.models.Switch.objects.all', return_value=[
                                  Switch(id=1, nombre='Test Switch')])
        self.sensor_patch = patch('models.models.Sensor.objects.all', return_value=[
                                  Sensor(id=1, nombre='Test Sensor')])
        self.reloj_patch = patch('models.models.Reloj.objects.all', return_value=[
                                 Reloj(id=1, nombre='Test Clock')])
        self.evento_patch = patch('models.models.Evento.objects.create')
        self.mock_evento = self.evento_patch.start()
        
        mock_event = MagicMock(spec=Evento)
        mock_event.sensor.id = 1  
        mock_event.valor = '25'   
        self.mock_evento.return_value = mock_event

        self.mock_switches = self.switch_patch.start()
        self.mock_sensors = self.sensor_patch.start()
        self.mock_relojes = self.reloj_patch.start()

    def tearDown(self):
        self.mqtt_patch.stop()
        self.switch_patch.stop()
        self.sensor_patch.stop()
        self.reloj_patch.stop()
        self.evento_patch.stop()

    def test_connection_to_broker_success(self):
        Controller('localhost', 1883)
        self.mock_client_instance.connect.assert_called_once_with(
            'localhost', 1883)

    def test_connection_to_broker_failure(self):
        self.mock_client_instance.connect.side_effect = Exception(
            "Connection failed")
        with self.assertRaises(Exception):
            Controller('localhost', 1883)

    def test_message_handling(self):
        controller = Controller('localhost', 1883)
        message = MQTTMessage()
        message.payload = b'1/25'
        controller.on_sensor_message(None, None, message)
        self.mock_evento.assert_called_once()

    def test_sensor_message_triggers_rule_check(self):
        controller = Controller('localhost', 1883)
        message = MQTTMessage()
        message.payload = b'1/25'
        controller.on_sensor_message(None, None, message)
        self.mock_evento.assert_called_once()
        self.mock_client_instance.publish.assert_called_with(
            'home/rule_engine/send', '1/25')

    def test_rule_engine_response_triggers_action(self):
        controller = Controller('localhost', 1883)
        message = MQTTMessage()
        message.payload = b'1/ON'
        controller.on_rule_engine_message(None, None, message)
        self.mock_client_instance.publish.assert_called_with(
            'home/switch/1/set', 'ON')

if __name__ == '__main__':
    unittest.main()
