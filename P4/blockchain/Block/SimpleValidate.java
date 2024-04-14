package blockchain.Block;

import blockchain.Interfaces.IMiningMethod;
import blockchain.Interfaces.IValidateMethod;

public class SimpleValidate implements IValidateMethod{

    public SimpleValidate(){

    }

    public boolean validate(IMiningMethod miningMethod, Block block){
        if(miningMethod.createHash(block).compareTo(block.getHash()) == 0){
            return true;
        }
        return false;
    }
}