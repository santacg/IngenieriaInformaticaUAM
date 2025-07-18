import argparse
import sympy
import numpy as np


def encrypt(m: int, n: int, perm, file_in, file_out):
    """
    Cifra el contenido de un archivo utilizando el cifrado de transposicion en bloques del tamaño de la matriz.

    Args:
        m (int): Tamaño del espacio del texto cifrado.
        n (int): Dimensión de la matriz.
        perm (np.ndarray): matriz de permutación de tamaño nxn.
        file_in (file): Archivo de entrada abierto en modo lectura.
        file_out (file): Archivo de salida abierto en modo escritura.
    """
    base_char = ord('A')

    flag_eo = False
    while flag_eo == False:
        block = []
        while len(block) != n:
            x = file_in.read(1)

            if not x:
                flag_eo = True
                break

            if x.isalpha():
                block.append(ord(x) - base_char)
            else:
                file_out.write(x)

        if block and len(block) < n:
            block += [np.random.randint(0, 26)] * (n - len(block))

        if len(block) == n:
            block = np.array(block, dtype=int)
            y = np.matmul(perm, block) % m

            for char in y:
                file_out.write(chr(char + base_char))


def decrypt(m: int, n: int, perm_inv, file_in, file_out):
    """
    Descifra el contenido de un archivo utilizando el cifrado de transposicion en bloques del tamaño de la matriz.

    Args:
        m (int): Tamaño del espacio del texto cifrado.
        n (int): Dimensión de la matriz.
        perm_inv (np.ndarray): Matriz de permutacion inversa de tamaño nxn.
        file_in (file): Archivo de entrada abierto en modo lectura.
        file_out (file): Archivo de salida abierto en modo escritura.
    """
    base_char = ord('A')

    flag_eo = False
    while flag_eo == False:
        block = []
        while len(block) != n:
            x = file_in.read(1)

            if not x:
                flag_eo = True
                break

            if x.isalpha():
                block.append(ord(x) - base_char)
            else:
                file_out.write(x)

        if len(block) == n:
            block = np.array(block, dtype=int)
            y = np.matmul(perm_inv, block) % m

            for char in y:
                file_out.write(chr(char + base_char))


def perm():
    """
    Argumentos de línea de comandos:
        -C: activa el modo cifrado.
        -D: activa el modo descifrado.
        -m: tamaño del espacio de texto cifrado (modulo).
        -p: matriz de transposicion.
        -n: dimensión de la matriz de transformación.
        -i: nombre del archivo de entrada.
        -o: nombre del archivo de salida.
    """
    parser = argparse.ArgumentParser(description="Cifrado de transposicion")

    group = parser.add_mutually_exclusive_group(required=True)
    group.add_argument('-C', action='store_true', help='El programa cifra')
    group.add_argument('-D', action='store_true', help='El programa descifra')

    parser.add_argument('-m', type=int, required=True, help='Tamaño del espacio de texto cifrado')
    parser.add_argument('-p', type=str, required=False, help='Cadena que contiene la permutación ej: ("4 2 1 3")')
    parser.add_argument('-n', type=int, required=False, help='Tamaño de la cadena de permutación aleatoria; no usar junto a la opción -p')

    parser.add_argument('-i', type=str, required=True, help='Fichero de entrada')
    parser.add_argument('-o', type=str, required=True, help='Fichero de salida')

    args = parser.parse_args()

    if args.n is not None:
        if not args.C:
            parser.error("La opción -n solo se puede utilizar junto con -C.")

        if args.p is not None:
            parser.error("No se puede utilizar la opción -n si se ha indicado -p.")

    if args.C and (args.p is None and args.n is None):
        parser.error("En modo cifrado (-C) se debe proporcionar -p o -n.")

    if args.p:
        perm_values = [int(value) for value in args.p.split()]

        if len(set(perm_values)) != len(perm_values):
            print("La permutacion contiene valores repetidos.")
            return
    else:
        perm_values = np.random.choice(args.n, size=args.n, replace=False)

        permutacion_dat = open('permutacion.dat', 'w')
        permutacion_dat.write(perm_values)

    n = len(perm_values)
    
    perm = np.zeros(shape=(n, n), dtype=int)
    for row, col in enumerate(perm_values):
        perm[row, col - 1] = 1

    file_in = open(args.i, 'r')
    file_out = open(args.o, 'w')

    n = perm.shape[0]
    if args.C is True:
        encrypt(args.m, n, perm, file_in, file_out)
    else:
        perm = sympy.Matrix(perm)
        perm_inv = perm.inv_mod(args.m)
        perm_inv = np.array(perm_inv.tolist(), dtype=int)

        decrypt(args.m, n, perm_inv, file_in, file_out)

    file_in.close()
    file_out.close()


if __name__ == "__main__":
    perm()

