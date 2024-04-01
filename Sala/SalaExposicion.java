package Sala;

import java.util.HashSet;
import java.util.Set;
import Obra.Obra;

public class SalaExposicion extends Sala {
    private Set<Obra> obras = new HashSet<>();

    public SalaExposicion(String nombre, Integer aforo, Double humedad, Double temperatura,
            Boolean climatizador, Integer tomasElectricidad, Double ancho, Double largo) {
        super(nombre, aforo, humedad, temperatura, climatizador, tomasElectricidad, ancho, largo);
    }

    public Set<Obra> getObras() {
        return obras;
    }

    public void setObras(Set<Obra> obras) {
        this.obras = obras;
    }

    public void addObra(Obra obra) {
        this.obras.add(obra);
    }

    public void removeObra(Obra obra) {
        this.obras.remove(obra);
    }

    public void removeAllObras() {
        this.obras.clear();
    }

    @Override
    public String toString() {
        return "SalaExposicion [obras=" + obras.toString() + "]";
    }

}
