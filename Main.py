import Datos
import numpy as np
from EstrategiaParticionado import ValidacionSimple, ValidacionCruzada
from Clasificador import ClasificadorRegresionLogistica

datasets = ['Datasets/heart.csv', 'Datasets/iris.csv', 'Datasets/wdbc.csv']

dataset_name = datasets[0]
dataset = Datos.Datos(dataset_name)

data = dataset.datos
nominalAtributos = dataset.nominalAtributos
diccionarios = dataset.diccionarios

print(data)
print(nominalAtributos)
print(diccionarios)

validacion_cruzada = ValidacionSimple(5, 0.25)
clasificador = ClasificadorRegresionLogistica()
print(np.mean(clasificador.validacion(validacion_cruzada, dataset, clasificador)))

