package Sala;

import java.util.Set;

public class SalaCompuesta extends Sala {
    private Set<Sala> salas;

    public SalaCompuesta(String nombre, Integer aforo, Integer humedad, Integer temperatura,
            Boolean climatizador, Integer tomasElectricidad, Double ancho, Double largo) {
        super(nombre, aforo, humedad, temperatura, climatizador, tomasElectricidad, ancho, largo);

    }

    public Set<Sala> getSalas() {
        return salas;
    }

    public void setSalas(Set<Sala> salas) {
        this.salas = salas;
    }

    public void addSala(Sala sala) {
        this.salas.add(sala);
    }

    public void removeSala(Sala sala) {
        this.salas.remove(sala);
    }

    public void removeAllSalas() {
        this.salas.clear();
    }
}