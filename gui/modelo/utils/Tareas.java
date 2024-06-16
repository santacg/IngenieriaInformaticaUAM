package gui.modelo.utils;

import java.time.LocalDate;
import java.util.Set;

import gui.modelo.centroExposicion.CentroExposicion;
import gui.modelo.centroExposicion.Sorteo;
import gui.modelo.expofy.Expofy;
import gui.modelo.exposicion.EstadoExposicion;
import gui.modelo.exposicion.Exposicion;
import gui.modelo.exposicion.SalaExposicion;
import gui.modelo.obra.Obra;

public class Tareas {
    public static void iniciarTareas() {
        System.out.println("Ejecutando tareas ");

        Expofy expofy = Expofy.getInstance();

        Set<CentroExposicion> centros = expofy.getCentrosExposicion();
        for (CentroExposicion centro : centros) {
            
            // Se realizan los sorteos de forma automática si es el día del sorteo
            for (Sorteo sorteo : centro.getSorteos()) {
                if (sorteo.getFechaSorteo().isEqual(LocalDate.now())) {
                    sorteo.realizarSorteo();
                }
            }

            for (Exposicion exposicion : centro.getExposiciones()) {

                EstadoExposicion estado = exposicion.getEstado();

                // Si la exposición está en creación y es el día de inicio, se exponen las obras
                if (exposicion.getFechaInicio().isEqual(LocalDate.now()) && estado == EstadoExposicion.PUBLICADA) {
                    for (SalaExposicion sala : exposicion.getSalas()) {
                        for (Obra obra : sala.getObras()) {
                            obra.exponerObra();
                        }
                    }

                } else if (exposicion.getFechaFin().isEqual(LocalDate.now()) && estado != EstadoExposicion.EN_CREACION) {
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