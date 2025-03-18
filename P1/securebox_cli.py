import os
import sys
import getpass
from userauth import UserAuth
from vault import Vault


class SecureBoxCLI:
    def __init__(self):
        self.user_auth = UserAuth()
        self.vault = None

    def run(self):
        while True:
            self.show_main_menu()
            option = input("Elige una opción: ").strip()
            options = {
                "1": self.generate_key,
                "2": self.remove_key,
                "3": self.open_vault,
                "4": self.exit_cli
            }
            action = options.get(option)
            if action:
                try:
                    action()
                except Exception as e:
                    print(f"Error: {e}")
            else:
                print("Opción no válida. Inténtalo de nuevo.")

    def show_main_menu(self):
        print("\n--- Menú Principal ---")
        print("1. Generar clave")
        print("2. Eliminar clave")
        print("3. Abrir Vault")
        print("4. Salir")

    def generate_key(self):
        if os.path.isfile(self.user_auth.STORAGE_FILE):
            print("Ya existe una clave generada.")
        else:
            password = getpass.getpass("Introduce la contraseña: ")
            salt, key_hash = self.user_auth.generate_key(password)
            self.user_auth.save_to_file(salt, key_hash)
            print("Clave generada exitosamente.")

    def remove_key(self):
        if os.path.isfile(self.user_auth.STORAGE_FILE):
            while True:
                password = getpass.getpass("Introduce la contraseña: ")
                if self.user_auth.verify_key(password):
                    print("Contraseña correcta. Clave eliminada.")
                    try:
                        os.remove(self.user_auth.STORAGE_FILE)
                    except Exception as e:
                        print(f"Error al eliminar la clave: {e}")
                    break
                else:
                    print("Contraseña incorrecta. Inténtalo de nuevo.")
        else:
            print("No existe clave para eliminar.")

    def open_vault(self):
        if not os.path.isfile(self.user_auth.STORAGE_FILE):
            print("No existe la clave del usuario. Primero crea una clave con la opción 1.")
            return

        while not self.user_auth.authenticated:
            password = getpass.getpass("Introduce la contraseña del Vault: ")
            if self.user_auth.verify_key(password):
                print("Clave correcta, acceso permitido.")
                self.user_auth.authenticated = True
                print("He salido")
            else:
                print("Clave incorrecta. Inténtalo de nuevo.")

        self.vault_menu(password)

    def exit_cli(self):
        print("Saliendo... ¡Adiós!")
        sys.exit(0)

    def vault_menu(self, password):
        self.vault = Vault()

        if os.path.isfile(self.vault.STORAGE_FILE):
            print("Vault detectado. Cargando desde archivo.")
            try:
                self.vault.load_from_file(password)
            except Exception as e:
                print(f"Error al cargar Vault: {e}")
                return
        else:
            print("Creando Vault.")
            self.vault.generate_dek(password)

        while True:
            self.show_vault_menu()
            option = input("Elige una opción: ").strip()
            options = {
                "1": self.vault.list_containers,
                "2": self.create_container,
                "3": self.update_container_secrets,
                "4": self.update_container_name,
                "5": self.update_container_id,
                "6": self.delete_container,
                "7": self.exit_vault_menu
            }
            action = options.get(option)
            if action:
                try:
                    action()
                    if option == "7":
                        break
                except Exception as e:
                    print(f"Error: {e}")
            else:
                print("Opción no válida. Inténtalo de nuevo.")

    def show_vault_menu(self):
        print("\n--- Menú del Vault ---")
        print("1. Listar contenedores")
        print("2. Crear contenedor")
        print("3. Actualizar secretos de un contenedor")
        print("4. Actualizar nombre de un contenedor")
        print("5. Actualizar ID de un contenedor")
        print("6. Eliminar contenedor")
        print("7. Salir del Vault")

    def create_container(self):
        cont_id = input("Introduce el ID del nuevo contenedor: ").strip()
        name = input("Introduce el nombre del nuevo contenedor: ").strip()
        if self.vault.create_container(cont_id, name):
            print("Contenedor creado exitosamente.")
        else:
            print("Error al crear contenedor.")

    def update_container_secrets(self):
        cont_id = input("Introduce el ID del contenedor a actualizar secretos: ").strip()
        if self.vault.update_container_secrets(cont_id):
            print("Secretos actualizados.")
        else:
            print("Error al actualizar secretos.")

    def update_container_name(self):
        cont_id = input("Introduce el ID del contenedor a actualizar nombre: ").strip()
        new_name = input("Introduce el nuevo nombre: ").strip()
        if self.vault.update_container_name(cont_id, new_name):
            print("Nombre actualizado.")
        else:
            print("Error al actualizar el nombre.")

    def update_container_id(self):
        cont_id = input("Introduce el ID del contenedor a actualizar: ").strip()
        new_id = input("Introduce el nuevo ID: ").strip()
        if self.vault.update_container_id(cont_id, new_id):
            print("ID actualizado.")
        else:
            print("Error al actualizar el ID.")

    def delete_container(self):
        cont_id = input("Introduce el ID del contenedor a eliminar: ").strip()
        if self.vault.delete_container(cont_id):
            print("Contenedor eliminado.")
        else:
            print("Error al eliminar el contenedor.")

    def exit_vault_menu(self):
        print("Saliendo del Vault...")
        try:
            self.vault.save_to_file()
            print("Vault guardado exitosamente.")
        except Exception as e:
            print(f"Error al guardar Vault: {e}")


if __name__ == '__main__':
    cli = SecureBoxCLI()
    try:
        cli.run()
    except KeyboardInterrupt:
        print("\nInterrupción detectada. Saliendo...")
        sys.exit(0)

