import argparse
import galois

GF256 = galois.GF(2**8, irreducible_poly=0x11B, repr='int')
c = 0x63
d = 0x05


def affine_transform(x: int) -> int:
    result = 0

    for i in range(8):
        bit = (
            ((x >> i) & 1) ^
            ((x >> ((i + 4) % 8)) & 1) ^
            ((x >> ((i + 5) % 8)) & 1) ^
            ((x >> ((i + 6) % 8)) & 1) ^
            ((x >> ((i + 7) % 8)) & 1) ^
            ((c >> i) & 1)
        )

        result |= (bit << i)

    return result


def inverse_affine_transform(x: int) -> int:
    result = 0

    for i in range(8):
        bit = (
              (x >> ((i + 2) % 8)) & 1) \
            ^ ((x >> ((i + 5) % 8)) & 1) \
            ^ ((x >> ((i + 7) % 8)) & 1) \
            ^ ((d >> i) & 1
        )

        result |= (bit << i)

    return result


def build_sbox_direct():
    sbox = []

    for x in range(256):
        gx = GF256(x)

        if x != 0:
            gx_inv = gx ** -1
        else:
            gx_inv = GF256(0)

        sbox.append(affine_transform(int(gx_inv)))

    return sbox


def build_sbox_inverse() -> list[int]:
    inv_sbox = []
    
    for y in range(256):
        x = inverse_affine_transform(y)

        gx = GF256(x)

        if x != 0:
            gx_inv = gx ** -1
        else:
            gx_inv = GF256(0)

        inv_sbox.append(int(gx_inv))

    return inv_sbox


def aes_sbox():
    parser = argparse.ArgumentParser(description='Generación de S-Boxes del AES')

    group = parser.add_mutually_exclusive_group(required=True)

    group.add_argument('-C', action='store_true', help='Calcula la S-box directa')
    group.add_argument('-D', action='store_true', help='Calcula la S-box inversa')

    parser.add_argument('-o', type=str, required=True, help='Fichero de salida')

    args = parser.parse_args()

    if args.C:
        table = build_sbox_direct()
    else:
        table = build_sbox_inverse()

    f = open(args.o, 'w')

    for i in range(256):
        if i % 16 == 0 and i != 0:
            f.write('\n')
        f.write(f"0x{table[i]:02X} ")
    f.write('\n')

if __name__ == "__main__":
    aes_sbox()

