package Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import GUI.modelo.expofy.Notificacion;

import java.time.LocalDate;

public class NotificacionTest {
    private Notificacion notificacion;

    @BeforeEach
    public void setUp() {
        notificacion = new Notificacion("Mensaje de prueba", LocalDate.now());
    }

    @Test
    public void testGetMensaje() {
        assertEquals("Mensaje de prueba", notificacion.getMensaje());
    }

    @Test
    public void testIsLeida() {
        assertFalse(notificacion.isLeida());
    }

    @Test
    public void testSetLeida() {
        notificacion.setLeida(true);
        assertTrue(notificacion.isLeida());
    }

    @Test
    public void testSetMensaje() {
        notificacion.setMensaje("Nuevo mensaje");
        assertEquals("Nuevo mensaje", notificacion.getMensaje());
    }

    @Test
    public void testSetFecha() {
        LocalDate newFecha = LocalDate.of(2022, 1, 1);
        notificacion.setFecha(newFecha);
        assertEquals(newFecha, notificacion.getFecha());
    }

}