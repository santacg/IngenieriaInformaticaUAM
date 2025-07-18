package blockchain.NetworkElement;

import java.util.ArrayList;

import blockchain.Block.ValidateBlockRes;
import blockchain.Interfaces.IMessage;
import blockchain.TransactionException.*;
import blockchain.Transaction.*;

/**
 * Representa un nodo en la red de blockchain. Cada nodo tiene un identificador
 * único,
 * una billetera asociada y puede realizar transacciones.
 * 
 * @author Carlos García Santa y Joaquín Abad Díaz
 */
public class Node extends NetworkElement {
    private Wallet wallet; // La billetera asociada con el nodo
    private ArrayList<Transaction> transactions; // Las transacciones realizadas por el nodo

    /**
     * Construye un nodo con una billetera asociada y asigna un ID único.
     * 
     * @param wallet La billetera asociada con este nodo.
     */
    public Node(Wallet wallet) {
        setId(getIdCounter());
        increaseIdCounter();
        this.wallet = wallet;
        this.transactions = new ArrayList<>();
    }

    /**
     * Devuelve el nombre completo del nodo, incluyendo su ID con formato.
     * 
     * @return El nombre completo del nodo.
     */
    public String fullName() {
        return "Node#" + String.format("%03d", getId());
    }

    /**
     * Crea una transacción desde este nodo hacia otra billetera especificada.
     * 
     * @param wallet La billetera destino de la transacción.
     * @param value  El valor de la transacción.
     * @return La transacción creada.
     * @throws TransactionException Si el valor es insuficiente o negativo.
     */
    public Transaction createTransaction(Wallet wallet, int value) throws TransactionException {
        if (value > this.wallet.getBalance()) {
            throw new InsufficientBalance(this.wallet, wallet.getPublicKey(), value);
        }
        if (value < 1) {
            throw new NegativeTransfer(this.wallet, wallet.getPublicKey(), value);
        }
        Transaction transaction = new Transaction(this.wallet, wallet, value);
        return transaction;
    }

    /**
     * Crea una transacción desde este nodo hacia un destinatario identificado por
     * clave pública.
     * 
     * @param keyReceiver La clave pública del destinatario.
     * @param value       El valor de la transacción.
     * @return La transacción creada.
     * @throws TransactionException Si el valor es insuficiente o negativo.
     */
    public Transaction createTransaction(String keyReceiver, int value) throws TransactionException {
        if (value > wallet.getBalance()) {
            throw new InsufficientBalance(wallet, keyReceiver, value);
        }
        if (value < 1) {
            throw new NegativeTransfer(wallet, keyReceiver, value);
        }
        Transaction transaction = new Transaction(wallet, keyReceiver, value);
        return transaction;
    }

    /**
     * Determina si el elemento actual es un nodo.
     * 
     * @return true, indicando que este elemento siempre es un nodo.
     */
    public boolean isNode() {
        return true;
    }

    /**
     * Registra el compromiso de una transacción en el sistema de logs del nodo,
     * indicando que la transacción está siendo procesada.
     *
     * @param transaction La transacción que se está comprometiendo.
     */
    private void commitingTransaction(Transaction transaction) {
        System.out.println(
                "[" + fullName() + "]" + " Commiting transaction : Tx-" + transaction.getId() + " in " + fullName());
    }

    /**
     * Registra los detalles de una transacción en el sistema de logs del nodo y la
     * añade a la lista de transacciones del nodo.
     *
     * @param transaction La transacción que se está detallando.
     */
    private void transactionDetails(Transaction transaction) {
        System.out.println("[" + fullName() + "]" + " -> Tx details:" + transaction);
        transactions.add(transaction);
    }

    /**
     * Aplica los efectos de una transacción al balance de la billetera del nodo,
     * aumentando o disminuyendo según corresponda,
     * y registra la aplicación de esta transacción en el sistema de logs del nodo.
     *
     * @param transaction La transacción que se está aplicando.
     */
    private void appliedTransaction(Transaction transaction) {
        if (transaction.getKeySender() == wallet.getPublicKey()) {
            wallet.decreaseBalance(transaction.getValue());
        } else {
            wallet.increaseBalance(transaction.getValue());
        }
        System.out.println("[" + fullName() + "]" + " Applied Transaction: " + transaction);
    }

    /**
     * Actualiza y muestra el nuevo valor de la billetera del nodo después de una
     * transacción.
     *
     * @param transaction La transacción que ha modificado el balance de la
     *                    billetera.
     */
    private void newWalletValue(Transaction transaction) {
        System.out.println("[" + fullName() + "]" + " New wallet value: " + wallet);
    }

    /**
     * Difunde un mensaje a través del nodo, procesándolo localmente y pasándolo a
     * otros componentes si es necesario.
     *
     * @param msg El mensaje a difundir.
     */
    public void broadcast(IMessage msg) {
        msg.process(this);
        if (msg instanceof ValidateBlockRes) {
            broadcastValidateBlockRes((ValidateBlockRes) msg);
        }
    }

    /**
     * Maneja la validación de un resultado de bloque, comprometiendo y actualizando
     * detalles de la transacción asociada si es válida.
     *
     * @param msg El mensaje de resultado de validación del bloque que contiene la
     *            transacción y el resultado de la validación.
     */
    public void broadcastValidateBlockRes(ValidateBlockRes msg) {
        Transaction transaction = msg.getBlock().getTransaction();
        if (msg.getValidationRes()) {
            commitingTransaction(transaction);
            transactionDetails(transaction);
            if (transaction.getKeySender() == wallet.getPublicKey()
                    || transaction.getKeyReceiver() == wallet.getPublicKey()) {// Si el nodo actual es el emisor o
                                                                               // receptor de la transacción,
                                                                               // actualizará el balance y mostrará la
                                                                               // información conveniente
                appliedTransaction(transaction);
                newWalletValue(transaction);
            }
        }
    }

    /**
     * Añade una transacción a la lista de transacciones del nodo.
     *
     * @param transaction La transacción a añadir.
     */
    protected void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
    }

    /**
     * Elimina una transacción de la lista de transacciones del nodo.
     *
     * @param transaction La transacción a eliminar.
     */
    protected void removeTransaction(Transaction transaction) {
        this.transactions.add(transaction);
    }

    /**
     * Comprueba si una transacción específica está contenida en la lista de
     * transacciones del nodo.
     *
     * @param transaction La transacción a verificar.
     * @return true si la transacción está en la lista, false en caso contrario.
     */
    protected boolean containsTransaction(Transaction transaction) {
        if (transactions.contains(transaction)) {
            return true;
        }
        return false;
    }

    /**
     * Devuelve la clave pública de la billetera asociada con este nodo.
     *
     * @return La clave pública de la billetera del nodo.
     */
    protected String getWalletPublicKey() {
        return wallet.getPublicKey();
    }

    /**
     * Representación en cadena del nodo que incluye la información de la billetera
     * y el nombre del nodo.
     * 
     * @return Una cadena representando la información del nodo.
     */
    @Override
    public String toString() {
        return wallet + " | @" + fullName();
    }
}
