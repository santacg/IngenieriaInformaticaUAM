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
    """Carga imágenes desde las rutas dadas y las normaliza (0-1)."""
    return [io.imread(ruta) / 255.0 for ruta in rutas]

def mostrar_piramides(piramides, titulo, niveles):
    """Muestra las pirámides (gaussianas o laplacianas) en subplots."""
    fig, axes = plt.subplots(1, niveles, figsize=(15, 5))
    fig.suptitle(titulo)
    
    for nivel in range(niveles):
        axes[nivel].imshow(np.clip(piramides[nivel], 0, 1))  
        axes[nivel].set_title(f'Nivel {nivel}')
        axes[nivel].axis('off') 
    
    plt.show()

def procesar_fusion(imgA_list, imgB_list, mask_list, niveles):
    """Procesa la fusión de imágenes en todos los niveles de la pirámide."""
    for j in range(len(imgA_list)):
        # Inicializar listas de pirámides
        Gypr_imgA_l, Gypr_imgB_l, Gypr_mask_l = [], [], []
        Lpyr_imgA_l, Lpyr_imgB_l, Lpyr_fus_l, Lpyr_fus_rec_l = [], [], [], []

        for i in range(3):  # Procesar los 3 canales (R, G, B)
            Gpyr_imgA, Gpyr_imgB, Gpyr_mask, Lpyr_imgA, Lpyr_imgB, Lpyr_fus, Lpyr_fus_rec = \
                p1_tarea4.run_fusion(imgA_list[j][:, :, i], imgB_list[j][:, :, i], mask_list[j][:, :, i], niveles)

            # Guardar resultados de los canales
            Gypr_imgA_l.append(Gpyr_imgA)
            Gypr_imgB_l.append(Gpyr_imgB)
            Gypr_mask_l.append(Gpyr_mask)
            Lpyr_imgA_l.append(Lpyr_imgA)
            Lpyr_imgB_l.append(Lpyr_imgB)
            Lpyr_fus_l.append(Lpyr_fus)
            Lpyr_fus_rec_l.append(Lpyr_fus_rec)

        # Combinar resultados de los 3 canales por nivel
        Gpyr_imgA_stack = [np.stack([Gypr_imgA_l[i][nivel] for i in range(3)], axis=2) for nivel in range(niveles)]
        Gpyr_imgB_stack = [np.stack([Gypr_imgB_l[i][nivel] for i in range(3)], axis=2) for nivel in range(niveles)]
        Gpyr_mask_stack = [np.stack([Gypr_mask_l[i][nivel] for i in range(3)], axis=2) for nivel in range(niveles)]
        Lpyr_imgA_stack = [np.stack([Lpyr_imgA_l[i][nivel] for i in range(3)], axis=2) for nivel in range(niveles)]
        Lpyr_imgB_stack = [np.stack([Lpyr_imgB_l[i][nivel] for i in range(3)], axis=2) for nivel in range(niveles)]
        Lpyr_fus_stack = [np.stack([Lpyr_fus_l[i][nivel] for i in range(3)], axis=2) for nivel in range(niveles)]

        # Mostrar pirámides gaussianas para imgA
        mostrar_piramides(Gpyr_imgA_stack, f'Pirámide Gaussiana - imgA (Imagen {j+1})', niveles)
        mostrar_piramides(Gpyr_imgB_stack, f'Pirámide Gaussiana - imgB (Imagen {j+1})', niveles)
        mostrar_piramides(Gpyr_mask_stack, f'Pirámide Gaussiana - mask (Imagen {j+1})', niveles)

        # Mostrar pirámides laplacianas para imgA
        mostrar_piramides(Lpyr_imgA_stack, f'Pirámide Laplaciana - imgA (Imagen {j+1})', niveles)
        mostrar_piramides(Lpyr_imgB_stack, f'Pirámide Laplaciana - imgB (Imagen {j+1})', niveles)
        mostrar_piramides(Lpyr_fus_stack, f'Pirámide Laplaciana - fusion (Imagen {j+1})', niveles)

        # Recomponer la pirámide fusionada final
        Lpyr_fus_rec_stack = np.stack([Lpyr_fus_rec_l[i] for i in range(3)], axis=2)

        # Mostrar imagen fusionada final
        io.imshow(Lpyr_fus_rec_stack)
        plt.title(f'Imagen fusionada final (Imagen {j+1})')
        plt.show()

if __name__ == "__main__":
    # Rutas de las imágenes y máscaras
    rutas_imgA = ["./img/orange1.jpg", "./img/orange2.jpg", "./img/orchid.jpg"]
    rutas_imgB = ["./img/apple1.jpg", "./img/apple2.jpg", "./img/violet.jpg"]
    rutas_mask = ["./img/mask_apple1_orange1.jpg", "./img/mask_apple2_orange2.jpg", "./img/orchid_mask.jpg"]

    # Cargar imágenes y máscaras
    imgA_list = cargar_imagenes(rutas_imgA)
    imgB_list = cargar_imagenes(rutas_imgB)
    mask_list = cargar_imagenes(rutas_mask)

    # Procesar la fusión con 5 niveles de pirámide
    procesar_fusion(imgA_list, imgB_list, mask_list, niveles=5)

