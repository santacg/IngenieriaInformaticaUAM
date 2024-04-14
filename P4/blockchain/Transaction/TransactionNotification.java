package blockchain.Transaction;

import blockchain.Interfaces.IMessage;
import blockchain.NetworkElement.MiningNode;

public class TransactionNotification implements IMessage {
    Transaction transaction;
    public TransactionNotification(Transaction transaction){
        this.transaction = transaction;
    }

    public Transaction getTransaction(){
        return transaction;
    }

    public String getMessage(){
        return transaction.toString();
    }

}
