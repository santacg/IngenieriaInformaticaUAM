import argparse
import numpy as np


def encrypt(key: list[int], file_in, file_out):
    """
    Cifra el contenido de un archivo utilizando el cifrado de Vigenère en bloques del tamaño de la clave.

    Args:
        key (list of int): Clave representada como una lista de enteros (0-25).
        file_in (file): Archivo de entrada abierto en modo lectura.
        file_out (file): Archivo de salida abierto en modo escritura.
    """
    base_char = ord('A')

    key_len = len(key)

    flag_eo = False
    while flag_eo == False:
        block = []
        while len(block) != key_len:
            x = file_in.read(1)

            if not x:
                flag_eo = True
                break

            if x.isalpha():
                block.append(ord(x) - base_char)
            else:
                file_out.write(x)

        if block and len(block) < key_len:
            block += [np.random.randint(0, 26)] * (key_len - len(block))

        if len(block) == key_len:
            block = np.array(block, dtype=int)
            y = (block + key) % 26

            for char in y:
                file_out.write(chr(char + base_char))


def decrypt(key: list[int], file_in, file_out):
    """
    Descifra el contenido de un archivo utilizando el cifrado de Vigenère en bloques del tamaño de la clave.

    Args:
        key (list of int): Clave representada como una lista de enteros (0-25).
        file_in (file): Archivo de entrada cifrado abierto en modo lectura.
        file_out (file): Archivo de salida abierto en modo escritura.
    """
    base_char = ord('A')

    key_len = len(key)

    flag_eo = False
    while flag_eo == False:
        block = []
        while len(block) != key_len:
            x = file_in.read(1)

            if not x:
                flag_eo = True
                break

            if x.isalpha():
                block.append(ord(x) - base_char)
            else:
                file_out.write(x)

        if len(block) == key_len:
            block = np.array(block, dtype=int)
            y = (block - key) % 26

            for char in y:
                file_out.write(chr(char + base_char))


def vigenere():
    """
    Argumentos de línea de comandos:
        -C: activa modo cifrado.
        -D: activa modo descifrado.
        -k: clave como cadena de letras.
        -i: nombre del archivo de entrada.
        -o: nombre del archivo de salida.
    """
    parser = argparse.ArgumentParser(description='Cifrado de Vigenere')

    group = parser.add_mutually_exclusive_group(required=True)
    group.add_argument('-C', action='store_true', help='El programa cifra')
    group.add_argument('-D', action='store_true', help='El programa descifra')

    parser.add_argument('-k', type=str, required=True, help='Cadena de caracteres usada como clave')

    parser.add_argument('-i', type=str, required=True, help='Fichero de entrada')
    parser.add_argument('-o', type=str, required=True, help='Fichero de salida')

    args = parser.parse_args()

    key = [(ord(k) - ord('A')) for k in args.k]

    file_in = open(args.i, 'r')
    file_out = open(args.o, 'w')

    if args.C is True:
        encrypt(key, file_in, file_out)
    else:
        decrypt(key, file_in, file_out)

    file_in.close()
    file_out.close()


if __name__ == "__main__":
    vigenere()

