import argparse
import gmpy2 as gmp
import numpy as np
import matplotlib.pyplot as plt
from collections import Counter

m = 26

coprimes = [1, 3, 5, 7, 9, 11, 15, 17, 19, 21, 23, 25]

weights_a = [0.2 if a in [1, 11, 21] else 0.05 for a in coprimes]
weights_a = np.array(weights_a)
weights_a /= weights_a.sum()

constants = list(range(m))
weights_b = [0.1 if b in [0, 13, 25] else 0.03 for b in constants]

weights_b = np.array(weights_b)
weights_b /= weights_b.sum()


def euclidean_gcd(a: str, b: str):
    r_list, q_list = [gmp.mpz(a), gmp.mpz(b)], []

    n = 1

    while r_list[n] != 0:
        q, r = gmp.f_divmod(r_list[n - 1], r_list[n])
        q_list.insert(n - 1, q)
        r_list.insert(n + 1, r)
        n += 1

    return q_list, r_list, n - 1


def euclidean_gcd_extended(q_list, r_list, n):
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


def get_key(mode):
    if mode == 'P':
        a = np.random.choice(coprimes)
        b = np.random.choice(constants)
        return a, b
    else:
        a = np.random.choice(coprimes, p=weights_a)
        b = np.random.choice(constants, p=weights_b)
        return a, b


def encrypt(mode, plain_text):
    base_char = ord('A')
    encrypted_text = ''

    key_a, key_b = [], []
    for x in plain_text:
        if x.isalpha():
            x_ord = ord(x) - base_char
            a, b = get_key(mode)
            y = (a * x_ord + b) % m

            encrypted_text += chr(y + base_char)

            key_a.append(a)
            key_b.append(b)

    return encrypted_text, key_a, key_b


def get_probs(plain_text: str, encrypted_text: str):
    base_char = ord('A')
    total = len(plain_text)

    p_x_counter = {chr(base_char + i): 0 for i in range(m)}
    p_y_counter = {chr(base_char + i): 0 for i in range(m)}
    p_xy_counter = {(chr(base_char + i), chr(base_char + j)): 0 for i in range(m) for j in range(m)}

    for c1, c2 in zip(plain_text, encrypted_text):
        p_x_counter[c1] += 1
        p_y_counter[c2] += 1
        p_xy_counter[(c1, c2)] += 1

    p_x = [p_x_counter[chr(base_char + i)] / total for i in range(m)]
    p_y = [p_y_counter[chr(base_char + i)] / total for i in range(m)]

    p_xy = [
        [p_xy_counter[(chr(base_char + i), chr(base_char + j))] / total for j in range(m)]
        for i in range(m)
    ]

    p_x_given_y = [
        [
            (p_xy[i][j] / p_y[j]) if p_y[j] > 0 else 0.0
            for j in range(m)
        ]
        for i in range(m)
    ]

    for i in range(m):
        print(f"Pp({chr(base_char + i)}) = {p_x[i]:.4f}")
    print()
    for i in range(m):
        row = []
        for j in range(m):
            x = chr(base_char + i)
            y = chr(base_char + j)
            row.append(f"Pp({x}|{y}) = {p_x_given_y[i][j]:.6f}")
        print('  '.join(row))
        print()

    return p_x, p_x_given_y


def plot_histograms(p_x, p_x_given_y, key_a, key_b):
    labels = [chr(ord('A') + i) for i in range(m)]

    plt.figure(figsize=(10, 4))
    plt.bar(labels, p_x)
    plt.title("Distribución de P(X)")
    plt.xlabel("Letra original")
    plt.ylabel("Probabilidad")
    plt.tight_layout()
    plt.show()

    x_index = 0
    probs = p_x_given_y[x_index]

    plt.figure(figsize=(12, 5))
    plt.bar(labels, probs)
    plt.title("Distribución de P(A|Y)")
    plt.xlabel("Y (letra cifrada)")
    plt.ylabel("P(A|Y)")
    plt.tight_layout()
    plt.show()

    a_counts = Counter(key_a)
    a_labels = sorted(a_counts)
    a_values = [a_counts[a] for a in a_labels]

    plt.figure(figsize=(10, 4))
    plt.bar([str(a) for a in a_labels], a_values)
    plt.title("Frecuencia de claves 'a'")
    plt.xlabel("Clave a")
    plt.ylabel("Frecuencia")
    plt.tight_layout()
    plt.show()

    b_counts = Counter(key_b)
    b_labels = sorted(b_counts)
    b_values = [b_counts[b] for b in b_labels]

    plt.figure(figsize=(10, 4))
    plt.bar([str(b) for b in b_labels], b_values)
    plt.title("Frecuencia de claves 'b'")
    plt.xlabel("Clave b")
    plt.ylabel("Frecuencia")
    plt.tight_layout()
    plt.show()



def seg_perf():
    """
    Argumentos de línea de comandos:
        -P: activa método equiprobable.
        -I: activa método no equiprobable.
        -i: nombre del archivo de entrada.
        -o: nombre del archivo de salida.
    """
    parser = argparse.ArgumentParser(description='Seguridad perfecta')

    group = parser.add_mutually_exclusive_group(required=True)
    group.add_argument('-P', action='store_true', help='Se utiliza el modo equiprobable')
    group.add_argument('-I', action='store_true', help='Se utiliza el modo no equiprobable')

    parser.add_argument('-i', type=str, required=True, help='Fichero de entrada')
    #parser.add_argument('-o', type=str, required=True, help='Fichero de salida')

    args = parser.parse_args()

    file_in = open(args.i, 'r')
    plain_text = file_in.read()
    # file_out = open(args.o, 'w')

    if args.P:
        encrypted_text, keys_a, keys_b = encrypt('P', plain_text)
    else:
        print("weights_a:", weights_a)
        print("weights_b:", weights_b)
        encrypted_text, keys_a, keys_b = encrypt('I', plain_text)

    p_x, p_x_given_y = get_probs(plain_text, encrypted_text)

    plot_histograms(p_x, p_x_given_y, keys_a, keys_b)
    file_in.close()
    #file_out.close()


if __name__ == "__main__":
    seg_perf()

