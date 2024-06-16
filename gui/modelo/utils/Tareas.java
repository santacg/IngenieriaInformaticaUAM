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
                                obra.exponerObra();
                            }
                        }
                    }

                    // Si la exposición ha finalizado, y no esta en estado de creacion se almacenan
                    // las obras
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
}