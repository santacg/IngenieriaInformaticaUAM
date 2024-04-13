package blockchain.NetworkElement;

import java.util.ArrayList;

import blockchain.IMessage;
import blockchain.TransactionException.*;
import blockchain.Transaction.*;
import blockchain.TransactionException.InsufficientBalance;
import blockchain.TransactionException.NegativeTransfer;

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

    public Transaction createTransaction(Wallet wallet, int value) throws TransactionException {
        if (value > this.wallet.getBalance()) {
            throw new InsufficientBalance(this.wallet, wallet.getPublicKey(), value);
        }
        if (value < 1) {
            throw new NegativeTransfer(this.wallet, wallet.getPublicKey(), value);
        }
        Transaction transaction = new Transaction(this.wallet, wallet, value);
        transactions.add(transaction);
        return transaction;
    }

    public Transaction createTransaction(String keyReceiver, int value) throws TransactionException {
        if (value > this.wallet.getBalance()) {
            throw new InsufficientBalance(this.wallet, keyReceiver, value);
        }
        if (value < 1) {
            throw new NegativeTransfer(this.wallet, keyReceiver, value);
        }
        Transaction transaction = new Transaction(this.wallet, wallet, value);
        transactions.add(transaction);
        return transaction;
    }

    public boolean isNode() {
        return true;
    }

    public void broadcast(IMessage msg) {
        msg.process(this);
    }

    protected void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
    }

    protected void removeTransaction(Transaction transaction) {
        this.transactions.add(transaction);
    }

    protected boolean containsTransaction(Transaction transaction) {
        if (transactions.contains(transaction)) {
            return true;
        }
        return false;
    }

    protected String getWalletPublicKey(){
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
        return "u: " + this.wallet.getUsername() + ", PK: " + this.wallet.getPublicKey() + ", balance: "
                + this.wallet.getBalance() + " | @" + fullName();
    }
}
