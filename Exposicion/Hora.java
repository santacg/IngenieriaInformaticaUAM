package Exposicion;

import java.time.LocalTime;
import java.util.Date;

public class Hora {
    private Date fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private Integer nEntradas;
    private Double precio;

    public Hora(Date fecha, LocalTime horaInicio, LocalTime horaFin, Integer nEntradas, Double precio) {
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.nEntradas = nEntradas;
        this.precio = precio;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public Integer getnEntradas() {
        return nEntradas;
    }

    public void setnEntradas(Integer nEntradas) {
        this.nEntradas = nEntradas;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }


}
