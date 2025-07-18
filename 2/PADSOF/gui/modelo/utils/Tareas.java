package gui.modelo.utils;

import java.time.LocalDate;
import java.util.Set;

import gui.modelo.centroExposicion.CentroExposicion;
import gui.modelo.centroExposicion.Sorteo;
import gui.modelo.expofy.Expofy;
import gui.modelo.exposicion.EstadoExposicion;
import gui.modelo.exposicion.Exposicion;
import gui.modelo.exposicion.SalaExposicion;
import gui.modelo.obra.Estado;
import gui.modelo.obra.Obra;

/**
 * Clase Tareas.
 * Es una clase que proporciona funcionalidades para la ejecución y comprobación
 * de fechas y sus consecuencias.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public class Tareas {
    public static void iniciarTareas() {
        System.out.println("Ejecutando tareas ");

        Expofy expofy = Expofy.getInstance();

        Set<CentroExposicion> centros = expofy.getCentrosExposicion();
        for (CentroExposicion centro : centros) {

            for (Sorteo sorteo : centro.getSorteos()) {
                if (sorteo.getFechaSorteo().isBefore(LocalDate.now())) {
                    expofy.enviarNotificacionUsuario("La fecha de realización del sorteo de la exposición "
                            + sorteo.getExposicion() + " con fecha " + sorteo.getFechaSorteo() + " ha pasado",
                            centro.getGestor());
                }
            }

            for (Exposicion exposicion : centro.getExposiciones()) {

                EstadoExposicion estado = exposicion.getEstado();

                // Si la exposición está en creación y es el día de inicio, se exponen las obras
                if (exposicion.getFechaInicio().isEqual(LocalDate.now()) && estado == EstadoExposicion.PUBLICADA) {
                    for (SalaExposicion sala : exposicion.getSalas()) {
                        for (Obra obra : sala.getObras()) {
                            if (obra.getEstado() != Estado.EXPUESTA) {
                                try {
                                    obra.exponerObra();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                    // Si la exposición ha finalizado, y no esta en estado de creacion se almacenan
                    // las obras y se borran las salas de exposición asociadas
                } else if (exposicion.getFechaFin().isEqual(LocalDate.now())
                        && estado != EstadoExposicion.EN_CREACION) {
                    for (SalaExposicion sala : exposicion.getSalas()) {
                        exposicion.removeSala(sala);
                    }
                    try {
                        centro.removeExposicion(exposicion);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };
}