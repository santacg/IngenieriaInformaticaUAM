from clases.controlador import Controlador
import sys

def main():

    print("Iniciando el controlador...")
    print("Pulsa control + c para detener el controlador y terminar su ejecución")

    controlador = Controlador()
    try:
        controlador.iniciar_controlador()
    except KeyboardInterrupt:
        print("Controlador interrumpido terminando ejecucion...")
        controlador.close()

if __name__ == '__main__':
    main()

