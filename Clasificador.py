from abc import ABCMeta, abstractmethod
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

    def error(self, datos, pred):
        total_filas = datos.shape[0]
        clases_reales = datos.loc[:, 'Class']

        aciertos = 0
        errores = 0
        for i in range(total_filas):
            clase_predicha = pred[i]
            clase_real = clases_reales.iloc[i]

            if clase_predicha == clase_real:
                aciertos += 1
            else:
                errores += 1

        ratio_error = errores / (aciertos + errores)
        return ratio_error

    def validacion(self, particionado, dataset, clasificador, seed=None):
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

            error = np.mean(error)

        return error


##############################################################################


class ClasificadorNaiveBayes(Clasificador):

    def __init__(self, laplace=False):
        self.priori = {}
        self.verosimilitud = {}
        self.laplace = laplace

    # TODO: implementar
    def entrenamiento(self, datosTrain, nominalAtributos, diccionario):
        total_filas = datosTrain.shape[0]
        clases_reales = datosTrain.loc[:, 'Class']
        clases_unicas, conteos_clases = np.unique(
            clases_reales, return_counts=True)

        # Calcular probabilidades a priori
        for idx, clase in enumerate(clases_unicas):
            self.priori[clase] = conteos_clases[idx] / total_filas

        # Calcular probabilidades de verosimilitud
        total_columnas = datosTrain.shape[1] - 1
        for i in range(total_columnas):
            atributo_series = datosTrain.iloc[:, i]

            if nominalAtributos[i] is True:
                valores_unicos = np.unique(atributo_series)
                num_valores_unicos = len(valores_unicos)
                for valor in valores_unicos:
                    self.verosimilitud[valor] = {}
                    for idx, clase in enumerate(clases_unicas):
                        conteo = ((atributo_series == valor) & (
                            clases_reales == clase)).sum()

                        if self.laplace is True:
                            conteo += 1
                            denominador = conteos_clases[idx] + \
                                num_valores_unicos
                        else:
                            denominador = conteos_clases[idx]

                        self.verosimilitud[valor][clase] = conteo / denominador
            else:
                nombre_atributo = datosTrain.columns[i]
                self.verosimilitud[nombre_atributo] = {}
                for clase in clases_unicas:
                    valores_clase = atributo_series[clases_reales == clase]
                    media = np.mean(valores_clase)
                    std_dev = np.std(valores_clase)
                    self.verosimilitud[nombre_atributo][clase] = {
                        "media": media, "std_dev": std_dev}

        return

    def clasifica(self, datosTest, nominalAtributos, diccionario):
        total_filas = datosTest.shape[0]
        total_columnas = datosTest.shape[1] - 1
        probabilidades = np.empty((total_filas, len(self.priori)))
        clases_unicas = np.unique(datosTest.loc[:, 'Class'])
        clasificaciones = []

        for i in range(total_filas):
            fila_datos = datosTest.iloc[i]
            for idx, clase in enumerate(self.priori):
                posteriori = self.priori[clase]
                for j in range(total_columnas):
                    valor = fila_datos.iloc[j]
                    if nominalAtributos[j] is True:
                        posteriori *= self.verosimilitud[valor][clase]
                    else:
                        nombre_atributo = datosTest.columns[j]
                        media = self.verosimilitud[nombre_atributo][clase]['media']
                        std_dev = self.verosimilitud[nombre_atributo][clase]['std_dev']
                        posteriori *= st.norm.pdf(
                            valor, loc=media, scale=std_dev)

                probabilidades[i][idx] = posteriori

            probabilidades[i] /= np.sum(probabilidades[i])
            clasificaciones.append(
                clases_unicas[np.argmax(probabilidades[i])])

        return np.array(clasificaciones)
