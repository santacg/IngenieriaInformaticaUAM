package CentroExposicion;

import java.util.Date;
import Exposicion.Exposicion;

public class SorteoFechas extends Sorteo {
    private Date fechaInicio;
    private Date fechaFin;

    public SorteoFechas(Exposicion exposicion, Date fechaSorteo, Date fechaInicio, Date fechaFin) {
        super(fechaSorteo, exposicion);
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
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
}