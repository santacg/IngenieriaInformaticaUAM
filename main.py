from Datos import Datos
from Datos import codificacion_one_hot
from EstrategiaParticionado import ValidacionSimple, ValidacionCruzada
from Clasificador import Clasificador
from ClasificadorAlgoritmoGenetico import ClasificadorAlgoritmoGenetico

datos = Datos("./Datasets/balloons.csv")
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

clf = ClasificadorAlgoritmoGenetico(poblacion_size=100, max_reglas=5, epochs=50, seed=42)
clf.entrenamiento(datosTrain, nom_atributos, dic)
