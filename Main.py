import numpy as np
import EstrategiaParticionado
from os import listdir
from Datos import Datos
from Clasificador import ClasificadorNaiveBayes
from sklearn import naive_bayes as nb


# def sklearn_nb(dataset, estrategia, rango, gaussian_nb, categorical_nb):
#     lista_error = []
#     not_nominalAtributos = [not value for value in dataset.nominalAtributos]
#     for i in range(rango):
#         # Extraemos los datos segun los indices
#         train = dataset.extraeDatos(
#             estrategia.particiones[i].indicesTrain)
#         test = dataset.extraeDatos(estrategia.particiones[i].indicesTest)
#
#         # Separamos en atributos nominales y numericos
#         train_categorical = train.loc[:, dataset.nominalAtributos].copy()
#         test_categorical = test.loc[:, dataset.nominalAtributos].copy()
#         train_numerical = train.loc[:, not_nominalAtributos]
#         test_numerical = test.loc[:, not_nominalAtributos]
#
#         # Separamos el target del split de datos
#         target_train = train.loc[:, 'Class']
#         target_test = test.loc[:, 'Class']
#
#         # Eliminamos la columna Class de los datos categóricos
#         train_categorical = train_categorical.drop(
#             'Class', axis=1, errors='ignore')
#         test_categorical = test_categorical.drop(
#             'Class', axis=1, errors='ignore')
#
#         # Codificamos los atributos categóricos si existen
#         if train_categorical is not None and test_categorical is not None:
#             for col, encoding in dataset.diccionarios.items():
#                 if encoding and col in train_categorical.columns:
#                     train_categorical[col] = train_categorical[col].map(
#                         encoding)
#                     test_categorical[col] = test_categorical[col].map(encoding)
#
#             # Entrenamos el modelo categórico
#             if not train_categorical.empty:
#                 categorical_nb.fit(train_categorical, target_train)
#
#         # Entrenamos el modelo Gaussiano si hay datos numéricos
#         if train_numerical is not None and test_numerical is not None:
#             gaussian_nb.fit(train_numerical, target_train)
#
#         # Calcular la tasa de error
#         error = 0
#         num_scores = 0
#
#         if not test_numerical.empty:
#             error += 1 - gaussian_nb.score(test_numerical, target_test)
#             num_scores += 1
#
#         if not test_categorical.empty:
#             error += 1 - categorical_nb.score(test_categorical, target_test)
#             num_scores += 1
#
#         if num_scores > 0:
#             error /= num_scores
#
#         lista_error.append(error)
#
#     return lista_error
#

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
    clasificador = ClasificadorNaiveBayes(laplace=True)

# Implementacion propia
# Con correccion de Laplace
    print("NB propio con correccion de Laplace:")
    print(f"Ratio de error de clasificación - ValidacionSimple {n_ejecuciones} ejecuciones: {clasificador.validacion(
        estrategia_simple, dataset, clasificador)}")
    print(f"Ratio de error de clasificación - ValidacionCruzada {n_folds} folds: {clasificador.validacion(
        estrategia_cruzada, dataset, clasificador)}")

# Sin correccion de Laplace
    clasificador.laplace = False
    print("NB propio sin correccion de Laplace:")
    print(f"Ratio de error de clasificación - ValidacionSimple {n_ejecuciones} ejecuciones: {clasificador.validacion(
        estrategia_simple, dataset, clasificador)}")
    print(f"Ratio de error de clasificación - ValidacionCruzada {n_folds} folds: {clasificador.validacion(
        estrategia_cruzada, dataset, clasificador)}")

    # gaussian_nb = nb.GaussianNB()
    # categorical_nb = nb.CategoricalNB()
    #
    # validacion_simple_error = sklearn_nb(
    #     dataset, estrategia_simple, n_ejecuciones, gaussian_nb, categorical_nb)
    # validacion_cruzada_error = sklearn_nb(
    #     dataset, estrategia_cruzada, n_folds, gaussian_nb, categorical_nb)
    #
    # validacion_simple_error = np.mean(validacion_simple_error)
    #
    # print("NB sklearn con correccion de Laplace:")
    # print(
    #     f"Ratio de error de clasificación - ValidaciónSimple sklearn {n_ejecuciones} ejecuciones: {validacion_simple_error}")
    # print(
    #     f"Ratio de error de clasificación - ValidacionCruzada sklearn {n_folds} folds: {validacion_cruzada_error}")
    #
# Sin correccion de Laplace
    # categorical_nb.alpha = 0
    #
    # validacion_simple_error = sklearn_nb(
    #     dataset, estrategia_simple, n_ejecuciones, gaussian_nb, categorical_nb)
    # validacion_cruzada_error = sklearn_nb(
    #     dataset, estrategia_cruzada, n_folds, gaussian_nb, categorical_nb)
    #
    # validacion_simple_error = np.mean(validacion_simple_error)
    #
    # print("NB sklearn sin correccion de Laplace:")
    # print(
    #     f"Ratio de error de clasificación - ValidaciónSimple sklearn {n_ejecuciones} ejecuciones: {validacion_simple_error}")
    # print(
    #     f"Ratio de error de clasificación - ValidacionCruzada sklearn {n_folds} folds: {validacion_cruzada_error}")
