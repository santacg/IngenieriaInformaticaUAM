

class Container:
    """
    Esta clase representa un Container.

    Atributos:
    """

    def __init__(self, id: str, name: str, secrets: str):
        """
        Inicializa una objeto Container

        Args:
            id (int): El identificador del contenedor
            name (str): El nombre del contenedor
        """
        self.id = id
        self.name = name
        self.secrets = secrets


    def __str__(self):
        out = ""
        out += f"ID:        {self.id}\n"
        out += f"Name:      {self.name}\n"

        if self.secrets != "":
            out += f"Secrets:       {self.secrets}\n"

        return out

