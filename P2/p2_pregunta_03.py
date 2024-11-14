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
    h1, w1 = imagen1.shape
    h2, w2 = imagen2.shape

    max_height = max(h1, h2)

    plt.figure(figsize=(15, 8))
    ax = plt.gca()

    ax.imshow(imagen1, cmap='gray', extent=(0, w1, max_height, max_height - h1))
    ax.imshow(imagen2, cmap='gray', extent=(w1, w1 + w2, max_height, max_height - h2))

    coords1_adj = coords1.copy()
    coords1_adj[:, 0] = max_height - coords1[:, 0]  # Invertir eje Y para imágenes
    coords2_adj = coords2.copy()
    coords2_adj[:, 0] = max_height - coords2[:, 0]  # Invertir eje Y
    coords2_adj[:, 1] += w1  # Desplazar en X

    ax.scatter(coords1_adj[:, 1], coords1_adj[:, 0], c='r', marker='o')
    ax.scatter(coords2_adj[:, 1], coords2_adj[:, 0], c='b', marker='o')

    for idx1, idx2 in correspondencias:
        x1, y1 = coords1_adj[idx1, 1], coords1_adj[idx1, 0]
        x2, y2 = coords2_adj[idx2, 1], coords2_adj[idx2, 0]
        ax.plot([x1, x2], [y1, y2], 'y-', linewidth=1)

    plt.axis('off')
    plt.show()

lista_imagen1 = ['img/EGaudi_1.jpg', 'img/Mount_Rushmore_1.jpg', 'img/NotreDame_1.jpg']
lista_imagen2 = ['img/EGaudi_2.jpg', 'img/Mount_Rushmore_2.jpg', 'img/NotreDame_2.jpg']

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


