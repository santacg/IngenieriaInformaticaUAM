package GUI.controlador;

import java.awt.event.*;

import javax.swing.JOptionPane;

import GUI.modelo.centroExposicion.CentroExposicion;
import GUI.modelo.obra.*;
import GUI.vistas.*;

public class ControladorObraFormulario {
    private ObraFormulario vista;
    private GestorPrincipal frame;
    private CentroExposicion centroExposicion;
    private ModeloTablaObras modeloTablaObras;

    public ControladorObraFormulario(GestorPrincipal frame, CentroExposicion centroExposicion) {
        this.frame = frame;
        this.vista = frame.getVistaObraFormulario();
        this.centroExposicion = centroExposicion;
    }

    private ActionListener guardarListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String tipoDeObra = vista.getTipoDeObra();

            if (vista.getObraNombre().equals("") || vista.getObraAutores().equals("") || vista.getObraAnio().equals("")
                    || vista.getObraCuantiaSeguro().equals("") || vista.getObraDescripcion().equals("")
                    || vista.getObraNumeroSeguro().equals("")) {
                JOptionPane.showMessageDialog(vista, "Debes rellenar todos los campos.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (tipoDeObra.equals("Cuadro") && vista.getCuadroTecnica().equals("")) {
                JOptionPane.showMessageDialog(vista, "Debes rellenar la técnica del cuadro.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (tipoDeObra.equals("Escultura") && (vista.getEsculturaMaterial().equals("")
                    || vista.getEsculturaProfundidad().equals(""))) {
                JOptionPane.showMessageDialog(vista, "Debes rellenar el material y la altura de la escultura.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (tipoDeObra.equals("Audiovisual") && (vista.getAudiovisualDuracion().equals("")
                    || vista.getAudiovisualIdioma().equals(""))) {
                JOptionPane.showMessageDialog(vista, "Debes rellenar la duración y el idioma del audiovisual.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (tipoDeObra.equals("Cuadro") || tipoDeObra.equals("Escultura") || tipoDeObra.equals("Fotografia")) {
                if (vista.getObraRangoTemperatura().equals("") || vista.getObraRangoHumedad().equals("")) {
                    JOptionPane.showMessageDialog(vista, "Debes rellenar el rango de temperatura y la humedad.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            Obra obra = null;

            switch (tipoDeObra) {
                case "Cuadro":
                    obra = new Cuadro(vista.getObraNombre(), Integer.parseInt(vista.getObraAnio()),
                            vista.getObraDescripcion(), vista.getObraExterna(),
                            Double.parseDouble(vista.getObraCuantiaSeguro()), vista.getObraNumeroSeguro(),
                            Double.parseDouble(vista.getObraAlto()), Double.parseDouble(vista.getObraAncho()),
                            Integer.parseInt(vista.getObraTemperaturaMax()),
                            Integer.parseInt(vista.getObraTemperaturaMin()),
                            Integer.parseInt(vista.getObraHumedadMax()), Integer.parseInt(vista.getObraHumedadMin()),
                            vista.getCuadroTecnica(), vista.getObraAutores());
                    break;
                case "Escultura":
                    obra = new Escultura(vista.getObraNombre(), Integer.parseInt(vista.getObraAnio()),
                            vista.getObraDescripcion(), vista.getObraExterna(),
                            Double.parseDouble(vista.getObraCuantiaSeguro()), vista.getObraNumeroSeguro(),
                            Double.parseDouble(vista.getObraAlto()), Double.parseDouble(vista.getObraAncho()),
                            Double.parseDouble(vista.getEsculturaProfundidad()),
                            Integer.parseInt(vista.getObraTemperaturaMax()),
                            Integer.parseInt(vista.getObraTemperaturaMin()),
                            Integer.parseInt(vista.getObraHumedadMax()), Integer.parseInt(vista.getObraHumedadMin()),
                            vista.getEsculturaMaterial(), vista.getObraAutores());
                    break;
                case "Fotografia":
                    obra = new Fotografia(vista.getObraNombre(), Integer.parseInt(vista.getObraAnio()),
                            vista.getObraDescripcion(), vista.getObraExterna(),
                            Double.parseDouble(vista.getObraCuantiaSeguro()), vista.getObraNumeroSeguro(),
                            Double.parseDouble(vista.getObraAlto()), Double.parseDouble(vista.getObraAncho()),
                            Integer.parseInt(vista.getObraTemperaturaMax()),
                            Integer.parseInt(vista.getObraTemperaturaMin()),
                            Integer.parseInt(vista.getObraHumedadMax()), Integer.parseInt(vista.getObraHumedadMin()),
                            vista.getFotografiaColor(), vista.getObraAutores());
                    break;
                case "Audiovisual":
                    obra = new Audiovisual(vista.getObraNombre(), Integer.parseInt(vista.getObraAnio()),
                            vista.getObraDescripcion(), vista.getObraExterna(),
                            Double.parseDouble(vista.getObraCuantiaSeguro()), vista.getObraNumeroSeguro(),
                            vista.getAudiovisualDuracion(), vista.getAudiovisualIdioma(), vista.getObraAutores());
                    break;
            }

            if (obra == null) {
                JOptionPane.showMessageDialog(vista, "Error al crear la obra.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (centroExposicion.addObra(obra) == false) {
                JOptionPane.showMessageDialog(vista, "Error al añadir la obra.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Object[] obraData = new Object[] { false, obra.getNombre(), obra.getAutores(), obra.getDescripcion(),
                    obra.getAnio(), obra.getExterna(), obra.getCuantiaSeguro(), obra.getNumeroSeguro(),
                    obra.getEstado(),
                    obra.getTipoObra() };

            frame.añadirFilaTablaObras(obraData);
            JOptionPane.showMessageDialog(vista, "Obra añadida correctamente.");
            vista.dispose();
        }
    };

    private ActionListener cancelarListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            vista.dispose();
        }
    };

    public ActionListener getGuardarListener() {
        return guardarListener;
    }

    public ActionListener getCancelarListener() {
        return cancelarListener;
    }
}
