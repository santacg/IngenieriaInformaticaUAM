package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import src.expofy.ClienteRegistrado;
import src.inscripcion.Inscripcion;

public class InscripcionTest {
    private Inscripcion inscripcion;
    private ClienteRegistrado cliente;

    @BeforeEach
    public void setUp() {
        cliente = new ClienteRegistrado("123456", false, "123", false, LocalDate.now(), null);
        inscripcion = new Inscripcion(2, cliente);
    }

    @Test
    public void testGetnEntradas() {
        assertEquals(1, inscripcion.getnEntradas());
    }

    @Test
    public void testSetnEntradas() {
        inscripcion.setnEntradas(3);
        assertEquals(3, inscripcion.getnEntradas());
    }

    @Test
    public void testGetCliente() {
        assertEquals(cliente, inscripcion.getCliente());
    }

    @Test
    public void testSetCliente() {
        ClienteRegistrado newCliente = new ClienteRegistrado("456123", true, "7898456", false, null, null);
        inscripcion.setCliente(newCliente);
        assertEquals(newCliente, inscripcion.getCliente());
    }

    @Test
    public void testGetCodigos() {
        assertTrue(inscripcion.getCodigos().isEmpty());
    }

    @Test
    public void testAddCodigo_ValidLength() {
        inscripcion.addCodigo("ABCD");
        assertTrue(inscripcion.getCodigos().contains("ABCD"));
    }

    @Test
    public void testAddCodigo_InvalidLength() {
        inscripcion.addCodigo("ABCDE");
        assertFalse(inscripcion.getCodigos().contains("ABCDE"));
    }

    @Test
    public void testRemoveCodigo() {
        inscripcion.addCodigo("ABCD");
        inscripcion.removeCodigo("ABCD");
        assertFalse(inscripcion.getCodigos().contains("ABCD"));
    }
}