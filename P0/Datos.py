# -*- coding: utf-8 -*-

# coding: utf-8
import pandas as pd
import numpy as np

class Datos:

    # Constructor: procesar el fichero para asignar correctamente las variables nominalAtributos, datos y diccionarios
    def __init__(self, nombreFichero):       
        df = pd.read_csv(nombreFichero, dtype={'Class': 'object'})

        cols = df.columns

        self.nominalAtributos = []
        self.diccionarios = {}

        for i in range(len(cols)):
            if df[cols[i]].dtype == 'object':
                self.nominalAtributos.append(True)
            else:
                self.nominalAtributos.append(False)
            diccionario = {}

            if self.nominalAtributos[i] is True:
                valores = df[cols[i]].unique()
                for j in range(len(valores)):
                    diccionario[valores[j]] = j


            self.diccionarios[cols[i]] = diccionario

        
    # Devuelve el subconjunto de los datos cuyos �ndices se pasan como argumento
    def extraeDatos(self,idx):
        return 
