package blockchain.Transaction;

/**
 * Representa una transacción en la red de blockchain. Cada transacción tiene un
 * identificador único,
 * junto con las claves del emisor y receptor, y el valor de la transacción.
 * 
 * @author Carlos García Santa y Joaquín Abad Díaz
 */
public class Transaction {
    private static int idcounter = 0; // Contador estático para generar un ID único para cada transacción.
    private int id; // El identificador único de la transacción.
    private String keySender; // La clave del emisor de la transacción.
    private String keyReceiver; // La clave del receptor de la transacción.
    private int value; // El valor de la transacción.

    /**
     * Crea una nueva transacción entre dos billeteras con un valor específico.
     * 
     * @param walletSender   La billetera del emisor de la transacción.
     * @param walletReceiver La billetera del receptor de la transacción.
     * @param value          El valor de la transacción.
     */
    public Transaction(Wallet walletSender, Wallet walletReceiver, int value) {
        this.id = idcounter++; // Asigna el ID actual y luego incrementa el contador para la próxima
                               // transacción.
        this.keySender = walletSender.getPublicKey();
        this.keyReceiver = walletReceiver.getPublicKey();
        this.value = value;
    }

    /**
     * Constructor alternativo que crea una nueva transacción utilizando la clave
     * pública del receptor en lugar de su billetera.
     * 
     * @param walletSender La billetera del emisor de la transacción.
     * @param keyReceiver  La clave pública de la billetera del receptor de la
     *                     transacción.
     * @param value        El valor de la transacción.
     */
    public Transaction(Wallet walletSender, String keyReceiver, int value) {
        this.id = idcounter++; // Asigna el ID actual y luego incrementa el contador para la próxima
                               // transacción.
        this.keySender = walletSender.getPublicKey();
        this.keyReceiver = keyReceiver;
        this.value = value;
    }

    /**
     * Devuelve el identificador único de la transacción.
     * 
     * @return El identificador de la transacción.
     */
    public int getId() {
        return id;
    }

    /**
     * Devuelve la clave pública del emisor de la transacción.
     * 
     * @return La clave pública del emisor.
     */
    public String getKeySender() {
        return keySender;
    }

    /**
     * Devuelve la clave pública del receptor de la transacción.
     * 
     * @return La clave pública del receptor.
     */
    public String getKeyReceiver() {
        return keyReceiver;
    }

    /**
     * Devuelve el valor monetario de la transacción.
     * 
     * @return El valor de la transacción.
     */
    public int getValue() {
        return value;
    }

    /**
     * Proporciona una representación en cadena de la transacción, incluyendo el
     * identificador,
     * las claves del emisor y del receptor, y el valor de la transacción.
     * 
     * @return Una cadena que representa la transacción.
     */
    @Override
    public String toString() {
        return "Transaction " + id + "| from: " + keySender + ", to: " + keyReceiver + ", quantity: " + value;
    }
}
