from Datos import Datos
import EstrategiaParticionado
import time as t

dataset = Datos('balloons.csv')
# dataset=Datos('heart.csv')
print("NOMINAL ATRIBUTOS")
print(dataset.nominalAtributos)
print("DICCIONARIO")
print(dataset.diccionarios)
print("MATRIZ DE DATOS")
print(dataset.datos.head(10))

estrategia = EstrategiaParticionado.ValidacionCruzada(10)
# datos se refiere a la matriz numérica calculada en Datos
estrategia.creaParticiones(dataset.datos)
print("VALIDACIÓN CRUZADA")
# particiones contiene la lista de particiones
print("Train-particion 0: ", estrategia.particiones[0].indicesTrain)
print("Test-particion 0: ", estrategia.particiones[0].indicesTest)

estrategia = EstrategiaParticionado.ValidacionSimple(10, 0.3)
# datos se refiere a la matriz numérica calculada en Datos
estrategia.creaParticiones(dataset.datos)
print("VALIDACIÓN SIMPLE")
# particiones contiene la lista de particiones
print("Train-particion 0: ", estrategia.particiones[0].indicesTrain)
print("Test-particion 0: ", estrategia.particiones[0].indicesTest)
