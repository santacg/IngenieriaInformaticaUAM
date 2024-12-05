from Datos import Datos
from EstrategiaParticionado import ValidacionSimple
from Clasificador import Clasificador
from ClasificadorAlgoritmoGenetico import ClasificadorAlgoritmoGenetico

datos = Datos("./Datasets/titanic.csv")
df = datos.datos
nom_atributos = datos.nominalAtributos
dic = datos.diccionarios

simple = ValidacionSimple(1)
indices = simple.creaParticiones(df)

indices_train = indices[0].indicesTrain
indices_test = indices[0].indicesTest

datosTrain = datos.extraeDatos(indices_train)
datosTest = datos.extraeDatos(indices_test)

clf = ClasificadorAlgoritmoGenetico()
clf.entrenamiento(datosTrain, nom_atributos, dic)
