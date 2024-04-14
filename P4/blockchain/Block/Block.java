package blockchain.Block;

import java.util.Date;
import blockchain.Transaction.Transaction;
import blockchain.utils.*;

/**
 * Representa un bloque en la cadena de bloques, conteniendo información
 * de transacciones y detalles para su validación y conexión con el bloque
 * anterior.
 *
 * @author Carlos García Santa y Joaquín Abad Díaz
 */
public class Block {
    private static int idCounter = 0; // Identificador único de bloque, autoincrementado.
    private int id; // Identificador del bloque.
    private int version = BlockConfig.VERSION; // Versión del bloque, por defecto se toma de la configuración de bloque.
    private int nonce; // Número aleatorio para la generación de hash.
    private int timestamp; // Marca de tiempo del bloque.
    private int difficulty = BlockConfig.DIFFICULTY; // Dificultad para la minería del bloque.
    private Transaction o_Transaction; // Transacción asociada al bloque.
    private boolean validation = false; // Indicador de si el bloque ha sido validado.
    private String hash; // Hash del bloque.
    private Block previousBlock; // Bloque anterior en la cadena.
    private String minerKey; // Clave del minero que crea el bloque.

    /**
     * Constructor para crear un nuevo bloque con la transacción y clave del minero
     * especificados.
     *
     * @param transaction La transacción que será incluida en el bloque.
     * @param minerKey    La clave pública del minero que genera el bloque.
     */
    public Block(Transaction transaction, String minerKey) {
        id = idCounter++;
        nonce = (int) (Math.random() * 1000);
        timestamp = (int) (new Date().getTime() / 1000);
        o_Transaction = transaction;
        this.minerKey = minerKey;
    }

    /**
     * Obtiene el identificador único del bloque.
     * 
     * @return el identificador del bloque.
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene la versión del bloque.
     * 
     * @return la versión del bloque.
     */
    public int getVersion() {
        return version;
    }

    /**
     * Obtiene el nonce del bloque, utilizado en la generación del hash.
     * 
     * @return el nonce del bloque.
     */
    public int getNonce() {
        return nonce;
    }

    /**
     * Obtiene la marca de tiempo del bloque, indicando cuando fue creado.
     * 
     * @return la marca de tiempo del bloque.
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * Obtiene la dificultad de minado del bloque.
     * 
     * @return la dificultad del bloque.
     */
    public int getDifficulty() {
        return difficulty;
    }

    /**
     * Obtiene la transacción asociada a este bloque.
     * 
     * @return la transacción del bloque.
     */
    public Transaction getTransaction() {
        return o_Transaction;
    }

    /**
     * Obtiene el estado de validación del bloque.
     * 
     * @return true si el bloque ha sido validado, false en caso contrario.
     */
    public boolean getValidation() {
        return validation;
    }

    /**
     * Establece el estado de validación del bloque.
     * 
     * @param validation true para validar el bloque, false para invalidarlo.
     */
    public void setValidation(boolean validation) {
        this.validation = validation;
    }

    /**
     * Obtiene el hash del bloque.
     * 
     * @return el hash del bloque.
     */
    public String getHash() {
        return hash;
    }

    /**
     * Obtiene el bloque anterior en la cadena de bloques.
     * 
     * @return el bloque anterior.
     */
    public Block getPreviousBlock() {
        return previousBlock;
    }

    /**
     * Establece el bloque anterior en la cadena de bloques.
     * 
     * @param block el bloque a establecer como anterior.
     */
    public void setPreviousBlock(Block block) {
        previousBlock = block;
    }

    /**
     * Establece el hash del bloque.
     * 
     * @param hash el nuevo hash del bloque.
     */
    public void setHash(String hash) {
        this.hash = hash;
    }

    /**
     * Devuelve el hash del bloque anterior si existe, o el hash del bloque génesis
     * si no hay bloque anterior.
     * 
     * @return el hash del bloque anterior o del bloque génesis.
     */
    public String getPreviousBlockHash() {
        if (previousBlock != null) {
            return previousBlock.hash;
        }
        return BlockConfig.GENESIS_BLOCK;
    }

    /**
     * Representación en cadena del bloque con información clave.
     * 
     * @return una cadena que representa los datos del bloque.
     */
    @Override
    public String toString() {
        return "id: " + this.id + ", v: " + this.version + ", nonce: "
                + this.nonce + ", ts: " + this.timestamp + ", diff: " + this.difficulty + ", hash: " + this.hash
                + ", minerK: " + this.minerKey;
    }

}
