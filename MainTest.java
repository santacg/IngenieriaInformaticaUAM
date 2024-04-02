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
      // Instanciamos el sistema 
      Expofy expofy = Expofy.getInstance();

      // Instanciamos varios atributos que componen el sistema

      // Instanciación de centroExposición y sus atributos 
      // Centro1

      // Empleados
      Set<Empleado> empleados = new HashSet<>();
      Empleado empleado1 = new Empleado("12345", "Jesus", "789", "456", true, true, true, "Mordor");
      Empleado empleado2 = new Empleado("789456", "Miguel", "456", "789", false, false, false, "Rivendell");
      empleados.add(empleado1);
      empleados.add(empleado2);

      // Gestor
      Gestor gestor = new Gestor("456123");

      // Salas
      Set<Sala> salas = new HashSet<>();
      Sala sala1 = new SalaCompuesta("Sala1", 100, 50, 25, true, 10, 10.0, 10.0);

      SalaCompuesta sala2 = new SalaCompuesta("Sala2", 50, 30, 20, true, 4, 25.0, 30.0);

      Sala subsala1 = new SalaCompuesta("subsala1", 25, 30, 20, true, 2, 12.5, 15.0);
      Sala subsala2 = new SalaCompuesta("subsala2", 25, 30, 20, true, 2, 12.5, 15.0);
      sala2.addSala(subsala1);
      sala2.addSala(subsala2);      

      salas.add(sala1);
      salas.add(sala2);


      CentroExposicion centroExposicion = new CentroExposicion("Centro1", LocalTime.of(10, 0, 0), LocalTime.of(21, 0, 0), "Madrid",
            "123", "456", empleados, gestor, salas);
      expofy.addCentroExposicion(centroExposicion);

      // Clientes
      expofy.registrarCliente("123456789", "123", false);
      expofy.loginCliente("123456879", "123");

      // Notificaciones
      // Enviamos una notificación a todos los usuarios
      expofy.enviarNotificacionAll("Bienvenidos a expofy");

      // Configuramos exposiciones
      // Elegimos sala física del centro como sala para la exposición
      System.out.println(centroExposicion.getSalas());

      
      SalaExposicion salaExposicion = new SalaExposicion(sala1);
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
