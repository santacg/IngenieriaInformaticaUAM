package blockchain;

import blockchain.NetworkElement.NetworkElement;

public class ConnectionException extends RuntimeException {
    private NetworkElement node;
    public ConnectionException(NetworkElement node){
        this.node = node;
    }

    @Override
    public String toString(){
        return "Connection exception: Node " + String.format("%03d", node.getId()) + "is already connected to the network\n";
    }
}
