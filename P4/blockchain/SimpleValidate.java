package blockchain;

public class SimpleValidate implements IValidateMethod{

    public SimpleValidate(){

    }

    public boolean validate(IMiningMethod miningMethod, Block block){
        if(miningMethod.createHash(block) == block.getHash()){
            return true;
        }
        return false;
    }
}