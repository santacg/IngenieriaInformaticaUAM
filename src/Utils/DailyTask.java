package src.Utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DailyTask {
    public static void main(String[] args) {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

        Runnable task = () -> {
            System.out.println("Ejecutando tareas diarias");
            // Comprobar si una exposición ha terminado
            
        };

        executor.scheduleAtFixedRate(task, 0, 24, TimeUnit.HOURS);
    }
}