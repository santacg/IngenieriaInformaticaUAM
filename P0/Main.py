from Datos import Datos
import EstrategiaParticionado
from EstrategiaParticionado import ValidacionSimple

datos = Datos('balloons.csv')

lista_particiones_simples = ValidacionSimple.creaParticiones(
    EstrategiaParticionado, datos, 0.2, 5, 0)

for i in range(len(lista_particiones_simples)):
    print(datos.extraeDatos(lista_particiones_simples[i].indicesTest))
    print(datos.extraeDatos(lista_particiones_simples[i].indicesTrain))
