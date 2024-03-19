package Exposicion;


public class Exposicion {
    private Integer ID;
    private String nombre;
    private String fechaInicio;
    private String fechaFin;
    private String descripcion;
    private Double benificios;
    private Boolean publicada;
    private Boolean cerradaTemporalmente;

    public Exposicion(Integer iD, String nombre, String fechaInicio, String fechaFin, String descripcion,
            Double benificios, Boolean publicada, Boolean cerradaTemporalmente) {
        ID = iD;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.descripcion = descripcion;
        this.benificios = benificios;
        this.publicada = publicada;
        this.cerradaTemporalmente = cerradaTemporalmente;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer iD) {
        ID = iD;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getBenificios() {
        return benificios;
    }

    public void setBenificios(Double benificios) {
        this.benificios = benificios;
    }

    public Boolean getPublicada() {
        return publicada;
    }

    public void setPublicada(Boolean publicada) {
        this.publicada = publicada;
    }

    public Boolean getCerradaTemporalmente() {
        return cerradaTemporalmente;
    }

    public void setCerradaTemporalmente(Boolean cerradaTemporalmente) {
        this.cerradaTemporalmente = cerradaTemporalmente;
    }


}