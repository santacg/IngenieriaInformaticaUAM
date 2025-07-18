import numpy as np
import math
import argparse


def encrypt(M: int, N: int, block_mat, k1: list[int], k2: list[int]):
    """
    Cifra el contenido de un archivo realizando una doble permutación por bloques MxN

    Args:
        M (int): Número de filas del bloque.
        N (int): Número de columnas del bloque.
        block_mat: bloque a cifrar
    """
    perm_rows = np.zeros_like(block_mat)
    for new_r in range(M):
        perm_rows[new_r, :] = block_mat[k1[new_r] - 1, :]

    perm_cols = np.zeros_like(perm_rows)
    for new_c in range(N):
        perm_cols[:, new_c] = perm_rows[:, k2[new_c] - 1]

    return perm_cols


def break_k1k2(m: int, M: int, N: int, k1: list[int], k2: list[int]):
    L = math.ceil(math.log(M * N, m))
    print(f"Se necesitan {L} bloques")

    digits = np.zeros((L, M * N), dtype=int)

    for d in range(L):
        base_m = (np.arange(M * N) // (m ** d)) % m # Array de números codificados en base m
        block_mat = base_m.reshape(M, N)
        encrypted_block = encrypt(M, N, block_mat, k1, k2)
        digits[d] = encrypted_block.flatten()

    p_orig = np.zeros(M * N, dtype=int)

    # Reconstruyo los números originales
    for d in range(L):
        p_orig += digits[d] * (m ** d)

    # Obtener K1 y K2
    K1 = [0] * M
    K2 = [0] * N

    for idx, p in enumerate(p_orig):
        new_row, new_col = divmod(idx, N) # Posición tras cifrado
        row_orig, col_orig = divmod(p, N) # Posición antes de cifrado

        K1[new_row] = row_orig + 1
        K2[new_col] = col_orig + 1

    return list(map(int, K1)), list(map(int, K2))


def break_perm():
    """
    Argumentos por línea de comandos:
        -m: Tamaño del espacio del texto cifrado.
        -k1: Permutación de filas.
        -k2: Permutación de columnas.
    """
    parser = argparse.ArgumentParser(description="Cifrado de transposicion por doble permutación (filas y columnas).")

    parser.add_argument('-m', type=int, required=True, help='Tamaño del espacio de texto')
    parser.add_argument('-k1', type=str, required=True, help='Permutación de filas, separada por espacios ej: ("4 3 1 2")')
    parser.add_argument('-k2', type=str, required=True, help='Permutación de columnas, separada por espacios ej: ("3 1 2 4")')

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

    print(f"Valores K1-K2 reales: {k1, k2}")
    print(f"Valores K1-K2 obtenidos: {break_k1k2(args.m, M, N, k1, k2)}")


if __name__ == "__main__":
    break_perm()

