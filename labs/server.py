import socket 

# Crear un objeto 

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM) 

s.bind(('localhost', 12345))

s.listen()
# Conectar el socket a una dirección específica

conn, addr = s.accept()
