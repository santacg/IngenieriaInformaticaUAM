#!/bin/bash

echo "Ejecutando simulador..."

gnome-terminal --title="Controller" -- python3 controller.py
sleep 0.5 
gnome-terminal --title="Interruptor1" -- python3 dummy_switch.py Interruptor1
sleep 0.5
gnome-terminal --title="Rule Engine" -- python3 rule_engine.py
sleep 0.5
gnome-terminal --title="Sensor1" -- python3 dummy_sensor.py --min 1 --max 15 Sensor1
sleep 0.5 
gnome-terminal --title="Reloj1" -- python3 dummy_clock.py --time 08:00:00 Reloj1
sleep 0.5

echo "Simulador ejecutado correctamente."
