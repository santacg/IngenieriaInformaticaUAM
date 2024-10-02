from Datos import Datos
import EstrategiaParticionado
import matplotlib.pyplot as plt
import mimesis as mim
import pandas as pd
import time as t


# test de rendimiento
i = 0
filas_list = []
for filas in range(1000, 100000, 10000):
    filas_list.append(filas)

    generic = mim.Generic('es')
    person = mim.Person('es')

    data = {
        'nombre': [person.full_name() for _ in range(filas)],
        'email': [person.email() for _ in range(filas)],
        'telefono': [person.telephone() for _ in range(filas)],
        'peso': [person.weight() for _ in range(filas)],
        'altura': [person.height() for _ in range(filas)]
    }

    df = pd.DataFrame(data)
    name = f'mimesis_{i}.csv'
    df.to_csv(name, index=False)
    i = i + 1

# carga del dataset
datasets = []
dataset_load_times = []
for i in range(i):
    name = f'mimesis_{i}.csv'
    start_time = t.time()
    datasets.append(Datos(name))
    dataset_load_times.append(t.time() - start_time)

plt.plot(dataset_load_times, filas_list)
plt.title("Tiempo de ejecucion constructor de la clase Datos")
plt.xlabel("Número de filas")
plt.ylabel("Tiempo de ejecución (s)")
plt.show()

# rendimiento estrategias de particionado
i = 10
particionado_cruzado_times = []
for i in range(i):
    start_time = t.time()
    estrategia = EstrategiaParticionado.ValidacionCruzada(10)
    estrategia.creaParticiones(datasets[i].datos)
    particionado_cruzado_times.append(t.time() - start_time)

i = 10
particionado_simple_times = []
for i in range(i):
    start_time = t.time()
    estrategia = EstrategiaParticionado.ValidacionSimple(10, 0.3)
    estrategia.creaParticiones(datasets[i].datos)
    particionado_simple_times.append(t.time() - start_time)


name = "Tiempo de ejecucion constructor y creacion de particiones de ValidacionSimple vs ValidacionCruzada"
plt.plot(particionado_simple_times, filas_list)
plt.plot(particionado_cruzado_times, filas_list)
plt.title(name)
plt.xlabel("Número de filas")
plt.ylabel("Tiempo de ejecución (s)")
plt.show()
