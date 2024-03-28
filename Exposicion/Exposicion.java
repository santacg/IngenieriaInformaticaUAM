package Exposicion;

import Sala.SalaExposicion;
import java.util.Date;
import java.util.Set;
import CentroExposicion.Descuento;

public class Exposicion {
    private Integer ID;
    private String nombre;
    private Date fechaInicio;
    private Date fechaFin;
    private String descripcion;
    private Double benificios;
    private EstadoExposicion estado;
    private Set<SalaExposicion> salas;
    private Set<Hora> horario;
    private Set<Descuento> descuentos;
    private Estadisticas estadisticas;
    private TipoExpo tipo;

    public Exposicion(Integer iD, String nombre, Date fechaInicio, Date fechaFin, String descripcion,
        Double benificios, Set<SalaExposicion> salas, Estadisticas estadisticas, 
        TipoExpo tipo, EstadoExposicion estado) { 
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.descripcion = descripcion;
        this.benificios = benificios;
        this.salas = salas;
        this.estadisticas = estadisticas;
        this.tipo = tipo;
        this.estado = estado;
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

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
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

    public void setEstado(EstadoExposicion estado) {
        this.estado = estado;
    }

    public void expoCrear() {
        this.estado = EstadoExposicion.EN_CREACION;
    }

    public void expoPublicar() {
        this.estado = EstadoExposicion.PUBLICADA;
    }

    public void expoCancelar() {
        this.estado = EstadoExposicion.CANCELADA;
    }

    public void expoProrrogar(Date fechaFin) {
        this.estado = EstadoExposicion.PRORROGADA;
        this.fechaFin = fechaFin;
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

    public void removeAllDescuentos() {
        this.descuentos.clear();
    }

}
