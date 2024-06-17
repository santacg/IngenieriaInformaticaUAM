package test;

import gui.modelo.centroExposicion.*;
import gui.modelo.expofy.ClienteRegistrado;
import gui.modelo.expofy.Expofy;
import gui.modelo.expofy.Notificacion;
import gui.modelo.exposicion.*;
import gui.modelo.obra.Cuadro;
import gui.modelo.sala.Sala;
import gui.modelo.tarjetaDeCredito.TarjetaDeCredito;
import gui.modelo.usuario.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para la clase Expofy.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public class ExpofyTest {
    private Expofy expofy;

    @BeforeEach
    public void setUp() {
        Expofy.removeExpofy();
        expofy = Expofy.getInstance();
    }

    @Test
    public void testRemoveCentroExposicion() {
        Sala sala = new Sala("Sala de Prueba", 100, true, 10, 10.0, 10.0, 10.0);
        Set<Sala> salas = new HashSet<>();
        salas.add(sala);
        Gestor gestor = new Gestor("123");
        CentroExposicion centro = new CentroExposicion("Museo de Prueba", LocalTime.of(9, 0),
                LocalTime.of(18, 0),
                "Calle de Prueba, 123", "passwordEmpleado", "passwordGestor", gestor, salas);
        ;
        expofy.addCentroExposicion(centro);
        expofy.removeCentroExposicion(centro);
        assertFalse(expofy.getCentrosExposicion().contains(centro));
    }

    @Test
    public void testAddNotificacion() {
        Notificacion notificacion = new Notificacion("Mensaje de prueba", LocalDate.now());
        expofy.addNotificacion(notificacion);
        assertTrue(expofy.getNotificaciones().contains(notificacion));
    }

    @Test
    public void testRemoveNotificacion() {
        Notificacion notificacion = new Notificacion("Mensaje de prueba", LocalDate.now());
        expofy.addNotificacion(notificacion);
        expofy.removeNotificacion(notificacion);
        assertFalse(expofy.getNotificaciones().contains(notificacion));
    }

    @Test
    public void testRegistrarCliente() {
        assertTrue(expofy.registrarCliente("12345678A", "contraseña", true));
        ClienteRegistrado cliente = expofy.getClienteRegistrado("12345678A");
        assertNotNull(cliente);
        assertEquals("12345678A", cliente.getNIF());
        assertEquals("contraseña", cliente.getContrasenia());
        assertTrue(cliente.getPublicidad());
    }

    @Test
    public void testRegistrarClienteDuplicado() {
        assertTrue(expofy.registrarCliente("12345678A", "contraseña", true));
        assertFalse(expofy.registrarCliente("12345678A", "contraseña", true));
    }

    @Test
    public void testEliminarCliente() {
        expofy.registrarCliente("12345678A", "contraseña", true);
        expofy.eliminarCliente("12345678A");
        assertNull(expofy.getClienteRegistrado("12345678A"));
    }

    @Test
    public void testLoginCliente() {
        expofy.registrarCliente("12345678A", "contraseña", true);
        assertTrue(expofy.loginCliente("12345678A", "contraseña"));
        assertTrue(expofy.getClienteRegistrado("12345678A").isLoged());
    }

    @Test
    public void testLoginClienteFallo() {
        expofy.registrarCliente("12345678A", "contraseña", true);
        assertFalse(expofy.loginCliente("12345678A", "contraseñaIncorrecta"));
        assertFalse(expofy.getClienteRegistrado("12345678A").isLoged());
    }

    @Test
    public void testEnviarNotificacionClienteExito() {
        Empleado empleado = new Empleado("123456789X", "María García", "123456789", "1234567890", "Calle", true, true,
                true, true);
        empleado.logIn();
        expofy.registrarCliente("12345678A", "contraseña", true);
        assertTrue(expofy.enviarNotificacionCliente("Mensaje de prueba", "12345678A", empleado));
        assertEquals(1, expofy.getClienteRegistrado("12345678A").getNotificaciones().size());
    }

    @Test
    public void testEnviarNotificacionesClientesPublicidad() {
        expofy.registrarCliente("12345678A", "contraseña", true);
        expofy.registrarCliente("87654321B", "contraseña2", false);
        expofy.enviarNotificacionesClientesPublicidad("Mensaje de publicidad");
        assertEquals(1, expofy.getClienteRegistrado("12345678A").getNotificaciones().size());
        assertEquals(0, expofy.getClienteRegistrado("87654321B").getNotificaciones().size());
    }

    @Test
    public void testEnviarNotificacionUsuarios() {
        Usuario usuario1 = new ClienteRegistrado("12345678A", true, "contraseña", false, null, null);
        Usuario usuario2 = new ClienteRegistrado("87654321B", true, "contraseña2", false, null, null);
        Set<Usuario> usuarios = new HashSet<>();
        usuarios.add(usuario1);
        usuarios.add(usuario2);
        expofy.enviarNotificacionUsuarios("Mensaje de prueba", usuarios);
        assertEquals(1, ((ClienteRegistrado) usuario1).getNotificaciones().size());
        assertEquals(1, ((ClienteRegistrado) usuario2).getNotificaciones().size());
    }

    @Test
    public void testEnviarNotificacionAll() {
        expofy.registrarCliente("12345678A", "contraseña", true);
        expofy.registrarCliente("87654321B", "contraseña2", false);
        expofy.enviarNotificacionAll("Mensaje para todos");
        assertEquals(1, expofy.getClienteRegistrado("12345678A").getNotificaciones().size());
        assertEquals(1, expofy.getClienteRegistrado("87654321B").getNotificaciones().size());
    }

    @Test
    public void testComprarEntradaExito() {
        expofy.registrarCliente("12345678A", "contraseña", true);
        expofy.getClienteRegistrado("12345678A").logIn();

        Exposicion exposicion = new Exposicion("Exposicion 1", LocalDate.now(), LocalDate.now().plusDays(30),
                "Descripción",
                TipoExpo.TEMPORAL, 10.0);

        Sala sala = new Sala("Sala expo 1", 100, true, 10, 10.0, 10.0, 10.0);
        SalaExposicion salaExpo = new SalaExposicion(sala);

        Cuadro cuadro = new Cuadro("Mona Lisa", 1503, "Cuadro famoso", false, 1000000.0, "12345", 1.5, 1.2, 26, 20, 90,
                20,
                "óleo", "Leonardo da Vinci");

        salaExpo.addObra(cuadro);
        exposicion.addSala(salaExpo);

        try {
            exposicion.expoPublicar();
        } catch (Exception e) {
        }

        Hora horaPrueba = exposicion.getHora(LocalDate.now(), 15);
        TarjetaDeCredito tarjeta = new TarjetaDeCredito("1234123412341234", LocalDate.of(2025, 12, 31), 123);

        assertTrue(expofy.comprarEntrada(expofy.getClienteRegistrado("12345678A"), exposicion, LocalDate.now(),
                horaPrueba, 2, tarjeta));
        assertEquals(2, expofy.getClienteRegistrado("12345678A").getNotificaciones().size());
    }

}
