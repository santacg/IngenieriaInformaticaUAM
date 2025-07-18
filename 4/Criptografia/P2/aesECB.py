import argparse
import os
from io import BufferedReader, BufferedWriter


# Constantes para el AES
BYTES_PER_WORD = 4
ROWS_PER_SBOX = 16
COLUMNS_PER_SBOX = 16
HEX_STRING_SIZE = 3

DIRECT_SBOX = [
    ["63", "7c", "77", "7b", "f2", "6b", "6f", "c5", "30", "01", "67", "2b", "fe", "d7", "ab", "76"],
    ["ca", "82", "c9", "7d", "fa", "59", "47", "f0", "ad", "d4", "a2", "af", "9c", "a4", "72", "c0"],
    ["b7", "fd", "93", "26", "36", "3f", "f7", "cc", "34", "a5", "e5", "f1", "71", "d8", "31", "15"],
    ["04", "c7", "23", "c3", "18", "96", "05", "9a", "07", "12", "80", "e2", "eb", "27", "b2", "75"],
    ["09", "83", "2c", "1a", "1b", "6e", "5a", "a0", "52", "3b", "d6", "b3", "29", "e3", "2f", "84"],
    ["53", "d1", "00", "ed", "20", "fc", "b1", "5b", "6a", "cb", "be", "39", "4a", "4c", "58", "cf"],
    ["d0", "ef", "aa", "fb", "43", "4d", "33", "85", "45", "f9", "02", "7f", "50", "3c", "9f", "a8"],
    ["51", "a3", "40", "8f", "92", "9d", "38", "f5", "bc", "b6", "da", "21", "10", "ff", "f3", "d2"],
    ["cd", "0c", "13", "ec", "5f", "97", "44", "17", "c4", "a7", "7e", "3d", "64", "5d", "19", "73"],
    ["60", "81", "4f", "dc", "22", "2a", "90", "88", "46", "ee", "b8", "14", "de", "5e", "0b", "db"],
    ["e0", "32", "3a", "0a", "49", "06", "24", "5c", "c2", "d3", "ac", "62", "91", "95", "e4", "79"],
    ["e7", "c8", "37", "6d", "8d", "d5", "4e", "a9", "6c", "56", "f4", "ea", "65", "7a", "ae", "08"],
    ["ba", "78", "25", "2e", "1c", "a6", "b4", "c6", "e8", "dd", "74", "1f", "4b", "bd", "8b", "8a"],
    ["70", "3e", "b5", "66", "48", "03", "f6", "0e", "61", "35", "57", "b9", "86", "c1", "1d", "9e"],
    ["e1", "f8", "98", "11", "69", "d9", "8e", "94", "9b", "1e", "87", "e9", "ce", "55", "28", "df"],
    ["8c", "a1", "89", "0d", "bf", "e6", "42", "68", "41", "99", "2d", "0f", "b0", "54", "bb", "16"]
]

INVERSE_SBOX = [
    ["52", "09", "6a", "d5", "30", "36", "a5", "38", "bf", "40", "a3", "9e", "81", "f3", "d7", "fb"],
    ["7c", "e3", "39", "82", "9b", "2f", "ff", "87", "34", "8e", "43", "44", "c4", "de", "e9", "cb"],
    ["54", "7b", "94", "32", "a6", "c2", "23", "3d", "ee", "4c", "95", "0b", "42", "fa", "c3", "4e"],
    ["08", "2e", "a1", "66", "28", "d9", "24", "b2", "76", "5b", "a2", "49", "6d", "8b", "d1", "25"],
    ["72", "f8", "f6", "64", "86", "68", "98", "16", "d4", "a4", "5c", "cc", "5d", "65", "b6", "92"],
    ["6c", "70", "48", "50", "fd", "ed", "b9", "da", "5e", "15", "46", "57", "a7", "8d", "9d", "84"],
    ["90", "d8", "ab", "00", "8c", "bc", "d3", "0a", "f7", "e4", "58", "05", "b8", "b3", "45", "06"],
    ["d0", "2c", "1e", "8f", "ca", "3f", "0f", "02", "c1", "af", "bd", "03", "01", "13", "8a", "6b"],
    ["3a", "91", "11", "41", "4f", "67", "dc", "ea", "97", "f2", "cf", "ce", "f0", "b4", "e6", "73"],
    ["96", "ac", "74", "22", "e7", "ad", "35", "85", "e2", "f9", "37", "e8", "1c", "75", "df", "6e"],
    ["47", "f1", "1a", "71", "1d", "29", "c5", "89", "6f", "b7", "62", "0e", "aa", "18", "be", "1b"],
    ["fc", "56", "3e", "4b", "c6", "d2", "79", "20", "9a", "db", "c0", "fe", "78", "cd", "5a", "f4"],
    ["1f", "dd", "a8", "33", "88", "07", "c7", "31", "b1", "12", "10", "59", "27", "80", "ec", "5f"],
    ["60", "51", "7f", "a9", "19", "b5", "4a", "0d", "2d", "e5", "7a", "9f", "93", "c9", "9c", "ef"],
    ["a0", "e0", "3b", "4d", "ae", "2a", "f5", "b0", "c8", "eb", "bb", "3c", "83", "53", "99", "61"],
    ["17", "2b", "04", "7e", "ba", "77", "d6", "26", "e1", "69", "14", "63", "55", "21", "0c", "7d"]
]

MIX_COLUMN_MATRIX = [
    ["02", "03", "01", "01"],
    ["01", "02", "03", "01"],
    ["01", "01", "02", "03"],
    ["03", "01", "01", "02"]
]

INV_MIX_COLUMN_MATRIX = [
    ["0E", "0B", "0D", "09"],
    ["09", "0E", "0B", "0D"],
    ["0D", "09", "0E", "0B"],
    ["0B", "0D", "09", "0E"]
]

RCON = [
    "01", "02", "04", "08", "10",
    "20", "40", "80", "1B", "36"
]


def get_rcon(i: int) -> bytearray:
    return bytearray([int(RCON[i], 16), 0x00, 0x00, 0x00])


def rot_word(word: bytearray) -> bytearray:
    return word[1:] + word[:1]


def xor_bytearrays(a: bytearray, b: bytearray) -> bytearray:
    return bytearray([x ^ y for x, y in zip(a, b)])


def key_expansion(key: bytearray) -> list[bytearray]: 
    words = [key[i:i+4] for i in range(0, 16, 4)]

    for i in range(4, 44):        
        temp = words[i - 1]

        if i % 4 == 0:
            temp = xor_bytearrays(sub_bytes(rot_word(temp)), get_rcon(i // 4 - 1))
        
        new_word = xor_bytearrays(words[i - 4], temp)
        words.append(new_word)

    round_keys = [bytearray().join(words[i:i+4]) for i in range(0, 44, 4)]

    return round_keys 


def add_round_key(state: bytearray, key: bytearray):
    return xor_bytearrays(state, key)


def sub_bytes(state: bytearray) -> bytearray:
    new_state = bytearray()

    for byte in state:
        row = byte >> 4 # Byte mas significativo
        col = byte & 0x0F # Byte menos significativo

        sub = bytearray.fromhex(DIRECT_SBOX[row][col])[0]
        new_state.append(sub)

    return new_state 


def shift_rows(state: bytearray) -> bytearray:
    new_state = bytearray(16)

    # Recorro columnas
    for col in range(4):
        new_state[col] = state[col] # Fila 0
        new_state[4 + col] = state[4 + (col + 1) % 4] # Fila 1
        new_state[8 + col] = state[8 + (col + 2) % 4] # Fila 2
        new_state[12 + col] = state[12 + (col + 3) % 4] # Fila 3

    return new_state


def xtime(a: int) -> int:
    res = a << 1 # Multiplicar por x (igual que multiplicar por 2)

    if a & 0x80:
        res ^= 0x1B  # Reducción con polinomio irreducible

    return res & 0xFF


def mul2(a): return xtime(a)
def mul3(a): return mul2(a) ^ a


def mix_column(col: list[int]) -> list[int]:
    a = col
    return [
        mul2(a[0]) ^ mul3(a[1]) ^ a[2] ^ a[3], # 02*a0 ^ 03*a1 ^ 01*a2 ^ 01*a3
        a[0] ^ mul2(a[1]) ^ mul3(a[2]) ^ a[3], # 01*a0 ^ 02*a1 ^ 03*a2 ^ 01*a3
        a[0] ^ a[1] ^ mul2(a[2]) ^ mul3(a[3]), # 01*a0 ^ 01*a1 ^ 02*a2 ^ 03*a3
        mul3(a[0]) ^ a[1] ^ a[2] ^ mul2(a[3])  # 03*a0 ^ 01*a1 ^ 01*a2 ^ 02*a3
    ]


def mix_columns(state: bytearray) -> bytearray:
    new_state = bytearray(16)

    for col in range(4):
        idx = col * 4

        column = [state[idx], state[idx + 1], state[idx + 2], state[idx + 3]] # Construyo la columna
        mixed = mix_column(column)

        # Añado por filas
        for row in range(4):
            new_state[idx + row] = mixed[row]

    return new_state


def pad_pkcs7(data: bytearray) -> bytearray:
    pad_len = 16 - (len(data) % 16) # Bytes para llegar a múltiplo de 16
    padding = bytearray([pad_len] * pad_len)

    return data + padding


def encrypt_block_aes(block: bytearray, round_keys: list[bytearray]) -> bytearray:
    state = bytearray(block)

    state = xor_bytearrays(state, round_keys[0])

    for r in range(1, 10):
        state = sub_bytes(state)
        state = shift_rows(state)
        state = mix_columns(state)
        state = xor_bytearrays(state, round_keys[r])

    state = sub_bytes(state)
    state = shift_rows(state)
    state = xor_bytearrays(state, round_keys[10])

    return state


def encrypt_aes(file_in: BufferedReader, file_out: BufferedWriter):
    data = bytearray(file_in.read())

    data = pad_pkcs7(data)

    key = os.urandom(16)
    print(f'KEY: {key.hex().upper()}')

    key = bytearray(key)

    round_keys = key_expansion(key)

    encrypted = bytearray()

    for i in range(0, len(data), 16):
        block = data[i:i+16]
        encrypted_block = encrypt_block_aes(block, round_keys)
        encrypted.extend(encrypted_block)

    file_out.write(encrypted)


def inv_shift_rows(state: bytearray) -> bytearray:
    new_state = bytearray(16)

    for i in range(4):
        new_state[i] = state[i]
        new_state[4 + i] = state[4 + (i - 1) % 4]
        new_state[8 + i] = state[8 + (i - 2) % 4]
        new_state[12 + i] = state[12 + (i - 3) % 4]

    return new_state


def inv_sub_bytes(state: bytearray) -> bytearray:
    new_state = bytearray()

    for byte in state:
        row = byte >> 4
        col = byte & 0x0F

        sub = bytearray.fromhex(INVERSE_SBOX[row][col])[0]
        new_state.append(sub)

    return new_state


def mul9(b): return mul2(mul2(mul2(b))) ^ b
def mul11(b): return mul2(mul2(mul2(b)) ^ b) ^ b
def mul13(b): return mul2(mul2(mul2(b) ^ b)) ^ b
def mul14(b): return mul2(mul2(mul2(b) ^ b) ^ b)


def inv_mix_column(col):
    a = col
    return [
        mul14(a[0]) ^ mul11(a[1]) ^ mul13(a[2]) ^ mul9(a[3]),  # 14*a0 ^ 11*a1 ^ 13*a2 ^ 09*a3
        mul9(a[0])  ^ mul14(a[1]) ^ mul11(a[2]) ^ mul13(a[3]), # 09*a0 ^ 14*a1 ^ 11*a2 ^ 13*a3
        mul13(a[0]) ^ mul9(a[1])  ^ mul14(a[2]) ^ mul11(a[3]), # 13*a0 ^ 09*a1 ^ 14*a2 ^ 11*a3
        mul11(a[0]) ^ mul13(a[1]) ^ mul9(a[2])  ^ mul14(a[3])  # 11*a0 ^ 13*a1 ^ 09*a2 ^ 14*a3
    ]


def inv_mix_columns(state: bytearray) -> bytearray:
    new_state = bytearray(16)

    for col in range(4):
        idx = col * 4

        column = [state[idx], state[idx + 1], state[idx + 2], state[idx + 3]]
        mixed = inv_mix_column(column)

        for row in range(4):
            new_state[idx + row] = mixed[row]

    return new_state


def unpad_pkcs7(data: bytearray) -> bytearray:
    pad_len = data[-1]
    return data[:-pad_len]


def decrypt_block_aes(block: bytearray, round_keys: list[bytearray]) -> bytearray:
    state = xor_bytearrays(block, round_keys[10])

    state = inv_shift_rows(state)
    state = inv_sub_bytes(state)

    for r in range(9, 0, -1):
        state = xor_bytearrays(state, round_keys[r])
        state = inv_mix_columns(state)
        state = inv_shift_rows(state)
        state = inv_sub_bytes(state)

    state = xor_bytearrays(state, round_keys[0])

    return state


def decrypt_aes(file_in: BufferedReader, file_out: BufferedWriter, key: bytearray):
    ciphertext = bytearray(file_in.read())

    round_keys = key_expansion(key)
    plaintext = bytearray()

    for i in range(0, len(ciphertext), 16):
        block = ciphertext[i:i+16]
        plaintext_block = decrypt_block_aes(block, round_keys)
        plaintext.extend(plaintext_block)

    plaintext = unpad_pkcs7(plaintext)

    file_out.write(plaintext)


def aes_ecb():
    """
    Argumentos de línea de comandos:
        -C: para cifrar
        -D: para descifrar
        -k: clave hexadecimal de 128 bits para descifrar
        -i: nombre del archivo de entrada
        -o: nombre del archivo de salida
    """
    parser = argparse.ArgumentParser(description='Cifrado AES-128 en modo ECB')

    group = parser.add_mutually_exclusive_group(required=True)
    group.add_argument('-C', action='store_true', help='El programa cifra')
    group.add_argument('-D', action='store_true', help='El programa descifra')

    parser.add_argument('-k', type=str, required=False, help='Clave escrita en hexadecimal para descifrar')

    parser.add_argument('-i', type=str, required=True, help='Fichero de entrada')
    parser.add_argument('-o', type=str, required=True, help='Fichero de salida')

    args = parser.parse_args()

    file_in = open(args.i, 'rb')
    file_out = open(args.o, 'wb')

    if args.C and args.k is not None:
        print("La clave se genera aleatoriamente, no hay que introducirla para el cifrado")
        return

    if args.C:
        encrypt_aes(file_in, file_out)
    else:
        key = bytearray.fromhex(args.k.strip())

        if len(key) != 16:
            print("Longitud de clave incorrecta")
            return

        decrypt_aes(file_in, file_out, key)

    file_in.close()
    file_out.close()


if __name__ == "__main__":
    aes_ecb()

