from Datos import Datos
import EstrategiaParticionado
from EstrategiaParticionado import ValidacionSimple, ValidacionCruzada

datos = Datos('balloons.csv')

lista_particiones_simples = ValidacionSimple.creaParticiones(
    EstrategiaParticionado, datos.datos, 0.2, 5, 0)

for i in range(len(lista_particiones_simples)):
    print(datos.extraeDatos(lista_particiones_simples[i].indicesTest))
    print(datos.extraeDatos(lista_particiones_simples[i].indicesTrain))

lista_particiones_cruzadas = ValidacionCruzada.creaParticiones(
    EstrategiaParticionado, datos.datos, 4, 0)

print("Particion cruzada: ")
for i in range(len(lista_particiones_cruzadas)):
    print("Particion test: ")
    print(datos.extraeDatos(lista_particiones_cruzadas[i].indicesTest))
    print("Particion entrenamiento: ")
    print(datos.extraeDatos(lista_particiones_cruzadas[i].indicesTrain))
