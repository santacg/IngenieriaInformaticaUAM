package Obra;

public class Fotografia extends ObraNoDigital{
    private Boolean color;

    public Fotografia(String nombre, Integer anio, String descripcion, Boolean externa,
            Double cuantiaSeguro, String numeroSeguro, Estado estado, Double alto, Double ancho, Integer temperaturaMaxima, Integer temperaturaMinima,
            Integer humedadMaxima, Integer humedadMinima, Boolean color) {
        super(nombre, anio, descripcion, externa, cuantiaSeguro, numeroSeguro, estado, alto, ancho, temperaturaMaxima, temperaturaMinima,
                humedadMaxima, humedadMinima);

        this.color = color;
    }

    public Boolean getColor() {
        return this.color;
    }

    public void setColor(Boolean color) {
        this.color = color;
    }

} 
