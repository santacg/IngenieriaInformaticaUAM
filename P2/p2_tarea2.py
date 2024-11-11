# Tratamiento de Señales Visuales/Tratamiento de Señales Multimedia I @ EPS-UAM
# Practica 2: Extraccion, descripcion y correspondencia de caracteristicas locales
# Tarea 2: Descripcion de puntos de interes mediante histogramas.

# AUTOR1: APELLIDO1 APELLIDO1, NOMBRE1
# AUTOR2: APELLIDO2 APELLIDO2, NOMBRE2
# PAREJA/TURNO: NUMERO_PAREJA/NUMERO_TURNO

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

# Normalizar la imagen
    imagen = imagen.astype(np.float64) #/ np.max(imagen)  # Normalización a [0, 1]

    # Tamaño de la imagen
    borde_x, borde_y = imagen.shape[1], imagen.shape[0]

    # Filtrar puntos de interés que están en los bordes
    coordenadas_validas = []
    
    for coord in coords_esquinas:
        x, y = coord[1], coord[0]  # [columna, fila]
        if x >= vtam / 2 and x < borde_x - vtam / 2 and y >= vtam / 2 and y < borde_y - vtam / 2:
            coordenadas_validas.append(coord)

    # Lista de coordenadas válidas
    new_coords_esquinas = np.array(coordenadas_validas)

    # Inicializar descriptores
    descriptores = []

    # Para cada punto de interés
    for coord in new_coords_esquinas:
        x, y = coord[1], coord[0]  # [columna, fila]
        
        # Extraer el vecindario de tamaño (vtam+1)x(vtam+1)
        vecindario = imagen[y - vtam // 2 : y + vtam // 2 + 1, x - vtam // 2 : x + vtam // 2 + 1]
        
        if tipoDesc == 'hist':
            # Para el tipo 'hist', calcular un histograma de los valores de gris
            histograma, _ = np.histogram(vecindario.flatten(), bins=nbins, range=(0, 1), density=True)
            
            # Normalizar el histograma
            histograma = histograma / np.sum(histograma)
            
            # Añadir el descriptor
            descriptores.append(histograma)
            
        elif tipoDesc == 'mag-ori':
            
            gradiente_h = ndi.sobel(vecindario, axis=0, mode="constant")
            gradiente_v = ndi.sobel(vecindario, axis=1, mode="constant")

            # Cálculo de magnitud de gradiente
            magnitud_gradiente = np.sqrt(gradiente_h**2 + gradiente_v**2)

            # Cálculo de la orientacion del gradiente
            orientacion_gradiente = np.arctan2(gradiente_v, gradiente_h)

            # Convertimos el gradiente de radianes a grados
            orientacion_gradiente = np.rad2deg(orientacion_gradiente)

            # Multiplicamos de forma escalar las magnitudes y las orientaciones del gradiente
            histograma_grad = magnitud_gradiente*orientacion_gradiente

            print("Magnitud gradiente", magnitud_gradiente)
            print("Orientacion: ", orientacion_gradiente)

            print("Histograma mult: ", histograma_grad)
            

           
    
    # Convertir descriptores a numpy array
    descriptores = np.array(descriptores)

    return descriptores, new_coords_esquinas

if __name__ == "__main__":    
    print("Practica 2 - Tarea 2 - Test autoevaluación\n")                

    ## tests descriptor tipo 'hist' (tarea 2a)
    #print("Tests completados = " + str(test_p2_tarea2(disptime=-1,stop_at_error=False,debug=False,tipoDesc='hist'))) #analizar todas las imagenes y esquinas del test
    #print("Tests completados = " + str(test_p2_tarea2(disptime=1,stop_at_error=False,debug=False,tipoDesc='hist'))) #analizar todas las imagenes y esquinas del test, mostrar imagenes con resultados (1 segundo)
    #print("Tests completados = " + str(test_p2_tarea2(disptime=-1,stop_at_error=True,debug=True,tipoDesc='hist'))) #analizar todas las imagenes y esquinas del test, pararse en errores y mostrar datos
    #print("Tests completados = " + str(test_p2_tarea2(disptime=-1,stop_at_error=True,debug=True,tipoDesc='hist',imgIdx = 3, poiIdx = 7))) #analizar solamente imagen #2 y esquina #7    

    ## tests descriptor tipo 'mag-ori' (tarea 2b)
    #print("Tests completados = " + str(test_p2_tarea2(disptime=-1,stop_at_error=False,debug=False,tipoDesc='mag-ori'))) #analizar todas las imagenes y esquinas del test
    #print("Tests completados = " + str(test_p2_tarea2(disptime=0.1,stop_at_error=False,debug=False,tipoDesc='mag-ori'))) #analizar todas las imagenes y esquinas del test, mostrar imagenes con resultados (1 segundo)
    print("Tests completados = " + str(test_p2_tarea2(disptime=-1,stop_at_error=True,debug=True,tipoDesc='mag-ori'))) #analizar todas las imagenes y esquinas del test, pararse en errores y mostrar datos
    #print("Tests completados = " + str(test_p2_tarea2(disptime=1,stop_at_error=True,debug=True,tipoDesc='mag-ori',imgIdx = 3,poiIdx = 7))) #analizar solamente imagen #1 y esquina #7           
   

    ## tests descriptor tipo 'mag-ori' (tarea 2b)
    #print("Tests completados = " + str(test_p2_tarea2(disptime=-1,stop_at_error=False,debug=False,tipoDesc='mag-ori'))) #analizar todas las imagenes y esquinas del test
    #print("Tests completados = " + str(test_p2_tarea2(disptime=0.1,stop_at_error=False,debug=False,tipoDesc='mag-ori'))) #analizar todas las imagenes y esquinas del test, mostrar imagenes con resultados (1 segundo)
    #print("Tests completados = " + str(test_p2_tarea2(disptime=-1,stop_at_error=True,debug=True,tipoDesc='mag-ori'))) #analizar todas las imagenes y esquinas del test, pararse en errores y mostrar datos
    #print("Tests completados = " + str(test_p2_tarea2(disptime=1,stop_at_error=True,debug=True,tipoDesc='mag-ori',imgIdx = 3,poiIdx = 7))) #analizar solamente imagen #1 y esquina #7           
