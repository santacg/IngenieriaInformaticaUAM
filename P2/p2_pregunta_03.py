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

from p2_tests import test_p2_tarea1
from p2_tests import test_p2_tarea2
from p2_tests import test_p2_tarea3

print("Tests completados = " + str(test_p2_tarea1(disptime=-1,stop_at_error=False,debug=False))) #analizar todos los casos sin pararse en errores

print("Tests completados = " + str(test_p2_tarea2(disptime=1,stop_at_error=False,debug=False,tipoDesc='hist'))) #analizar todas las imagenes y esquinas del test, mostrar imagenes con resultados (1 segundo)
print("Tests completados = " + str(test_p2_tarea2(disptime=1,stop_at_error=False,debug=False,tipoDesc='mag-ori'))) #analizar todas las imagenes y esquinas del test, mostrar imagenes con resultados (1 segundo)

print("Tests completados = " + str(test_p2_tarea3(disptime=-1,stop_at_error=False,debug=False,tipoDesc='hist',tipoCorr='mindist'))) #analizar todas las imagenes con descriptor 'hist'
print("Tests completados = " + str(test_p2_tarea3(disptime=1,stop_at_error=False,debug=False,tipoDesc='mag-ori',tipoCorr='mindist'))) #analizar todas las imagenes con descriptor 'mag-ori'


