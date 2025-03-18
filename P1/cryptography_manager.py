import os
import hmac
import hashlib
from cryptography.hazmat.primitives.ciphers import Cipher, algorithms, modes
from cryptography.hazmat.primitives import padding
from cryptography.hazmat.backends import default_backend


class CryptoManager:
    """
    Clase que representa los métodos criptográficos del sistema.

    Attributes:
        salt_length (int): Tamaño del salt.
        N (int): Factor de coste computacional de scrypt.
        R (int): Factor de uso de memoria de scrypt.
        P (int): Factor de paralelismo de scrypt.
    """


    def __init__(self, salt_length=64, N=2**14, R=8, P=1): 
        self.SALT_LENGTH = salt_length 
        self.N = N
        self.R = R
        self.P = P


    def aes_encrypt(self, data: bytes, key: bytes) -> bytes:
        """
        Método para cifrar bytes utilizando AES en modo CBC con padding PKCS7.

        Args:
            data (bytes): Datos a ser cifrados.
            key (bytes): Clave para cifrar los datos.

        Returns:
            iv de 16 bytes y datos cifrados con la clave
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
        Método para descifrar bytes que fueron cifrados con aes_encrypt.

        Args:
            encrypted_data (bytes): Datos a ser descifrados.
            key (bytes): Clave para desencriptar los datos.

        Returns:
            Datos (bytes) desencriptados con la clave 
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
        Cifra texto plano utilizando la DEK.
        """
        return self.aes_encrypt(plaintext.encode("utf-8"), key)


    def decrypt_data(self, key: bytes, encryptedtext: bytes) -> str:
        """
        Descifra los datos cifrados utilizando la DEK.
        """
        plaintext_bytes = self.aes_decrypt(encryptedtext, key)

        return plaintext_bytes.decode("utf-8")


    def generate_key(self, password: str, key_len=64):
        """
        Genera un salt aleatorio y una clave derivada a partir de la contraseña proporcionada.

        Args:
            password (str): Contraseña.

        Returns:
            salt (bytes): Salt aleatorio de longitud SALT_LENGTH.
            hash (bytes): Clave derivada de la contraseña.
        """
        salt = os.urandom(self.SALT_LENGTH)
        hash = hashlib.scrypt(
            password=password.encode("utf-8"),
            salt=salt,
            n=self.N,
            r=self.R,
            p=self.P,
            dklen=key_len
        )

        return salt, hash


    def get_dk(self, salt: bytes, password: str, key_len=64):
        """
        Devuelve una clave derivada obtenida con un salt y una contraseña.

        Args:
            salt (bytes): Salt para generar la clave derivada.
            password (str): Contraseña.

        Returns:
            La clave derivada obtenida con el salt y la password dadas
        """

        dk = hashlib.scrypt(
            password=password.encode("utf-8"),
            salt=salt,
            n=self.N, 
            r=self.R,
            p=self.P,
            dklen=key_len
        )

        return dk


    def verify_key(self, salt: bytes, hash: bytes, password: str) -> bool:
        """
        Verifica la contraseña proporcionada comparando con la clave derivada almacenada.

        Args:
            salt (bytes): Salt para generar la clave derivada.
            hash (bytes): Clave derivada a ser comprobada.
            password (str): Contraseña.

        Returns:
            True si hash y la clave calculada son iguales, False si no lo son 
        """

        dk = hashlib.scrypt(
            password=password.encode("utf-8"),
            salt=salt,
            n=self.N, 
            r=self.R,
            p=self.P,
        )

        # Con compare_digest no se puede inferir nada de la comparación
        return hmac.compare_digest(hash, dk)


    def generate_hmac(self, key: bytes, data: bytes) -> bytes:
        """
        Genera un HMAC usando SHA-256.
        
        Args:
            key (bytes): Clave para el HMAC.
            data (bytes): Datos a verificar.
            
        Returns:
            HMAC en bytes.
        """
        h = hmac.new(key, data, hashlib.sha256)
        return h.digest()


    def verify_hmac(self, key: bytes, data: bytes, expected_hmac: bytes) -> bool:
        """
        Verifica si el HMAC de los datos coincide con el esperado.
        """
        actual_hmac = self.generate_hmac(key, data)
        return hmac.compare_digest(actual_hmac, expected_hmac)

