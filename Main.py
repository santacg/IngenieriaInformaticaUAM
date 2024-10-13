from Datos import Datos
import EstrategiaParticionado
from Clasificador import ClasificadorNaiveBayes

# dataset = Datos('balloons.csv')
dataset=Datos('Datasets/heart.csv')
print("NOMINAL ATRIBUTOS")
print(dataset.nominalAtributos)
print("DICCIONARIO")
print(dataset.diccionarios)
print("MATRIZ DE DATOS")
print(dataset.datos.head(10))

# estrategia = EstrategiaParticionado.ValidacionCruzada(10)
# # datos se refiere a la matriz numérica calculada en Datos
# estrategia.creaParticiones(dataset.datos)
# print("VALIDACIÓN CRUZADA")
# # particiones contiene la lista de particiones
# print("Train-particion 0: ", estrategia.partitions[0].indicesTrain)
# print("Test-particion 0: ", estrategia.partitions[0].indicesTest)
#
estrategia = EstrategiaParticionado.ValidacionSimple(1, 0.2)
# datos se refiere a la matriz numérica calculada en Datos
estrategia.creaParticiones(dataset.datos)
print("VALIDACIÓN SIMPLE")
# particiones contiene la lista de particiones
print("Train-particion 0: ", estrategia.partitions[0].indicesTrain)
print("Test-particion 0: ", estrategia.partitions[0].indicesTest)

clasificador = ClasificadorNaiveBayes()
training_index = estrategia.partitions[0].indicesTrain
training_data = dataset.extraeDatos(training_index)
clasificador.entrenamiento(training_data, dataset.nominalAtributos, dataset.diccionarios)

test_index = estrategia.partitions[0].indicesTest
test_data = dataset.extraeDatos(test_index)
classification = clasificador.clasifica(test_data, dataset.nominalAtributos, dataset.diccionarios)
print(classification)
print(clasificador.error(test_data, classification))
