import uuid


class Pedido:
    def __init__(self, cliente_id, productos_ids):
        """
        Crea un pedido con los productos especificados
        """

        self.pedido_id = str(uuid.uuid4())
        self.cliente_id = cliente_id
        self.productos_ids = productos_ids
        self.status = 'En almacen'

    def actualizar_estado(self, new_status):
        """
        Actualiza el estado del pedido
        """

        transiciones_permitidas = {
            'En almacen': ['En cinta', 'Cancelado'],
            'En cinta': ['En entrega', 'Cancelado'],
            'En entrega': ['Entregado', 'En cinta'],
        }
        if self.status in transiciones_permitidas and new_status in transiciones_permitidas[self.status]:
            self.status = new_status
            print(
                f"Estado del pedido {self.pedido_id} actualizado a {self.status}")
            return True
        else:
            print(
                f"Transición de estado no permitida de {self.status} a {new_status}.")
            return False

    def cancelar(self):
        """
        Cancela el pedido
        """

        if self.status in ['En almacen', 'En cinta']:
            self.actualizar_estado('Cancelado')
            print(f"Pedido {self.pedido_id} cancelado.")
            return True
        else:
            print(
                f"No se puede cancelar el pedido {self.pedido_id} en estado {self.status}.")
            return False

    def __str__(self):
        return f"Pedido ID: {self.pedido_id}, Cliente ID: {self.cliente_id}, Productos: {self.productos_ids}, Estado: {self.status}"
