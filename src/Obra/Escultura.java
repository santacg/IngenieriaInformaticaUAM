package src.Obra;

/**
 * Clase Escultura
 * Representa una escultura, una categoría específica de obra de arte que
 * extiende la clase {@link ObraNoDigital}.
 * Esta clase incorpora características específicas de las esculturas, como el
 * material de la que están hecha y su profundidad.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public class Escultura extends ObraNoDigital {
    /**
     * El material del que está hecha la escultura.
     */
    private String material;
    /**
     * La profundidad de la escultura en metros.
     */
    private Double profundidad;

    /**
     * Crea una nueva instancia de una escultura con los detalles especificados.
     * 
     * @param nombre            El nombre de la escultura.
     * @param anio              El año de creación de la escultura.
     * @param descripcion       Una breve descripción de la escultura.
     * @param externa           Indica si la obra es externa
     * @param cuantiaSeguro     El valor asegurado de la escultura.
     * @param numeroSeguro      El número de póliza de seguro asociado
     * @param alto              La altura de la escultura en metros.
     * @param ancho             El ancho de la escultura en metros.
     * @param profundidad       La profundidad de la escultura en metros.
     * @param temperaturaMaxima La temperatura máxima para la conservación
     * @param temperaturaMinima La temperatura mínima para la conservación
     * @param humedadMaxima     La humedad máxima para la conservación
     * @param humedadMinima     La humedad mínima para la conservación
     * @param material          El material del que está hecha la escultura.
     */
    public Escultura(String nombre, Integer anio, String descripcion, Boolean externa,
            Double cuantiaSeguro, String numeroSeguro, Double alto, Double ancho, Double profundidad,
            Integer temperaturaMaxima, Integer temperaturaMinima,
            Integer humedadMaxima, Integer humedadMinima, String material) {
        super(nombre, anio, descripcion, externa, cuantiaSeguro, numeroSeguro, alto, ancho, temperaturaMaxima,
                temperaturaMinima,
                humedadMaxima, humedadMinima);
        this.material = material;
        this.profundidad = profundidad;
    }

    /**
     * Obtiene el material del que está hecha la escultura.
     * 
     * @return El material de la escultura.
     */
    public String getMaterial() {
        return material;
    }

    /**
     * Establece el material del que está hecha la escultura.
     * 
     * @param material El nuevo material de la escultura.
     */
    public void setMaterial(String material) {
        this.material = material;
    }

    /**
     * Obtiene la profundidad de la escultura.
     * 
     * @return La profundidad de la escultura en metros.
     */
    public Double getProfundidad() {
        return profundidad;
    }

    /**
     * Establece la profundidad de la escultura.
     * 
     * @param profundidad La nueva profundidad de la escultura en metros.
     */
    public void setProfundidad(Double profundidad) {
        this.profundidad = profundidad;
    }
}