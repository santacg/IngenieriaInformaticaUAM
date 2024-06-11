package gui.modelo.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TareasDiarias {
    public static void main(String[] args) {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

        Runnable tareas = () -> {
            System.out.println("Ejecutando tareas diarias");
            // Comprobar si una exposición ha terminado
            
        };

        executor.scheduleAtFixedRate(tareas, 0, 24, TimeUnit.HOURS);
    }
}