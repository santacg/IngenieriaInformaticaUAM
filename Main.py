import Datos
import numpy as np
from EstrategiaParticionado import ValidacionSimple, ValidacionCruzada
from Clasificador import Clasificador, ClasificadorRegresionLogistica, ClasificadorRegresionLogisticaSK, ClasificadorSGD

datasets = ['Datasets/heart.csv', 'Datasets/iris.csv', 'Datasets/wdbc.csv']

dataset_name = datasets[0]
dataset = Datos.Datos(dataset_name)

data = dataset.datos
nominalAtributos = dataset.nominalAtributos
diccionarios = dataset.diccionarios

print(data)
print(nominalAtributos)
print(diccionarios)

validacion_cruzada = ValidacionCruzada(5)
clasificador = Clasificador()

cl_rl = ClasificadorRegresionLogistica(15, 2)
cl_rl_sk = ClasificadorRegresionLogisticaSK()
cl_sgd_sk = ClasificadorSGD()

print(np.mean(clasificador.validacion(validacion_cruzada, dataset, cl_rl)))
print(np.mean(clasificador.validacion(validacion_cruzada, dataset, cl_rl_sk)))
print(np.mean(clasificador.validacion(validacion_cruzada, dataset, cl_sgd_sk)))

