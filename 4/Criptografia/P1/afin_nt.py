import argparse
import gmpy2 as gmp


def euclidean_gcd(a: str, b: str):
    """
    Calcula el MCD de dos números utilizando el algoritmo de Euclides y GMP.

    Args:
        a (str): Primer número en formato string.
        b (str): Segundo número en formato string.

    Returns:
        tuple: Una tupla que contiene:
            - q_list (list): Lista de cocientes obtenidos en cada iteración.
            - r_list (list): Lista de residuos obtenidos en cada iteración.
            - int: Número de pasos realizados.
    """
    r_list, q_list = [gmp.mpz(a), gmp.mpz(b)], []

    n = 1

    while r_list[n] != 0:
        q, r = gmp.f_divmod(r_list[n - 1], r_list[n])
        q_list.insert(n - 1, q)
        r_list.insert(n + 1, r)
        n += 1

    return q_list, r_list, n - 1


def euclidean_ext(q_list, r_list, n):
    """
    Calcula el inverso multiplicativo utilizando el algoritmo extendido de Euclides.

    Args:
        q_list (list): Lista de cocientes del algoritmo de Euclides.
        r_list (list): Lista de residuos del algoritmo de Euclides.
        n (int): Índice donde el residuo se hace 1.

    Returns:
        tuple: Una tupla (u, v) tal que au + bv = 1.

    Raises:
        ValueError: Si el MCD no es 1 no se puede calcular el inverso multiplicativo.
    """
    if r_list[n] != 1:
        raise ValueError()

    u_list = [gmp.mpz(1), gmp.mpz(0)]
    v_list = [gmp.mpz(0), gmp.mpz(1)]

    i = 2
    while i < n:
        u_list.append(u_list[i - 2] - q_list[i - 1] * u_list[i - 1])
        v_list.append(v_list[i - 2] - q_list[i - 1] * v_list[i - 1])
        i += 1
    
    i -= 1

    return u_list[i], v_list[i]


def encrypt(a: int, b: int, m:int, substitution: list[int], file_in, file_out):
    """
    Cifra un archivo utilizando una combinación de cifrado por sustitución y cifrado afin.

    Args:
        a (int): Coeficiente multiplicativo del cifrado afín.
        b (int): Término constante del cifrado afín.
        m (int): Tamaño del alfabeto.
        substitution (list[int]): Lista que representa la sustitución de cada letra.
        file_in (file): Archivo de entrada abierto en modo lectura.
        file_out (file): Archivo de salida abierto en modo escritura.
    """
    base_char = ord('A')

    while True:
        x = file_in.read(1)

        if not x:
            break

        if x.isalpha():
            x_ord = ord(x) - base_char

            y = substitution[x_ord]

            y = (a * y + b) % m

            file_out.write(chr(y + base_char))
        else:
            file_out.write(x)


def decrypt(b: int, m: int, inv: int, inv_substitution: list[int], file_in, file_out):
    """
    Descifra un archivo que fue cifrado con cifrado por sustitución y cifrado afín.

    Args:
        b (int): Término constante del cifrado afin.
        m (int): Tamaño del alfabeto.
        inv (int): Inverso multiplicativo de 'a' módulo m.
        inv_substitution (list[int]): Lista que representa la sustitución inversa de cada letra.
        file_in (file): Archivo cifrado abierto en modo lectura.
        file_out (file): Archivo de salida abierto en modo escritura.
    """
    base_char = ord('A')

    while True:
        y = file_in.read(1)

        if not y:
            break
        
        if y.isalpha():
            y_ord = ord(y) - base_char

            x = ((y_ord - b) * inv) % m

            x = inv_substitution[x]

            file_out.write(chr(x + base_char))
        else:
            file_out.write(y)


def afin_nt():
    """
    Argumentos de línea de comandos:
        -C: para cifrar
        -D: para descifrar
        -m: tamaño del espacio de texto cifrado
        -a: coeficiente multiplicativo
        -b: termino constante
        -p: string que contiene la permutación
        -i: nombre del archivo de entrada
        -o: nombre del archivo de salida
    """
    parser = argparse.ArgumentParser(description="Cifrado afín no trivial, se aplica una sustitución y luego la transformación afín")

    group = parser.add_mutually_exclusive_group(required=True)
    group.add_argument('-C', action='store_true', help='El programa cifra')
    group.add_argument('-D', action='store_true', help='El programa descifra')

    # Por defecto inglés
    parser.add_argument('-m', type=int, default=26, required=False, help='Tamaño del espacio de texto cifrado, por defecto 26')
    parser.add_argument('-a', type=int, required=True, help='Coeficiente multiplicativo')
    parser.add_argument('-b', type=int, required=True, help='Término constante')

    # Por defecto se aplica esta sustitucion (por eso fijo 26)
    substitution = "16 3 24 1 9 8 10 19 12 20 0 7 11 4 25 21 5 14 6 17 2 13 23 22 15 18"
    parser.add_argument('-p', type=str, default=substitution, required=False, help=f'Sustitución del alfabeto, por defecto: "{substitution}"')

    parser.add_argument('-i', type=str, required=True, help='Fichero de entrada')
    parser.add_argument('-o', type=str, required=True, help='Fichero de salida')

    args = parser.parse_args()

    if args.p:
        if len(args.p) < args.m:
            parser.error('La longitud de la lista de permutación debe \
                          ser igual a la del espacio del alfabeto')

    q, r, n = euclidean_gcd(args.a, args.m)

    if r[n] != 1:
        print(f"El MCD de {args.a} y {args.m} es {r[n]}")
        return

    substitution = [int(x) for x in args.p.split()]

    print(f"Lista de sustitución: {substitution}")
    for value in substitution:
        if substitution.count(value) > 1:
            parser.error('No pueden haber elementos repetidos dentro de la lista')

    file_in = open(args.i, 'r')
    file_out = open(args.o, 'w')

    if args.C is True:
        encrypt(args.a, args.b, args.m, substitution, file_in, file_out)
    else:
        _, inv = euclidean_ext(q, r, n)

        inv_substitution = [0] * args.m
        for i, val in enumerate(substitution):
            inv_substitution[val] = i

        decrypt(args.b, args.m, inv, inv_substitution, file_in, file_out)

    file_in.close()
    file_out.close()


if __name__ == "__main__":
    afin_nt()

