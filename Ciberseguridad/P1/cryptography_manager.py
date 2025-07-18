import os
import hmac
import hashlib
from cryptography.hazmat.primitives.ciphers import Cipher, algorithms, modes
from cryptography.hazmat.primitives import padding
from cryptography.hazmat.backends import default_backend


class CryptoManager:
    """
    Clase para gestionar operaciones criptográficas, incluyendo cifrado AES, generación de claves 
    con scrypt y verificacon de integridad mediante HMAC.

    Atributos:
        SALT_LENGTH (int): Longitud del salt utilizado en la derivación de claves.
        N (int): Factor de coste computacional para scrypt.
        R (int): Factor de uso de memoria para scrypt.
        P (int): Factor de paralelismo para scrypt.
    """

    def __init__(self, salt_length=64, N=2**14, R=8, P=1):
        self.SALT_LENGTH = salt_length
        self.N = N
        self.R = R
        self.P = P


    def aes_encrypt(self, data: bytes, key: bytes) -> bytes:
        """
        Cifra datos utilizando AES en modo CBC con padding PKCS7.

        Args:
            data (bytes): Datos a cifrar.
            key (bytes): Clave de cifrado AES (debe ser de 16, 24 o 32 bytes).

        Returns:
            bytes: Datos cifrados con un IV de 16 bytes prepended.
        """
        iv = os.urandom(16)

        padder = padding.PKCS7(128).padder()
        padded_data = padder.update(data) + padder.finalize()

        cipher = Cipher(algorithms.AES(key), modes.CBC(iv), backend=default_backend())
        encryptor = cipher.encryptor()
        ciphertext = encryptor.update(padded_data) + encryptor.finalize()

        return iv + ciphertext


    def aes_decrypt(self, encrypted_data: bytes, key: bytes) -> bytes:
        """
        Descifra datos previamente cifrados con AES en modo CBC.

        Args:
            encrypted_data (bytes): Datos cifrados con un IV prepended.
            key (bytes): Clave AES utilizada para el descifrado.

        Returns:
            bytes: Datos descifrados en su formato original.
        """
        if len(encrypted_data) < 16:
            raise ValueError("Datos insuficientes para extraer el IV.")

        iv = encrypted_data[:16]
        ciphertext = encrypted_data[16:]

        cipher = Cipher(algorithms.AES(key), modes.CBC(iv), backend=default_backend())
        decryptor = cipher.decryptor()

        padded_data = decryptor.update(ciphertext) + decryptor.finalize()
        unpadder = padding.PKCS7(128).unpadder()
        data = unpadder.update(padded_data) + unpadder.finalize()

        return data


    def encrypt_data(self, key: bytes, plaintext: str) -> bytes:
        """
        Cifra una cadena de texto utilizando AES.

        Args:
            key (bytes): Clave AES para cifrar los datos.
            plaintext (str): Texto a cifrar.

        Returns:
            bytes: Datos cifrados.
        """
        return self.aes_encrypt(plaintext.encode("utf-8"), key)


    def decrypt_data(self, key: bytes, encryptedtext: bytes) -> str:
        """
        Descifra una cadena de texto cifrada con AES.

        Args:
            key (bytes): Clave AES para descifrar los datos.
            encryptedtext (bytes): Datos cifrados.

        Returns:
            str: Texto descifrado.
        """
        plaintext_bytes = self.aes_decrypt(encryptedtext, key)
        return plaintext_bytes.decode("utf-8")


    def generate_key(self, password: str, key_len=64):
        """
        Genera un salt aleatorio y una clave derivada a partir de la contraseña usando scrypt.

        Args:
            password (str): Contraseña del usuario.
            key_len (int, opcional): Longitud de la clave derivada (por defecto 64 bytes).

        Returns:
            salt (bytes): Salt aleatorio generado.
            dk (bytes): Clave derivada a partir de la contraseña y el salt.
        """
        salt = os.urandom(self.SALT_LENGTH)
        dk = hashlib.scrypt(
            password=password.encode("utf-8"),
            salt=salt,
            n=self.N,
            r=self.R,
            p=self.P,
            dklen=key_len
        )

        return salt, dk


    def get_dk(self, salt: bytes, password: str, key_len=64) -> bytes:
        """
        Deriva una clave a partir de un salt y una contraseña usando scrypt.

        Args:
            salt (bytes): Salt utilizado en la derivación de clave.
            password (str): Contraseña del usuario.
            key_len (int, opcional): Longitud de la clave derivada (por defecto 64 bytes).

        Returns:
            bytes: Clave derivada a partir del salt y la contraseña.
        """
        return hashlib.scrypt(
            password=password.encode("utf-8"),
            salt=salt,
            n=self.N,
            r=self.R,
            p=self.P,
            dklen=key_len
        )


    def verify_key(self, salt: bytes, hash: bytes, password: str) -> bool:
        """
        Verifica la contrasea comparándola con la clave derivada almacenada.

        Args:
            salt (bytes): Salt utilizado para derivar la clave.
            hash (bytes): Clave derivada almacenada.
            password (str): Contraseña ingresada por el usuario.

        Returns:
            True si la contraseña es válida, False en caso contrario.
        """
        dk = self.get_dk(salt, password, len(hash))
        return hmac.compare_digest(hash, dk)


    def generate_hmac(self, key: bytes, data: bytes) -> bytes:
        """
        Genera un HMAC-SHA256 para verificar la integridad de los datos.

        Args:
            key (bytes): Clave para el HMAC.
            data (bytes): Datos sobre los que se calculará el HMAC.

        Returns:
            bytes: HMAC calculado.
        """
        return hmac.new(key, data, hashlib.sha256).digest()


    def verify_hmac(self, key: bytes, data: bytes, expected_hmac: bytes) -> bool:
        """
        Verifica si el HMAC de los datos coincide con el esperado.

        Args:
            key (bytes): Clave HMAC utilizada para la verificación.
            data (bytes): Datos sobre los que se verificará el HMAC.
            expected_hmac (bytes): HMAC esperado.

        Returns:
            True si los HMAC coinciden, False en caso contrario.
        """
        actual_hmac = self.generate_hmac(key, data)
        return hmac.compare_digest(actual_hmac, expected_hmac)

