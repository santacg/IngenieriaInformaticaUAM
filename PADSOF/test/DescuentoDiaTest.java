package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gui.modelo.exposicion.DescuentoDia;

import java.time.LocalDate;

public class DescuentoDiaTest {
    private DescuentoDia descuento;

    @BeforeEach
    public void setUp() {
        descuento = new DescuentoDia(10.00, 5);
    }

    @Test
    public void testValidezDescuento_ValidDate_ReturnsTrue() {
        LocalDate fecha = LocalDate.now().plusDays(3);
        assertTrue(descuento.validezDescuento(fecha));
    }

    @Test
    public void testValidezDescuento_EqualDate_ReturnsTrue() {
        LocalDate fecha = LocalDate.now().plusDays(5);
        assertTrue(descuento.validezDescuento(fecha));
    }

    @Test
    public void testValidezDescuento_InvalidDate_ReturnsFalse() {
        LocalDate fecha = LocalDate.now().minusDays(5);
        assertFalse(descuento.validezDescuento(fecha));
    }

    @Test
    public void testGetDescuento() {
        assertEquals(10.00, descuento.getDescuento());
    }

    @Test
    public void testSetDescuento() {
        descuento.setDescuento(20.00);
        assertEquals(20.00, descuento.getDescuento());
    }

    @Test
    public void testGetDias() {
        assertEquals(5, descuento.getCantidad());
    }

    @Test
    public void testSetDias() {
        descuento.setCantidad(3);
        assertEquals(3, descuento.getCantidad());
    }

}