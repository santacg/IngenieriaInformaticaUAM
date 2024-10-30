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
        # y obtenemos el error en la particion test. Otra opción es repetir la validación simple un número especificado de veces, obteniendo en cada una un error. Finalmente se calcularía la media.
        # devuelve el vector con los errores por cada partición

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

class ClasificadorNaiveBayes(Clasificador):

    def __init__(self, laplace=False):
        self.priori = {}
        self.verosimilitud = {}
        self.laplace = laplace

    def entrenamiento(self, datosTrain, nominalAtributos, diccionario):
        filas = datosTrain.shape[0]
        columnas = datosTrain.shape[1] - 1

        # Obtenemos las clases únicas         
        clases = datosTrain.loc[:, 'Class']
        clases_unicas, count_clases = np.unique(clases, return_counts=True)

        # Calculamos y guardamos la probabilidad a priori de cada clase
        for idx, clase in enumerate(clases_unicas):
            self.priori[clase] = count_clases[idx] / filas

        # Procesamos cada columna del conjunto de entrenamiento
        for i in range(columnas):
            atributos = datosTrain.iloc[:, i]
            nombre_atributo = datosTrain.columns[i]

            # Si el atributo es categórico
            if nominalAtributos[i]:
                valores_unicos = np.unique(atributos)
                num_valores_unicos = len(valores_unicos)
                if nombre_atributo not in self.verosimilitud:
                    self.verosimilitud[nombre_atributo] = {}

                # Calculamos la verosimilitud de cada valor categórico para cada clase
                for valor in valores_unicos:
                    if valor not in self.verosimilitud[nombre_atributo]:
                        self.verosimilitud[nombre_atributo][valor] = {}
                    for idx_clase, clase in enumerate(clases_unicas):
                        # Contamos las ocurrencias del valor dado en la clase actual
                        count = ((atributos == valor) & (clases == clase)).sum()

                        # Aplicamos Laplace si esta activado
                        if self.laplace:
                            count += 1
                            denominador = count_clases[idx_clase] + num_valores_unicos
                        else:
                            denominador = count_clases[idx_clase]
                            if denominador == 0:  # Evitamos división por cero
                                denominador = 1e-6  

                        # Guardamos la probabilidad condicional de cada valor para cada clase
                        self.verosimilitud[nombre_atributo][valor][clase] = count / denominador
            else:
                # Si el atributo es numerico calculamos media y desviación estandar para cada clase
                if nombre_atributo not in self.verosimilitud:
                    self.verosimilitud[nombre_atributo] = {}
                for idx_clase, clase in enumerate(clases_unicas):
                    valores_clase = atributos[clases == clase]
                    media = np.mean(valores_clase)
                    std_dev = np.std(valores_clase)
                    
                    # Evitamos una desviación estandar cero que pueda causar errores
                    if std_dev == 0:
                        std_dev = 1e-6

                    # Guardamos la media y desviación estandar para el valor de la clase
                    self.verosimilitud[nombre_atributo][clase] = {
                        "media": media, "std_dev": std_dev}

        return    

    def clasifica(self, datosTest, nominalAtributos, diccionario):
        filas = datosTest.shape[0]
        columnas = datosTest.shape[1] - 1

        probabilidades = np.empty((filas, len(self.priori)))  # Matriz para las probabilidades de cada clase
        clases_unicas = list(self.priori.keys())  # Usamos las clases obtenidas en el entrenamiento
        clasificaciones = []

        # Iteramos sobre cada muestra en el conjunto de prueba
        for i in range(filas):
            fila_datos = datosTest.iloc[i]
            for idx, clase in enumerate(clases_unicas):
                # Inicializamos la probabilidad posterior con la probabilidad a priori de la clase
                posteriori = self.priori[clase]
                for j in range(columnas):
                    nombre_atributo = datosTest.columns[j]
                    valor = fila_datos.iloc[j]
                    
                    # Para atributos categóricos
                    if nominalAtributos[j]:
                        if valor in self.verosimilitud[nombre_atributo]:
                            prob_atributo = self.verosimilitud[nombre_atributo][valor].get(clase, 0)
                            posteriori *= prob_atributo
                        else:
                            # Manejamos los valores no vistos en el entrenamiento
                            posteriori *= 0
                    else:
                        # Para atributos numericos calculamos la probabilidad usando distribución normal
                        media = self.verosimilitud[nombre_atributo][clase]['media']
                        std_dev = self.verosimilitud[nombre_atributo][clase]['std_dev']
                        prob_atributo = st.norm.pdf(valor, loc=media, scale=std_dev)
                        posteriori *= prob_atributo

                # Guardamos la probabilidad total para la clase actual
                probabilidades[i][idx] = posteriori

            # Normalizamos las probabilidades y evitamos problemas cuando la suma es cero
            suma_probabilidades = np.sum(probabilidades[i])
            if suma_probabilidades > 0:
                probabilidades[i] /= suma_probabilidades
            else:
                # Distribuimos probabilidades uniformes si todas son cero
                probabilidades[i] = np.full(len(clases_unicas), 1 / len(clases_unicas))
            
            # Asignamos la clase con mayor probabilidad
            clasificaciones.append(clases_unicas[np.argmax(probabilidades[i])])

        return np.array(clasificaciones)

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
