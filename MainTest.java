import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import javax.swing.text.AbstractDocument.LeafElement;

import CentroExposicion.*;
import Entrada.Entrada;
import Expofy.*;
import Exposicion.*;
import Inscripcion.Inscripcion;
import Obra.*;
import Sala.*;
import TarjetaDeCredito.TarjetaDeCredito;
import Usuario.Usuario;
import Utils.LectorCSVObras;

public class MainTest {
   public static void main(String[] args) {
      Expofy expofy = Expofy.getInstance();

      Set<Empleado> empleados = new HashSet<>();
      Empleado empleado = new Empleado("12345", "Jesus", "789", "456", true, true, true, "Mordor");
      empleados.add(empleado);
      Gestor gestor = new Gestor("456123");

      Set<Sala> salas = new HashSet<>();
      Sala sala = new SalaCompuesta("Sala1", 100, 50.0, 25.0, true, 10, 10.0, 10.0);
      salas.add(sala);
      CentroExposicion centroExposicion = new CentroExposicion("Centro1", LocalTime.now(), LocalTime.now(), "Centro1",
            "123", "456", empleados, gestor, salas);
      expofy.addCentroExposicion(centroExposicion);

      Set<Notificacion> notificaciones = new HashSet<>();
      Notificacion notificacion = new Notificacion("Hola", LocalDate.now());
      expofy.addNotificacion(notificacion);

      Set<ClienteRegistrado> clienteRegistrados = new HashSet<>();
      ClienteRegistrado clienteRegistrado = new ClienteRegistrado("123456789", false, "123", false, LocalDate.now(),
            LocalDate.now());
      expofy.addClienteRegistrado(clienteRegistrado);

      Set<SalaExposicion> salasExposicion = new HashSet<>();
      SalaExposicion salaExposicion = new SalaExposicion("SalaExpo1", 100, 50.0, 25.0, true, 10, 10.0, 10.0);
      salasExposicion.add(salaExposicion);
      Cuadro cuadro = new Cuadro(
            "El Guernica", 
            1937, 
            "Una pintura de Picasso", 
            false, 
            2000000.0, 
            "123456789", 
            Estado.ALMACENADA,
            349.3,
            776.6, 
            25,
            15, 
            60, 
            40, 
            "Óleo" 
      );
      salaExposicion.addObra(cuadro);
      Exposicion exposicion = new Exposicion("Expo1", LocalDate.now(), LocalDate.now(), "Expo1", salasExposicion,
            TipoExpo.PERMANENTE);
      centroExposicion.addExposicion(exposicion);

      LectorCSVObras.leerObras(salaExposicion);
      System.out.println(expofy.toString());
   }
}
