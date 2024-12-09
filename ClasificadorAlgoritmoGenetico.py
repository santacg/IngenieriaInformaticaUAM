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
        self.poblacion = []
        self.longitud_regla = -1


    def generar_poblacion(self, datosTrain):
        # Generamos la poblacion
        poblacion = []

        # Obtenemos el número de columnas
        cols_size = datosTrain.shape[1]
        self.longitud_regla = cols_size

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
            
        self.poblacion = poblacion
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

    
    def cruce(self, progenitores_idx):
        descendencia = []
        # para cada progenitor se busca una pareja para el cruce
        for progenitor_idx in progenitores_idx:
            parejas_disp_idx = progenitores_idx[progenitores_idx != progenitor_idx]
            pareja_idx = np.random.choice(parejas_disp_idx)

            # cruzamos los inidividuos
            progenitor = self.poblacion[progenitor_idx]
            pareja = self.poblacion[pareja_idx]

            # se elige una regla de cada individuo
            regla_progenitor_idx = np.random.randint(low=0, high=len(progenitor))
            regla_pareja_idx = np.random.randint(low=0, high=len(pareja))

            # se elige un punto de cruce
            pto_cruce = np.random.randint(low=0, high=self.longitud_regla+1)

            # realizamos el cruce
            regla_progenitor = progenitor[regla_progenitor_idx]
            regla_pareja = pareja[regla_pareja_idx]

            regla_progenitor[pto_cruce:], regla_pareja[pto_cruce:] = regla_pareja[pto_cruce:], regla_progenitor[pto_cruce:]
            
            progenitor[regla_progenitor_idx] = regla_progenitor
            pareja[regla_pareja_idx] = regla_pareja

            # generamos la descendencia
            descendencia.append(progenitor)
            descendencia.append(pareja)

        return descendencia

    
    def mutacion(self, seleccion, pmut):
        descendencia = []
        # para cada individuo aplicamos la mutación 
        for individuo in seleccion:
            print(individuo)
            # se elige una regla a mutar
            regla_idx = np.random.randint(low=0, high=len(individuo))

            # realizamos la mutación
            regla = individuo[regla_idx]
            for i in range(self.longitud_regla):
                if np.random.random() < pmut:
                    regla[i] = not regla[i]
                

            descendencia.append(individuo)

        return descendencia




    
    def entrenamiento(self, datosTrain, nominalAtributos, diccionario):
        np.random.seed(self.seed)

        # Creación de la primera generación
        poblacion = self.generar_poblacion(datosTrain)

        for _ in range(self.epochs):
            # Cálculo del fitness 
            fitness = self.fitness(datosTrain, poblacion)

            # Selección de individuos a recombinarse
            seleccion = self.seleccion(poblacion, fitness)

            # Cruce de progenitores 
            seleccion = self.cruce(seleccion)

            # Mutacion de la seleccion
            seleccion = self.mutacion(seleccion, 0.05)


