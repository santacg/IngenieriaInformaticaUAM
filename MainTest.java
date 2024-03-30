import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import CentroExposicion.*;
import Entrada.Entrada;
import Expofy.*;
import Exposicion.*;
import Inscripcion.Inscripcion;
import Obra.*;
import Sala.*;
import TarjetaDeCredito.TarjetaDeCredito;
import Usuario.Usuario;

public class MainTest {
   public static void main(String[] args) {
    Expofy expofy = Expofy.getInstance();

    Set<Empleado> empleados = new HashSet<>();
    Empleado empleado = new Empleado("12345", "Jesus", "789", "456", true, true, true, "Mordor");
    empleados.add(empleado);
    Gestor gestor = new Gestor("456123");

    CentroExposicion centroExposicion = new CentroExposicion(1, "MiauCenter", LocalTime.of(10, 0, 0), LocalTime.of(21, 0, 0, 0), "Madrid", "123", "321", null, empleados, gestor);
    expofy.addCentroExposicion(centroExposicion);

    Set<Notificacion> notificaciones = new HashSet<>();  
    Notificacion notificacion = new Notificacion("Hola", LocalDate.now());
    expofy.addNotificacion(notificacion);
    
    Set<ClienteRegistrado> clienteRegistrados = new HashSet<>();
    ClienteRegistrado clienteRegistrado = new ClienteRegistrado("123456789", false, "123", false, LocalDate.now(), LocalDate.now());
    expofy.addClienteRegistrado(clienteRegistrado);

    System.out.println(expofy.toString());
   }
}
