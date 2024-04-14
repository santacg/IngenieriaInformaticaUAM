package blockchain.NetworkElement;

import java.util.ArrayList;

import blockchain.Transaction.Transaction;
import blockchain.Transaction.TransactionNotification;
import blockchain.Transaction.Wallet;
import blockchain.Block.Block;
import blockchain.Block.SimpleMining;
import blockchain.Block.SimpleValidate;
import blockchain.Block.ValidateBlockRes;
import blockchain.Block.ValidateBlockRq;
import blockchain.Interfaces.IMessage;
import blockchain.Interfaces.IMiningMethod;
import blockchain.Interfaces.IValidateMethod;

public class MiningNode extends Node {
    private int mips; // Capacidad de minería del nodo, en MIPS.
    private static ArrayList<Block> validateBlocks;
    private int validatedBlocks = 0;
    private IMiningMethod miningMethod = new SimpleMining();
    private IValidateMethod validationMethod = new SimpleValidate();

    public MiningNode(Wallet wallet, int mips) {
        super(wallet); // Llama al constructor de la clase base (Node) para inicializar la billetera.
        validateBlocks = new ArrayList<>();
        this.mips = mips; // Establece la capacidad de minería.
    }

    private void addValidateBlocks(Block block) {
        block.setValidation(true);
        validateBlocks.add(block);
        validatedBlocks++;
    }

    @Override
    public void broadcast(IMessage msg) {
        msg.process(this);
        if (msg instanceof TransactionNotification) {
            broadcastTransactionNotification((TransactionNotification) msg);
        } else if (msg instanceof ValidateBlockRq) {
            broadcastValidateBlockRq((ValidateBlockRq) msg);
        } else if (msg instanceof ValidateBlockRes) {
            broadcastValidateBlockRes((ValidateBlockRes) msg);
        }
    }

    public void broadcastTransactionNotification(TransactionNotification msg) {
        Block block;
        Transaction transaction = msg.getTransaction();
        if (!this.containsTransaction(transaction)) {
            if (validateBlocks.isEmpty()) {
                block = this.miningMethod.mineBlock(transaction, null, this.getWalletPublicKey());
            } else {
                block = this.miningMethod.mineBlock(transaction, validateBlocks.get(validatedBlocks - 1),
                        this.getWalletPublicKey());
            }
            System.out.println("[" + fullName() + "] Mined block: " + block);
            this.getTopParent().broadcast(new ValidateBlockRq(block, this));
        } else {
            System.out.println("[" + fullName() + "] Transaction already confirmed: Tx-" + transaction.getId());
        }

    }

    public void broadcastValidateBlockRq(ValidateBlockRq msg) {
        boolean bool;
        if (msg.getMiningNode().getId() != this.getId()) {

            bool = validationMethod.validate(miningMethod, msg.getBlock());

            System.out.println("[" + fullName() + "]" +
                    " Emitted Task: " + "ValidateBlockRes" + ": <b:" + msg.getBlock().getId() + ", res:" + bool
                    + ", src:" + String.format("%03d", getId()) + ">");

            if (bool) {
                addValidateBlocks(msg.getBlock());
            }
            this.getTopParent().broadcast(new ValidateBlockRes(msg.getBlock(), this, bool));
        } else {
            System.out.println("[" + fullName() + "] You cannot validate your own block");
        }
    }

    public void setMiningMethod(IMiningMethod miningMethod) {
        this.miningMethod = miningMethod;
    }

    public void setValidationMethod(IValidateMethod validationMethod) {
        this.validationMethod = validationMethod;
    }

    /**
     * Devuelve el nombre completo del nodo de minería, incluyendo su ID con
     * formato.
     * 
     * @return El nombre completo del nodo de minería.
     */
    @Override
    public String fullName() {
        return "MiningNode#" + String.format("%03d", this.getId());
    }
}
