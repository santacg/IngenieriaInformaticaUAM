from container import Container
import json
import os
import curses
import curses.textpad


class Vault:
    """
    Esta clase representa un Vault.

    Atributos:
        containers (dict): Lista de objetos tipo Container
    """

    def __init__(self, storage_file: str):
        """
        Inicializa una objeto Vault

        Args:
        """
        self.storage_file = storage_file
        self.containers = {}


    def load_from_file(self) -> None:
        """
        Carga los contenedores desde un archivo JSON si existe.
        """
        if os.path.isfile(self.storage_file):
            try:
                with open(self.storage_file, "r", encoding="utf-8") as f:
                    data = json.load(f)

                self.containers = {}

                for id, info in data.items():
                    self.containers[id] = Container(
                        id=id,
                        name=info["name"],
                        secrets=info["secrets"]
                    )

            except (json.JSONDecodeError, KeyError):
                print("Error")


    def save_to_file(self) -> None:
        """
        Guarda los contenedores en un archivo JSON.
        """
        data_to_save = {}
        for id, container in self.containers.items():
            data_to_save[id] = {
                "name": container.name,
                "secrets": container.secrets
            }

        encrypted_data = 


    def get_container_by_id(self, id: str):
        """
        Retorna un contenedor segun el id.
        """
        return self.containers.get(id)


    def get_container_by_name(self, name: str):
        """
        Retorna un contenedor segun el nombre
        """
        for container in self.containers.values():
            if container.name == name:
                return container

        return None

    def create_container(self, id: str, name: str) -> bool:
        """
        Añade un objeto Container a la lista de Containers del Vault

        Args:
            id (int): El id del Container a añadir.
            name (str): El nombre del Container a añadir.
        """

        if self.get_container_by_id(id) is not None or self.get_container_by_name(name) is not None:
            return False

        container = Container(id, name, "")
        self.containers[id] = container

        return True 


    def update_container_secrets(self, id) -> bool:
        container = self.get_container_by_id(id)
        if not container:
            return False

        initial_secrets = str(container.secrets) if container.secrets else ""

        updated_secrets = self._edit_text(initial_secrets)

        container.secrets = updated_secrets

        return True


    def update_container_name(self, id, name):
        container = self.get_container_by_id(id)
        if not container:
            print("No encontrado")
            return

        container.name = name 
        return


    def update_container_id(self, id, new_id):
        container = self.get_container_by_id(id)
        if not container:
            print("No encontrado")
            return

        container.id = new_id 
        return


    def delete_container(self, id):
        self.containers[id] = None


    def list_containers(self):
        """
        Lista todos los contenedores (por ID y nombre).
        """
        if not self.containers:
            print("No hay contenedores almacenados.")
            return
        print("\nContenedores en el Vault:")
        for container in self.containers.values():
            print(container)


    def _edit_text(self, initial_text) -> str:
        def _curses_main(stdscr):
            curses.cbreak()
            curses.noecho()
            stdscr.keypad(True)

            # Obtenemos el tamaño de la pantalla
            max_y, max_x = stdscr.getmaxyx()

            # Creamos una ventana "edit_win" un poco más pequeña que la pantalla
            edit_height = max_y - 4
            edit_width = max_x - 4
            edit_win = curses.newwin(edit_height, edit_width, 2, 2)

            # Creamos el Textbox a partir de la ventana
            text_box = curses.textpad.Textbox(edit_win)

            # Mostramos un mensaje de instrucciones en la parte superior
            stdscr.addstr(0, 0, "Edita los secretos. Pulsa Ctrl-G para guardar y salir.")
            stdscr.refresh()

            # Prellenamos la ventana con el texto inicial
            lines = initial_text.split('\n')
            for idx, line in enumerate(lines):
                edit_win.addstr(idx, 0, line)

            # Activamos el modo edición: el usuario escribe hasta pulsar Ctrl-G
            edited_text = text_box.edit()
            return edited_text

        # curses.wrapper se encarga de iniciar y finalizar adecuadamente curses
        result = curses.wrapper(_curses_main)
        return result
