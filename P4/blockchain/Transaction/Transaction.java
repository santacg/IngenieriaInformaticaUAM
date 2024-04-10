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
        this.keySender = walletSender.getKey();
        this.keyReceiver = walletReceiver.getKey();
        this.value = value;
    }
}
