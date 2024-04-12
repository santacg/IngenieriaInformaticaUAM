package blockchain.Transaction;

import blockchain.IMessage;

public class TransactionNotification implements IMessage {
    int id;
    String keySender;
    String ketReceiver;
    int value;
    public TransactionNotification(Transaction transaction){
        this.id = transaction.getId();
        this.keySender = transaction.getKeySender();
        this.ketReceiver = transaction.getKeyReceiver();
        this.value = transaction.getValue();
    }

    public String getMessage(){
        return "Transaction " + id + "| from: " + keySender + ", to: " + ketReceiver + ", quantity: " + value;
    }
}
