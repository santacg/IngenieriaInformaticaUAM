

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

/**
 * Clase TarjetaDeCreditoTest.
 * Esta clase contiene pruebas unitarias que validan la funcionalidad de los
 * métodos de la clase {@link TarjetaDeCredito}.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public class TarjetaDeCreditoTest {
    private TarjetaDeCredito tarjeta;

    /**
     * Inicializa una nueva tarjeta de crédito antes de cada prueba.
     */
    @BeforeEach
    public void setUp() {
        tarjeta = new TarjetaDeCredito("12345", LocalDate.of(2025, 8, 24), 123);
    }

    /**
     * Verifica que el método getNumero retorne el número correcto de la tarjeta.
     */
    @Test
    public void testGetNumero() {
        String numero = "12345";
        assertEquals(numero, tarjeta.getNumero());
    }

    /**
     * Verifica que el método setNumero actualice correctamente el número de la
     * tarjeta.
     */
    @Test
    public void testSetNumero() {
        String newNumero = "9876543210987654";
        tarjeta.setNumero(newNumero);
        assertEquals(newNumero, tarjeta.getNumero());
    }

    /**
     * Verifica que el método getFechaCaducidad retorne la fecha de caducidad
     * correcta.
     */
    @Test
    public void testGetFechaCaducidad() {
        LocalDate fechaCaducidad = LocalDate.of(2025, 8, 24);
        assertEquals(fechaCaducidad, tarjeta.getFechaCaducidad());
    }

    /**
     * Verifica que el método setFechaCaducidad actualice correctamente la fecha de
     * caducidad.
     */
    @Test
    public void testSetFechaCaducidad() {
        LocalDate newFechaCaducidad = LocalDate.of(2029, 12, 31);
        tarjeta.setFechaCaducidad(newFechaCaducidad);
        assertEquals(newFechaCaducidad, tarjeta.getFechaCaducidad());
    }

    /**
     * Verifica que el método getCVV retorne el código CVV correcto.
     */
    @Test
    public void testGetCVV() {
        int CVV = 123;
        assertEquals(CVV, tarjeta.getCVV());
    }

    /**
     * Verifica que el método setCVV actualice correctamente el código CVV.
     */
    @Test
    public void testSetCVV() {
        int newCVV = 456;
        tarjeta.setCVV(newCVV);
        assertEquals(newCVV, tarjeta.getCVV());
    }
}import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

/**
 * Clase TarjetaDeCreditoTest.
 * Esta clase contiene pruebas unitarias que validan la funcionalidad de los
 * métodos de la clase {@link TarjetaDeCredito}.
 * 
 * Generated tests for the TarjetaDeCredito class.
 */
public class TarjetaDeCreditoTest {
    private TarjetaDeCredito tarjeta;

    /**
     * Inicializa una nueva tarjeta de crédito antes de cada prueba.
     */
    @BeforeEach
    public void setUp() {
        tarjeta = new TarjetaDeCredito("12345", LocalDate.of(2025, 8, 24), 123);
    }

    /**
     * Verifica que el método getNumero retorne el número correcto de la tarjeta.
     */
    @Test
    public void testGetNumero() {
        String numero = "12345";
        assertEquals(numero, tarjeta.getNumero());
    }

    /**
     * Verifica que el método setNumero actualice correctamente el número de la
     * tarjeta.
     */
    @Test
    public void testSetNumero() {
        String newNumero = "9876543210987654";
        tarjeta.setNumero(newNumero);
        assertEquals(newNumero, tarjeta.getNumero());
    }

    /**
     * Verifica que el método getFechaCaducidad retorne la fecha de caducidad
     * correcta.
     */
    @Test
    public void testGetFechaCaducidad() {
        LocalDate fechaCaducidad = LocalDate.of(2025, 8, 24);
        assertEquals(fechaCaducidad, tarjeta.getFechaCaducidad());
    }

    /**
     * Verifica que el método setFechaCaducidad actualice correctamente la fecha de
     * caducidad.
     */
    @Test
    public void testSetFechaCaducidad() {
        LocalDate newFechaCaducidad = LocalDate.of(2029, 12, 31);
        tarjeta.setFechaCaducidad(newFechaCaducidad);
        assertEquals(newFechaCaducidad, tarjeta.getFechaCaducidad());
    }

    /**
     * Verifica que el método getCVV retorne el código CVV correcto.
     */
    @Test
    public void testGetCVV() {
        int CVV = 123;
        assertEquals(CVV, tarjeta.getCVV());
    }

    /**
     * Verifica que el método setCVV actualice correctamente el código CVV.
     */
    @Test
    public void testSetCVV() {
        int newCVV = 456;
        tarjeta.setCVV(newCVV);
        assertEquals(newCVV, tarjeta.getCVV());
    }
}