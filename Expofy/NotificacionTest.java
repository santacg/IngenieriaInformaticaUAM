package Expofy;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NotificacionTest {
    private Notificacion notificacion;

    @BeforeEach
    public void setUp() {
        String mensaje = "Test mensaje";
        LocalDate fecha = LocalDate.now();
        notificacion = new Notificacion(mensaje, fecha);
    }

    @Test
    public void testGetMensaje() {
        String expected = "Test mensaje";
        String actual = notificacion.getMensaje();
        assertEquals(expected, actual);
    }

    @Test
    public void testIsLeida() {
        boolean expected = false;
        boolean actual = notificacion.isLeida();
        assertEquals(expected, actual);
    }

    @Test
    public void testSetLeida() {
        notificacion.setLeida(true);
        boolean actual = notificacion.isLeida();
        assertTrue(actual);
    }

    @Test
    public void testSetMensaje() {
        String newMensaje = "New test mensaje";
        notificacion.setMensaje(newMensaje);
        String actual = notificacion.getMensaje();
        assertEquals(newMensaje, actual);
    }

    @Test
    public void testGetFecha() {
        LocalDate expected = LocalDate.now();
        LocalDate actual = notificacion.getFecha();
        assertEquals(expected, actual);
    }

    @Test
    public void testSetFecha() {
        LocalDate newFecha = LocalDate.of(2022, 1, 1);
        notificacion.setFecha(newFecha);
        LocalDate actual = notificacion.getFecha();
        assertEquals(newFecha, actual);
    }
}