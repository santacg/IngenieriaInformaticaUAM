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

    public boolean isNode() {
        return true;
    }

    private void commitingTransaction(Transaction transaction) {
        System.out.println(
                "[" + fullName() + "]" + " Commiting transaction : Tx-" + transaction.getId() + " in " + fullName());
    }

    private void transactionDetails(Transaction transaction) {
        System.out.println("[" + fullName() + "]" + " -> Tx details:" + transaction);
        transactions.add(transaction);
    }

    private void appliedTransaction(Transaction transaction) {
        if (transaction.getKeySender() == wallet.getPublicKey()) {
            wallet.decreaseBalance(transaction.getValue());
        } else {
            wallet.increaseBalance(transaction.getValue());
        }
        System.out.println("[" + fullName() + "]" + " Applied Transaction: " + transaction);
    }

    private void newWalletValue(Transaction transaction) {
        System.out.println("[" + fullName() + "]" + " New wallet value: " + wallet);
    }

    public void broadcast(IMessage msg) {
        msg.process(this);
        if (msg instanceof ValidateBlockRes) {
            broadcastValidateBlockRes((ValidateBlockRes) msg);
        }
    }

    public void broadcastValidateBlockRes(ValidateBlockRes msg) {
        Transaction transaction = msg.getBlock().getTransaction();
        if (msg.getValidationRes()) {
            commitingTransaction(transaction);
            transactionDetails(transaction);
            if (transaction.getKeySender() == wallet.getPublicKey()
                    || transaction.getKeyReceiver() == wallet.getPublicKey()) {
                appliedTransaction(transaction);
                newWalletValue(transaction);
            }
        }
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
