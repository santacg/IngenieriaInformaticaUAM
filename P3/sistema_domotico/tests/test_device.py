import pytest
from unittest.mock import patch, MagicMock
from paho.mqtt.client import Client as MqttClient
import sys

sys.path.append("..")

from sistemas.dummy_sensor import DummySensor

# Test de conexión MQTT
def test_mqtt_connection_success():
    with patch.object(MqttClient, 'connect', return_value=None) as mock_connect:
        client = MqttClient()
        client.connect('localhost', 1883)
        mock_connect.assert_called_once_with('localhost', 1883)

def test_mqtt_connection_failure():
    with patch.object(MqttClient, 'connect', side_effect=Exception("Connection failed")) as mock_connect:
        client = MqttClient()
        with pytest.raises(Exception) as excinfo:
            client.connect('wrong_host', 1883)
        assert "Connection failed" in str(excinfo.value)
        mock_connect.assert_called_once_with('wrong_host', 1883)

# Test de lectura de parámetros de línea de comandos
def test_command_line_arguments():
    test_args = ['--host', 'localhost', '--port', '1883', '--time', '12:00:00', '--increment', '1', '--rate', '1', '1']
    with patch('sys.argv', test_args):
        args = parser.parse_args()
        assert args.host == 'localhost'
        assert args.port == 1883
        assert args.time == '12:00:00'
        assert args.increment == 1
        assert args.rate == 1
        assert args.clock_id == '1'

# Cambio de estado de un switch
def test_switch_state_change():
    switch = DummySwitch('localhost', 1883, 'ON', 'OFF', 'switch_id')
    switch.toggle()  # Asumiendo que toggle cambia el estado de ON a OFF o viceversa
    assert switch.state == 'OFF'

# Cambio de estado del sensor
def test_sensor_state_change():
    sensor = DummySensor('localhost', 1883, 20, 30, 1, 1, 'sensor_id')
    sensor.simulate_values()
    assert 20 <= sensor.value <= 30
