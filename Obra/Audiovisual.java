package Obra;

public class Audiovisual extends Obra{
    private String duracion;
    private String idioma;
    
    public Audiovisual(String nombre, Integer anio, String descripcion, Boolean externa, Double cuantiaSeguro,
            String numeroSeguro, Estado estado, String duracion, String idioma) {
        super(nombre, anio, descripcion, externa, cuantiaSeguro, numeroSeguro, estado);
        this.duracion = duracion;
        this.idioma = idioma;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }
    
}
