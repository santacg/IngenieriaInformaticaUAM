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
        # Generamos la poblacion
        poblacion = []

        # Obtenemos el número de columnas
        cols_size = datosTrain.shape[1]

        print("Numero de columnas:", cols_size)
        # Para cada individuo obtenemos sus reglas 
        for _ in range(self.poblacion_size):
            # Establecemos aleatoriamente el número de reglas para el individuo
            n_reglas = np.random.randint(1, self.max_reglas + 1)
            individuo = []
            for _ in range(n_reglas):
                regla = bitarray()
                # Para cada atributo generamos una regla
                for _ in range(cols_size):
                    # Elegimos un bit aleatoriamente
                    bit_aleatorio = np.random.choice([0, 1])
                    # Añadimos el bit al bitarray
                    regla.append(bit_aleatorio)

                # Añadimos la regla al conjunto de reglas del inidividuo
                individuo.append(regla)

            # Añadimos el individuo a la población
            poblacion.append(individuo)
            
        return poblacion

    
    def fitness(self, datosTrain, poblacion):
        rows = datosTrain.shape[0]

        fitness_list = []
        for individuo in poblacion:
            n_aciertos = 0
            # Para cada instancia del conjunto de entrenamiento se prueban las reglas de cada individuo
            for i in range(rows):
                instancia = bitarray(datosTrain.iloc[i, :].values.tolist())
                # Comprobamos las reglas del individuo que se activan
                predicciones = []
                for regla in individuo:
                    if (instancia[:-1] & regla[:-1]) == instancia[:-1]:
                        # Si se activa la regla introducimos la predicción
                        predicciones.append(regla[-1])

                if len(predicciones) > 1:
                    # Contamos la predicción más común de las reglas que se han activado
                    prediccion = np.bincount(predicciones).argmax()
                else:
                    prediccion = predicciones[0] if predicciones else None
                   
                # Si se han activado una o más reglas comprobamos la predicción
                if prediccion is not None:
                    if prediccion == instancia[-1]:
                        n_aciertos += 1

            fitness = n_aciertos / rows 
            fitness_list.append(fitness)

        return fitness_list


    def seleccion(self, poblacion, fitness):
        progenitores = []

        # Se calcula el fitness total
        fitness_total = np.sum(fitness)

        # Se calcula la probabilidad de selección proporcional al fitness de cada individuo
        prob_seleccion = [f / fitness_total for f in fitness]
        
        # Calculamos la probabilidad de seleccion acumulada
        prob_acumulada = np.cumsum(prob_seleccion)

        for i in range(len(poblacion)):
            seleccion = np.random.random()
            for i, prob in enumerate(prob_acumulada):
                if seleccion <= prob:
                    progenitores.append(i)
                    break

        return progenitores
    
    def entrenamiento(self, datosTrain, nominalAtributos, diccionario):
        np.random.seed(self.seed)

        # Creación de la primera generación
        poblacion = self.generar_poblacion(datosTrain)

        # Cálculo del fitness 
        fitness = self.fitness(datosTrain, poblacion)

        # Selección de individuos a recombinarse
        seleccion = self.seleccion(poblacion, fitness)
        print(seleccion)
    



