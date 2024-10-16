from abc import ABCMeta, abstractmethod
from typing import dataclass_transform
import numpy as np
from scipy import stats as st
from EstrategiaParticionado import ValidacionCruzada

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
        return np.array(None)

    # Obtiene el numero de aciertos y errores para calcular la tasa de fallo
    # TODO: implementar
    def error(self, datos, pred):
        # Aqui se compara la prediccion (pred) con las clases reales y se calcula el error
        rows = datos.shape[0]
        class_series = datos.loc[:, 'Class']

        n_succes = 0
        n_error = 0
        for i in range(rows):
            pred_class = int(np.argmax(pred[i]))
            real_class = int(class_series.iloc[i])

            if pred_class == real_class:
                n_succes += 1
            else:
                n_error += 1

        error_ratio = n_error / (n_succes + n_error) 
        return error_ratio

    # Realiza una clasificacion utilizando una estrategia de particionado determinada
    # TODO: implementar esta funcion
    def validacion(self, particionado, dataset, clasificador, seed=None):

        # Creamos las particiones siguiendo la estrategia llamando a particionado.creaParticiones
        # - Para validacion cruzada: en el bucle hasta nv entrenamos el clasificador con la particion de train i
        # y obtenemos el error en la particion de test i
        # - Para validacion simple (hold-out): entrenamos el clasificador con la particion de train
        # y obtenemos el error en la particion test. Otra opción es repetir la validación simple un número especificado de veces, obteniendo en cada una un error. Finalmente se calcularía la media.
        error = []

        partitions = particionado.creaParticiones(dataset.datos, seed)
        nominalAtributos = dataset.nominalAtributos
        diccionarios = dataset.diccionarios

        if type == ValidacionCruzada:
            n_folds = particionado.folds
            for i in range(n_folds):
                training_data = dataset.extraeDatos(partitions[i].indicesTrain)
                clasificador.entrenamiento(training_data, nominalAtributos, diccionarios)

                test_data = dataset.extraeDatos(partitions[i].indicesTest)
                classification = clasificador.clasifica(test_data, nominalAtributos, diccionarios)

                error.append(clasificador.error(test_data, classification))
        else:
            n_executions = particionado.executions;
            for i in range(n_executions):
                training_data = dataset.extraeDatos(partitions[i].indicesTrain)
                clasificador.entrenamiento(training_data, nominalAtributos, diccionarios)

                test_data = dataset.extraeDatos(partitions[i].indicesTest)
                classification = clasificador.clasifica(test_data, nominalAtributos, diccionarios)

                error.append(clasificador.error(test_data, classification))

            error_len = len(error)
            if (error_len > 0):
                error = np.sum(error) / error_len

        return error





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
        cols = datosTrain.shape[1] - 1
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
                    count = series[class_series == class_value]
                    mean = np.mean(count)
                    std_dev = np.std(count)
                    self.verosimilitude[series_name][class_value] = {"mean": mean, "std_dev": std_dev}

        return 

    # TODO: implementar
    def clasifica(self, datosTest, nominalAtributos, diccionario):
        rows = datosTest.shape[0]
        cols = datosTest.shape[1] - 1
        classification = np.empty((rows, len(self.priori)))

        for i in range(rows):
            row = datosTest.iloc[i]
            for idx, priori in enumerate(self.priori):
                posteriori = self.priori[priori]
                for j in range(cols):
                    value = row.iloc[j]
                    if nominalAtributos[j] is True:
                        posteriori *= self.verosimilitude[value][priori]
                    else:
                        attribute = datosTest.columns[j]
                        mean = self.verosimilitude[attribute][priori]['mean']
                        std_dev = self.verosimilitude[attribute][priori]['std_dev']
                        posteriori *= st.norm.pdf(value, loc=mean, scale=std_dev)

                classification[i][idx] = posteriori

            classification[i] /= np.sum(classification[i])

        return classification

