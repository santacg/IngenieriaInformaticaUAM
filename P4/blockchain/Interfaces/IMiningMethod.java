package blockchain.Interfaces;

import blockchain.Block.Block;
import blockchain.Transaction.Transaction;

/**
 * Interfaz para los métodos de minado en una red de blockchain.
 * Define cómo crear un hash para un bloque y cómo minar un nuevo bloque basado
 * en una transacción y un bloque previo confirmado.
 * 
 */
public interface IMiningMethod {
    /**
     * Crea un hash para un bloque dado.
     * 
     * @param block El bloque para el cual se crea el hash.
     * @return El hash generado como una cadena de texto.
     */
    String createHash(Block block);

    /**
     * Realiza el minado de un nuevo bloque utilizando una transacción y el bloque
     * previamente confirmado.
     * 
     * @param transaction            La transacción que será incluida en el nuevo
     *                               bloque.
     * @param previousConfirmedBlock El bloque confirmado anterior que será
     *                               vinculado como el bloque anterior en la nueva
     *                               cadena.
     * @param minerKey               La clave del minero que realiza el minado.
     * @return El nuevo bloque minado.
     */
    Block mineBlock(Transaction transaction, Block previousConfirmedBlock, String minerKey);
}
