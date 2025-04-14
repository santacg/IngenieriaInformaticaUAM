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

        self.vault = Vault()

    def run(self):
        print("--- SecureBox ---")
        while True:
            self.show_main_menu()
            option = input("Elige una opción: ").strip()
            options = {
                "1": self.generate_key,
                "2": self.change_key,
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
        print("2. Cambiar clave")
        print("3. Abrir Vault")
        print("4. Salir")


    def generate_key(self):
        if os.path.isfile(self.user_auth.STORAGE_FILE):
            print("\nYa existe una clave generada.")
        else:
            while True:
                password = getpass.getpass("Introduce la contraseña (mínimo 8 caracteres): ")
                if len(password) >= 8:
                    break
                print("\nLa contraseña tiene menos de 8 caracteres.")

            self.user_auth.generate_key(password)
            if self.user_auth.save_to_file():
                print("\nClave generada.")
            else:
                print("\nClave no generada.")


    def change_key(self):
        if not os.path.isfile(self.user_auth.STORAGE_FILE):
            print("\nNo existe una clave generada.")
        else:
            while True:
                old_password = getpass.getpass("Introduce la contraseña anterior: ")
                if self.user_auth.verify_key(old_password):
                    print("\nContraseña correcta.")
                    break
                else:
                    print("\nContraseña incorrecta. Intentalo de nuevo.")

            while True:
                new_password = getpass.getpass("Introduce la nueva contraseña: ")
                if len(new_password) >= 8:
                    break
                print("\nLa contraseña tiene menos de 8 caracteres.")

            self.user_auth.generate_key(new_password)
            if self.user_auth.save_to_file():
                print("\nContraseña cambiada.")
            else:
                print("\nError generando nueva clave de autenticación.")
                return

            if os.path.isfile(self.vault.STORAGE_FILE):
                print("\nVault existente. Cambiando claves del Vault.")
                if self.vault.change_keys(old_password, new_password):
                    print("\nClaves del Vault cambiadas.")
                else:
                    print("\nError cambiando las claves del Vault.")
                    exit(1)



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
            password = getpass.getpass("Introduce la contraseña: ")
            if self.user_auth.verify_key(password):
                print("\nContraseña correcta. Acceso permitido.")
                break
            else:
                print("\nContraseña incorrecta. Inténtalo de nuevo.")

        self.vault_menu(password)


    def exit_cli(self):
        print("--- SecureBox ---")
        print("Saliendo...")
        sys.exit(0)


    def vault_menu(self, password):
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
                "2": self.show_container,
                "3": self.create_container,
                "4": self.update_container_secrets,
                "5": self.update_container_name,
                "6": self.update_container_id,
                "7": self.delete_container,
                "8": self.cloud_menu,
                "9": self.exit_vault_menu
            }

            action = options.get(option)
            if action:
                try:
                    action()
                    if option == "9":
                        break
                except Exception as e:
                    print(f"Error: {e}")
            else:
                print("Opción no válida. Inténtalo de nuevo.")


    def show_vault_menu(self):
        print("\n--- Menú del Vault ---")
        print("1. Listar contenedores")
        print("2. Mostrar contenedor")
        print("3. Crear contenedor")
        print("4. Actualizar secretos de un contenedor")
        print("5. Actualizar nombre de un contenedor")
        print("6. Actualizar ID de un contenedor")
        print("7. Eliminar contenedor")
        print("8. Administrar Nube")
        print("9. Salir del Vault")


    def create_container(self):
        id = input("Introduce el ID del nuevo contenedor: ").strip()
        name = input("Introduce el nombre del nuevo contenedor: ").strip()
        if self.vault.create_container(id, name):
            print("\nContenedor creado.")
        else:
            print("\nError al crear contenedor.")


    def update_container_secrets(self):
        id = input("Introduce el ID del contenedor a actualizar secretos: ").strip()
        if self.vault.update_container_secrets(id):
            print("\nSecretos actualizados.")
        else:
            print("\nError al actualizar secretos.")


    def update_container_name(self):
        id = input("Introduce el ID del contenedor a actualizar nombre: ").strip()
        new_name = input("Introduce el nuevo nombre: ").strip()
        if self.vault.update_container_name(id, new_name):
            print("\nNombre actualizado.")
        else:
            print("\nError al actualizar el nombre.")


    def update_container_id(self):
        id = input("Introduce el ID del contenedor a actualizar: ").strip()
        new_id = input("Introduce el nuevo ID: ").strip()
        if self.vault.update_container_id(id, new_id):
            print("\nID actualizado.")
        else:
            print("\nError al actualizar el ID.")


    def delete_container(self):
        id = input("Introduce el ID del contenedor a eliminar: ").strip()
        if self.vault.delete_container(id):
            print("\nContenedor eliminado.")
        else:
            print("\nError al eliminar el contenedor.")


    def show_container(self):
        id = input("Introduce el ID del contenedor: ").strip()
        if not self.vault.show_container(id):
            print("\nContenedor no encontrado.")


    def exit_vault_menu(self):
        print("\nSaliendo del Vault...")
        if self.vault.save_to_file():
            print("\nVault guardado.")
        else:
            exit(1)


    def cloud_menu(self):
        credentials_file = input("\nIntroduce el archivo de credenciales: ")
        self.cloud_manager = GoogleDriveManager(credentials_file=credentials_file)

        print("--- SecureBox ---")
        while True:
            self.show_cloud_menu()
            option = input("Elige una opción: ").strip()
            options = {
                "1": self.upload_vault_to_cloud,
                "2": self.download_vault_from_cloud,
                "3": self.change_credentials_file,
                "4": self.exit_cloud_menu
            }

            action = options.get(option)
            if action:
                try:
                    action()
                    if option == "4":
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
        print("4. Volver al menú del Vault")


    def upload_vault_to_cloud(self):
        if not self.vault:
            print("\nNo hay Vault abierto.")
            return

        if self.vault.save_to_file():
            folder_path = input("Introduce el Path de la carpeta donde se subirá: ")
            file_path = input("Introduce el nombre del archivo a subir: ")
            if self.cloud_manager.upload_vault(folder_path, file_path):
                print("\nVault subido a la nube.")
            else:
                print("\nError subiendo el Vault a la nube.")
        else:
            print("\nError al guardar el Vault localmente.")


    def download_vault_from_cloud(self):
        folder_path = input("Introduce el Path de la carpeta de donde se descargará: ")
        file_path = input("Introduce el nombre del archivo a descargar: ")
        if self.cloud_manager.download_vault(folder_path, file_path):
            print("\nVault descargado.")
        else:
            print("\nFallo descargando Vault.")


    def change_credentials_file(self):
        credentials_file = input("\nIntroduce el nuevo archivo de credenciales: ")
        self.cloud_manager.credentials_file = credentials_file


    def exit_cloud_menu(self):
        print("\nVolviendo al menú del Vault...")


if __name__ == '__main__':
    cli = SecureBoxCLI()
    try:
        cli.run()
    except KeyboardInterrupt:
        print("\nInterrupción detectada. Saliendo...")
        print("--- SecureBox ---")
        sys.exit(0)

