package blockchain;

import blockchain.NetworkElement.MiningNode;

public class ValidateBlockRes extends ValidateBlock{
    private boolean validationRes;
    public ValidateBlockRes(Block block, MiningNode miningNode, boolean validationRes) {
        super(block, miningNode);
        this.validationRes = validationRes;
    }
    public String getMessage() {
        return "ValidateBlockRes";
    }
    
    @Override
    public String getFullMessage() {
        Block block = getBlock();
        MiningNode miningNode = getMiningNode();
        return getMessage() + ": <b:" + block.getId() + ", res:" + validationRes + ", src:" + String.format("%03d", miningNode.getId()) + ">";
    }

}
