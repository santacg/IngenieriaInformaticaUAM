package Obra;

public class Cuadro extends ObraNoDigital {

    private String tecnica;

    public Cuadro(String id, String nombre, Integer anio, String descripcion, boolean externa, Double cuantiaSeguro,
            Double alto, Double ancho, Double temperaturaMaxima, Double temperaturaMinima, Double humedadMaxima,
            Double humedadMinima, String numeroSeguro, String tecnica) {
        super(id, nombre, anio, descripcion, externa, cuantiaSeguro, alto, ancho, temperaturaMaxima, temperaturaMinima,
                humedadMaxima, humedadMinima, numeroSeguro);


        this.tecnica = tecnica;
    }

    public String getTecnica() {
        return tecnica;
    }

    public void setTecnica(String tecnica) {
        this.tecnica = tecnica;
    }

}