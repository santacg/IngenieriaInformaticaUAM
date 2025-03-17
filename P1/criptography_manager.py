import os
import hmac
import hashlib
from cryptography.hazmat.primitives.ciphers import Cipher, algorithms, modes
from cryptography.hazmat.primitives import padding
from cryptography.hazmat.backends import default_backend

class CryptoManager:
    def __init__(self, key: bytes):
        """
        Inicializa el CryptoManager con la clave KEK y define el archivo de almacenamiento.
        """
        self.key = key 


    def _aes_encrypt(self, data: bytes, key: bytes) -> bytes:
        """
        Método auxiliar para cifrar datos utilizando AES en modo CBC con padding PKCS7.
        """
        iv = os.urandom(16)

        padder = padding.PKCS7(128).padder()
        padded_data = padder.update(data) + padder.finalize()

        cipher = Cipher(algorithms.AES(key), modes.CBC(iv), backend=default_backend())
        encryptor = cipher.encryptor()

        ciphertext = encryptor.update(padded_data) + encryptor.finalize()

        return iv + ciphertext


    def _aes_decrypt(self, ciphered_data: bytes, key: bytes) -> bytes:
        """
        Método auxiliar para descifrar datos que fueron cifrados con _aes_encrypt.
        """
        if len(ciphered_data) < 16:
            raise ValueError("Datos insuficientes para extraer el IV.")

        iv = ciphered_data[:16]

        ciphertext = ciphered_data[16:]
        cipher = Cipher(algorithms.AES(key), modes.CBC(iv), backend=default_backend())

        decryptor = cipher.decryptor()

        padded_data = decryptor.update(ciphertext) + decryptor.finalize()
        unpadder = padding.PKCS7(128).unpadder()

        data = unpadder.update(padded_data) + unpadder.finalize()

        return data


    def encrypt_data(self, plaintext: str) -> bytes:
        """
        Cifra el texto plano utilizando la DEK a través del método auxiliar.
        """
        if self.key is None:
            raise ValueError("La DEK no ha sido cargada o definida.")
 
        return self._aes_encrypt(plaintext.encode("utf-8"), self.key)


    def decrypt_data(self, ciphered_data: bytes) -> str:
        """
        Descifra los datos cifrados utilizando la DEK a través del método auxiliar.
        """
        if self.key is None:
            raise ValueError("La DEK no ha sido cargada o definida.")

        plaintext_bytes = self._aes_decrypt(ciphered_data, self.key)

        return plaintext_bytes.decode("utf-8")

