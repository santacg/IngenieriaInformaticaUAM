from abc import ABCMeta, abstractmethod
import numpy as np
from pandas.core.arrays import categorical
from scipy import stats as st
from EstrategiaParticionado import ValidacionCruzada
from sklearn import naive_bayes as nb


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
        class_values = np.unique(class_series)

        n_succes = 0
        n_error = 0
        for i in range(rows):
            pred_class = pred[i]
            real_class = class_series.iloc[i]

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

        if isinstance(particionado, ValidacionCruzada):
            n_folds = particionado.folds
            for i in range(n_folds):
                training_data = dataset.extraeDatos(partitions[i].indicesTrain)
                clasificador.entrenamiento(
                    training_data, nominalAtributos, diccionarios)

                test_data = dataset.extraeDatos(partitions[i].indicesTest)
                classification = clasificador.clasifica(
                    test_data, nominalAtributos, diccionarios)

                error.append(clasificador.error(test_data, classification))
        else:
            n_executions = particionado.executions
            for i in range(n_executions):
                training_data = dataset.extraeDatos(partitions[i].indicesTrain)
                clasificador.entrenamiento(
                    training_data, nominalAtributos, diccionarios)

                test_data = dataset.extraeDatos(partitions[i].indicesTest)
                classification = clasificador.clasifica(
                    test_data, nominalAtributos, diccionarios)

                error.append(clasificador.error(test_data, classification))

            error_len = len(error)
            if (error_len > 0):
                error = np.sum(error) / error_len

        return error


##############################################################################


class ClasificadorNaiveBayes(Clasificador):

    def __init__(self):
        self.priori = {}
        self.verosimilitud = {}

    # TODO: implementar
    def entrenamiento(self, datosTrain, nominalAtributos, diccionario):
        rows = datosTrain.shape[0]
        class_series = datosTrain.loc[:, 'Class']
        class_values, class_counts = np.unique(
            class_series, return_counts=True)

        # Calcular probabilidades a priori
        for idx, class_value in enumerate(class_values):
            self.priori[class_value] = class_counts[idx] / rows

        # Calcular verosimilitudes
        cols = datosTrain.shape[1] - 1
        for i in range(cols):
            series = datosTrain.iloc[:, i]

            if nominalAtributos[i] is True:
                unique_values = np.unique(series)
                total_categories = len(unique_values)
                apply_laplace = False

                for unique_value in unique_values:
                    self.verosimilitud[unique_value] = {}
                    for idx, class_value in enumerate(class_values):
                        count = ((series == unique_value) & (
                            class_series == class_value)).sum()
                        prob = count / class_counts[idx]

                        print(f"Probabilidad de {unique_value} dado {
                              class_value}: {prob:.4f}")

                        if prob == 0:
                            print(f"Aplicando correccion de Laplace para el atributo {
                                  datosTrain.columns[i]}")

                            apply_laplace = True
                        self.verosimilitud[unique_value][class_value] = prob

                if apply_laplace:
                    for unique_value in unique_values:
                        for idx, class_value in enumerate(class_values):
                            count = ((series == unique_value) & (
                                class_series == class_value)).sum()

                            # Aplicar correccion de Laplace
                            laplace_prob = (count + 1) / \
                                (class_counts[idx] + total_categories)
                            print(f"Probabilidad corregida con Laplace de {
                                  unique_value} dado {class_value}: {laplace_prob}")
                            self.verosimilitud[unique_value][class_value] = laplace_prob

            else:
                series_name = datosTrain.columns[i]
                self.verosimilitud[series_name] = {}
                for class_value in class_values:
                    count = series[class_series == class_value]
                    mean = np.mean(count)
                    std_dev = np.std(count)
                    self.verosimilitud[series_name][class_value] = {
                        "mean": mean, "std_dev": std_dev}

        return

    def clasifica(self, datosTest, nominalAtributos, diccionario):
        rows = datosTest.shape[0]
        cols = datosTest.shape[1] - 1
        map = np.empty((rows, len(self.priori)))
        class_values = np.unique(datosTest.loc[:, 'Class'])
        classification = []

        for i in range(rows):
            row = datosTest.iloc[i]
            for idx, priori in enumerate(self.priori):
                posteriori = self.priori[priori]
                for j in range(cols):
                    value = row.iloc[j]
                    if nominalAtributos[j] is True:
                        posteriori *= self.verosimilitud[value][priori]
                    else:
                        attribute = datosTest.columns[j]
                        mean = self.verosimilitud[attribute][priori]['mean']
                        std_dev = self.verosimilitud[attribute][priori]['std_dev']
                        posteriori *= st.norm.pdf(value,
                                                  loc=mean, scale=std_dev)

                map[i][idx] = posteriori

            map[i] /= np.sum(map[i])
            classification.append(class_values[np.argmax(map[i])])

        return np.array(classification)


class ClasificadorKNN(Clasificador):
    def __init__(self, K=3, normalize=True):
        super().__init__()
        self.K = K
        self.training_data = None
        self.normalize = normalize
        self.min_vals = {}
        self.max_vals = {}
        self.nominalAtributos = None
        self.diccionarios = None
        self.class_mapping = {}

    def entrenamiento(self, datosTrain, nominalAtributos, diccionarios):
        self.nominalAtributos = nominalAtributos
        self.diccionarios = diccionarios
        self.training_data = datosTrain.copy()

        for i, column in enumerate(self.training_data.columns):
            if nominalAtributos[i] and column != 'Class':
                mapping = diccionarios[column]
                self.training_data[column] = self.training_data[column].map(
                    mapping)

        if 'Class' in self.training_data.columns:
            mapping = diccionarios['Class']
            self.training_data['Class'] = self.training_data['Class'].map(
                mapping)
            self.class_mapping = {v: k for k, v in mapping.items()}
            self.training_data['Class'] = self.training_data['Class'].astype(
                int)

        if self.normalize:
            # Normalizar x = (x-x_min)/(x_max-x_min)
            for i, column in enumerate(self.training_data.columns):
                if not nominalAtributos[i] and column != 'Class':
                    min_val = self.training_data[column].min()
                    max_val = self.training_data[column].max()
                    self.min_vals[column] = min_val
                    self.max_vals[column] = max_val
                    self.training_data[column] = (
                        self.training_data[column] - min_val) / (max_val - min_val)
        else:
            for i, column in enumerate(self.training_data.columns):
                if not nominalAtributos[i] and column != 'Class':
                    self.min_vals[column] = 0
                    self.max_vals[column] = 1

    def clasifica(self, datosTest, nominalAtributos, diccionarios):
        test_data = datosTest.copy()

        # nominales
        for i, column in enumerate(test_data.columns):
            if nominalAtributos[i] and column != 'Class':
                mapping = diccionarios[column]
                test_data[column] = test_data[column].map(mapping)

        if 'Class' in test_data.columns:
            mapping = diccionarios['Class']
            test_data['Class'] = test_data['Class'].map(mapping)
            test_data['Class'] = test_data['Class'].astype(int)

        if self.normalize:
            for i, column in enumerate(test_data.columns):
                if not nominalAtributos[i] and column != 'Class':
                    min_val = self.min_vals[column]
                    max_val = self.max_vals[column]
                    test_data[column] = (
                        test_data[column] - min_val) / (max_val - min_val)

        training_features = self.training_data.drop(columns=['Class']).values
        test_features = test_data.drop(columns=['Class']).values
        training_labels = self.training_data['Class'].values.astype(int)

        predictions = []
        # distancias euclidianas
        for test_instance in test_features:
            distances = np.sqrt(
                np.sum((training_features - test_instance) ** 2, axis=1))
            neighbor_indices = distances.argsort()[:self.K]
            neighbor_classes = training_labels[neighbor_indices]

            counts = np.bincount(neighbor_classes)
            prediction = counts.argmax()
            prediction_label = self.class_mapping.get(prediction, prediction)
            predictions.append(prediction_label)

        return np.array(predictions)
