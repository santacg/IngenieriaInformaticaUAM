# Tratamiento de Señales Visuales/Tratamiento de Señales Multimedia I @ EPS-UAM
# Practica 3: Reconocimiento de escenas con modelos BOW
# Memoria - Pregunta 3.3

# AUTOR1: GONZÁLEZ GALLEGO, MIGUEL ÁNGEL
# AUTOR2: GARCÍA SANTA, CARLOS
# PAREJA/TURNO: 02/NUMERO_TURNO

import numpy as np
from sklearn.model_selection import train_test_split
from sklearn.ensemble import RandomForestClassifier
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

# Mapeo de etiquetas numéricas a nombres de categoría
y_train_names = [categorias[label] for label in y_train]
y_test_names = [categorias[label] for label in y_test]

# Mejor tamaño de vocabulario cálculado anteriormente 
vocab_size = 25

# Construir vocabulario BOW con datos train
vocabulario = construir_vocabulario(hog_features_train, vocab_size, max_iter=10)

# Obtener descriptores BOW para train y test
train_bow = obtener_bags_of_words(hog_features_train, vocabulario)
test_bow = obtener_bags_of_words(hog_features_test, vocabulario)

scores_test = []
predicciones = []

# Elegimos n_estimators como parametro
n_estimators = [10, 20, 50, 100, 200, 500, 1000]

for n_estimator in n_estimators:
    print("Procesando para n_estimators:", n_estimator)
    rf = RandomForestClassifier(n_estimators=n_estimator)
    rf.fit(train_bow, y_train)

    # Score de la clasificación sobre test
    score = rf.score(test_bow, y_test)
    scores_test.append(score)

    # Predicciones 
    prediccion = rf.predict(test_bow)
    predicciones.append(prediccion)

print("Scores test:", scores_test)

# Ploteamos los scores de test
plt.plot(n_estimators, scores_test, label="Clasificador RF")

plt.xlabel("Tamaño de Vocabulario")
plt.ylabel("Precisión de la Clasificación en Test")
plt.title("Precisión Clasificación Test RF vs Parámetro N_estimators")
plt.grid()
plt.legend()
plt.show()

best_n_estimators_idx = np.argmax(scores_test)
predicciones = predicciones[best_n_estimators_idx]
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
    name_experiment='HOG_BOW_RF'
)
