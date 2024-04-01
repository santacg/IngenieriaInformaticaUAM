package Obra;

public class Escultura extends ObraNoDigital {
    private String material;

    public Escultura(String nombre, Integer anio, String descripcion, Boolean externa,
            Double cuantiaSeguro, String numeroSeguro, Estado estado, Double alto, Double ancho, Integer temperaturaMaxima,  Integer temperaturaMinima,
            Integer humedadMaxima, Integer humedadMinima, String material) {
        super(nombre, anio, descripcion, externa, cuantiaSeguro, alto, ancho, temperaturaMaxima, temperaturaMinima,
                humedadMaxima, humedadMinima, numeroSeguro, estado);
        this.material = material;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }
}