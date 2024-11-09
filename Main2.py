import Datos
import numpy as np
from EstrategiaParticionado import ValidacionSimple
from Clasificador import ClasificadorNaiveBayes, ClasificadorKNN, ClasificadorRegresionLogistica
import matplotlib.pyplot as plt

datasets = ['Datasets/heart.csv', 'Datasets/wdbc.csv']
nombres_datasets = ['Heart', 'WDBC']

clasificadores = {
    'Naive Bayes': ClasificadorNaiveBayes(),
    'KNN': ClasificadorKNN(K=5),
    'Regresión Logística': ClasificadorRegresionLogistica(epocas=100, aprendizaje=0.1)
}

for idx_dataset, dataset_name in enumerate(datasets):
    
    dataset = Datos.Datos(dataset_name)
    
    particionado = ValidacionSimple(1, 0.3)  
    particiones = particionado.creaParticiones(dataset.datos)
    
    particion = particiones[0]
    datos_train = dataset.extraeDatos(particion.indicesTrain)
    datos_test = dataset.extraeDatos(particion.indicesTest)
    
    atributos_nominales = dataset.nominalAtributos
    diccionario = dataset.diccionarios
    
    TPRs = []
    FPRs = []
    
    for nombre_clasificador, clasificador in clasificadores.items():
        print(f"\nClasificador: {nombre_clasificador}")
        
        clasificador.entrenamiento(datos_train, atributos_nominales, diccionario)
        
        predicciones = clasificador.clasifica(datos_test, atributos_nominales, diccionario)
        
        # Comparar predicciones con las clases reales
        clases_reales = datos_test['Class'].values
        errores = 0

        for i in range(len(predicciones)):
            if predicciones[i] != clases_reales[i]:
                errores += 1
                print(f"Predicción: {predicciones[i]} - Real: {clases_reales[i]} | Error: {errores}")
            else:
                print(f"Predicción: {predicciones[i]} - Real: {clases_reales[i]} | Error: {errores}")
        
        print(f"\nErrores totales: {errores} de {len(predicciones)}")
        print(f"Tasa de error: {errores / len(predicciones):.4f}")
        
        TP, FP, TN, FN = clasificador.matriz_confusion(datos_test, predicciones)
        print(f"TP: {TP}, FP: {FP}, TN: {TN}, FN: {FN}")
        
        # TPR y FPR
        TPR = TP / (TP + FN) if (TP + FN) > 0 else 0
        FPR = FP / (FP + TN) if (FP + TN) > 0 else 0
        print(f"TPR: {TPR}, FPR: {FPR}")
        
        TPRs.append(TPR)
        FPRs.append(FPR)
