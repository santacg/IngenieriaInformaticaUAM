class Secret:
    """
    Esta clase representa un Container.

    Atributos:
    """

    def __init__(self, id: str, content: str):
        """
        Inicializa una objeto Container

        Args:
            id (int): El identificador del contenedor
            name (str): El nombre del contenedor
        """
        self.id = id
        self.content = content 


    def get_content(self):
        return self.content


    def set_content(self, content: str):
        self.content = content


    def __str__(self):
        out = ""
        out += f"ID:        {self.id}\n"
        out += f"Content:   {self.content}\n"

        return out

