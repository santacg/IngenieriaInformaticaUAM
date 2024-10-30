import numpy as np
import EstrategiaParticionado
from os import listdir
from Datos import Datos
from Clasificador import ClasificadorNaiveBayes
from sklearn import naive_bayes as nb

for dataset in listdir('Datasets/'):
    dataset = Datos('Datasets/' + dataset)
    print("NOMINAL ATRIBUTOS")
    print(dataset.nominalAtributos)
    print("DICCIONARIO")
    print(dataset.diccionarios)
    print("MATRIZ DE DATOS")
    print(dataset.datos.head(10))

    n_ejecuciones = 5
    n_folds = 5
    estrategia_simple = EstrategiaParticionado.ValidacionSimple(
        n_ejecuciones, 0.25)
    estrategia_cruzada = EstrategiaParticionado.ValidacionCruzada(n_folds)
    clasificador = ClasificadorNaiveBayes(laplace=0)

# Naive Bayes
# Con correccion de Laplace
    print("NB propio con correccion de Laplace:")
    print(f"Ratio de error de clasificación - ValidacionSimple {n_ejecuciones} ejecuciones: {clasificador.validacion(
        estrategia_simple, dataset, clasificador)}")
    print(f"Ratio de error de clasificación - ValidacionCruzada {n_folds} folds: {clasificador.validacion(
        estrategia_cruzada, dataset, clasificador)}")

# Sin correccion de Laplace
    clasificador.laplace = 1 
    print("NB propio sin correccion de Laplace:")
    print(f"Ratio de error de clasificación - ValidacionSimple {n_ejecuciones} ejecuciones: {clasificador.validacion(
        estrategia_simple, dataset, clasificador)}")
    print(f"Ratio de error de clasificación - ValidacionCruzada {n_folds} folds: {clasificador.validacion(
        estrategia_cruzada, dataset, clasificador)}")

# KNN
