package blockchain.Interfaces;

import blockchain.Block.Block;

public interface IValidateMethod {
    public boolean validate(IMiningMethod miningMethod, Block block);
}