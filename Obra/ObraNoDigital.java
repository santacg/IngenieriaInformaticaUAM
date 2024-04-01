package Obra;

import java.util.Map;
import java.util.HashMap;

public abstract class ObraNoDigital extends Obra {
    private Double alto, ancho, largo;
    private Map<Integer, Integer> rangoTemperatura;
    private Map<Integer, Integer> rangoHumedad;

    public ObraNoDigital(String nombre, Integer anio, String descripcion, Boolean externa,
            Double cuantiaSeguro, String numeroSeguro, Estado estado, Double alto, Double ancho, Integer temperaturaMaxima,  Integer temperaturaMinima,
            Integer humedadMaxima, Integer humedadMinima) {
        super(nombre, anio, descripcion, externa, cuantiaSeguro, numeroSeguro, estado);
        this.alto = alto;
        this.ancho = ancho;
        this.rangoHumedad = new HashMap<>();
        this.rangoTemperatura = new HashMap<>();

        this.rangoHumedad.put(humedadMinima, humedadMaxima);
        this.rangoTemperatura.put(temperaturaMinima, temperaturaMinima);
    }

    public Double getAlto() {
        return this.alto;
    }

    public Double getAncho() {
        return this.ancho;
    }

    public Double getLargo() {
        return this.largo;
    }

    public Map<Integer, Integer> getRangoTemperatura() {
        return rangoTemperatura;
    }

    public Map<Integer, Integer> getRangoHumedad() {
        return rangoHumedad;
    }

    public void setAlto(Double alto) {
        this.alto = alto;
    }

    public void setAncho(Double ancho) {
        this.ancho = ancho;
    }

    public void setLargo(Double largo) {
        this.largo = largo;
    }

    public void setRangoTemperatura(Map<Integer, Integer> rangoTemperatura) {
        this.rangoTemperatura = rangoTemperatura;
    }

    public void setRangoHumedad(Map<Integer, Integer> rangoHumedad) {
        this.rangoHumedad = rangoHumedad;
    }
}