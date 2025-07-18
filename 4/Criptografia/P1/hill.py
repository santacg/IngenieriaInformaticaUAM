import argparse
import math
import sympy
import numpy as np


def encrypt(m: int, n: int, matrix, f_in, f_out):
    """
    Cifra el contenido de un archivo utilizando el cifrado de Hill en bloques del tamaño de la matriz.

    Args:
        m (int): Tamaño del espacio del texto cifrado.
        n (int): Dimensión de la matriz.
        matrix (np.ndarray): Matriz clave de tamaño nxn.
        f_in (file): Archivo de entrada abierto en modo lectura.
        f_out (file): Archivo de salida abierto en modo escritura.
    """
    base_char = ord('A')

    flag_eo = False
    while flag_eo == False:
        block = []
        while len(block) != n:
            x = f_in.read(1)

            if not x:
                flag_eo = True
                break

            if x.isalpha():
                block.append(ord(x) - base_char)
            else:
                f_out.write(x)

        if block and len(block) < n:
            block += [np.random.randint(0, 26)] * (n - len(block))

        if len(block) == n:
            block = np.array(block, dtype=int)
            y = np.matmul(matrix, block) % m

            for char in y:
                f_out.write(chr(char + base_char))


def decrypt(m: int, n: int, matrix_inv, f_in, f_out):
    """
    Descifra el contenido de un archivo utilizando el cifrado de Hill en bloques del tamaño de la matriz.

    Args:
        m (int): Tamaño del espacio del texto cifrado.
        n (int): Dimensión de la matriz.
        matrix_inv (np.ndarray): Matriz inversa de la clave de tamaño nxn.
        f_in (file): Archivo de entrada abierto en modo lectura.
        f_out (file): Archivo de salida abierto en modo escritura.
    """
    base_char = ord('A')

    flag_eo = False
    while flag_eo == False:
        block = []
        while len(block) != n:
            x = f_in.read(1)

            if not x:
                flag_eo = True
                break

            if x.isalpha():
                block.append(ord(x) - base_char)
            else:
                f_out.write(x)

        if len(block) == n:
            block = np.array(block, dtype=int)
            y = np.matmul(matrix_inv, block) % m

            for char in y:
                f_out.write(chr(char + base_char))


def hill():
    """
    Argumentos de línea de comandos:
        -C: activa el modo cifrado.
        -D: activa el modo descifrado.
        -m: tamaño del espacio de texto.
        -n: dimensión de la matriz de transformación.
        -k: fichero que contiene la matriz de transformación.
        -i: nombre del archivo de entrada.
        -o: nombre del archivo de salida.
    """
    parser = argparse.ArgumentParser(description="Cifrado Hill")

    group = parser.add_mutually_exclusive_group(required=True)
    group.add_argument('-C', action='store_true', help='El programa cifra')
    group.add_argument('-D', action='store_true', help='El programa descifra')

    parser.add_argument('-m', type=int, required=True, help='Tamaño del espacio de texto cifrado')
    parser.add_argument('-n', type=int, required=True, help='Dimensión de la matriz de transformación')

    parser.add_argument('-k', type=str, required=True, help='Fichero que contiene la matriz de transformación')
    parser.add_argument('-i', type=str, required=True, help='Fichero de entrada')
    parser.add_argument('-o', type=str, required=True, help='Fichero de salida')

    args = parser.parse_args()

    file_matrix = open(args.k, 'r')
    file_in = open(args.i, 'r')
    file_out = open(args.o, 'w')

    matrix = np.zeros(dtype=int, shape=(args.n, args.n))

    for row, line in enumerate(file_matrix.readlines()):
        line = line.split()
        matrix[row:] = line

    det = round(np.linalg.det(matrix))
    gcd = math.gcd(det, args.m)

    if gcd != 1:
        print(f"El MCD del determinante {det} y {args.m} es {gcd}")
        return

    if args.C is True:
        encrypt(args.m, args.n, matrix, file_in, file_out)
    else:
        matrix = sympy.Matrix(matrix)
        matrix_inv = matrix.inv_mod(args.m)
        matrix_inv = np.array(matrix_inv.tolist(), dtype=int)

        decrypt(args.m, args.n, matrix_inv, file_in, file_out)

    file_matrix.close()
    file_in.close()
    file_out.close()


if __name__ == "__main__":
    hill()

