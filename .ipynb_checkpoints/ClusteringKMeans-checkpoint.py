import numpy as np


class ClusteringKMeans:

    def __init__(self, k=3, max_iter=100):
        self.k = k
        self.max_iter = max_iter


    def kmeans(self, data):
        # Eliminamos la columna de la clase y tranfarmamos a array de numpy
        data = data.drop(columns='Class').values

        # Seleccionamos k datos como centroides iniciales
        k_centroides = data[np.random.choice(data.shape[0], self.k)]

        centroides_asignados = None 
        # Iteramos un máximo número de iteraciones
        for _ in range(self.max_iter): 
            k_centroides_prev = k_centroides.copy()
            # Asignamos los centroides a los vectores de datos
            # Obtenemos las distancias de cada vector a cada k_inicial
            centroides_dist = np.empty(shape=(data.shape[0], self.k))
            for idx, k_centroide in enumerate(k_centroides):
                centroides_dist[::, idx] = np.linalg.norm(k_centroide - data, axis=1)

            # Asignamos cada vector a su centroide más cercano 
            centroides_asignados = np.argmin(centroides_dist, axis=1) 

            # Calculamos los nuevos centroides con la media
            for idx in range(self.k):
                # Obtenemos los datos de cada cluster
                cluster = data[np.where(centroides_asignados == idx)]
                # Calculamos la media del cluster en funcion de las filas
                if len(cluster) > 0:
                    k_centroides[idx] = np.mean(cluster, axis=0)
                else:
                    k_centroides[idx] = data[np.random.choice(data.shape[0])]

            # Aplicamos el criterio de convergencia de movimiento minimo de centroides
            convergencia = np.linalg.norm(k_centroides - k_centroides_prev, axis=1)
            if np.max(convergencia) < 1e-8:
                break

        return k_centroides, centroides_asignados
