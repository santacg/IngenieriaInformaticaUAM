# Tratamiento de Señales Visuales/Tratamiento de Señales Multimedia I @ EPS-UAM
# Practica 3: Reconocimiento de escenas con modelos BOW
# Memoria - Pregunta 3.1

# AUTOR1: GONZÁLEZ GALLEGO, MIGUEL ÁNGEL
# AUTOR2: GARCÍA SANTA, CARLOS
# PAREJA/TURNO: 02/NUMERO_TURNO

import numpy as np
from sklearn.model_selection import train_test_split
from sklearn.svm import SVC
from sklearn.metrics import accuracy_score, confusion_matrix
import matplotlib.pyplot as plt
from p3_tarea1 import obtener_bags_of_words, construir_vocabulario
from p3_tarea2 import obtener_features_hog 
from p3_utils import load_image_dataset, create_results_webpage

tam_hog = 100
max_images_per_category = 200
test_ratio = 0.20
vocab_sizes = [50, 100, 150, 200]

dataset_path = "./datasets/scenes15/"  # Ajustar esta ruta según la ubicación del dataset
images = load_image_dataset(
    container_path=dataset_path,
    resize_shape=(tam_hog, tam_hog),
    max_per_category=max_images_per_category,

)
categories =  ['Bedroom', 'Coast', 'Forest', 'Highway', 'Industrial',
                                            'InsideCity', 'Kitchen', 'LivingRoom', 'Mountain', 'Office',
                                            'OpenCountry', 'Store', 'Street', 'Suburb', 'TallBuilding']

# Extraer características HOG
hog_features = [obtener_features_hog(image, tam_hog) for image in images]

# Dividir datos en entrenamiento y prueba
X_train, X_test, y_train, y_test, train_image_paths, test_image_paths = train_test_split(
    hog_features, test_size=test_ratio, random_state=42
)

train_accuracies = []
test_accuracies = []

# Experimento con diferentes tamaños de vocabulario
for vocab_size in vocab_sizes:
    print(f"Procesando tamaño del vocabulario: {vocab_size}")

    # Construir vocabulario BOW
    vocabulario = construir_vocabulario(X_train, vocab_size)

    # Convertir características HOG a BOW
    X_train_bow = obtener_bags_of_words(X_train, vocabulario)
    X_test_bow = obtener_bags_of_words(X_test, vocabulario)

    # Entrenar SVM 
    clf = SVC(kernel="linear", max_iter=10)
    clf.fit(X_train_bow, y_train)

    train_acc = accuracy_score(y_train, clf.predict(X_train_bow))
    test_predictions = clf.predict(X_test_bow)
    test_acc = accuracy_score(y_test, test_predictions)

    train_accuracies.append(train_acc)
    test_accuracies.append(test_acc)

    print(f"Tamaño vocab: {vocab_size} - Train Acc: {train_acc:.2f}, Test Acc: {test_acc:.2f}")

    # Crear página de resultados y matriz de confusión
    confusion = create_results_webpage(
        train_image_paths=train_image_paths,
        test_image_paths=test_image_paths,
        train_labels=y_train,
        test_labels=y_test,
        categories=categories,
        abbr_categories= ['Bed','Cst','For','HWy', 'Ind','Cty','Kit','Liv','Mnt',
                                            'Off','OC','Sto','St','Sub','Bld'],
        predicted_categories=test_predictions,
        name_experiment=f"BOW_vocab{vocab_size}"
    )

    print(f"Matriz de confusión para vocab_size={vocab_size}:")
    print(confusion)

plt.figure(figsize=(10, 6))
plt.plot(vocab_sizes, train_accuracies, label='Train Accuracy', marker='o')
plt.plot(vocab_sizes, test_accuracies, label='Test Accuracy', marker='o')
plt.title("Rendimiento de Clasificación vs Tamaño del Vocabulario BOW")
plt.xlabel("Tamaño del Vocabulario BOW")
plt.ylabel("Precisión")
plt.legend()
plt.grid()
plt.show()

