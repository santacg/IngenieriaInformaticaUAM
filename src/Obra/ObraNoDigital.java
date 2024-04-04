package Obra;

import java.util.Map;
import java.util.HashMap;

/**
 * Clase ObraNoDigital.
 * Es una clase abstarcta que representa una obra de arte no digital,
 * extendiendo la clase {@link Obra}. Incluye atributos específicos para obras
 * no digitales como dimensiones físicas y rangos óptimos de temperatura
 * y humedad para su conservación.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public abstract class ObraNoDigital extends Obra {
    /**
     * Dimensiones de la obra en metros.
     */
    private Double alto, ancho;
    /**
     * Rango de temperatura óptima para la conservación de la obra, expresada en
     * grados Celsius.
     */
    private Map<Integer, Integer> rangoTemperatura;
    /**
     * Rango de humedad óptima para la conservación de la obra, expresada en
     * porcentaje.
     */
    private Map<Integer, Integer> rangoHumedad;

    /**
     * Crea una nueva obra no digital con las especificaciones proporcionadas.
     *
     * @param nombre            el nombre de la obra
     * @param anio              el año de creación de la obra
     * @param descripcion       una breve descripción de la obra
     * @param externa           indica si la obra es externa
     * @param cuantiaSeguro     el valor asegurado de la obra
     * @param numeroSeguro      el número de póliza del seguro de la obra
     * @param alto              la altura de la obra en metros
     * @param ancho             el ancho de la obra en metros
     * @param temperaturaMaxima la temperatura máxima para la conservación
     * @param temperaturaMinima la temperatura mínima para la conservación
     * @param humedadMaxima     la humedad máxima para la conservación
     * @param humedadMinima     la humedad mínima para la conservación
     */
    public ObraNoDigital(String nombre, Integer anio, String descripcion, Boolean externa,
            Double cuantiaSeguro, String numeroSeguro, Double alto, Double ancho,
            Integer temperaturaMaxima, Integer temperaturaMinima,
            Integer humedadMaxima, Integer humedadMinima) {
        super(nombre, anio, descripcion, externa, cuantiaSeguro, numeroSeguro);
        this.alto = alto;
        this.ancho = ancho;
        this.rangoHumedad = new HashMap<>();
        this.rangoTemperatura = new HashMap<>();

        this.rangoHumedad.put(humedadMinima, humedadMaxima);
        this.rangoTemperatura.put(temperaturaMinima, temperaturaMaxima);
    }

    /**
     * Obtiene el alto del objeto.
     * 
     * @return El alto como Double.
     */
    public Double getAlto() {
        return this.alto;
    }

    /**
     * Obtiene el ancho del objeto.
     * 
     * @return El ancho como Double.
     */
    public Double getAncho() {
        return this.ancho;
    }

    /**
     * Obtiene el rango de temperatura aceptable para el objeto.
     * 
     * @return El rango de temperatura como un Map donde la clave y el valor
     *         representan la temperatura mínima y máxima aceptables,
     *         respectivamente.
     */
    public Map<Integer, Integer> getRangoTemperatura() {
        return rangoTemperatura;
    }

    /**
     * Obtiene el rango de humedad aceptable para el objeto.
     * 
     * @return El rango de humedad como un Map donde la clave y el valor representan
     *         la humedad mínima y máxima aceptables, respectivamente.
     */
    public Map<Integer, Integer> getRangoHumedad() {
        return rangoHumedad;
    }

    /**
     * Establece el alto del objeto.
     * 
     * @param alto El nuevo alto a establecer.
     */
    public void setAlto(Double alto) {
        this.alto = alto;
    }

    /**
     * Establece el ancho del objeto.
     * 
     * @param ancho El nuevo ancho a establecer.
     */
    public void setAncho(Double ancho) {
        this.ancho = ancho;
    }

    /**
     * Establece el rango de temperatura aceptable para el objeto.
     * 
     * @param rangoTemperatura El nuevo rango de temperatura a establecer, donde la
     *                         clave y el valor representan la temperatura mínima y
     *                         máxima aceptables, respectivamente.
     */
    public void setRangoTemperatura(Map<Integer, Integer> rangoTemperatura) {
        this.rangoTemperatura = rangoTemperatura;
    }

    /**
     * Establece el rango de humedad aceptable para el objeto.
     * 
     * @param rangoHumedad El nuevo rango de humedad a establecer, donde la clave y
     *                     el valor representan la humedad mínima y máxima
     *                     aceptables, respectivamente.
     */
    public void setRangoHumedad(Map<Integer, Integer> rangoHumedad) {
        this.rangoHumedad = rangoHumedad;
    }

}