# Tratamiento de Señales Visuales/Tratamiento de Señales Multimedia I @ EPS-UAM
# Practica 2: Extraccion, descripcion y correspondencia de caracteristicas locales
# Tarea 2: Descripcion de puntos de interes mediante histogramas.

# AUTOR1: GARCÍA SANTA, CARLOS
# AUTOR2: GONZÁLEZ GALLEGO, MIGUEL ÁNGEL
# PAREJA/TURNO: 02/NUMERO_TURNO

# librerias y paquetes por defecto
import numpy as np
from p2_tests import test_p2_tarea2

# Incluya aqui las librerias que necesite en su codigo
import scipy.ndimage as ndi
from matplotlib import pyplot as plt

def descripcion_puntos_interes(imagen, coords_esquinas, vtam = 8, nbins = 16, tipoDesc='hist'):
    """
    # Esta funcion describe puntos de interes de una imagen mediante histogramas, analizando 
    # vecindarios con dimensiones "vtam+1"x"vtam+1" centrados en las coordenadas de cada punto de interes
    #   
    # La descripcion obtenida depende del parametro 'tipoDesc'
    #   - Caso 'hist': histograma normalizado de valores de gris 
    #   - Caso 'mag-ori': histograma de orientaciones de gradiente
    #
    # En el caso de que existan puntos de interes en los bordes de la imagen, el descriptor no
    # se calcula y el punto de interes se elimina de la lista <new_coords_esquinas> que devuelve
    # esta funcion. Esta lista indica los puntos de interes para los cuales existe descriptor.
    #
    # Argumentos de entrada:
    #   - imagen: numpy array con dimensiones [imagen_height, imagen_width].        
    #   - coords_esquinas: numpy array con dimensiones [num_puntos_interes, 2] con las coordenadas 
    #                      de los puntos de interes detectados en la imagen. Tipo int64
    #                      Cada punto de interes se encuentra en el formato [fila, columna]
    #   - vtam: valor de tipo entero que indica el tamaño del vecindario a considerar para
    #           calcular el descriptor correspondiente.
    #   - nbins: valor de tipo entero que indica el numero de niveles que tiene el histograma 
    #           para calcular el descriptor correspondiente.
    #   - tipoDesc: cadena de caracteres que indica el tipo de descriptor calculado
    #
    # Argumentos de salida
    #   - descriptores: numpy array con dimensiones [num_puntos_interes, nbins] con los descriptores 
    #                   de cada punto de interes (i.e. histograma de niveles de gris)
    #   - new_coords_esquinas: numpy array con dimensiones [num_puntos_interes, 2], solamente con las coordenadas 
    #                      de los puntos de interes descritos. Tipo int64  <class 'numpy.ndarray'>
    #
    # NOTA: no modificar los valores por defecto de las variables de entrada vtam y nbins, 
    #       pues se utilizan para verificar el correcto funciomaniento de esta funcion
    """


    # Convertimos la imagen a float y normalizamos al rango [0,1]
    imagen = imagen.astype(np.float64) # / 255.0

    borde_y, borde_x = imagen.shape

    # Filtrar puntos de interés que están cerca de los bordes
    half_vtam = vtam // 2
    new_coords_esquinas = np.array([
        coord for coord in coords_esquinas
        if (coord[1] >= half_vtam and coord[1] < borde_x - half_vtam and
            coord[0] >= half_vtam and coord[0] < borde_y - half_vtam)
    ])

    descriptores = []

    if tipoDesc == 'mag-ori':
        # Calcula los gradientes de la imagen en los ejes x e y
        gradiente_v = ndi.sobel(imagen, axis=0, mode="constant")  # Derivada respecto a y
        gradiente_h = ndi.sobel(imagen, axis=1, mode="constant")  # Derivada respecto a x

        # Calcula la magnitud y orientación del gradiente en cada píxel
        magnitud_gradiente = np.sqrt(gradiente_h**2 + gradiente_v**2)
        orientacion_gradiente = np.rad2deg(np.arctan2(gradiente_v, gradiente_h)) % 360

        # Definimos los límites de los bins para las orientaciones en el histograma
        bins = np.linspace(0, 360, nbins + 1)

    # Iterar sobre cada punto de interes
    for coord in new_coords_esquinas:
        y, x = coord[0], coord[1]

        # Extraemos el vecindario en torno al punto de interes según el vtam dado 
        vecindario = imagen[y - half_vtam: y + half_vtam + 1, x - half_vtam: x + half_vtam + 1]

        if tipoDesc == 'hist':
            # Calcula el histograma de intensidad de los valores de gris en el vecindario
            histograma, _ = np.histogram(vecindario.flatten(), bins=nbins, range=(0, 1), density=True)
            descriptores.append(histograma / np.sum(histograma))  # Normalizar el histograma

        elif tipoDesc == 'mag-ori':
            vec_magnitud = magnitud_gradiente[y - half_vtam: y + half_vtam + 1, x - half_vtam: x + half_vtam + 1]
            vec_orientacion = orientacion_gradiente[y - half_vtam: y + half_vtam + 1, x - half_vtam: x + half_vtam + 1]

            # Inicializamos histograma para acumular magnitudes según las orientaciones
            histograma_grad = np.zeros(nbins)
            bin_indices = np.digitize(vec_orientacion.flatten(), bins) - 1
            for idx, magnitud in zip(bin_indices, vec_magnitud.flatten()):
                if 0 <= idx < nbins:
                    histograma_grad[idx] += magnitud 
            descriptores.append(histograma_grad)

    descriptores = np.array(descriptores)
    return descriptores, new_coords_esquinas


if __name__ == "__main__":    
    print("Practica 2 - Tarea 2 - Test autoevaluación\n")                

    ## tests descriptor tipo 'hist' (tarea 2a)
    #print("Tests completados = " + str(test_p2_tarea2(disptime=-1,stop_at_error=False,debug=False,tipoDesc='hist'))) #analizar todas las imagenes y esquinas del test
    print("Tests completados = " + str(test_p2_tarea2(disptime=1,stop_at_error=False,debug=False,tipoDesc='hist'))) #analizar todas las imagenes y esquinas del test, mostrar imagenes con resultados (1 segundo)
    #print("Tests completados = " + str(test_p2_tarea2(disptime=-1,stop_at_error=True,debug=True,tipoDesc='hist'))) #analizar todas las imagenes y esquinas del test, pararse en errores y mostrar datos
    #print("Tests completados = " + str(test_p2_tarea2(disptime=-1,stop_at_error=True,debug=True,tipoDesc='hist',imgIdx = 3, poiIdx = 7))) #analizar solamente imagen #2 y esquina #7    

    ## tests descriptor tipo 'mag-ori' (tarea 2b)
    #print("Tests completados = " + str(test_p2_tarea2(disptime=-1,stop_at_error=False,debug=False,tipoDesc='mag-ori'))) #analizar todas las imagenes y esquinas del test
    print("Tests completados = " + str(test_p2_tarea2(disptime=0.1,stop_at_error=False,debug=False,tipoDesc='mag-ori'))) #analizar todas las imagenes y esquinas del test, mostrar imagenes con resultados (1 segundo)
    #print("Tests completados = " + str(test_p2_tarea2(disptime=-1,stop_at_error=True,debug=True,tipoDesc='mag-ori'))) #analizar todas las imagenes y esquinas del test, pararse en errores y mostrar datos
    #print("Tests completados = " + str(test_p2_tarea2(disptime=1,stop_at_error=True,debug=True,tipoDesc='mag-ori',imgIdx = 3,poiIdx = 7))) #analizar solamente imagen #1 y esquina #7           
   
