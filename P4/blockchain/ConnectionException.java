package blockchain;

import blockchain.NetworkElement.NetworkElement;

public class ConnectionException extends RuntimeException {
    private NetworkElement element;
    public ConnectionException(NetworkElement element){
        this.element = element;
    }

    public NetworkElement getElement(){
        return element;
    }

    @Override
    public String toString(){
        return "Connection exception: Node " + String.format("%03d", element.getId()) + " is already connected to the network";
    }
}
