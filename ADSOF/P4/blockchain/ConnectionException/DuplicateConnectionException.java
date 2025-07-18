package blockchain.ConnectionException;

import blockchain.NetworkElement.NetworkElement;

/**
 * Excepción personalizada que se lanza cuando se intenta conectar un elemento
 * de red
 * que ya pertenece a otra red diferente.
 * Extiende {@link ConnectionException} para proporcionar información específica
 * sobre intentos de conexión inválidos
 * donde el elemento ya está asociado a otra red.
 * 
 * @author Carlos García Santa y Joaquín Abad Díaz
 */
public class DuplicateConnectionException extends ConnectionException {

    /**
     * Constructor que crea una nueva excepción con el elemento de red específico
     * que causó el error.
     * Este constructor inicializa la excepción con el elemento que está
     * incorrectamente vinculado a otra red.
     * 
     * @param element El elemento de red que está conectado a otra red.
     */
    public DuplicateConnectionException(NetworkElement element) {
        super(element);
    }

    /**
     * Devuelve un mensaje de error que incluye el identificador y el tipo del
     * elemento de red que causó la excepción.
     * 
     * @return Una cadena que describe el error de conexión, indicando que el
     *         elemento está conectado a una red diferente.
     */
    @Override
    public String toString() {
        String name = this.getElement().isNode() ? "Node" : "Subnet";
        return "Connection exception: " + name + " " + String.format("%03d", this.getElement().getId())
                + " is connected to a different network";
    }
}
