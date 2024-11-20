import numpy as np
from Clasificador import Clasificador
from scipy import stats as st

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