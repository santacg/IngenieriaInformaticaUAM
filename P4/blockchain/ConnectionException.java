package blockchain;

import blockchain.NetworkElement.Node;

public class ConnectionException extends Exception {
    private Node node;
    public ConnectionException(Node node){
        this.node = node;
    }

    public String toString(){
        return "Connection exception: Node " + String.format("%03d", node.getId()) + "is already connected to the network\n";
    }
}
