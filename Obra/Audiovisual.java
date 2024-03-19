package Obra;

public class Audiovisual extends Obra{
    private Integer duracion;
    private String idioma;
    
    public Audiovisual(String ID, String nombre, Integer anio, String descripcion, Boolean externa,
            Double cuantiaSeguro, String numeroSeguro, Integer duracion, String idioma, Estado estado) {
        super(ID, nombre, anio, descripcion, externa, cuantiaSeguro, numeroSeguro, estado);
        this.duracion = duracion;
        this.idioma = idioma;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }
    
}
