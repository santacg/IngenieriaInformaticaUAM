package Sala;

/**
 * Clase Sala.
 * Es una clase abstracta que representa una sala con características
 * específicas como aforo, humedad, temperatura, entre otras.
 *
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 *
 */
public abstract class Sala {
    private Integer ID;
    private static Integer IDcount = 0;
    private String nombre;
    private Integer aforo;
    private Integer humedad;
    private Integer temperatura;
    private Boolean climatizador;
    private Integer tomasElectricidad;
    private Double ancho;
    private Double largo;

    /**
     * Verifica si esta Sala es igual a otro objeto.
     * La igualdad se basa en la comparación de todas las propiedades de la sala.
     *
     * @param obj El objeto con el que comparar esta Sala.
     * @return true si el objeto proporcionado es una Sala con las mismas
     *         propiedades; de lo contrario, false.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Sala other = (Sala) obj;
        if (nombre == null) {
            if (other.nombre != null)
                return false;
        } else if (!nombre.equals(other.nombre))
            return false;
        if (aforo == null) {
            if (other.aforo != null)
                return false;
        } else if (!aforo.equals(other.aforo))
            return false;
        if (humedad == null) {
            if (other.humedad != null)
                return false;
        } else if (!humedad.equals(other.humedad))
            return false;
        if (temperatura == null) {
            if (other.temperatura != null)
                return false;
        } else if (!temperatura.equals(other.temperatura))
            return false;
        if (climatizador == null) {
            if (other.climatizador != null)
                return false;
        } else if (!climatizador.equals(other.climatizador))
            return false;
        if (tomasElectricidad == null) {
            if (other.tomasElectricidad != null)
                return false;
        } else if (!tomasElectricidad.equals(other.tomasElectricidad))
            return false;
        if (ancho == null) {
            if (other.ancho != null)
                return false;
        } else if (!ancho.equals(other.ancho))
            return false;
        if (largo == null) {
            if (other.largo != null)
                return false;
        } else if (!largo.equals(other.largo))
            return false;
        return true;
    }

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
        this.ID = IDcount++;
        this.nombre = nombre;
        this.aforo = aforo;
        this.humedad = humedad;
        this.temperatura = temperatura;
        this.climatizador = climatizador;
        this.tomasElectricidad = tomasElectricidad;
        this.ancho = ancho;
        this.largo = largo;
    }

    public Integer getID() {
        return ID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getAforo() {
        return aforo;
    }

    public void setAforo(Integer aforo) {
        this.aforo = aforo;
    }

    public Integer getHumedad() {
        return humedad;
    }

    public void setHumedad(Integer humedad) {
        this.humedad = humedad;
    }

    public Integer getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(Integer temperatura) {
        this.temperatura = temperatura;
    }

    public Boolean getClimatizador() {
        return climatizador;
    }

    public void setClimatizador(Boolean climatizador) {
        this.climatizador = climatizador;
    }

    public Integer getTomasElectricidad() {
        return tomasElectricidad;
    }

    public void setTomasElectricidad(Integer tomasElectricidad) {
        this.tomasElectricidad = tomasElectricidad;
    }

    public Double getAncho() {
        return ancho;
    }

    public void setAncho(Double ancho) {
        this.ancho = ancho;
    }

    public Double getLargo() {
        return largo;
    }

    public void setLargo(Double largo) {
        this.largo = largo;
    }

    /**
     * Genera una representación en cadena de la sala y sus propiedades.
     *
     * @return Una cadena que representa los detalles de la sala.
     */
    public String toString() {
        return "Sala [ID=" + ID + ", nombre=" + nombre + ", aforo=" + aforo + ", humedad=" + humedad + ", temperatura="
                + temperatura + ", climatizador=" + climatizador + ", tomasElectricidad=" + tomasElectricidad
                + ", ancho=" + ancho + ", largo=" + largo + "]";
    }
}