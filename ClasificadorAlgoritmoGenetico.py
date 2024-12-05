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
        # Generamos la poblacion
        poblacion = []

        # Obtenemos el número de columnas
        cols_size = datosTrain.shape[1]

        valores_unicos = []
        # Calculamos el número de valores únicos para cada atributo
        for i in range(cols_size):
            serie = datosTrain.iloc[:, i]
            valores_unicos.append(len(np.unique(serie)))

        print("Valores únicos para cada atributo:", valores_unicos)
        # Para cada individuo obtenemos sus reglas 
        for _ in range(self.poblacion_size):
            # Establecemos aleatoriamente el número de reglas para el individuo
            n_reglas = np.random.randint(1, self.max_reglas)
            individuo = []
            for n_regla in range(n_reglas):
                reglas = []
                # Para cada atributo generamos una regla
                for i in range(cols_size):
                    # Elegimos un bit aleatoriamente
                    bit_aleatorio = np.random.randint(0, valores_unicos[i])
                    # Construimos el bitarray
                    
                    bits = bitarray(valores_unicos[i])
                    bits[bit_aleatorio] = 1

                    diccionario = {datosTrain.columns[i]: bits}
                    reglas.append(diccionario)

                individuo.append({n_regla: reglas})

            poblacion.append(individuo)
                
        print(poblacion)

