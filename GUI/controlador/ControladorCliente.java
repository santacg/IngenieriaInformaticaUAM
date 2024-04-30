package GUI.controlador;

import GUI.modelo.centroExposicion.CentroExposicion;
import GUI.modelo.expofy.*;
import GUI.modelo.exposicion.Exposicion;
import GUI.vistas.ClientePrincipal;
import GUI.vistas.Ventana;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;

/**
 * Clase ControladorCliente.
 * Este controlador se encarga de gestionar la vista del cliente registrado.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public class ControladorCliente {

    private Ventana frame;
    private ClientePrincipal vista;
    private Expofy expofy;
    private ClienteRegistrado cliente;

    /**
     * Constructor de la clase ControladorCliente.
     * 
     * @param frame Ventana principal de la aplicación.
     * @param expofy Instancia de la aplicación.
     * @param cliente Cliente registrado.
     */
    public ControladorCliente(Ventana frame, Expofy expofy, ClienteRegistrado cliente) {
        this.frame = frame;
        this.cliente = cliente;
        this.expofy = expofy;
        this.vista = frame.getVistaClientePrincipal();

        mostrarExposiciones();
        mostrarPerfil();
        mostrarNotificaciones();
    }

    /**
     * Método que muestra las exposiciones en la vista.
     */
    public void mostrarExposiciones() {
        ArrayList<Object[]> data = new ArrayList<>();
        for (CentroExposicion centro : expofy.getCentrosExposicion()) {
            for (Exposicion exposicion : centro.getExposiciones()) {
                data.add(new Object[] {
                        exposicion.getNombre(),
                        exposicion.getDescripcion(),
                        exposicion.getFechaInicio(),
                        exposicion.getFechaFin(),
                        exposicion.getPrecio(),
                        centro.getNombre(),
                        centro.getLocalizacion()
                });
            }
        }
        vista.addTablaExposiciones(data);
    }

    /**
     * Método que muestra las notificaciones en la vista.
     */
    public void mostrarNotificaciones() {
        ArrayList<Object[]> data = new ArrayList<>();
        for (Notificacion notificacion : cliente.getNotificaciones()) {
            data.add(new Object[] {
                    notificacion.getFecha(),
                    notificacion.getMensaje()
            });
        }
        vista.addTablaNotificaciones(data);
    }

    /**
     * Método que muestra el perfil del cliente en la vista.
     */
    public void mostrarPerfil() {
        vista.addPerfil(cliente.getNIF(), cliente.getContrasenia(), cliente.getPublicidad());
    }   

    /**
     * Método que devuelve el ActionListener para comprar una entrada.
     * 
     * @return ActionListener para comprar una entrada.
     */
    public ActionListener getComprarListener() {
        return comprarListener;
    }

    /**
     * Método que devuelve el ActionListener para actualizar los datos del cliente.
     * 
     * @return ActionListener para actualizar los datos del cliente.
     */
    public ActionListener getActualizarDatos() {
        return actualizarDatosListener;
    }

    /**
     * Método que devuelve el ActionListener para cerrar la sesión del cliente.
     * 
     * @return ActionListener para cerrar la sesión del cliente.
     */
    public ActionListener getCerrarSesion() {
        return cerrarSesionListener;
    }

    /**
     * ActionListener para comprar una entrada.
     */
    private ActionListener comprarListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            int selectedRow = vista.getTablaExposiciones().getSelectedRow();
            if (selectedRow >= 0) {
                vista.getTablaExposiciones().clearSelection();
                String nombreExposicion = (String) vista.getTablaExposiciones().getValueAt(selectedRow, 0);
                 JOptionPane.showMessageDialog(frame,
                        "Rellene el siguiente formulario para la compra de entradas para la exposición: "
                                + nombreExposicion);
                Exposicion exposicion = expofy.getExposicionPorNombre(nombreExposicion);
                ControladorCompraFormulario controladorCompraFormulario = new ControladorCompraFormulario(vista, expofy,
                        exposicion, cliente);
                vista.setCompraFormularioControlador(controladorCompraFormulario);
                
            } else {
                JOptionPane.showMessageDialog(frame, "Por favor, selecciona una exposición.");
            }
        }
    };

    /**
     * ActionListener para actualizar los datos del cliente.
     */
    private ActionListener actualizarDatosListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (vista.getCheckBoxPublicidad().isSelected() && cliente.getPublicidad() == false) {
                cliente.setPublicidad(true);
                JOptionPane.showMessageDialog(frame, "Se ha ajustado su perfil para recibir publicidad.");
            } else if ((!vista.getCheckBoxPublicidad().isSelected() && cliente.getPublicidad() == true)) {
                cliente.setPublicidad(false);
                JOptionPane.showMessageDialog(frame, "Se ha ajustado su perfil para no  recibir publicidad.");
            }
            String contrasena = vista.getFieldContrasena();
            String contrasenaCofirmada = vista.getFieldContrasenaConfirmar();
            if (!contrasena.equals("") && !contrasenaCofirmada.equals("")) {
                if (contrasena.equals(contrasenaCofirmada)) {
                    if (contrasena.equals(cliente.getContrasenia())) {
                        JOptionPane.showMessageDialog(frame,
                                "Las contraseña a la que se intenta cambiar es ya actualmente la contraseña asociada a esta cuenta.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    cliente.setContrasenia(contrasena);
                    JOptionPane.showMessageDialog(frame, "Se ha cambiado con éxito su contraseña.");
                } else {
                    JOptionPane.showMessageDialog(frame, "Las contraseñas no coinciden.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    };

    /**
     * ActionListener para cerrar la sesión del cliente.
     */
    private ActionListener cerrarSesionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            expofy.logOut(cliente);
            JOptionPane.showMessageDialog(frame, "Se ha cerrado la sesión.");
            frame.mostrarPanel(frame.getPanelPrincipal());
        }
    };
}