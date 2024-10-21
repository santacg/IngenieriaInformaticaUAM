from Datos import Datos
import EstrategiaParticionado
from Clasificador import ClasificadorNaiveBayes
from sklearn import naive_bayes as nb
import pandas as pd

# dataset = Datos('balloons.csv')
dataset = Datos('Datasets/heart.csv')
print("NOMINAL ATRIBUTOS")
print(dataset.nominalAtributos)
print("DICCIONARIO")
print(dataset.diccionarios)
print("MATRIZ DE DATOS")
print(dataset.datos.head(10))

n_ejecuciones = 5
n_folds = 5
estrategia_simple = EstrategiaParticionado.ValidacionSimple(n_ejecuciones, 0.2)
estrategia_cruzada = EstrategiaParticionado.ValidacionCruzada(n_folds)
clasificador = ClasificadorNaiveBayes()
print(f"Ratio de error de clasificación - ValidacionSimple {n_ejecuciones} ejecuciones: {clasificador.validacion(
    estrategia_simple, dataset, clasificador)}")
print(f"Ratio de error de clasificación - ValidacionCruzada {n_folds} folds: {clasificador.validacion(
    estrategia_cruzada, dataset, clasificador)}")
