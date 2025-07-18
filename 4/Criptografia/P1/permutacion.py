import argparse
import numpy as np


def encrypt(M: int, N: int, k1: list[int], k2: list[int], file_in, file_out):
    """
    Cifra el contenido de un archivo realizando una doble permutación por bloques MxN

    Args:
        M (int): Número de filas del bloque.
        N (int): Número de columnas del bloque.
        k1 (list): Permutación de filas.
        k2 (list): Permutación de columnas.
        file_in (file): Archivo de entrada abierto en modo lectura.
        file_out (file): Archivo de salida abierto en modo escritura.
    """
    while True:
        block = []
        while len(block) < M * N:
            c = file_in.read(1)

            if not c:
                break

            if c.isalpha():
                block.append(c)
            else:
                file_out.write(c)

        if len(block) == 0:
            break

        while len(block) < M * N:
            block.append(np.random.randint(0, 26))

        block_mat = np.array(block).reshape(M, N)

        perm_rows = np.zeros_like(block_mat)
        for new_r in range(M):
            perm_rows[new_r, :] = block_mat[k1[new_r] - 1, :]

        perm_cols = np.zeros_like(perm_rows)
        for new_c in range(N):
            perm_cols[:, new_c] = perm_rows[:, k2[new_c] - 1]

        for val in perm_cols.flatten():
            file_out.write(val)


def decrypt(M: int, N: int, k1: list[int], k2: list[int], file_in, file_out):
    """
    Descifra el contenido de un archivo realizando la doble permutación inversa
    Args:
        M (int): Número de filas del bloque.
        N (int): Número de columnas del bloque.
        k1 (list): Permutación de filas.
        k2 (list): Permutación de columnas.
        file_in (file): Archivo de entrada abierto en modo lectura.
        file_out (file): Archivo de salida abierto en modo escritura.
    """
    inv_k1 = [0] * M
    for i in range(M):
        inv_k1[k1[i] - 1] = i + 1

    inv_k2 = [0] * N
    for j in range(N):
        inv_k2[k2[j] - 1] = j + 1

    while True:
        block = []
        while len(block) < M * N:
            c = file_in.read(1)

            if not c:
                break

            if c.isalpha():
                block.append(c)
            else:
                file_out.write(c)

        if len(block) == 0:
            break

        while len(block) < M * N:
            block.append(np.random.randint(0, 26))

        block_mat = np.array(block).reshape(M, N)

        col_perm = np.zeros_like(block_mat)
        for new_c in range(N):
            col_perm[:, new_c] = block_mat[:, inv_k2[new_c] - 1]

        row_perm = np.zeros_like(col_perm)
        for new_r in range(M):
            row_perm[new_r, :] = col_perm[inv_k1[new_r] - 1, :]

        for val in row_perm.flatten():
            file_out.write(val)


def perm():
    """
    Argumentos por línea de comandos:
        -C: Ejecuta en modo cifrado.
        -D: Ejecuta en modo descifrado.
        -k1: Permutación de filas.
        -k2: Permutación de columnas.
        -i: nombre del archivo de entrada.
        -o: nombre del archivo de salida.
    """
    parser = argparse.ArgumentParser(description="Cifrado de transposicion por doble permutación (filas y columnas).")

    group = parser.add_mutually_exclusive_group(required=True)
    group.add_argument('-C', action='store_true', help='El programa cifra')
    group.add_argument('-D', action='store_true', help='El programa descifra')

    parser.add_argument('-k1', type=str, required=True, help='Permutación de filas, separada por espacios ej: ("4 3 1 2")')
    parser.add_argument('-k2', type=str, required=True, help='Permutación de columnas, separada por espacios ej: ("3 1 2 4")')

    parser.add_argument('-i', type=str, required=True, help='Fichero de entrada')
    parser.add_argument('-o', type=str, required=True, help='Fichero de salida')

    args = parser.parse_args()

    k1 = [int(x) for x in args.k1.split()]
    k2 = [int(x) for x in args.k2.split()]

    M = len(k1)
    N = len(k2)

    if M != N:
        print("Las permutaciones tienen que tener la misma longitud.")
        return

    if len(set(k1)) != M:
        print("K1 contiene valores repetidos.")
        return

    if len(set(k2)) != N:
        print("K2 contiene valores repetidos.")
        return

    file_in = open(args.i, 'r')
    file_out = open(args.o, 'w')

    if args.C:
        encrypt(M, N, k1, k2, file_in, file_out)
    else:
        decrypt(M, N, k1, k2, file_in, file_out)

    file_in.close()
    file_out.close()


if __name__ == "__main__":
    perm()

