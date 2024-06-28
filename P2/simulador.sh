#!/bin/bash

# Array to hold process IDs
declare -a pids

echo "Launching controller..."
python3 launch_controlador.py &
pids+=($!)  # Store PID

sleep 2

echo "Launching robots..."
for i in {1..5}; do
    python3 launch_robot.py &
done

sleep 2

echo "Launching deliverers..."
for i in {1..3}; do
    python3 launch_repartidor.py &
done

sleep 2

echo "Launching clients..."
for i in {1..3}; do
    python3 launch_cliente.py &
    sleep 5
done

wait
