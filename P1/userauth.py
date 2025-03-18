import os
import time
import json
from cryptography_manager import CryptoManager


class UserAuth:
    """
    Clase que representa la autenticación del usuario.

    Attributes:
        n_tries (int): Número de intentos de autenticacion.
        STORAGE_FILE (str): Ruta del archivo donde se almacenan el salt y la clave derivada de la contraseña usuario.
        salt (bytes): Salt generado aleatoriamente.
        dk (bytes): Clave derivada con la función scrypt, empleando el salt y una contraseña.
    """

    def __init__(self):
        self.n_tries = 0
        self.STORAGE_FILE = "auth.json"
        self.cryptography = CryptoManager()
        self.salt = None
        self.dk = None

    
    def save_to_file(self) -> bool:
        """
        Guarda un salt y una clave derivada en el archivo de datos del usuario.

        Args:
            salt (bytes): Salt generado.
            hash (bytes): Clave derivada de la contraseña.
        """
        if self.salt is None or self.dk is None:
            print("No se han leido los datos del usuario")
            return False

        data = {
            "salt": self.salt.hex(),
            "hash": self.dk.hex(),
        }

        try:
            with open(self.STORAGE_FILE, "w") as f:
                json.dump(data, f, indent=2)

            # Permisos lectura y escritura para solamente el usuario que ejecute
            os.chmod(self.STORAGE_FILE, 0o600)
            return True

        except Exception as e:
            print("Error al guardar los datos en el archivo:", e)
            return False


    def load_from_file(self) -> bool:
        """
        Carga el salt y la clave derivada desde el archivo de datos del usuario.

        Returns:
            salt (bytes): Salt generado.
            hash (bytes): Clave derivada de la contraseña.
        """
        try:
            with open(self.STORAGE_FILE, "r") as f:
                data = json.load(f)

            self.salt = bytes.fromhex(data["salt"])
            self.dk = bytes.fromhex(data["hash"])
            return True

        except Exception as e:
            print(f"Error al cargar los datos de autenticación: {e}")
            return False 


    def generate_key(self, password: str):
        """
        Genera un salt aleatorio y una clave derivada a partir de la contraseña proporcionada.

        Args:
            password (str): Contraseña del usuario.
        """
        self.salt, self.dk = self.cryptography.generate_key(password)


    def verify_key(self, password: str) -> bool:
        """
        Verifica la contraseña proporcionada comparando con la clave derivada almacenada.

        Args:
            password (str): Contraseña del usuario.
        """
        if self.salt is None or self.dk is None:
            print("No se han leido los datos del usuario")
            return False

        res = self.cryptography.verify_key(self.salt, self.dk, password)

        if res is True:
            self.n_tries = 0
        else:
            self.n_tries += 1
            # Si se han hecho más de 3 intentos fallidos hacemos una espera que incrementa exponencialmente 
            if self.n_tries >= 3:
                delay = 2 ** (self.n_tries - 3)
                print(f"Demasiados intentos fallidos, espera {delay} segundos")
                time.sleep(delay)

        return res
