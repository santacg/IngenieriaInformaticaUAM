import Datos
import numpy as np
from EstrategiaParticionado import ValidacionSimple
from Clasificador import ClasificadorNaiveBayes, ClasificadorKNN, ClasificadorRegresionLogistica
import matplotlib.pyplot as plt

datasets = ['Datasets/heart.csv', 'Datasets/wdbc.csv']
nombres_datasets = ['HEART', 'WDBC']

clasificadores = {
    'Naive Bayes': ClasificadorNaiveBayes(),
    'KNN': ClasificadorKNN(K=11),
    'Regresión Logística': ClasificadorRegresionLogistica(epocas=100, aprendizaje=0.1)
}

for idx_dataset, dataset_name in enumerate(datasets):
    print(f"\nDataset: {nombres_datasets[idx_dataset]}")
    dataset = Datos.Datos(dataset_name)
    
    # Preparar validación simple
    particionado = ValidacionSimple(1, 0.3)  
    particiones = particionado.creaParticiones(dataset.datos)
    
    particion = particiones[0]
    datos_train = dataset.extraeDatos(particion.indicesTrain).reset_index(drop=True)
    datos_test = dataset.extraeDatos(particion.indicesTest).reset_index(drop=True)
    
    atributos_nominales = dataset.nominalAtributos
    diccionario = dataset.diccionarios
    
    TPRs = []
    FPRs = []
    nombres_clasificadores = []
    
    for nombre_clasificador, clasificador in clasificadores.items():
        print(f"\nClasificador: {nombre_clasificador}")
        
        #Entrenamiento
        clasificador.entrenamiento(datos_train, atributos_nominales, diccionario)
        
        #Clasificación
        if nombre_clasificador == 'Regresión Logística':
            predicciones = clasificador.clasifica(datos_test, atributos_nominales, diccionario)
            #Para obtener los scores, no se me ocurrió otra manera de optimizarlo
            scores = clasificador.clasifica(datos_test, atributos_nominales, diccionario, return_scores=True)
        else:
            predicciones = clasificador.clasifica(datos_test, atributos_nominales, diccionario)
        
        #Calcular matriz de confusión
        TP, FP, TN, FN = clasificador.matriz_confusion(datos_test, predicciones)
        print(f"TP: {TP}, FP: {FP}, TN: {TN}, FN: {FN}")
        
        #Calcular TPR y FPR
        if (TP + FN) > 0:
            TPR = TP / (TP + FN)
        else:
            TPR = 0 #Asignar 0 si el denominador es 0  

        if (FP + TN) > 0:
            FPR = FP / (FP + TN)
        else:
            FPR = 0 
        print(f"TPR: {TPR}, FPR: {FPR}")
        
        TPRs.append(TPR)
        FPRs.append(FPR)
        nombres_clasificadores.append(nombre_clasificador)

        #Curva ROC para la Regresión Logística
        if nombre_clasificador == 'Regresión Logística':
            clases_reales = datos_test['Class'].values

            unique_scores = np.unique(scores)
            umbrales = sorted(unique_scores, reverse=True) #Si el score es 0.1, el umbral es 0.9 (1-score)

            TPR_list = []
            FPR_list = []
            
            for umbral in umbrales:

                comparacion = scores >= umbral 
                pred_binarias = comparacion.astype(int)  # True se convierte en 1, False en 0

                TP_umbral, FP_umbral, TN_umbral, FN_umbral = clasificador.matriz_confusion(datos_test, pred_binarias) #matriz de confusión con los valores reales y la predicción

                #Calcular TPR (True Positive Rate) 
                if (TP_umbral + FN_umbral) > 0:
                    TPR_umbral = TP_umbral / (TP_umbral + FN_umbral)
                else:
                    TPR_umbral = 0 #Asignar 0 si el denominador es 0  

                #Calcular FPR (False Positive Rate) 
                if (FP_umbral + TN_umbral) > 0:
                    FPR_umbral = FP_umbral / (FP_umbral + TN_umbral)
                else:
                    FPR_umbral = 0  

                TPR_list.append(TPR_umbral) #lista de las Y
                FPR_list.append(FPR_umbral) #lista de las X
            
            TPR_list.insert(0, 0)
            TPR_list.append(1)

            FPR_list.insert(0, 0)
            FPR_list.append(1)
            print(FPR_list) 
            print(TPR_list)

            sorted_indices = np.argsort(FPR_list)

            # Ordenar FPR_list y TPR_list usando los índices ordenados
            FPR_list = np.array(FPR_list)[sorted_indices]
            TPR_list = np.array(TPR_list)[sorted_indices]

            auc = 0
            for i in range(1, len(FPR_list)): #Formula del cálculo del área de un trapecio (base * (altura derecha + altura izq) /2)
                auc += (FPR_list[i] - FPR_list[i-1]) * (TPR_list[i] + TPR_list[i-1]) / 2
            auc = auc 
            print(f"AUC: {auc}")

            plt.figure()
            plt.plot(FPR_list, TPR_list, marker='.', label='Regresión Logística')
            plt.plot([0, 1], [0, 1], linestyle='--', color='gray', label='Clasificador Aleatorio')
            plt.title(f'Curva ROC - {nombres_datasets[idx_dataset]}')
            plt.xlabel('FPR (Tasa de Falsos Positivos)')
            plt.ylabel('TPR (Tasa de Verdaderos Positivos)')
            plt.legend()
            plt.show()

    plt.figure()
    for i, nombre_clasificador in enumerate(nombres_clasificadores):
        plt.scatter(FPRs[i], TPRs[i], label=nombre_clasificador)
    plt.plot([0, 1], [0, 1], linestyle='--', color='gray', label='Clasificador Aleatorio')
    plt.title(f'Espacio ROC - {nombres_datasets[idx_dataset]}')
    plt.xlabel('FPR (Tasa de Falsos Positivos)')
    plt.ylabel('TPR (Tasa de Verdaderos Positivos)')
    plt.legend()
    plt.show()
