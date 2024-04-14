package blockchain.ConnectionException;

import blockchain.NetworkElement.NetworkElement;

/**
 * Excepción personalizada que se lanza cuando se intenta conectar un elemento
 * que ya está conectado a la red de blockchain.
 * Esta clase proporciona detalles específicos sobre el elemento que causó la
 * excepción.
 * 
 * @author Carlos García Santa y Joaquín Abad Díaz
 */
public class ConnectionException extends RuntimeException {
    private NetworkElement element; // Elemento de red que intentó conectarse duplicadamente.

    /**
     * Constructor que crea una nueva excepción con el elemento de red específico
     * que causó el error.
     * 
     * @param element El elemento de red que ya está conectado y que intentó
     *                conectarse nuevamente.
     */
    public ConnectionException(NetworkElement element) {
        this.element = element;
    }

    /**
     * Obtiene el elemento de red que causó la excepción.
     * 
     * @return El elemento de red que ya estaba conectado y causó el lanzamiento de
     *         esta excepción.
     */
    public NetworkElement getElement() {
        return element;
    }

    /**
     * Devuelve un mensaje de error que incluye el identificador del elemento de red
     * que causó la excepción.
     * 
     * @return Una cadena que describe el error de conexión, indicando que el nodo
     *         ya estaba conectado.
     */
    @Override
    public String toString() {
        return "Connection exception: Node " + String.format("%03d", element.getId())
                + " is already connected to the network";
    }
}
