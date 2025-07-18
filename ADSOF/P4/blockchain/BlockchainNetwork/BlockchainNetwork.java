package blockchain.BlockchainNetwork;

import java.util.ArrayList;

import blockchain.ConnectionException.ConnectionException;
import blockchain.ConnectionException.DuplicateConnectionException;
import blockchain.Interfaces.IConnectable;
import blockchain.Interfaces.IMessage;
import blockchain.NetworkElement.*;

/**
 * Implementa una red de blockchain con capacidad para añadir, remover y
 * conectar elementos.
 * Permite la gestión de nodos y subredes dentro de la red, facilitando su
 * expansión y mantenimiento.
 * 
 * @author Carlos García Santa y Joaquín Abad Díaz
 */
public class BlockchainNetwork implements IConnectable {
    private String name;
    private ArrayList<NetworkElement> elements;
    private IConnectable parent = null;

    /**
     * Constructor para crear una nueva red de blockchain con un nombre específico.
     * 
     * @param name El nombre de la red de blockchain.
     */
    public BlockchainNetwork(String name) {
        this.name = name;
        this.elements = new ArrayList<>();
    }

    /**
     * Añade un elemento a la red de blockchain.
     * 
     * @param element El elemento a añadir.
     */
    public void addElement(NetworkElement element) {
        this.elements.add(element);
    }

    /**
     * Elimina un elemento de la red de blockchain.
     * 
     * @param element El elemento a eliminar.
     */
    public void removeElement(NetworkElement element) {
        this.elements.remove(element);
    }

    /**
     * Conecta un elemento a la red de blockchain e imprime un mensaje.
     * 
     * @param element El elemento a conectar.
     * @return La instancia actual de BlockchainNetwork para permitir
     *         encadenamiento.
     * @throws ConnectionException          Si el elemento ya está conectado a la
     *                                      red.
     * @throws DuplicateConnectionException Si el elemento pertenece a otra red.
     */
    public BlockchainNetwork connect(NetworkElement element) throws ConnectionException {
        if (element.isNode()) {

            if (elements.contains(element)) {
                throw new ConnectionException(element);
            }
        }
        if (element.getParent() != this && element.getParent() != null) {
            throw new DuplicateConnectionException(element);
        }

        addElement(element);
        element.setParent(this);
        System.out.println(this.name + " - new peer connected: " + element.toString());
        return this;
    }

    /**
     * Obtiene el elemento padre de esta red, que es siempre null para la red principal.
     * 
     * @return null ya que es la red de nivel superior.
     */
    public IConnectable getParent() {
        return parent;
    }

    /**
     * Envía un mensaje a todos los elementos de la red, propagando el mensaje a través de la red.
     * 
     * @param msg El mensaje a transmitir a todos los elementos conectados.
     */
    public void broadcast(IMessage msg) {
        for (NetworkElement element : elements) {
            element.broadcast(msg);
        }
    }

    /**
     * Devuelve una representación en cadena de la red de blockchain, mostrando
     * todos los elementos conectados.
     * 
     * @return Una cadena que representa la red de blockchain y sus elementos.
     */
    @Override
    public String toString() {
        String elementsString = "";
        for (NetworkElement networkElement : elements) {
            elementsString += "* " + networkElement.toString() + "\n";
        }
        return name + " consists of " + elements.size() + " elements:\n" + elementsString;
    }
}
