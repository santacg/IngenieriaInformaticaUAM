package blockchain.NetworkElement;

import blockchain.IConnectable;

/**
 * @author Carlos García Santa y Joaquín Abad Díaz
 */
public abstract class NetworkElement implements IConnectable {
    private static int idCounter = 0; // Contador estático para asignar un ID único a cada network element.
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

    public int getIdCounter() {
        return idCounter;
    }

    public void increaseIdCounter() {
        idCounter++;
    }

    public void decreaseIdCounter() {
        idCounter--;
    }

    public abstract boolean isNode();
}
