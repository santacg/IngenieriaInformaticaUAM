package src.Obra;

/**
 * Clase Fotografía.
 * Esta clase representa las obras fotográficas. Hereda de {@link ObraNoDigital}
 * e introduce la distinción entre fotografías en color y blanco y negro a
 * través del atributo {@code color}.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public class Fotografia extends ObraNoDigital {
    /**
     * Indica si la fotografía es en color.
     * {@code true} para color
     * {@code false} para blanco y negro.
     */
    private Boolean color;

    /**
     * Constructor de una obra fotográfica con los parámetros especificados.
     * 
     * @param nombre            el nombre de la fotografía.
     * @param anio              el año de creación de la fotografía.
     * @param descripcion       una descripción de la fotografía.
     * @param externa           indica si la obra es externa.
     * @param cuantiaSeguro     el valor asegurado de la fotografía.
     * @param numeroSeguro      el número de póliza de seguro de la fotografía.
     * @param alto              el alto de la fotografía.
     * @param ancho             el ancho de la fotografía.
     * @param temperaturaMaxima la temperatura máxima para la conservación
     * @param temperaturaMinima la temperatura mínima para la conservación
     * @param humedadMaxima     la humedad máxima para la conservación de
     * @param humedadMinima     la humedad mínima para la conservación de
     * @param color             si la fotografía es en color o en blanco y negro.
     */
    public Fotografia(String nombre, Integer anio, String descripcion, Boolean externa,
            Double cuantiaSeguro, String numeroSeguro, Double alto, Double ancho,
            Integer temperaturaMaxima, Integer temperaturaMinima,
            Integer humedadMaxima, Integer humedadMinima, Boolean color) {
        super(nombre, anio, descripcion, externa, cuantiaSeguro, numeroSeguro,
                alto, ancho, temperaturaMaxima, temperaturaMinima,
                humedadMaxima, humedadMinima);

        this.color = color;
    }

    /**
     * Devuelve si la fotografía es en color.
     *
     * @return {@code true} si la fotografía es en color,
     *         {@code false} si es en blanco y negro.
     */
    public Boolean getColor() {
        return this.color;
    }

    /**
     * Establece si la fotografía es en color o en blanco y negro.
     *
     * @param color {@code true} para establecer la fotografía en color,
     *              {@code false} para blanco y negro.
     */
    public void setColor(Boolean color) {
        this.color = color;
    }

}
