from Datos import Datos
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
train_split, test_split, train_target, test_target = model_selection.train_test_split(df_numerical, target, test_size=0.2)
train_split_cat, test_split_cat, train_target_cat, test_target_cat = model_selection.train_test_split(data_categorical_encoded, target, test_size=0.2)
# Validación cruzada
kf = model_selection.KFold(5)
validacion_cruzada = kf.get_n_splits(df)

# Naive bayes para atributos numéricos
gaussian_nb = nb.GaussianNB()
gaussian_nb.fit(train_split, train_target)

# Naive bayer para atributos categóricos
categorical_nb = nb.CategoricalNB(fit_prior=True, alpha=1)
categorical_nb.fit(train_split_cat, train_target_cat)

# Calculo de tasa de error
print(1 - gaussian_nb.score(test_split, test_target))
print(1 - categorical_nb.score(test_split_cat, test_target_cat))
