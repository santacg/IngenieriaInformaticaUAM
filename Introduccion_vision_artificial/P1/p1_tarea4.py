# Tratamiento de Señales Visuales/Tratamiento de Señales Multimedia I @ EPS-UAM
# Practica 1: Fusion de imagenes mediante piramides
# Tarea 4: fusion de imagenes

# AUTOR1: APELLIDO1 APELLIDO1, NOMBRE1
# AUTOR2: APELLIDO2 APELLIDO2, NOMBRE2
# PAREJA/TURNO: NUMERO_PAREJA/NUMERO_TURNO

import numpy as np
import math

from p1_utils import visualizar_fusion
import p1_tarea1
import p1_tarea2
import p1_tarea3
from p1_tests import test_p1_tarea4

def run_fusion(imgA, imgB, mask, niveles): 
    """ 
    # Esta funcion implementa la fusion de dos imagenes calculando las 
    # pirámides Laplacianas de las imagenes de entrada y la pirámide
    # Gausiana de una mascara.
    #  
    # Argumentos de entrada:
    #   imgA: numpy array de tamaño [imagen_height, imagen_width].
    #   imgB: numpy array de tamaño [imagen_height, imagen_width].
    #   mask: numpy array de tamaño [imagen_height, imagen_width].
    #
    # Devuelve:
    #   Gpyr_imgA: lista de numpy arrays con variable tamaño con "niveles+1" elementos 
    #               correspodientes a la piramide Gaussiana de la imagen A
    #   Gpyr_imgB: lista de numpy arrays con variable tamaño con "niveles+1" elementos 
    #               correspodientes a la piramide Gaussiana de la imagen B
    #   Gpyr_mask: lista de numpy arrays con variable tamaño con "niveles+1" elementos 
    #               correspodientes a la piramide Gaussiana de la máscara
    #   Lpyr_imgA: lista de numpy arrays con variable tamaño con "niveles+1" elementos 
    #               correspodientes a la piramide Laplaciana de la imagen A
    #   Lpyr_imgB: lista de numpy arrays con variable tamaño con "niveles+1" elementos 
    #               correspodientes a la piramide Laplaciana de la imagen B
    #   Lpyr_fus: lista de numpy arrays con variable tamaño con "niveles+1" elementos 
    #               correspodientes a la piramide Laplaciana de la fusion imagen A & B
    #   Lpyr_fus_rec:  numpy array de tamaño [imagen_height, imagen_width] correspondiente
    #               a la reconstruccion de la pirámide Lpyr_fus
    """ 
    # Verificamos que las imágenes sean matrices bidimensionales
    if np.ndim(imgA) != 2 or np.ndim(imgB) != 2:
        raise ValueError("Las imágenes no están en escala de griss") 
    
    # Convertimos las imágenes y la máscara a tipo float
    imgA = np.array(imgA, dtype=np.float64)
    imgB = np.array(imgB, dtype=np.float64)
    mask = np.array(mask, dtype=np.float64)

    # iniciamos las variables de salida    
    Gpyr_imgA = p1_tarea2.gaus_piramide(imgA, niveles)      # Pirámide Gaussiana imagen A
    Gpyr_imgB = p1_tarea2.gaus_piramide(imgB, niveles)      # Pirámide Gaussiana imagen B
    Gpyr_mask = p1_tarea2.gaus_piramide(mask, niveles)      # Pirámide Gaussiana máscara    
    Lpyr_imgA = p1_tarea2.lapl_piramide(Gpyr_imgA)          # Pirámide Laplaciana imagen A
    Lpyr_imgB = p1_tarea2.lapl_piramide(Gpyr_imgB)          # Pirámide Laplaciana imagen B
    Lpyr_fus = p1_tarea3.fusionar_lapl_pyr(Lpyr_imgA, Lpyr_imgB, Gpyr_mask)       # Pirámide Laplaciana fusionada
    Lpyr_fus_rec = p1_tarea3.reconstruir_lapl_pyr(Lpyr_fus)                       # Imagen reconstruida de la pirámide Laplaciana fusionada

    # Recortamos los valores que esten fuera del rango [0, 1]
    Lpyr_fus_rec = np.clip(Lpyr_fus_rec, 0, 1)

    return Gpyr_imgA, Gpyr_imgB, Gpyr_mask, Lpyr_imgA, Lpyr_imgB, Lpyr_fus, Lpyr_fus_rec
if __name__ == "__main__":    
    
    path_imagenes = "./img/"
    print("Practica 1 - Tarea 4 - Test autoevaluación\n")    
    result,imgAgray,imgBgray,maskgray,\
        Gpyr_imgA, Gpyr_imgB, Gpyr_mask, Lpyr_imgA, Lpyr_imgB, Lpyr_fus, Lpyr_fus_rec \
            = test_p1_tarea4(path_img=path_imagenes,precision=2)
    print("Tests completado = " + str(result)) 

    if result==True:
        #visualizar piramides de la fusion (puede consultar el codigo en el fichero p1_utils.py)
        visualizar_fusion(imgAgray,imgBgray,maskgray,Gpyr_imgA, Gpyr_imgB, Gpyr_mask, Lpyr_imgA, Lpyr_imgB, Lpyr_fus, Lpyr_fus_rec)
