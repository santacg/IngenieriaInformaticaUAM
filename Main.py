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

estrategia = EstrategiaParticionado.ValidacionSimple(1, 0.2)
clasificador = ClasificadorNaiveBayes()
print("Error: ")
print(clasificador.validacion(estrategia, dataset, clasificador))

