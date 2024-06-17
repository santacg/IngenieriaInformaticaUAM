package gui.modelo.utils;

/**
 * Clase que representa una excepción de mensaje.
 * Esta excepción se lanza cuando se produce un error en la aplicación y se necesita mostrar un mensaje de error.
 * 
 * @author Carlos Garcia Santa
 */
public class ExcepcionMensaje extends Exception{
    private String mensaje;

    /**
     * Constructor de la clase ExcepcionMensaje.
     * 
     * @param mensaje Mensaje de error.
     */
    public ExcepcionMensaje(String mensaje){
        super(mensaje);
        this.mensaje = mensaje;
    }
    
    /**
     * Método que devuelve el mensaje de error.
     * 
     * @return Mensaje de error.
     */
    public String getMensaje(){
        return mensaje;
    }
    
}
