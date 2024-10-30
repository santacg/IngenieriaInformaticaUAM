from Datos import Datos
import EstrategiaParticionado
from Clasificador import ClasificadorKNN
import numpy as np
import os

f_name = 'heart.csv'
dataset = Datos('Datasets/{}'.format(f_name))

print("NOMINAL ATRIBUTOS")
print(dataset.nominalAtributos)
print("DICCIONARIO")
print(dataset.diccionarios)
print("MATRIZ DE DATOS")
print(dataset.datos.head(10))

n_folds = 5
n_ejecuciones = 5

# Estrategias de validación
estrategia_simple = EstrategiaParticionado.ValidacionSimple(n_ejecuciones, 0.2)
estrategia_cruzada = EstrategiaParticionado.ValidacionCruzada(n_folds)

clasificador_knn = ClasificadorKNN(21)

error = clasificador_knn.validacion(
    estrategia_cruzada, dataset, clasificador_knn)
print(f"Ratio de error: {error}")
