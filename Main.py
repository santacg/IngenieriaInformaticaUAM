import Datos
import numpy as np
from EstrategiaParticionado import ValidacionSimple, ValidacionCruzada
from Clasificador import Clasificador, ClasificadorRegresionLogistica, ClasificadorRegresionLogisticaSK, ClasificadorSGD, ClasificadorKNN
from ClusteringKMeans import ClusteringKMeans
import matplotlib.pyplot as plt

datasets = ['Datasets/heart.csv', 'Datasets/iris.csv', 'Datasets/wdbc.csv']

for dataset_name in datasets:
    dataset = Datos.Datos(dataset_name)

    nominalAtributos = dataset.nominalAtributos
    diccionarios = dataset.diccionarios
    dataset.datos, _, _ = Datos.estandarizarDatos(dataset.datos, nominalAtributos, diccionarios)

    print(dataset.datos)
    print(nominalAtributos)
    print(diccionarios)

    validacion_simple = ValidacionSimple(1)
    validacion_cruzada = ValidacionCruzada(5)
    clasificador = Clasificador()

    knn = ClasificadorKNN(11)
    cl_rl = ClasificadorRegresionLogistica(epocas=1000)
    cl_rl_sk = ClasificadorRegresionLogisticaSK()
    cl_sgd_sk = ClasificadorSGD()

    print("Ejecutando KNN:")
    print(np.mean(clasificador.validacion(validacion_cruzada, dataset, knn)))
    if dataset_name != "Datasets/iris.csv":
        print("Ejecutando regresión logística:")
        print(np.mean(clasificador.validacion(validacion_cruzada, dataset, cl_rl)))
        print(np.mean(clasificador.validacion(validacion_cruzada, dataset, cl_rl_sk)))
        print(np.mean(clasificador.validacion(validacion_cruzada, dataset, cl_sgd_sk)))

    print("Ejecutando K-means:")
    kmeans = ClusteringKMeans()
    centroides, asignaciones = kmeans.kmeans(dataset.datos)
    print("Centroides ", centroides)
