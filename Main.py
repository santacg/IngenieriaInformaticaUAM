import Datos
import numpy as np
from EstrategiaParticionado import ValidacionSimple, ValidacionCruzada
from Clasificador import Clasificador, ClasificadorRegresionLogistica, ClasificadorRegresionLogisticaSK, ClasificadorSGD, ClasificadorKNN
from ClusteringKMeans import ClusteringKMeans
from sklearn.cluster import KMeans
from sklearn.decomposition import PCA
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
    #
    # knn = ClasificadorKNN(11)
    # cl_rl = ClasificadorRegresionLogistica(epocas=1000)
    # cl_rl_sk = ClasificadorRegresionLogisticaSK()
    # cl_sgd_sk = ClasificadorSGD()
    #
    # print("Ejecutando KNN:")
    # print(np.mean(clasificador.validacion(validacion_cruzada, dataset, knn)))
    # if dataset_name != "Datasets/iris.csv":
    #     print("Ejecutando regresión logística:")
    #     print(np.mean(clasificador.validacion(validacion_cruzada, dataset, cl_rl)))
    #     print(np.mean(clasificador.validacion(validacion_cruzada, dataset, cl_rl_sk)))
    #     print(np.mean(clasificador.validacion(validacion_cruzada, dataset, cl_sgd_sk)))

    print("Ejecutando K-means propio:")
    kmeans_sk = KMeans(5)
    kmeans = ClusteringKMeans(5, 1000)

    centroides, asignaciones = kmeans.kmeans(dataset.datos)
    print("Centroides ", centroides)

    pca = PCA(n_components=2)
    data_reducida = pca.fit_transform(dataset.datos.drop(columns='Class').values)
    centroides_reducidos = pca.transform(centroides)

    h = 0.02
    x_min, x_max = data_reducida[:, 0].min() - 1, data_reducida[:, 0].max() + 1
    y_min, y_max = data_reducida[:, 1].min() - 1, data_reducida[:, 1].max() + 1
    xx, yy = np.meshgrid(np.arange(x_min, x_max, h), np.arange(y_min, y_max, h))

    grid_data = np.c_[xx.ravel(), yy.ravel()]

    grid_original = pca.inverse_transform(grid_data)
    distancias_grid = np.linalg.norm(grid_original[:, None] - centroides, axis=2)
    Z = np.argmin(distancias_grid, axis=1)
    Z = Z.reshape(xx.shape)

    plt.figure(1)
    plt.clf()
    plt.imshow(
        Z,
        interpolation="nearest",
        extent=(xx.min(), xx.max(), yy.min(), yy.max()),
        cmap=plt.cm.Paired,
        aspect="auto",
        origin="lower",
    )

    plt.scatter(data_reducida[:, 0], data_reducida[:, 1], c='k', s=10)
    plt.scatter(
        centroides_reducidos[:, 0],
        centroides_reducidos[:, 1],
        marker="x",
        s=169,
        linewidths=3,
        color="w",
        zorder=10,
    )
    plt.title(
        "K-means clustering \n"
    )
    plt.xlim(x_min, x_max)
    plt.ylim(y_min, y_max)
    plt.xticks(())
    plt.yticks(())
    plt.show()

    print("Ejecutando K-means skleanr:")


    reduced_data = PCA(n_components=2).fit_transform(dataset.datos.values)
    kmeans = KMeans(init="k-means++", n_clusters=5)
    kmeans.fit(reduced_data)

# Step size of the mesh. Decrease to increase the quality of the VQ.
    h = 0.02  # point in the mesh [x_min, x_max]x[y_min, y_max].

# Plot the decision boundary. For that, we will assign a color to each
    x_min, x_max = reduced_data[:, 0].min() - 1, reduced_data[:, 0].max() + 1
    y_min, y_max = reduced_data[:, 1].min() - 1, reduced_data[:, 1].max() + 1
    xx, yy = np.meshgrid(np.arange(x_min, x_max, h), np.arange(y_min, y_max, h))

# Obtain labels for each point in mesh. Use last trained model.
    Z = kmeans.predict(np.c_[xx.ravel(), yy.ravel()])
    print(Z)

# Put the result into a color plot
    Z = Z.reshape(xx.shape)
    plt.figure(1)
    plt.clf()
    plt.imshow(
        Z,
        interpolation="nearest",
        extent=(xx.min(), xx.max(), yy.min(), yy.max()),
        cmap=plt.cm.Paired,
        aspect="auto",
        origin="lower",
    )

    plt.plot(reduced_data[:, 0], reduced_data[:, 1], "k.", markersize=2)
# Plot the centroids as a white X
    centroids = kmeans.cluster_centers_
    plt.scatter(
        centroids[:, 0],
        centroids[:, 1],
        marker="x",
        s=169,
        linewidths=3,
        color="w",
        zorder=10,
    )
    plt.title(
        "K-means clustering on the digits dataset (PCA-reduced data)\n"
        "Centroids are marked with white cross"
    )
    plt.xlim(x_min, x_max)
    plt.ylim(y_min, y_max)
    plt.xticks(())
    plt.yticks(())
    plt.show()
