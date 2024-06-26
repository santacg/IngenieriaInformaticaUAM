from clases.cliente import Cliente 
import random
import time

def main():

    print("Iniciando cliente...")
    cliente = Cliente()

    usuario_random = random.randint(1, 10000)
    cliente.registrar(usuario_random)
    
    for _ in range(0, random.randint(1, 8)):
        producto_random = random.randint(1, 100000)
        cliente.realizar_pedido(producto_random)
        time.sleep(random.randint(1, 5))

    cliente.ver_pedidos()
    
    print("Cerrando cliente...")
    cliente.close()


if __name__ == '__main__':
    main()
