package gui.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import gui.modelo.centroExposicion.CentroExposicion;
import gui.modelo.expofy.Expofy;
import gui.modelo.exposicion.EstadoExposicion;
import gui.modelo.exposicion.Exposicion;
import gui.modelo.exposicion.SalaExposicion;
import gui.modelo.obra.Estado;
import gui.modelo.obra.Obra;
import gui.modelo.sala.Sala;
import gui.modelo.utils.LectorCSVObras;
import gui.vistas.GestorPrincipal;
import gui.vistas.ModeloTablaObras;
import gui.vistas.Ventana;

/**
 * Clase ControladorGestor.
 * Implementa el controlador de la vista del gestor.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public class ControladorGestor {

    private Ventana frame;
    private GestorPrincipal vista;
    private CentroExposicion centro;

    /**
     * Constructor de la clase ControladorGestor.
     * 
     * @param frame  Ventana principal de la aplicación.
     * @param centro Centro de exposición.
     */
    public ControladorGestor(Ventana frame, CentroExposicion centro) {
        this.frame = frame;
        this.frame.setCartaGestorPrincipal();
        this.centro = centro;
        this.vista = frame.getVistaGestorPrincipal();

        mostrarExposiciones();
        mostrarSalasExposicion();
        mostrarSalas();
        mostrarObras();
        mostrarEmpleados();
        mostrarSorteos();
        mostrarDescuentos();
        mostrarActividades();
        mostrarInfo();
    }

    /**
     * Método que muestra la vista del gestor.
     */
    public void mostrarExposiciones() {
        vista.addPanelExposiciones(centro);
    }

    /**
     * Método que muestra la vista de las salas de exposición.
     */
    public void mostrarSalasExposicion() {
        vista.addPanelSalasExposicion(centro);
    }

    /**
     * Método que muestra la vista de las salas.
     */
    public void mostrarSalas() {
        vista.addPanelSalas(centro);
    }

    /**
     * Método que muestra la vista de las obras.
     */
    public void mostrarObras() {
        vista.addPanelObras(centro);
    }

    /**
     * Metodo que muestra la vista de empleados
     */
    public void mostrarEmpleados() {
        vista.addPanelEmpleados(centro);
    }

    /**
     * Método que muestra la vista de los sorteos.
     */
    public void mostrarSorteos() {
        vista.addPanelSorteos(centro);
    }

    /**
     * Método que muestra la vista de los descuentos.
     */
    public void mostrarDescuentos() {
        vista.addPanelDescuentos(centro);
    }

    /**
     * Método que muestra la vista de las actividades.
     */
    public void mostrarActividades() {
        vista.addPanelActividades(centro);
    }

    /**
     * Método que muestra la información del centro de exposición.
     */
    public void mostrarInfo() {
        vista.actualizarInfo(centro);
    }

    /**
     * Método que actualiza la vista de las exposiciones segun la ejecucion que se
     * haya realizado.
     */
    private ActionListener exposicionEjecutarListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String accion = vista.getExposicionAccionSeleccionada();
            JTable tabla = vista.getTablaExposiciones();

            int selectedRow = tabla.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(frame, "Debes seleccionar una exposición.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            for (Exposicion exposicion : centro.getExposiciones()) {
                if (exposicion.getNombre().equals(tabla.getValueAt(selectedRow, 0))) {
                    ControladorExposicionFormulario controladorExposicionFormulario = new ControladorExposicionFormulario(
                            vista, centro, exposicion, accion);
                    vista.setControladorExposicionFormulario(controladorExposicionFormulario);
                    break;
                }
            }

        }
    };

    /**
     * Método que actualiza la vista de las obras segun la ejecucion que se haya
     * realizado.
     */
    private ActionListener obraEjecutarListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String accion = vista.getObraAccionSeleccionada();
            JTable tabla = vista.getTablaObras();
            ModeloTablaObras modelo = (ModeloTablaObras) tabla.getModel();
            List<Obra> obras = new ArrayList<>();

            for (int i = 0; i < modelo.getRowCount(); i++) {
                Boolean seleccionado = (Boolean) modelo.getValueAt(i, 0);
                if (seleccionado) {
                    String nombreObra = (String) modelo.getValueAt(i, 1);
                    for (Obra obra : centro.getObras()) {
                        if (obra.getNombre().equals(nombreObra)) {
                            obras.add(obra);
                            switch (accion) {
                                case "Retirar Obra":

                                    if (obra.retirarObra() == false) {
                                        JOptionPane.showMessageDialog(frame,
                                                "No se puede retirar la obra " + nombreObra);
                                        continue;
                                    }

                                    modelo.setValueAt(Estado.RETIRADA, i, 8);
                                    JOptionPane.showMessageDialog(frame, "Obra retirada correctamente.");
                                    break;
                                case "Almacenar Obra":

                                    if (obra.almacenarObra() == false) {
                                        JOptionPane.showMessageDialog(frame,
                                                "No se puede almacenar la obra " + nombreObra);
                                        continue;
                                    }

                                    modelo.setValueAt(Estado.ALMACENADA, i, 8);
                                    JOptionPane.showMessageDialog(frame, "Obra almacenada correctamente.");
                                    break;
                                case "Exponer Obra":

                                    if (obra.exponerObra() == false) {
                                        JOptionPane.showMessageDialog(frame,
                                                "No se puede exponer la obra " + nombreObra);
                                        continue;
                                    }

                                    modelo.setValueAt(Estado.EXPUESTA, i, 8);
                                    JOptionPane.showMessageDialog(frame, "Obra expuesta correctamente.");
                                    break;
                                case "Asignar Obra a Sala":

                                    Map<String, Set<SalaExposicion>> exposicionesYSalas = new HashMap<>();
                                    for (Exposicion exposicion : centro.getExposiciones()) {
                                        if (exposicion.getEstado().equals(EstadoExposicion.EN_CREACION)) {
                                            exposicionesYSalas.put(exposicion.getNombre(), exposicion.getSalas());
                                        }
                                    }

                                    if (exposicionesYSalas.isEmpty()) {
                                        JOptionPane.showMessageDialog(frame, "No hay exposiciones en creación.");
                                        break;
                                    }

                                    String exposicionSeleccionada = (String) JOptionPane.showInputDialog(frame,
                                            "Seleccione la exposición donde quiere exponer la obra " + nombreObra,
                                            "Exponer Obra",
                                            JOptionPane.QUESTION_MESSAGE,
                                            null,
                                            exposicionesYSalas.keySet().toArray(),
                                            exposicionesYSalas.keySet().toArray()[0]);

                                    if (exposicionSeleccionada == null) {
                                        JOptionPane.showMessageDialog(frame, "No se seleccionó ninguna exposición.");
                                        continue;
                                    }

                                    Set<SalaExposicion> salas = exposicionesYSalas.get(exposicionSeleccionada);
                                    List<String> nombresSalas = new ArrayList<>();
                                    for (SalaExposicion salaExposicion : salas) {
                                        Sala salaPrincipal = salaExposicion.getSala();
                                        nombresSalas.add(salaPrincipal.getNombre());
                                        for (Sala subSala : salaPrincipal.getSubSalas()) {
                                            nombresSalas.add(subSala.getNombre());
                                        }
                                    }

                                    String salaSeleccionadaNombre = (String) JOptionPane.showInputDialog(
                                            frame,
                                            "Seleccione la sala donde quiere exponer la obra " + nombreObra,
                                            "Exponer Obra",
                                            JOptionPane.QUESTION_MESSAGE,
                                            null,
                                            nombresSalas.toArray(),
                                            null);

                                    if (salaSeleccionadaNombre == null) {
                                        JOptionPane.showMessageDialog(frame, "No se seleccionó ninguna sala.");
                                        continue;
                                    }

                                    SalaExposicion salaSeleccionada = null;
                                    for (SalaExposicion salaExpo : salas) {
                                        if (salaExpo.getSala().getNombre().equals(salaSeleccionadaNombre)) {
                                            salaSeleccionada = salaExpo;
                                            break;
                                        }
                                        for (Sala subSala : salaExpo.getSala().getSubSalas()) {
                                            if (subSala.getNombre().equals(salaSeleccionadaNombre)) {
                                                salaSeleccionada = new SalaExposicion(subSala);
                                                break;
                                            }
                                        }
                                        if (salaSeleccionada != null) {
                                            break;
                                        }
                                    }

                                    if (salaSeleccionada == null || salaSeleccionada.addObra(obra) == false) {
                                        JOptionPane.showMessageDialog(frame,
                                                "No se puede añadir la obra " + nombreObra);
                                        continue;
                                    }

                                    JOptionPane.showMessageDialog(frame,
                                            "Obra " + nombreObra + " expuesta correctamente en "
                                                    + exposicionSeleccionada + " - " + salaSeleccionadaNombre);

                                    vista.actualizarTablaSalasExposicion(centro);

                                    break;
                                case "Eliminar Obra de Sala":

                                    List<String> nombresSalasExposicion = new ArrayList<>();

                                    for (Exposicion exposicion : centro.getExposiciones()) {
                                        if (exposicion.getEstado().equals(EstadoExposicion.EN_CREACION)) {
                                            for (SalaExposicion salaExpo : exposicion.getSalas()) {
                                                if (salaExpo.getObras().contains(obra)) {
                                                    nombresSalasExposicion.add(exposicion.getNombre() + " - "
                                                            + salaExpo.getSala().getNombre());
                                                }
                                            }
                                        }
                                    }

                                    if (nombresSalasExposicion.isEmpty()) {
                                        JOptionPane.showMessageDialog(frame,
                                                "La obra no está en ninguna sala de exposiciones de exposiciones en creación.");
                                        break;
                                    }

                                    String salaExposicionSeleccionada = (String) JOptionPane.showInputDialog(frame,
                                            "Seleccione la sala de exposición de la que quiere eliminar la obra "
                                                    + nombreObra,
                                            "Eliminar Obra de Sala",
                                            JOptionPane.QUESTION_MESSAGE,
                                            null,
                                            nombresSalasExposicion.toArray(),
                                            nombresSalasExposicion.toArray()[0]);

                                    if (salaExposicionSeleccionada == null) {
                                        JOptionPane.showMessageDialog(frame, "No se seleccionó ninguna sala.");
                                        continue;
                                    }

                                    String[] partes = salaExposicionSeleccionada.split(" - ");
                                    String nombreExposicion = partes[0];
                                    String nombreSala = partes[1];

                                    Exposicion exposicion = centro.getExposicionPorNombre(nombreExposicion);

                                    for (SalaExposicion salaExpo : exposicion.getSalas()) {
                                        if (salaExpo.getSala().getNombre().equals(nombreSala)) {
                                            salaExpo.removeObra(obra);
                                            break;
                                        }
                                    }

                                    JOptionPane.showMessageDialog(frame,
                                            "Obra " + nombreObra + " eliminada correctamente de " + nombreExposicion
                                                    + " - " + nombreSala);

                                    vista.actualizarTablaSalasExposicion(centro);

                                    break;

                                case "Prestar Obra":

                                    Expofy expofy = Expofy.getInstance();
                                    Set<CentroExposicion> centros = expofy.getCentrosExposicion();
                                    centros.remove(centro);
                                    List<String> nombresCentros = new ArrayList<>();

                                    if (centros.isEmpty()) {
                                        JOptionPane.showMessageDialog(frame, "No hay centros a los que prestar la obra.");
                                        continue;
                                    }

                                    for (CentroExposicion centro : centros) {
                                        nombresCentros.add(centro.getNombre());
                                    }

                                    String[] opcionesCentros = nombresCentros.toArray(new String[0]);

                                    String nombreCentroSeleccionado = (String) JOptionPane.showInputDialog(frame,
                                            "Seleccione el centro al que se prestará la obra " + nombreObra,
                                            "Prestar Obra",
                                            JOptionPane.QUESTION_MESSAGE,
                                            null,
                                            opcionesCentros,
                                            opcionesCentros[0]);

                                    if (nombreCentroSeleccionado == null) {
                                        JOptionPane.showMessageDialog(frame, "No se ha seleccionado ningún centro.");
                                        continue;
                                    }

                                    CentroExposicion centroDestino = null;
                                    for (CentroExposicion centro : centros) {
                                        if (centro.getNombre().equals(nombreCentroSeleccionado)) {
                                            centroDestino = centro;
                                            break;
                                        }
                                    }

                                    if (centroDestino == null) {
                                        JOptionPane.showMessageDialog(frame, "Centro no encontrado.");
                                        continue;
                                    }

                                    if (obra.prestarObra() == false) {
                                        JOptionPane.showMessageDialog(frame,
                                                "No se puede prestar la obra " + nombreObra);
                                        continue;
                                    }

                                    for (Exposicion exposiciones : centro.getExposiciones()) {
                                        for (SalaExposicion salaExpo : exposiciones.getSalas()) {
                                            if (salaExpo.getObras().contains(obra)) {
                                                salaExpo.removeObra(obra);
                                                break;
                                            }
                                        }
                                    }

                                    vista.actualizarTablaSalasExposicion(centroDestino);

                                    centroDestino.addObra(obra);
                                    modelo.setValueAt(Estado.PRESTADA, i, 8);
                                    JOptionPane.showMessageDialog(frame,
                                            "Obra prestada correctamente al centro " + nombreCentroSeleccionado + ".");
                                    break;
                                case "Restaurar Obra":

                                    if (obra.restaurarObra() == false) {
                                        JOptionPane.showMessageDialog(frame,
                                                "No se puede restaurar la obra " + nombreObra);
                                        continue;
                                    }

                                    for (Exposicion exposicionPublicada : centro.getExposiciones()) {
                                        if (!exposicionPublicada.getEstado().equals(EstadoExposicion.EN_CREACION)) {
                                            for (SalaExposicion salaExpo : exposicionPublicada.getSalas()) {
                                                if (salaExpo.getObras().contains(obra)) {
                                                    salaExpo.removeObra(obra);
                                                    break;
                                                }
                                            }
                                        }
                                    }

                                    vista.actualizarTablaSalasExposicion(centro);

                                    modelo.setValueAt(Estado.RESTAURACION, i, 8);
                                    JOptionPane.showMessageDialog(frame, "Obra puesta en restauracion correctamente.");
                                    break;
                            }
                            break;
                        }
                    }
                }
            }
            vista.deseleccionarTabla();
        }
    };

    /**
     * Método que inicializa un listener para leer las obras desde un CSV.
     */
    private ActionListener obraLeerCSVListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String fileName = JOptionPane.showInputDialog(vista,
                    "Introduce el nombre del archivo CSV (no debes incluir el .csv)");
            if (LectorCSVObras.leerObras(centro, fileName) == false) {
                JOptionPane.showMessageDialog(frame, "Error al leer las obras.");
                return;
            }
            JOptionPane.showMessageDialog(frame, "Obras leídas correctamente.");
            vista.actualizarTablaObras(centro);
        }
    };

    /**
     * Método que actualiza la vista de las obras incializando un listener.
     */
    private ActionListener obraAgregarListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            ControladorObraFormulario controladorObraFormulario = new ControladorObraFormulario(vista, centro);
            vista.setControladorObraFormulario(controladorObraFormulario);
        }
    };

    /**
     * Método que inicializa un listener para ejecutar las salas.
     */
    private ActionListener salaEjecutarListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String accion = vista.getSalaAccionSeleccionada();
            ControladorSalaFormulario controladorSalaFormulario = new ControladorSalaFormulario(vista, centro, accion);
            vista.setControladorSalaFormulario(controladorSalaFormulario);
        }
    };

    /**
     * Método que inicializa un listener para agregar exposiciones.
     */
    private ActionListener exposicionAgregarListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            ControladorExposicionFormulario controladorExposicionFormulario = new ControladorExposicionFormulario(vista,
                    centro);
            vista.setControladorExposicionFormulario(controladorExposicionFormulario);
        }
    };

    /**
     * Método que inicializa un listener para agregar empleados.
     */
    private ActionListener empleadoAgregarListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            ControladorEmpleadoFormulario controladorEmpleadoFormulario = new ControladorEmpleadoFormulario(vista,
                    centro);
            vista.setControladorEmpleadoFormulario(controladorEmpleadoFormulario);
        }
    };

    /**
     * Método que inicializa un listener para configurar la contraseña de un
     * empleado.
     */
    private ActionListener empleadoConfigurarContraseniaListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String contrasenia = JOptionPane.showInputDialog(vista, "Introduce la nueva contraseña");
            centro.setContraseniaEmpleado(contrasenia);
            if (contrasenia == null || contrasenia.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No se ha actualizado la contraseña");
                return;

            }
            JOptionPane.showMessageDialog(frame, "Contraseña actualizada correctamente");
        }
    };

    /**
     * Método que inicializa un listener para agregar sorteos.
     */
    private ActionListener sorteoAgregarListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            ControladorSorteoFormulario controladorSorteoFormulario = new ControladorSorteoFormulario(vista, centro);
            vista.setControladorSorteoFormulario(controladorSorteoFormulario);
        }
    };

    /**
     * Método que inicializa un listener para agregar descuentos.
     */
    private ActionListener descuentoAgregarListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            ControladorDescuentoFormulario controladorDescuentoFormulario = new ControladorDescuentoFormulario(vista,
                    centro);
            vista.setControladorDescuentoFormulario(controladorDescuentoFormulario);
        }
    };

    /**
     * Método que inicializa un listener para agregar actividades.
     */
    private ActionListener actividadAgregarListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            ControladorActividadFormulario controladorActividadFormulario = new ControladorActividadFormulario(vista,
                    centro);
            vista.setControladorActividadFormulario(controladorActividadFormulario);
        }
    };

    private ActionListener cambiarHorasListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String stringHora = JOptionPane.showInputDialog(vista, "Introduce la nueva hora de apertura (HH:MM)");

            if (stringHora.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No se ha actualizado la hora de cierre");
            }

            try {
                LocalTime hora = LocalTime.parse(stringHora);
                centro.setHoraApertura(hora);
                JOptionPane.showMessageDialog(frame, "Hora de apertura actualizada correctamente");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Hora de apertura no válida");
            }

            stringHora = JOptionPane.showInputDialog(vista, "Introduce la nueva hora de cierre (HH:MM)");

            if (stringHora.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No se ha actualizado la hora de apertura");
            }

            try {
                LocalTime hora = LocalTime.parse(stringHora);
                centro.setHoraCierre(hora);
                JOptionPane.showMessageDialog(frame, "Hora de cierre actualizada correctamente");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Hora de cierre no válida");
            }

            vista.actualizarInfo(centro);
        }
    };

    /**
     * Método que inicializa un listener para cerrar sesion.
     */
    private ActionListener cerrarSesionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            centro.getGestor().logOut();
            vista.removeControlador(salaEjecutarListener, obraLeerCSVListener, obraEjecutarListener,
                    obraAgregarListener, exposicionEjecutarListener, exposicionAgregarListener, empleadoAgregarListener,
                    empleadoConfigurarContraseniaListener, sorteoAgregarListener, descuentoAgregarListener,
                    actividadAgregarListener,
                    cerrarSesionListener, cambiarHorasListener);
            vista.removeAll();
            JOptionPane.showMessageDialog(frame, "Se ha cerrado la sesión.");
            frame.mostrarPanel(frame.getPanelPrincipal());
        }
    };

    /**
     * Método que obtiene el listener de de ejecucion de obras.
     * 
     * @return ActionListener para ejecutar obras.
     */
    public ActionListener getObraEjecutarListener() {
        return obraEjecutarListener;
    }

    /**
     * Método que obtiene el listener de agregar obras.
     * 
     * @return ActionListener para agregar obras.
     */
    public ActionListener getObraAgregarListener() {
        return obraAgregarListener;
    }

    /**
     * Método que obtiene el listener de leer obras desde un CSV.
     * 
     * @return ActionListener para leer obras desde un CSV.
     */
    public ActionListener getObraLeerCSVListener() {
        return obraLeerCSVListener;
    }

    /**
     * Método que obtiene el listener de ejecucion de salas.
     * 
     * @retunr ActionListener para ejecutar salas
     */
    public ActionListener getSalaEjecutarListener() {
        return salaEjecutarListener;
    }

    /**
     * Método que obtiene el listener de ejecucion de exposiciones.
     * 
     * @return ActionListener para ejecutar exposiciones
     */
    public ActionListener getExposicionEjecutarListener() {
        return exposicionEjecutarListener;
    }

    /**
     * Método que obtiene el listener de agregar exposiciones.
     * 
     * @return ActionListener para agregar exposiciones
     */
    public ActionListener getExposicionAgregarListener() {
        return exposicionAgregarListener;
    }

    /**
     * Metodo que devuelve el listener de agregar empleados
     * 
     * @return ActionListener para agregar empleados
     */
    public ActionListener getEmpleadoAgregarListener() {
        return empleadoAgregarListener;
    }

    /**
     * Método que devuelve el listener de configurar contraseña de empleado.
     * 
     * @return ActionListener para configurar contraseña de empleado.
     */
    public ActionListener getEmpleadoConfigurarContraseniaListener() {
        return empleadoConfigurarContraseniaListener;
    }

    /**
     * Método que devuelve el ActionListener para agregar sorteos.
     * 
     * @return ActionListener para agregar sorteos.
     */
    public ActionListener getSorteoAgregarListener() {
        return sorteoAgregarListener;
    }

    /**
     * Método que devuelve el ActionListener para agregar descuentos.
     * 
     * @return ActionListener para agregar descuentos.
     */
    public ActionListener getDescuentoAgregarListener() {
        return descuentoAgregarListener;
    }

    /**
     * Método que devuelve el ActionListener para agregar actividades.
     * 
     * @return ActionListener para agregar actividades.
     */
    public ActionListener getActividadAgregarListener() {
        return actividadAgregarListener;
    }

    /**
     * Método que devuelve el ActionListener para cambiar las horas de apertura y
     * cierre.
     * 
     * @return ActionListener para cambiar las horas de apertura y cierre.
     */
    public ActionListener getCambiarHorasListener() {
        return cambiarHorasListener;
    }

    /**
     * Método que devuelve el ActionListener para cerrar la sesión del gestor.
     * 
     * @return ActionListener para cerrar la sesión del cliente.
     */
    public ActionListener getCerrarSesionListener() {
        return cerrarSesionListener;
    }

}
