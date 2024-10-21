import pandas as pd
from sklearn import model_selection
from sklearn import naive_bayes as nb

df = pd.read_csv("Datasets/heart.csv", dtype={'Class': 'object'})
df_numerical = df.select_dtypes(include='number')
df_categorical = df.select_dtypes(include='object')

# Separar data y target
target = df['Class']
data_categorical = df_categorical.drop('Class', axis=1)
data_categorical_encoded = pd.get_dummies(data_categorical, drop_first=True)

# Validación simple
train_split, test_split, train_target, test_target = model_selection.train_test_split(
    df_numerical, target)
train_split_cat, test_split_cat, train_target_cat, test_target_cat = model_selection.train_test_split(
    data_categorical_encoded, target, test_size=0.2)

# Naive bayes para atributos numéricos
gaussian_nb = nb.GaussianNB()
gaussian_nb.fit(train_split, train_target)

# Naive bayes para atributos categóricos
categorical_nb = nb.CategoricalNB(fit_prior=True, alpha=1)
categorical_nb.fit(train_split_cat, train_target_cat)

# Calculo de tasa de error
error = (1 - gaussian_nb.score(test_split, test_target))
error += (1 - categorical_nb.score(test_split_cat, test_target_cat))
error = error / 2
print(f"Ratio de error de clasificación - ValidaciónSimple sklearn: {error}")

# Validación cruzada
print(f"Ratio de error de clasificación - ValidacionCruzada sklearn: {
      1 - model_selection.cross_val_score(gaussian_nb, train_split, train_target)}")
