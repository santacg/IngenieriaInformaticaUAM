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
        self.N = 2**14
        self.R = 8
        self.P = 1
        self.STORAGE_FILE = ".auth.sec"


    def save_to_file(self, salt: bytes, hash: bytes):
        """
        Guarda el salt, la clave derivada, la clave de encriptación y el hmac en el storage_file.

        Args:
            salt (bytes): Salt generado.
            hash (bytes): Clave derivada.
        """
        dek = os.urandom(32)

        h = hmac.new(salt + hash + dek, )

        # En hex para que sean serializables
        data = {
            "salt": salt.hex(),
            "hash": hash.hex(),
            "dek": os.urandom(32).hex(),
            "hmac": "miau"
        }

        try:
            with open(self.STORAGE_FILE, "w") as f:
                json.dump(data, f)

                # Permisos lectura y escritura para solamente el usuario que ejecute
                os.chmod(self.STORAGE_FILE, 0o600)

        except Exception as e:
            print("Error al guardar los datos de seguridad en el archivo:", e)
            raise


    def load_from_file(self):
        """
        Carga el salt y la clave derivada desde el archivo de autenticación.
        """
        if not os.path.isfile(self.STORAGE_FILE):
            raise FileNotFoundError("Error: No existen datos del usuario")

        try:
            with open(self.STORAGE_FILE, "r") as f:
                data = json.load(f)

            salt = bytes.fromhex(data["salt"])
            hash = bytes.fromhex(data["hash"])

            return salt, hash 

        except Exception as e:
            print("Error al cargar los datos de seguridad en el archivo:", e)
            raise


    def get_key(self):
        """
        Retorna la clave derivada si el usuario está autenticado.

        Returns:
            La clave derivada. None si hay un error
        """
        if self.authenticated is True:
            try:
                _, hash = self.load_from_file()
                return hash
            except Exception as e:
                print("No se pudo obtener la clave de autenticación:", e)
                return None
        else:
            return None


    def generate_key(self, password: str):
        """
        Genera y almacena la clave derivada a partir de la contraseña proporcionada.

        Args:
            password (str): Contraseña del usuario.
        """
        salt = os.urandom(self.SALT_LENGTH)
        dk = hashlib.scrypt(
            password=password.encode("utf-8"),
            salt=salt,
            n=self.N,
            r=self.R,
            p=self.P,
        )

        self.save_to_file(salt, dk)


    def verify_key(self, password: str) -> bool:
        """
        Verifica la contraseña proporcionada comparando con la clave derivada.

        Args:
            password (str): Contraseña del usuario.

        Returns:
            True si se ha verificado correctamente, False si la verificacion falla
        """
        try: 
            salt, hash = self.load_from_file()
        except Exception as e:
            print("Erorr verificando clave: ", e)
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


    def change_key(self, old_password: str, new_password: str):
        res = self.verify_key(password=old_password)

        if res is True:
            self.generate_key(new_password)
            return True
        else:
            return False


