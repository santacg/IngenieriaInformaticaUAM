import socket 

# Crear un objeto 

socket s = socket.socket(socket.AF_INET, socket.SOCK_STREAM) 

# Conectar el socket a una dirección específica

s.connect(('localhost', 12345))
