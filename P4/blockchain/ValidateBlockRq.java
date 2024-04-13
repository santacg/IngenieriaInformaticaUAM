package blockchain;

import blockchain.NetworkElement.MiningNode;

public class ValidateBlockRq extends ValidateBlock {

    public ValidateBlockRq(Block block, MiningNode miningNode) {
        super(block, miningNode);
    }

    public String getMessage() {
        return "ValidateBlockRq";
    }

    @Override
    public String getFullMessage() {
        Block block = getBlock();
        MiningNode miningNode = getMiningNode();
        return getMessage() + ": <b:" + block.getId() + ", src:" + String.format("%03d", miningNode.getId()) + ">";
    }
}
