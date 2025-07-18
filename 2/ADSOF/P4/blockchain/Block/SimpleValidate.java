package blockchain.Block;

import blockchain.Interfaces.IMiningMethod;
import blockchain.Interfaces.IValidateMethod;

/**
 * Clase que implementa el método simple de validación de bloques en una cadena
 * de blockchain.
 * Esta clase valida un bloque asegurándose de que el hash calculado coincida
 * con el hash almacenado en el bloque.
 *
 * @author Carlos García Santa y Joaquín Abad Díaz
 */
public class SimpleValidate implements IValidateMethod {

    /**
     * Constructor para crear una instancia de SimpleValidate.
     */
    public SimpleValidate() {

    }

    /**
     * Valida un bloque comparando su hash almacenado con un hash recalculado.
     * Utiliza el método de minado proporcionado para recalcula el hash del bloque y
     * compara el resultado con el hash almacenado en el bloque.
     * 
     * @param miningMethod Instancia de IMiningMethod utilizada para calcular el
     *                     hash del bloque.
     * @param block        El bloque que se va a validar.
     * @return true si el hash recalculado coincide con el hash almacenado, false en
     *         caso contrario.
     */
    public boolean validate(IMiningMethod miningMethod, Block block) {
        return miningMethod.createHash(block).equals(block.getHash());
    }
}
