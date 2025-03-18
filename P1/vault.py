from container import Container
from cryptography_manager import CryptoManager
import os
import json
import curses
import curses.textpad


class Vault:
    """
    Esta clase representa un Vault.

    Atributos:
        containers (dict): Lista de objetos tipo Container
    """

    _instance = None

    def __new__(cls):
        """
        Método que controla la creación de instancias de Vault.
        Si ya existe una instancia, devuelve esa misma.
        """
        if cls._instance is None:
            cls._instance = super(Vault, cls).__new__(cls)
            cls._instance._initialized = False

        return cls._instance


    def __init__(self):
        """
        Inicializa una objeto Vault

        Args:
            storage_file (str): Ruta al archivo en el que se almacenarán los contenedores con los secretos.
        """
        if self._initialized:
            return

        self.STORAGE_FILE = "vault.json" 
        self.containers = {}
        self.salt = None
        self.kek = None
        self.dek = None
        self.cryptography = CryptoManager()
        self._initialized = True


    def generate_keys(self, password: str):
        """
        Genera claves de cifrado a partir de una contraseña.

        Args:
            password (str): La contraseña con la que se generarán las claves.
        """
        self.dek = os.urandom(32)
        # Encriptamos la DEK con una KEK que es una DK de la contraseña
        self.salt, self.kek = self.cryptography.generate_key(password, key_len=32)


    def load_from_file(self, password: str) -> bool:
        """
        Carga los contenedores desde un archivo JSON y verifica su integridad.

        Args:
            password (str): Contraseña para descifrar los datos.

        Returns:
            bool: True si la carga fue exitosa, False si hubo un error.
        """
        try:
            with open(self.STORAGE_FILE, "r") as f:
                data = json.load(f)

            stored_hmac = bytes.fromhex(data.pop("hmac"))

            json_data = json.dumps(data).encode("utf-8")

            self.salt = bytes.fromhex(data["salt"])
            self.kek = self.cryptography.get_dk(self.salt, password, key_len=32)

            if not self.cryptography.verify_hmac(self.kek, json_data, stored_hmac):
                print("Error de integridad. El archivo ha sido modificado.")
                return False

            encrypted_dek = bytes.fromhex(data["key"])
            self.dek = self.cryptography.aes_decrypt(encrypted_dek, self.kek)

            if self.salt is None or self.kek is None or self.dek is None:
                print("Error leyendo datos")
                return False

            self.containers = {}

            for info in data.get("containers", []):
                container_id = info["id"]
                self.containers[container_id] = Container(
                    id=container_id,
                    name=info["name"],
                    secrets=self.cryptography.decrypt_data(self.dek, bytes.fromhex(info["secrets"]))
                )

            return True

        except Exception as e:
            print("Error al cargar desde el archivo:", e)
            return False


    def save_to_file(self) -> bool:
        """
        Guarda los contenedores y las claves en un archivo JSON cifrado.

        Returns:
            True si la operación fue exitosa, False en caso contrario.
        """
        if self.salt is None or self.dek is None or self.kek is None:
            return False

        encrypted_dek = self.cryptography.aes_encrypt(self.dek, self.kek)
        data_to_save = {
            "key": encrypted_dek.hex(),
            "salt": self.salt.hex(),
            "containers": []
        }

        for id, container in self.containers.items():
            if container is None:
                continue

            encrypted_secret = self.cryptography.encrypt_data(self.dek, container.secrets).hex()
            data_to_save["containers"].append({
                "id": id,
                "name": container.name,
                "secrets": encrypted_secret
            })

        json_data = json.dumps(data_to_save).encode("utf-8")

        hmac_key = self.kek
        data_to_save["hmac"] = self.cryptography.generate_hmac(hmac_key, json_data).hex()

        with open(self.STORAGE_FILE, "w") as f:
            json.dump(data_to_save, f, indent=2)

        os.chmod(self.STORAGE_FILE, 0o600)

        return True


    def get_container_by_id(self, id: str):
        """
        Busca un contenedor por su ID.

        Args:
            id (str): ID del contenedor.

        Returns:
            Container | None: Contenedor encontrado o None si no existe.
        """
        return self.containers.get(id)


    def get_container_by_name(self, name: str):
        """
        Busca un contenedor por su nombre.

        Args:
            name (str): Nombre del contenedor.

        Returns:
            Container | None: Contenedor encontrado o None si no existe.
        """
        for container in self.containers.values():
            if container is not None and container.name == name:
                return container

        return None


    def create_container(self, id: str, name: str) -> bool:
        """
        Crea y añade un nuevo contenedor al Vault.

        Args:
            id (str): Identificador del contenedor.
            name (str): Nombre del contenedor.

        Returns:
            True si el contenedor se creó con éxito, False si ya existe.
        """
        if self.get_container_by_id(id) is not None:
            print(f"Ya existe un contenedor con ID {id}.")
            return False

        if self.get_container_by_name(name) is not None:
            print(f"Ya existe un contenedor con nombre '{name}'.")
            return False

        container = Container(id, name, "")
        self.containers[id] = container

        return True


    def update_container_secrets(self, id: str) -> bool:
        """
        Actualiza los secretos almacenados en un contenedor.

        Args:
            id (str): ID del contenedor a actualizar.

        Returns:
            True si la actualización fue exitosa, False si no se encontró el contenedor.
        """
        container = self.get_container_by_id(id)
        if not container:
            print(f"No se encontró contenedor con ID {id}.")
            return False

        initial_secrets = str(container.secrets) if container.secrets else ""
        updated_secrets = self._edit_text(initial_secrets)

        container.secrets = updated_secrets

        return True


    def update_container_name(self, id, name):
        """
        Actualiza el nombre de un contenedor.

        Args:
            id (str): ID del contenedor.
            name (str): Nuevo nombre del contenedor.
        """
        container = self.get_container_by_id(id)
        if not container:
            print("No encontrado")
            return

        container.name = name 
        return


    def update_container_id(self, id, new_id):
        """
        Actualiza el ID de un contenedor.

        Args:
            id (str): ID actual del contenedor.
            new_id (str): Nuevo ID del contenedor.
        """
        container = self.get_container_by_id(id)
        if not container:
            print("No encontrado")
            return

        if self.get_container_by_id(new_id) is not None:
            print(f"Ya existe un contenedor con ID {new_id}.")
            return

        container.id = new_id 
        self.containers[new_id] = container
        
        self.containers[id] = None

        return


    def delete_container(self, id):
        """
        Elimina un contenedor del Vault.

        Args:
            id (str): ID del contenedor a eliminar.
        """
        if id in self.containers:
            self.containers[id] = None


    def list_containers(self):
        """
        Muestra la lista de contenedores almacenados.
        """
        if not self.containers:
            print("No hay contenedores almacenados.")
            return

        print("\nContenedores en el Vault:")

        for container in self.containers.values():
            if container is not None:
                print(container)


    def _edit_text(self, initial_text: str) -> str:
        """
        Abre un editor de texto que usa Curses en la terminal para modificar un secreto.

        Args:
            initial_text (str): Texto inicial del secreto.

        Returns:
            str: Texto editado por el usuario.
        """
        def _curses_main(stdscr):
            curses.cbreak()
            curses.noecho()
            stdscr.clear()

            max_y, max_x = stdscr.getmaxyx()

            edit_height = max_y - 2
            edit_width = max_x - 2
            edit_win = curses.newwin(edit_height, edit_width, 2, 2)

            edit_win.border()

            instruction = "Edita los secretos. Pulsa Ctrl-G para guardar y salir."
            stdscr.addstr(0, max(0, (max_x - len(instruction)) // 2), instruction, curses.A_BOLD)
            stdscr.refresh()

            textbox_win = edit_win.derwin(edit_height - 2, edit_width - 2, 1, 1)

            text_box = curses.textpad.Textbox(textbox_win)

            lines = initial_text.split('\n')
            for idx, line in enumerate(lines[:edit_height - 3]):
                textbox_win.addstr(idx, 0, line[:edit_width - 3])

            textbox_win.move(0, 0)

            edited_text = text_box.edit().strip()

            return edited_text
        try:
            return curses.wrapper(_curses_main)
        except Exception as e:
            return f"Error al editar: {str(e)}"
