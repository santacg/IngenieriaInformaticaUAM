from clases.cliente import Cliente 
import sys

def main():

    print("Iniciando cliente...")
    cliente = Cliente()

    cliente.registrar("usuario1")

    cliente.realizar_pedido("1,2,3")
    cliente.realizar_pedido("4,5,6")

    cliente.ver_pedidos()
    
    print("Cerrando cliente...")
    cliente.close()


if __name__ == '__main__':
    main()