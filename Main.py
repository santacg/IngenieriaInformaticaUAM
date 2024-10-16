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

estrategia_simple = EstrategiaParticionado.ValidacionSimple(5, 0.2)
estrategia_cruzada = EstrategiaParticionado.ValidacionCruzada(5)
clasificador = ClasificadorNaiveBayes()
print("Error: ")
print(clasificador.validacion(estrategia_simple, dataset, clasificador))

