package Sala;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase Sala.
 * Es una clase abstracta que representa una sala con características
 * específicas como aforo, humedad, temperatura, entre otras.
 *
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 *
 */
public class Sala {
    private String nombre;
    private Integer aforo;
    private Integer humedad;
    private Integer temperatura;
    private Boolean climatizador;
    private Integer tomasElectricidad;
    private Double ancho;
    private Double largo;
    private List<Sala> subsalas = new ArrayList<>();
    private Sala salaPadre = null;

    /**
     * Constructor que inicializa una nueva sala con sus propiedades específicas.
     *
     * @param nombre            El nombre de la sala.
     * @param aforo             El aforo máximo permitido en la sala.
     * @param humedad           El nivel de humedad en la sala.
     * @param temperatura       La temperatura en la sala.
     * @param climatizador      Si la sala tiene o no climatizador.
     * @param tomasElectricidad El número de tomas de electricidad disponibles en la
     *                          sala.
     * @param ancho             El ancho de la sala.
     * @param largo             El largo de la sala.
     */
    public Sala(String nombre, Integer aforo, Integer humedad, Integer temperatura, Boolean climatizador,
            Integer tomasElectricidad, Double ancho, Double largo) {
        this.nombre = nombre;
        this.aforo = aforo;
        this.humedad = humedad;
        this.temperatura = temperatura;
        this.climatizador = climatizador;
        this.tomasElectricidad = tomasElectricidad;
        this.ancho = ancho;
        this.largo = largo;
    }

    /**
     * Obtiene el nombre de la sala.
     * 
     * @return El nombre de la sala.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre de la sala.
     * 
     * @param nombre El nuevo nombre para la sala.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el aforo de la sala.
     * 
     * @return El aforo de la sala.
     */
    public Integer getAforo() {
        return aforo;
    }

    /**
     * Establece el aforo de la sala.
     * 
     * @param aforo El nuevo aforo para la sala.
     */
    public void setAforo(Integer aforo) {
        this.aforo = aforo;
    }

    /**
     * Obtiene el nivel de humedad de la sala.
     * 
     * @return El nivel de humedad en la sala.
     */
    public Integer getHumedad() {
        return humedad;
    }

    /**
     * Establece el nivel de humedad en la sala.
     * 
     * @param humedad El nuevo nivel de humedad para la sala.
     */
    public void setHumedad(Integer humedad) {
        this.humedad = humedad;
    }

    /**
     * Obtiene la temperatura de la sala.
     * 
     * @return La temperatura en la sala.
     */
    public Integer getTemperatura() {
        return temperatura;
    }

    /**
     * Establece la temperatura en la sala.
     * 
     * @param temperatura La nueva temperatura para la sala.
     */
    public void setTemperatura(Integer temperatura) {
        this.temperatura = temperatura;
    }

    /**
     * Obtiene el estado del climatizador de la sala.
     * 
     * @return Verdadero si la sala tiene climatizador, falso de lo contrario.
     */
    public Boolean getClimatizador() {
        return climatizador;
    }

    /**
     * Establece el estado del climatizador en la sala.
     * 
     * @param climatizador Verdadero para indicar que la sala tiene climatizador,
     *                     falso de lo contrario.
     */
    public void setClimatizador(Boolean climatizador) {
        this.climatizador = climatizador;
    }

    /**
     * Obtiene el número de tomas de electricidad disponibles en la sala.
     * 
     * @return El número de tomas de electricidad.
     */
    public Integer getTomasElectricidad() {
        return tomasElectricidad;
    }

    /**
     * Establece el número de tomas de electricidad en la sala.
     * 
     * @param tomasElectricidad El nuevo número de tomas de electricidad para la
     *                          sala.
     */
    public void setTomasElectricidad(Integer tomasElectricidad) {
        this.tomasElectricidad = tomasElectricidad;
    }

    /**
     * Obtiene el ancho de la sala.
     * 
     * @return El ancho de la sala.
     */
    public Double getAncho() {
        return ancho;
    }

    /**
     * Establece el ancho de la sala.
     * 
     * @param ancho El nuevo ancho para la sala.
     */
    public void setAncho(Double ancho) {
        this.ancho = ancho;
    }

    /**
     * Obtiene el largo de la sala.
     * 
     * @return El largo de la sala.
     */
    public Double getLargo() {
        return largo;
    }

    /**
     * Establece el largo de la sala.
     * 
     * @param largo El nuevo largo para la sala.
     */
    public void setLargo(Double largo) {
        this.largo = largo;
    }

    public boolean addSubsala(Double ancho, Double largo, Integer nTomasElectricidad, Integer aforo) {
        if (this.aforo <= aforo || this.ancho <= ancho || this.largo <= largo
                || this.tomasElectricidad <= nTomasElectricidad) {
            System.out.println("No se puede añadir la subsala, no hay suficientes recursos.");
            return false;
        }

        int numSubsalas = subsalas.size();
        Sala subsala = new Sala(nombre + " subsala" + (numSubsalas + 1), nAforo, humedad, temperatura, climatizador,
                nTomasElectricidad, ancho, largo);
        subsala.salaPadre = this;
        this.aforo -= aforo;
        this.ancho -= ancho;
        this.largo -= largo;
        this.tomasElectricidad -= nTomasElectricidad;
        this.subsalas.add(subsala);

        return true;
    }

    public void removeSubsala() {
        if (subsalas.size() > 0) {
            subsalas.remove(subsalas.size() - 1);
            subsalas.remove(subsalas.size() - 1);
        }
    }

    public List<Sala> getSubsalas() {
        return subsalas;
    }

    public Sala getSalaPadre() {
        return salaPadre;
    }

    /**
     * Genera una representación en cadena de la sala y sus propiedades.
     *
     * @return Una cadena que representa los detalles de la sala.
     */
    public String toString() {
        return "Sala [nombre=" + nombre + ", aforo=" + aforo + ", humedad=" + humedad + ", temperatura=" + temperatura
                + ", climatizador=" + climatizador + ", tomasElectricidad=" + tomasElectricidad + ", ancho=" + ancho
                + ", largo=" + largo + ", subsalas=" + subsalas + ", salaPadre=" + salaPadre + "]";
    }
}