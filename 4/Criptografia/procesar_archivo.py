import argparse
import re


def procesa_archivo():
    """
    Argumentos de línea de comandos:
        -n: elimina saltos de linea
        -N: incluye números
        -A: incluye todos los caracteres posibles
        -i: nombre del archivo de entrada
        -o: nombre del archivo de salida
    """
    parser = argparse.ArgumentParser(description="Procesador de texto - por defecto incluye solamente caracteres a-z, A-Z y saltos de línea")

    parser.add_argument('-n', action='store_true', required=False, help='Elimina los saltos de linea')
    parser.add_argument('-N', action='store_true', required=False, help='Incluye números del 0 al 9')
    parser.add_argument('-A', action='store_true', required=False, help='Incluye todos los caracteres posibles')

    parser.add_argument('-i', type=str, required=True, help='Fichero de entrada')
    parser.add_argument('-o', type=str, required=True, help='Fichero de salida')

    args = parser.parse_args()

    file_in = open(args.i, 'r')

    text = file_in.read()
    file_in.close()

    if args.A:
        allowed_pattern = r'.'
    else:
        allowed_pattern = r'a-zA-Z'
        if args.N:
            allowed_pattern += r'0-9'
        if not args.n:
            allowed_pattern += r'\n'

    pattern = f'[^{allowed_pattern}]'
    processed_text = re.sub(pattern, '', text).upper()

    file_out = open(args.o, 'w')
    file_out.write(processed_text)

    file_out.close()


if __name__ == "__main__":
    procesa_archivo()
