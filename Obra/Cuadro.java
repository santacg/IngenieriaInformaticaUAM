package Obra;

public class Cuadro extends ObraNoDigital {

    private String tecnica;

    public Cuadro(String nombre, Integer anio, String descripcion, Boolean externa,
            Double cuantiaSeguro, String numeroSeguro, Estado estado, Double alto, Double ancho, Integer temperaturaMaxima,  Integer temperaturaMinima,
            Integer humedadMaxima, Integer humedadMinima, String tecnica) {
        super(nombre, anio, descripcion, externa, cuantiaSeguro, alto, ancho, temperaturaMaxima, temperaturaMinima,
                humedadMaxima, humedadMinima, numeroSeguro, estado);

        this.tecnica = tecnica;
    }

    public String getTecnica() {
        return tecnica;
    }

    public void setTecnica(String tecnica) {
        this.tecnica = tecnica;
    }

}