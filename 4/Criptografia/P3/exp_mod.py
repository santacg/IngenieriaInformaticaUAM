import argparse
import random
import time
import gmpy2 as gmp


def binary_exponentiation(b: int, e: int, m:int) -> int:
    if m == 1:
        return 0

    res = 1
    b = b % m
    while e > 0:
        if e % 2 == 1:
            res = (res * b) % m

        e = e >> 1
        b = (b * b) % m

    return res


def test():
    min_bits = 100
    max_bits = 4000
    step = 100
    num_tests = 5
    
    print(f"Ejecutando test desde {min_bits} hasta {max_bits} bits, con paso de {step} bits, "
          f"realizando {num_tests} pruebas por tamaño.\n")

    print("{:<10} {:<25} {:<10}".format("Bits", "Tiempo bin_exp (s)", "Tiempo GMP (s)"))
    
    for bits in range(min_bits, max_bits + 1, step):
        total_time = 0.0
        total_gmp_time = 0.0

        for _ in range(num_tests):
            base = random.getrandbits(bits)
            exponent = random.getrandbits(bits)
            modulo = random.getrandbits(bits)

            t0 = time.time()
            res = binary_exponentiation(base, exponent, modulo)
            t1 = time.time()
            
            t2 = time.time()
            res_gmp = gmp.powmod(base, exponent, modulo)
            t3 = time.time()

            if res != res_gmp:
                print(f"Error calculando: ({base} elevado a {exponent}) mod {modulo}\n"
                        "{res} != {res_gmp}")
            
            total_time += (t1 - t0)
            total_gmp_time += (t3 - t2)

        avg_custom = total_time / num_tests
        avg_gmp = total_gmp_time / num_tests

        print("{:<10} {:<25.6f} {:<10.6f}".format(bits, avg_custom, avg_gmp))


def exp_mod():
    parser = argparse.ArgumentParser(description="Algoritmo de potenciación modular")

    subparsers = parser.add_subparsers(title='Modos de operación', dest='modo',
        help='Elige el modo de operación: "calc" para cálculo o "test" para test automático'
    )

    subparsers.required = True 

    calc_parser = subparsers.add_parser('calc', help='Modo cálculo: requiere base, exponente y módulo')
    calc_parser.add_argument("base", type=int, help="La base")
    calc_parser.add_argument("exponente", type=int, help="El exponente")
    calc_parser.add_argument("modulo", type=int, help="El módulo")

    test_parser = subparsers.add_parser('test', help='Modo test automático')

    args = parser.parse_args()

    if args.modo == 'calc':
        res = binary_exponentiation(args.base, args.exponente, args.modulo)
        print(f"Resultado propio: ({args.base} elevado a {args.exponente}) mod {args.modulo} = {res}")
        res_gmp = gmp.powmod(args.base, args.exponente, args.modulo)
        print(f"Resultado GMP:    ({args.base} elevado a {args.exponente}) mod {args.modulo} = {res_gmp}")
    elif args.modo == 'test':
        test()


if __name__ == "__main__":
    exp_mod()

