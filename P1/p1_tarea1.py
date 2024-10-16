# Tratamiento de Señales Visuales/Tratamiento de Señales Multimedia I @ EPS-UAM
# Practica 1: Fusion de imagenes mediante piramides
# Tarea 1: metodos reduce y expand

# AUTOR1: García Santa Carlos
# AUTOR2: González Gallego Miguel Ángel
# PAREJA/TURNO: 02/TARDE
import numpy as np
import math
import scipy.signal

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
    rows = imagen.shape[0]
    cols = imagen.shape[1]

    output_rows = math.ceil(rows / 2)
    output_cols = math.ceil(cols / 2)

    output = np.empty(shape=(output_rows, output_cols))

    gaussian_kernel = generar_kernel_suavizado(0.4)
    imagen_suavizada = scipy.signal.convolve2d(
        imagen, gaussian_kernel, 'same')

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
    rows = imagen.shape[0]
    cols = imagen.shape[1]

    output_rows = rows * 2
    output_cols = cols * 2

    output = np.zeros(shape=(output_rows, output_cols))

    output[::2, ::2] = imagen

    gaussian_kernel = generar_kernel_suavizado(0.4)

    output = scipy.signal.convolve2d(output, gaussian_kernel, 'same')
    output *= 4

    return output


if __name__ == "__main__":
    print("Practica 1 - Tarea 1 - Test autoevaluación\n")
    print("Tests completados = " + str(test_p1_tarea1()))
