# Tratamiento de Señales Visuales/Tratamiento de Señales Multimedia I @ EPS-UAM
# Practica 2: Extraccion, descripcion y correspondencia de caracteristicas locales
# Memoria: codigo de la pregunta 04

# AUTOR1: GARCÍA SANTA, CARLOS
# AUTOR2: GONZÁLEZ GALLEGO, MIGUEL ÁNGEL
# PAREJA/TURNO: 02/NUMERO_TURNO

# librerias y paquetes por defecto
import numpy as np
from p2_tests import test_p2_tarea3

def correspondencias_puntos_interes(descriptores_imagen1, descriptores_imagen2, tipoCorr='mindist',max_distancia=25):
    """
    # Esta funcion determina la correspondencias entre dos conjuntos de descriptores mediante
    # el calculo de la similitud entre los descriptores.
    #
    # El parametro 'tipoCorr' determina el criterio de similitud aplicado 
    # para establecer correspondencias entre pares de descriptores:
    #   - Criterio 'mindist': minima distancia euclidea entre descriptores 
    #                         menor que el umbral 'max_distancia'
    #  
    # Argumentos de entrada:
    #   - descriptores1: numpy array con dimensiones [numero_descriptores, longitud_descriptor] 
    #                    con los descriptores de los puntos de interes de la imagen 1.        
    #   - descriptores2: numpy array con dimensiones [numero_descriptores, longitud_descriptor] 
    #                    con los descriptores de los puntos de interes de la imagen 2.        
    #   - tipoCorr: cadena de caracteres que indica el tipo de criterio para establecer correspondencias
    #   - max_distancia: valor de tipo double o float utilizado por el criterio 'mindist' y 'nndr', 
    #                    que determina si se aceptan correspondencias entre descriptores 
    #                    con distancia minima menor que 'max_distancia' 
    #
    # Argumentos de salida
    #   - correspondencias: numpy array con dimensiones [numero_correspondencias, 2] de tipo int64 
    #                       que determina correspondencias entre descriptores de imagen 1 e imagen 2.
    #                       Por ejemplo: 
    #                       correspondencias[0,:]=[5,22] significa que el descriptor 5 de la imagen 1 
    #                                                  corresponde con el descriptor 22 de la imagen 2. 
    #                       correspondencias[1,:]=[6,23] significa que el descriptor 6 de la imagen 1 
    #                                                  corresponde con el descriptor 23 de la imagen 2.
    #
    # NOTA: no modificar los valores por defecto de las variables de entrada tipoCorr y max_distancia, 
    #       pues se utilizan para verificar el correcto funciomaniento de esta funcion
    #
    # CONSIDERACIONES: 
    # 1) La funcion no debe permitir correspondencias de uno a varios descriptores. Es decir, 
    #   un descriptor de la imagen 1 no puede asignarse a multiples descriptores de la imagen 2 
    # 2) En el caso de que existan varios descriptores de la imagen 2 con la misma distancia minima 
    #    con algún descriptor de la imagen 1, seleccione el descriptor de la imagen 2 con 
    #    indice/posicion menor. Por ejemplo, si las correspondencias [5,22] y [5,23] tienen la misma
    #    distancia minima, seleccione [5,22] al ser el indice 22 menor que 23
    """    
    correspondencias = []
    # Conjunto para los indices de la imagen 2 que ya han sido asignados
    asignados_imagen2 = set()
    
    if tipoCorr == 'mindist':
        for idx1, desc1 in enumerate(descriptores_imagen1):
            # Obtenemos los indices de los descriptores de la imagen 2 que no han sido asignados
            indices_disp = [idx for idx in range(len(descriptores_imagen2)) if idx not in asignados_imagen2]
            
            if not indices_disp:
                # Si no hay descriptores disponibles en la imagen 2 salimos
                break
            
            # Seleccionamos los descriptores disponibles de la imagen 2
            desc2_disp = descriptores_imagen2[indices_disp]
            
            # Calculamos la distancia euclidea entre el descriptor de la imagen 1 y los disponibles de la imagen 2
            distancias = np.linalg.norm(desc2_disp - desc1, axis=1)
            
            # Encontramos el índice del descriptor de la imagen 2 con la distancia mínima
            idx_min = np.argmin(distancias)
            distancia_min = distancias[idx_min]
            idx2 = indices_disp[idx_min]
            
            # Verificamos si la distancia mínima es menor que el umbral máximo
            if distancia_min < max_distancia:
                correspondencias.append([idx1, idx2])
                # Marcamos el descriptor de la imagen 2 como asignado
                asignados_imagen2.add(idx2)
    
    elif tipoCorr == 'nndr':
        umbral_nndr = 0.75  # Umbral para el criterio NNDR
        for idx1, desc1 in enumerate(descriptores_imagen1):
            # Obtenemos los indices de los descriptores de la imagen 2 que no han sido asignados
            indices_disp = [idx for idx in range(len(descriptores_imagen2)) if idx not in asignados_imagen2]
            
            if len(indices_disp) < 2:
                # Si no hay suficientes descriptores disponibles en la imagen 2 continuamos
                continue
            
            # Seleccionamos los descriptores disponibles de la imagen 2
            desc2_disp = descriptores_imagen2[indices_disp]
            
            # Calculamos la distancia euclidea entre el descriptor de la imagen 1 y los disponibles de la imagen 2
            distancias = np.linalg.norm(desc2_disp - desc1, axis=1)
            
            # Ordenamos las distancias y obtenemos los índices ordenados
            indices_ordenados = np.argsort(distancias)
            distancias_ordenadas = distancias[indices_ordenados]
            
            # Obtenemos las dos distancias más pequeñas
            d1 = distancias_ordenadas[0]
            d2 = distancias_ordenadas[1]
            
            # Calculamos el ratio NNDR
            nndr = d1 / d2 if d2 != 0 else np.inf
            
            # Obtenemos el indice del vecino más cercano en la imagen 2
            idx2 = indices_disp[indices_ordenados[0]]
            
            # Verificamos si el NNDR es menor que el umbral y si la distancia mínima es menor que max_distancia
            if nndr < umbral_nndr and d1 < max_distancia:
                correspondencias.append([idx1, idx2])
                # Marcamos el descriptor de la imagen 2 como asignado
                asignados_imagen2.add(idx2)
    
    correspondencias = np.array(correspondencias, dtype=np.int64)
    return correspondencias

if __name__ == "__main__":
    print("Tests completados = " + str(test_p2_tarea3(disptime=-1,stop_at_error=False,debug=False,tipoDesc='hist',tipoCorr='nndr'))) #analizar todas las imágenes con descriptor 'hist' y criterio 'nndr'
    print("Tests completados = " + str(test_p2_tarea3(disptime=1,stop_at_error=False,debug=False,tipoDesc='mag-ori',tipoCorr='nndr'))) #analizar todas las imágenes con descriptor 'mag-ori' y criterio 'nndr'

