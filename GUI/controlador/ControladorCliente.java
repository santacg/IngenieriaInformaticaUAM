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

import java.awt.event.*;
import javax.swing.event.*;
import java.awt.*;


public class ControladorCliente {

    private Ventana frame;
    private ClientePrincipal vista;
    private Expofy expofy;
    private ClienteRegistrado cliente;

    public ControladorCliente(Ventana frame, Expofy expofy, ClienteRegistrado cliente) {
        this.frame = frame;
        this.cliente = cliente;
        this.expofy = expofy;
        this.vista = frame.getVistaClientePrincipal();

        mostrarExposiciones();
        mostrarPerfil();
        mostrarNotificaciones();
    }

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

    public void mostrarPerfil() {
        vista.addPerfil(cliente.getNIF(), cliente.getContrasenia(), cliente.getPublicidad());
    }

    public ActionListener getComprarListener() {
        return comprarListener;
    }

    public ActionListener getActualizarDatos() {
        return actualizarDatosListener;
    }

    public ActionListener getCerrarSesion() {
        return cerrarSesionListener;
    }

    private ActionListener comprarListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            int selectedRow = vista.getTablaExposiciones().getSelectedRow();
            if (selectedRow >= 0) {
                String nombreExposicion = (String) vista.getTablaExposiciones().getValueAt(selectedRow, 0);
                JOptionPane.showMessageDialog(frame, "Mostrando horarios para la exposición: " + nombreExposicion);
                // exposicion = expofy.getExposicionPorNombre(nombreExposicion);
                // ControladorHorario controladorHorario = new ControladorHorario(exposicion);
                // frame.setHorarioControlador(controladorHorario);
                // vista.update();
                // frame.mostrarPanel(frame.getHorario());
            } else {
                JOptionPane.showMessageDialog(frame, "Por favor, selecciona una exposición.");
            }
        }
    };

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

    private ActionListener cerrarSesionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            expofy.logOut(cliente);
            JOptionPane.showMessageDialog(frame, "Se ha cerrado la sesión.");
            frame.mostrarPanel(frame.getPanelPrincipal());
        }
    };
}