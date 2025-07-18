import numpy as np
from Clasificador import Clasificador
from Datos import estandarizarDatos

class ClasificadorKNN(Clasificador):
    def __init__(self, K=3, normalize=True):
        self.K = K
        self.normalize = normalize

    def entrenamiento(self, datosTrain, nominalAtributos, diccionario):
        self.media_vals = -1
        self.std_vals = -1

        # Estandarizamos o asignamos directamente los datos de entrenamiento
        if self.normalize:
            self.training_data, self.media_vals, self.std_vals = estandarizarDatos(
                datosTrain, nominalAtributos, diccionario)
        else:
            self.training_data = datosTrain

        return

    def clasifica(self, datosTest, nominalAtributos, diccionario):
        # Aplicamos la misma transformaci�n que en entrenamiento
        if self.normalize:
            datosTest, _, _ = estandarizarDatos(
                datosTest, nominalAtributos, diccionario, self.media_vals, self.std_vals)

        # Extraemos las caracter�sticas y etiquetas
        training_features = self.training_data.drop(columns=['Class']).values
        test_features = datosTest.drop(columns=['Class']).values
        training_labels = self.training_data['Class'].values

        predictions = []

        # Recorremos cada instancia de prueba
        for test_instance in test_features:
            test_instance = test_instance.reshape(1, -1)
            # Calculamos distancias euclidias a todas las instancias de entrenamiento
            distances = np.linalg.norm(training_features - test_instance, axis=1)

            # Seleccionamos los �ndices de los K vecinos m�s cercanos
            neighbor_indices = distances.argsort()[:self.K]

            # Obtenemos las clases de los vecinos
            neighbor_classes = training_labels[neighbor_indices]

            # Contamos las ocurrencias de cada clase entre los vecinos
            counts = np.bincount(neighbor_classes)
            prediction = np.argmax(counts)  # Clase con m�s ocurrencias

            predictions.append(prediction)

        return np.array(predictions)