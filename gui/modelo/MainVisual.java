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
                registrarClientes(expofy);
                CentroExposicion centroExposicionPrado = configurarCentroExposicionPrado();
                CentroExposicion centroExposicionReinaSofia = configurarCentroExposicionReinaSofia();

                expofy.addCentroExposicion(centroExposicionPrado);
                expofy.addCentroExposicion(centroExposicionReinaSofia);

                configurarExposicionesYObra(centroExposicionPrado);
                configurarExposicionesYObra(centroExposicionReinaSofia);

                configurarYPublicarExposiciones(centroExposicionPrado);
                configurarYPublicarExposiciones(centroExposicionReinaSofia);

                enviarNotificaciones(expofy);
                expofy.persistirExpofy();
        }

        private static void registrarClientes(Expofy expofy) {
                expofy.registrarCliente("123", "Carlos123", true);
                expofy.registrarCliente("456", "Ana456", true);
        }

        private static CentroExposicion configurarCentroExposicionPrado() {
                Gestor gestorPrado = new Gestor("gestionPrado");
                Set<Sala> salasPrado = new HashSet<>();
                Sala salaRenacimiento = new Sala("Sala de Renacimiento", 200, 55, 22, true, 15, 25.0, 40.0);
                Sala salaImpresionismo = new Sala("Sala de Impresionismo", 150, 45, 24, true, 20, 30.0, 50.0);
                salasPrado.add(salaRenacimiento);
                salasPrado.add(salaImpresionismo);

                Empleado empleado1 = new Empleado("555", "Laura", "789", "789456", "Madrid", true, true, true);
                Empleado empleado2 = new Empleado("666", "Jorge", "987", "987456", "Madrid", true, false, true);

                CentroExposicion centroPrado = new CentroExposicion("Museo Nacional del Prado", LocalTime.of(9, 0),
                                LocalTime.of(20, 0), "Madrid", "empleadoPrado", "gestionPrado", gestorPrado,
                                salasPrado);
                centroPrado.addEmpleado(empleado1);
                centroPrado.addEmpleado(empleado2);

                return centroPrado;
        }

        private static CentroExposicion configurarCentroExposicionReinaSofia() {
                Gestor gestorSofia = new Gestor("gestionSofia");
                Set<Sala> salasSofia = new HashSet<>();
                Sala salaModerna = new Sala("Sala Arte Moderno", 180, 60, 21, true, 12, 20.0, 35.0);
                Sala salaContemporaneo = new Sala("Sala Arte Contemporáneo", 160, 50, 23, true, 18, 28.0, 45.0);
                salasSofia.add(salaModerna);
                salasSofia.add(salaContemporaneo);

                Empleado empleado3 = new Empleado("777", "Marta", "123", "123456", "Madrid", true, true, false);
                Empleado empleado4 = new Empleado("888", "Antonio", "321", "654321", "Madrid", true, false, true);

                CentroExposicion centroSofia = new CentroExposicion("Museo Reina Sofia", LocalTime.of(10, 0),
                                LocalTime.of(19, 0), "Madrid", "empleadoSofia", "gestionSofia", gestorSofia,
                                salasSofia);
                centroSofia.addEmpleado(empleado3);
                centroSofia.addEmpleado(empleado4);

                return centroSofia;
        }

        private static void configurarExposicionesYObra(CentroExposicion centro) {
                SalaExposicion salaExposicion = new SalaExposicion(centro.getSalas().iterator().next());
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
                salaExposicion.addObra(cuadro);
                exposicion1.addSala(salaExposicion);
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
