package gui.vistas;

import java.awt.*;
import java.awt.event.ActionListener;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.*;

import gui.modelo.centroExposicion.CentroExposicion;
import gui.modelo.centroExposicion.TipoActividad;
import gui.modelo.sala.Sala;

public class ActividadFormulario extends JDialog {
    private JTextField nombreActividad;
    private TipoActividad tipoActividad;
    private Map<String, TipoActividad> tipoActividadMap;
    private JTextField descripcion;
    private JComboBox<String> salas = new JComboBox<>();
    private JTextField fechaActividad;
    private JTextField maxParticipantes;
    private JTextField horaActividad;
    private JButton aceptarBtn;
    private JButton cancelarBtn;

    /**
     * Constructor de la clase SalaFormulario
     * 
     * @param accion String que indica la acción a realizar
     */
    public ActividadFormulario() {
        setSize(600, 700);
        setLocationRelativeTo(null); // Centrar el formulario
        setModal(true); // Hacer el diálogo modal para bloquear otras ventanas hasta que se cierre

        tipoActividadMap = new HashMap<>();
        tipoActividadMap.put("Conferencia", TipoActividad.CONFERENCIA);
        tipoActividadMap.put("Mesa redonda", TipoActividad.MESA_REDONDA);
        tipoActividadMap.put("Proyección", TipoActividad.PROYECCION);
        tipoActividadMap.put("Actuación en vivo", TipoActividad.ACTUACION_EN_VIVO);
        tipoActividadMap.put("Visita guiada", TipoActividad.VISITA_GUIADA);
        tipoActividadMap.put("Formativa", TipoActividad.FORMATIVA);
        tipoActividadMap.put("Otros", TipoActividad.OTROS);

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));

        JPanel panelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);

        this.tipoActividad = formularioTipoActividad();
        if (tipoActividad == null) {
            dispose();
            return;
        }

        constraints.gridy = 0;

        addCampo("Nombre de la actividad:", this.nombreActividad = new JTextField(20), panelFormulario, constraints, 0);
        addCampo("Descripción:", this.descripcion = new JTextField(20), panelFormulario, constraints, 1);
        addCampo("Fecha de la actividad (yyyy-mm-dd):", this.fechaActividad = new JTextField(20), panelFormulario,
                constraints, 2);
        addCampo("Hora de la actividad (hh:mm):", this.horaActividad = new JTextField(20), panelFormulario, constraints,
                3);
        addCampo("Número máximo de participantes:", this.maxParticipantes = new JTextField(20), panelFormulario,
                constraints, 4);
        addCampo("Elige una sala:", salas = new JComboBox<>(), panelFormulario, constraints, 5);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addBotones(buttonPanel);
        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);
        panelPrincipal.add(buttonPanel, BorderLayout.SOUTH);
        add(panelPrincipal);
    }

    /**
     * Método que crea un formulario para seleccionar el tipo de actividad
     * 
     * @return TipoActividad con el tipo de activad seleccionada
     */
    public TipoActividad formularioTipoActividad() {
        String[] opcionesActividad = tipoActividadMap.keySet().toArray(new String[0]);
        JComboBox<String> comboActividad = new JComboBox<>(opcionesActividad);

        int seleccion = JOptionPane.showConfirmDialog(null, comboActividad, "Seleccione el tipo de actividad",
                JOptionPane.OK_CANCEL_OPTION);

        if (seleccion == JOptionPane.OK_OPTION) {
            String seleccionado = (String) comboActividad.getSelectedItem();
            return tipoActividadMap.get(seleccionado);
        }

        return null;
    }

    /**
     * Método que muestra las exposiciones de un centro de exposición
     * 
     * @param centroExposicion CentroExposicion del que se mostrarán las
     *                         exposiciones disponibles
     */
    public void mostrarSalas(CentroExposicion centroExposicion) {
        Set<Sala> salasSet = centroExposicion.getSalas();

        for (Sala sala : salasSet) {
            salas.addItem(sala.getNombre());
        }

        if (salasSet.size() == 0) {
            salas.addItem("No hay salas disponibles");
        }
    }

    /**
     * Método que añade un campo al formulario
     * 
     * @param label       String que indica el nombre del campo
     * @param comp        Component que se añadirá al formulario
     * @param panel       JPanel en el que se añadirá el campo
     * @param constraints GridBagConstraints que definen la posición del campo
     * @param gridy       int que indica la fila en la que se añadirá el campo
     */
    private void addCampo(String label, Component comp, JPanel panel, GridBagConstraints constraints, int gridy) {
        constraints.gridx = 0;
        panel.add(new JLabel(label), constraints);
        constraints.gridx++;
        panel.add(comp, constraints);
        constraints.gridy++;
    }

    /**
     * Método que añade los botones de aceptar y cancelar al formulario
     * 
     * @param panel JPanel en el que se añadirán los botones
     */
    private void addBotones(JPanel buttonPanel) {
        this.aceptarBtn = new JButton("Aceptar");
        this.cancelarBtn = new JButton("Cancelar");

        buttonPanel.add(aceptarBtn);
        buttonPanel.add(cancelarBtn);
    }

    /**
     * Método que devuelve el tipo de Actividad seleccionado en el formulario
     * 
     * @return TipoActividad con el tipo de Actividad seleccionado
     */
    public TipoActividad getTipoActividad() {
        return this.tipoActividad;
    }

    /**
     * Método que devuelve la fecha de Actividad introducida en el formulario
     * 
     * @return LocalDate con la fecha introducida
     */
    public LocalDate getFechaActividad() {
        if (this.fechaActividad.getText().equals("")) {
            return null;
        }

        try {
            return LocalDate.parse(this.fechaActividad.getText());
        } catch (DateTimeException e) {
            return null;
        }
    }

    /**
     * Fecha que devuelve el número de participantes máximmo introducido en el
     * formulario
     * 
     * @return Integer con el número de participantes máximo introducido
     */
    public Integer getActividadMaxParticipantes() {
        try {
            return Integer.parseInt(maxParticipantes.getText());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Método que devuelve la hora introducida en el formulario
     * 
     * @return LocalTime con la hora introducida
     */
    public LocalTime getHoraActividad() {
        if (this.horaActividad.getText().equals("")) {
            return null;
        }

        try {
            return LocalTime.parse(this.horaActividad.getText());
        } catch (DateTimeException e) {
            return null;
        }
    }

    /**
     * Método que devuelve el nombre de la actividad
     * 
     * @return String con el nombre de la actividad
     */
    public String getNombreActividad() {
        return nombreActividad.getText();
    }

    /**
     * Método que devuelve la descripción de la actividad
     * 
     * @return String con la descripción de la actividad
     */
    public String getDescripcion() {
        return descripcion.getText();
    }

    /**
     * Método que devuelve la sala seleccionada en el formulario
     * 
     * @return String con la sala seleccionada
     */
    public String getSelectedSala() {
        return (String) salas.getSelectedItem();
    }

    /**
     * Método que establece los controladores de los botones del formulario
     * 
     * @param cAceptar  ActionListener que se añadirá al botón de aceptar
     * @param cCancelar ActionListener que se añadirá al botón de cancelar
     */
    public void setControlador(ActionListener cAceptar, ActionListener cCancelar) {
        if (aceptarBtn == null || cancelarBtn == null) {
            return;
        }
        aceptarBtn.addActionListener(cAceptar);
        cancelarBtn.addActionListener(cCancelar);
        setVisible(true);
    }

}
