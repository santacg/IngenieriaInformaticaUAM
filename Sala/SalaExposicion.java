package Sala;

import java.util.HashSet;
import java.util.Set;
import Obra.Obra;

public class SalaExposicion {
    private Sala sala;
    private Set<Obra> obras = new HashSet<>();

    public SalaExposicion(Sala sala) {
        this.sala = sala;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
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

    public String toString() {
        return "SalaExposicion [sala=" + sala + ", obras=" + obras + "]";
    }
}
