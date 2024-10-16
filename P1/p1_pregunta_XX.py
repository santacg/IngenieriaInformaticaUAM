# Tratamiento de Señales Visuales/Tratamiento de Señales Multimedia I @ EPS-UAM
# Practica 1: Fusion de imagenes mediante piramides
# Memoria: codigo de la pregunta XX

# AUTOR1: García Santa Carlos
# AUTOR2: González Gallego, Miguel Angel
# PAREJA/TURNO: 02/NUMERO_TURNO

import numpy as np
from skimage import io
from p1_utils import visualizar_fusion
import p1_tarea2
import p1_tarea3


path_imagenes = "./p1/img/"

def run_fusion_rgb(imgA, imgB, mask, niveles): 
   # Verificamos que las imágenes sean matrices bidimensionales
    if np.ndim(imgA) != 3 or np.ndim(imgB) != 3:
        raise ValueError("Las imágenes no están en formato RGB") 
    
    # Convertimos las imágenes y la máscara a tipo float
    imgA = np.array(imgA, dtype=np.float64)
    imgB = np.array(imgB, dtype=np.float64)
    mask = np.array(mask, dtype=np.float64)

    # Obtenemos cada canal de las imágenes y lo introducimos en una lista para cada imagen
    imgA_rgb = [imgA[:, :, i] for i in range(3)]
    imgB_rgb = [imgB[:, :, i] for i in range(3)]
    mask_rgb = [mask[:, :, i] for i in range(3)]

    # iniciamos las variables de salida    
    Gypr_imgA = []
    Gypr_imgB = []
    Gypr_mask = []
    Lpyr_imgA = []
    Lpyr_imgB = []
    Lpyr_fus = []
    Lpyr_fus_rec = []

    for i in range(3):
        Gypr_imgA.append(p1_tarea2.gaus_piramide(imgA_rgb[i], niveles))
        Gypr_imgB.append(p1_tarea2.gaus_piramide(imgB_rgb[i], niveles))
        Gypr_mask.append(p1_tarea2.gaus_piramide(mask_rgb[i], niveles))
        Lpyr_imgA.append(p1_tarea2.lapl_piramide(Gypr_imgA[i]))
        Lpyr_imgB.append(p1_tarea2.lapl_piramide(Gypr_imgB[i]))
        Lpyr_fus.append(p1_tarea3.fusionar_lapl_pyr(Lpyr_imgA[i], Lpyr_imgB[i], Gypr_mask[i]))
        Lpyr_fus_rec.append(p1_tarea3.reconstruir_lapl_pyr(Lpyr_fus[i]))
        Lpyr_fus_rec[i] = np.clip(Lpyr_fus_rec[i], 0, 1)

    Gypr_imgA = np.stack(Gypr_imgA, axis=2)
    Gypr_imgB = np.stack(Gypr_imgB, axis=2)
    Gypr_mask = np.stack(Gypr_mask, axis=2)
    Lpyr_imgA = np.stack(Lpyr_imgA, axis=2)
    Lpyr_imgB = np.stack(Lpyr_imgB, axis=2)
    Lpyr_fus = np.stack(Lpyr_fus, axis=2)
    Lpyr_fus_rec = np.stack(Lpyr_fus_rec, axis=2)

    return Gypr_imgA, Gypr_imgA, Gypr_mask, Lpyr_imgA, Lpyr_imgB, Lpyr_fus, Lpyr_fus_rec

if __name__ == "__main__":
    # Leer las imágenes de prueba y la máscara
    imgA = io.imread("./p1/img/orange1.jpg") / 255.0
    imgB = io.imread("./p1/img/orange2.jpg") / 255.0
    mask = io.imread("./p1/img/mask_apple2_orange2.jpg") / 255.0
    
    # Ejecutar la fusión de imágenes
    niveles = 5
    Gpyr_imgA, Gpyr_imgB, Gpyr_mask, \
    Lpyr_imgA, Lpyr_imgB, Lpyr_fus, Lpyr_fus_rec = run_fusion_rgb(imgA, imgB, mask, niveles)

    # Visualizar las pirámides de fusión para cada canal
    for i in range(3):
        visualizar_fusion(imgA[:, :, i], imgB[:, :, i], mask[:, :, i],
                          Gpyr_imgA[:, :, i], Gpyr_imgB[:, :, i], Gpyr_mask[:, :, i],
                          Lpyr_imgA[:, :, i], Lpyr_imgB[:, :, i], Lpyr_fus[:, :, i], 
                          Lpyr_fus_rec[:, :, i])

