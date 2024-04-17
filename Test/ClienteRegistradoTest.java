package Test;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import src.expofy.ClienteRegistrado;
/**
 * Clase ClienteRegistradoTest
 * Esta clase contiene pruebas unitarias que validan la funcionalidad de los
 * métodos de la clase {@link ClienteRegistrado}.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public class ClienteRegistradoTest {
    private ClienteRegistrado cliente;

    /**
     * Inicializa una nueva instancia de {@link ClienteRegistrado} antes de cada prueba
     * con valores predeterminados para asegurar un entorno de prueba consistente.
     */
    @BeforeEach
    public void setUp() {
        String NIF = "123456789";
        Boolean publicidad = true;
        String contrasenia = "alumnodb";
        Boolean sancionado = false;
        LocalDate ultimaCompra = LocalDate.now();
        LocalDate sancionadoHasta = null;
        cliente = new ClienteRegistrado(NIF, publicidad, contrasenia, sancionado, ultimaCompra, sancionadoHasta);
    }

    /**
     * Verifica que el método {@code getPublicidad} retorne el valor correcto.
     */
    @Test
    public void testGetPublicidad() {
        Boolean expected = true;
        Boolean actual = cliente.getPublicidad();
        assertEquals(expected, actual);
    }

    /**
     * Prueba que el método {@code setPublicidad} actualiza correctamente el atributo
     * publicidad de la instancia de {@link ClienteRegistrado}.
     */
    @Test
    public void testSetPublicidad() {
        Boolean nuevaPublicidad = false;
        cliente.setPublicidad(nuevaPublicidad);
        Boolean actual = cliente.getPublicidad();
        assertEquals(nuevaPublicidad, actual);
    }

    /**
     * Verifica que el método {@code getContrasenia} retorna la contraseña correcta.
     */
    @Test
    public void testGetContrasenia() {
        String esperado = "alumnodb";
        String actual = cliente.getContrasenia();
        assertEquals(esperado, actual);
    }

    /**
     * Prueba que el método {@code setContrasenia} actualiza correctamente la contraseña
     * de la instancia de {@link ClienteRegistrado}.
     */
    @Test
    public void testSetContrasenia() {
        String nuevaContrasenia = "padsof";
        cliente.setContrasenia(nuevaContrasenia);
        String actual = cliente.getContrasenia();
        assertEquals(nuevaContrasenia, actual);
    }

    /**
     * Verifica que el método {@code getSancionado} refleje correctamente el estado
     * de sanción del {@link ClienteRegistrado}.
     */
    @Test
    public void testGetSancionado() {
        Boolean esperado = false;
        Boolean actual = cliente.getSancionado();
        assertEquals(esperado, actual);
    }

    /**
     * Prueba que el método {@code setSancionado} puede cambiar el estado de sanción
     * de la instancia de {@link ClienteRegistrado}.
     */
    @Test
    public void testSetSancionado() {
        Boolean nuevoSancionado = true;
        cliente.setSancionado(nuevoSancionado);
        Boolean actual = cliente.getSancionado();
        assertEquals(nuevoSancionado, actual);
    }

    /**
     * Verifica que el método {@code getUltimaCompra} retorna correctamente la fecha
     * de la última compra.
     */
    @Test
    public void testGetUltimaCompra() {
        LocalDate esperado = LocalDate.now();
        LocalDate actual = cliente.getUltimaCompra();
        assertEquals(esperado, actual);
    }

    /**
     * Prueba que el método {@code setUltimaCompra} actualiza la fecha de la última
     * compra.
     */
    @Test
    public void testSetUltimaCompra() {
        LocalDate nuevaUltimaCompra = LocalDate.of(2022, 1, 1);
        cliente.setUltimaCompra(nuevaUltimaCompra);
        LocalDate actual = cliente.getUltimaCompra();
        assertEquals(nuevaUltimaCompra, actual);
    }

    /**
     * Verifica que el método {@code getSancionadoHasta} retorna correctamente la fecha
     * hasta la cual el cliente está sancionado.
     */
    @Test
    public void testGetSancionadoHasta() {
        LocalDate actual = cliente.getSancionadoHasta();
        assertEquals(LocalDate.of(1970,1, 1), actual);
    }

    /**
     * Prueba que el método {@code setSancionadoHasta} actualiza la fecha hasta la cual
     * el cliente está sancionado.
     */
    @Test
    public void testSetSancionadoHasta() {
        LocalDate nuevoSancionadoHasta = LocalDate.of(2022, 12, 31);
        cliente.setSancionadoHasta(nuevoSancionadoHasta);
        LocalDate actual = cliente.getSancionadoHasta();
        assertEquals(nuevoSancionadoHasta, actual);
    }
}