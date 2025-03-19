from googleapiclient.discovery import build
from googleapiclient.http import MediaFileUpload
from google_auth_oauthlib.flow import InstalledAppFlow
from google.auth.transport.requests import Request
from google.oauth2.credentials import Credentials
import os


class GoogleDriveManager:
    """
    Clase para gestionar la carga y descarga segura de archivos en Google Drive.

    Atributos:
        credentials_file (str): Nombre del archivo de credenciales de Google.
        storage_file (str): Nombre del archivo que se subira o descargará.
        scopes (list): Lista de permisos requeridos para la API de Google Drive.
        service: Cliente autenticado para interactuar con Google Drive.
    """

    def __init__(self, credentials_file="client_secret.json"):
        self.credentials_file = credentials_file
        self.scopes = ['https://www.googleapis.com/auth/drive']
        self.service = self.authenticate()


    def authenticate(self):
        """
        Realiza la autenticación con Google Drive y maneja los tokens de acceso.
        Si el token de acceso no es válido o ha expirado, lo renueva o solicita nuevas credenciales.

        Returns:
            Cliente autenticado de Google Drive.
        """
        creds = None

        if os.path.exists('token.json'):
            creds = Credentials.from_authorized_user_file('token.json', self.scopes)

        if not creds or not creds.valid:
            if creds and creds.expired and creds.refresh_token:
                creds.refresh(Request())
            else:
                flow = InstalledAppFlow.from_client_secrets_file(self.credentials_file, self.scopes)
                creds = flow.run_local_server(port=0)

            with open('token.json', 'w') as token:
                token.write(creds.to_json())

        return build("drive", "v3", credentials=creds)


    def upload_vault(self, folder_path: str, file_path: str) -> bool:
        """
        Sube el archivo del vault a Google Drive dentro de la carpeta SecureBox_Backup.
        Si la carpeta no exist se crea automáticamente.

        Args:
            folder_path (str): Path donde se creará o usara un fichero.
            file_path (str): Path donde se almacenará el archivo del Vault.

        Returns:
            True si la carga fue exitosa, False si el archivo ya existe.
        """

        folder_id = self.get_folder(folder_path)

        query = f"name='{file_path}' and parents in '{folder_id}' and trashed=false"
        results = self.service.files().list(q=query, fields="files(id)").execute()
        files = results.get("files", [])

        if files:
            print(f"\nEl archivo '{file_path}' ya existe en Google Drive.")
            return False

        file_metadata = {
            "name": file_path,
            "parents": [folder_id]
        }

        media = MediaFileUpload(file_path, mimetype="application/json")
        self.service.files().create(body=file_metadata, media_body=media, fields="id").execute()
        return True


    def download_vault(self, folder_path: str, file_path: str) -> bool:
        """
        Descarga el archivo del vault desde Google Drive.
        Busca el archivo en la carpeta SecureBox_Backup y lo guarda localmente.

        Args:
            folder_path (str): Path donde buscará el arhcivo. 
            file_path (str): Path donde se almacenará el archivo del Vault


        Returns:
            True si la descarga fue exitosa, False si el archivo no se encontro.
        """
        folder_id = self.get_folder(folder_path)

        query = f"name='{file_path}' and parents in '{folder_id}' and trashed=false"
        results = self.service.files().list(q=query, fields="files(id)").execute()
        files = results.get("files", [])

        if not files:
            print("\nNo se encontró el vault en Google Drive.")
            return False

        file_id = files[0]["id"]
        request = self.service.files().get_media(fileId=file_id)

        with open(file_path, "wb") as f:
            f.write(request.execute())

        return True


    def get_folder(self, folder_name: str) -> str:
        """
        Busca una carpeta en Google Drive por su nombre. Si no existe, la crea.

        Args:
            folder_name (str): Nombre de la carpeta a buscar o crear.

        Returns:
            str: ID de la carpeta en Google Drive.
        """
        query = f"name='{folder_name}' and mimeType='application/vnd.google-apps.folder' and trashed=false"
        results = self.service.files().list(q=query, fields="files(id, name)").execute()
        folders = results.get("files", [])

        if folders:
            return folders[0]["id"]

        metadata = {
            "name": folder_name,
            "mimeType": "application/vnd.google-apps.folder"
        }
        folder = self.service.files().create(body=metadata, fields="id").execute()
        print(f"Carpeta '{folder_name}' creada en Google Drive.")
        return folder["id"]
