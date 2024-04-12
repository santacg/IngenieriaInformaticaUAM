package blockchain;

import blockchain.Transaction.Wallet;

public class TransactionException extends RuntimeException{
    Wallet walletSender;
    String keyReceiver;
    int value;

    public TransactionException(Wallet walletSender, String keyReceiver, int value){
        this.walletSender = walletSender;
        this.keyReceiver = keyReceiver;
        this.value = value;
    }

    public String getMessage(){
        return "source: " + walletSender.getPublicKey() + ", receiver: " + keyReceiver + ", amount: " + value;
    }
}
