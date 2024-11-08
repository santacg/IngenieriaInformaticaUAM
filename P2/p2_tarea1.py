# Tratamiento de Señales Visuales/Tratamiento de Señales Multimedia I @ EPS-UAM
# Practica 2: Extraccion, descripcion y correspondencia de caracteristicas locales
# Tarea 1: Deteccion de puntos de interes con Harris corner detector.

# AUTOR1: GARCÍA SANTA, CARLOS
# AUTOR2: GONZÁLEZ GALLEGO, MIGUEL ÁNGEL
# PAREJA/TURNO: 02/NUMERO_TURNO

# librerias y paquetes por defecto
import numpy as np
from p2_tests import test_p2_tarea1

# Incluya aqui las librerias que necesite en su codigo
from scipy import ndimage as ndi
from skimage.feature import corner_peaks 

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

    # Normalizamos la imagen
    imagen = imagen.astype(np.float64) / 255.0

    # Obtenemos las derivadas parciales con respecto de x e y
    imagen_h = ndi.sobel(imagen, 0, mode="constant")
    imagen_v = ndi.sobel(imagen, 1, mode="constant")

    # Calculamos los elementos de la matriz Hessiana
    imagen_hv = imagen_h * imagen_v
    imagen_h = imagen_h ** 2
    imagen_v = imagen_v ** 2

    # Suavizamos aplicando una convolucion Gaussiana
    imagen_h = ndi.gaussian_filter(imagen_h, sigma, mode='constant')
    imagen_v = ndi.gaussian_filter(imagen_v, sigma, mode='constant')
    imagen_hv = ndi.gaussian_filter(imagen_hv, sigma, mode='constant')

    # Calculamos el coeficiente R con la traza de la Hessiana y su determinante
    hessiana_det = imagen_h * imagen_v - (imagen_hv ** 2)
    traza = imagen_h + imagen_v

    coeficiente_r = hessiana_det - k * (traza **2)

    # Usamos corner peaks para obtener las coordenadas de los valores mayores que el threshold
    coords_esquinas = corner_peaks(coeficiente_r, min_distance=5, threshold_rel=threshold_rel)

    return coords_esquinas

if __name__ == "__main__":    
    print("Practica 2 - Tarea 1 - Test autoevaluación\n")                
    
    print("Tests completados = " + str(test_p2_tarea1(disptime=-1,stop_at_error=False,debug=False))) #analizar todos los casos sin pararse en errores
    #print("Tests completados = " + str(test_p2_tarea1(disptime=1,stop_at_error=False,debug=False))) #analizar y visualizar todos los casos sin pararse en errores
    #print("Tests completados = " + str(test_p2_tarea1(disptime=-1,stop_at_error=True,debug=False))) #analizar todos los casos y pararse en errores 
    #print("Tests completados = " + str(test_p2_tarea1(disptime=-1,stop_at_error=True,debug=True))) #analizar todos los casos, pararse en errores y mostrar informacion
