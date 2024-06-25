import uuid

class Pedido:
    def __init__(self, cliente_id, productos_ids):
        self.pedido_id = str(uuid.uuid4())
        self.cliente_id = cliente_id
        self.productos_ids = productos_ids
        self.status = 'Pendiente'

    def actualizar_estado(self, new_status):
        transiciones_permitidas = {
            'Pendiente': ['En almacen'],
            'En almacen': ['Listo'],
            'Listo': ['En reparto'],
            'En reparto': ['Entregado']
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
        if self.status in ['Pendiente', 'En almacen', 'Listo']:
            self.actualizar_estado('Cancelled')
            print(f"Pedido {self.pedido_id} cancelado.")
            return True
        else:
            print(
                f"No se puede cancelar el pedido {self.pedido_id} en estado {self.status}.")
            return False
