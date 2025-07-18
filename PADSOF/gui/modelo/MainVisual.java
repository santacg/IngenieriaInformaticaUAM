package gui.modelo;

import java.time.*;
import java.util.*;

import gui.modelo.centroExposicion.*;
import gui.modelo.expofy.*;
import gui.modelo.exposicion.*;
import gui.modelo.obra.*;
import gui.modelo.sala.*;
import gui.modelo.utils.ExcepcionMensaje;

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

                Sala salaRenacimiento = new Sala("Sala de Renacimiento", 200, 55, 22, true, 15, 25.0, 40.0, 6.0);
                Sala salaModerna = new Sala("Sala Arte Moderno", 180, 60, 21, true, 12, 20.0, 35.0, 5.0);
                Sala salaImpresionismo = new Sala("Sala de Impresionismo", 150, 45, 24, true, 20, 30.0, 50.0, 8.0);

                salasPrado.add(salaRenacimiento);
                salasPrado.add(salaModerna);
                salasPrado.add(salaImpresionismo);

                Empleado empleado1 = new Empleado("555", "Laura", "789", "789456", "Madrid", true, true, true, true);
                Empleado empleado2 = new Empleado("666", "Jorge", "987", "987456", "Madrid", true, true, true, false);
                Empleado empleado3 = new Empleado("777", "Marta", "123", "123456", "Madrid", true, true, false, false);
                Empleado empleado4 = new Empleado("888", "Antonio", "321", "654321", "Madrid", true, false, false,
                                false);
                Empleado empleado5 = new Empleado("999", "Sara", "654", "654987", "Madrid", false, false, false, false);

                CentroExposicion centroPrado = new CentroExposicion("Museo Nacional del Prado", LocalTime.of(9, 0),
                                LocalTime.of(20, 0), "Madrid", "empleadoPrado", "gestionPrado", gestorPrado,
                                salasPrado);

                centroPrado.addEmpleado(empleado1);
                centroPrado.addEmpleado(empleado2);
                centroPrado.addEmpleado(empleado3);
                centroPrado.addEmpleado(empleado4);
                centroPrado.addEmpleado(empleado5);

                return centroPrado;
        }

        private static CentroExposicion configurarCentroExposicionReinaSofia() {
                Gestor gestorSofia = new Gestor("4453519848");
                Set<Sala> salasSofia = new HashSet<>();
                Sala salaContemporaneo = new Sala("Sala Arte Contemporáneo", 160, 50, 23, true, 18, 28.0, 45.0, 6.0);
                salasSofia.add(salaContemporaneo);

                CentroExposicion centroSofia = new CentroExposicion("Museo Reina Sofia", LocalTime.of(10, 0),
                                LocalTime.of(19, 0), "Madrid", "empleadoSofia", "gestionSofia", gestorSofia,
                                salasSofia);

                return centroSofia;
        }

        private static void configurarExposicionesYObra(CentroExposicion centro) {
                SalaExposicion salaExposicionRenacimiento = null;
                SalaExposicion salaExposicionImpresionismo = null;
                SalaExposicion salaExposicionModerno = null;

                for (Sala sala : centro.getSalas()) {
                        if (sala.getNombre().equals("Sala de Renacimiento")) {
                                salaExposicionRenacimiento = new SalaExposicion(sala);
                        } else if (sala.getNombre().equals("Sala de Impresionismo")) {
                                salaExposicionImpresionismo = new SalaExposicion(sala);
                        } else if (sala.getNombre().equals("Sala Arte Moderno")) {
                                salaExposicionModerno = new SalaExposicion(sala);
                        }
                }

                Exposicion exposicion1 = new Exposicion("Van Gogh: Los Últimos Años", LocalDate.now(),
                                LocalDate.now().plusDays(30),
                                "Explora la intensa última década de Van Gogh con obras nunca antes vistas en nuestro país.",
                                TipoExpo.TEMPORAL, 20.0);

                Exposicion exposicion2 = new Exposicion("Clásicos del Impresionismo", LocalDate.now(),
                                LocalDate.now().plusDays(10),
                                "Una colección selecta de las obras más representativas del impresionismo.",
                                TipoExpo.TEMPORAL, 15.0);

                Exposicion exposicion3 = new Exposicion("Arte Moderno", LocalDate.now(), null,
                                "Una exposición de arte moderno con obras de los artistas más influyentes del siglo XX.",
                                TipoExpo.PERMANENTE, 10.0);

                centro.addExposicion(exposicion1);
                centro.addExposicion(exposicion2);
                centro.addExposicion(exposicion3);

                Cuadro cuadro = new Cuadro("Girasoles", 1888,
                                "Una de las series más famosas de Van Gogh.", true, 3000.0, "VG456",
                                92.1, 73.7, 30, 20, 70, 45, "Óleo sobre lienzo", "Vincent van Gogh");

                Cuadro cuadro2 = new Cuadro("La noche estrellada", 1889,
                                "Una de las obras más conocidas de Van Gogh.", true, 4000.0, "VG789",
                                73.7, 92.1, 45, 30, 70, 45, "Óleo sobre lienzo", "Vincent van Gogh");

                Audiovisual audiovisual = new Audiovisual("Van Gogh: Los Últimos Años", 2024,
                                "Documental sobre la última década de Van Gogh.", false, 500.0, "VG123",
                                "60", "ESP", "Alguien");

                Cuadro cuadro3 = new Cuadro("Impresión, sol naciente", 1872,
                                "La obra que dio nombre al impresionismo.", true, 2000.0, "IM456",
                                48.0, 63.0, 30, 20, 70, 45, "Óleo sobre lienzo", "Claude Monet");

                Cuadro cuadro4 = new Cuadro("Mujer con sombrilla", 1875, "Una de las obras más conocidas de Monet.",
                                true, 2500.0, "IM789", 100.0, 81.0, 45, 30, 70, 45, "Óleo sobre lienzo",
                                "Claude Monet");

                Audiovisual audiovisual2 = new Audiovisual("Impresionismo documental", 2024,
                                "Documental sobre el impresionismo.",
                                false, 200.0, "IM123", "60", "ESP", "Alguien2");

                Escultura escultura = new Escultura("La danza", 1881, "Escultura impresionista.", true, 10000.0,
                                "DE456",
                                2.0, 0.5, 0.5, 22, 10, 65, 35, "Bronce", "Jean-Baptiste Carpeaux");

                Cuadro cuadro5 = new Cuadro("La persistencia de la memoria", 1931, "Obra de Dalí", false, 5000.0,
                                "DM456", 24.1, 33.0, 30, 20, 70, 45, "Óleo sobre lienzo", "Salvador Dalí");

                Fotografia fotografia = new Fotografia("Migrant Mother", 1936, "Fotografía de Dorothea Lange", false,
                                1000.0, "FL456", 10.0, 8.0, 30, 20, 70, 45, false, "Dorothea Lange");

                centro.addObra(cuadro);
                centro.addObra(cuadro2);
                centro.addObra(audiovisual);
                centro.addObra(cuadro3);
                centro.addObra(cuadro4);
                centro.addObra(audiovisual2);
                centro.addObra(escultura);
                centro.addObra(cuadro5);
                centro.addObra(fotografia);

                salaExposicionRenacimiento.addObra(cuadro);
                salaExposicionRenacimiento.addObra(cuadro2);
                salaExposicionRenacimiento.addObra(audiovisual);

                exposicion1.addSala(salaExposicionRenacimiento);

                salaExposicionImpresionismo.addObra(cuadro3);
                salaExposicionImpresionismo.addObra(cuadro4);
                salaExposicionImpresionismo.addObra(audiovisual2);
                salaExposicionImpresionismo.addObra(escultura);

                exposicion2.addSala(salaExposicionImpresionismo);

                salaExposicionModerno.addObra(cuadro5);
                salaExposicionModerno.addObra(fotografia);

                exposicion3.addSala(salaExposicionModerno);
        }

        private static void configurarYPublicarExposiciones(CentroExposicion centro) {
                for (Exposicion exposicion : centro.getExposiciones()) {
                        centro.confgiurarSorteoExposicion(exposicion, LocalDate.now().plusDays(4), 10);
                        try {
                                exposicion.expoPublicar();
                        } catch (ExcepcionMensaje e) {
                                System.out.println(e.getMessage());
                        }
                }
        }

        private static void configurarDescuentos(CentroExposicion centro) {
                for (Exposicion exposicion : centro.getExposiciones()) {
                        exposicion.configurarDescuentoDia(20.00, 2);
                }
        }

        private static void configurarActividades(CentroExposicion centro) {
                try {
                        centro.addActividad("Visita expo Van Gogh", TipoActividad.VISITA_GUIADA,
                                        "Visita guiada a la exposición 'Van Gogh: Los Últimos Años", 10,
                                        LocalDate.of(2024, 10, 2), LocalTime.of(11, 0),
                                        centro.getSalas().iterator().next());
                } catch (ExcepcionMensaje e) {
                        System.out.println(e.getMessage());
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
