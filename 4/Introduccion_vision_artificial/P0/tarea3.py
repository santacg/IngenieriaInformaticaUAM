import skimage as ski
import matplotlib.pyplot as plt
import numpy as np

# Lea y visualice la imagen RGB “A_small_cup_of_coffe” disponible en
# https://bit.ly/2Zjkcm7
img = ski.io.imread(
    'https://upload.wikimedia.org/wikipedia/commons/4/45/A_small_cup_of_coffee.JPG')
plt.imshow(img)
plt.show()

# Muestre por consola las dimensiones, el tipo y el valor máximo de la
# imagen
print("Dimensiones | tipo | valor máximo")
print(img.shape, img.dtype, np.max(img))

# Posteriormente cambie el tipo a float y normalice la imagen para que
# esté en el rango [0,1]. Muestre por consola las dimensiones de la
# imagen y el tipo
float_img = ski.img_as_float32(img)
print("Dimensiones | tipo")
print(float_img.shape, float_img.dtype)

# Posteriormente transforme la imagen al espacio de color HSV
hsv_img = ski.color.rgb2hsv(float_img)

# Visualice cada canal en una sola ventana como una imagen en escala
# de grises (e.g., una ventana con tres columnas y una fila).
hueChannel = hsv_img[:, :, 0]
saturationChannel = hsv_img[:, :, 1]
valueChannel = hsv_img[:, :, 2]

_, axis = plt.subplots(1, 3)
axis[0].imshow(hueChannel, cmap='gray')
axis[1].imshow(saturationChannel, cmap='gray')
axis[2].imshow(valueChannel, cmap='gray')
plt.tight_layout()
plt.show()
