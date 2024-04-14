package blockchain.Interfaces;

import blockchain.Block.Block;

/**
 * Interfaz para los métodos de validación de bloques en una red de blockchain.
 * Define cómo se valida un bloque utilizando un método de minado.
 * 
 */
public interface IValidateMethod {
    /**
     * Valida un bloque utilizando un método de minado específico.
     * Comprueba que el hash del bloque es correcto y que el bloque cumple con los
     * criterios de la red.
     * 
     * @param miningMethod El método de minado utilizado para verificar el bloque.
     * @param block        El bloque que se necesita validar.
     * @return true si el bloque es válido, false en caso contrario.
     */
    boolean validate(IMiningMethod miningMethod, Block block);
}
