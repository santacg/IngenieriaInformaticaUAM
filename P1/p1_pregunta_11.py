# Tratamiento de Señales Visuales/Tratamiento de Señales Multimedia I @ EPS-UAM
# Practica 1: Fusion de imagenes mediante piramides
# Memoria: codigo de la pregunta 11

# AUTOR1: García Santa Carlos
# AUTOR2: González Gallego, Miguel Angel
# PAREJA/TURNO: 02

import numpy as np
from skimage import io
import matplotlib.pyplot as plt
import p1_tarea2
import p1_tarea3

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

def run_fusion_rgb(imgA, imgB, mask, niveles): 
    # Verificamos que las imágenes sean matrices bidimensionales
    if np.ndim(imgA) != 3 or np.ndim(imgB) != 3:
        raise ValueError("Las imágenes no están en formato RGB") 
    
    # Convertimos las imágenes y la máscara a tipo float
    imgA = np.array(imgA, dtype=np.float64)
    imgB = np.array(imgB, dtype=np.float64)
    mask = np.array(mask, dtype=np.float64)

    # Obtenemos cada canal
    imgA_rgb = [imgA[:, :, i] for i in range(3)]
    imgB_rgb = [imgB[:, :, i] for i in range(3)]
    mask_rgb = [mask[:, :, i] for i in range(3)]

    Gpyr_imgA_l, Gpyr_imgB_l, Gpyr_mask_l = [], [], []
    Lpyr_imgA_l, Lpyr_imgB_l, Lpyr_fus_l, Lpyr_fus_rec_l = [], [], [], []

    for i in range(3):
        Gpyr_imgA_l.append(p1_tarea2.gaus_piramide(imgA_rgb[i], niveles))      # Pirámide Gaussiana imagen A
        Gpyr_imgB_l.append(p1_tarea2.gaus_piramide(imgB_rgb[i], niveles))      # Pirámide Gaussiana imagen B
        Gpyr_mask_l.append(p1_tarea2.gaus_piramide(mask_rgb[i], niveles))      # Pirámide Gaussiana máscara    
        Lpyr_imgA_l.append(p1_tarea2.lapl_piramide(Gpyr_imgA_l[i]))          # Pirámide Laplaciana imagen A
        Lpyr_imgB_l.append(p1_tarea2.lapl_piramide(Gpyr_imgB_l[i]))          # Pirámide Laplaciana imagen B
        Lpyr_fus_l.append(p1_tarea3.fusionar_lapl_pyr(Lpyr_imgA_l[i], Lpyr_imgB_l[i], Gpyr_mask_l[i]))       # Pirámide Laplaciana fusionada

        # Recortamos los valores que esten fuera del rango [0, 1]
        clip = np.clip(p1_tarea3.reconstruir_lapl_pyr(Lpyr_fus_l[i]), 0, 1)
        Lpyr_fus_rec_l.append(clip)


    # Combinamos los resultados de los 3 canales por cada nivel de las piramides
    Gpyr_imgA_stack = [np.stack([Gpyr_imgA_l[i][nivel] for i in range(3)], axis=2) for nivel in range(niveles)]
    Gpyr_imgB_stack = [np.stack([Gpyr_imgB_l[i][nivel] for i in range(3)], axis=2) for nivel in range(niveles)]
    Gpyr_mask_stack = [np.stack([Gpyr_mask_l[i][nivel] for i in range(3)], axis=2) for nivel in range(niveles)]
    Lpyr_imgA_stack = [np.stack([Lpyr_imgA_l[i][nivel] for i in range(3)], axis=2) for nivel in range(niveles)]
    Lpyr_imgB_stack = [np.stack([Lpyr_imgB_l[i][nivel] for i in range(3)], axis=2) for nivel in range(niveles)]
    Lpyr_fus_stack = [np.stack([Lpyr_fus_l[i][nivel] for i in range(3)], axis=2) for nivel in range(niveles)]

    # Recomponemos la pirámide fusionada final
    Lpyr_fus_rec_stack = np.stack([Lpyr_fus_rec_l[i] for i in range(3)], axis=2)

    return Gpyr_imgA_stack, Gpyr_imgB_stack, Gpyr_mask_stack, Lpyr_imgA_stack, Lpyr_imgB_stack, Lpyr_fus_stack, Lpyr_fus_rec_stack

if __name__ == "__main__":
    # Rutas de las imágenes y máscaras
    rutas_imgA = ["./img/orange1.jpg", "./img/orange2.jpg", "./img/orchid.jpg"]
    rutas_imgB = ["./img/apple1.jpg", "./img/apple2.jpg", "./img/violet.jpg"]
    rutas_mask = ["./img/mask_apple1_orange1.jpg", "./img/mask_apple2_orange2.jpg", "./img/orchid_mask.jpg"]

    # Cargamos imágenes y máscaras
    imgA_list = cargar_imagenes(rutas_imgA)
    imgB_list = cargar_imagenes(rutas_imgB)
    mask_list = cargar_imagenes(rutas_mask)

    niveles = 10 
    for i in range(len(rutas_imgA)):
        Gpyr_imgA, Gpyr_imgB, Gpyr_mask, \
        Lpyr_imgA, Lpyr_imgB, Lpyr_fus, Lpyr_fus_rec = run_fusion_rgb(imgA_list[i], imgB_list[i], mask_list[i], niveles=5)

        # Mostramos las piramides gaussianas de cada imagen
        mostrar_piramides(Gpyr_imgA, "Piramide Gaussiana - imgA", niveles)
        mostrar_piramides(Gpyr_imgB, "Piramide Gaussiana - imgB", niveles)
        mostrar_piramides(Gpyr_mask, "Piramide Gaussiana - mask", niveles)

        # Mostramos pirámides laplacianas de cada imagen
        mostrar_piramides(Lpyr_imgA, "Piramide Laplaciana - imgA", niveles)
        mostrar_piramides(Lpyr_imgB, "Piramide Laplaciana - imgB", niveles)
        mostrar_piramides(Lpyr_fus, "Piramide Laplaciana - fusion", niveles)

        # Mostramos la imagen fusionada final
        io.imshow(Lpyr_fus_rec)
        plt.title("Imagen fusionada final")
        plt.show()

