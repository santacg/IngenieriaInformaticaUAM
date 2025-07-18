import argparse
import re

# Frecuencias relativas de letras
FREQ_ENG = [
    0.082, 0.015, 0.028, 0.043, 0.127, 0.022, 0.020,
    0.061, 0.070, 0.002, 0.008, 0.040, 0.024, 0.067,
    0.075, 0.019, 0.001, 0.060, 0.063, 0.091, 0.028,
    0.010, 0.023, 0.001, 0.020, 0.001
]
FREQ_ESP = [
    0.1196, 0.0092, 0.0292, 0.0687, 0.1678, 0.0052, 0.0073, 0.0089, 0.0415,
    0.0030, 0.0000, 0.0837, 0.0212, 0.0701, 0.0869, 0.0277, 0.0153, 0.0494,
    0.0788, 0.0331, 0.0480, 0.0039, 0.0000, 0.0006, 0.0154, 0.0015
]

# ICs
IC_ENG = 0.065
IC_ESP = 0.084


def get_vigenere_key(text: str, key_len: int, freq_lang: list, ic_lang: float):
    """
    Determina la clave probable para un texto cifrado con Vigenère
    usando análisis del IC y frecuencias.

    Args:
        text (str): Texto cifrado.
        key_len (int): Longitud de la clave supuesta.
        freq_lang (list): Frecuencias del idioma.
        ic_lang (float): IC esperado del idioma.

    Returns:
        str: Clave criptoanalizada.
    """
    subkeys = []
    subsequences = [text[i::key_len] for i in range(key_len)]

    for index_sub, subseq in enumerate(subsequences, start=1):
        print(f"Subsecuencia {index_sub}:")

        if len(subseq) == 0:
            subkeys.append('A')
            continue

        freq_abs = [0] * 26
        for c in subseq:
            pos_letra = ord(c) - ord('A')
            if 0 <= pos_letra < 26:
                freq_abs[pos_letra] += 1

        n = len(subseq)
        freq_rel = [f / n for f in freq_abs]

        best_k = 0
        best_mk = 0.0
        best_diff = abs(ic_lang - 0.0)

        for k in range(26):
            mk = sum(freq_lang[j] * freq_rel[(j + k) % 26] for j in range(26))
            diff = abs(ic_lang - mk)

            if diff < best_diff:
                best_diff = diff
                best_k = k
                best_mk = mk

        print(f"Mejor k = {best_k} con M(k) = {best_mk:.4f} (diff = {best_diff:.4f})")
        subkeys.append(chr(ord('A') + best_k))

    return "".join(subkeys)


def main():
    """
    Argumentos de línea de comandos:
        -l: longitud de clave.
        -i: nombre del archivo de entrada
        --lang: idioma (eng o esp)
    """
    parser = argparse.ArgumentParser(description="Calcular subclaves Vigenere con IC")

    parser.add_argument("-l", type=int, required=True, help="Longitud de la clave")
    parser.add_argument("-i", type=str, required=True, help="Fichero de entrada")
    parser.add_argument('--lang', type=str, choices=['eng', 'esp'], default='eng',
                        help='Idioma del texto original: "eng" para ingles y "esp" para español (por defecto eng)')

    args = parser.parse_args()

    file_in = open(args.i, 'r')
    text = file_in.read()
    text = re.sub(r'[^a-zA-Z]', '', text).upper()

    freq_lang = FREQ_ENG if args.lang == "eng" else FREQ_ESP
    ic_lang = IC_ENG if args.lang == "eng" else IC_ESP

    key = get_vigenere_key(text, args.l, freq_lang, ic_lang)
    print(f"\nLa clave criptoanalizada es: {key}")

    file_in.close()


if __name__ == "__main__":
    main()

