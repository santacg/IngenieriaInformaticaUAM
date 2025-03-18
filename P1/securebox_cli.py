import os
from userauth import UserAuth
from vault import Vault


class SecureBoxCLI:
    def __init__(self):
        self.user_auth = UserAuth()
        self.vault = None


    def run(self):
        while True:
            self.show_main_menu()
            opcion = input("Elige una opción: ").strip()
            opciones = {
                "1": self.create_user,
                "2": self.authenticate_and_open_vault,
                "3": self.exit_cli
            }
            accion = opciones.get(opcion, None)
            if accion:
                accion()
            else:
                print("Opción no válida. Intenta de nuevo.")


    def show_main_menu(self):
        print("\n--- Menú Principal ---")
        print("1. Crear usuario (generar clave)")
        print("2. Autenticarse y abrir Vault")
        print("3. Salir")


    def create_user(self):
        if os.path.isfile(self.user_auth.STORAGE_FILE):
            print("Ya existen datos del usuario")
        else:
            password = input("Introduce la contraseña: ")
            salt, hash = self.user_auth.generate_key(password)
            self.user_auth.save_to_file(salt, hash)
            print("Usuario creado y clave generada.")


    def authenticate_and_open_vault(self):
        if not os.path.isfile(self.user_auth.STORAGE_FILE):
            print("No existen datos del usuario. Primero crea un usuario con la opción 1.")
            return

        while not self.user_auth.authenticated:
            password = input("Introduce la contraseña del Vault: ")
            salt, hash = self.user_auth.load_from_file()
            if self.user_auth.verify_key(salt, hash, password):
                print("Clave correcta, acceso permitido.")
            else:
                print("Clave incorrecta, intenta de nuevo.")

        self.vault_menu()


    # def change_password(self):
    #     old_password = input("Introduce tu contraseña actual: ")
    #     new_password = input("Introduce la nueva contraseña: ")
    #     if self.user_auth.change_key(old_password, new_password):
    #         print("Contraseña cambiada con éxito.")
    #     else:
    #         print("No se pudo cambiar la contraseña. Verifica que la contraseña actual sea correcta.")
    #
    def exit_cli(self):
        print("Saliendo... Adiós!")
        exit()

    def vault_menu(self):
        self.vault = Vault()

        # Cargar Vault si el archivo existe
        if os.path.isfile(self.vault.STORAGE_FILE):
            print("Vault detectado. Cargando desde archivo.")
            self.vault.load_from_file()
        else:
            print("Creando Vault.")
            self.vault.open_vault()

        while True:
            print("\n--- Menú del Vault ---")
            print("1. Listar contenedores")
            print("2. Crear contenedor")
            print("3. Actualizar secretos de un contenedor")
            print("4. Actualizar nombre de un contenedor")
            print("5. Actualizar ID de un contenedor")
            print("6. Eliminar contenedor")
            print("7. Guardar Vault")
            print("8. Cargar Vault")
            print("9. Salir del Vault")
            opcion = input("Elige una opción: ").strip()

            opciones = {
                "1": self.vault.list_containers,
                "2": self.create_container,
                "3": self.update_container_secrets,
                "4": self.update_container_name,
                "5": self.update_container_id,
                "6": self.delete_container,
                "7": self.save_vault,
                "8": self.load_vault,
                "9": self.exit_vault_menu
            }

            accion = opciones.get(opcion, None)
            if accion:
                accion()
                if opcion == "9":
                    break
            else:
                print("Opción no válida. Intenta de nuevo.")

    # Métodos auxiliares para operaciones en el Vault
    def create_container(self):
        cont_id = input("Introduce el ID del nuevo contenedor: ")
        name = input("Introduce el nombre del nuevo contenedor: ")
        if self.vault.create_container(cont_id, name):
            print("Contenedor creado exitosamente.")

    def update_container_secrets(self):
        cont_id = input("Introduce el ID del contenedor a actualizar secretos: ")
        if self.vault.update_container_secrets(cont_id):
            print("Secretos actualizados.")

    def update_container_name(self):
        cont_id = input("Introduce el ID del contenedor a actualizar nombre: ")
        new_name = input("Introduce el nuevo nombre: ")
        self.vault.update_container_name(cont_id, new_name)
        print("Nombre actualizado.")

    def update_container_id(self):
        cont_id = input("Introduce el ID del contenedor a actualizar: ")
        new_id = input("Introduce el nuevo ID: ")
        self.vault.update_container_id(cont_id, new_id)
        print("ID actualizado.")

    def delete_container(self):
        cont_id = input("Introduce el ID del contenedor a eliminar: ")
        self.vault.delete_container(cont_id)
        print("Contenedor eliminado.")

    def save_vault(self):
        self.vault.save_to_file()
        print("Vault guardado exitosamente.")

    def load_vault(self):
        self.vault.load_from_file()
        print("Vault cargado exitosamente.")

    def exit_vault_menu(self):
        print("Saliendo del Vault...")
        self.vault.save_to_file()

if __name__ == '__main__':
    cli = SecureBoxCLI()
    cli.run()

