# Tratamiento de Señales Visuales/Tratamiento de Señales Multimedia I @ EPS-UAM
# Practica 1: Fusion de imagenes mediante piramides
# Memoria: codigo de la pregunta 12

# AUTOR1: García Santa Carlos
# AUTOR2: González Gallego, Miguel Angel
# PAREJA/TURNO: 02

import numpy as np
from skimage import io
import matplotlib.pyplot as plt
import p1_tarea4

def cargar_imagenes(rutas):
    """Carga imágenes desde las rutas dadas y las normaliza (0-1)."""
    return [io.imread(ruta) / 255.0 for ruta in rutas]

def mostrar_fusiones_en_ventana(list_img_fusion, niveles_list, imagen_num):
    """Muestra todas las imágenes fusionadas para los diferentes niveles en una sola ventana."""
    num_niveles = len(niveles_list)
    fig, axes = plt.subplots(1, num_niveles, figsize=(15, 5))
    fig.suptitle(f'Comparación de fusión para Imagen {imagen_num+1} con diferentes niveles de pirámide')

    for nivel, (img_fus, niveles) in enumerate(zip(list_img_fusion, niveles_list)):
        axes[nivel].imshow(np.clip(img_fus, 0, 1))
        axes[nivel].set_title(f'{niveles} niveles')
        axes[nivel].axis('off')  # Ocultar los ejes
    
    plt.show()

def procesar_fusion_con_niveles(imgA_list, imgB_list, mask_list, niveles_list):
    """Procesa la fusión de imágenes variando el número de niveles y muestra los resultados."""
    for j in range(len(imgA_list)):
        # Lista para almacenar las imágenes fusionadas con distintos niveles
        list_img_fusion = []

        for niveles in niveles_list:
            print(f"\nProcesando fusión con {niveles} niveles de pirámide para Imagen {j+1}...")

            # Inicializar listas para almacenar los resultados de los 3 canales
            Lpyr_fus_rec_l = []

            for i in range(3):  # Procesar los 3 canales (R, G, B)
                _, _, _, _, _, _, Lpyr_fus_rec = p1_tarea4.run_fusion(
                    imgA_list[j][:, :, i], imgB_list[j][:, :, i], mask_list[j][:, :, i], niveles
                )
                # Guardar los resultados de la pirámide fusionada por canal
                Lpyr_fus_rec_l.append(Lpyr_fus_rec)

            # Recomponer la imagen fusionada a partir de los 3 canales
            Lpyr_fus_rec_stack = np.stack([Lpyr_fus_rec_l[i] for i in range(3)], axis=2)

            # Añadir la imagen fusionada a la lista
            list_img_fusion.append(Lpyr_fus_rec_stack)

        # Mostrar todas las imágenes fusionadas en una sola ventana
        mostrar_fusiones_en_ventana(list_img_fusion, niveles_list, j)

if __name__ == "__main__":
    # Rutas de las imágenes y máscaras
    rutas_imgA = ["./img/orange1.jpg", "./img/orange2.jpg"]
    rutas_imgB = ["./img/apple1.jpg", "./img/apple2.jpg"]
    rutas_mask = ["./img/mask_apple1_orange1.jpg", "./img/mask_apple2_orange2.jpg"]

    # Cargar imágenes y máscaras
    imgA_list = cargar_imagenes(rutas_imgA)
    imgB_list = cargar_imagenes(rutas_imgB)
    mask_list = cargar_imagenes(rutas_mask)

    # Explorar la fusión con distintos niveles de pirámides
    niveles_list = [3, 5, 7]  # Niveles a explorar
    procesar_fusion_con_niveles(imgA_list, imgB_list, mask_list, niveles_list)