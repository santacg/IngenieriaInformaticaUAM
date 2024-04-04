package CentroExposicion;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class DescuentoMesTest {
    private DescuentoMes descuento;

    @BeforeEach
    public void setUp() {
        descuento = new DescuentoMes(0.1, 3);
    }

    @Test
    public void testValidezDescuento_ValidDate_ReturnsTrue() {
        LocalDate fecha = LocalDate.now().plusMonths(2);
        assertTrue(descuento.validezDescuento(fecha));
    }

    @Test
    public void testValidezDescuento_InvalidDate_ReturnsFalse() {
        LocalDate fecha = LocalDate.now().plusMonths(4);
        assertFalse(descuento.validezDescuento(fecha));
    }

    @Test
    public void testValidezDescuento_CurrentDate_ReturnsTrue() {
        LocalDate fecha = LocalDate.now();
        assertTrue(descuento.validezDescuento(fecha));
    }
}