package gui.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

import javax.swing.JOptionPane;

import gui.modelo.centroExposicion.*;
import gui.modelo.expofy.*;
import gui.modelo.exposicion.Exposicion;
import gui.modelo.exposicion.TipoExpo;
import gui.modelo.obra.Obra;
import gui.vistas.ClientePrincipal;
import gui.vistas.Ventana;

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
    private ArrayList<Sorteo> sorteos;

    /**
     * Constructor de la clase ControladorCliente.
     * 
     * @param frame   Ventana principal de la aplicación.
     * @param expofy  Instancia de la aplicación.
     * @param cliente Cliente registrado.
     */
    public ControladorCliente(Ventana frame, Expofy expofy, ClienteRegistrado cliente) {
        this.frame = frame;
        this.frame.setCartaClientePrincipal();
        this.cliente = cliente;
        this.expofy = expofy;
        this.sorteos = new ArrayList<>();
        this.vista = frame.getVistaClientePrincipal();

        mostrarExposiciones();
        mostrarPerfil();
        mostrarNotificaciones();
        mostrarSorteos();
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
        if (vista.getTablaExposiciones() == null) {
            vista.addTablaExposiciones(data);
        } else {
            vista.actualizarTablaExposiciones(data);
        }

    }

    /**
     * Método que muestra las exposiciones filtradas por fecha en la vista.
     */
    public void filtrarPorFecha(LocalDate fechaInicio, LocalDate fechaFin) {
        ArrayList<Object[]> data = new ArrayList<>();
        for (CentroExposicion centro : expofy.getCentrosExposicion()) {
            for (Exposicion exposicion : centro.getExposiciones()) {
                if ((exposicion.getFechaInicio().isAfter(fechaInicio)
                        || exposicion.getFechaInicio().isEqual(fechaInicio))
                        && (exposicion.getFechaFin().isBefore(fechaFin)
                                || exposicion.getFechaFin().isEqual(fechaFin))) {

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
        }
        vista.actualizarTablaExposiciones(data);
    }

    /**
     * Método que muestra las exposiciones filtradas por temporalidad en la vista.
     */
    public void filtrarPorTemp(TipoExpo tipo) {
        ArrayList<Object[]> data = new ArrayList<>();
        for (CentroExposicion centro : expofy.getCentrosExposicion()) {
            for (Exposicion exposicion : centro.getExposiciones()) {
                if ((exposicion.getTipo().equals(tipo))) {
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
        }
        vista.actualizarTablaExposiciones(data);
    }

    /**
     * Método que muestra las exposiciones filtradas por tipo de obra en la vista.
     */
    public void filtrarPorTipoObra(String tipoObra) {
        ArrayList<Object[]> data = new ArrayList<>();
        for (CentroExposicion centro : expofy.getCentrosExposicion()) {
            for (Exposicion exposicion : centro.getExposiciones()) {
                for (Obra obra : exposicion.getObras()) {
                    if (obra.getTipoObra().equalsIgnoreCase(tipoObra)) {
                        data.add(new Object[] {
                                exposicion.getNombre(),
                                exposicion.getDescripcion(),
                                exposicion.getFechaInicio(),
                                exposicion.getFechaFin(),
                                exposicion.getPrecio(),
                                centro.getNombre(),
                                centro.getLocalizacion()
                        });
                        break; // Solo necesitamos agregar la exposición una vez, no importa cuántas obras
                               // coincidan
                    }
                }
            }
        }
        vista.actualizarTablaExposiciones(data);
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
     * Método que muestra los sorteos en la vista.
     */
    public void mostrarSorteos() {
        ArrayList<Object[]> data = new ArrayList<>();
        for (CentroExposicion centro : expofy.getCentrosExposicion()) {
            for (Sorteo sorteo : centro.getSorteos()) {
                Exposicion exposicion = sorteo.getExposicion();
                data.add(new Object[] {
                        sorteo.getFechaSorteo(),
                        exposicion.getNombre(),
                        exposicion.getDescripcion(),
                        exposicion.getFechaInicio(),
                        exposicion.getFechaFin(),
                        centro.getNombre(),
                        centro.getLocalizacion()
                });
                sorteos.add(sorteo);
            }
        }
        vista.addTablaSorteos(data);
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
                                "Error", JOptionPane.ERROR_MESSAGE);
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
            vista.removeControlador(comprarListener, actualizarDatosListener, cerrarSesionListener,
                    inscribirseListener, filtroFechaListener, filtroTempListener, filtroTipoObraListener,
                    eliminarFiltrosListener);
            vista.removeAll();
            JOptionPane.showMessageDialog(frame, "Se ha cerrado la sesión.");
            frame.mostrarPanel(frame.getPanelPrincipal());
        }
    };

    /**
     * Método que devuelve el ActionListener para cerrar la sesión del cliente.
     * 
     * @return ActionListener para cerrar la sesión del cliente.
     */
    public ActionListener getInscribirse() {
        return inscribirseListener;
    }

    /**
     * ActionListener para cerrar la sesión del cliente.
     */
    private ActionListener inscribirseListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            int selectedRow = vista.getTablaSorteos().getSelectedRow();
            if (selectedRow >= 0) {
                Sorteo sorteo = sorteos.get(selectedRow);
                vista.getTablaExposiciones().clearSelection();
                if (cliente.getSancionado()) {
                    JOptionPane.showMessageDialog(frame,
                            "Su cuenta está sancionada para la participación en sorteos hasta la fecha."
                                    + cliente.getSancionadoHasta());
                    return;
                }
                if (sorteo.clienteInscristo(cliente.getNIF())) {
                    JOptionPane.showMessageDialog(frame,
                            "Usted ya se encuentra inscrito en este sorteo.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Integer[] opciones = { 1, 2 };
                Integer nSeleccionado = (Integer) JOptionPane.showInputDialog(frame,
                        "Selecciona el numero de entradas para participar:",
                        "Número de entradas",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        opciones,
                        opciones[0]);
                if (nSeleccionado == null) {
                    return;
                }
                cliente.inscribirse(sorteo, nSeleccionado);
                JOptionPane.showMessageDialog(frame, "Usted se ha inscrito al sorteo");
            } else {
                JOptionPane.showMessageDialog(frame, "Por favor, selecciona un sorteo.");
            }
        }
    };

    /**
     * Método que devuelve el ActionListener para el filtrado por fecha.
     * 
     * @return ActionListener para el filtrado por fecha.
     */
    public ActionListener getFiltroFechaListener() {
        return filtroFechaListener;
    }

    /**
     * ActionListener para el filtrado por fecha.
     */
    private ActionListener filtroFechaListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            LocalDate fechaInicio = null;
            LocalDate fechaFin = null;

            String fechaInicioStr = JOptionPane.showInputDialog(frame,
                    "Introduce la fecha de inicio (yyyy-mm-dd):",
                    "Filtro por fecha",
                    JOptionPane.QUESTION_MESSAGE);

            if (fechaInicioStr.isEmpty()) {
                JOptionPane.showMessageDialog(frame,
                        "Fecha de inicio no válida.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                fechaInicio = LocalDate.parse(fechaInicioStr);
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(frame,
                        "Fecha de inicio no válida. Usa el formato yyyy-mm-dd.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

            String fechaFinStr = JOptionPane.showInputDialog(frame,
                    "Introduce la fecha de fin (yyyy-mm-dd):",
                    "Filtro por fecha",
                    JOptionPane.QUESTION_MESSAGE);

            if (fechaFinStr.isEmpty()) {
                JOptionPane.showMessageDialog(frame,
                        "Fecha de fin no válida.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                fechaFin = LocalDate.parse(fechaFinStr);
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(frame,
                        "Fecha de fin no válida. Usa el formato yyyy-mm-dd.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

            JOptionPane.showMessageDialog(frame,
                    "Filtrar desde " + fechaInicio + " hasta " + fechaFin,
                    "Información de Filtro",
                    JOptionPane.INFORMATION_MESSAGE);
            filtrarPorFecha(fechaInicio, fechaFin);
        }

    };

    /**
     * Método que devuelve el ActionListener para el filtrado por fecha.
     * 
     * @return ActionListener para el filtrado por fecha.
     */
    public ActionListener getFiltroTempListener() {
        return filtroTempListener;
    }

    /**
     * ActionListener para el filtrado por fecha.
     */
    private ActionListener filtroTempListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            TipoExpo[] opciones = TipoExpo.values();
            TipoExpo tipoSeleccionado = (TipoExpo) JOptionPane.showInputDialog(frame,
                    "Selecciona el tipo de exposición:",
                    "Filtro por tipo",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opciones,
                    opciones[0]);

            if (tipoSeleccionado != null) {
                filtrarPorTemp(tipoSeleccionado);
            } else {
                return;
            }
        }

    };

    /**
     * Método que devuelve el ActionListener para el filtrado por tipo de obra.
     * 
     * @return ActionListener para el filtrado por tipo de obra.
     */
    public ActionListener getFiltroTipoObraListener() {
        return filtroTipoObraListener;
    }

    /**
     * ActionListener para el filtrado por tipo de obra.
     */
    private ActionListener filtroTipoObraListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String[] opciones = { "Audiovisual", "Cuadro", "Escultura", "Fotografía" };
            String tipoObraSeleccionado = (String) JOptionPane.showInputDialog(frame,
                    "Selecciona el tipo de obra:",
                    "Filtro por tipo de obra",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opciones,
                    opciones[0]);

            if (tipoObraSeleccionado != null && !tipoObraSeleccionado.trim().isEmpty()) {
                filtrarPorTipoObra(tipoObraSeleccionado);
            } else {
                return;
            }
        }
    };

    /**
     * Método que devuelve el ActionListener para eliminar el filtro actual.
     * 
     * @return ActionListener para eliminar el filtro actual.
     */
    public ActionListener getEliminarFiltrosListener() {
        return eliminarFiltrosListener;
    }

    /**
     * ActionListener para eliminar el filtro actual.
     */
    private ActionListener eliminarFiltrosListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(frame,
                    "Se han eliminado los filtros aplicados anteriormente",
                    "Información de Filtro",
                    JOptionPane.INFORMATION_MESSAGE);
            mostrarExposiciones();
        }
    };
}