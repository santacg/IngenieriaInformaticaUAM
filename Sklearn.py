import pandas as pd
from os import listdir
from sklearn import model_selection
from sklearn import naive_bayes as nb
from sklearn.preprocessing import StandardScaler
from sklearn import neighbors as knn


for archivo in listdir('Datasets/'):
    dataset = 'Datasets/' + archivo
    print(f"Dataset: {dataset}")
    df = pd.read_csv(dataset, dtype={'Class': 'object'})

    # Separamos características numéricas y categóricas
    datos_numericos = df.select_dtypes(include='number')
    datos_categoricos = df.select_dtypes(include='object')

    # Separamos la columna target
    target = df['Class']
    datos_categoricos = datos_categoricos.drop('Class', axis=1)

    # Codificación One-Hot para datos categóricos si existen
    datos_categoricos_codificados = None
    if not datos_categoricos.empty:
        datos_categoricos_codificados = pd.get_dummies(
            datos_categoricos, drop_first=True)
    else:
        datos_categoricos = None

    # Si no hay datos numericos None
    if datos_numericos.empty:
        datos_numericos = None

    # Concatenamos los datos numéricos y categóricos codificados
    X = pd.concat([datos_numericos, datos_categoricos_codificados], axis=1)

    # Realizamos la división de los datos
    train_X, test_X, train_y, test_y = model_selection.train_test_split(
        X, target, test_size=0.25, random_state=42)

    # Ahora separamos los datos numéricos y categóricos a partir de los conjuntos de entrenamiento y prueba
    if datos_numericos is not None:
        train_num = train_X[datos_numericos.columns]
        test_num = test_X[datos_numericos.columns]

    if datos_categoricos_codificados is not None:
        train_cat = train_X[datos_categoricos_codificados.columns]
        test_cat = test_X[datos_categoricos_codificados.columns]

    # NAIVE BAYES
    # Naive Bayes para atributos numéricos
    nb_gaussian = nb.GaussianNB()
    if datos_numericos is not None:
        nb_gaussian.fit(train_num, train_y)

    # Naive Bayes para atributos categóricos con corrección de Laplace
    nb_categorical = nb.CategoricalNB()
    if datos_categoricos is not None:
        nb_categorical.fit(train_cat, train_y)

    print(f"Naive Bayes para el dataset {dataset}:")
    # Cálculo de tasa de error para Naive Bayes
    # ValidaciónSimple
    if datos_categoricos is not None and datos_numericos is not None:
        error_num = 1 - nb_gaussian.score(test_num, test_y)
        error_cat = 1 - nb_categorical.score(test_cat, test_y)
        error_promedio = (error_num + error_cat) / 2
    elif datos_categoricos is None and datos_numericos is not None:
        error_num = 1 - nb_gaussian.score(test_num, test_y)
        error_promedio = error_num
    elif datos_categoricos is not None and datos_numericos is None:
        error_cat = 1 - nb_categorical.score(test_cat, test_y)
        error_promedio = error_cat
    else:
        error_promedio = -1
    print(
        f"Ratio de error de clasificación - ValidaciónSimple sklearn NB: {error_promedio}")

    # ValidaciónCruzada
    if datos_categoricos is not None and datos_numericos is not None:
        error_num = 1 - \
            model_selection.cross_val_score(
                nb_gaussian, datos_numericos, target, cv=5)
        error_cat = 1 - \
            model_selection.cross_val_score(
                nb_categorical, datos_categoricos_codificados, target, cv=5)
        error_promedio = (error_num + error_cat) / 2
    elif datos_categoricos is None and datos_numericos is not None:
        error_num = 1 - \
            model_selection.cross_val_score(
                nb_gaussian, datos_numericos, target, cv=5)
        error_promedio = error_num
    elif datos_categoricos is not None and datos_numericos is None:
        error_cat = 1 - \
            model_selection.cross_val_score(
                nb_categorical, datos_categoricos_codificados, target, cv=5)
        error_promedio = error_cat
    else:
        error_promedio = -1
    print(
        f"Ratio de error de clasificación - ValidaciónCruzada sklearn NB: {error_promedio}")

    # KNN
    print(f"KNN para el dataset {dataset}:")
    # Estandarizamos los datos creando una copia con sklearn standarScaler,
    # empleamos tanto la media como la desviacion estandar
    df_std = df.copy()
    scaler = StandardScaler()
    # Usamos nuevamente la codificacion One-Hot
    df_std = df_std.drop('Class', axis=1)
    df_std = pd.get_dummies(df_std)

    datos_numericos = df_std.select_dtypes(include='number').columns

    df_std[datos_numericos] = scaler.fit_transform(df_std[datos_numericos])
    # Hacemos una particionado simple usando los datos estandarizados
    train_X, test_X, train_y, test_y = model_selection.train_test_split(
        df_std, target, test_size=0.25, random_state=42)

    # Ahora separamos los datos numéricos y categóricos a partir de los conjuntos de entrenamiento y prueba

    # Para knn empleamos la distancia euclidia con el parametro p=2,
    # que tiene este valor por defecto, así como metric='minkowski' que
    # resulta en la distancia euclidia estandar
    neigh = knn.KNeighborsClassifier()
    neigh.fit(train_X, train_y)
    knn_error = 1 - neigh.score(test_X, test_y)
    print(
        f"Ratio de error de clasificación - ValidaciónSimple sklearn KNN: {knn_error}")

    knn_error = 1 - model_selection.cross_val_score(neigh, df_std, target)
    print(
        f"Ratio de error de clasificación - ValidaciónCruzada sklearn KNN: {knn_error}")
