package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gui.modelo.exposicion.DescuentoMes;

import java.time.LocalDate;

public class DescuentoMesTest {
    private DescuentoMes descuento;

    @BeforeEach
    public void setUp() {
        descuento = new DescuentoMes(20.00, 3);
    }

    @Test
    public void testValidezDescuento_ValidDate_ReturnsTrue() {
        LocalDate fecha = LocalDate.now().plusMonths(2);
        assertTrue(descuento.validezDescuento(fecha));
    }

    @Test
    public void testValidezDescuento_CurrentDate_ReturnsTrue() {
        LocalDate fecha = LocalDate.now();
        assertTrue(descuento.validezDescuento(fecha));
    }

    @Test
    public void testValidezDescuento_InvalidDate_ReturnsFalse() {
        LocalDate fecha = LocalDate.now().minusMonths(5);
        assertFalse(descuento.validezDescuento(fecha));
    }

    @Test
    public void testGetDescuento() {
        assertEquals(20.00, descuento.getDescuento());
    }

    @Test
    public void testSetDescuento() {
        descuento.setDescuento(20.00);
        assertEquals(20.00, descuento.getDescuento());
    }

    @Test
    public void testGetMeses() {
        assertEquals(3, descuento.getCantidad());
    }

    @Test
    public void testSetMeses() {
        descuento.setCantidad(3);
        assertEquals(3, descuento.getCantidad());
    }
}