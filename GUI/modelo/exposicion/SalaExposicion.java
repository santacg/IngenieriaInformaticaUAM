package GUI.modelo.exposicion;

import java.util.HashSet;
import java.util.Set;
import java.io.Serializable;

import GUI.modelo.obra.*;
import GUI.modelo.sala.Sala;

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
     * 
     * @return {@code true} si las obras se establecen correctamente, {@code false} en caso contrario.
     */
    public Boolean setObras(Set<Obra> obras) {
        for (Obra obra: obras) {
            if (addObra(obra) == false) {
                return false;
            }
        }

        return true;
    }

    /**
     * Añade una obra de arte al conjunto de obras contenidas en esta sala de
     * exposición.
     * 
     * @param obra La obra de arte a añadir.
     * 
     * @return {@code true} si la obra se añade correctamente, {@code false} en caso contrario.
     */
    public Boolean addObra(Obra obra) {
        if (obras.contains(obra)) {
            System.out.println("La obra ya está en la sala");
            return false;
        }

        switch (obra.getEstado()) {
            case RETIRADA:
                System.out.println("No se puede añadir una obra retirada");
                return false;
            case RESTAURACION:
                System.out.println("No se puede añadir una obra en restauración");
                return false;
            case PRESTADA:
                System.out.println("No se puede añadir una obra prestada");
                return false;
            case EXPUESTA:
                System.out.println("No se puede añadir una obra ya expuesta");
                return false;
            default:
                break;
        }

        if (obra.getClass() == ObraNoDigital.class) {
            if (sala.getClimatizador() == false) {
                System.out.println("No se puede añadir una obra no digital a una sala sin climatizador");
                return false;
            }
        }
        
        if (obra.getClass() == Audiovisual.class) {
            int tomasElectricidadRestantes = sala.getTomasElectricidad();
            for (Obra o: obras) {
                if (o.getClass() == Audiovisual.class) {
                    tomasElectricidadRestantes--;
                }
            }
            tomasElectricidadRestantes--;
            if (tomasElectricidadRestantes < 0) {
                System.out.println("No se puede añadir una obra audiovisual a una sala sin tomas de electricidad");
                return false;
            }
        }

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
        if (this.obras.remove(obra) == false) {
            System.out.println("La obra no está en la sala");
            return;
        }

        obra.almacenarObra();
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

    /**
     * Genera un código hash para esta SalaExposicion.
     * 
     * @return Un código hash para esta SalaExposicion.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((sala == null) ? 0 : sala.hashCode());
        return result;
    }

    /**
     * Compara esta SalaExposicion con otro objeto dado. Dos salas de exposición
     * se consideran iguales si tienen la misma sala física.
     * 
     * @param obj El objeto con el que comparar esta SalaExposicion.
     * 
     * @return {@code true} si los objetos son iguales, {@code false} en caso contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SalaExposicion other = (SalaExposicion) obj;
        if (sala == null) {
            if (other.sala != null)
                return false;
        } else if (!sala.equals(other.sala))
            return false;
        return true;
    }

    
}
