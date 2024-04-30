package GUI.modelo;

import GUI.modelo.centroExposicion.*;
import GUI.modelo.expofy.*;
import GUI.modelo.exposicion.*;
import GUI.modelo.obra.Obra;
import GUI.modelo.sala.Sala;
import GUI.modelo.utils.LectorCSVObras;

import java.time.*;
import java.util.HashSet;
import java.util.Set;

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
                Empleado empleado1 = new Empleado("455456", "PowerBazinga", "489", "423", true, false, false, "AnorLondo");


                CentroExposicion centroExposicion1 = new CentroExposicion("Centro1", LocalTime.of(10, 0, 0),
                                LocalTime.of(21, 0, 0), "Madrid",
                                "123", "456", gestor1, salas);

                centroExposicion1.loginGestor("123");
                centroExposicion1.addEmpleado(empleado1);

                expofy.addCentroExposicion(centroExposicion1);
                centroExposicion1.loginGestor("456");

                // Exposicion
                Set<SalaExposicion> salasExposicion = new HashSet<>();
                SalaExposicion salaExposicion1 = new SalaExposicion(sala1);
                salasExposicion.add(salaExposicion1);

                Exposicion exposicion1 = new Exposicion("Exposicion1", LocalDate.of(2025, 1, 2),
                                LocalDate.now().plusYears(7),
                                "Descripción", salasExposicion, TipoExpo.TEMPORAL, 10.0);
                Exposicion exposicion2 = new Exposicion("Exposicion2", LocalDate.of(2025, 1, 2),
                                LocalDate.now().plusYears(7),
                                "Descripción", salasExposicion, TipoExpo.TEMPORAL, 10.0);

                centroExposicion1.addExposicion(exposicion1);
                centroExposicion1.addExposicion(exposicion2);
                LectorCSVObras.leerObras(centroExposicion1);

                for (Obra obra : centroExposicion1.getObras()) {
                        salaExposicion1.addObra(obra);
                }

                exposicion1.expoPublicar();
                exposicion2.expoPublicar();
                expofy.enviarNotificacionUsuario(
                                "Hola qué tal, esto es una Notificacion de prueba a un cliente en concreto",
                                expofy.getClienteRegistrado("123"));
                expofy.enviarNotificacionUsuario("Hola qué tal, esto es una Notificacion de prueba a un cliente",
                                expofy.getClienteRegistrado("123"));
                expofy.persistirExpofy();
                System.out.println(expofy.toString());
        }
}
