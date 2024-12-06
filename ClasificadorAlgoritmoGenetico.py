import enum
import numpy as np
import pandas as pd
from bitarray.util import ba2int
from bitarray import bitarray
from Clasificador import Clasificador

class ClasificadorAlgoritmoGenetico(Clasificador):

    def __init__(self, poblacion_size=100, epochs=100, max_reglas=5, elitismo=0.05, seed=None):
        self.poblacion_size = poblacion_size
        self.epochs = epochs
        self.max_reglas = max_reglas
        self.elitismo = elitismo
        self.seed = seed


    def generar_poblacion(self, datosTrain):
        np.random.seed(self.seed)

        # Generamos la poblacion
        poblacion = []

        # Obtenemos el número de columnas menos la clase
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
            n_reglas = np.random.randint(1, self.max_reglas + 1)
            individuo = []
            for _ in range(n_reglas):
                reglas = []
                # Para cada atributo generamos una regla
                for i in range(cols_size):
                    # Elegimos un bit aleatoriamente
                    bit_aleatorio = np.random.randint(0, valores_unicos[i])
                    # Construimos el bitarray
                    bits = bitarray(valores_unicos[i])
                    bits[bit_aleatorio] = 1

                    reglas.append(bits)

                individuo.append(reglas)

            poblacion.append(individuo)
                
        return poblacion

    
    def fitness(self, datosTrain, poblacion):
        rows = datosTrain.shape[0]

        fitness = []
        for individuo in poblacion:
            n_aciertos = 0
            n_errores = 0
            for regla in individuo:
                for i in range(rows):
                    instancia = datosTrain.iloc[i, :].values
                    flag = True
                    for atributo_idx, atributo in enumerate(regla):

                        atributo_value = atributo.find(1)

                        if atributo_idx == 0:
                            atributo_value += 1

                        if atributo_value != instancia[atributo_idx]:
                            flag = False
                            break

                    if flag == True:
                        n_aciertos += 1


            fitness.append(n_aciertos / rows)

        return fitness
    
    def entrenamiento(self, datosTrain, nominalAtributos, diccionario):
        print(datosTrain)

        poblacion = self.generar_poblacion(datosTrain)
        fitness = self.fitness(datosTrain, poblacion)
        print(fitness)
