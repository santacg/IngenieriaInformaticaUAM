package gui.modelo.utils;

public class ExcepcionMensaje extends Exception{
    private String mensaje;

    public ExcepcionMensaje(String mensaje){
        super(mensaje);
        this.mensaje = mensaje;
    }
    
    public String getMensaje(){
        return mensaje;
    }
    
}
