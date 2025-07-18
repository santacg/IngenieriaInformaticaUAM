import argparse
import math
import numpy as np
import re


def get_key_len(text: str, l: int):
    """
    Aplica el método de Kasiski para estimar la longitud de la clave en un texto cifrado.

    Args:
        text (str): Texto cifrado.
        l (int): Longitud del n-grama.

    Returns:
        int: Estimación de la longitud de la clave basada en el MCD de las distancias entre repeticiones.
    """
    for i in range(len(text) - l):
        seq = text[i:i + l]
        positions = []

        start = i + l

        while True:
            pos = text.find(seq, start)

            if pos == -1:
                break

            positions.append(pos)
            start = pos + l

        if len(positions) > 0:
            positions.insert(0, i)

            return math.gcd(*positions)

    return 0  # Si ninguna subcadena se repite


def kasiski():
    """
    Argumentos de línea de comandos:
        -l: longitud máxima del n-grama buscado.
        -i: archivo de entrada con el texto cifrado.
    """
    parser = argparse.ArgumentParser(description='Método de Kasiski')

    parser.add_argument('-l', type=int, required=True, help='Máxima longitud de n-grama buscado')
    parser.add_argument('-i', type=str, required=True, help='Fichero de entrada')

    args = parser.parse_args()

    file_in = open(args.i, 'r')

    text = file_in.read()
    text = re.sub(r'[^a-zA-Z]', '', text).upper()

    key_lens = []

    key_len = 0
    for l in range(1, args.l + 1):
        key_len = get_key_len(text, l)
        key_lens.append(key_len)

        print(f'Longitud de clave para n-grama de tamaño {l}: {key_len}')

        if key_len == 0:
            print(f"No existen repeticiones de las que se puedan extraer subsecuencias con tamaño de n-grama {l}")

    key_lens = [k for k in key_lens if k > 1] # Sin 0 ni 1

    if key_lens:
        valores, conteos = np.unique(key_lens, return_counts=True)
        key_len_final = valores[np.argmax(conteos)] # Selecciono el valor que más se repite

        print(f'Longitud de clave encontrada (más comun, sin 0 ni 1): {key_len_final}')
    else:
        print("No se encontró ninguna longitud de clave válida (mayor que 1).")

    file_in.close()


if __name__ == "__main__":
    kasiski()

