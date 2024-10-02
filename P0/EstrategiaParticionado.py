from abc import ABCMeta, abstractmethod
import random


class Particion():

    # Esta clase mantiene la lista de índices de Train y Test para cada partición del conjunto de particiones
    def __init__(self):
        self.indicesTrain = []
        self.indicesTest = []

    def __str__(self):
        return f'train: {self.indicesTrain} test: {self.indicesTest}'

#####################################################################################################


class EstrategiaParticionado:

    # Clase abstracta
    __metaclass__ = ABCMeta

    # Atributos: deben rellenarse adecuadamente para cada estrategia concreta. Se pasan en el constructor

    @abstractmethod
    # TODO: esta funcion deben ser implementadas en cada estrategia concreta
    def creaParticiones(self, datos, seed=None):
        return []


#####################################################################################################

class ValidacionSimple(EstrategiaParticionado):

    # Crea particiones segun el metodo tradicional de division de los datos segun el porcentaje deseado y el número de ejecuciones deseado
    # Devuelve una lista de particiones (clase Particion)
    # TODO: implementar

    def __init__(self, nEjecuciones, porcentaje):
        self.particiones = []
        self.nEjecuciones = nEjecuciones
        self.porcentaje = porcentaje

    def creaParticiones(self, datos, seed=None):
        random.seed(seed)

        datos_len = datos.shape[0]
        test_len = round(datos_len * self.porcentaje)
        filas = list(range(datos_len))

        for _ in range(self.nEjecuciones):
            random.seed(random.random())
            secuencia_aleatoria = filas[:]
            random.shuffle(secuencia_aleatoria)

            particion = Particion()
            particion.indicesTest = secuencia_aleatoria[:test_len]
            particion.indicesTrain = secuencia_aleatoria[test_len:]

            self.particiones.append(particion)

        return self.particiones


#####################################################################################################
class ValidacionCruzada(EstrategiaParticionado):

    # Crea particiones segun el metodo de validacion cruzada.
    # El conjunto de entrenamiento se crea con las nfolds-1 particiones y el de test con la particion restante
    # Esta funcion devuelve una lista de particiones (clase Particion)
    # TODO: implementar

    def __init__(self, nFolds):
        self.particiones = []
        self.nFolds = nFolds

    def creaParticiones(self, datos, seed=None):
        random.seed(seed)

        datos_len = datos.shape[0]
        folds_len_base = datos_len // self.nFolds
        resto = datos_len % self.nFolds

        lista_filas = list(range(datos_len))

        indice = 0
        for i in range(self.nFolds):
            folds_len = folds_len_base + 1 if i < resto else folds_len_base

            lista_test = lista_filas[indice:indice + folds_len]
            lista_entranamiento = lista_filas[:indice] + lista_filas[indice + folds_len:]
           
            particion = Particion()
            particion.indicesTest = lista_test
            particion.indicesTrain = lista_entranamiento

            self.particiones.append(particion)

            indice += folds_len

        return self.particiones
