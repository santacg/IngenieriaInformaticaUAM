import os
from os.path import isfile
import time
import json
import hashlib
import hmac


class UserAuth:
    """
    Clase que representa la autenticación del usuario.

    Attributes:
        authenticated (bool): Estado de autenticacion del usuario.
        n_tries (int): Número de intentos de verificación de la clave (inicio de sesión).
        salt_length (int): Tamaño del salt.
        N (int): Factor de coste de scrypt.
        R (int): Factor de uso de memoria de scrypt.
        P (int): Factor de paralelismo de scrypt.
        STORAGE_FILE (str): Archivo para almacenar salt y hash sin encriptar.
    """

    def __init__(self):
        self.authenticated = False
        self.n_tries = 0
        self.SALT_LENGTH = 64 
        self.N = 2**20
        self.R = 8
        self.P = 1
        self.STORAGE_FILE = "auth.json"

    
    # def verify_file(self, salt: bytes, hash: bytes, hmac_file: bytes, password: str) -> bool:
    #     hmac_calc = hmac.new(bytes(password.encode()), salt + hash, hashlib.sha256).digest()
    #
    #     res = hmac.compare_digest(hmac_file, hmac_calc)
    #
    #     return res

    def save_to_file(self, salt_hash: bytes, salt_key: bytes, hash: bytes):
        """
        Guarda el salt, la clave derivada, la clave de encriptación y el hmac en el storage_file.

        Args:
            salt_hash (bytes): Salt generado.
            salt_key (bytes): Salt generado.
            hash (bytes): Clave derivada.
        """
        # En hex para que sean serializables
        data = {
            "salt_hash": salt_hash.hex(),
            "salt_key": salt_key.hex(),
            "hash": hash.hex(),
        }

        try:
            with open(self.STORAGE_FILE, "w") as f:
                json.dump(data, f, indent=2)

                # Permisos lectura y escritura para solamente el usuario que ejecute
                os.chmod(self.STORAGE_FILE, 0o600)

        except Exception as e:
            print("Error al guardar los datos de seguridad en el archivo:", e)


    def load_from_file(self):
        """
        Carga el salt y la clave derivada desde el archivo de autenticación.

        Returns:
            salt (bytes): Salt generado.
            hash (bytes): Clave derivada.
        """
        with open(self.STORAGE_FILE, "r") as f:
            data = json.load(f)

        salt_hash = bytes.fromhex(data["salt_hash"])
        salt_key = bytes.fromhex(data["salt_key"])
        hash = bytes.fromhex(data["hash"])

        return salt, hash


    def generate_key(self, password: str):
        """
        Genera un salt aleatorio y una clave derivada a partir de la contraseña proporcionada.

        Args:
            password (str): Contraseña del usuario.

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
        )

        return salt, hash


    def verify_key(self, salt: bytes, hash: bytes, password: str) -> bool:
        """
        Verifica la contraseña proporcionada comparando con la clave derivada.

        Args:
            salt (bytes): Contraseña del usuario.
            hash (bytes): Contraseña del usuario.
            password (str): Contraseña del usuario.

        Returns:
            True si se ha verificado correctamente, False si la verificacion falla
        """
        dk = hashlib.scrypt(
            password=password.encode("utf-8"),
            salt=salt,
            n=self.N, 
            r=self.R,
            p=self.P,
        )

        # Con compare_digest no se puede inferir nada de la comparación
        res = hmac.compare_digest(hash, dk)

        if res is True:
            self.authenticated = True
            self.n_tries = 0
            return True
        else:
            # Si se han hecho más de 3 intentos fallidos hacemos una espera que incrementa exponencialmente 
            if self.n_tries >= 3:
                print("Demasiados intentos, espera unos segundos")
                time.sleep(2 ** self.n_tries)

            self.n_tries += 1
            return False


    # def change_key(self, old_password: str, new_password: str):
    #     res = True
    #
    #     if res is True:
    #         self.generate_key(new_password)
    #         return True
    #     else:
    #         return False


