package blockchain;

import blockchain.NetworkElement.MiningNode;
import blockchain.NetworkElement.Node;

public abstract class ValidateBlock implements IMessage{
    private Block block;
    private MiningNode miningNode;

    public ValidateBlock(Block block, MiningNode miningNode) {
        this.block = block;
        this.miningNode = miningNode;
    }

    public Block getBlock(){
        return block;
    }

    public MiningNode getMiningNode(){
        return miningNode;
    }

    public void process(Node n) {
        System.out.println("[" + n.fullName() + "]" +
                " Received Task: " +
                this.getFullMessage());
    }

    public abstract String getFullMessage();

}
