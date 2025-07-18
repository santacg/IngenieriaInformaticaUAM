# Tratamiento de Señales Visuales/Tratamiento de Señales Multimedia I @ EPS-UAM
# Practica 1: Fusion de imagenes mediante piramides
# Tarea 1: metodos reduce y expand

# AUTOR1: García Santa Carlos
# AUTOR2: González Gallego Miguel Ángel
# PAREJA/TURNO: 02/TARDE
import numpy as np
import math
import scipy.signal
from p1_tests import test_p1_tarea1


from p1_utils import generar_kernel_suavizado


def reduce(imagen):
    """  
    # Esta funcion implementa la operacion "reduce" sobre una imagen
    # 
    # Argumentos de entrada:
    #    imagen: numpy array de tamaño [imagen_height, imagen_width].
    # 
    # Devuelve:
    #    output: numpy array de tamaño [imagen_height/2, imagen_width/2] (output).
    #
    # NOTA: si imagen_height/2 o imagen_width/2 no son numeros enteros, 
    #        entonces se redondea al entero mas cercano por arriba 
    #        Por ejemplo, si la imagen es 5x7, la salida sera 3x4  
    """
    # Tomamos las dimensiones de la imagen a reducir
    rows = imagen.shape[0]
    cols = imagen.shape[1]

    # Reducimos a la mitad las filas y las columnas
    output_rows = math.ceil(rows / 2)
    output_cols = math.ceil(cols / 2)

    # Inicializamos el output
    output = np.empty(shape=(output_rows, output_cols))

    # Generamos un kernel gaussiano de 0.4
    gaussian_kernel = generar_kernel_suavizado(0.4)
    imagen_suavizada = scipy.signal.convolve2d(
        imagen, gaussian_kernel, 'same')

    # Se reduce la imagen en 1 de cada 2 píxeles en ancho y largo
    output = imagen_suavizada[::2, ::2]

    return output


def expand(imagen):
    """  
    # Esta funcion implementa la operacion "expand" sobre una imagen
    # 
    # Argumentos de entrada:
    #    imagen: numpy array de tamaño [imagen_height, imagen_width].
    #     
    # Devuelve:
    #    output: numpy array de tamaño [imagen_height*2, imagen_width*2].
    """
    
    # Tomamos las dimensiones de la imagen a expandir
    rows = imagen.shape[0]
    cols = imagen.shape[1]

    # Multiplicamos por 2 el valor de filas y columnas expandiendo
    output_rows = rows * 2
    output_cols = cols * 2

    # Inicializamos a 0 todos los valores del output 
    output = np.zeros(shape=(output_rows, output_cols))

    # Tomamos 1 de cada 2 pixeles en ancho y largo del array con el doble de filas y columnas
    output[::2, ::2] = imagen

    # Generamos un kernel gaussiano con suavizado de 0.4
    gaussian_kernel = generar_kernel_suavizado(0.4)

    # Convolucionamos l aimagen expandida con gaussian_kernel
    output = scipy.signal.convolve2d(output, gaussian_kernel, 'same')
    output *= 4

    return output


if __name__ == "__main__":
    print("Practica 1 - Tarea 1 - Test autoevaluación\n")
    print("Tests completados = " + str(test_p1_tarea1()))
