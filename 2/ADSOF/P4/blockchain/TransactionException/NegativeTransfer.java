package blockchain.TransactionException;

import blockchain.Transaction.Wallet;

/**
 * Excepción lanzada cuando se intenta realizar una transferencia con un valor
 * negativo.
 * Extiende de TransactionException para utilizar la información del emisor,
 * receptor y el valor negativo intentado.
 */
public class NegativeTransfer extends TransactionException {

    /**
     * Construye una nueva excepción de transferencia negativa.
     * 
     * @param walletSender La billetera del emisor de la transacción.
     * @param keyReceiver  La clave pública del receptor de la transacción.
     * @param value        El valor negativo de la transacción que se intentó.
     */
    public NegativeTransfer(Wallet walletSender, String keyReceiver, int value) {
        super(walletSender, keyReceiver, value);
    }

    /**
     * Proporciona un mensaje descriptivo del error.
     * 
     * @return Una cadena descriptiva del intento fallido de realizar una
     *         transferencia con valor negativo.
     */
    @Override
    public String toString() {
        return "Negative transfer attempt: " + super.getMessage();
    }
}
