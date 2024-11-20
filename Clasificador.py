from abc import ABCMeta, abstractmethod
import numpy as np
import Datos
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
    # datosTest: matriz numpy con los datos de validaci�n
    # nominalAtributos: array bool con la indicatriz de los atributos nominales
    # diccionario: array de diccionarios de la estructura Datos utilizados para la codificacion de variables discretas
    def clasifica(self, datosTest, nominalAtributos, diccionario):
        return np.array(None)

    def matriz_confusion(self, datos, pred):
        clases_reales = datos['Class'].values
        clases_predichas = pred
        TP = ((clases_reales == 1) & (clases_predichas == 1)).sum()
        TN = ((clases_reales == 0) & (clases_predichas == 0)).sum()
        FP = ((clases_reales == 0) & (clases_predichas == 1)).sum()
        FN = ((clases_reales == 1) & (clases_predichas == 0)).sum()
        return TP, FP, TN, FN

    
    # Obtiene el numero de aciertos y errores para calcular la tasa de fallo
    def error(self, datos, pred):
        # Aqui se compara la prediccion (pred) con las clases reales y se calcula el error
        # devuelve el error
        total_filas = datos.shape[0]
        clases = datos.loc[:, 'Class']

        aciertos = 0
        errores = 0
        for i in range(total_filas):
            clase_predicha = pred[i]
            clase_real = clases.iloc[i]

            if clase_predicha == clase_real:
                aciertos += 1
            else:
                errores += 1

        ratio_error = errores / (aciertos + errores)
        return ratio_error

    # Realiza una clasificacion utilizando una estrategia de particionado determinada
    def validacion(self, particionado, dataset, clasificador, seed=None):
        # Creamos las particiones siguiendo la estrategia llamando a particionado.creaParticiones
        # - Para validacion cruzada: en el bucle hasta nv entrenamos el clasificador con la particion de train i
        # y obtenemos el error en la particion de test i
        # - Para validacion simple (hold-out): entrenamos el clasificador con la particion de train
        # y obtenemos el error en la particion test. Otra opci�n es repetir la validaci�n simple un n�mero especificado de veces, obteniendo en cada una un error. Finalmente se calcular�a la media.
        # devuelve el vector con los errores por cada partici�n

        error = []
        particiones = particionado.creaParticiones(dataset.datos, seed)
        atributos_nominales = dataset.nominalAtributos
        diccionario = dataset.diccionarios

        if isinstance(particionado, ValidacionCruzada):
            num_folds = particionado.folds
            for i in range(num_folds):
                train = dataset.extraeDatos(
                    particiones[i].indicesTrain)
                clasificador.entrenamiento(
                    train, atributos_nominales, diccionario)
                test = dataset.extraeDatos(particiones[i].indicesTest)
                clasificaciones = clasificador.clasifica(
                    test, atributos_nominales, diccionario)

                error.append(clasificador.error(
                    test, clasificaciones))
        else:
            num_ejecuciones = particionado.executions
            for i in range(num_ejecuciones):
                train = dataset.extraeDatos(
                    particiones[i].indicesTrain)
                clasificador.entrenamiento(
                    train, atributos_nominales, diccionario)

                test = dataset.extraeDatos(particiones[i].indicesTest)
                clasificaciones = clasificador.clasifica(
                    test, atributos_nominales, diccionario)

                error.append(clasificador.error(
                    test, clasificaciones))

        return error


##############################################################################
