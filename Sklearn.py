import pandas as pd
from os import listdir
from sklearn import model_selection
from sklearn import naive_bayes as nb


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

    # División de los datos en entrenamiento y prueba
    if datos_numericos is not None:
        train_num, test_num, train_target_num, test_target_num = model_selection.train_test_split(
            datos_numericos, target, test_size=0.25)

    if datos_categoricos is not None:
        train_cat, test_cat, train_target_cat, test_target_cat = model_selection.train_test_split(
            datos_categoricos_codificados, target, test_size=0.25)

    # Naive Bayes para atributos numéricos
    nb_gaussian = nb.GaussianNB()
    if datos_numericos is not None:
        nb_gaussian.fit(train_num, train_target_num)

    # Naive Bayes para atributos categóricos con corrección de Laplace
    nb_categorical = nb.CategoricalNB()
    if datos_categoricos is not None:
        nb_categorical.fit(train_cat, train_target_cat)

    # Cálculo de tasa de error para Naive Bayes
    # ValidaciónSimple
    if datos_categoricos is not None and datos_numericos is not None:
        error_num = 1 - nb_gaussian.score(test_num, test_target_num)
        error_cat = 1 - nb_categorical.score(test_cat, test_target_cat)
        error_promedio = (error_num + error_cat) / 2
    elif datos_categoricos is None and datos_numericos is not None:
        error_num = 1 - nb_gaussian.score(test_num, test_target_num)
        error_promedio = error_num
    elif datos_categoricos is not None and datos_numericos is None:
        error_cat = 1 - nb_categorical.score(test_cat, test_target_cat)
        error_promedio = error_cat
    else:
        error_promedio = -1
    print(
        f"Ratio de error de clasificación - ValidaciónSimple sklearn: {error_promedio}")

    # ValidaciónCruzada
    if datos_categoricos is not None and datos_numericos is not None:
        error_num = 1 - \
            model_selection.cross_val_score(
                nb_gaussian, datos_numericos, target, cv=5).mean()
        error_cat = 1 - \
            model_selection.cross_val_score(
                nb_categorical, datos_categoricos_codificados, target, cv=5).mean()
        error_promedio = (error_num + error_cat) / 2
    elif datos_categoricos is None and datos_numericos is not None:
        error_num = 1 - \
            model_selection.cross_val_score(
                nb_gaussian, datos_numericos, target, cv=5).mean()
        error_promedio = error_num
    elif datos_categoricos is not None and datos_numericos is None:
        error_cat = 1 - \
            model_selection.cross_val_score(
                nb_categorical, datos_categoricos_codificados, target, cv=5).mean()
        error_promedio = error_cat
    else:
        error_promedio = -1
    print(
        f"Ratio de error de clasificación - ValidaciónCruzada sklearn: {error_promedio}")
