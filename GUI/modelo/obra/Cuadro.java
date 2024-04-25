package GUI.modelo.obra;

/**
 * Clase Cuadro.
 * Representa un cuadro, una categoría específica de obra de arte que extiende
 * la clase {@link ObraNoDigital}. Esta clase incorpora características
 * específicas de un cuadro, como la técnica utilizada en su creación.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 *
 */
public class Cuadro extends ObraNoDigital {
    /**
     * La técnica artística utilizada para crear el cuadro.
     */
    private String tecnica;

    /**
     * Crea una nueva instancia de un cuadro con los detalles especificados.
     * 
     * @param nombre            El nombre del cuadro.
     * @param anio              El año de creación del cuadro.
     * @param descripcion       Una breve descripción del cuadro.
     * @param externa           Indica si la obra es externa
     * @param cuantiaSeguro     El valor asegurado del cuadro.
     * @param numeroSeguro      El número de póliza de seguro asociado al cuadro.
     * @param estado            El estado actual del cuadro
     * @param alto              La altura del cuadro en metros.
     * @param ancho             El ancho del cuadro en metros.
     * @param temperaturaMaxima La temperatura máxima para la conservación
     * @param temperaturaMinima La temperatura mínima para la conservación
     * @param humedadMaxima     La humedad máxima para la conservación
     * @param humedadMinima     La humedad mínima para la conservación
     * @param tecnica           La técnica utilizada para crear el cuadro.
     */
    public Cuadro(String nombre, Integer anio, String descripcion, Boolean externa,
            Double cuantiaSeguro, String numeroSeguro, Double alto, Double ancho,
            Integer temperaturaMaxima, Integer temperaturaMinima,
            Integer humedadMaxima, Integer humedadMinima, String tecnica, String... autores) {
        super(nombre, anio, descripcion, externa, cuantiaSeguro, numeroSeguro, alto, ancho, temperaturaMaxima,
                temperaturaMinima, humedadMaxima, humedadMinima, autores);
        this.tecnica = tecnica;
    }

    /**
     * Obtiene la técnica utilizada para crear el cuadro.
     * 
     * @return La técnica artística del cuadro.
     */
    public String getTecnica() {
        return tecnica;
    }

    /**
     * Establece la técnica utilizada para crear el cuadro.
     * 
     * @param tecnica La nueva técnica artística del cuadro.
     */
    public void setTecnica(String tecnica) {
        this.tecnica = tecnica;
    }

}