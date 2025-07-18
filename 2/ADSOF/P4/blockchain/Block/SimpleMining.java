package blockchain.Block;

import blockchain.Interfaces.IMiningMethod;
import blockchain.Transaction.Transaction;
import utils.*;

/**
 * Clase que implementa el método simple de minado para bloques en una cadena de
 * blockchain.
 * Proporciona funcionalidades para crear el hash de un bloque y para minar un
 * nuevo bloque basado en una transacción dada.
 *
 * @author Carlos García Santa y Joaquín Abad Díaz
 */
public class SimpleMining implements IMiningMethod {

    /**
     * Constructor para crear una instancia de SimpleMining.
     */
    public SimpleMining() {

    }

    /**
     * Crea un hash para un bloque dado utilizando SHA-256.
     * El hash se calcula basado en la versión del bloque, el hash del bloque
     * anterior, la marca de tiempo, la dificultad y el nonce del bloque.
     *
     * @param block El bloque para el cual se calcula el hash.
     * @return El hash calculado como una cadena de texto en formato hexadecimal.
     */
    public String createHash(Block block) {
        return CommonUtils.sha256(block.getVersion() + block.getPreviousBlockHash() + block.getTimestamp()
                + block.getDifficulty() + block.getNonce());
    }

    /**
     * Realiza el minado de un nuevo bloque utilizando una transacción y el bloque
     * confirmado anteriormente.
     * Establece el bloque anterior y calcula el hash del nuevo bloque.
     *
     * @param transaction            La transacción a incluir en el nuevo bloque.
     * @param previousConfirmedBlock El último bloque confirmado que precederá al
     *                               nuevo bloque.
     * @param minerKey               La clave del minero que genera el nuevo bloque.
     * @return El nuevo bloque minado.
     */
    public Block mineBlock(Transaction transaction, Block previousConfirmedBlock, String minerKey) {
        Block mineBlock = new Block(transaction, minerKey);
        mineBlock.setPreviousBlock(previousConfirmedBlock); // Establece el bloque anterior.
        mineBlock.setHash(createHash(mineBlock)); // Calcula y establece el hash del nuevo bloque.
        return mineBlock;
    }
}
