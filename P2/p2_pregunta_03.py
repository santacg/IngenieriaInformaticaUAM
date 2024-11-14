# Tratamiento de Señales Visuales/Tratamiento de Señales Multimedia I @ EPS-UAM
# Practica 2: Extraccion, descripcion y correspondencia de caracteristicas locales
# Memoria: codigo de la pregunta 03

# AUTOR1: GARCÍA SANTA, CARLOS
# AUTOR2: GONZÁLEZ GALLEGO, MIGUEL ÁNGEL
# PAREJA/TURNO: 02/NUMERO_TURNO

import numpy as np
import matplotlib.pyplot as plt
from skimage import io, color

from p2_tarea1 import detectar_puntos_interes_harris
from p2_tarea2 import descripcion_puntos_interes
from p2_tarea3 import correspondencias_puntos_interes

def mostrar_puntos_interes(imagen, coords_esquinas, titulo):
    plt.figure(figsize=(8,6))
    plt.imshow(imagen, cmap='gray')
    plt.scatter(coords_esquinas[:, 1], coords_esquinas[:, 0], c='r', s=40, marker='o')
    plt.title(titulo)
    plt.axis('off')
    plt.show()

def visualizar_correspondencias(imagen1, imagen2, coords1, coords2, correspondencias):
    imagen_concat = np.concatenate((imagen1, imagen2), axis=1)
    
    coords2_adj = coords2.copy()
    coords2_adj[:, 1] += imagen1.shape[1]
    
    plt.figure(figsize=(15, 8))
    plt.imshow(imagen_concat, cmap='gray')
    
    plt.scatter(coords1[:, 1], coords1[:, 0], c='r', marker='o')
    plt.scatter(coords2_adj[:, 1], coords2_adj[:, 0], c='b', marker='o')
    
    for idx1, idx2 in correspondencias:
        y1, x1 = coords1[idx1]
        y2, x2 = coords2_adj[idx2]
        plt.plot([x1, x2], [y1, y2], 'y-', linewidth=1)
    
    plt.axis('off')
    plt.show()

lista_imagen1 = ['img/EGaudi1.jpg', 'img/Mount_Rushmore1.jpg', 'img/NotreDame1.jpg']
lista_imagen2 = ['img/EGaudi2.jpg', 'img/Mount_Rushmore2.jpg', 'img/NotreDame2.jpg']

for img1, img2 in zip(lista_imagen1, lista_imagen2):
    imagen1 = io.imread(img1, as_gray=True)
    imagen2 = io.imread(img2, as_gray=True)

    # 1
    coords_esquinas1 = detectar_puntos_interes_harris(imagen1)
    coords_esquinas2 = detectar_puntos_interes_harris(imagen2)
    mostrar_puntos_interes(imagen1, coords_esquinas1, f'Esquinas en {imagen1}')
    mostrar_puntos_interes(imagen2, coords_esquinas2, f'Esquinas en {imagen2}')

    # 2
    descriptores1, new_coords_esquinas1 = descripcion_puntos_interes(imagen1, coords_esquinas1, tipoDesc='mag-ori')
    descriptores2, new_coords_esquinas2 = descripcion_puntos_interes(imagen2, coords_esquinas2, tipoDesc='mag-ori')

    # 3
    correspondencias = correspondencias_puntos_interes(descriptores1, descriptores2, tipoCorr='mindist', max_distancia=25)
    visualizar_correspondencias(imagen1, imagen2, new_coords_esquinas1, new_coords_esquinas2, correspondencias)


