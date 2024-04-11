package blockchain;

import blockchain.NetworkElement.NetworkElement;
import blockchain.NetworkElement.Node;

public class DuplicateConnectionException extends Exception {
    private NetworkElement element;

    public DuplicateConnectionException(NetworkElement element) {
        this.element = element;
    }

    public String toString() {
        String name = "";

        if (element instanceof Node) {
            name = "Node";
        } else {
            name = "Subnet";
        }
        return "Connection exception: " + name + " " + String.format("%03d", element.getId())
                + " is already connected to the network\n";
    }
}
