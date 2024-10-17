# Tratamiento de Señales Visuales/Tratamiento de Señales Multimedia I @ EPS-UAM
# Practica 1: Fusion de imagenes mediante piramides
# Memoria: codigo de la pregunta 11

# AUTOR1: García Santa Carlos
# AUTOR2: González Gallego, Miguel Angel
# PAREJA/TURNO: 02

import numpy as np
from skimage import io
import matplotlib.pyplot as plt
import p1_tarea4

def cargar_imagenes(rutas):
    """Carga imágenes desde las rutas dadas y las normaliza."""
    return [io.imread(ruta) / 255.0 for ruta in rutas]

def mostrar_piramides(piramides, titulo, niveles):
    """Muestra las pirámides en subplots."""
    fig, axes = plt.subplots(1, niveles, figsize=(15, 5))
    fig.suptitle(titulo)
    
    for nivel in range(niveles):
        # Clipeamos para que los valores se mantengan en rango
        axes[nivel].imshow(np.clip(piramides[nivel], 0, 1))  
        axes[nivel].set_title("Nivel " + str(nivel))
        axes[nivel].axis('off') 
    
    plt.show()

def run_fusion_rgb(imgA_list, imgB_list, mask_list, niveles):
    # Se procesa cada imagen en la lista de imágenes 
    for j in range(len(imgA_list)):
        # Inicializamos las listas que van a guardar las pirámides
        Gypr_imgA_l, Gypr_imgB_l, Gypr_mask_l = [], [], []
        Lpyr_imgA_l, Lpyr_imgB_l, Lpyr_fus_l, Lpyr_fus_rec_l = [], [], [], []

        for i in range(3):  # Procesamos cada canal rgb
            Gpyr_imgA, Gpyr_imgB, Gpyr_mask, Lpyr_imgA, Lpyr_imgB, Lpyr_fus, Lpyr_fus_rec = \
                p1_tarea4.run_fusion(imgA_list[j][:, :, i], imgB_list[j][:, :, i], mask_list[j][:, :, i], niveles)

            # Guardamos los resultados de cada canal
            Gypr_imgA_l.append(Gpyr_imgA)
            Gypr_imgB_l.append(Gpyr_imgB)
            Gypr_mask_l.append(Gpyr_mask)
            Lpyr_imgA_l.append(Lpyr_imgA)
            Lpyr_imgB_l.append(Lpyr_imgB)
            Lpyr_fus_l.append(Lpyr_fus)
            Lpyr_fus_rec_l.append(Lpyr_fus_rec)

        # Combinamos los resultados de los 3 canales por cada nivel de las piramides
        Gpyr_imgA_stack = [np.stack([Gypr_imgA_l[i][nivel] for i in range(3)], axis=2) for nivel in range(niveles)]
        Gpyr_imgB_stack = [np.stack([Gypr_imgB_l[i][nivel] for i in range(3)], axis=2) for nivel in range(niveles)]
        Gpyr_mask_stack = [np.stack([Gypr_mask_l[i][nivel] for i in range(3)], axis=2) for nivel in range(niveles)]
        Lpyr_imgA_stack = [np.stack([Lpyr_imgA_l[i][nivel] for i in range(3)], axis=2) for nivel in range(niveles)]
        Lpyr_imgB_stack = [np.stack([Lpyr_imgB_l[i][nivel] for i in range(3)], axis=2) for nivel in range(niveles)]
        Lpyr_fus_stack = [np.stack([Lpyr_fus_l[i][nivel] for i in range(3)], axis=2) for nivel in range(niveles)]

        # Mostramos las piramides gaussianas de cada imagen
        mostrar_piramides(Gpyr_imgA_stack, "Piramide Gaussiana - imgA", niveles)
        mostrar_piramides(Gpyr_imgB_stack, "Piramide Gaussiana - imgB", niveles)
        mostrar_piramides(Gpyr_mask_stack, "Piramide Gaussiana - mask", niveles)

        # Mostrar pirámides laplacianas de cada imagen
        mostrar_piramides(Lpyr_imgA_stack, "Piramide Laplaciana - imgA", niveles)
        mostrar_piramides(Lpyr_imgB_stack, "Piramide Laplaciana - imgB", niveles)
        mostrar_piramides(Lpyr_fus_stack, "Piramide Laplaciana - fusion", niveles)

        # Recomponemos la pirámide fusionada final
        Lpyr_fus_rec_stack = np.stack([Lpyr_fus_rec_l[i] for i in range(3)], axis=2)

        # Mostramos la imagen fusionada final
        io.imshow(Lpyr_fus_rec_stack)
        plt.title("Imagen fusionada final")
        plt.show()

if __name__ == "__main__":
    # Rutas de las imágenes y máscaras
    rutas_imgA = ["./img/orange1.jpg", "./img/orange2.jpg", "./img/orchid.jpg"]
    rutas_imgB = ["./img/apple1.jpg", "./img/apple2.jpg", "./img/violet.jpg"]
    rutas_mask = ["./img/mask_apple1_orange1.jpg", "./img/mask_apple2_orange2.jpg", "./img/orchid_mask.jpg"]

    # Cargamos imágenes y máscaras
    imgA_list = cargar_imagenes(rutas_imgA)
    imgB_list = cargar_imagenes(rutas_imgB)
    mask_list = cargar_imagenes(rutas_mask)

    run_fusion_rgb(imgA_list, imgB_list, mask_list, niveles=5)

