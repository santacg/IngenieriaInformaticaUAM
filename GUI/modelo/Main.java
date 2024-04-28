package GUI.modelo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
import java.io.File;

import GUI.modelo.centroExposicion.*;
import GUI.modelo.expofy.*;
import GUI.modelo.exposicion.*;
import GUI.modelo.inscripcion.Inscripcion;
import GUI.modelo.obra.*;
import GUI.modelo.sala.*;
import GUI.modelo.tarjetaDeCredito.TarjetaDeCredito;
import GUI.modelo.utils.LectorCSVObras;
import es.uam.eps.padsof.tickets.*;
import es.uam.eps.padsof.tickets.UnsupportedImageTypeException;
import es.uam.eps.padsof.tickets.NonExistentFileException;
import es.uam.eps.padsof.tickets.TicketSystem;
import es.uam.eps.padsof.tickets.UnsupportedImageTypeException;

/**
 * Clase MainTest.
 * Clase principal para demostrar la funcionalidad del sistema Expofy.
 * En este ejemplo, se instancian componentes principales del sistema como
 * centros de exposición, empleados, gestores, y salas.
 * Se configuran clientes y notificaciones, se configuran y se añaden
 * exposiciones a un centro, y se demuestra la carga de obras desde un archivo
 * CSV a una sala de exposición específica.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public class Main {
      public static void main(String[] args) throws NonExistentFileException, UnsupportedImageTypeException {
            // Instanciamos el sistema
            Expofy expofy = Expofy.getInstance();

            // Instanciamos varios atributos que componen el sistema

            // Clientes
            // Registro y login de clientes
            expofy.registrarCliente("123456789", "123", true);
            expofy.loginCliente("123456789", "123");
            expofy.registrarCliente("784512", "456", true);
            expofy.loginCliente("784512", "456");

            // Instanciación de centroExposición y sus atributos
            // Centro1

            // Empleados
            Empleado empleado1 = new Empleado("123485", "Jesus", "789", "456", true, true, true, "Mordor");
            Empleado empleado2 = new Empleado("789456", "Miguel", "456", "789", false, false, false, "Rivendell");
            Empleado empleado3 = new Empleado("455456", "PowerBazinga", "489", "423", false, false, false, "AnorLondo");

            // Gestor
            Gestor gestor1 = new Gestor("456123");
            Gestor gestor2 = new Gestor("216548");
            
            // Salas
            // Centro1
            Set<Sala> salas1 = new HashSet<>();
            Sala sala1 = new Sala("Sala1", 100, 50, 25, true, 10, 15.0, 20.0);
            Sala sala2 = new Sala("Sala2", 25, 30, 30, false, 2, 10.0, 15.0);
            salas1.add(sala1);
            salas1.add(sala2);

            // Centro2
            Set<Sala> salas2 = new HashSet<>();
            Sala sala3 = new Sala("Sala2", 10, 50, 25, true, 10, 5.0, 20.0);
            salas2.add(sala3);

            // Centros
            // Centro1
            CentroExposicion centroExposicion1 = new CentroExposicion("Centro1", LocalTime.of(10, 0, 0),
                        LocalTime.of(21, 0, 0), "Madrid",
                        "123", "456", gestor1, salas1);
            centroExposicion1.addEmpleado(empleado1);
            centroExposicion1.addEmpleado(empleado2);
            expofy.addCentroExposicion(centroExposicion1);

            // Centro2
            CentroExposicion centroExposicion2 = new CentroExposicion("Centro2", LocalTime.of(10, 0, 0),
                        LocalTime.of(21, 0, 0), "Barcelona",
                        "789", "456", gestor2, salas2);
            centroExposicion2.addEmpleado(empleado3);
            expofy.addCentroExposicion(centroExposicion2);

            // Login empleados y gestores
            centroExposicion1.loginEmpleado("123485", "123");
            centroExposicion1.loginEmpleado("789456", "123");
            centroExposicion2.loginEmpleado("455456", "789");
            centroExposicion1.loginGestor("456123");
            centroExposicion2.loginGestor("216548");

            // Empleado edita su perfil
            
            // Notificaciones
            // Enviamos una notificación a todos los usuarios
            expofy.enviarNotificacionAll("Bienvenidos a expofy");
            // Envio de notificaciones a un usuario en concreto por parte de los empleados
            // que tengan capacidad de realizar la accion
            expofy.enviarNotificacionCliente("Bienvenido al centro 1", "123456789", empleado1);
            // Recibo de notificaciones por parte de los usuarios
            ClienteRegistrado cliente = expofy.getClienteRegistrado("123456789");
            for (Notificacion notificacion : cliente.getNotificaciones()) {
                  cliente.getMensajeNotificacion(notificacion);
            }

            // Configuramos exposiciones
            Set<SalaExposicion> salasExposicion1 = new HashSet<>();
            Set<SalaExposicion> salasExposicion2 = new HashSet<>();

            SalaExposicion salaExposicion1 = new SalaExposicion(sala1);
            SalaExposicion salaExposicion2 = new SalaExposicion(sala2);
            salasExposicion1.add(salaExposicion1);
            salasExposicion2.add(salaExposicion2);
            Exposicion exposicion1 = new Exposicion("Expo1", LocalDate.of(2025, 1, 1), LocalDate.now().plusYears(1),
                        "Expo1", salasExposicion1, TipoExpo.PERMANENTE, 21.0);
            Exposicion exposicion2 = new Exposicion("Expo2", LocalDate.of(2025, 2, 2), LocalDate.now().plusYears(1),
                        "Expo2", salasExposicion2, TipoExpo.TEMPORAL, 22.0);

            // Añadimos obras a una de las exposiciones
            Cuadro cuadro = new Cuadro(
                        "El Guernica",
                        1937,
                        "Una pintura de Picasso",
                        false,
                        2000000.0,
                        "123456789",
                        349.3,
                        776.6,
                        25,
                        15,
                        60,
                        40,
                        "Óleo",
                        "Kojima, Hideo");        
            salaExposicion1.addObra(cuadro);

            // Leemos obras de un archivo y lo añadimos a una sala de una de las
            // exposiciones
            LectorCSVObras.leerObras(centroExposicion1);

            // Añadimos las exposiciones al centro
            centroExposicion1.addExposicion(exposicion1);
            centroExposicion2.addExposicion(exposicion2);

            // Cliente registrado desactiva recepción de publicidad
            cliente.setPublicidad(false);

            for (Obra obra: centroExposicion1.getObras()) {
                  salaExposicion2.addObra(obra);
            }
            // Publicación de exposiciones
            exposicion1.expoPublicar();
            exposicion2.expoPublicar();

            // Cliente registrado participa en un sorteo
            Sorteo sorteo = new SorteoExpo(exposicion1, LocalDate.now().plusDays(5), 2);
            expofy.getClienteRegistrado("123456789").inscribirse(sorteo, 2);

            // Empleado vende entradas para el mismo día
            // centroExposicion1.venderEntrada(exposicion2, new Hora(LocalDate.now(), LocalTime.of(9,0,0), LocalTime.of(10,0,0), 10, 25.00), 3);

            // Gestor cambia el estado de una obra
            cuadro.restaurarObra();

            // Gestor configura la contraseña de los empleados y la penalización de sorteos
            centroExposicion1.setContraseniaEmpleado("123654");
            centroExposicion1.setSancion(10);

            // Gestor asigna un descuento a una exposicion
            exposicion1.configurarDescuentoDia(15, 20);     
            // Gestor realiza un sorteo una vez finalizado el periodo de inscripcion
            sorteo.realizarSorteo();
            // Obtener código ganador Sorteo
            String codigo_ganador = null;
            ClienteRegistrado ganador = null;
            for (Inscripcion inscripcion: sorteo.getInscripciones()) {
                  for (String codigo : inscripcion.getCodigos()) {
                        if (codigo != null){
                              ganador = inscripcion.getCliente();
                              codigo_ganador = codigo;
                              break;}
                  }

            }
            // Cliente registrado compra entrada con código de Sorteo
            boolean resultadocompra = expofy.comprarEntrada(ganador, exposicion1, LocalDate.of(2021,1,2), 
            new Hora(LocalDate.of(2021,1,2), LocalTime.of(9,0,0), 
            LocalTime.of(10,0,0), 10, 25.00), 3, new TarjetaDeCredito("1234123412341234", LocalDate.of(2050,5,5), 243), codigo_ganador);

            System.out.println("Resultado de la compra: " + resultadocompra);
            
            Ticket ticket = new Ticket(exposicion1, 5.0, 2, LocalDate.of(2050,5,5), new Hora(LocalDate.of(2021,1,2), LocalTime.of(9,0,0), 
            LocalTime.of(10,0,0), 10, 25.00));
            System.out.println(ticket.dataTicket());
            TicketSystem.createTicket(ticket, "." + File.separator + "tmp");

            // Gestor divide salas
            sala1.addSubsala(5.0, 7.0, 2, 20);
            sala1.addSubsala(3.0, 5.0, 3, 10);

            // Gestor cambia el horario
            centroExposicion1.setHoraApertura(LocalTime.of(9, 0, 0));
            centroExposicion2.setHoraCierre(LocalTime.of(22, 0, 0));

            // Cliente registrado busca exposiciones con filtros
            for (Exposicion e : centroExposicion1.getExposicionesPorFecha(LocalDate.of(2021, 4, 4), LocalDate.now().plusMonths(2))) {
                  System.out.println(e.toString());
            }
            for (Exposicion e: centroExposicion1.getExposicionesPermanentes()) {
                  System.out.println(e.toString());
            }
            for (Exposicion e: centroExposicion1.getExposicionesTemporales()) {
                  System.out.println(e.toString());
            }
            for (Exposicion e : centroExposicion1.getExposicionesPorTipoObra(Cuadro.class)) {
                  System.out.println(e.toString());
            }


            System.out.println(expofy.toString());
            expofy.persistirExpofy();
      }
}
