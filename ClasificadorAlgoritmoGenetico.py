import numpy as np
from bitarray import bitarray
from Clasificador import Clasificador

class ClasificadorAlgoritmoGenetico(Clasificador):

    def __init__(self, poblacion_size=100, epochs=100, max_reglas=5, elitismo=0.05):
        self.poblacion_size = poblacion_size
        self.epochs = epochs
        self.max_reglas = max_reglas
        self.elitismo = elitismo

    def entrenamiento(self, datosTrain, nominalAtributos, diccionario):
        poblacion = []

        # Obtenemos el número de columnas
        cols_size = datosTrain.shape[1]

        # Para cada columna obtenemos el número de valores únicos
        for i in range(cols_size):
            serie = datosTrain.iloc[:, i]
            # Contamos el número de valores únicos
            valores_unicos = np.unique(serie)
            n_valores_unicos = len(valores_unicos)

            # Añadimos n reglas a cada individuo según el número de valores únicos del atributo
            for individuo in range(self.poblacion_size):
                 
                n_reglas = np.random.randint(0, self.max_reglas)
                for n_regla in range(n_reglas):
                    # Utilizamos un bitarray para representar cada regla
                    regla = bitarray(n_valores_unicos)
                    # Generamos un bit aleatorio

                    poblacion[individuo].append()


