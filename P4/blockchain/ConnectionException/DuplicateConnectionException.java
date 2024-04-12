package blockchain.ConnectionException;

import blockchain.NetworkElement.NetworkElement;

public class DuplicateConnectionException extends ConnectionException {

    public DuplicateConnectionException(NetworkElement element) {
        super(element);
    }

    @Override
    public String toString() {
        String name = "";
        NetworkElement element = this.getElement();

        if (element.isNode()) {
            name = "Node";
        } else {
            name = "Subnet";
        }
        return "Connection exception: " + name + " " + String.format("%03d", element.getId())
                + " is connected to a different network";
    }
}
