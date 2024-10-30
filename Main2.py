import numpy as np
import pandas as pd
from Datos import Datos
import EstrategiaParticionado
from Clasificador import ClasificadorKNN
from os import listdir

# Datasets a utilizar
datasets = ['heart.csv', 'wdbc.csv']

# Valores de K a probar
K_values = [1, 5, 11, 21]

normalizations = [False, True]

# Número de ejecuciones y folds
n_ejecuciones = 5
n_folds = 5

resultados = []

for archivo in datasets:
    dataset = Datos('Datasets/' + archivo)
    print("NOMINAL ATRIBUTOS")
    print(dataset.nominalAtributos)
    print("DICCIONARIO")
    print(dataset.diccionarios)
    print("MATRIZ DE DATOS")
    print(dataset.datos.head(10))

    for normalizado in normalizations:
        if normalizado:
            normalizacion = 'Normalizado'
        else:
            normalizacion = 'No Normalizado'

        estrategia_simple = EstrategiaParticionado.ValidacionSimple(
            n_ejecuciones, 0.25)
        estrategia_cruzada = EstrategiaParticionado.ValidacionCruzada(n_folds)

        for K in K_values:
            # Validación Simple
            clasificador_simple = ClasificadorKNN(
                K=K, normalize=normalizado)
            errores_simple = clasificador_simple.validacion(
                estrategia_simple, dataset, clasificador_simple)

            # Validación Cruzada
            clasificador_cruzada = ClasificadorKNN(K=K, normalize=normalizado)
            errores_cruzada = clasificador_cruzada.validacion(
                estrategia_cruzada, dataset, clasificador_cruzada)

            resultados.append({
                'Dataset': archivo,
                'Particionado': 'Simple',
                'K': K,
                'Normalizado': normalizacion,
                'Error Promedio': np.mean(errores_simple),
                'Desviación Típica': np.std(errores_simple)
            })
            resultados.append({
                'Dataset': archivo,
                'Particionado': 'Cruzada',
                'K': K,
                'Normalizado': normalizacion,
                'Error Promedio': np.mean(errores_cruzada),
                'Desviación Típica': np.std(errores_cruzada)
            })

df_resultados = pd.DataFrame(resultados)

print(df_resultados)
