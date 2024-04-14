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

/**
 * Nodo especializado en la red de blockchain que tiene capacidades adicionales
 * de minería y validación de bloques.
 * Extiende la clase Node agregando la capacidad de minar y validar bloques
 * utilizando métodos específicos de minería y validación.
 * 
 * @author Carlos García Santa y Joaquín Abad Díaz
 */
public class MiningNode extends Node {
    private int mips; // Capacidad de minería del nodo, medida en MIPS.
    private static ArrayList<Block> validateBlocks; // Lista de bloques validados por este nodo.
    private int validatedBlocks = 0; // Contador de bloques validados por este nodo.
    private IMiningMethod miningMethod = new SimpleMining(); // Método de minería utilizado por este nodo.
    private IValidateMethod validationMethod = new SimpleValidate(); // Método de validación utilizado por este nodo.

    /**
     * Constructor para crear un nodo de minería con una billetera asociada y
     * capacidad de minería específica.
     * 
     * @param wallet La billetera asociada con este nodo de minería.
     * @param mips   La capacidad de minería del nodo, medida en MIPS.
     */
    public MiningNode(Wallet wallet, int mips) {
        super(wallet); // Llama al constructor de la clase base (Node) para inicializar la billetera.
        validateBlocks = new ArrayList<>();
        this.mips = mips; // Establece la capacidad de minería.
    }

    /**
     * Agrega un bloque a la lista de bloques validados y marca el bloque como
     * validado.
     * 
     * @param block El bloque que se ha validado.
     */
    private void addValidateBlocks(Block block) {
        block.setValidation(true);
        validateBlocks.add(block);
        validatedBlocks++;
    }

    /**
     * Maneja la difusión de mensajes, procesando diferentes tipos de mensajes
     * específicos de transacciones y validaciones de bloques.
     * 
     * @param msg El mensaje a difundir y procesar.
     */
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

    /**
     * Procesa y responde a una notificación de transacción intentando minar un
     * nuevo bloque si la transacción aún no está confirmada.
     * 
     * @param msg La notificación de transacción recibida.
     */
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

    /**
     * Procesa una solicitud de validación de bloque recibida, validando el bloque a
     * menos que haya sido minado por este mismo nodo.
     * 
     * @param msg El mensaje de solicitud de validación de bloque recibido.
     */
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

    /**
     * Establece el método de minería a utilizar por este nodo.
     * 
     * @param miningMethod El método de minería a establecer.
     */
    public void setMiningMethod(IMiningMethod miningMethod) {
        this.miningMethod = miningMethod;
    }

    /**
     * Establece el método de validación a utilizar por este nodo.
     * 
     * @param validationMethod El método de validación a establecer.
     */
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
