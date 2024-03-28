package Exposicion;

import Sala.SalaExposicion;
import java.util.Set;
import CentroExposicion.Descuento;

public class Exposicion {
    private Integer ID;
    private String nombre;
    private String fechaInicio;
    private String fechaFin;
    private String descripcion;
    private Double benificios;
    private Boolean publicada;
    private EstadoExposicion estado;
    private Set<SalaExposicion> salas;
    private Set<Hora> horario;
    private Set<Descuento> descuentos;
    private Estadisticas estadisticas;
    private TipoExpo tipo;

    public Exposicion(Integer iD, String nombre, String fechaInicio, String fechaFin, String descripcion,
        Double benificios, Boolean publicada, Set<SalaExposicion> salas, Estadisticas estadisticas, TipoExpo tipo) { 
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.descripcion = descripcion;
        this.benificios = benificios;
        this.publicada = publicada;
        this.salas = salas;
        this.estadisticas = estadisticas;
        this.tipo = tipo;
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

    public void setEstado(EstadoExposicion estado) {
        this.estado = estado;
    }

    public void expoCrear() {
        this.estado = EstadoExposicion.EN_CREACION;
        this.publicada = false;
    }

    public void expoPublicar() {
        this.estado = EstadoExposicion.PUBLICADA;
        this.publicada = true;
    }

    public void expoCancelar() {
        this.estado = EstadoExposicion.CANCELADA;
        this.publicada = false;
    }

    public void expoProrrogar() {
        this.estado = EstadoExposicion.PRORROGADA;
        this.publicada = false;
    }

    public EstadoExposicion getEstado() {
        return this.estado;
    }

    public void setSalas(Set<SalaExposicion> salas) {
        this.salas = salas;
    }

    public Set<SalaExposicion> getSalas() {
        return this.salas;
    }

    public void addSala(SalaExposicion sala) {
        this.salas.add(sala);
    }

    public void removeSala(SalaExposicion sala) {
        this.salas.remove(sala);
    }

    public void setHorario(Set<Hora> horario) {
        this.horario = horario;
    }

    public Set<Hora> getHorario() {
        return this.horario;
    }

    public void addHorario(Hora hora) {
        this.horario.add(hora);
    }

    public void removeHorario(Hora hora) {
        this.horario.remove(hora);
    }

    public void setEstadisticas(Estadisticas estadisticas) {
        this.estadisticas = estadisticas;
    }

    public Estadisticas getEstadisticas() {
        return this.estadisticas;
    }

    public TipoExpo getTipo() {
        return tipo;
    }

    public void setTipo(TipoExpo tipo) {
        this.tipo = tipo;
    }

    public void expoTemporal() {
        this.tipo = TipoExpo.TEMPORAL;   
    }

    public void expoPermanente() {
        this.tipo = TipoExpo.PERMANENTE;
    }

    public Set<Descuento> getDescuentos() {
        return descuentos;
    }

    public void setDescuentos(Set<Descuento> descuentos) {
        this.descuentos = descuentos;
    }

    public void addDescuento(Descuento descuento) {
        this.descuentos.add(descuento);
    }

    public void removeDescuento(Descuento descuento) {
        this.descuentos.remove(descuento);
    }

}
