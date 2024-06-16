package gui.modelo.utils;

import java.time.LocalDate;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import gui.modelo.centroExposicion.CentroExposicion;
import gui.modelo.centroExposicion.Sorteo;
import gui.modelo.expofy.Expofy;
import gui.modelo.exposicion.EstadoExposicion;
import gui.modelo.exposicion.Exposicion;
import gui.modelo.exposicion.SalaExposicion;
import gui.modelo.obra.Obra;

public class Tareas {
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public static void iniciarTareas() {
        System.out.println("Programando tareas para ejecución cada 10 segundos.");

        Runnable tareas = () -> {

            Expofy expofy = Expofy.getInstance();
            Set<CentroExposicion> centros = expofy.getCentrosExposicion();

            System.out.println("Ejecutando tareas programadas.");

            for (CentroExposicion centro : centros) {
                
                for (Sorteo sorteo : centro.getSorteos()) {
                    if (sorteo.getFechaSorteo().isEqual(LocalDate.now()) && sorteo.isRealizado() == false) {
                        sorteo.realizarSorteo();
                    }
                }

                for (Exposicion exposicion : centro.getExposiciones()) {

                    EstadoExposicion estado = exposicion.getEstado();

                    if (exposicion.getFechaInicio().isEqual(LocalDate.now()) && estado == EstadoExposicion.PUBLICADA) {

                        for (SalaExposicion sala : exposicion.getSalas()) {
                            for (Obra obra : sala.getObras()) {
                                obra.exponerObra();
                            }
                        }

                    } else if (exposicion.getFechaFin().isEqual(LocalDate.now())
                            && estado != EstadoExposicion.EN_CREACION) {

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

        scheduler.scheduleAtFixedRate(tareas, 0, 10, TimeUnit.SECONDS);
    }

    public static void detenerTareas() {
        if (!scheduler.isShutdown()) {
            scheduler.shutdown();
            System.out.println("Tareas detenidas.");
        }
    }
}
