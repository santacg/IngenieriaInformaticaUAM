import os
import sys
import getpass
from userauth import UserAuth
from vault import Vault
from cloud_manager import GoogleDriveManager


class SecureBoxCLI:
    def __init__(self):
        self.user_auth = UserAuth()

        if os.path.isfile(self.user_auth.STORAGE_FILE):
            self.user_auth.load_from_file()


    def run(self):
        print("--- SecureBox ---")
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
            print("\nYa existe una clave generada.")
        else:
            password = getpass.getpass("Introduce la contraseña: ")
            self.user_auth.generate_key(password)
            if self.user_auth.save_to_file():
                print("\nClave generada.")
            else:
                print("\nClave no generada.")


    def remove_key(self):
        if os.path.isfile(self.user_auth.STORAGE_FILE):
            while True:
                password = getpass.getpass("Introduce la contraseña: ")
                if self.user_auth.verify_key(password):
                    print("\nContraseña correcta. Clave eliminada.")
                    try:
                        os.remove(self.user_auth.STORAGE_FILE)
                    except Exception as e:
                        print(f"\nError al eliminar la clave: {e}")
                    break
                else:
                    print("\nContraseña incorrecta. Inténtalo de nuevo.")
        else:
            print("\nNo existe clave para eliminar.")

            
    def open_vault(self):
        if not os.path.isfile(self.user_auth.STORAGE_FILE):
            print("\nNo existe clave. Genera una clave para acceder al Vault.")
            return

        while True:
            password = getpass.getpass("Introduce la contraseña del Vault: ")
            if self.user_auth.verify_key(password):
                print("\nContraseña correcta. Acceso permitido.")
                break
            else:
                print("\nContraseña incorrecta. Inténtalo de nuevo.")

        self.vault_menu(password)


    def exit_cli(self):
        print("Saliendo... ¡Adiós!")
        sys.exit(0)


    def vault_menu(self, password):
        self.vault = Vault()

        if os.path.isfile(self.vault.STORAGE_FILE):
            print("\nVault detectado. Cargando desde archivo.")
            if self.vault.load_from_file(password):
                print("\nVault cargado correctamente")
            else:
                exit(1)

        else:
            print("\nVault no detectado. Creando Vault.")
            self.vault.generate_keys(password)
            self.vault.save_to_file()

        print("--- SecureBox ---")
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
                "7": self.cloud_menu,
                "8": self.exit_vault_menu

            }

            action = options.get(option)
            if action:
                try:
                    action()
                    if option == "8":
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
        print("7. Administrar Nube")
        print("8. Salir del Vault")


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
        if self.vault.save_to_file():
            print("Vault guardado exitosamente.")
        else:
            exit(1)


    def cloud_menu(self):
        """Menú de administración de la nube"""
        credentials_file = input("\nIntroduce el archivo de credenciales: ")
        storage_file = input("\nIntroduce el archivo donde se almacenará el vault subido o descargado de la nube: ")
        self.cloud_manager = GoogleDriveManager(credentials_file=credentials_file, storage_file=storage_file)

        print("--- SecureBox ---")
        while True:
            self.show_cloud_menu()
            option = input("Elige una opción: ").strip()
            options = {
                "1": self.upload_vault_to_cloud,
                "2": self.download_vault_from_cloud,
                "3": self.change_credentials_file,
                "4": self.change_storage_file,
                "5": self.exit_cloud_menu
            }

            action = options.get(option)
            if action:
                try:
                    action()
                    if option == "5":
                        break
                except Exception as e:
                    print(f"Error: {e}")
            else:
                print("Opción no válida. Inténtalo de nuevo.")


    def show_cloud_menu(self):
        print("\n--- Administración de la Nube ---")
        print("1. Subir Vault a la Nube")
        print("2. Descargar Vault desde la Nube")
        print("3. Cambiar archivo de credenciales")
        print("4. Cambiar archivo de almacenamiento")
        print("5. Volver al menú del Vault")


    def upload_vault_to_cloud(self):
        """Sube el vault cifrado a Google Drive"""
        if not self.vault:
            print("No hay Vault abierto.")
            return

        if self.vault.save_to_file():
            if self.cloud_manager.upload_vault():
                print("\nVault subido a la nube.")
        else:
            print("\nError al guardar el Vault localmente.")


    def download_vault_from_cloud(self):
        """Descarga y carga el vault desde Google Drive"""
        if self.cloud_manager.download_vault():
            print("\nVault descargado.")


    def change_credentials_file(self):
        credentials_file = input("\nIntroduce el nuevo archivo de credenciales: ")
        self.cloud_manager.credentials_file = credentials_file


    def change_storage_file(self):
        storage_file = input("\nIntroduce el nuevo archivo de almacenamiento: ")
        self.cloud_manager.storage_file = storage_file


    def exit_cloud_menu(self):
        """Vuelve al menú del vault"""
        print("\nVolviendo al menú del Vault...")

if __name__ == '__main__':
    cli = SecureBoxCLI()
    try:
        cli.run()
    except KeyboardInterrupt:
        print("\nInterrupción detectada. Saliendo...")
        sys.exit(0)

