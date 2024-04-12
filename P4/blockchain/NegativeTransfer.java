package blockchain;

import blockchain.Transaction.Wallet;

public class NegativeTransfer extends TransactionException{
    
    public NegativeTransfer(Wallet walletSender, String keyReceiver, int value){
        super(walletSender, keyReceiver, value);
    }

    @Override
    public String toString(){
        return "Negative transfer attempt: " + getMessage();
    }
}
