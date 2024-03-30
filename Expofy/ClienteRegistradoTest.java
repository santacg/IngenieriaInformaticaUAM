package Expofy;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ClienteRegistradoTest {
    private ClienteRegistrado cliente;

    @BeforeEach
    public void setUp() {
        String NIF = "123456789";
        Boolean publicidad = true;
        String contrasenia = "password";
        Boolean sancionado = false;
        LocalDate ultimaCompra = LocalDate.now();
        LocalDate sancionadoHasta = null;
        cliente = new ClienteRegistrado(NIF, publicidad, contrasenia, sancionado, ultimaCompra, sancionadoHasta);
    }

    @Test
    public void testGetPublicidad() {
        Boolean expected = true;
        Boolean actual = cliente.getPublicidad();
        assertEquals(expected, actual);
    }

    @Test
    public void testSetPublicidad() {
        Boolean newPublicidad = false;
        cliente.setPublicidad(newPublicidad);
        Boolean actual = cliente.getPublicidad();
        assertEquals(newPublicidad, actual);
    }

    @Test
    public void testGetContrasenia() {
        String expected = "password";
        String actual = cliente.getContrasenia();
        assertEquals(expected, actual);
    }

    @Test
    public void testSetContrasenia() {
        String newContrasenia = "newpassword";
        cliente.setContrasenia(newContrasenia);
        String actual = cliente.getContrasenia();
        assertEquals(newContrasenia, actual);
    }

    @Test
    public void testGetSancionado() {
        Boolean expected = false;
        Boolean actual = cliente.getSancionado();
        assertEquals(expected, actual);
    }

    @Test
    public void testSetSancionado() {
        Boolean newSancionado = true;
        cliente.setSancionado(newSancionado);
        Boolean actual = cliente.getSancionado();
        assertEquals(newSancionado, actual);
    }

    @Test
    public void testGetUltimaCompra() {
        LocalDate expected = LocalDate.now();
        LocalDate actual = cliente.getUltimaCompra();
        assertEquals(expected, actual);
    }

    @Test
    public void testSetUltimaCompra() {
        LocalDate newUltimaCompra = LocalDate.of(2022, 1, 1);
        cliente.setUltimaCompra(newUltimaCompra);
        LocalDate actual = cliente.getUltimaCompra();
        assertEquals(newUltimaCompra, actual);
    }

    @Test
    public void testGetSancionadoHasta() {
        LocalDate expected = null;
        LocalDate actual = cliente.getSancionadoHasta();
        assertEquals(expected, actual);
    }

    @Test
    public void testSetSancionadoHasta() {
        LocalDate newSancionadoHasta = LocalDate.of(2022, 12, 31);
        cliente.setSancionadoHasta(newSancionadoHasta);
        LocalDate actual = cliente.getSancionadoHasta();
        assertEquals(newSancionadoHasta, actual);
    }
}