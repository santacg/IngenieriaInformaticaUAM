package Sala;

public class SalaCompuesta extends Sala {

    public SalaCompuesta(String iD, String nombre, Integer aforo, Double humedad, Double temperatura,
            Boolean climatizador, Integer tomasElectricidad, Double ancho, Double largo, Sala[] salas) {
        super(iD, nombre, aforo, humedad, temperatura, climatizador, tomasElectricidad, ancho, largo);

    }

    public void addSala(Integer subdivisiones) {
    } 

    public void removeSala() {
    }
    
}
