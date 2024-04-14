package blockchain.Transaction;

import blockchain.Interfaces.IMessage;

/**
 * Clase que implementa la interfaz IMessage para notificar sobre transacciones en la red de blockchain.
 * Esta clase encapsula una transacción, permitiendo su difusión a través de mensajes en la red.
 * 
 * @author Carlos García Santa y Joaquín Abad Díaz
 */
public class TransactionNotification implements IMessage {
    private Transaction transaction; // La transacción que se notifica.

    /**
     * Constructor que crea una notificación de transacción.
     * 
     * @param transaction La transacción que se desea notificar.
     */
    public TransactionNotification(Transaction transaction) {
        this.transaction = transaction;
    }

    /**
     * Devuelve la transacción asociada a esta notificación.
     * 
     * @return La transacción notificada.
     */
    public Transaction getTransaction() {
        return transaction;
    }

    /**
     * Devuelve el mensaje de la transacción en forma de cadena de texto.
     * 
     * @return Una cadena que representa la transacción notificada.
     */
    @Override
    public String getMessage() {
        return transaction.toString();
    }
}
