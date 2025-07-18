import argparse
import random
import gmpy2 as gmp


def vegas_attack(n: int, e: int, d: int, max_tries: int = 1000):
    phiN = e*d - 1

    t = 0
    m = phiN 
    while m % 2 == 0:
        m >>= 1
        t += 1

    for _ in range(max_tries):
        w = random.randint(2, n-2)

        g = gmp.gcd(w, n)
        if g > 1 and g < n:
            return int(g), int(n // g)

        x = gmp.powmod(w, m, n)
        
        if x == 1 or x == n-1:
            continue

        for _ in range(t - 1):
            x_next = gmp.powmod(x, 2, n)

            if x_next == 1 and x != 1 and x != n-1:
                p = gmp.gcd(x - 1, n)

                if p > 1 and p < n:
                    return int(p), int(n // p)

            x = x_next
            if x == n-1:
                break

        if x != 1:
            p = gmp.gcd(x-1, n)

            if p > 1 and p < n:
                return int(p), int(n // p)

    return None, None


def vegas():
    parser = argparse.ArgumentParser(
        description="Ataque de Las Vegas al RSA"
    )
    parser.add_argument('-n', type=int, required=True, help='Módulo RSA')
    parser.add_argument('-e', type=int, required=True, help='Exponente público e')
    parser.add_argument('-d', type=int, required=True, help='Exponente privado d')

    parser.add_argument('-t', type=int, default=1000, help='Máx. de intentos aleatorios (default=1000)')

    args = parser.parse_args()

    p, q = vegas_attack(args.n, args.e, args.d, args.t)

    if p is None or q is None:
        print(f"No se encontraron factores de n tras {args.t} intentos.")
    else:
        print("Factores encontrados:")
        print(f"p = {p}")
        print(f"q = {q}")
        print(f"Verificación (p*q = n): {p*q} = {args.n}")


if __name__ == "__main__":
    vegas()

