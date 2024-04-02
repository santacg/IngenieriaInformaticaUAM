package Expofy;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Clase NotificacionTest.
 * Esta clase contiene pruebas unitarias que validan la funcionalidad de los
 * métodos de la clase {@link Notificacion}.
 *
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public class NotificacionTest {
    private Notificacion notificacion;

    /**
     * Inicializa una nueva notificación antes de cada prueba.
     * Crea una notificación con un mensaje de prueba y la fecha actual.
     */
    @BeforeEach
    public void setUp() {
        String mensaje = "Test mensaje";
        LocalDate fecha = LocalDate.now();
        notificacion = new Notificacion(mensaje, fecha);
    }

    /**
     * Verifica que el mensaje obtenido de la notificación sea el esperado.
     * Asegura que el método {@code getMensaje()} retorna correctamente el mensaje
     * inicializado en {@code setUp()}.
     */
    @Test
    public void testGetMensaje() {
        String expected = "Test mensaje";
        String actual = notificacion.getMensaje();
        assertEquals(expected, actual);
    }

    /**
     * Comprueba si una notificación nueva es considerada como no leída por defecto.
     * Confirma que el método {@code isLeida()} retorna {@code false} para una nueva
     * notificación.
     */
    @Test
    public void testIsLeida() {
        boolean expected = false;
        boolean actual = notificacion.isLeida();
        assertEquals(expected, actual);
    }

    /**
     * Prueba que una notificación pueda ser marcada como leída.
     * Valida que después de llamar a {@code setLeida()}, el método
     * {@code isLeida()} retorna {@code true}.
     */
    @Test
    public void testSetLeida() {
        notificacion.setLeida(true);
        boolean actual = notificacion.isLeida();
        assertTrue(actual);
    }

    /**
     * Verifica que el mensaje de la notificación pueda ser actualizado
     * correctamente. Comprueba que el método {@code setMensaje()} actualiza el
     * mensaje de la notificación.
     */
    @Test
    public void testSetMensaje() {
        String newMensaje = "New test mensaje";
        notificacion.setMensaje(newMensaje);
        String actual = notificacion.getMensaje();
        assertEquals(newMensaje, actual);
    }

    /**
     * Comprueba que la fecha obtenida de la notificación sea la esperada.
     * Asegura que el método {@code getFecha()} retorna la fecha inicializada en
     * {@code setUp()}.
     */
    @Test
    public void testGetFecha() {
        LocalDate expected = LocalDate.now();
        LocalDate actual = notificacion.getFecha();
        assertEquals(expected, actual);
    }

    /**
     * Prueba la actualización de la fecha en la notificación.
     * Verifica que el método {@code setFecha()} cambie correctamente la fecha de la
     * notificación.
     */
    @Test
    public void testSetFecha() {
        LocalDate newFecha = LocalDate.of(2022, 1, 1);
        notificacion.setFecha(newFecha);
        LocalDate actual = notificacion.getFecha();
        assertEquals(newFecha, actual);
    }
}