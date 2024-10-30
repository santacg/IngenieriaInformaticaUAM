# -*- coding: utf-8 -*-

# coding: utf-8
import pandas as pd


class Datos:

    # Constructor: procesar el fichero para asignar correctamente las variables
    # nominalAtributos, datos y diccionarios
    def __init__(self, nombreFichero):
        self.datos = pd.read_csv(nombreFichero, dtype={'Class': 'object'})
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
                raise ValueError(f"El tipo de dato en la columna {
                                 cols[i]} no es válido.")

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


def estandarizarDatos(df, nominalAtributos, diccionarios, media=True, std=True):
    datos = df.copy()
    # Hacemos un mapeado según los valores del diccionario
    for i, column in enumerate(datos.columns):
        if nominalAtributos[i] and column != 'Class':
            mapping = diccionarios[column]
            datos[column] = datos[column].map(
                mapping)

    # Empleamos la media y el desviacion estandar por defecto
    media_vals = {}
    std_vals = {}

    for i, column in enumerate(datos.columns):
        # Solo se estandarizan los atributos numericos
        if not nominalAtributos[i] and column != 'Class':
            # Calculamos la media y la desviación estándar de la columna
            col_media = datos[column].mean() if media else 0
            col_std = datos[column].std() if std else 1

            media_vals[column] = col_media
            std_vals[column] = col_std

            # Estandarizamos la columna
            if col_std != 0:
                datos[column] = (
                    datos[column] - col_media) / col_std
            else:
                # Si la desviación es 0 asignamos un valor constante
                datos[column] = 0

    return datos
