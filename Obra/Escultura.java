package Obra;

public class Escultura extends ObraNoDigital {
    private String material;
    private Double profundidad;

    public Escultura(String nombre, Integer anio, String descripcion, Boolean externa,
            Double cuantiaSeguro, String numeroSeguro, Estado estado, Double alto, Double ancho, Double profundidad, Integer temperaturaMaxima,  Integer temperaturaMinima,
            Integer humedadMaxima, Integer humedadMinima, String material) {
        super(nombre, anio, descripcion, externa, cuantiaSeguro, numeroSeguro, estado, alto, ancho, temperaturaMaxima, temperaturaMinima,
                humedadMaxima, humedadMinima);
        this.material = material;
        this.profundidad = profundidad;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public Double getProfundidad() {
        return profundidad;
    }

    public void setProfundidad(Double profundidad) {
        this.profundidad = profundidad;
    }
}