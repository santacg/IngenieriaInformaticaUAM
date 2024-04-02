package Sala;

import java.util.HashSet;
import java.util.Set;

/**
 * Clase SalaCompuesta.
 * Es una clase que hereda de {@link Sala} y está compuesta de salas en su
 * interior.
 *
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 *
 */
public class SalaCompuesta extends Sala {
    private Set<Sala> salas = new HashSet<>();

    /**
     * Constructor que inicializa una SalaCompuesta con propiedades específicas y un
     * conjunto inicial vacío de salas.
     *
     * @param nombre            El nombre de la sala compuesta.
     * @param aforo             El aforo máximo permitido en la sala compuesta.
     * @param humedad           El nivel de humedad en la sala compuesta.
     * @param temperatura       La temperatura en la sala compuesta.
     * @param climatizador      Indica si la sala compuesta tiene o no climatizador.
     * @param tomasElectricidad El número de tomas de electricidad disponibles
     * @param ancho             El ancho de la sala compuesta.
     * @param largo             El largo de la sala compuesta.
     */
    public SalaCompuesta(String nombre, Integer aforo, Integer humedad, Integer temperatura,
            Boolean climatizador, Integer tomasElectricidad, Double ancho, Double largo) {
        super(nombre, aforo, humedad, temperatura, climatizador, tomasElectricidad, ancho, largo);

    }

    /**
     * Obtiene el conjunto de salas que forman esta sala compuesta.
     * 
     * @return El conjunto de salas.
     */
    public Set<Sala> getSalas() {
        return salas;
    }

    /**
     * Establece el conjunto de salas que forman esta sala compuesta.
     * 
     * @param salas El nuevo conjunto de salas.
     */
    public void setSalas(Set<Sala> salas) {
        this.salas = salas;
    }

    /**
     * Añade una sala al conjunto de salas que forman esta sala compuesta.
     * 
     * @param sala La sala a añadir.
     */
    public void addSala(Sala sala) {
        this.salas.add(sala);
    }

    /**
     * Elimina una sala del conjunto de salas que forman esta sala compuesta.
     * 
     * @param sala La sala a eliminar.
     */
    public void removeSala(Sala sala) {
        this.salas.remove(sala);
    }

    /**
     * Elimina todas las salas del conjunto de salas que forman esta sala compuesta.
     */
    public void removeAllSalas() {
        this.salas.clear();
    }
}