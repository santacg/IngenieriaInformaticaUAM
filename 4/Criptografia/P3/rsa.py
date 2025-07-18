import argparse
import random
import math
import gmpy2 as gmp


def test(n: int, k: int):
    if n <= 2:
        return -1

    n_1 = n - 1
    s = 0
    d = n_1
    while d % 2 == 0:
        d >>= 1
        s += 1

    y = 0
    for _ in range(k):
        a = random.randint(2, n - 2)
        x = gmp.powmod(a, d, n) # Uso la de gmp, la mía es mucho más lenta para números grandes

        for _ in range(s):
            y = gmp.powmod(x, 2, n)
            if y == 1 and x != 1 and x != n_1:
                return 0

            x = y

        if y != 1:
            return 0

    return 1


def get_prime_candidate(bits: int) -> int:
    candidate = random.getrandbits(bits)
    candidate |= 1
    candidate |= (1 << (bits - 1))

    return candidate


def get_n_iterations(n, error_prob):
    P = error_prob

    numerator = (1 / P) - 1
    inner = numerator * n * math.log(2)
    m = (1 / (2 * math.log(2))) * math.log(inner)

    return math.floor(m)


def get_prime_number(bits: int, error_prob: float):
    k = get_n_iterations(bits, error_prob)

    while True:
        candidate = get_prime_candidate(bits)
        if test(candidate, k):
            return candidate


def get_keys_from_file(f_key):
    p = int(f_key.readline().split(': ')[1].strip('\n'))
    q = int(f_key.readline().split(': ')[1].strip('\n'))
    n = int(f_key.readline().split(': ')[1].strip('\n'))
    e = int(f_key.readline().split(': ')[1].strip('\n'))
    d = int(f_key.readline().split(': ')[1].strip('\n'))

    return p, q, n, e, d


def get_rsa_keys(bits: int, f_out):
    p = get_prime_number(bits, 1e-10)

    q = get_prime_number(bits, 1e-10)
    while p == q:
        q = get_prime_number(bits, 1e-10)

    n = p * q

    phi_n = (p - 1) * (q - 1)

    e = 65537 # Valor fijado
    d = gmp.invert(e, phi_n)

    f_out.write(f"p: {str(p)}\n")
    f_out.write(f"q: {str(q)}\n")
    f_out.write(f"n: {str(n)}\n")
    f_out.write(f"e: {str(e)}\n")
    f_out.write(f"d: {str(d)}\n")


def encrypt(n: int, e: int, f_in, f_out):
    m = f_in.read()

    m = gmp.mpz.from_bytes(m, byteorder='big')
    if m >= n:
        print(f"No se puede encriptar, puesto que el tamaño del mensaje es mayor que n")
        return 

    encrypted_m = gmp.powmod(m, e, n)

    encrypted_bytes = int(encrypted_m).to_bytes(
        (encrypted_m.bit_length() + 7) // 8,
        byteorder='big'
    )

    f_out.write(encrypted_bytes)


def decrypt(n: int, d: int, f_in, f_out):
    m = f_in.read()

    m = gmp.mpz.from_bytes(m, byteorder='big')

    decrypted_m = gmp.powmod(m, d, n)

    decrypted_bytes = decrypted_m.to_bytes(
        (decrypted_m.bit_length() + 7) // 8,
        byteorder='big'
    )
    
    f_out.write(decrypted_bytes)


def rsa():
    parser = argparse.ArgumentParser(description="Algoritmo RSA")
   
    subparsers = parser.add_subparsers(dest='mode', required=True, help='Modo de funcionamiento')

    parser_k = subparsers.add_parser('K', help='Generar una clave aleatoria')
    parser_k.add_argument('-k', type=int, required=True, help='Número de bits de la clave')
    parser_k.add_argument('-o', dest='fileout', type=str, required=True, help='Archivo de salida para guardar la clave generada')

    parser_c = subparsers.add_parser('C', help='Cifrar un mensaje')
    group_c = parser_c.add_mutually_exclusive_group(required=True)
    group_c.add_argument('-n', nargs=2, metavar=('n', 'e'), type=int, help='Valores de n y e en base 10 (ej: 35 7); No usar junto con la opción -f')
    group_c.add_argument('-f', dest='filekey', type=str, help='Archivo con claves públicas')
    parser_c.add_argument('-i', dest='filein', type=str, required=True, help='Archivo de entrada')
    parser_c.add_argument('-o', dest='fileout', type=str, required=True, help='Archivo de salida cifrado')

    parser_d = subparsers.add_parser('D', help='Descifrar un mensaje')
    group_d = parser_d.add_mutually_exclusive_group(required=True)
    group_d.add_argument('-n', nargs=2, metavar=('mod', 'd'), type=int, help='Valores de n y d en base 10')
    group_d.add_argument('-f', dest='filekey', type=str, help='Archivo con claves privadas')
    parser_d.add_argument('-i', dest='filein', type=str, help='Archivo de entrada cifrado')
    parser_d.add_argument('-o', dest='fileout', type=str, help='Archivo de salida descifrado')

    args = parser.parse_args()

    f_in = None
    if args.mode in ['C', 'D']:
        f_in = open(args.filein, "rb")

    if args.mode == 'K':
        f_out = open(args.fileout, "w")
    else:
        f_out = open(args.fileout, "wb")

    if args.mode == 'K':
        get_rsa_keys(args.k, f_out)
    elif args.mode == 'C':
        if args.filekey != None:
            f_key = open(args.filekey, "r")
            _, _, n, e, _ = get_keys_from_file(f_key)
            f_key.close()
        else:
            n, e = args.n

        encrypt(n, e, f_in, f_out)
    elif args.mode == 'D':
        if args.filekey != None:
            f_key = open(args.filekey, "r")
            _, _, n, _, d = get_keys_from_file(f_key)
            f_key.close()
        else:
            n, d = args.n

        decrypt(n, d, f_in, f_out)

    if f_in != None:
        f_in.close()

    f_out.close()


if __name__ == "__main__":
    rsa()

