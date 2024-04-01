package Sala;

import java.util.Set;
import Obra.Obra;

public class SalaExposicion extends Sala {
    private Set<Obra> obras;

    public SalaExposicion(Integer iD, String nombre, Integer capacidad, Set<Obra> obras) {
        super(iD, nombre, capacidad);
        this.obras = obras;
    }
}
