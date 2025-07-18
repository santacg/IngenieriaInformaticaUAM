from clases.robot import Robot
import sys

def main():
    robot = Robot()

    print("Iniciando robot...")
    
    try:
        robot.iniciar_robot()
    except KeyboardInterrupt:
        print("Robot interrumpido terminando ejecucion...")
        robot.close()


if __name__ == '__main__':
    main()
