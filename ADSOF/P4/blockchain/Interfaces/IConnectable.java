package blockchain.Interfaces;

/**
 * Interfaz para elementos conectables dentro de una red de blockchain.
 * Define métodos para la difusión de mensajes y la gestión de la jerarquía de
 * la red.
 * Los elementos que pueden conectarse entre sí dentro de la red implementan
 * esta interfaz.
 * Esto permite la comunicación y el conocimiento estructural en un entorno de
 * red.
 * 
 */
public interface IConnectable {

    /**
     * Difunde un mensaje a todos los demás elementos conectables en la red.
     * 
     * @param msg El mensaje que se difundirá a través de la red.
     */
    void broadcast(IMessage msg);

    /**
     * Obtiene el elemento conectable padre en la jerarquía de la red.
     * 
     * @return El elemento IConnectable padre, o null si es el elemento más alto.
     */
    IConnectable getParent();

    /**
     * Obtiene el elemento conectable más alto en la jerarquía de la red, empezando
     * desde este elemento.
     * 
     * @return El elemento IConnectable más alto en la jerarquía, o null si no
     *         existe un padre.
     */
    default IConnectable getTopParent() {
        IConnectable parent = getParent();
        while (parent != null) {
            if (parent.getParent() == null)
                return parent;
            parent = parent.getParent();
        }
        return parent;
    }
}
