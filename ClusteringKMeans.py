import numpy as np

class ClusteringKMeans:

    def __init__(self, k=3, max_iter=100):
        self.k = k
        self.max_iter = max_iter

    def kmeans(self, data):
        # Eliminamos la columna de la clase y transformamos a array de numpy
        data = data.drop(columns='Class').values

        # Seleccionamos k datos como centroides iniciales
        k_centroides = data[np.random.choice(data.shape[0], self.k, replace=False)]

        centroides_asignados = None
        # Se hacen un número máximo de iteraciones
        for _ in range(self.max_iter):
            k_centroides_prev = k_centroides.copy()
            centroides_asignados_prev = centroides_asignados.copy() if centroides_asignados is not None else None

            # Calculamos las distancias de cada punto a cada centroide
            centroides_dist = np.linalg.norm(data[:, np.newaxis] - k_centroides, axis=2)

            # Asignamos cada punto al centroide más cercano
            centroides_asignados = np.argmin(centroides_dist, axis=1)

            # Calculamos los nuevos centroides
            for idx in range(self.k):
                cluster = data[centroides_asignados == idx]
                if len(cluster) > 0:
                    k_centroides[idx] = np.mean(cluster, axis=0)
                else:
                    # Reasignamos el centroide vacío a un punto aleatorio
                    k_centroides[idx] = data[np.random.choice(data.shape[0])]

            # Aplicamos 2 criterios de convergencia
            # Movimiento mínimo de centroides
            convergencia_centroides = np.linalg.norm(k_centroides - k_centroides_prev, axis=1)
            centroides_estables = np.max(convergencia_centroides) < 1e-4

            # Número mínimo de reasignaciones
            if centroides_asignados_prev is not None:
                cambios_asignaciones = np.sum(centroides_asignados != centroides_asignados_prev)
                asignaciones_estables = cambios_asignaciones = 0
            else:
                asignaciones_estables = False

            # Verificamos si ambos criterios de convergencia se cumplen
            if centroides_estables or asignaciones_estables:
                break

        return k_centroides, centroides_asignados