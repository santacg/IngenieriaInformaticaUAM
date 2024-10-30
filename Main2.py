from Datos import Datos
import EstrategiaParticionado
from Clasificador2 import ClasificadorKNN
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

K_values = [1, 5, 15, 25]
n_folds = 5
n_ejecuciones = 5

# Estrategias de validación
estrategia_simple = EstrategiaParticionado.ValidacionSimple(n_ejecuciones, 0.2)
estrategia_cruzada = EstrategiaParticionado.ValidacionCruzada(n_folds)

for normalizado in [False, True]:
    if normalizado:
        normalizacion = 'Normalizado'
    else:
        normalizacion = 'No Normalizado'

    print(f"\nEstrategia: Validación Cruzada - {normalizacion}")

    for K in K_values:
        print(f"\n--- K = {K} ---")

        clasificador_knn = ClasificadorKNN(K=K, normalize=normalizado)

        error = clasificador_knn.validacion(estrategia_cruzada, dataset, clasificador_knn)

        # Calcular el error promedio
        if isinstance(error, list):
            error_promedio = np.mean(error)
        else:
            error_promedio = error

        print(f"Error promedio con nuestro clasificador KNN (K={K}, {normalizacion}): {error_promedio}")

