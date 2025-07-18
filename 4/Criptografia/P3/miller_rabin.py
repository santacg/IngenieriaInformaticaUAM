import argparse
import math
import random
import time
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
    start = time.time()

    while True:
        candidate = get_prime_candidate(bits)
        if test(candidate, k):
            elapsed = time.time() - start
            return candidate, k, elapsed


def miller_rabin():
    parser = argparse.ArgumentParser(description="Generador de primos usando Miller-Rabin")
    parser.add_argument('-b', type=int, required=True, help='Número de bits del número primo a generar')
    parser.add_argument('-p', type=float, required=True, help='Probabilidad máxima de error permitida (ej: 1e-7)')

    args = parser.parse_args()
    bits = args.b
    p = args.p

    prime, k, elapsed = get_prime_number(bits, p)

    test_res = "Probablemente primo" if test(prime, k) else "Compuesto"

    start = time.time()
    test_gmp = "Probablemente primo" if gmp.is_prime(prime) > 0 else "Compuesto"
    elapsed_gmp = time.time() - start

    print(f"Número candidato: {prime}")
    print(f"Resultado del test: {test_res}")
    print(f"Resultado de GMP: {test_gmp}")
    print(f"Probabilidad de equivocación: {p}")
    print(f"Número de iteraciones del test: {k}")
    print(f"Tiempo de ejecución del test de GMP: {elapsed_gmp:.6f} segundos")
    print(f"Tiempo de ejecución del test:        {elapsed:.6f} segundos")


if __name__ == "__main__":
    miller_rabin()

