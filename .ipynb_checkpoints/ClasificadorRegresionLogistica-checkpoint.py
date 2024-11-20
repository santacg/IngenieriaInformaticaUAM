import numpy as np
from Clasificador import Clasificador
from sklearn.linear_model import LogisticRegression, SGDClassifier


class ClasificadorRegresionLogistica(Clasificador):
    def __init__(self, epocas=100, aprendizaje=0.1):
        self.epocas = epocas
        self.aprendizaje = aprendizaje


    def entrenamiento(self, datosTrain, nominalAtributos, diccionario):
        target = datosTrain['Class'].values.reshape(-1, 1)
        datosTrain = datosTrain.drop(columns='Class').values

        columnas = datosTrain.shape[1]
        self.vector_pesos = np.random.uniform(-0.5, 0.5, size=(1, columnas))

        for _ in range(self.epocas):
            z = np.dot(datosTrain, self.vector_pesos.T)

            z = np.clip(z, -700, 700)
            exp = np.exp(-z)

            sigma = (1 / (1 + exp))
            error = sigma - target
            gradiente = np.dot(datosTrain.T, error) / datosTrain.shape[0]
            self.vector_pesos -= self.aprendizaje * gradiente.T

        return
    
    def clasifica(self, datosTest, nominalAtributos, diccionario, return_scores=False):
        datosTest = datosTest.drop(columns='Class').values

        z = np.dot(datosTest, self.vector_pesos.T)

        z = np.clip(z, -700, 700)
        exp = np.exp(-z)

        scores = (1 / (1 + exp))

        clasificaciones = np.where(scores >= 0.5, 1, 0)

        return (scores.flatten(), clasificaciones.flatten()) if return_scores else clasificaciones.flatten()


class ClasificadorRegresionLogisticaSK(Clasificador):
    def __init__(self, maxiter=100, aprendizaje=0.01):
       self.modelo = LogisticRegression(max_iter=maxiter, C=aprendizaje) 

    def entrenamiento(self, datosTrain, nominalAtributos, diccionario):
        target = datosTrain['Class'].values
        datosTrain = datosTrain.drop(columns='Class').values

        self.modelo.fit(datosTrain, target)
        return


    def clasifica(self, datosTest, nominalAtributos, diccionario):
        datosTest = datosTest.drop(columns='Class').values
        clasificaciones = self.modelo.predict(datosTest)

        return clasificaciones
        

class ClasificadorSGD(Clasificador):
    def __init__(self, maxiter=100, aprendizaje=0.0001):
        self.modelo = SGDClassifier(max_iter=maxiter, alpha=aprendizaje) 

    def entrenamiento(self, datosTrain, nominalAtributos, diccionario):
        target = datosTrain['Class'].values
        datosTrain = datosTrain.drop(columns='Class').values

        self.modelo.fit(datosTrain, target)
        return


    def clasifica(self, datosTest, nominalAtributos, diccionario):
        datosTest = datosTest.drop(columns='Class').values
        clasificaciones = self.modelo.predict(datosTest)

        return clasificaciones
