import argparse
from io import BufferedReader, BufferedWriter
from bitarray import bitarray
from bitarray import util
from bitarray.util import ba2hex, ba2int, hex2ba, int2ba

# Constantes para el DES
BITS_IN_PC1 = 56
BITS_IN_PC2 = 48
ROUNDS = 16
BITS_IN_IP = 64
BITS_IN_E = 48
BITS_IN_P = 32
NUM_S_BOXES = 8
ROWS_PER_SBOX = 4
COLUMNS_PER_SBOX = 16

# Permutación PC1
PC1 = [
    57, 49, 41, 33, 25, 17, 9,
    1, 58, 50, 42, 34, 26, 18,
    10, 2, 59, 51, 43, 35, 27,
    19, 11, 3, 60, 52, 44, 36,
    63, 55, 47, 39, 31, 23, 15,
    7, 62, 54, 46, 38, 30, 22,
    14, 6, 61, 53, 45, 37, 29,
    21, 13, 5, 28, 20, 12, 4
]

# Permutación PC2
PC2 = [
    14, 17, 11, 24, 1, 5,
    3, 28, 15, 6, 21, 10,
    23, 19, 12, 4, 26, 8,
    16, 7, 27, 20, 13, 2,
    41, 52, 31, 37, 47, 55,
    30, 40, 51, 45, 33, 48,
    44, 49, 39, 56, 34, 53,
    46, 42, 50, 36, 29, 32
]

# Rotaciones por ronda
ROUND_SHIFTS = [
    1, 1, 2, 2, 2, 2, 2, 2,
    1, 2, 2, 2, 2, 2, 2, 1
]

# Permutación IP
IP = [
    58, 50, 42, 34, 26, 18, 10, 2,
    60, 52, 44, 36, 28, 20, 12, 4,
    62, 54, 46, 38, 30, 22, 14, 6,
    64, 56, 48, 40, 32, 24, 16, 8,
    57, 49, 41, 33, 25, 17, 9, 1,
    59, 51, 43, 35, 27, 19, 11, 3,
    61, 53, 45, 37, 29, 21, 13, 5,
    63, 55, 47, 39, 31, 23, 15, 7
]

# Inversa de IP
IP_INV = [
    40, 8, 48, 16, 56, 24, 64, 32,
    39, 7, 47, 15, 55, 23, 63, 31,
    38, 6, 46, 14, 54, 22, 62, 30,
    37, 5, 45, 13, 53, 21, 61, 29,
    36, 4, 44, 12, 52, 20, 60, 28,
    35, 3, 43, 11, 51, 19, 59, 27,
    34, 2, 42, 10, 50, 18, 58, 26,
    33, 1, 41, 9, 49, 17, 57, 25
]

# Expansión E
E = [
    32, 1, 2, 3, 4, 5,
    4, 5, 6, 7, 8, 9,
    8, 9, 10, 11, 12, 13,
    12, 13, 14, 15, 16, 17,
    16, 17, 18, 19, 20, 21,
    20, 21, 22, 23, 24, 25,
    24, 25, 26, 27, 28, 29,
    28, 29, 30, 31, 32, 1
]

# Permutación P
P = [
    16, 7, 20, 21,
    29, 12, 28, 17,
    1, 15, 23, 26,
    5, 18, 31, 10,
    2, 8, 24, 14,
    32, 27, 3, 9,
    19, 13, 30, 6,
    22, 11, 4, 25
]

# Cajas S
S_BOXES = [
    [
        [14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7],
        [0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8],
        [4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0],
        [15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13],
    ],
    [
        [15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10],
        [3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5],
        [0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15],
        [13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9],
    ],
    [
        [10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8],
        [13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1],
        [13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7],
        [1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12],
    ],
    [
        [7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15],
        [13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9],
        [10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4],
        [3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14],
    ],
    [
        [2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9],
        [14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6],
        [4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14],
        [11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3],
    ],
    [
        [12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11],
        [10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8],
        [9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6],
        [4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13],
    ],
    [
        [4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1],
        [13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6],
        [1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2],
        [6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12],
    ],
    [
        [13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7],
        [1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2],
        [7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8],
        [2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11],
    ],
]


def circular_shift(bitarray: bitarray, shift: int) -> bitarray:
    return bitarray[shift:] + bitarray[:shift]


def generate_subkeys(key: bitarray) -> list[bitarray]: 
    # Permutamos la clave con PC1
    permutated_key = bitarray([key[i - 1] for i in PC1])

    # Obtenemos la parte izquierda y derecha
    c = permutated_key[:28]
    d = permutated_key[28:]

    # Construimos las subclaves con las rotaciones izquierdas
    subkeys = []
    for i in range(ROUNDS):
        c = circular_shift(c, ROUND_SHIFTS[i])
        d = circular_shift(d, ROUND_SHIFTS[i])

        subkey = c + d
        permutated_subkey = bitarray([subkey[i - 1] for i in PC2])

        subkeys.append(permutated_subkey)

    return subkeys


def f_function(right: bitarray, subkey: bitarray) -> bitarray:
    # Expansión con la caja E
    expanded_right = bitarray([right[i - 1] for i in E])

    # Mezcla XOR con la subclave correspondiente
    xor_mix = subkey ^ expanded_right

    # Sustición con las cajas S
    group_len = BITS_IN_E // NUM_S_BOXES
    substition = bitarray()

    for i in range(NUM_S_BOXES):
        group = xor_mix[i * group_len:group_len * (i + 1)]

        row = ba2int(group[0:1] + group[-1:])
        col = ba2int(group[1:-1])

        s_box_value = S_BOXES[i][row][col]
        substition += int2ba(s_box_value, length=4)

    # Permutación con la caja P
    permutation = bitarray([substition[i - 1] for i in P])

    return permutation


def encrypt_block(block: bitarray, subkeys: list[bitarray]) -> bitarray:
    # Permutación inicial IP
    block = bitarray([block[i - 1] for i in IP])

    left = block[:32]
    right = block[32:]

    # Rondas Feistel
    for i in range(ROUNDS):
        new_left = right 
        new_right = left ^ f_function(right, subkeys[i])
        left, right = new_left, new_right

    # Concatenación inversa
    block = right + left

    # Permutación inicial inversa IP_INV
    block = bitarray([block[i - 1] for i in IP_INV])

    return block


def pad_pkcs7(data: bytes) -> bytes:
    pad_len = 8 - (len(data) % 8)
    return data + bytes([pad_len] * pad_len)


def encrypt(file_in: BufferedReader, file_out: BufferedWriter):
    data = file_in.read()
    padded_data = pad_pkcs7(data)

    bits_in = bitarray()
    bits_in.frombytes(padded_data)

    key = util.urandom(64)
    subkeys = generate_subkeys(key)
    print(f'KEY: {ba2hex(key).upper()}')

    iv = util.urandom(64)
    print(f'IV: {ba2hex(iv).upper()}')

    bits_out = bitarray()
    block_out = bitarray()

    n_blocks = len(bits_in) // 64

    prev_cipher = iv

    for i in range(n_blocks):
        block_in = bits_in[i * 64:(i + 1) * 64]
        block_in ^= prev_cipher

        block_out = encrypt_block(block_in, subkeys)

        bits_out += block_out

        prev_cipher = block_out

    file_out.write(bits_out.tobytes())


def unpad_pkcs7(data: bytes) -> bytes:
    pad_len = data[-1]
    return data[:-pad_len]


def decrypt(file_in: BufferedReader, file_out: BufferedWriter, key_hex: str, iv_hex: str):
    bits_in = bitarray()
    bits_in.frombytes(file_in.read())

    key = hex2ba(key_hex)
    subkeys = generate_subkeys(key)
    subkeys.reverse()
    print(f'KEY: {ba2hex(key).upper()}')

    iv = hex2ba(iv_hex)
    print(f'IV: {ba2hex(iv).upper()}')

    bits_out = bitarray()
    block_out = bitarray()

    n_blocks = len(bits_in) // 64

    prev_cipher = iv

    for i in range(n_blocks):
        block_in = bits_in[i * 64:(i + 1) * 64]

        block_out = encrypt_block(block_in, subkeys)

        block_out ^= prev_cipher
        prev_cipher = block_in

        bits_out += block_out

    decrypted_bytes = bits_out.tobytes()
    unpadded = unpad_pkcs7(decrypted_bytes)
    file_out.write(unpadded)


def desCBC():
    """
    Argumentos de línea de comandos:
        -C: para cifrar.
        -D: para descifrar.
        -k: clave hexadecimal de 64 bits para descifrar.
        -i: nombre del archivo de entrada.
        -o: nombre del archivo de salida.
    """
    parser = argparse.ArgumentParser(description='Cifrado DES en modo CBC')

    group = parser.add_mutually_exclusive_group(required=True)
    group.add_argument('-C', action='store_true', help='El programa cifra')
    group.add_argument('-D', action='store_true', help='El programa descifra')

    parser.add_argument('-k', type=str, required=False, help='Clave para descifrar (escrita en hexadecimal)')
    parser.add_argument('-iv', type=str, required=False, help='Bloque de inicialización para descifrar (escrito en hexadecimal)')

    parser.add_argument('-i', type=str, required=True, help='Fichero de entrada')
    parser.add_argument('-o', type=str, required=True, help='Fichero de salida')

    args = parser.parse_args()

    file_in = open(args.i, 'rb')
    file_out = open(args.o, 'wb')

    if args.C and (args.k is not None or args.iv is not None):
        print("La clave y el IV se generan aleatoriamente, no hay que introducirlos para el cifrado")
        return

    if args.C is True:
        encrypt(file_in, file_out)
    else:
        if not args.k or not args.iv:
            parser.error("Se requiere una clave con -k para descifrar.")

        if len(args.k) != 16:
            print("Longitud de clave incorrecta")
            return

        if len(args.iv) != 16:
            print("Longitud de IV incorrecta")
            return

        decrypt(file_in, file_out, args.k, args.iv) 

    file_in.close()
    file_out.close()


if __name__ == "__main__":
    desCBC()

