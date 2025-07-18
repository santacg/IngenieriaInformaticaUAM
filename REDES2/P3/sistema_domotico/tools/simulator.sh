#!/bin/bash

echo "Iniciando MQTT broker..."
mosquitto &

HOST="localhost"
PORT=1883

echo "Ejecutando creador de la simulacion..."
python3 set_simulation.py 

sleep 2

echo "Lanzando controlador..."
python3 ../scripts/controller.py --host $HOST --port $PORT &

sleep 2

echo "Lanzando Rule Engine..."
python3 ../scripts/rule_engine.py --host $HOST --port $PORT &

sleep 2

echo "Lanzando Dummy Sensores..."
python3 ../scripts/dummy_sensor.py --host $HOST --port $PORT --min 250 --max 275 --increment 1 --interval 5 1 &

sleep 2

echo "Lanzando Dummy Switches..."
python3 ../scripts/dummy_switch.py --host $HOST --port $PORT --probability 0.9 1 &

sleep 2

echo "Lanzando Dummy Relojes..."
python ../scripts/dummy_clock_simulator.py --host $HOST --port $PORT --time "12:00:00" --increment 3600 1 &


echo "Simulacion ejecutandose. Pulsa CTRL+C para salir"
trap "trap - SIGTERM && kill -- -$$" SIGINT SIGTERM EXIT

wait
