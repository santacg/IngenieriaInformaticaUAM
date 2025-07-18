from clases.repartidor import Repartidor 
import sys

def main():
    repartidor = Repartidor()

    print("Iniciando repartidor...")
    
    try:
        repartidor.iniciar_repartidor()
    except KeyboardInterrupt:
        print("Repartidor interrumpido terminando ejecucion...")
        repartidor.close()


if __name__ == '__main__':
    main()
