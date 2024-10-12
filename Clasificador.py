from abc import ABCMeta, abstractmethod
from typing import dataclass_transform
import numpy as np
from scipy.stats import norm

class Clasificador:

    # Clase abstracta
    __metaclass__ = ABCMeta

    # Metodos abstractos que se implementan en casa clasificador concreto
    @abstractmethod
    # TODO: esta funcion debe ser implementada en cada clasificador concreto. Crea el modelo a partir de los datos de entrenamiento
    # datosTrain: matriz numpy con los datos de entrenamiento
    # nominalAtributos: array bool con la indicatriz de los atributos nominales
    # diccionario: array de diccionarios de la estructura Datos utilizados para la codificacion de variables discretas
    def entrenamiento(self, datosTrain, nominalAtributos, diccionario):
        pass

    @abstractmethod
    # TODO: esta funcion debe ser implementada en cada clasificador concreto. Devuelve un numpy array con las predicciones
    # datosTest: matriz numpy con los datos de validación
    # nominalAtributos: array bool con la indicatriz de los atributos nominales
    # diccionario: array de diccionarios de la estructura Datos utilizados para la codificacion de variables discretas
    def clasifica(self, datosTest, nominalAtributos, diccionario):
        return

    # Obtiene el numero de aciertos y errores para calcular la tasa de fallo
    # TODO: implementar
    def error(self, datos, pred):
        # Aqui se compara la prediccion (pred) con las clases reales y se calcula el error
        pass

    # Realiza una clasificacion utilizando una estrategia de particionado determinada
    # TODO: implementar esta funcion
    def validacion(self, particionado, dataset, clasificador, seed=None):

        # Creamos las particiones siguiendo la estrategia llamando a particionado.creaParticiones
        # - Para validacion cruzada: en el bucle hasta nv entrenamos el clasificador con la particion de train i
        # y obtenemos el error en la particion de test i
        # - Para validacion simple (hold-out): entrenamos el clasificador con la particion de train
        # y obtenemos el error en la particion test. Otra opción es repetir la validación simple un número especificado de veces, obteniendo en cada una un error. Finalmente se calcularía la media.
        pass

##############################################################################


class ClasificadorNaiveBayes(Clasificador):
    
    def __init__(self):
        self.priori = {}
        self.verosimilitude = {}

    # TODO: implementar
    def entrenamiento(self, datosTrain, nominalAtributos, diccionario):
        rows = datosTrain.shape[0]
        class_series = datosTrain.loc[:, 'Class']
        class_values, class_counts = np.unique(class_series, return_counts=True)

        # Calculate a priori probabilities
        for idx, class_value in enumerate(class_values):
            self.priori[class_value] = class_counts[idx] / rows

        # Calculate verosimilitude probabilities
        cols = datosTrain.shape[1]
        for i in range(cols):
            series = datosTrain.iloc[:, i]

            if nominalAtributos[i] is True:
                unique_values= np.unique(series)
                for unique_value in unique_values:
                    self.verosimilitude[unique_value] = {}
                    for idx, class_value in enumerate(class_values):
                        count = ((series == unique_value) & (class_series == class_value)).sum()
                        self.verosimilitude[unique_value][class_value] = count / class_counts[idx]
            else:
                series_name = datosTrain.columns[i]
                self.verosimilitude[series_name] = {}
                for class_value in class_values:
                    class_data = series[class_series == class_value]
                    mean = np.mean(class_data)
                    std_dev = np.std(class_data)
                    self.verosimilitude[series_name][class_value] = norm(loc=mean, scale=std_dev)

        print(self.verosimilitude)
        return 

    # TODO: implementar
    def clasifica(self, datostest, nominalAtributos, diccionario):
        pass
