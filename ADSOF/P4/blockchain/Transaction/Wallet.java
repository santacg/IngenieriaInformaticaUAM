package blockchain.Transaction;

/**
 * Representa una billetera en la red de blockchain. Una billetera está asociada
 * a un usuario y contiene
 * claves criptográficas junto con el balance actual de monedas o tokens.
 * 
 * @author Carlos García Santa y Joaquín Abad Díaz
 */
public class Wallet {

    private String username; // Nombre de usuario asociado a la billetera.
    private String key; // Clave criptográfica de la billetera.
    private int balance; // Balance actual de la billetera en monedas o tokens.

    /**
     * Construye una nueva billetera con un nombre de usuario, clave y balance
     * inicial.
     * 
     * @param username El nombre de usuario asociado a la billetera.
     * @param key      La clave criptográfica de la billetera.
     * @param balance  El balance inicial de la billetera.
     */
    public Wallet(String username, String key, int balance) {
        this.username = username;
        this.key = key;
        this.balance = balance;
    }

    /**
     * Obtiene la clave de la billetera.
     * 
     * @return La clave criptográfica de la billetera.
     */
    public String getPublicKey() {
        return this.key;
    }

    /**
     * Obtiene el nombre de usuario asociado a la billetera.
     * 
     * @return El nombre de usuario de la billetera.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Obtiene el balance actual de la billetera.
     * 
     * @return El balance de la billetera en monedas o tokens.
     */
    public int getBalance() {
        return this.balance;
    }

    /**
     * Aumenta el balance de la billetera en la cantidad especificada.
     * 
     * @param value La cantidad de monedas o tokens a añadir al balance.
     */
    public void increaseBalance(int value) {
        balance += value;
    }

    /**
     * Disminuye el balance de la billetera en la cantidad especificada.
     * 
     * @param value La cantidad de monedas o tokens a restar del balance.
     */
    public void decreaseBalance(int value) {
        balance -= value;
    }

    /**
     * Proporciona una representación en cadena de la billetera, incluyendo el
     * nombre de usuario,
     * la clave criptográfica, y el balance.
     * 
     * @return Una cadena que representa la billetera.
     */
    @Override
    public String toString() {
        return "u: " + username + ", PK:" + key + ", balance: "
                + balance;
    }
}
