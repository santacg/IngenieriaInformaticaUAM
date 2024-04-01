package Obra;

public class Fotografia extends ObraNoDigital{
    private Boolean color;

    public Fotografia(String nombre, Integer anio, String descripcion, boolean externa, Double cuantiaSeguro,
            Double alto, Integer ancho, Integer temperaturaMaxima, Integer temperaturaMinima, Integer humedadMaxima,
            Integer humedadMinima, Integer numeroSeguro, Boolean color, Estado estado) {
        super(nombre, anio, descripcion, externa, cuantiaSeguro, alto, ancho, temperaturaMaxima, temperaturaMinima,
                humedadMaxima, humedadMinima, numeroSeguro, estado);

        this.color = color;
    }

    public Boolean getColor() {
        return this.color;
    }

    public void setColor(Boolean color) {
        this.color = color;
    }

} 
