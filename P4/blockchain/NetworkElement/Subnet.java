package blockchain.NetworkElement;

import java.util.HashSet;

import blockchain.IMessage;
import blockchain.BlockchainNetwork.BlockchainNetwork;

/**
 * La clase Subnet representa una subred dentro de la red de blockchain, la cual puede contener múltiples nodos.
 * Esta estructura permite agrupar nodos para organizar mejor la red y potencialmente optimizar su funcionamiento.
 * 
 * @author Carlos García Santa y Joaquín Abad Díaz
 */
public class Subnet extends NetworkElement {
    private HashSet<Node> nodes; // Conjunto de nodos que pertenecen a esta subred.

    /**
     * Construye una nueva subred y opcionalmente inicializa con un conjunto de nodos.
     * 
     * @param nodes Nodos iniciales para incluir en la subred.
     */
    public Subnet(Node... nodes) {
        setId(getIdCounter());
        increaseIdCounter();
        this.nodes = new HashSet<>();
        for (Node node : nodes) {
            this.nodes.add(node);
            node.setParent(this);
        }
    }

    

    /**
     * Añade un nodo a la subred.
     * 
     * @param node El nodo a añadir.
     */
    public void addNode(Node node) {
        this.nodes.add(node);
    }
    
    /**
     * Elimina un nodo de la subred.
     * 
     * @param node El nodo a eliminar.
     */
    public void removeNode(Node node) {
        this.nodes.remove(node);
    }

    public boolean isNode(){
        return false;
    }
    
    public void broadcast(IMessage msg){
        String mensaje = "[Subnet#"  + String.format("%03d", getId()) + "] " + msg.getMessage() + "\nBroadcasting to " + nodes.size() + " nodes:";
        System.out.println(mensaje);
        for (Node node : nodes) {
            msg.process(node);
        }
    }

    /**
     * Proporciona una representación en cadena de la subred, incluyendo la cantidad de nodos
     * que contiene y una lista de estos nodos.
     * 
     * @return Una representación en cadena de la subred y sus nodos.
     */
    @Override
    public String toString() {
        String nodeList = "";
        for (Node node : nodes) {
            nodeList += "[" + node.toString() + "]";
        }
        return "Node network of " + nodes.size() + " nodes: " + nodeList;
    }
}
