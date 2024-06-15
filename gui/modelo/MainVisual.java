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

                configurarYPublicarExposiciones(centroExposicionPrado);

                configurarDescuentos(centroExposicionPrado);

                configurarActividades(centroExposicionPrado);

                enviarNotificaciones(expofy);
                expofy.persistirExpofy();
        }

        private static void registrarClientes(Expofy expofy) {
                expofy.registrarCliente("123", "Carlos123", true);
                expofy.registrarCliente("456", "Ana456", false);
        }

        private static CentroExposicion configurarCentroExposicionPrado() {
                Gestor gestorPrado = new Gestor("7451321659");
                Set<Sala> salasPrado = new HashSet<>();

                Sala salaRenacimiento = new Sala("Sala de Renacimiento", 200, 55, 22, true, 15, 25.0, 40.0);
                Sala salaModerna = new Sala("Sala Arte Moderno", 180, 60, 21, false, 12, 20.0, 35.0);
                Sala salaImpresionismo = new Sala("Sala de Impresionismo", 150, 45, 24, true, 20, 30.0, 50.0);

                salasPrado.add(salaRenacimiento);
                salasPrado.add(salaModerna);
                salasPrado.add(salaImpresionismo);

                Empleado empleado1 = new Empleado("555", "Laura", "789", "789456", "Madrid", true, true, true, true);
                Empleado empleado2 = new Empleado("666", "Jorge", "987", "987456", "Madrid", true, false, true, false);
                Empleado empleado3 = new Empleado("777", "Marta", "123", "123456", "Madrid", true, true, false, false);
                Empleado empleado4 = new Empleado("888", "Antonio", "321", "654321", "Madrid", true, false, true, true);

                CentroExposicion centroPrado = new CentroExposicion("Museo Nacional del Prado", LocalTime.of(9, 0),
                                LocalTime.of(20, 0), "Madrid", "empleadoPrado", "gestionPrado", gestorPrado,
                                salasPrado);

                centroPrado.addEmpleado(empleado1);
                centroPrado.addEmpleado(empleado2);
                centroPrado.addEmpleado(empleado3);
                centroPrado.addEmpleado(empleado4);

                return centroPrado;
        }

        private static CentroExposicion configurarCentroExposicionReinaSofia() {
                Gestor gestorSofia = new Gestor("4453519848");
                Set<Sala> salasSofia = new HashSet<>();
                Sala salaContemporaneo = new Sala("Sala Arte Contemporáneo", 160, 50, 23, true, 18, 28.0, 45.0);
                salasSofia.add(salaContemporaneo);

                CentroExposicion centroSofia = new CentroExposicion("Museo Reina Sofia", LocalTime.of(10, 0),
                                LocalTime.of(19, 0), "Madrid", "empleadoSofia", "gestionSofia", gestorSofia,
                                salasSofia);

                return centroSofia;
        }

        private static void configurarExposicionesYObra(CentroExposicion centro) {
                SalaExposicion salaExposicion = new SalaExposicion(centro.getSalas().iterator().next());

                Exposicion exposicion1 = new Exposicion("Van Gogh: Los Últimos Años", LocalDate.of(2024, 10, 1),
                                LocalDate.of(2024, 11, 1),
                                "Explora la intensa última década de Van Gogh con obras nunca antes vistas en nuestro país.",
                                TipoExpo.TEMPORAL, 20.0);

                centro.addExposicion(exposicion1);

                Cuadro cuadro = new Cuadro("Girasoles", 1888,
                                "Una de las series más famosas de Van Gogh.", true, 3000.0, "VG456",
                                92.1, 73.7, 30, 20, 70, 45, "Óleo sobre lienzo", "Vincent van Gogh");

                Cuadro cuadro2 = new Cuadro("La noche estrellada", 1889,
                                "Una de las obras más conocidas de Van Gogh.", true, 4000.0, "VG789",
                                73.7, 92.1, 45, 30, 70, 45, "Óleo sobre lienzo", "Vincent van Gogh");

                Audiovisual audiovisual = new Audiovisual("Van Gogh: Los Últimos Años", 2024,
                                "Documental sobre la última década de Van Gogh.", false, 500.0, "VG123",
                                "60", "ESP", "Alguien");

                centro.addObra(cuadro);
                centro.addObra(cuadro2);
                centro.addObra(audiovisual);

                salaExposicion.addObra(cuadro);
                salaExposicion.addObra(cuadro2);
                salaExposicion.addObra(audiovisual);

                exposicion1.addSala(salaExposicion);
        }

        private static void configurarYPublicarExposiciones(CentroExposicion centro) {
                for (Exposicion exposicion : centro.getExposiciones()) {
                        centro.confgiurarSorteoExposicion(exposicion, LocalDate.of(2024, 10, 5), 10);
                        exposicion.expoPublicar();
                }
        }

        private static void configurarDescuentos(CentroExposicion centro) {
                for (Exposicion exposicion : centro.getExposiciones()) {
                        exposicion.configurarDescuentoDia(20.00, 2);
                }
        }

        private static void configurarActividades(CentroExposicion centro) {
                centro.addActividad("Visita expo Van Gogh", TipoActividad.VISITA_GUIADA, "Visita guiada a la exposición 'Van Gogh: Los Últimos Años", 10,
                                LocalDate.of(2024, 10, 2), LocalTime.of(11, 0), centro.getSalas().iterator().next());
        }

        private static void enviarNotificaciones(Expofy expofy) {
                expofy.enviarNotificacionUsuario(
                                "Estimado Carlos le notificamos la próxima apertura de la exposición 'Van Gogh: Los Últimos Años'.",
                                expofy.getClienteRegistrado("123"));
                expofy.enviarNotificacionesClientesPublicidad(
                                "Descubre las nuevas exposiciones de arte moderno en el Museo Nacional del Prado. ¡Visítanos!");
        }
}
