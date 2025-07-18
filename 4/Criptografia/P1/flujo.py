import argparse

start_state = 0xACE1 # Estado inicial del LFSR


def lfsr_stream(lfsr):
    """
    Genera un valor de clave pseudoaleatoria de 8 bits utilizando LFSR.

    Args:
        lfsr (int): Estado actual del LFSR.

    Returns:
        tuple: Una tupla que contiene el nuevo estado del LFSR y el valor de salida generado (int).
    """
    out = 0

    for _ in range(8):
        bit_in = (lfsr ^ (lfsr >> 2) ^ (lfsr >> 3) ^ (lfsr >> 5)) & 1
        out = (out << 1) | (lfsr & 1)
        lfsr = (lfsr >> 1) | (bit_in << 15)
    
    return lfsr, out


def encrypt(m, file_in, file_out):
    """
    Cifra el contenido de un archivo utilizando cifrado por flujo con LFSR.

    Args:
        m (int): Tamaño del espacio del texto cifrado.
        file_in (file): Archivo de entrada abierto en modo lectura.
        file_out (file): Archivo de salida abierto en modo escritura.
    """
    base_char = ord('A')

    lfsr = start_state
    while True:
        x = file_in.read(1)

        if not x:
            break

        if x.isalpha():
            lfsr, key = lfsr_stream(lfsr)

            x = ord(x) - base_char
            y = (x + key) % m

            file_out.write(chr(y + base_char))
        else:
            file_out.write(x)


def decrypt(m, file_in, file_out):
    """
    Descifra el contenido de un archivo utilizando cifrado por flujo con LFSR.

    Args:
        m (int): Tamaño del espacio del texto cifrado.
        file_in (file): Archivo de entrada cifrado abierto en modo lectura.
        file_out (file): Archivo de salida abierto en modo escritura.
    """
    base_char = ord('A')

    lfsr = start_state
    while True:
        x = file_in.read(1)

        if not x:
            break

        if x.isalpha():
            lfsr, key = lfsr_stream(lfsr)

            x = ord(x) - base_char
            y = (x - key) % m

            file_out.write(chr(y + base_char))
        else:
            file_out.write(x)


def flujo():
    """
    Argumentos de línea de comandos:
        -C: para cifrar.
        -D: para descifrar.
        -m: tamaño del espacio de texto cifrado.
        -i: nombre del archivo de entrada.
        -o: nombre del archivo de salida.
    """
    parser = argparse.ArgumentParser(description='Cifrado de flujo LFSR Fibonacci')

    group = parser.add_mutually_exclusive_group(required=True)
    group.add_argument('-C', action='store_true', help='El programa cifra')
    group.add_argument('-D', action='store_true', help='El programa descifra')

    parser.add_argument('-m', type=int, required=True, help='Tamaño del espacio de texto cifrado')

    parser.add_argument('-i', type=str, required=True, help='Fichero de entrada')
    parser.add_argument('-o', type=str, required=True, help='Fichero de salida')

    args = parser.parse_args()

    file_in = open(args.i, 'r')
    file_out = open(args.o, 'w')

    if args.C is True:
        encrypt(args.m, file_in, file_out)
    else:
        decrypt(args.m, file_in, file_out)

    file_in.close()
    file_out.close()


if __name__ == "__main__":
    flujo()

