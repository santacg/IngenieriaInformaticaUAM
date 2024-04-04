package src.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import src.Exposicion.Estadisticas;

public class EstadisticasTest {
    private Estadisticas estadisticas;

    @BeforeEach
    public void setUp() {
        estadisticas = new Estadisticas();
    }

    @Test
    public void testGetTicketsVendidos() {
        assertEquals(0, estadisticas.getTicketsVendidos());
    }

    @Test
    public void testGetIngresosTotales() {
        assertEquals(0.0, estadisticas.getIngresosTotales());
    }

    @Test
    public void testIncrementarTicketsVendidos() {
        estadisticas.incrementarTicketsVendidos();
        assertEquals(1, estadisticas.getTicketsVendidos());
    }

    @Test
    public void testIncrementarIngresosTotales() {
        estadisticas.incrementarIngresosTotales(10.0);
        assertEquals(10.0, estadisticas.getIngresosTotales());
    }
}