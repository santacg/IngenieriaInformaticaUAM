from vault import Vault
import os
import getpass

vault_file = "vault.sec"  # El archivo local donde guardas el vault
salt_file = vault_file + ".salt"  # Archivo local para la salt

if not os.path.isfile(vault_file):
    # El vault aún no existe: lo creamos
    print("No se ha encontrado un vault local. Crearemos uno nuevo.")
    
    # 1. Pedir contraseña y confirmarla
    password = getpass.getpass("Introduce una nueva contraseña maestra: ")
    confirm = getpass.getpass("Confirma la contraseña: ")
    if password != confirm:
        print("Las contraseñas no coinciden. Abortando.")
    
    # 2. Crear e inicializar el Vault
    vault = Vault(storage_file=vault_file)

else:
    # Si el vault existe, lo abrimos
    print("Vault local encontrado.")
    
    password = getpass.getpass("Introduce la contraseña maestra: ")
    
    vault = Vault(storage_file=vault_file)
    
    print("Vault cargado correctamente.")

# A partir de aquí, tienes acceso a 'vault' para listar contenedores, editar, etc.
# Ejemplo:
# vault.list_all_containers()  # si tu clase lo define
# ... y al terminar, puedes guardar cambios con vault.save()

