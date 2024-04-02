package Sala;

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

    public String toString() {
        return "Sala [ID=" + ID + ", nombre=" + nombre + ", aforo=" + aforo + ", humedad=" + humedad + ", temperatura="
                + temperatura + ", climatizador=" + climatizador + ", tomasElectricidad=" + tomasElectricidad
                + ", ancho=" + ancho + ", largo=" + largo + "]";
    }
}