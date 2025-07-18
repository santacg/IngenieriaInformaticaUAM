# -*- coding: utf-8 -*-

# coding: utf-8
import pandas as pd


def estandarizarDatos(datos, nominalAtributos, diccionarios, media=None, std=None):
    datos = datos.copy()
    # Aseguramos que media_vals y std_vals sean diccionarios
    if media is None:
        media_vals = {}
    else:
        media_vals = media.copy()
    if std is None:
        std_vals = {}
    else:
        std_vals = std.copy()

    for i, column in enumerate(datos.columns):
        if not nominalAtributos[i] and column != 'Class':
            if column not in media_vals or column not in std_vals:
                # Calculamos la media y desviación estándar de la columna
                col_media = datos[column].mean()
                col_std = datos[column].std()
                media_vals[column] = col_media
                std_vals[column] = col_std
            else:
                # Utilizamos la media y desviación estándar proporcionadas
                col_media = media_vals[column]
                col_std = std_vals[column]
            # Estandarizamos la columna
            if col_std != 0:
                datos[column] = (datos[column] - col_media) / col_std
            else:
                datos[column] = 0

    return datos, media_vals, std_vals


class Datos:

    # Constructor: procesar el fichero para asignar correctamente las variables
    # nominalAtributos, datos y diccionarios
    def __init__(self, nombreFichero):
        self.datos = pd.read_csv(nombreFichero)
        self.nominalAtributos = []
        self.diccionarios = {}

        cols = self.datos.columns
        cols_len = len(cols)
        for i in range(cols_len):
            tipo_columna = self.datos[cols[i]].dtype
            if tipo_columna == 'object':
                self.nominalAtributos.append(True)
            elif tipo_columna in ['int64', 'float64']:
                self.nominalAtributos.append(False)
            else:
                raise ValueError(f"El tipo de dato en la columna {cols[i]} no es válido.")

            diccionario = {}
            if self.nominalAtributos[i] is True:
                # Orden lexicográfico de valores únicos
                valores = sorted(self.datos[cols[i]].unique())
                for j in range(len(valores)):
                    diccionario[valores[j]] = j

            self.diccionarios[cols[i]] = diccionario

        for i, column in enumerate(cols):
            if self.nominalAtributos[i]:
                mapping = self.diccionarios[column]
                self.datos[column] = self.datos[column].map(mapping)
                self.datos[column] = self.datos[column].astype(int)

    # Devuelve el subconjunto de los datos cuyos �ndices se pasan como
    # argumento
    def extraeDatos(self, idx):
        return self.datos.iloc[idx]
