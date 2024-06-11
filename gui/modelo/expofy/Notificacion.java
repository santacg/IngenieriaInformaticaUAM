package gui.modelo.expofy;

import java.time.LocalDate;
import java.io.Serializable;

/**
 * Clase Notificacion.
 * Esta clase proporciona funcionalidades al envío de notificaciones en Expofy.
 *
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public class Notificacion implements Serializable {
    private String mensaje;
    private LocalDate fecha;
    private boolean leida;

    /**
     * Constructor para crear una nueva notificación.
     * 
     * @param mensaje El mensaje de la notificación.
     * @param fecha   La fecha en que se crea la notificación.
     */
    public Notificacion(String mensaje, LocalDate fecha) {
        this.mensaje = mensaje;
        this.fecha = fecha;
        this.leida = false;
    }

    /**
     * Obtiene el mensaje de la notificación.
     * 
     * @return El mensaje.
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * Indica si la notificación ha sido leída.
     * 
     * @return true si ha sido leída, false en caso contrario.
     */
    public boolean isLeida() {
        return leida;
    }

    /**
     * Establece el estado de la notificación en cuanto a su lectura.
     * 
     * @param leida true si ha sido leída, false en caso contrario.
     */
    public void setLeida(Boolean leida) {
        this.leida = leida;
    }

    /**
     * Establece o actualiza el mensaje de la notificación.
     * 
     * @param mensaje El nuevo mensaje de la notificación.
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * Establece o actualiza la fecha en que se creó la notificación.
     * 
     * @param fecha La nueva fecha.
     */
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    /**
     * Obtiene la fecha en que se creó la notificación.
     * 
     * @return La fecha.
     */
    public LocalDate getFecha() {
        return fecha;
    }

    /**
     * Genera una representación en cadena de la notificación.
     * 
     * @return La representación en cadena de la notificación.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Notificación:\n");
        sb.append("Mensaje: ").append(mensaje).append("\n");
        sb.append("Fecha: ").append(fecha).append("\n");
        sb.append("Leída: ").append(leida).append("\n");
        return sb.toString();
    }
}
