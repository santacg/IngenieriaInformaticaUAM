package Obra;

import java.util.Map;
import java.util.HashMap;

public abstract class ObraNoDigital extends Obra {
    private Double alto, ancho, largo;
    private Map<Double, Double> rangoTemperatura;
    private Map<Double, Double> rangoHumedad;

    public ObraNoDigital(String id, String nombre, Integer anio, String descripcion, boolean externa,
            Double cuantiaSeguro, Double alto, Double ancho, Double temperaturaMaxima, Double temperaturaMinima,
            Double humedadMaxima, Double humedadMinima, String numeroSeguro, Estado estado) {
        super(id, nombre, anio, descripcion, externa, cuantiaSeguro, numeroSeguro, estado);
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

    public Map<Double, Double> getRangoTemperatura() {
        return rangoTemperatura;
    }

    public Map<Double, Double> getRangoHumedad() {
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

    public void setRangoTemperatura(Map<Double, Double> rangoTemperatura) {
        this.rangoTemperatura = rangoTemperatura;
    }

    public void setRangoHumedad(Map<Double, Double> rangoHumedad) {
        this.rangoHumedad = rangoHumedad;
    }
}