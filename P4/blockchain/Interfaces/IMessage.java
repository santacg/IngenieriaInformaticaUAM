package blockchain.Interfaces;

import blockchain.NetworkElement.Node;

/**
 * Interfaz para los mensajes que se pueden enviar entre nodos en una red de
 * blockchain.
 * Define cómo se obtiene el mensaje y cómo se procesa en el contexto de un
 * nodo.
 * 
 */
public interface IMessage {
    /**
     * Obtiene el contenido del mensaje.
     * 
     * @return El contenido del mensaje como cadena de texto.
     */
    String getMessage();

    /**
     * Procesa el mensaje en el contexto de un nodo específico, imprimiendo una
     * notificación.
     * Este método predeterminado muestra cómo se maneja el mensaje en el nodo.
     * 
     * @param n El nodo en el que se procesa el mensaje.
     */
    default void process(Node n) {
        System.out.println("[" + n.fullName() + "]" +
                " - Received notification - Nex Tx: " +
                this.getMessage());
    }
}
