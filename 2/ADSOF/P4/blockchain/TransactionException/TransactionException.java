package blockchain.TransactionException;

import blockchain.Transaction.Wallet;

/**
 * Excepción personalizada para manejar errores que ocurran durante las
 * transacciones en la red de blockchain.
 * Extiende RuntimeException y proporciona detalles específicos del error
 * relacionado con la transacción.
 * 
 * @author Carlos García Santa y Joaquín Abad Díaz
 */
public abstract class TransactionException extends RuntimeException {
    private Wallet walletSender; // La billetera del emisor de la transacción.
    private String keyReceiver; // La clave pública del receptor de la transacción.
    private int value; // El valor involucrado en la transacción.

    /**
     * Constructor que crea una excepción de transacción especificando el emisor, el
     * receptor y el valor de la transacción.
     * 
     * @param walletSender La billetera del emisor de la transacción.
     * @param keyReceiver  La clave pública del receptor de la transacción.
     * @param value        El valor de la transacción.
     */
    public TransactionException(Wallet walletSender, String keyReceiver, int value) {
        this.walletSender = walletSender;
        this.keyReceiver = keyReceiver;
        this.value = value;
    }

    /**
     * Devuelve la billetera del emisor de la transacción que causó la excepción.
     * 
     * @return La billetera del emisor.
     */
    protected Wallet getWalletSender() {
        return walletSender;
    }

    /**
     * Devuelve un mensaje detallando la excepción de transacción.
     * 
     * @return Un mensaje que describe el error ocurrido durante la transacción.
     */
    @Override
    public String getMessage() {
        return "source: " + walletSender.getPublicKey() + ", receiver: " + keyReceiver + ", amount: " + value;
    }
}
