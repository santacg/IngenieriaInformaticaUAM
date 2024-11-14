# Tratamiento de Señales Visuales/Tratamiento de Señales Multimedia I @ EPS-UAM
# Practica 2: Extraccion, descripcion y correspondencia de caracteristicas locales
# Tarea 3:  Similitud y correspondencia de puntos de interes

# AUTOR1: GARCÍA SANTA, CARLOS
# AUTOR2: GONZÁLEZ GALLEGO, MIGUEL ÁNGEL
# PAREJA/TURNO: 02/NUMERO_TURNO

# librerias y paquetes por defecto
import numpy as np
from p2_tests import test_p2_tarea3

# Incluya aqui las librerias que necesite en su codigo
# ...

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
    # Indices de la imagen 2 que ya han sido asignados
    asignados_imagen2 = set()
    
    for idx1, desc1 in enumerate(descriptores_imagen1):
        # Obtenemos los índices de los descriptores de la imagen 2 no han sido asignadoss
        indices_disp = [idx for idx in range(len(descriptores_imagen2)) if idx not in asignados_imagen2]
        
        if not indices_disp:
            # Si no hay descriptores disponibles en la imagen 2 salimos
            break
        
        # Seleccionamos los descriptores disponibles de la imagen 2
        desc2_disp = descriptores_imagen2[indices_disp]
        
        # Calculamos la distancia euclidea entre el descriptor de la imagen 1 y los disponibles de la imagen 2
        distancias = np.linalg.norm(desc2_disp - desc1, axis=1)
        
        # Encontramos el indice del descriptor de la imagen 2 con la distancia mínima
        idx_min = np.argmin(distancias)
        distancia_min = distancias[idx_min]
        idx2 = indices_disp[idx_min]
        
        # Verificamos si la distancia mínima es menor que el umbral máximo
        if distancia_min < max_distancia:
            correspondencias.append([idx1, idx2])
            # Marcamos el descriptor de la imagen 2 como asignados
            asignados_imagen2.add(idx2)
    
    correspondencias = np.array(correspondencias, dtype=np.int64)
    return correspondencias

if __name__ == "__main__":
    print("Practica 2 - Tarea 3 - Test autoevaluación\n")                

    ## tests correspondencias tipo 'minDist' (tarea 3a)
    #print("Tests completados = " + str(test_p2_tarea3(disptime=-1,stop_at_error=True,debug=True,tipoDesc='hist',tipoCorr='mindist'))) #analizar todas las imagenes con descriptor 'hist' y ver errores
    print("Tests completados = " + str(test_p2_tarea3(disptime=-1,stop_at_error=False,debug=False,tipoDesc='hist',tipoCorr='mindist'))) #analizar todas las imagenes con descriptor 'hist'
    print("Tests completados = " + str(test_p2_tarea3(disptime=1,stop_at_error=False,debug=False,tipoDesc='mag-ori',tipoCorr='mindist'))) #analizar todas las imagenes con descriptor 'mag-ori'
