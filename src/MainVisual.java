package src;

import src.centroExposicion.*;
import src.expofy.*;
import src.exposicion.*;
import src.obra.Obra;
import src.sala.Sala;
import src.utils.LectorCSVObras;

import java.time.*;
import java.util.HashSet;
import java.util.Set;

public class MainVisual {
    public static void main(String[] args) {
        Expofy expofy = Expofy.getInstance();

        // Centro de exposicion
        Gestor gestor1 = new Gestor("123");
        Set<Sala> salas = new HashSet<>();
        Sala sala1 = new Sala("Sala1", 100, 50, 25, true, 10, 15.0, 20.0);
        salas.add(sala1);

        CentroExposicion centroExposicion1 = new CentroExposicion("Centro1", LocalTime.of(10, 0, 0),
                LocalTime.of(21, 0, 0), "Madrid",
                "123", "456", gestor1, salas);
                
        expofy.addCentroExposicion(centroExposicion1);
        centroExposicion1.loginGestor("456");

        // Exposicion 
        Set<SalaExposicion> salasExposicion = new HashSet<>();
        SalaExposicion salaExposicion1 = new SalaExposicion(sala1);
        salasExposicion.add(salaExposicion1);

        Exposicion exposicion1 = new Exposicion("Exposicion1", LocalDate.of(2025, 1, 2), LocalDate.now().plusYears(7),
                "Descripción", salasExposicion, TipoExpo.TEMPORAL, 10.0);

        centroExposicion1.addExposicion(exposicion1);
        LectorCSVObras.leerObras(centroExposicion1);

        for (Obra obra : centroExposicion1.getObras()) {
            salaExposicion1.addObra(obra);
        }
        
        exposicion1.expoPublicar();
        expofy.persistirExpofy();
        System.out.println(expofy.toString());
    }
}
