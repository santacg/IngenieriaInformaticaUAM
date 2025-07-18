import argparse
import numpy as np
import re

# ICs
IC_ENG = 0.065
IC_ESP = 0.084


def get_ic(text: str, key_len: int):
    """
    Calcula el IC promedio para una posible longitud de clave en un texto cifrado.

    Args:
        text (str): Texto cifrado.
        key_len (int): Posible longitud de clave.

    Returns:
        float: Valor promedio del IC para esa longitud de clave.
    """
    base_char = ord('A')

    vec_ics = []
    for i in range(key_len):
        subtext = text[i::key_len]

        counts = []
        for j in range(26):
            letter = chr(base_char + j)
            counts.append(subtext.count(letter))

        size = sum(counts)
        num = sum(f * (f - 1) for f in counts)
        denum = size * (size - 1)

        ic = num / denum

        vec_ics.append(ic)

    return sum(vec_ics) / len(vec_ics)


def ic():
    """
    Argumentos de línea de comandos:
        -l: Longitud máxima de clave a probar.
        -i: Archivo de entrada con el texto cifrado.
        --lang: idioma ("eng" o "esp")
    """
    parser = argparse.ArgumentParser(description='Método del Índice de Coincidencia')

    parser.add_argument('-l', type=int, required=True, help='Máxima longitud de clave a probar')
    parser.add_argument('-i', type=str, required=True, help='Fichero de entrada')
    parser.add_argument('--lang', type=str, choices=['eng', 'esp'], default='eng',
                        help='Idioma del texto original: "eng" para ingles y "esp" para español (por defecto eng)')

    args = parser.parse_args()

    file_in = open(args.i, 'r')

    text = file_in.read()
    text = re.sub(r'[^a-zA-Z]', '', text).upper()

    ic_lang = IC_ENG if args.lang == 'eng' else IC_ESP

    ics = []

    for key_len in range(1, args.l + 1):
        ic = get_ic(text, key_len)
        ics.append(ic)

        print(f'IC para longitud de clave {key_len}: {ic}')

    ics_diff = [abs(ic - ic_lang) for ic in ics]

    best_ic = np.argmin(ics_diff)
    best_key_len = best_ic + 1

    print(f'IC más cercano al idioma original: {ics[best_ic]}, con longitud de clave {best_key_len}')
    file_in.close()


if __name__ == "__main__":
    ic()

