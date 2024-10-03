# -*- coding: utf-8 -*-

# coding: utf-8
import pandas as pd


class Datos:

    # Constructor: procesar el fichero para asignar correctamente las variables
    # nominalAtributos, datos y diccionarios
    def __init__(self, nombreFichero):
        self.datos = pd.read_csv(nombreFichero, dtype={'Class': 'object'})

        cols = self.datos.columns

        self.nominalAtributos = []
        self.diccionarios = {}

        for i in range(len(cols)):
            tipo_columna = self.datos[cols[i]].dtype
            if tipo_columna == 'object':
                self.nominalAtributos.append(True)
            elif tipo_columna in ['int64', 'float64']:
                self.nominalAtributos.append(False)
            else:
                raise ValueError(f"El tipo de dato en la columna {cols[i]} no es válido.")

            diccionario = {}
            if self.nominalAtributos[i] is True:
                valores = self.datos[cols[i]].unique()
                for j in range(len(valores)):
                    diccionario[valores[j]] = j

            self.diccionarios[cols[i]] = diccionario

    # Devuelve el subconjunto de los datos cuyos �ndices se pasan como
    # argumento
    def extraeDatos(self, idx):
        return self.datos.iloc[idx]
