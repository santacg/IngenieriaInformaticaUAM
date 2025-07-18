import numpy as np
from Clasificador import Clasificador
from sklearn.linear_model import LogisticRegression, SGDClassifier


class ClasificadorRegresionLogistica(Clasificador):
    def __init__(self, epocas=100, aprendizaje=0.1):
        self.epocas = epocas
        self.aprendizaje = aprendizaje


    def entrenamiento(self, datosTrain, nominalAtributos, diccionario):
        # Extraemos las etiquetas y los datos de entrada sin las etiquetas
        target = datosTrain['Class'].values.reshape(-1, 1)
        datosTrain = datosTrain.drop(columns='Class').values

        columnas = datosTrain.shape[1]
        # Inicializamos los pesos de forma aleatoria en el rango -0.5, 0.5
        self.vector_pesos = np.random.uniform(-0.5, 0.5, size=(1, columnas))

         # Entrenamos el modelo durante el número de épocas especificado
        for _ in range(self.epocas):
            # Cálculo del producto punto entre los datos y los pesos
            z = np.dot(datosTrain, self.vector_pesos.T)
            
            # Evitamos el Overflow limitando los valores de z
            z = np.clip(z, -700, 700)

            # Aplicamos la función sigmoide
            exp = np.exp(-z)
            sigma = (1 / (1 + exp))
            
            error = sigma - target
            gradiente = np.dot(datosTrain.T, error) / datosTrain.shape[0]
            
            # Actualizamos los pesos usando el gradiente y la tasa de aprendizaje
            self.vector_pesos -= self.aprendizaje * gradiente.T

        return
    
    def clasifica(self, datosTest, nominalAtributos, diccionario, return_scores=False):
        datosTest = datosTest.drop(columns='Class').values
        # Calculamos el producto punto entre los datos y los pesos entrenados
        z = np.dot(datosTest, self.vector_pesos.T)
        
        # Evitamos el Overflow limitando los valores de z
        z = np.clip(z, -700, 700)
        
        # Aplicamos la función sigmoide
        exp = np.exp(-z)
        scores = (1 / (1 + exp))

        # Clasificamos segun un umbral de 0.5
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
