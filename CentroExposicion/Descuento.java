package CentroExposicion;

public abstract class Descuento {
    private Double descuento;
    private Integer candtidad;

    public Descuento(Double descuento, Integer candtidad) {
        this.descuento = descuento;
        this.candtidad = candtidad;
    }

    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    public Integer getCandtidad() {
        return candtidad;
    }

    public void setCandtidad(Integer candtidad) {
        this.candtidad = candtidad;
    }
}
