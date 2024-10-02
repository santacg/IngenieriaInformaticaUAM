from Datos import Datos
import EstrategiaParticionado
import matplotlib.pyplot as plt
import mimesis as mim
import pandas as pd
import time as t


# test de rendimiento
i = 0
filas_list = []
for filas in range(100000, 1000000, 100000):
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
    name = f'Datasets/mimesis_{i}.csv'
    df.to_csv(name, index=False)
    i = i + 1

n_interations = i
# carga del dataset
datasets = []
dataset_load_times = []
for i in range(n_interations):
    name = f'Datasets/mimesis_{i}.csv'
    start_time = t.time()
    datasets.append(Datos(name))
    dataset_load_times.append(t.time() - start_time)

plt.plot(filas_list, dataset_load_times)
plt.title("Tiempo de ejecucion constructor de la clase Datos")
plt.xlabel("Número de filas")
plt.ylabel("Tiempo de ejecución (s)")
plt.show()

# rendimiento estrategias de particionado
particionado_cruzado_times = []
for i in range(n_interations):
    start_time = t.time()
    estrategia = EstrategiaParticionado.ValidacionCruzada(10)
    estrategia.creaParticiones(datasets[i].datos)
    particionado_cruzado_times.append(t.time() - start_time)

particionado_simple_times = []
for i in range(n_interations):
    start_time = t.time()
    estrategia = EstrategiaParticionado.ValidacionSimple(10, 0.3)
    estrategia.creaParticiones(datasets[i].datos)
    particionado_simple_times.append(t.time() - start_time)


name = "Tiempo de ejecucion constructor y creacion de particiones de ValidacionSimple vs ValidacionCruzada"
plt.plot(filas_list, particionado_simple_times, label="ValidacionSimple")
plt.plot(filas_list, particionado_cruzado_times, label="ValidacionCruzada")
plt.title(name)
plt.xlabel("Número de filas")
plt.ylabel("Tiempo de ejecución (s)")
plt.legend()
plt.show()
