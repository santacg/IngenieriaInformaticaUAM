from abc import ABCMeta, abstractmethod
import random
import numpy as np


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

    def __init__(self, n_executions=5, percentage=0.25):
        self.particiones = []
        self.executions = n_executions
        self.percentage = percentage

    def creaParticiones(self, datos, seed=None):
        np.random.seed(seed)

        data_len = datos.shape[0]
        test_len = round(data_len * self.percentage)
        data_rows = np.arange(data_len)

        for _ in range(self.executions):
            np.random.shuffle(data_rows)

            particion = Particion()
            particion.indicesTest = data_rows[:test_len].tolist()
            particion.indicesTrain = data_rows[test_len:].tolist()

            self.particiones.append(particion)

        return self.particiones


#####################################################################################################
class ValidacionCruzada(EstrategiaParticionado):

    # Crea particiones segun el metodo de validacion cruzada.
    # El conjunto de entrenamiento se crea con las nfolds-1 particiones y el de test con la partición restante
    # Esta funcion devuelve una lista de particiones (clase Particion)
    # TODO: implementar

    def __init__(self, n_folds=5):
        self.particiones = []
        self.folds = n_folds

    def creaParticiones(self, datos, seed=None):
        data_len = datos.shape[0]
        folds_len_base = data_len // self.folds
        remainder = data_len % self.folds

        rows = np.arange(data_len)

        index = 0
        for i in range(self.folds):
            folds_len = folds_len_base + 1 if i < remainder else folds_len_base

            test_rows = rows[index:index + folds_len]
            train_rows = np.concatenate(
                (rows[:index], rows[index + folds_len:]))

            particion = Particion()
            particion.indicesTest = test_rows
            particion.indicesTrain = train_rows.tolist()

            self.particiones.append(particion)

            index += folds_len

        return self.particiones
