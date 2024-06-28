#!/bin/bash

echo "Lanzando controller..."
python3 launch_controlador.py &

sleep 2

echo "Lanzando robots..."
for i in {1..5}; do
    python3 launch_robot.py &
done

sleep 2

echo "Lanzando repartidores..."
for i in {1..3}; do
    python3 launch_repartidor.py &
done

sleep 2

echo "Lanzando clientes..."
for i in {1..3}; do
    python3 launch_cliente.py &
    sleep 5
done

wait
