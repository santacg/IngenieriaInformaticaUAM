import numpy as np
import skimage as ski
import scipy.ndimage as ndi
import matplotlib.pyplot as plt

# Lea y visualice la imagen en escala de grises “brick” disponible en el
# paquete skimage.data (consulte https://scikitimage.org/docs/dev/api/skimage.data.html#skimage.data.brick)
img = ski.data.brick()
plt.imshow(img, cmap='grey')
plt.show()

# Muestre por consola las dimensiones de la imagen y el tipo
print("Dimensiones | tipo")
print(img.shape, img.dtype)

# Aplique las siguientes operaciones a la imagen leida:
# 1. Filtrado de Sobel horizontal
# 2. Filtrado Gaussiano con sigma=10
sobel_img = ndi.sobel(img, axis=0)
gaussian_img = ndi.gaussian_filter(img, sigma=10)

# Visualice la imagen original y cada resultado en ventanas distintas
_, axis = plt.subplots(1, 2)
axis[0].imshow(sobel_img)
axis[1].imshow(img)
plt.show()

_, axis = plt.subplots(1, 2)
axis[0].imshow(gaussian_img)
axis[1].imshow(img)
plt.show()
