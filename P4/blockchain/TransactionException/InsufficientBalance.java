package blockchain.TransactionException;

import blockchain.Transaction.Wallet;

public class InsufficientBalance extends TransactionException {
    
    public InsufficientBalance(Wallet walletSender, String keyReceiver, int value){
        super(walletSender, keyReceiver, value);
    }

    @Override
    public String toString(){
        return "Insufficient balance attempt: " + getMessage() + " Actual source balance: " + walletSender.getBalance()  + "\n";
    }
}
