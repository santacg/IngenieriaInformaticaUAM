# Tratamiento de Señales Visuales/Tratamiento de Señales Multimedia I @ EPS-UAM
# Practica 3: Reconocimiento de escenas con modelos BOW/BOF
# Tarea 2: extraccion de caracteristicas

# AUTOR1: GARCÍA SANTA, CARLOS
# AUTOR2: GONZÁLEZ GALLEGO, MIGUEL ANGEL
# PAREJA/TURNO: 02/NUMERO_TURNO

# librerias y paquetes por defecto
from p3_tests import test_p3_tarea2
from skimage.color import rgb2gray
from skimage.transform import resize
from skimage.io import imread
from skimage.feature import hog
import numpy as np

# Incluya aqui las librerias que necesite en su codigo
# ...

def obtener_features_tiny(path_imagenes, tamano = 16):
    """
    # Esta funcion calcula un descriptor basado en submuestreo para una lista de imagenes.
    # Para cada imagen, el descriptor se basa en convertir la imagen a gris, redimensionar 
    # la imagen y posteriormente convertirla en un vector fila.        
    #
    # Argumentos de entrada:
    #   - path_imagenes: lista, una lista de Python con N strings. Cada string corresponde
    #                    con la ruta/path de la imagen en el sistema, que se debe cargar 
    #                    para calcular la caracteristica Tiny.
    #   - tamano:        int, valor de la dimension de cada imagen resultante
    #                    tras aplicar el redimensionado de las imagenes de entrada
    #
    # Argumentos de salida:
    # - list_img_desc_tiny: Lista 1xN, donde cada posicion representa los descriptores calculados para cada imagen.
    #                       En el caso de caracteristicas Tiny, cada posicion contiene UN DESCRIPTOR 
    #                       con dimensiones 1xD donde D el numero de dimensiones del vector de caracteristicas/feature Tiny.
    #                       Ejemplo: si tamano=16, entonces D = 16 * 16 = 256 y el vector será 1x256
    """       
    # Iniciamos variable de salida
    list_img_desc_tiny = list()

    for path in path_imagenes:

        # Convertimos las imagenes a escala de gris
        img_gray = rgb2gray(imread(path))
        
        # Damos el formato float en el rango [0,1]
        img_gray = img_gray.astype(np.float64)/255.0
        
        # Redimensionar la imagen al tamaño deseado
        resized_img = resize(img_gray, (tamano, tamano))

        # Aplanar la imagen redimensionada y convertir a formato 1xD
        descriptor = resized_img.flatten().reshape(1, -1)

        # Añadir los descriptores a la lista 1xN
        list_img_desc_tiny.append(descriptor)
       
    return list_img_desc_tiny

def obtener_features_hog(path_imagenes, tamano=100, orientaciones=9,pixeles_por_celda=(8, 8),celdas_bloque=(2,2)):
    """
    # Esta funcion calcula un descriptor basado en Histograma de Gradientes Orientados (HOG) 
    # para una lista de imagenes. Para cada imagen, se convierte la imagen a escala de grises, redimensiona 
    # la imagen y el descriptor se basa aplicar HOG a la imagen que posteriormente se convierte a un vector fila.      
    #
    # Argumentos de entrada:
    #   - path_imagenes: lista, una lista de Python con N strings. Cada string corresponde
    #                    con la ruta/path de la imagen en el sistema, que se debe cargar 
    #                    para calcular la caracteristica HOG.
    #   - tamano:        int, valor de la dimension de cada imagen resultante
    #                    tras aplicar el redimensionado de las imagenes de entrada
    #   - orientaciones: int, numero de orientaciones a considerar en el descriptor HOG
    #   - pixeles_por_celda: tupla de int, numero de pixeles en cada celdas del descriptor HOG
    #   - celdas_bloque:  tupla de int, numero de celdas a considerar en cada bloque del descriptor HOG
    #
    # Argumentos de salida:
    # - list_img_desc_hog: Lista 1xN, donde cada posicion representa los descriptores calculados para cada imagen.
    #                       En el caso de caracteristicas HOG, cada posicion contiene VARIOS DESCRIPTORES 
    #                       con dimensiones MxD donde 
    #                       - M es el numero de vectores de caracteristicas/features de cada imagen 
    #                       - D el numero de dimensiones del vector de caracteristicas/feature HOG.
    #                       Ejemplo: Para una imagen de 100x100 y con valores por defecto, 
    #                       para cada imagen se obtienen M=81 vectores/descriptores de D=144 dimensiones.  
    #   
    # NOTA: para cada imagen utilice la funcion 'skimage.feature.hog' con los argumentos 
    #                           "orientations=orientaciones, pixels_per_cell=pixeles_por_celda, 
    #                           cells_per_block=celdas_bloque, feature_vector=False"
    #       obtendra un array numpy de cinco dimensiones con 'shape' (S1,S2,S3,S4,S5), en este caso:
    #                      - 'M' se corresponde a las dos primeras dimensiones S1, S2
    #                      - 'D' se corresponde con las tres ultimas dimensiones S3,S4,S5
    #       Con lo cual transforme su vector (S1,S2,S3,S4,S5) en (M,D). Se sugiere utilizar la funcion 'numpy.reshape'
    """
    # Iniciamos variable de salida
    list_img_desc_hog = list()

    # Recorremos la lista de imagenes 
    for img_path in path_imagenes:
        # Leemos la imagen y la pasamos a escala de grises
        img = rgb2gray(imread(img_path))

        # Convertimos a float y pasamos al rango 0 - 1
        img = img.astype(np.float64) / 255.0

        # Redimensionamos la imagen
        img = resize(img, (tamano, tamano))

        # Empleamos la funcion HOG
        img_hog = hog(img, orientaciones, pixeles_por_celda, celdas_bloque, feature_vector=False)

        # Lo convertimos a vector fila
        list_img_desc_hog.append(img_hog.reshape(np.prod(img_hog.shape[:2]), np.prod(img_hog.shape[2:5])))


    return list_img_desc_hog
    
if __name__ == "__main__":    
    dataset_path = './datasets/scenes15/'
    print("Practica 3 - Tarea 2 - Test autoevaluación\n")                    
    print("Tests completados = " + str(test_p3_tarea2(dataset_path,stop_at_error=False,debug=False))) #analizar todos los casos sin pararse en errores ni mostrar datos
    #print("Tests completados = " + str(test_p3_tarea2(dataset_path,stop_at_error=True,debug=True))) #analizar todos los casos, pararse en errores y mostrar datos
