package test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gui.modelo.centroExposicion.SorteoExpo;
import gui.modelo.exposicion.*;

public class SorteoExpoTest {
    private SorteoExpo sorteo;
    private Exposicion exposicion;

    @BeforeEach
    public void setUp() {
        exposicion = new Exposicion("Exposicion 1", LocalDate.now(), LocalDate.now().plusDays(7), "Descripción", TipoExpo.TEMPORAL, 10.0);
        sorteo = new SorteoExpo(exposicion, LocalDate.of(2022, 10, 15), 1);
    }

    @Test
    public void testGetFechaLimite() {
        assertEquals(LocalDate.now().plusDays(7), sorteo.getFechaLimite());
    }

    @Test
    public void testGetFechaSorteo() {
        assertEquals(LocalDate.of(2022, 10, 15), sorteo.getFechaSorteo());
    }

    @Test
    public void testGetN_entradas() {
        assertEquals(1, sorteo.getN_entradas());
    }

    @Test
    public void testGetExposicion() {
        assertEquals(exposicion, sorteo.getExposicion());
    }

    @Test
    public void testSetExposicion() {
        Exposicion newExpo = new Exposicion("Exposicion 2", LocalDate.now(), LocalDate.now().plusDays(14), "Descripción 2", TipoExpo.TEMPORAL, 20.0);
        sorteo.setExposicion(newExpo);
        assertEquals(newExpo, sorteo.getExposicion());
    }

}