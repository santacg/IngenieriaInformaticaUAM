import matplotlib.pyplot as plt
import numpy as np
import skimage as ski

# Cree una lista vacía con el nombre list_img
list_img = []

# Lea la imagen astronaut (disponible en skimage.data
# 1) y añádala a la lista
# list_img. Identifique si la imagen es gris o color
list_img.append(ski.data.astronaut())
# imagen a color

# Lea la imagen camera (disponible en skimage.data1) y añádala a la lista
# list_img. Identifique si la imagen es gris o color
list_img.append(ski.data.camera())
# imagen gris

# Cree un array bidimensional de ceros con tamaño 10x10 y añádala a la lista
# list_img. Identifique si el array es gris (bidimensional)
# o color (tridimensional)
list_img.append(np.zeros((10, 10)))
# imagen gris

# Recorra la lista mediante un bucle utilizando la instrucción range
# 1. Muestre por consola las dimensiones de la imagen, tipo y valor máximo
# 2. Visualice la imagen en una ventana
for i in range(0, 3):
    print("Dimensiones | tipo | valor máximo")
    print(list_img[i].shape)
    print(list_img[i].dtype)
    print(np.max(list_img[i]))
    plt.imshow(list_img[i])
    plt.show()

# Cree una nueva lista con los dos primeros elementos de la lista list_img y
# muestra el número de elementos de esta nueva lista
new_list = list_img[0:2]
print(len(new_list))
