import numpy as np
from Datos import Datos
from Datos import codificacion_one_hot
from EstrategiaParticionado import ValidacionSimple, ValidacionCruzada
from Clasificador import Clasificador
from ClasificadorAlgoritmoGenetico import ClasificadorAlgoritmoGenetico

datos = Datos("./Datasets/balloons.csv")

cols = datos.datos.columns 
atributos_unicos = []
print(datos.datos)

for col in cols:
    if col == 'Class':
        break
    atributos_unicos.append(len(np.unique(datos.datos[col])))

print("Atributos", atributos_unicos)

datos.datos = codificacion_one_hot(datos.datos)
print(f"One hot codificado: \n{datos.datos}")
nom_atributos = datos.nominalAtributos
dic = datos.diccionarios

simple = ValidacionSimple(1)
indices = simple.creaParticiones(datos.datos, seed=42)

indices_train = indices[0].indicesTrain
indices_test = indices[0].indicesTest

datosTrain = datos.extraeDatos(indices_train)
datosTest = datos.extraeDatos(indices_test)

clf = ClasificadorAlgoritmoGenetico(poblacion_size=50, max_reglas=5, epochs=50, elitismo=0.02, atributos_unicos=atributos_unicos)
clf.entrenamiento(datosTrain, nom_atributos, dic)
clf.clasifica(datosTest, nom_atributos, dic)
