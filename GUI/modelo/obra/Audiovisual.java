package gui.modelo.obra;

/**
 * Clase Audiovisual.
 * Esta clase representa una obra de arte audiovisual, extendiendo la clase
 * abstracta {@link Obra}. Incluye características específicas de las obras
 * audiovisuales como la duración y el idioma.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 *
 */
public class Audiovisual extends Obra {
    private String duracion;
    private String idioma;

    /**
     * Construye una nueva instancia de una obra de arte audiovisual con los
     * parámetros especificados, utilizando el constructor de la superclase
     * {@link Obra}.
     *
     * @param nombre        El nombre de la obra audiovisual.
     * @param anio          El año de creación de la obra audiovisual.
     * @param descripcion   Una breve descripción de la obra audiovisual.
     * @param externa       Indica si la obra es externa o no.
     * @param cuantiaSeguro El valor del seguro de la obra audiovisual.
     * @param numeroSeguro  El número de la póliza del seguro.
     * @param duracion      La duración de la obra audiovisual.
     * @param idioma        El idioma principal de la obra audiovisual.
     */
    public Audiovisual(String nombre, Integer anio, String descripcion, Boolean externa, Double cuantiaSeguro,
            String numeroSeguro, String duracion, String idioma, String... autores) {
        super(nombre, anio, descripcion, externa, cuantiaSeguro, numeroSeguro, autores);
        this.duracion = duracion;
        this.idioma = idioma;
    }

    /**
     * Retorna la duración del contenido.
     * 
     * @return Duración como cadena.
     */
    public String getDuracion() {
        return duracion;
    }

    /**
     * Define la duración del contenido.
     * 
     * @param duracion Duración del contenido.
     */
    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    /**
     * Obtiene el idioma del contenido.
     * 
     * @return Idioma del contenido.
     */
    public String getIdioma() {
        return idioma;
    }

    /**
     * Establece el idioma del contenido.
     * 
     * @param idioma Idioma del contenido.
     */
    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

}
