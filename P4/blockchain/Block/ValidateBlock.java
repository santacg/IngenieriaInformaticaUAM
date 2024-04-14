package blockchain.Block;

import blockchain.Interfaces.IMessage;
import blockchain.NetworkElement.MiningNode;
import blockchain.NetworkElement.Node;

/**
 * Clase abstracta que define la estructura básica de un mensaje de validación
 * de bloque en una red blockchain.
 * Esta clase encapsula la información del bloque a validar y del nodo minador
 * que ha generado el bloque.
 * Implementa la interfaz IMessage, permitiendo el procesamiento de estos
 * mensajes dentro de la red.
 *
 * @author Carlos García Santa y Joaquín Abad Díaz
 */
public abstract class ValidateBlock implements IMessage {
    private Block block; // El bloque que se va a validar.
    private MiningNode miningNode; // El nodo minador responsable de la creación del bloque.

    /**
     * Constructor que inicializa un nuevo mensaje de validación de bloque.
     * 
     * @param block      El bloque que se desea validar.
     * @param miningNode El nodo minador que generó el bloque.
     */
    public ValidateBlock(Block block, MiningNode miningNode) {
        this.block = block;
        this.miningNode = miningNode;
    }

    /**
     * Obtiene el bloque asociado a este mensaje de validación.
     * 
     * @return El bloque a validar.
     */
    public Block getBlock() {
        return block;
    }

    /**
     * Obtiene el nodo minador que generó el bloque y envió este mensaje de
     * validación.
     * 
     * @return El nodo minador.
     */
    public MiningNode getMiningNode() {
        return miningNode;
    }

    /**
     * Procesa el mensaje de validación en el nodo especificado, imprimiendo una
     * tarea recibida.
     * Este método define cómo se maneja el mensaje de validación en el contexto del
     * nodo dado.
     *
     * @param n El nodo en el que se procesa el mensaje.
     */
    @Override
    public void process(Node n) {
        System.out.println("[" + n.fullName() + "]" +
                " Received Task: " +
                this.getFullMessage());
    }

    /**
     * Método abstracto para
     * proporcionar el mensaje completo de validación.
     * Este mensaje incluye detalles adicionales específicos del tipo de mensaje de
     * validación.
     *
     * @return El mensaje completo de validación como una cadena de texto.
     */
    public abstract String getFullMessage();
}
