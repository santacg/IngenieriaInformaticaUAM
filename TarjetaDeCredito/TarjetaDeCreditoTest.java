package TarjetaDeCredito;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class TarjetaDeCreditoTest {
    private TarjetaDeCredito tarjeta;

    @BeforeEach
    public void setUp() {
        tarjeta = new TarjetaDeCredito("12345", LocalDate.of(2025, 8, 24), 123);
    }

    @Test
    public void testGetNumero() {
        String numero = "12345";

        assertEquals(numero, tarjeta.getNumero());
    }

    @Test
    public void testSetNumero() {
        String newNumero = "9876543210987654";
        tarjeta.setNumero(newNumero);

        assertEquals(newNumero, tarjeta.getNumero());
    }

    @Test
    public void testGetFechaCaducidad() {
        LocalDate fechaCaducidad = LocalDate.of(2025, 8, 24);

        assertEquals(fechaCaducidad, tarjeta.getFechaCaducidad());
    }

    @Test
    public void testSetFechaCaducidad() {
        LocalDate newFechaCaducidad = LocalDate.of(1, 1, 1);
        tarjeta.setFechaCaducidad(newFechaCaducidad);

        assertEquals(newFechaCaducidad, tarjeta.getFechaCaducidad());
    }

    @Test
    public void testGetCVV() {
        int CVV = 123;

        assertEquals(CVV, tarjeta.getCVV());
    }

    @Test
    public void testSetCVV() {
        int newCVV = 456;

        tarjeta.setCVV(newCVV);

        assertEquals(newCVV, tarjeta.getCVV());
    }
}