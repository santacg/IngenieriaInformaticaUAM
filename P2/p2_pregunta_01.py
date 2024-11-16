# Tratamiento de Señales Visuales/Tratamiento de Señales Multimedia I @ EPS-UAM
# Practica 2: Extraccion, descripcion y correspondencia de caracteristicas locales
# Memoria: codigo de la pregunta 01

# AUTOR1: GARCÍA SANTA, CARLOS
# AUTOR2: GONZÁLEZ GALLEGO, MIGUEL ÁNGEL
# PAREJA/TURNO: 02/NUMERO_TURNO


from skimage.data import camera
import matplotlib.pyplot as plt
from p2_tarea1 import *
from p2_tarea2 import *
from p2_tests import test_p2_tarea2

# Calculo de bins en el intervalo [0, 255]
def calculo_bins(nbins):
    bins = [int(i) for i in np.linspace(0, 255, nbins + 1)]
    return [ f"[{bins[i]},{bins[i+1]})" for i in range(len(bins)) if i + 1 < len(bins)]

# Comparacion y muestra de figuras
def comparacion(coordinatestitle, histogram1, histogram2, histogram3, histogram4, title1, title2, title3, title4, bins12, bins34, rotation = 90):
    
    
    fig, (ax1, ax2, ax3, ax4) = plt.subplots(4,1)
    
    # Titulo con coordenadas
    plt.suptitle(str(coordinatestitle))
    
    
    ax1.bar(bins12, histogram1)
    ax1.title.set_text(title1)
    plt.setp(ax1.get_xticklabels(), rotation=rotation, ha="center")

    ax2.bar(bins12, histogram2)
    ax2.title.set_text(title2)
    plt.setp(ax2.get_xticklabels(), rotation=rotation, ha="right",  rotation_mode="anchor")

    ax3.bar(bins34, histogram3)
    ax3.title.set_text(title3)
    plt.setp(ax3.get_xticklabels(), rotation=rotation, ha="right",  rotation_mode="anchor")

    ax4.bar(bins34, histogram4)
    ax4.title.set_text(title4)
    plt.setp(ax4.get_xticklabels(), rotation=rotation, ha="right",  rotation_mode="anchor")

  
    fig.subplots_adjust(hspace=2)

    plt.show()


def plot_comparacion(lista_indices):
    
    # Imagen a comparar descriptores
    imagen = camera()

    # Normalizamos la imagen 
    imagen = imagen.astype(np.float64) /255.0

    # Tomar las esquinas a partir de detectar puntos de interes
    coords_esquinas = detectar_puntos_interes_harris(imagen)

    # Devolvemos los descriptores de puntos de interes
    descriptores_caso8_16, coordenada = descripcion_puntos_interes(imagen, coords_esquinas, 8, 16, 'hist')
    descriptores_caso8_32, coordenada = descripcion_puntos_interes(imagen, coords_esquinas, 8, 32, 'hist')
    descriptores_caso16_16, _ = descripcion_puntos_interes(imagen, coords_esquinas, 16, 16, 'hist')
    descriptores_caso16_32, _ = descripcion_puntos_interes(imagen, coords_esquinas, 16, 32, 'hist')

    # Comparamos con los descriptores y mostramos en imagen los histogramas
    for i in lista_indices:
        comparacion(coordenada[i], descriptores_caso16_32[i], descriptores_caso8_32[i], descriptores_caso16_16[i], descriptores_caso8_16[i], "nbins = 32, vtam = 16", "nbins = 32, vtam = 8", "nbins = 16, vtam = 16", "nbins = 16, vtam = 8", calculo_bins(32), calculo_bins(16))

# Indice a utilizar en la comparacion
indices = [0, 10, 15, 40]
plot_comparacion(indices)