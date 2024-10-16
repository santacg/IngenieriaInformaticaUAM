# Tratamiento de Señales Visuales/Tratamiento de Señales Multimedia I @ EPS-UAM
# Practica 1: Fusion de imagenes mediante piramides
# Memoria: codigo de la pregunta XX

# AUTOR1: García Santa Carlos
# AUTOR2: González Gallego, Miguel Angel
# PAREJA/TURNO: 02/NUMERO_TURNO

import numpy as np
from skimage import io
import matplotlib.pyplot as plt
import p1_tarea4
import p1_utils

if __name__ == "__main__":
    # Leer las imágenes de prueba y la máscara
    imgA = io.imread("./img/orange1.jpg") / 255.0
    imgB = io.imread("./img/apple1.jpg") / 255.0
    mask = io.imread("./img/mask_apple1_orange1.jpg") / 255.0

    Gypr_imgA_l = []
    Gypr_imgB_l = []
    Gypr_mask_l = []
    Lpyr_imgA_l = []
    Lpyr_imgB_l = []
    Lpyr_fus_l = []
    Lpyr_fus_rec_l = []

    niveles = 5 
    for i in range(3):
        Gpyr_imgA, Gpyr_imgB, Gpyr_mask, \
        Lpyr_imgA, Lpyr_imgB, Lpyr_fus, Lpyr_fus_rec = p1_tarea4.run_fusion(imgA[:, :, i], imgB[:, :, i], mask[:, :, i], niveles)
        Gypr_imgA_l.append(Gpyr_imgA)
        Gypr_imgB_l.append(Gpyr_imgB)
        Gypr_mask_l.append(Gpyr_mask)
        Lpyr_imgA_l.append(Lpyr_imgA)
        Lpyr_imgB_l.append(Lpyr_imgB)
        Lpyr_fus_l.append(Lpyr_fus)
        Lpyr_fus_rec_l.append(Lpyr_fus_rec)

    Gpyr_imgA_stack = []
    Gpyr_imgB_stack = []
    Gpyr_mask_stack = []
    Lpyr_imgA_stack = []
    Lpyr_imgB_stack = []
    Lpyr_fus_stack = []
    for nivel in range(niveles):
        Gpyr_imgA_stack.append(np.stack([Gypr_imgA_l[i][nivel] for i in range(3)], axis=2))
        Gpyr_imgB_stack.append(np.stack([Gypr_imgB_l[i][nivel] for i in range(3)], axis=2))
        Gpyr_mask_stack.append(np.stack([Gypr_mask_l[i][nivel] for i in range(3)], axis=2))
        Lpyr_imgA_stack.append(np.stack([Lpyr_imgA_l[i][nivel] for i in range(3)], axis=2))
        Lpyr_imgB_stack.append(np.stack([Lpyr_imgB_l[i][nivel] for i in range(3)], axis=2))
        Lpyr_fus_stack.append(np.stack([Lpyr_fus_l[i][nivel] for i in range(3)], axis=2))
        Lpyr_fus_stack[nivel] = np.clip(Lpyr_fus_stack[nivel], 0, 1)

    Lpyr_fus_rec_stack = np.stack([Lpyr_fus_rec_l[i] for i in range(3)], axis=2)

    io.imshow(Lpyr_fus_rec_stack)
    plt.show()
