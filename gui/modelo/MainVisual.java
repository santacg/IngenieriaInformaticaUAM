package gui.modelo;

import java.time.*;
import java.util.HashSet;
import java.util.Set;

import gui.modelo.centroExposicion.*;
import gui.modelo.expofy.*;
import gui.modelo.exposicion.*;
import gui.modelo.obra.Cuadro;
import gui.modelo.obra.Obra;
import gui.modelo.sala.Sala;

/**
 * Clase MainVisual.
 * Esta clase se encarga de crear un entorno visual de la aplicación Expofy
 * estableciendo un centro de exposición con un gestor, un empleado, una sala,
 * una exposición y una obra. Además, se registrará un cliente y se enviarán
 * notificaciones a dicho cliente.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public class MainVisual {
        public static void main(String[] args) {
                Expofy expofy = Expofy.getInstance();

                // Cliente
                expofy.registrarCliente("123", "123", false);

                // Centro de exposicion
                // Gestor
                Gestor gestor1 = new Gestor("123");

                // Salas
                Set<Sala> salas = new HashSet<>();
                Sala sala1 = new Sala("Sala1", 100, 50, 25, true, 10, 15.0, 20.0);
                salas.add(sala1);
                sala1.addSubsala(7.0, 5.0, 4, 35);

                // Empleado
                Empleado empleado1 = new Empleado("455456", "PowerBazinga", "489", "423", "AnorLondo", true, false, false);

                CentroExposicion centroExposicion1 = new CentroExposicion("Centro1", LocalTime.of(10, 0, 0),
                                LocalTime.of(21, 0, 0), "Madrid",
                                "123", "456", gestor1, salas);

                centroExposicion1.loginGestor("456");
                centroExposicion1.addEmpleado(empleado1);

                expofy.addCentroExposicion(centroExposicion1);

                // Exposicion
                Set<SalaExposicion> salasExposicion = new HashSet<>();
                SalaExposicion salaExposicion1 = new SalaExposicion(sala1);
                salasExposicion.add(salaExposicion1);

                Exposicion exposicion1 = new Exposicion("Picasso 1960", LocalDate.of(2024, 1, 2),
                                LocalDate.now().plusYears(7),
                                "Vive en el prado esta espectacular experiencia recordando los últimos años de este gran artista ",
                                salasExposicion, TipoExpo.TEMPORAL, 15.0);
                Exposicion exposicion2 = new Exposicion("Exposicion2", LocalDate.of(2025, 1, 2),
                                LocalDate.now().plusYears(7),
                                "Descripción", salasExposicion, TipoExpo.TEMPORAL, 10.0);

                centroExposicion1.addExposicion(exposicion1);
                centroExposicion1.addExposicion(exposicion2);

                Cuadro cuadro = new Cuadro(
                                "La noche estrellada",
                                1889,
                                "Representación del paisaje visto desde la ventana del sanatorio donde estaba internado",
                                false,
                                2000.0,
                                "456",
                                73.7,
                                92.1,
                                25,
                                15,
                                60,
                                40,
                                "Óleo sobre lienzo",
                                "Vincent van Gogh");

                centroExposicion1.addObra(cuadro);

                for (Obra obra : centroExposicion1.getObras()) {
                        salaExposicion1.addObra(obra);
                }

                centroExposicion1.confgiurarSorteoExposicion(exposicion1, LocalDate.of(2025, 5, 2), 2);

                exposicion1.expoPublicar();
                exposicion2.expoPublicar();
                expofy.enviarNotificacionUsuario(
                                "Hola qué tal, esto es una Notificacion de prueba a un cliente en concreto",
                                expofy.getClienteRegistrado("123"));
                expofy.enviarNotificacionesClientesPublicidad(
                                "Hola qué tal, esto es una Notificacion de prueba para cualquier cliente con la publicidad activada");
                expofy.persistirExpofy();
                System.out.println(expofy.toString());
        }
}
