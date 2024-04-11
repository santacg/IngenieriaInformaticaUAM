package blockchain.NetworkElement;

import java.util.ArrayList;


import blockchain.Transaction.*;

/**
 * Representa un nodo en la red de blockchain. Cada nodo tiene un identificador único,
 * una billetera asociada y puede realizar transacciones.
 * 
 * @author Carlos García Santa y Joaquín Abad Díaz
 */
public class Node extends NetworkElement {
    private static int idcounter = 0; // Contador estático para asegurar un ID único para cada nodo
    private Wallet wallet; // La billetera asociada con el nodo
    private ArrayList<Transaction> transactions; // Las transacciones realizadas por el nodo

    /**
     * Construye un nodo con una billetera asociada y asigna un ID único.
     * 
     * @param wallet La billetera asociada con este nodo.
     */
    public Node(Wallet wallet) {
        setId(idcounter++);
        this.wallet = wallet;
        this.transactions = new ArrayList<>();
    }


    /**
     * Devuelve el nombre completo del nodo, incluyendo su ID con formato.
     * 
     * @return El nombre completo del nodo.
     */
    public String fullName() {
        return "Node#" + String.format("%03d", id);
    }
    
    /**
     * Representación en cadena del nodo que incluye la información de la billetera y el nombre del nodo.
     * 
     * @return Una cadena representando la información del nodo.
     */
    @Override
    public String toString() {
        return "u: " + this.wallet.getUsername() + ", PK: " + this.wallet.getKey() + ", balance: " + this.wallet.getBalance() + " | @" + fullName();
    }
}
