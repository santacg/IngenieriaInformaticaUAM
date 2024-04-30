package GUI.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import GUI.modelo.centroExposicion.CentroExposicion;
import GUI.modelo.centroExposicion.Empleado;
import GUI.vistas.EmpleadoPrincipal;
import GUI.vistas.Ventana;
import GUI.modelo.expofy.Expofy;

public class ControladorEmpleado {
    private Ventana frame;
    private EmpleadoPrincipal vista;
    private Expofy expofy;
    private Empleado empleado;

    public ControladorEmpleado(Ventana frame, Expofy expofy, CentroExposicion centro, String usuario) {
        this.frame = frame;
        this.expofy = expofy;
        this.vista = frame.getVistaEmpleadoPrincipal();
        this.empleado = centro.getEmpleado(usuario);

    }

    private ActionListener cambiarClimatizacionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if(empleado.getPermisoControl()==false){
                JOptionPane.showMessageDialog(frame, "Actualmente no tienes permiso para cambiar la climatización. Solicita al gestor que modifique tu permiso.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                        return;
            }
            frame.mostrarPanel(frame.getAjustarClimatizacion());
        }
    };

    public ActionListener getClimatizacionListener() {
        return cambiarClimatizacionListener;
    }
 
    private ActionListener enviarMsjListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if(empleado.getPermisoMensajes()==false){
                JOptionPane.showMessageDialog(frame, "Actualmente no tienes permiso para enviar mensajes. Solicita al gestor que modifique tu permiso.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                        return;
                        
            }
            ControladorEnviarMensajes controladorEnviarMensajes = new ControladorEnviarMensajes(frame, expofy, empleado);
            frame.setControladorEnviarMensajes(controladorEnviarMensajes);           
            frame.mostrarPanel(frame.getEnviarMensajes());
        }
    };

    public ActionListener getEnviarMsjListener() {
        return enviarMsjListener;
    }

    private ActionListener cerrarSesionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            //expofy.logOut(empleado);
            JOptionPane.showMessageDialog(frame, "Sesión cerrada correctamente");
            frame.mostrarPanel(frame.getPanelPrincipal());
        }
    };

    public ActionListener getCerrarSesionListener() {
        return cerrarSesionListener;
    }

}
