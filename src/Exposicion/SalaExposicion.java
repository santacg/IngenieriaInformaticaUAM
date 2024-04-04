package src.Exposicion;

import java.util.HashSet;
import java.util.Set;
import java.io.Serializable;

import src.Obra.Obra;
import src.Obra.ObraNoDigital;
import src.Sala.Sala;

/**
 * Clase SalaExposicion.
 * Es una clase que representa una sala de exposición, que contiene una sala
 * física y un conjunto de obras de arte.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public class SalaExposicion implements Serializable {
    private Sala sala;
    private Set<Obra> obras = new HashSet<>();

    /**
     * Constructor que inicializa una nueva SalaExposicion con una sala específica.
     * 
     * @param sala La sala física asociada a esta sala de exposición.
     */
    public SalaExposicion(Sala sala) {
        this.sala = sala;
    }

    /**
     * Obtiene la sala física asociada a esta sala de exposición.
     * 
     * @return La sala física.
     */
    public Sala getSala() {
        return sala;
    }

    /**
     * Establece la sala física asociada a esta sala de exposición.
     * 
     * @param sala La nueva sala física.
     */
    public void setSala(Sala sala) {
        this.sala = sala; 
    }

    /**
     * Obtiene el conjunto de obras de arte contenidas en esta sala de exposición.
     * 
     * @return El conjunto de obras de arte.
     */
    public Set<Obra> getObras() {
        return obras;
    }

    /**
     * Establece el conjunto de obras de arte contenidas en esta sala de exposición.
     * 
     * @param obras El nuevo conjunto de obras de arte.
     */
    public void setObras(Set<Obra> obras) {
        this.obras = obras;
    }

    /**
     * Añade una obra de arte al conjunto de obras contenidas en esta sala de
     * exposición.
     * 
     * @param obra La obra de arte a añadir.
     */
    public Boolean addObra(Obra obra) {
        if (obra instanceof ObraNoDigital) {
            if (sala.getClimatizador() == false) {
                System.out.println("No se puede añadir una obra no digital a una sala sin climatizador");
                return false;
            }
        }
        obra.exponerObra();
        this.obras.add(obra);
        return true;
    }

    /**
     * Elimina una obra de arte del conjunto de obras contenidas en esta sala de
     * exposición.
     * 
     * @param obra La obra de arte a eliminar.
     */
    public void removeObra(Obra obra) {
        this.obras.remove(obra);
        obra.almecenarObra();
    }

    /**
     * Genera una representación en cadena de texto de esta SalaExposicion,
     * incluyendo la sala y las obras contenidas.
     * 
     * @return Una representación en cadena de la SalaExposicion.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("SalaExposicion Details:\n");
        sb.append("Sala: ").append(sala.getNombre()).append("\n");
        sb.append("Obras: ").append(obras).append("\n");

        return sb.toString();
    }
}
