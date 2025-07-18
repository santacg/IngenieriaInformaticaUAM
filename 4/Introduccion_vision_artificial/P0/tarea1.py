import numpy as np


# Cree un vector de tamaño 10x1 con valores aleatorios
# y muéstrelo por pantalla. Utilice numpy.random.random
vec = np.random.random((10, 1))
print("Vector 10x1 con valores aleatorios: ")
print(vec)

# Posteriormente cree un array bidimensional de ceros
# con tamaño 10x10. Utilice numpy.zeros
zeros_array = np.zeros((10, 10), dtype=np.int8)

# Haga que las posiciones de los extremos del array bidimensional
# (i.e. bordes del array) tengan el valor 1. Utilice indexación de matrices
zeros_array[0::9, :] = 1
zeros_array[:, 0::9] = 1

# Genere un nuevo array bidimensional con los elementos en posiciones con
# índices impares (filas y columnas) del array obtenido en el punto 3. Utilice
# numpy.array con el argumento copy=True e indexación de matrices
odd_array = np.array(zeros_array[1::2, 1::2], copy=True)

# Genere un nuevo array bidimensional con los elementos en posiciones con
# índices pares (filas y columnas) del array obtenido en el punto 3. Utilice
# numpy.array con el argumento copy=True e indexación de matrices
even_array = np.array(zeros_array[0::2, 0::2], copy=True)

# Partiendo del array obtenido en 3), cambie al valor 3 la posición definida
# por la columna tercera y fila segunda. Muestre por pantalla la segunda fila.
zeros_array[1, 2] = 1
print("Segunda fila de array de zeros con segunda fila tercera columna a 1: ")
print(zeros_array[1, :])
