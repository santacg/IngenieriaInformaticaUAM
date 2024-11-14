from abc import ABCMeta, abstractmethod
import numpy as np
from scipy import stats as st
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

    
    def clasifica(self, datosTest, nominalAtributos, diccionario, return_scores=False):
        # Estandarizar los datos
        datosTest = datosTest.drop(columns='Class')
        datosTest, _, _ = Datos.estandarizarDatos(datosTest, nominalAtributos, diccionario)
        
        filas = datosTest.shape[0]
        scores = np.zeros(filas)
        clasificaciones = np.zeros(filas, dtype=int)
        
        for i in range(filas):
            # Cálculo del score (probabilidad)
            z = np.dot(self.vector_pesos, datosTest.iloc[i])
            sigma = 1 / (1 + np.exp(-z))
            scores[i] = sigma
            
            # Clasificación binaria
            clasificaciones[i] = 1 if sigma >= 0.5 else 0
        
        if return_scores:
            return scores
        else:
            return clasificaciones



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

class ClasificadorNaiveBayes(Clasificador):

    def __init__(self, laplace=0):
        self.laplace = laplace

    def entrenamiento(self, datosTrain, nominalAtributos, diccionario):
        filas = datosTrain.shape[0]
        columnas = datosTrain.shape[1] - 1

        # Obtenemos las clases �nicas
        clases = datosTrain.loc[:, 'Class']
        clases_unicas, count_clases = np.unique(clases, return_counts=True)

        self.priori = {}
        self.verosimilitud = {}

        # Calculamos y guardamos la probabilidad a priori de cada clase
        for idx, clase in enumerate(clases_unicas):
            self.priori[clase] = count_clases[idx] / filas

        # Procesamos cada columna del conjunto de entrenamiento
        for i in range(columnas):
            atributos = datosTrain.iloc[:, i]
            nombre_atributo = datosTrain.columns[i]

            # Si el atributo es categ�rico
            if nominalAtributos[i]:
                valores_unicos = np.unique(atributos)
                num_valores_unicos = len(valores_unicos)
                if nombre_atributo not in self.verosimilitud:
                    self.verosimilitud[nombre_atributo] = {}

                # Calculamos la verosimilitud de cada valor categ�rico para cada clase
                for valor in valores_unicos:
                    if valor not in self.verosimilitud[nombre_atributo]:
                        self.verosimilitud[nombre_atributo][valor] = {}
                    for idx_clase, clase in enumerate(clases_unicas):
                        # Contamos las ocurrencias del valor dado en la clase actual
                        count = ((atributos == valor) &
                                 (clases == clase)).sum()

                        # Aplicamos Laplace si esta activado
                        if self.laplace != 0:
                            count += self.laplace
                            denominador = count_clases[idx_clase] + \
                                (num_valores_unicos * self.laplace)
                        else:
                            denominador = count_clases[idx_clase]
                            if denominador == 0:  # Evitamos divisi�n por cero
                                denominador = 1e-6

                        # Guardamos la probabilidad condicional de cada valor para cada clase
                        self.verosimilitud[nombre_atributo][valor][clase] = count / denominador
            else:
                # Si el atributo es numerico calculamos media y desviaci�n estandar para cada clase
                if nombre_atributo not in self.verosimilitud:
                    self.verosimilitud[nombre_atributo] = {}
                for idx_clase, clase in enumerate(clases_unicas):
                    valores_clase = atributos[clases == clase]
                    media = np.mean(valores_clase)
                    std_dev = np.std(valores_clase)

                    # Evitamos una desviaci�n estandar cero que pueda causar errores
                    if std_dev == 0:
                        std_dev = 1e-6

                    # Guardamos la media y desviaci�n estandar para el valor de la clase
                    self.verosimilitud[nombre_atributo][clase] = {
                        "media": media, "std_dev": std_dev}

        return

    def clasifica(self, datosTest, nominalAtributos, diccionario):
        filas = datosTest.shape[0]
        columnas = datosTest.shape[1] - 1

        # Matriz para las probabilidades de cada clase
        probabilidades = np.empty((filas, len(self.priori)))
        # Usamos las clases obtenidas en el entrenamiento
        clases_unicas = list(self.priori.keys())
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

                    # Para atributos categ�ricos
                    if nominalAtributos[j]:
                        if valor in self.verosimilitud[nombre_atributo]:
                            prob_atributo = self.verosimilitud[nombre_atributo][valor].get(
                                clase, 0)
                            posteriori *= prob_atributo
                        else:
                            # Manejamos los valores no vistos en el entrenamiento
                            posteriori *= 0
                    else:
                        # Para atributos numericos calculamos la probabilidad usando distribuci�n normal
                        media = self.verosimilitud[nombre_atributo][clase]['media']
                        std_dev = self.verosimilitud[nombre_atributo][clase]['std_dev']
                        prob_atributo = st.norm.pdf(
                            valor, loc=media, scale=std_dev)
                        posteriori *= prob_atributo

                # Guardamos la probabilidad total para la clase actual
                probabilidades[i][idx] = posteriori

            # Normalizamos las probabilidades y evitamos problemas cuando la suma es cero
            suma_probabilidades = np.sum(probabilidades[i])
            if suma_probabilidades > 0:
                probabilidades[i] /= suma_probabilidades
            else:
                probabilidades[i] = 0

            # Asignamos la clase con mayor probabilidad
            clasificaciones.append(clases_unicas[np.argmax(probabilidades[i])])

        return np.array(clasificaciones)


class ClasificadorKNN(Clasificador):
    def __init__(self, K=3, normalize=True):
        self.K = K
        self.normalize = normalize

    def entrenamiento(self, datosTrain, nominalAtributos, diccionario):
        self.media_vals = -1
        self.std_vals = -1

        # Estandarizamos o asignamos directamente los datos de entrenamiento
        if self.normalize:
            self.training_data, self.media_vals, self.std_vals = Datos.estandarizarDatos(
                datosTrain, nominalAtributos, diccionario)
        else:
            self.training_data = datosTrain

        return

    def clasifica(self, datosTest, nominalAtributos, diccionario):
        # Aplicamos la misma transformaci�n que en entrenamiento
        if self.normalize:
            datosTest, _, _ = Datos.estandarizarDatos(
                datosTest, nominalAtributos, diccionario, self.media_vals, self.std_vals)

        # Extraemos las caracter�sticas y etiquetas
        training_features = self.training_data.drop(columns=['Class']).values
        test_features = datosTest.drop(columns=['Class']).values
        training_labels = self.training_data['Class'].values

        predictions = []
        # Recorremos cada instancia de prueba
        for test_instance in test_features:
            # Calculamos distancias euclidianas a todas las instancias de entrenamiento
            distances = []
            for train_feature in training_features:
                diferencia = np.sum((train_feature - test_instance) ** 2)
                raiz = np.sqrt(diferencia)
                distances.append(raiz)

            distances = np.array(distances)
            # Seleccionamos los �ndices de los K vecinos m�s cercanos
            neighbor_indices = distances.argsort()[:self.K]

            # Obtenemos las clases de los vecinos
            neighbor_classes = training_labels[neighbor_indices]

            # Contamos las ocurrencias de cada clase entre los vecinos
            counts = np.bincount(neighbor_classes)
            prediction = counts.argmax()  # Clase con m�s ocurrencias

            predictions.append(prediction)

        return np.array(predictions)

class ClasificadorRegresionLogistica(Clasificador):
    def __init__(self, epocas=1, aprendizaje=1.0):
        self.epocas = epocas
        self.aprendizaje = aprendizaje


    def entrenamiento(self, datosTrain, nominalAtributos, diccionario):
        # Obtenemos el target de los datos de entrenamiento
        target = datosTrain['Class']
        # Estandarizamos los datos
        datosTrain = datosTrain.drop(columns='Class')
        datosTrain, _, _ = Datos.estandarizarDatos(datosTrain, nominalAtributos, diccionario)

        filas = datosTrain.shape[0]
        columnas = datosTrain.shape[1]
        # Inicializamos el vector de pesos a 0 con la dimension de las columnas
        self.vector_pesos = np.zeros((1, columnas))

        # Para cada fila de datos realizamos el ajuste de pesos
        for _ in range(self.epocas):
            for i in range(filas):
                # Obtenci�n del vector de datos y su transpuesta
                vector_datos = datosTrain.iloc[i:i+1]
                vector_datos_t = np.transpose(vector_datos)
                # Multiplicacion escalar entre vector de pesos y vector de datos
                z = np.dot(self.vector_pesos, vector_datos_t)
                # Calculo de funci�n sigmoide
                sigma = float(1 / (1 + np.exp(-z)))
                # Actualizaci�n de vector de pesos
                self.vector_pesos = np.array(self.vector_pesos - (self.aprendizaje * (vector_datos * (sigma - target.iloc[i]))))

        return

    def clasifica(self, datosTest, nominalAtributos, diccionario, return_scores=False):
        # Estandarizamos los datos
        clases_unicas = np.unique(datosTest['Class'])

        datosTest = datosTest.drop(columns='Class')
        datosTest, _, _ = Datos.estandarizarDatos(datosTest, nominalAtributos, diccionario)

        filas = datosTest.shape[0]

        scores = np.zeros(filas)
        clasificaciones = np.zeros(filas, dtype=int)

        for i in range(filas):
            # Vector de datos de la instancia i
            vector_datos = datosTest.iloc[i].values
            # Producto punto entre vector de pesos y vector de datos
            z = np.dot(self.vector_pesos, vector_datos)
            # Cálculo de la función sigmoide
            sigma = float(1 / (1 + np.exp(-z)))
            scores[i] = sigma

            if sigma < 0.5:
                clasificaciones[i] = clases_unicas[0]
            else:
                clasificaciones[i] = clases_unicas[1]

        if return_scores:
            return scores
        else:
            return clasificaciones


