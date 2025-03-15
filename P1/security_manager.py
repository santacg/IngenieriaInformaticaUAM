import os
import json
import hashlib
import hmac
from cryptography.hazmat.primitives.ciphers.aead import AESGCM


class SecurityManager:
    def __init__(self):
        self.salt = None 
        self.hash = None
        self.iterations = None
        self.storage_file = "security.sec"
    

    def save_to_file(self):
        if self.salt == None or self.hash == None or self.iterations == None:
            print("No se ha generado una clave")
            return

        print(self.hash, self.salt, self.iterations)
        data = {
            "salt": self.salt.hex(),
            "hash": self.hash.hex(),
            "iterations": self.iterations
        }

        with open(self.storage_file, "w") as f:
            json.dump(data, f, indent=2)


    def load_from_file(self):
        if not os.path.isfile(self.storage_file):
            print("No se ha encontrado archivo de seguridad")
            return False

        try:
            with open(self.storage_file, "r", encoding="utf-8") as f:
                data = json.load(f)

            self.salt = bytes.fromhex(data["salt"])
            self.hash = bytes.fromhex(data["hash"])
            self.iterations = data["iterations"]

            return True

        except Exception as e:
            print("Miau", e)
            return False


    def generate_key(self, password, salt_length, iterations):
        if self.salt != None or self.hash != None:
            print("Clave ya generada")
            return

        self.iterations = iterations
        self.salt = os.urandom(salt_length)
        self.hash = hashlib.pbkdf2_hmac('sha256', password.encode("utf-8"), self.salt, self.iterations)

        del password


    def verify_key(self, password):
        if self.salt == None or self.hash == None or self.iterations == None:
            print("Clave no generada")
            return

        hash = hashlib.pbkdf2_hmac('sha256', password.encode("utf-8"), self.salt, self.iterations)

        return hmac.compare_digest(self.hash, hash)


    def encrypt_data(self, plaintext: str) -> bytes:
        """
        Cifra un string usando AES-GCM y retorna los datos cifrados (nonce + ciphertext).
        """
        if self.hash is None:
            print(" No hay clave de cifrado disponible.")
            return b""

        aesgcm = AESGCM(self.hash[:32])
        nonce = os.urandom(12)

        ciphertext = aesgcm.encrypt(nonce, plaintext.encode("utf-8"), None)
        return nonce + ciphertext


    def decrypt_data(self, ciphered_data: bytes) -> str:
        """
        Descifra los datos usando AES-GCM.
        """
        if self.hash is None:
            print("⚠️ No hay clave de cifrado disponible.")
            return ""

        if len(ciphered_data) < 12:
            return ""

        nonce = ciphered_data[:12]
        ciphertext = ciphered_data[12:] 

        aesgcm = AESGCM(self.hash[:32])

        try:
            plaintext = aesgcm.decrypt(nonce, ciphertext, None)
            return plaintext.decode("utf-8")
        except:
            print("Womp womp")
            return ""

security = SecurityManager()

password = "seguridad"
security.generate_key(password, 64, 10000)
security.save_to_file()

password_ingresada = input("Introduce la contraseña: ")

if security.verify_key(password_ingresada):
    print("Contraseña correcta. Acceso concedido.")
    mensaje = "Este es un mensaje ultra secreto."
    datos_cifrados = security.encrypt_data(mensaje)
    print("Datos cifrados:", datos_cifrados)

    mensaje_descifrado = security.decrypt_data(datos_cifrados)
    print("Mensaje descifrado:", mensaje_descifrado)

else:
    print("Contraseña incorrecta. Acceso denegado.")

