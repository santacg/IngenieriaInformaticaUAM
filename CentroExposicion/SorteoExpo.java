package CentroExposicion;

import java.util.Date;
import Exposicion.Exposicion;   

public class SorteoExpo extends Sorteo{
    private Boolean vigenciaExpo;

    public SorteoExpo(Exposicion exposicion, Date fechaSorteo, Boolean vigenciaExpo) {
        super(fechaSorteo, exposicion);
        this.vigenciaExpo = vigenciaExpo;
    }

    public Boolean getVigenciaExpo() {
        return vigenciaExpo;
    }

    public void setVigenciaExpo(Boolean vigenciaExpo) {
        this.vigenciaExpo = vigenciaExpo;
    }

}
