from clases.cliente import Cliente
import sys

def main():
    print("Iniciando cliente...")
    cliente = Cliente()

    while True:
        print("\nAcciones disponibles:")
        print("1. Registrarse")
        print("2. Añadir pedido")
        print("3. Salir")
        choice = input("Seleccione una opción: ")

        if choice == '1':
            nombre_usuario = input("Ingrese el numbre de usuario a introducir: ")
            respuesta = cliente.registrar(nombre_usuario)
            if respuesta == False:
                print("El nombre de usuario ya existe")
            else:
                print("Registrado correctamente")

        elif choice == '2':
            productos_ids = input("Introduzca los ids de los productos a añadir en el pedido (separador por comas): ")
            respuesta = cliente.realizar_pedido(productos_ids)
            if respuesta == False:
                print("Ha habido un error en el pedido")
            else:
                print("Pedido realizado correctamente")

        elif choice == '3':
            break
        else:
            print("Opción no válida, intente de nuevo.")

if __name__ == '__main__':
    main()

