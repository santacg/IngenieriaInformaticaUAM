package blockchain.Interfaces;

import blockchain.Block.Block;
import blockchain.Transaction.Transaction;
public interface IMiningMethod {
    String createHash(Block block);

    Block mineBlock(Transaction transaction, Block previousConfirmedBlock, String minerKey);
}