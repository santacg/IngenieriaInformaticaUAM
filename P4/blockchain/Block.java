package blockchain;


import java.util.Date;

import blockchain.Transaction.Transaction;
import blockchain.utils.*;

public class Block {
    private static int idCounter = 0;
    private int id;
    private int version;
    private int nonce;
    private int timestamp;
    private int difficulty;
    private Transaction o_Transaction;
    private boolean validation;
    private String hash;
    private Block previousBlock = null;

    public Block() {
        id = idCounter++;
        version = BlockConfig.VERSION;
        nonce = (int) Math.random() * (1000);
        timestamp = (int) (new Date().getTime()/1000);
        difficulty = BlockConfig.DIFFICULTY;
    }

    public int getVersion(){
        return version;
    }

    public int getNonce(){
        return nonce;
    }

    public int getTimestamp(){
        return timestamp;
    }

    public int getDifficulty(){
        return difficulty;
    }

    public String getHash(){
        return hash;
    }
    public Block getPreviousBlock(){
        return previousBlock;
    }

    public String getPreviousBlockHash(){
        if (previousBlock != null) {
            return previousBlock.hash;
        }
        return BlockConfig.GENESIS_BLOCK;
    }

}
