package blockchain;

public interface IValidateMethod {
    public boolean validate(IMiningMethod miningMethod, Block block);
}