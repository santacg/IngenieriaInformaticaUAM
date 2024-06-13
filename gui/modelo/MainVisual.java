package gui.modelo;

import java.time.*;
import java.util.*;

import gui.modelo.centroExposicion.*;
import gui.modelo.expofy.*;
import gui.modelo.exposicion.*;
import gui.modelo.obra.*;
import gui.modelo.sala.*;

public class MainVisual {

        public static void main(String[] args) {
                Expofy expofy = Expofy.getInstance();
                registrarCliente(expofy);
                CentroExposicion centroExposicion = configurarCentroExposicion();
                expofy.addCentroExposicion(centroExposicion);
                configurarExposicionesYObra(centroExposicion);
                configurarYPublicarExposiciones(centroExposicion);
                enviarNotificaciones(expofy);
                expofy.persistirExpofy();
        }

        private static void registrarCliente(Expofy expofy) {
                expofy.registrarCliente("123", "Carlos123", true);
        }

        private static CentroExposicion configurarCentroExposicion() {
                Gestor gestor = new Gestor("456");
                Set<Sala> salas = new HashSet<>();
                Sala salaPrincipal = new Sala("Sala de Renacimiento", 200, 55, 22, true, 15, 25.0, 40.0);
                Sala salaImpresionismo = new Sala("Sala de Impresionismo", 150, 45, 24, true, 20, 30.0, 50.0);
                salaPrincipal.addSubsala(8.0, 6.0, 3, 30);
                salaImpresionismo.addSubsala(5.0, 7.0, 2, 25);
                salas.add(salaPrincipal);
                salas.add(salaImpresionismo);

                Empleado empleado1 = new Empleado("555", "Laura", "789", "789456",
                                "Madrid", true, true, true);
                Empleado empleado2 = new Empleado("666", "Jorge", "987",
                                "987456", "Madrid", true, false, true);
                CentroExposicion centro = new CentroExposicion("Museo Nacional del Prado", LocalTime.of(9, 0),
                                LocalTime.of(20, 0), "Madrid", "empleadoPrado", "gestionPrado", gestor,
                                salas);
                centro.addEmpleado(empleado1);
                centro.addEmpleado(empleado2);
                return centro;
        }

        private static void configurarExposicionesYObra(CentroExposicion centro) {
                Set<SalaExposicion> salasExposicion = new HashSet<>();
                SalaExposicion salaExposicion1 = new SalaExposicion(centro.getSalas().iterator().next());
                salasExposicion.add(salaExposicion1);
                Exposicion exposicion1 = new Exposicion("Van Gogh: Los Últimos Años", LocalDate.of(2024, 10, 1),
                                LocalDate.of(2025, 3, 30),
                                "Explora la intensa última década de Van Gogh con obras nunca antes vistas en nuestro país.",
                                TipoExpo.TEMPORAL, 20.0);
                Exposicion exposicion2 = new Exposicion("Los Modernos", LocalDate.of(2024, 6, 15),
                                LocalDate.of(2024, 12, 15),
                                "Un recorrido por el arte moderno a través de las obras de Picasso, Dalí y Matisse.",
                                TipoExpo.TEMPORAL, 18.0);
                centro.addExposicion(exposicion1);
                centro.addExposicion(exposicion2);

                Cuadro cuadro = new Cuadro("Girasoles", 1888,
                                "Una de las series más famosas de Van Gogh.", true, 3000.0, "VG456",
                                92.1, 73.7, 30, 20, 70, 45, "Óleo sobre lienzo", "Vincent van Gogh");
                centro.addObra(cuadro);
                salaExposicion1.addObra(cuadro);
                exposicion1.addSala(salaExposicion1);
        }

        private static void configurarYPublicarExposiciones(CentroExposicion centro) {
                for (Exposicion exposicion : centro.getExposiciones()) {
                        centro.confgiurarSorteoExposicion(exposicion, LocalDate.of(2024, 12, 1), 100);
                        exposicion.expoPublicar();
                }
        }

        private static void enviarNotificaciones(Expofy expofy) {
                expofy.enviarNotificacionUsuario(
                                "Estimado Carlos le notificamos la próxima apertura de la exposición 'Van Gogh: Los Últimos Años'.",
                                expofy.getClienteRegistrado("123"));
                expofy.enviarNotificacionesClientesPublicidad(
                                "Descubre las nuevas exposiciones de arte moderno en el Museo Nacional del Prado. ¡Visítanos!");
        }
}
