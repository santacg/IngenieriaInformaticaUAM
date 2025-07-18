package blockchain.NetworkElement;

import blockchain.Interfaces.IConnectable;

/**
 * Clase abstracta que representa un elemento de la red en una blockchain.
 * Todos los elementos de la red que pueden conectarse implementan esta clase,
 * proporcionando una base común para la gestión de identificadores y relaciones
 * jerárquicas.
 * 
 * @author Carlos García Santa y Joaquín Abad Díaz
 */
public abstract class NetworkElement implements IConnectable {
    private static int idCounter = 0; // Contador estático para asignar un ID único a cada elemento de la red.
    private int id; // Identificador único para cada elemento de la red.
    private IConnectable parent = null; // Elemento conectable superior en la jerarquía de la red.

    /**
     * Obtiene el elemento padre de este elemento en la jerarquía de la red.
     * 
     * @return El elemento conectable que actúa como padre de este elemento, o null
     *         si no tiene.
     */
    public IConnectable getParent() {
        return parent;
    }

    /**
     * Establece el elemento padre de este elemento en la jerarquía de la red.
     * 
     * @param parent El elemento conectable que se establecerá como padre.
     */
    public void setParent(IConnectable parent) {
        this.parent = parent;
    }

    /**
     * Obtiene el identificador único del elemento.
     * 
     * @return El identificador único del elemento.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador único del elemento.
     * 
     * @param id El nuevo identificador que se asignará al elemento.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el valor actual del contador de identificadores.
     * 
     * @return El valor actual del contador de ID.
     */
    public int getIdCounter() {
        return idCounter;
    }

    /**
     * Incrementa el contador de identificadores cuando se crea un nuevo elemento.
     */
    public void increaseIdCounter() {
        idCounter++;
    }

    /**
     * Decrementa el contador de identificadores, útil en casos de eliminación de
     * elementos.
     */
    public void decreaseIdCounter() {
        idCounter--;
    }

    /**
     * Método abstracto que determina si el elemento actual es un nodo dentro de la
     * red.
     * 
     * @return true si el elemento es un nodo, false en caso contrario.
     */
    public abstract boolean isNode();
}
