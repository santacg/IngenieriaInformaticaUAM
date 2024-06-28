import argparse
from clases.cliente import Cliente  

def main():
    cliente = Cliente()

    parser = argparse.ArgumentParser(description="Cliente de línea de comandos para gestionar pedidos")
    parser.add_argument("--registrar", metavar="NOMBRE_USUARIO", help="Registra un nuevo usuario")
    parser.add_argument("--añadir-pedido", metavar="PRODUCTOS_IDS", help="Añade un pedido con los IDs de productos separados por comas")
    parser.add_argument("--ver-pedidos", action="store_true", help="Muestra todos los pedidos")
    parser.add_argument("--cancelar-pedido", metavar="PEDIDO_ID", help="Cancela el pedido con el ID especificado")
    
    args = parser.parse_args()

    if args.registrar:
        cliente.registrar(args.registrar)
    elif args.añadir_pedido:
        cliente.realizar_pedido(args.añadir_pedido)
    elif args.ver_pedidos:
        cliente.ver_pedidos()
    elif args.cancelar_pedido:
        cliente.cancelar_pedido(args.cancelar_pedido)
    else:
        parser.print_help()

    cliente.close()

if __name__ == '__main__':
    main()