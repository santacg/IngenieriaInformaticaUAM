package blockchain.NetworkElement;

import blockchain.IConnectable;
import blockchain.BlockchainNetwork.BlockchainNetwork;

/**
 * @author Carlos García Santa y Joaquín Abad Díaz
 */
public class NetworkElement implements IConnectable {
    private int id;
    private IConnectable parent = null;

    public IConnectable getParent(){
        return parent;
    }

    public void setParent(IConnectable parent){
        this.parent = parent;
    }

    /**
     * Obtiene el identificador del elemento.
     * 
     * @return El identificador del elemento.
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
