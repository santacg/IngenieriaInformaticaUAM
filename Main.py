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

    # print("Ejecutando KNN:")
    # print(np.mean(clasificador.validacion(validacion_cruzada, dataset, knn)))
    # print("Ejecutando regresión logística:")
    # print(np.mean(clasificador.validacion(validacion_cruzada, dataset, cl_rl)))
    # print(np.mean(clasificador.validacion(validacion_cruzada, dataset, cl_rl_sk)))
    # print(np.mean(clasificador.validacion(validacion_cruzada, dataset, cl_sgd_sk)))
    #
    kmeans = ClusteringKMeans()
    centroides, asignaciones = kmeans.kmeans(dataset.datos)

    dataset.datos = dataset.datos.drop(columns='Class').values
    plt.figure(figsize=(8, 6))
    for cluster_id in range(len(centroides)):
        cluster_points = dataset.datos[asignaciones == cluster_id]
        print(cluster_points.shape)
        plt.scatter(cluster_points[:, 0], cluster_points[:, 1], label=f'Cluster {cluster_id}')

    plt.scatter(centroides[:, 0], centroides[:, 1], color='red', marker='x', s=200, label='Centroids')

    plt.xlabel('Feature 1')
    plt.ylabel('Feature 2')
    plt.title('K-Means Clustering Results')
    plt.legend()
    plt.show()

