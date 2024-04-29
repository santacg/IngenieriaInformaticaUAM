package GUI.controlador;

import GUI.modelo.expofy.*;
import GUI.vistas.ClientePrincipal;
import GUI.vistas.Ventana;

public class ControladorCliente {

    private Ventana frame;
    private ClientePrincipal vista;
    private Expofy expofy;
    private ClienteRegistrado cliente;

    public ControladorCliente(Ventana frame, Expofy expofy, ClienteRegistrado cliente){
        this.frame = frame;
        this.cliente = cliente;
        this.expofy = expofy;
        this.vista = frame.getVistaClientePrincipal();

        mostrarExposiciones();
    }

    public void mostrarExposiciones(){
        vista.addTablaExposiciones(expofy);
    }
}