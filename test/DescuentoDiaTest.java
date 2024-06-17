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
        descuento = new DescuentoDia(0.1, 5);
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
}