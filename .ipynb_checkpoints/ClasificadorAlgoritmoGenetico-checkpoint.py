import enum
import numpy as np
import pandas as pd
from bitarray.util import ba2int
from bitarray import bitarray, bits2bytes
from Clasificador import Clasificador

class ClasificadorAlgoritmoGenetico(Clasificador):

    def __init__(self, poblacion_size=100, epochs=100, max_reglas=5, pmut=0.02, 
                 p_cruce=0.8, elitismo=0.05, atributos_unicos=[], seed=None):
        self.poblacion_size = poblacion_size
        self.epochs = epochs
        self.max_reglas = max_reglas
        self.p_cruce = p_cruce
        self.pmut = pmut
        self.elitismo = elitismo
        self.seed = seed
        self.atributos_unicos = atributos_unicos
        self.longitud_regla = -1

    
    def corregir_regla(self, regla):
        if regla.all():
            regla[np.random.randint(self.longitud_regla)] = 0
        elif not regla.any():
            regla[np.random.randint(self.longitud_regla)] = 1


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

        for individuo in poblacion:
            for regla in individuo:
                self.corregir_regla(regla)
                
        return poblacion


    def fitness(self, datos, poblacion):
        rows = datos.shape[0]

        fitness_list = []
        # Para cada individuo calculamos su fitness
        for individuo in poblacion:
            n_aciertos = 0
            # Para cada instancia del conjunto de entrenamiento se prueban las reglas del individuo
            for i in range(rows):
                instancia = bitarray(datos.iloc[i, :].values.tolist())
                predicciones = []
                # Comprobamos las reglas del individuo que se activan
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


    def seleccion_ruleta_progenitores(self, poblacion, fitness):
        # Método ruleta
        progenitores = []

        # Se calcula el fitness total
        fitness_total = np.sum(fitness)

        # Se calcula la probabilidad de selección proporcional al fitness de cada individuo
        if fitness_total == 0:
            # Asignamos probabilidades iguales si todo el fitness es cero
            prob_seleccion = [1 / len(fitness)] * len(fitness)
        else:
            # Calculamos la probabilidad de selección proporcional al fitness de cada individuo
            prob_seleccion = [f / fitness_total for f in fitness]
            
        # Calculamos la probabilidad de seleccion acumulada
        prob_acumulada = np.cumsum(prob_seleccion)

        # Seleccionamos la mitad de individuos de la poblacion como progenitores
        while len(progenitores) < len(poblacion):
            seleccion = np.random.random()
            # Buscamos el individuo seleccionado según la probabilidad acumulada
            for j, prob in enumerate(prob_acumulada):
                if seleccion <= prob:
                    reglas = []
                    for regla in poblacion[j]:
                        reglas.append(regla.copy())
                    progenitores.append(reglas)
                    break

        return progenitores


    def recombinacion(self, progenitores):
        np.random.shuffle(progenitores)
        descendencia = []

        # Para cada progenitor se busca una pareja para el cruce
        while len(progenitores) > 1: 
            progenitor, pareja = progenitores.pop(), progenitores.pop()

            # Probabilidad de cruce
            if np.random.random() <= self.p_cruce:
                # Se elige una de las reglas para cada individuo 
                regla_progenitor_idx = np.random.randint(len(progenitor))
                regla_pareja_idx = np.random.randint(len(pareja))

                # Se elige un punto de cruce
                pto_cruce = np.random.randint(self.longitud_regla)

                # Realizamos el cruce
                regla_progenitor = progenitor[regla_progenitor_idx]
                regla_pareja = pareja[regla_pareja_idx]

                regla_progenitor[pto_cruce:], regla_pareja[pto_cruce:] = regla_pareja[pto_cruce:], regla_progenitor[pto_cruce:]
                
                progenitor[regla_progenitor_idx] = regla_progenitor
                pareja[regla_pareja_idx] = regla_pareja

            # Añadimos la descendencia
            descendencia.append(progenitor)
            descendencia.append(pareja)

        for individuo in descendencia:
            for regla in individuo:
                self.corregir_regla(regla)

        return descendencia

    
    def mutacion(self, seleccion, pmut):
        descendencia = []
        # Para cada individuo aplicamos la mutación 
        for individuo in seleccion:
            descendiente = []
            # Mutamos todas las reglas
            for regla in individuo:
                # Realizamos la mutación
                regla_mutada = bitarray() 
                for i in range(self.longitud_regla):
                    if np.random.random() < pmut:
                        regla_mutada.append(not regla[i])
                    else:
                        regla_mutada.append(regla[i])

                descendiente.append(regla_mutada)

            descendencia.append(descendiente)
            
        for individuo in descendencia:
            for regla in individuo:
                self.corregir_regla(regla)
                
        return descendencia


    def seleccion_supervivientes(self, datosTrain, poblacion, seleccion, proporcion_elite):
        # Concatenamos la población y la descendencia
        poblacion_completa = poblacion + seleccion

        # Calculamos el fitness de cada individuo
        fitness = self.fitness(datosTrain, poblacion_completa)
        
        # Seleccionamos los mejores individuos de la poblacion y su descendencia
        indices_mejores = (np.argsort(fitness)[::-1])[:self.poblacion_size - proporcion_elite]
        
        nueva_poblacion = [poblacion_completa[i] for i in indices_mejores]
        
        return nueva_poblacion


    def entrenamiento(self, datosTrain, nominalAtributos, diccionario):
        np.random.seed(self.seed)

        # Creación de la primera generación
        poblacion = self.generar_poblacion(datosTrain)

        fitness_medio_list, mejor_fitness_list = [], []

        for i in range(self.epochs):
            # Cálculo del fitness 
            fitness = self.fitness(datosTrain, poblacion)
            fitness_medio_list.append(np.mean(fitness))
            mejor_fitness = max(fitness)
            mejor_fitness_list.append(mejor_fitness)

            print(f"Iteración {i}: Mejor fitness: {mejor_fitness_list[-1]}, Fitness promedio: {fitness_medio_list[-1]}")

            # Selección de la elite
            elite = [poblacion[i] for i in np.argsort(fitness)[::-1][:max(1, int(self.poblacion_size * self.elitismo))]]

            # Selección de individuos a recombinarse
            seleccion = self.seleccion_ruleta_progenitores(poblacion, fitness)

            # Cruce de progenitores 
            seleccion = self.recombinacion(seleccion)

            # Mutacion de la seleccion
            seleccion = self.mutacion(seleccion, pmut=self.pmut)

            # Selección de supervivientes
            poblacion = seleccion
            poblacion.extend(elite)

            np.random.shuffle(poblacion)

        fitness = self.fitness(datosTrain, poblacion)
        mejor_fitness = max(fitness)

        print(f"Mejor Fitness: {mejor_fitness}")
        # Obtención del individuo con más fitness
        self.mejor_individuo = poblacion[np.argmax(fitness)]
        print(f"Mejor Individuo: {self.mejor_individuo}")

        return fitness_medio_list, mejor_fitness_list

    
    def clasifica(self, datosTest, nominalAtributos, diccionario):
        fitness = self.fitness(datosTest, [self.mejor_individuo])
        print(f"Fitness en Test: {fitness}")
        return np.array(fitness)
