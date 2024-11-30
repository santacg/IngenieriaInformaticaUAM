# Tratamiento de Señales Visuales/Tratamiento de Señales Multimedia I @ EPS-UAM
# Practica 3: Reconocimiento de escenas con modelos BOW
# Memoria - Pregunta 3.1

# AUTOR1: GONZÁLEZ GALLEGO, MIGUEL ÁNGEL
# AUTOR2: GARCÍA SANTA, CARLOS
# PAREJA/TURNO: 02/NUMERO_TURNO


from p3_tarea2 import obtener_features_hog, obtener_features_tiny
from p3_tarea1 import obtener_bags_of_words, construir_vocabulario
from sklearn.neighbors import KNeighborsClassifier
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score
from p3_utils import load_image_dataset, create_results_webpage
import numpy as np

# Cargamos datos del directorio de imagenes
dataset_path = './datasets/scenes15/' 

# Cargar datos mediante la funcion laod_image_dataset
data = load_image_dataset(dataset_path, load_content = False, max_per_category=200)

# Usamos el test de train_test_split  
X_train, X_test, y_train, y_test = train_test_split(data['filenames'], data['target'], test_size=0.20, random_state=42)

# Apartado 3.1.1
# Características: HOG con parámetro tam=100
hog_features_train = obtener_features_hog(X_train, tamano = 100)
hog_features_test = obtener_features_hog(X_test, tamano = 100)

# Características: Tiny con parámetro tam=64
tiny_features_train = obtener_features_tiny(X_train, tamano = 64)
tiny_features_test = obtener_features_tiny(X_test, tamano = 64)

# Construir vocabulario BOW con tamaño de diccionario de 50
vocab = construir_vocabulario(hog_features_train, vocab_size = 50)

# Convertir a Bag-of-Words
bow_train_hog = obtener_bags_of_words(hog_features_train, vocab = vocab)
bow_test_hog = obtener_bags_of_words(hog_features_test, vocab = vocab)

## Valores al utilizar las características de HOG

# Clasificador KNN
knn = KNeighborsClassifier(n_neighbors=5)
knn.fit(bow_train_hog, y_train)

# Predicciones
y_pred_train_hog = knn.predict(bow_train_hog)
y_pred_test_hog = knn.predict(bow_test_hog)

# Evaluar precisión
train_acc_hog = accuracy_score(y_train, y_pred_train_hog)
test_acc_hog = accuracy_score(y_test, y_pred_test_hog)
print(f"HOG -> Train Rendimiento: {train_acc_hog:.3f}, Test Rendimiento: {test_acc_hog:.3f}")

## Valores al utilizar las características de Tiny

# Repetir para Tiny
bow_train_tiny = np.array(tiny_features_train).squeeze()
bow_test_tiny = np.array(tiny_features_test).squeeze()

# Clasificador KNN
knn = KNeighborsClassifier(n_neighbors=5)
knn.fit(bow_train_tiny, y_train)

# Predicciones
y_pred_train_tiny = knn.predict(bow_train_tiny)
y_pred_test_tiny = knn.predict(bow_test_tiny)

# Evaluar rendimiento
train_acc_tiny = accuracy_score(y_train, y_pred_train_tiny)
test_acc_tiny = accuracy_score(y_test, y_pred_test_tiny)
print(f"Tiny -> Train Rendimiento: {train_acc_tiny:.3f}, Test Rendimiento: {test_acc_tiny:.3f}")

# Apartado 3.1.2
# Variamos el tamaño del diccionario BOW
vocab_sizes = [50, 100, 150, 200]

trains = []
tests = []

for size in vocab_sizes:
    vocab = construir_vocabulario(hog_features_train, vocab_size = size)
    bow_train_hog = obtener_bags_of_words(hog_features_train, vocab = vocab)
    bow_test_hog = obtener_bags_of_words(hog_features_test, vocab = vocab)

    knn.fit(bow_train_hog, y_train)
    trains.append(accuracy_score(y_train, knn.predict(bow_train_hog)))
    tests.append(accuracy_score(y_test, knn.predict(bow_test_hog)))

print(f"Valores obtenidos para los valores {vocab} -> Train {trains}, Test {tests}")


# Apartado 3.1.3
# Variamos el número de vecinos del clasificador KNN con valores impares de 1 a 21
neighbors = range(1, 22, 2)

train_acc_knn = []
test_acc_knn = []

for neig in neighbors:
    knn = KNeighborsClassifier(n_neighbors=neig)
    knn.fit(bow_train_hog, y_train)

    # Calculamos la precisión
    train_acc_knn.append(accuracy_score(y_train, knn.predict(bow_train_hog)))
    test_acc_knn.append(accuracy_score(y_test, knn.predict(bow_test_hog)))

# Imprimimos los valores utilizados y generados
print("Valores de k", list(neighbors))
print("Rendimiento (train)", train_acc_knn)
print("Rendimiento (test)", test_acc_knn)

# Tomamos el valor máximo de K
best_k = neighbors[test_acc_knn.index(max(test_acc_knn))]
print("Valor máximo de K: ", best_k)

# Entrenamos y predecimos con KNN con el mayor valor de K
knn = KNeighborsClassifier(n_neighbors=best_k)
knn.fit(bow_train_hog, y_train)
predicted_categories = knn.predict(bow_test_hog)

# Creamos una página web mostrando los resultados visuales de aciertos/errores
create_results_webpage (train_image_paths= X_train, test_image_paths= X_test,train_labels=y_train, test_labels=y_test,
                                           categories=['Bedroom', 'Coast', 'Forest', 'Highway', 'Industrial',
                                            'InsideCity', 'Kitchen', 'LivingRoom', 'Mountain', 'Office',
                                            'OpenCountry', 'Store', 'Street', 'Suburb', 'TallBuilding'], 
                                            abbr_categories= ['Bed','Cst','For','HWy', 'Ind','Cty','Kit','Liv','Mnt',
                                            'Off','OC','Sto','St','Sub','Bld'], predicted_categories = predicted_categories ,
                                            name_experiment = 'HOG_BOW_KNN')

