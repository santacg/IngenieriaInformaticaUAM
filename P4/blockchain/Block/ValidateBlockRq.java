package blockchain.Block;

import blockchain.NetworkElement.MiningNode;

/**
 * Clase que extiende ValidateBlock para manejar las solicitudes de validación
 * de bloques.
 * Esta clase encapsula los datos necesarios para solicitar la validación de un
 * bloque en la red blockchain.
 *
 * @author Carlos García Santa y Joaquín Abad Díaz
 */
public class ValidateBlockRq extends ValidateBlock {

    /**
     * Constructor que inicializa una nueva solicitud de validación de bloque.
     *
     * @param block      El bloque para el cual se solicita la validación.
     * @param miningNode El nodo minador que envía la solicitud de validación.
     */
    public ValidateBlockRq(Block block, MiningNode miningNode) {
        super(block, miningNode);
    }

    /**
     * Obtiene el mensaje base para este tipo de mensaje de validación.
     *
     * @return Una cadena constante que identifica este tipo de mensaje.
     */
    public String getMessage() {
        return "ValidateBlockRq";
    }

    /**
     * Genera y devuelve el mensaje completo de la solicitud de validación,
     * que incluye detalles del bloque y el identificador del nodo minador.
     *
     * @return Una cadena de texto que representa el mensaje completo de la
     *         solicitud de validación,
     *         incluyendo el identificador del bloque y el identificador del nodo
     *         minador.
     */
    @Override
    public String getFullMessage() {
        Block block = getBlock();
        MiningNode miningNode = getMiningNode();
        return getMessage() + ": <b:" + block.getId() + ", src:" + String.format("%03d", miningNode.getId()) + ">";
    }
}
