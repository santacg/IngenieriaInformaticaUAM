package blockchain.TransactionException;

import blockchain.Transaction.Wallet;

/**
 * Excepción lanzada cuando el emisor de una transacción no tiene suficiente
 * saldo para llevar a cabo la operación.
 * Extiende de TransactionException para incluir información detallada sobre el
 * emisor, el receptor y el valor implicado.
 */
public class InsufficientBalance extends TransactionException {

    /**
     * Construye una nueva excepción de balance insuficiente.
     * 
     * @param walletSender La billetera del emisor de la transacción.
     * @param keyReceiver  La clave pública del receptor de la transacción.
     * @param value        El valor de la transacción que se intentó y falló por
     *                     saldo insuficiente.
     */
    public InsufficientBalance(Wallet walletSender, String keyReceiver, int value) {
        super(walletSender, keyReceiver, value);
    }

    /**
     * Proporciona un mensaje descriptivo del error, incluyendo detalles de la
     * transacción y el saldo actual del emisor.
     * 
     * @return Una cadena descriptiva del intento fallido de transacción por saldo
     *         insuficiente.
     */
    @Override
    public String toString() {
        return "Insufficient balance attempt: " + getMessage() + " Actual source balance: "
                + getWalletSender().getBalance();
    }
}
