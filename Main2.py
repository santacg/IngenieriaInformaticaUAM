from Datos import Datos
import EstrategiaParticionado
from Clasificador import ClasificadorNaiveBayes
from Clasificador import ClasificadorKNN
from sklearn.preprocessing import StandardScaler
from sklearn.metrics import confusion_matrix, classification_report
import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import seaborn as sns
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

estrategia_simple = EstrategiaParticionado.ValidacionSimple(n_ejecuciones, 0.2)
estrategia_cruzada = EstrategiaParticionado.ValidacionCruzada(n_folds)
clasificador_nb = ClasificadorNaiveBayes()

error_simple = clasificador_nb.validacion(estrategia_simple, dataset, clasificador_nb)
error_cruzada = clasificador_nb.validacion(estrategia_cruzada, dataset, clasificador_nb)

print(f"\nNaive Bayes - Ratio de error de clasificación - ValidacionSimple {n_ejecuciones} ejecuciones: {error_simple}")
print(f"Naive Bayes - Ratio de error de clasificación - ValidacionCruzada {n_folds} folds: {error_cruzada}")
    
estrategia = EstrategiaParticionado.ValidacionCruzada(n_folds)

nominalAtributos = dataset.nominalAtributos
diccionarios = dataset.diccionarios

output_dir = 'heatmaps'

if not os.path.exists(output_dir):
    os.makedirs(output_dir)

for normalizado in [False, True]:
    if normalizado:
        normalizacion = 'Normalizado'
    else:
        normalizacion = 'No Normalizado'

    print(f"\nEstrategia: Validación Cruzada - {normalizacion}")

    estrategia_cruzada = EstrategiaParticionado.ValidacionCruzada(n_folds)
    partitions = estrategia_cruzada.creaParticiones(dataset.datos)

    for K in K_values:
        print(f"\n--- K = {K} ---")

        errores = []
        clasificador = ClasificadorKNN(K=K, normalize=normalizado)

        true_labels = []
        predictions = []

        for i in range(n_folds):
            training_data = dataset.extraeDatos(partitions[i].indicesTrain)
            test_data = dataset.extraeDatos(partitions[i].indicesTest)

            clasificador.entrenamiento(training_data, nominalAtributos, diccionarios)
            classification = clasificador.clasifica(test_data, nominalAtributos, diccionarios)
            error = clasificador.error(test_data, classification)
            errores.append(error)

            true_labels.extend(test_data['Class'].values)
            predictions.extend(classification)

        error_promedio = np.mean(errores)

        print(f"Error promedio con nuestro clasificador: {error_promedio}")

        labels = np.unique(true_labels)
        cm = confusion_matrix(true_labels, predictions, labels=labels)
        print(f"\nMatriz de confusión (K={K}, {normalizacion}):")
        print(cm)

        print(f"\nReporte de clasificación (K={K}, {normalizacion}):")
        print(classification_report(true_labels, predictions))


        plt.figure(figsize=(8, 6))
        sns.heatmap(cm, annot=True, fmt='d', cmap='Blues', xticklabels=labels, yticklabels=labels)
        plt.title(f"Matriz de Confusión (K={K}, {normalizacion})")
        plt.ylabel('Etiqueta Real')
        plt.xlabel('Etiqueta Predicha')

        filename = f"{f_name}_heatmap_K{K}_{normalizacion}.png".replace(" ", "_")
        filepath = os.path.join(output_dir, filename)
        plt.savefig(filepath)
        plt.close()
