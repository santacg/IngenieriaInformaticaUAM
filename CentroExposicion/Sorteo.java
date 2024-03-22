package CentroExposicion;

import java.util.Date;
import Exposicion.Exposicion;

public abstract class Sorteo {
    private Date fechaSorteo;
    private Exposicion exposicion;
    
    public Sorteo(Date fechaSorteo, Exposicion exposicion) {
        this.fechaSorteo = fechaSorteo;
        this.exposicion = exposicion;
    }

    public Date getFechaSorteo() {
        return fechaSorteo;
    }

    public void setFechaSorteo(Date fechaSorteo) {
        this.fechaSorteo = fechaSorteo;
    }

    public Exposicion getExposicion() {
        return exposicion;
    }

    public void setExposicion(Exposicion exposicion) {
        this.exposicion = exposicion;
    }
}
