# Tratamiento de Señales Visuales/Tratamiento de Señales Multimedia I @ EPS-UAM
# Practica 3: Reconocimiento de escenas con modelos BOW
# Memoria - Pregunta 3.2

# AUTOR1: GONZÁLEZ GALLEGO, MIGUEL ÁNGEL
# AUTOR2: GARCÍA SANTA, CARLOS
# PAREJA/TURNO: 02/NUMERO_TURNO

import numpy as np
from sklearn.model_selection import train_test_split
from sklearn.svm import SVC
import matplotlib.pyplot as plt
from p3_tarea1 import obtener_bags_of_words, construir_vocabulario
from p3_tarea2 import obtener_features_hog
from p3_utils import load_image_dataset, create_results_webpage

categorias = ['Bedroom', 'Coast', 'Forest', 'Highway', 'Industrial',
              'InsideCity', 'Kitchen', 'LivingRoom', 'Mountain', 'Office',
              'OpenCountry', 'Store', 'Street', 'Suburb', 'TallBuilding']

abbr_categorias = ['Bed','Cst','For','HWy', 'Ind','Cty','Kit','Liv','Mnt',
                   'Off','OC','Sto','St','Sub','Bld']

# Leer el dataset
dataset_path = "./datasets/scenes15"
datos = load_image_dataset(
    container_path=dataset_path,
    load_content=False,
    shuffle=False,
    max_per_category=200
)

# Dividir datos en entrenamiento y prueba
X_train, X_test, y_train, y_test = train_test_split(datos['filenames'], datos['target'], test_size=0.20, random_state=42)

# Extraer características HOG de los datos de entrenamiento
hog_features_train = obtener_features_hog(X_train, tamano=100)

# Extraer características HOG de los datos de prueba
hog_features_test = obtener_features_hog(X_test, tamano=100)

scores = {} 

# Mapeo de etiquetas numéricas a nombres de categoría
y_train_names = [categorias[label] for label in y_train]
y_test_names = [categorias[label] for label in y_test]

# Tamaños de vocabulario
vocab_sizes = [5, 10, 25, 50, 100, 200]

# Tipos de kernel a probar
kernels = ["linear", "poly", "rbf"]

for vocab_size in vocab_sizes:
    print("Procesando tamaño del vocabulario:", vocab_size)

    # Construir vocabulario BOW con datos train
    vocabulario = construir_vocabulario(hog_features_train, vocab_size, max_iter=10)

    # Obtener descriptores BOW para train y test
    train_bow = obtener_bags_of_words(hog_features_train, vocabulario)
    test_bow = obtener_bags_of_words(hog_features_test, vocabulario)

    for kernel in kernels:
        # Entrenar SVM
        svm = SVC(kernel=kernel)
        svm.fit(train_bow, y_train)

        # Score de la clasificación
        score = svm.score(test_bow, y_test)
        if kernel not in scores:
            scores[kernel] = []
        scores[kernel].append(score)

        # Predicciones de la clasificación
        predicciones = svm.predict(test_bow)

        # Convertir predicciones a nombres de categoría
        predicciones_nombres = [categorias[pred] for pred in predicciones]

        # Crear página de resultados y matriz de confusión
        confusion = create_results_webpage(
            train_image_paths=X_train, 
            test_image_paths=X_test, 
            train_labels=y_train_names, 
            test_labels=y_test_names,
            categories=categorias, 
            abbr_categories=abbr_categorias, 
            predicted_categories=predicciones_nombres,
            name_experiment='HOG_BOW_SVM'
        )

print(scores)
