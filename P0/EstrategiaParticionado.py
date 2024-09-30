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
        pass


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
        lista_seeds = []

        for _ in range(self.nEjecuciones):
            lista_seeds.append(random.random())

        datos_len = datos.shape[0]
        test_len = int(datos_len * self.porcentaje)

        lista_filas = set(range(datos_len))

        for i in range(self.nEjecuciones):
            random.seed(lista_seeds[i])

            lista_test = random.sample(range(datos_len), test_len)

            lista_entranamiento = list(lista_filas - set(lista_test))

            particion = Particion()
            particion.indicesTest = sorted(lista_test)
            particion.indicesTrain = sorted(lista_entranamiento)

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
        folds_len = int(datos_len / self.nFolds)

        lista_filas = set(range(datos_len))

        for i in range(self.nFolds):
            lista_test = list(range(folds_len * i, folds_len * (i + 1)))
            lista_entranamiento = list(lista_filas - set(lista_test))

            particion = Particion()
            particion.indicesTest = lista_test
            particion.indicesTrain = lista_entranamiento

            self.particiones.append(particion)

        return self.particiones
