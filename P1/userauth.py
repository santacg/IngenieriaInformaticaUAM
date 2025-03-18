import os
import time
import json
from cryptography_manager import CryptoManager


class UserAuth:
    """
    Clase para gestionar la autenticación del usuario mediante una clave derivada segura.

    Atributos:
        n_tries (int): Número de intentos fallidos de autenticación.
        STORAGE_FILE (str): Ruta del archivo donde se almacenan el salt y la clave derivada del usuario.
        salt (bytes): Salt generado aleatoriamente para la derivación de la clave.
        dk (bytes): Clave derivada a partir de la contraseña usando scrypt.
    """

    def __init__(self):
        self.n_tries = 0
        self.STORAGE_FILE = "auth.json"
        self.cryptography = CryptoManager()
        self.salt = None
        self.dk = None


    def save_to_file(self) -> bool:
        """
        Guarda el salt y la clave derivada en un archivo JSON.

        Returns:
            True si la operación fue exitosa, False en caso de error.
        """
        if self.salt is None or self.dk is None:
            print("No se han leído los datos del usuario.")
            return False

        data = {
            "salt": self.salt.hex(),
            "hash": self.dk.hex(),
        }

        try:
            with open(self.STORAGE_FILE, "w") as f:
                json.dump(data, f, indent=2)

            # Permisos de solo lectura y escritura para el usuario que ejecuta
            os.chmod(self.STORAGE_FILE, 0o600)
            return True

        except Exception as e:
            print("Error al guardar los datos en el archivo:", e)
            return False


    def load_from_file(self) -> bool:
        """
        Carga el salt y la clave derivada desde el archivo de datos del usuario.

        Returns:
            True si los datos se cargaron correctamente, False en caso contrario.
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
        Genera un nuevo salt y una clave derivada a partir de la contraseña del usuario.

        Args:
            password (str): Contraseña del usuario.
        """
        self.salt, self.dk = self.cryptography.generate_key(password)


    def verify_key(self, password: str) -> bool:
        """
        Verifica si la contraseña ingresada es correcta comparándola con la clave derivada almacenada.

        Implementa un mecanismo de retraso exponencial en caso de múltiples intentos fallidos.

        Args:
            password (str): Contraseña del usuario.

        Returns:
            True si la contraseña es correcta, False si es incorrecta.
        """
        if self.salt is None or self.dk is None:
            print("No se han leído los datos del usuario.")
            return False

        res = self.cryptography.verify_key(self.salt, self.dk, password)

        if res:
            self.n_tries = 0  # Reiniciamos el contador de intentos fallidos si hay exito
        else:
            self.n_tries += 1

            # Retraso exponencial si hay 3 o más intentos fallidos
            if self.n_tries >= 3:
                delay = 2 ** (self.n_tries - 3)
                print(f"Demasiados intentos fallidos, espera {delay} segundos.")
                time.sleep(delay)

        return res

