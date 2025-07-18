package blockchain.Block;

import blockchain.NetworkElement.MiningNode;

/**
 * Clase que extiende ValidateBlock para manejar las respuestas de la validación
 * de bloques.
 * Encapsula el resultado de la validación de un bloque junto con la referencia
 * al bloque y al nodo minador.
 *
 * @author Carlos García Santa y Joaquín Abad Díaz
 */
public class ValidateBlockRes extends ValidateBlock {
    private boolean validationRes; // Resultado de la validación del bloque.

    /**
     * Constructor que crea una instancia de ValidateBlockRes con el bloque
     * especificado,
     * el nodo minador y el resultado de la validación.
     *
     * @param block         El bloque que fue validado.
     * @param miningNode    El nodo minador que participó en la validación del
     *                      bloque.
     * @param validationRes El resultado de la validación del bloque (true si es
     *                      válido, false en caso contrario).
     */
    public ValidateBlockRes(Block block, MiningNode miningNode, boolean validationRes) {
        super(block, miningNode);
        this.validationRes = validationRes;
    }

    /**
     * Obtiene el mensaje base para este tipo de mensaje de validación.
     *
     * @return Una cadena constante que identifica este tipo de mensaje.
     */
    public String getMessage() {
        return "ValidateBlockRes";
    }

    /**
     * Obtiene el resultado de la validación del bloque.
     *
     * @return true si el bloque fue validado correctamente, false en caso
     *         contrario.
     */
    public boolean getValidationRes() {
        return validationRes;
    }

    /**
     * Genera y devuelve el mensaje completo de validación, que incluye detalles del
     * bloque,
     * el resultado de la validación y el identificador del nodo minador.
     *
     * @return Una cadena de texto que representa el mensaje completo de validación,
     *         incluyendo el identificador del bloque, el resultado de la validación
     *         y el identificador del nodo minador.
     */
    @Override
    public String getFullMessage() {
        Block block = getBlock();
        MiningNode miningNode = getMiningNode();
        return getMessage() + ": <b:" + block.getId() + ", res:" + validationRes + ", src:"
                + String.format("%03d", miningNode.getId()) + ">";
    }

}
