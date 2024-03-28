package Obra;

public class Escultura extends ObraNoDigital {
    private String material;

    public Escultura(Integer id, String nombre, Integer anio, String descripcion, boolean externa, Double cuantiaSeguro,
            Double alto, Double ancho, Double temperaturaMaxima, Double temperaturaMinima, Double humedadMaxima,
            Double humedadMinima, String numeroSeguro, String material, Estado estado) {
        super(id, nombre, anio, descripcion, externa, cuantiaSeguro, alto, ancho, temperaturaMaxima, temperaturaMinima,
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