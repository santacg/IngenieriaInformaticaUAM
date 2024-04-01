package Obra;

public class Audiovisual extends Obra{
    private Integer duracion;
    private String idioma;
    
    public Audiovisual(String nombre, Integer anio, String descripcion, Boolean externa, Double cuantiaSeguro,
            String numeroSeguro, Estado estado, Integer duracion, String idioma) {
        super(nombre, anio, descripcion, externa, cuantiaSeguro, numeroSeguro, estado);
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
