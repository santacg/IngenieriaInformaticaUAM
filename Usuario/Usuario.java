package Usuario;

public abstract class Usuario {    
    private String NIF;

    public Usuario(String NIF) {
        this.NIF = NIF;
    }

    public String getNIF() {
        return NIF;
    }

    public void setNIF(String NIF) {
        this.NIF = NIF;
    }
}
