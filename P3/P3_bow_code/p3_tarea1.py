# Tratamiento de Señales Visuales/Tratamiento de Señales Multimedia I @ EPS-UAM
# Practica 3: Reconocimiento de escenas con modelos BOW/BOF
# Tarea 1: modelo BOW/BOF

# AUTOR1: GARCÍA SANTA, CARLOS
# AUTOR2: GONZÁLEZ GALLEGO, MIGUEL ANGEL
# PAREJA/TURNO: 02/NUMERO_TURNO

# librerias y paquetes por defecto
from p3_tests import test_p3_tarea1
from sklearn.cluster import KMeans
from scipy.spatial.distance import cdist
import numpy as np

def construir_vocabulario(list_img_desc, vocab_size=5, max_iter=300):
    """   
    # Esta funcion utiliza K-Means para agrupar los descriptores en "vocab_size" clusters.
    #
    # Argumentos de entrada:
    # - list_array_desc: Lista 1xN con los descriptores de cada imagen. Cada posicion de la lista 
    #                   contiene (MxD) numpy arrays que representan UNO O VARIOS DESCRIPTORES 
    #                   extraidos sobre la imagen
    #                   - M es el numero de vectores de caracteristicas/features de cada imagen 
    #                   - D el numero de dimensiones del vector de caracteristicas/feature.    
    #   - vocab_size: int, numero de palabras para el vocabulario a construir.    
    #   - max_iter: int, numero maximo de iteraciones del algoritmo KMeans
    #
    # Argumentos de salida:
    #   - vocabulario: Numpy array de tamaño [vocab_size, D], 
    #                   que contiene los centros de los clusters obtenidos por K-Means
    #
    #
    # NOTA: se sugiere utilizar la funcion sklearn.cluster.KMeans
    # https://scikit-learn.org/stable/modules/generated/sklearn.cluster.KMeans.html     
    """
    vocabulario = np.empty(shape=[vocab_size,list_img_desc[0].shape[1]]) # iniciamos la variable de salida (numpy array)

    # Creamos kmeans
    kmeans = KMeans(n_clusters=vocab_size, max_iter=max_iter, random_state=0)
    # Concatenamos la lista list_img_desc
    concatenate = np.concatenate(list_img_desc)
    # Ajustamos kmeans a la concatenacion de descriptores
    kmeans.fit(concatenate)

    # Obtenemos los centros de los clusters por K-Means
    vocabulario = kmeans.cluster_centers_

    return vocabulario

def obtener_bags_of_words(list_img_desc, vocab):
    """
    # Esta funcion obtiene el Histograma Bag of Words para cada imagen
    #
    # Argumentos de entrada:
    # - list_img_desc: Lista 1xN con los descriptores de cada imagen. Cada posicion de la lista 
    #                   contiene (MxD) numpy arrays que representan UNO O VARIOS DESCRIPTORES 
    #                   extraidos sobre la imagen
    #                   - M es el numero de vectores de caracteristicas/features de cada imagen 
    #                   - D el numero de dimensiones del vector de caracteristicas/feature.  
    #   - vocab: Numpy array de tamaño [vocab_size, D], 
    #                  que contiene los centros de los clusters obtenidos por K-Means.   
    #
    # Argumentos de salida: 
    #   - list_img_bow: Array de Numpy [N x vocab_size], donde cada posicion contiene 
    #                   el histograma bag-of-words construido para cada imagen.
    #
    """
    # iniciamos la variable de salida (numpy array)
    list_img_bow = np.empty(shape=[len(list_img_desc),len(vocab)]) 
    # Longitud del vocabulario
    vocab_tam = len(vocab)
    
    # Para cada descriptor calculamos el histograma bag-of-words
    for idx, descriptor in enumerate(list_img_desc):
        # Distancias entre cada palabra del vocabulario y cada descriptor
        distancias = cdist(vocab, descriptor)

        # Seleccionamos las distancias mínimas
        palabras_min = np.argmin(distancias, axis=1)

        # Contamos las palabras con menor distancia a cada descriptor para hacer el histograma
        histograma = np.bincount(palabras_min, minlength=vocab_tam)

        list_img_bow[idx] = histograma
        
    
    return list_img_bow

if __name__ == "__main__":    
    dataset_path = './datasets/scenes15/'
    print("Practica 3 - Tarea 1 - Test autoevaluación\n")                    
    #print("Tests completados = " + str(test_p3_tarea1(dataset_path,stop_at_error=False,debug=False))) #analizar todos los casos sin pararse en errores ni mostrar datos
    print("Tests completados = " + str(test_p3_tarea1(dataset_path,stop_at_error=True,debug=True))) #analizar todos los casos, pararse en errores y mostrar datos
