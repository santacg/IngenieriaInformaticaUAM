# Tratamiento de Señales Visuales/Tratamiento de Señales Multimedia I @ EPS-UAM
# Practica 2: Extraccion, descripcion y correspondencia de caracteristicas locales
# Memoria: codigo de la pregunta 03

# AUTOR1: GARCÍA SANTA, CARLOS
# AUTOR2: GONZÁLEZ GALLEGO, MIGUEL ÁNGEL
# PAREJA/TURNO: 02/NUMERO_TURNO

import numpy as np
import matplotlib.pyplot as plt
import skimage as ski
from skimage import io, color, draw, feature

from p2_tarea1 import detectar_puntos_interes_harris
from p2_tarea2 import descripcion_puntos_interes
from p2_tarea3 import correspondencias_puntos_interes

img_list_1 = ['img/EGaudi_1.jpg', 'img/Mount_Rushmore1.jpg', 'img/NotreDame1.jpg']
img_list_2 = ['img/EGaudi_2.jpg', 'img/Mount_Rushmore2.jpg', 'img/NotreDame2.jpg']

for img1, img2 in zip(img_list_1, img_list_2):
    img1 = io.imread(img1) / 255.0
    img2 = io.imread(img2) / 255.0

    img1_gray = color.rgb2gray(img1)
    img2_gray = color.rgb2gray(img2)

    ptos_interes_harris_img1 = detectar_puntos_interes_harris(img1_gray, threshold_rel=0.a<)
    ptos_interes_harris_img2 = detectar_puntos_interes_harris(img2_gray, threshold_rel=0.2)

    for pto_1, pto_2 in zip(ptos_interes_harris_img1, ptos_interes_harris_img2):
        x1, y1 = ski.draw.disk((pto_1[0], pto_1[1]), radius=5, shape=img1.shape)
        x2, y2 = ski.draw.disk((pto_2[0], pto_2[1]), radius=5, shape=img2.shape)
        img1[x1, y1] = (1, 0, 0) 
        img2[x2, y2] = (1, 0, 0) 

    fig, axes = plt.subplots(1, 2, figsize=(12, 6))
    axes[0].imshow(img1)
    axes[0].set_title('Esquinas en Imagen 1')

    axes[1].imshow(img2)
    axes[1].set_title('Esquinas en Imagen 2')

    plt.tight_layout()
    plt.show()

    desc_1, new_coords_1 = descripcion_puntos_interes(img1_gray, ptos_interes_harris_img1)
    desc_2, new_coords_2 = descripcion_puntos_interes(img1_gray, ptos_interes_harris_img2)

    corr = correspondencias_puntos_interes(desc_1, desc_2)
    fig, ax = plt.subplots(nrows=1, ncols=1, figsize=(10, 5)) 

    feature.plot_matches(
        ax,
        img1_gray,
        img2_gray,
        new_coords_1,
        new_coords_2,
        matches=corr,
        only_matches=True,
    )

    ax.set_title("Correspondencias")

    plt.tight_layout()
    plt.show()

