# Tratamiento de Señales Visuales/Tratamiento de Señales Multimedia I @ EPS-UAM
# Practica 2: Extraccion, descripcion y correspondencia de caracteristicas locales
# Tarea 1: Deteccion de puntos de interes con Harris corner detector.

# AUTOR1: GARCÍA SANTA, CARLOS
# AUTOR2: GONZÁLEZ GALLEGO, MIGUEL ÁNGEL
# PAREJA/TURNO: 02/NUMERO_TURNO

# librerias y paquetes por defecto
import numpy as np
from p2_tests import test_p2_tarea1
import scipy as sc
from scipy import signal as sg
from scipy import ndimage as ndi
# Incluya aqui las librerias que necesite en su codigo
# ...


def detectar_puntos_interes_harris(imagen: np.ndarray, sigma = 1.0, k = 0.05, threshold_rel = 0.2):
    """
    # Esta funcion detecta puntos de interes en una imagen con el algoritmo de Harris.
    #
    # Argumentos de entrada:
    # - imagen: numpy array con dimensiones [imagen_height, imagen_width].
    # - sigma: valor de tipo double o float que determina el factor de suavizado aplicado
    # - k: valor de tipo double o float que determina la respuesta R de Harris
    # - threshold_rel: valor de tipo double o float que define el umbral relativo aplicado sobre el valor maximo de R
    # Argumentos de salida
    # - coords_esquinas: numpy array con dimensiones [num_puntos_interes, 2] con las coordenadas
    # de los puntos de interes detectados en la imagen. Cada punto de interes
    # se encuentra en el formato [fila, columna] de tipo int64
    #
    # NOTA: no modificar los valores por defecto de las variables de entrada sigma y k,
    # pues se utilizan para verificar el correcto funciomaniento de esta funcion
    """
    coords_esquinas = np.empty(shape=[0,0]) # iniciamos la variable de salida (numpy array)

    # Convertimos la imagen a float
    imagen = imagen.astype(np.float64)

    # Normalizamos la imagen
    imagen /= np.sum(imagen)

    # Obtenemos las derivadas parciales con respecto de x e y
    imagen_h = ndi.sobel(imagen, 0)
    imagen_v = ndi.sobel(imagen, 1)
    imagen_hv = imagen_h * imagen_v

    imagen_h = imagen_h * imagen_h
    imagen_v = imagen_v * imagen_v

    # Suavizamos aplicando una convolucion Gaussiana
    imagen_h = ndi.gaussian_filter(imagen_h, sigma, mode='constant')
    imagen_v = ndi.gaussian_filter(imagen_v, sigma, mode='constant')
    imagen_hv = ndi.gaussian_filter(imagen_hv, sigma, mode='constant')

    # Calculamos la matrix Hessiana
    hessiana = np.array([imagen_h, imagen_hv], [imagen_hv, imagen_v])

    # Calculamos los autovalores de la matriz Hessiana
    eig_values = np.linalg.eig(hessiana)

    # Calculamos el coeficiente R
    r_coeficiente = eig_values[0] * eig_values[1] - k * pow(eig_values[0] + eig_values[1])

    return coords_esquinas

if __name__ == "__main__":    
    print("Practica 2 - Tarea 1 - Test autoevaluación\n")                
    
    print("Tests completados = " + str(test_p2_tarea1(disptime=-1,stop_at_error=False,debug=False))) #analizar todos los casos sin pararse en errores
    #print("Tests completados = " + str(test_p2_tarea1(disptime=1,stop_at_error=False,debug=False))) #analizar y visualizar todos los casos sin pararse en errores
    #print("Tests completados = " + str(test_p2_tarea1(disptime=-1,stop_at_error=True,debug=False))) #analizar todos los casos y pararse en errores 
    #print("Tests completados = " + str(test_p2_tarea1(disptime=-1,stop_at_error=True,debug=True))) #analizar todos los casos, pararse en errores y mostrar informacion