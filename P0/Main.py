from Datos import Datos
from EstrategiaParticionado import ValidacionSimple
import random

datos = Datos('heart.csv')
print(datos.nominalAtributos)
print(datos.diccionarios)
print(datos.extraeDatos([1, 2, 3, 4]))
