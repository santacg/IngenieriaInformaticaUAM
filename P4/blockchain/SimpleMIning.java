package blockchain;

import blockchain.Transaction.Transaction;
import blockchain.utils.*;
import blockchain.Block;

public class SimpleMIning implements IMiningMethod{
    
    public SimpleMIning(){

    }
    public String createHash(Block block){
        return CommonUtils.sha256(block.getVersion() + block.getPreviousBlockHash() + block.getTimestamp() + block.getDifficulty() + block.getNonce());
    }

    public Block mineBlock(Transaction transaction, Block block, String string){
        Block block1 = new Block();
    }
}
