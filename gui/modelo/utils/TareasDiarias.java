package gui.modelo.utils;

import java.time.LocalDate;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import gui.modelo.centroExposicion.CentroExposicion;
import gui.modelo.expofy.Expofy;
import gui.modelo.exposicion.Exposicion;
import gui.modelo.exposicion.SalaExposicion;
import gui.modelo.obra.Obra;

public class TareasDiarias {
    public static void main(String[] args) {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

        Runnable tareas = () -> {
            System.out.println("Ejecutando tareas diarias");

            Expofy expofy = Expofy.getInstance();

            Set<CentroExposicion> centros = expofy.getCentrosExposicion();
            for (CentroExposicion centro : centros) {
                for (Exposicion exposicion : centro.getExposiciones()) {
                    if (exposicion.getFechaInicio().isEqual(LocalDate.now())) {
                        for (SalaExposicion sala : exposicion.getSalas()) {
                            for (Obra obra : sala.getObras()) {
                                obra.exponerObra();
                            }
                        }
                    }
                    else if (exposicion.getFechaFin().isEqual(LocalDate.now())) {
                        for (SalaExposicion sala : exposicion.getSalas()) {
                            for (Obra obra : sala.getObras()) {
                                obra.almacenarObra();
                                sala.removeObra(obra);
                            }
                        }
                    }
                }
            }
        };

        executor.scheduleAtFixedRate(tareas, 0, 24, TimeUnit.HOURS);
    }
}