package blockchain.Block;

import java.util.Date;

import blockchain.Transaction.Transaction;
import blockchain.utils.*;

public class Block {
    private static int idCounter = 0;
    private int id;
    private int version = BlockConfig.VERSION;
    private int nonce;
    private int timestamp;
    private int difficulty = BlockConfig.DIFFICULTY;
    private Transaction o_Transaction;
    private boolean validation = false;
    private String hash;
    private Block previousBlock;
    private String minerKey;

    public Block(Transaction transaction, String minerKey) {
        id = idCounter++;
        nonce = (int) (Math.random() * 1000);
        timestamp = (int) (new Date().getTime() / 1000);
        o_Transaction = transaction;
        this.minerKey = minerKey;
    }

    public int getId(){
        return id;
    }

    public int getVersion() {
        return version;
    }

    public int getNonce() {
        return nonce;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public Transaction getTransaction(){
        return o_Transaction;
    }

    public boolean getValidation(){
        return validation;
    }

    public void setValidation(boolean validation){
        this.validation = validation;
    }

    public String getHash() {
        return hash;
    }

    public Block getPreviousBlock() {
        return previousBlock;
    }

    public void setPreviousBlock(Block block){
        previousBlock = block;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getPreviousBlockHash() {
        if (previousBlock != null) {
            return previousBlock.hash;
        }
        return BlockConfig.GENESIS_BLOCK;
    }

    @Override
    public String toString() {
        return "id: " + this.id + ", v: " + this.version + ", nonce: "
                + this.nonce + ", ts: " + this.timestamp + ", diff: " + this.difficulty + ", hash: " + this.hash
                + ", minerK: " + this.minerKey;
    }

}
