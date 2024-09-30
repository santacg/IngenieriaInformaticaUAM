from Datos import Datos
import EstrategiaParticionado
import matplotlib.pyplot as plt
import mimesis as mim
import pandas as pd
import time as t


def generate_graph(name, filas_list, times_list):
    plt.plot(filas_list, times_list)
    plt.title(name)
    plt.xlabel("Número de filas")
    plt.ylabel("Tiempo de ejecución (s)")
    plt.show()


# test de rendimiento
i = 10
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

# carga del dataset
datasets = []
dataset_load_times = []
for i in range(i):
    name = f'mimesis_{i}.csv'
    start_time = t.time()
    datasets.append(Datos(name))
    dataset_load_times.append(t.time() - start_time)

name = "Tiempo de ejecucion constructor de la clase Datos"
generate_graph(name, filas_list, dataset_load_times)

# rendimiento estrategias de particionado
i = 10
particionado_times = []
for i in range(i):
    start_time = t.time()
    estrategia = EstrategiaParticionado.ValidacionCruzada(10)
    estrategia.creaParticiones(datasets[i].datos)
    particionado_times.append(t.time() - start_time)

name = "Tiempo de ejecucion constructor y creacion de particiones de la estrategia de ValidacionCruzada"
generate_graph(name, filas_list, particionado_times)
print(f"tiempo máximo para ValidacionCruzada: {max(particionado_times)}")

i = 10
particionado_times = []
for i in range(i):
    start_time = t.time()
    estrategia = EstrategiaParticionado.ValidacionSimple(10, 0.3)
    estrategia.creaParticiones(datasets[i].datos)
    particionado_times.append(t.time() - start_time)

name = "Tiempo de ejecucion constructor y creacion de particiones de la estrategia de ValidacionSimple"
generate_graph(name, filas_list, particionado_times)
print(f"tiempo máximo para ValidacionSimple: {max(particionado_times)}")
