package src.obra;


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

    private double alto;
    private double ancho;
    private Integer temperaturaMaxima;
    private Integer temperaturaMinima;
    private Integer humedadMaxima;
    private Integer humedadMinima;

    /**
     * Crea una nueva obra no digital con las especificaciones proporcionadas.
     *
     * @param nombre            el nombre de la obra
     * @param anio              el año de cración de la obra
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
     * @param autores           los autores de la obra
     */
    public ObraNoDigital(String nombre, Integer anio, String descripcion, Boolean externa,
            Double cuantiaSeguro, String numeroSeguro, Double alto, Double ancho,
            Integer temperaturaMaxima, Integer temperaturaMinima,
            Integer humedadMaxima, Integer humedadMinima, String... autores) {
        super(nombre, anio, descripcion, externa, cuantiaSeguro, numeroSeguro, autores);
        if (temperaturaMaxima != null && temperaturaMinima != null && humedadMaxima != null && humedadMinima != null) {
            if (temperaturaMaxima < temperaturaMinima || humedadMaxima < humedadMinima) {
                System.out.println("Temperaturas o humedades incorrectas");
                return;
            }
        }
        this.alto = alto;
        this.ancho = ancho;
        this.temperaturaMaxima = temperaturaMaxima;
        this.temperaturaMinima = temperaturaMinima;
        this.humedadMaxima = humedadMaxima;
        this.humedadMinima = humedadMinima;
    }

    /**
     * Obtiene la altura de la obra en metros.
     *
     * @return la altura de la obra
     */
    public double getAlto() {
        return alto;
    } 

    /**
     * Obtiene el ancho de la obra en metros.
     *
     * @return el ancho de la obra
     */
    public double getAncho() {
        return ancho;
    }

    /**
     * Obtiene la temperatura máxima para la conservación de la obra.
     *
     * @return la temperatura máxima
     */
    public Integer getTemperaturaMaxima() {
        return temperaturaMaxima;
    }

    /**
     * Obtiene la temperatura mínima para la conservación de la obra.
     *
     * @return la temperatura mínima
     */
    public Integer getTemperaturaMinima() {
        return temperaturaMinima;
    }

    /**
     * Obtiene la humedad máxima para la conservación de la obra.
     *
     * @return la humedad máxima
     */
    public Integer getHumedadMaxima() {
        return humedadMaxima;
    }

    /**
     * Obtiene la humedad mínima para la conservación de la obra.
     *
     * @return la humedad mínima
     */
    public Integer getHumedadMinima() {
        return humedadMinima;
    }

    /**
     * Establece la altura de la obra en metros.
     *
     * @param alto la altura de la obra
     */
    public void setAlto(double alto) {
        this.alto = alto;
    }

    /**
     * Establece el ancho de la obra en metros.
     *
     * @param ancho el ancho de la obra
     */
    public void setAncho(double ancho) {
        this.ancho = ancho;
    }

    /**
     * Establece la temperatura máxima para la conservación de la obra.
     *
     * @param temperaturaMaxima la temperatura máxima
     */
    public void setTemperaturaMaxima(Integer temperaturaMaxima) {
        if (temperaturaMaxima < this.temperaturaMinima) {
            System.out.println("Temperatura máxima incorrecta");
            return;
        }
        this.temperaturaMaxima = temperaturaMaxima;
    }

    /**
     * Establece la temperatura mínima para la conservación de la obra.
     *
     * @param temperaturaMinima la temperatura mínima
     */
    public void setTemperaturaMinima(Integer temperaturaMinima) {
        if (temperaturaMinima > this.temperaturaMaxima) {
            System.out.println("Temperatura mínima incorrecta");
            return;
        }
        this.temperaturaMinima = temperaturaMinima;
    }

    /**
     * Establece la humedad máxima para la conservación de la obra.
     *
     * @param humedadMaxima la humedad máxima
     */
    public void setHumedadMaxima(Integer humedadMaxima) {
        if (humedadMaxima < this.humedadMinima) {
            System.out.println("Humedad máxima incorrecta");
            return;
        }
        this.humedadMaxima = humedadMaxima;
    }

    /**
     * Establece la humedad mínima para la conservación de la obra.
     *
     * @param humedadMinima la humedad mínima
     */
    public void setHumedadMinima(Integer humedadMinima) {
        if (humedadMinima > this.humedadMaxima) {
            System.out.println("Humedad mínima incorrecta");
            return;
        }
        this.humedadMinima = humedadMinima;
    }

    /**
     * Establece las dimensiones de la obra.
     *
     * @param alto  la altura de la obra
     * @param ancho el ancho de la obra
     */
    public void setDimensiones(double alto, double ancho) {
        this.alto = alto;
        this.ancho = ancho;
    }

    /**
     * Establece los rangos climáticos de la obra.
     *
     * @param temperaturaMaxima la temperatura máxima
     * @param temperaturaMinima la temperatura mínima
     * @param humedadMaxima     la humedad máxima
     * @param humedadMinima     la humedad mínima
     */
    public void setRangosClima(Integer temperaturaMaxima, Integer temperaturaMinima, Integer humedadMaxima, Integer humedadMinima) {
        if (temperaturaMaxima < temperaturaMinima || humedadMaxima < humedadMinima) {
            System.out.println("Temperaturas o humedades incorrectas");
            return;
        }
        this.temperaturaMaxima = temperaturaMaxima;
        this.temperaturaMinima = temperaturaMinima;
        this.humedadMaxima = humedadMaxima;
        this.humedadMinima = humedadMinima;
    }
}