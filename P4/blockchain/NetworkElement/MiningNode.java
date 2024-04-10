package blockchain.NetworkElement;

import blockchain.Transaction.Wallet;

/**
 * Representa un nodo de minería en la red de blockchain. Extiende la clase Node añadiendo
 * capacidad de minería, representada por MIPS (Millones de Instrucciones Por Segundo).
 * 
 * @author Carlos García Santa y Joaquín Abad Díaz
 */
public class MiningNode extends Node {
    private int mips; // Capacidad de minería del nodo, en MIPS.

    /**
     * Construye un nodo de minería con una billetera asociada y capacidad de minería específica.
     * 
     * @param wallet La billetera asociada con este nodo de minería.
     * @param mips La capacidad de minería del nodo, en Millones de Instrucciones Por Segundo.
     */
    public MiningNode(Wallet wallet, int mips) {
        super(wallet); // Llama al constructor de la clase base (Node) para inicializar la billetera.
        this.mips = mips; // Establece la capacidad de minería.
    }

    /**
     * Devuelve el nombre completo del nodo de minería, incluyendo su ID con formato.
     * 
     * @return El nombre completo del nodo de minería.
     */
    @Override
    public String fullName() {
        return "MiningNode#" + String.format("%03d", this.getId());
    }
}
