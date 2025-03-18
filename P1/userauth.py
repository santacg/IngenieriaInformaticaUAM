import os
import time
import json
import hashlib
import hmac


class UserAuth:
    """
    Clase que representa la autenticación del usuario.

    Attributes:
        authenticated (bool): Estado de autenticacion del usuario.
        n_tries (int): Número de intentos de autenticacion.
        salt_length (int): Tamaño del salt.
        N (int): Factor de coste computacional de scrypt.
        R (int): Factor de uso de memoria de scrypt.
        P (int): Factor de paralelismo de scrypt.
        STORAGE_FILE (str): Ruta del archivo donde se almacenan el salt y el hash.
    """

    def __init__(self):
        self.authenticated = False
        self.n_tries = 0
        self.SALT_LENGTH = 64 
        self.N = 2**14
        self.R = 8
        self.P = 1
        self.STORAGE_FILE = "auth.json"

    
    def save_to_file(self, salt: bytes, hash: bytes):
        """
        Guarda el salt y la clave derivada en el archivo.

        Args:
            salt (bytes): Salt generado.
            hash (bytes): Clave derivada de la contraseña.
        """
        data = {
            "salt": salt.hex(),
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
            hash (bytes): Clave derivada de la contraseña.
        """
        try:
            with open(self.STORAGE_FILE, "r") as f:
                data = json.load(f)

            salt = bytes.fromhex(data["salt"])
            key_hash = bytes.fromhex(data["hash"])

            return salt, key_hash

        except Exception as e:
            print(f"Error al cargar los datos de autenticación: {e}")
            return None, None


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


    def verify_key(self, password: str) -> bool:
        """
        Verifica la contraseña proporcionada comparando con la clave derivada almacenada.

        Args:
            salt (bytes): Salt generado.
            hash (bytes): Clave derivada.
            password (str): Contraseña del usuario.

        Returns:
            True si se ha verificado correctamente, False si la verificacion falla
        """
        
        try:
            salt, hash = self.load_from_file()
        except Exception as e:
            print("Error al cargar los datos de autenticación:", e)
            return False

        if salt is None or hash is None:
            print("No se encontraron datos de autenticación validos")
            return False

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
            self.n_tries = 0
            return True
        else:
            self.n_tries += 1
            # Si se han hecho más de 3 intentos fallidos hacemos una espera que incrementa exponencialmente 
            if self.n_tries >= 3:
                delay = 2 ** (self.n_tries - 3)
                print(f"Demasiados intentos fallidos, espera {delay} segundos")
                time.sleep(delay)

            return False

