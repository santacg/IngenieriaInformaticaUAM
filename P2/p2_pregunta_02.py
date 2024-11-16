# Tratamiento de Señales Visuales/Tratamiento de Señales Multimedia I @ EPS-UAM
# Practica 2: Extraccion, descripcion y correspondencia de caracteristicas locales
# Memoria: codigo de la pregunta 02

# AUTOR1: GARCÍA SANTA, CARLOS
# AUTOR2: GONZÁLEZ GALLEGO, MIGUEL ÁNGEL
# PAREJA/TURNO: 02/NUMERO_TURNO

# librerias y paquetes por defecto
import numpy as np
from p2_tests import test_p2_tarea3


if __name__ == "__main__":
    print("Practica 2 - Tarea 4 - Pregunta 2\n")                

    print("Tests completados = " + str(test_p2_tarea3(imgIdx=0, disptime=0,stop_at_error=False,debug=False,tipoDesc='hist',tipoCorr='mindist'))) #analizar todas las imagenes con descriptor 'hist'
    print("Tests completados = " + str(test_p2_tarea3(imgIdx=0, disptime=0,stop_at_error=False,debug=False,tipoDesc='mag-ori',tipoCorr='mindist'))) #analizar todas las imagenes con descriptor 'mag-ori'