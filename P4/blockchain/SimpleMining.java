package blockchain;

import blockchain.Transaction.Transaction;
import blockchain.utils.*;

public class SimpleMining implements IMiningMethod{
    
    public SimpleMining(){

    }
    public String createHash(Block block){
        return CommonUtils.sha256(block.getVersion() + block.getPreviousBlockHash() + block.getTimestamp() + block.getDifficulty() + block.getNonce());
    }

    public Block mineBlock(Transaction transaction, Block previousConfirmedBlock, String minerKey){
        Block mineBlock = new Block(transaction, previousConfirmedBlock, minerKey);
        mineBlock.setHash(createHash(mineBlock));
        return mineBlock;
    }
}
