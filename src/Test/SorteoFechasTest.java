package src.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import src.Exposicion.Exposicion;
import src.CentroExposicion.SorteoFechas;

public class SorteoFechasTest {
    private SorteoFechas sorteo;

    @BeforeEach
    public void setUp() {
        Exposicion exposicion = new Exposicion();
        LocalDate fechaSorteo = LocalDate.now();
        LocalDate fechaInicio = LocalDate.of(2022, 1, 1);
        LocalDate fechaFin = LocalDate.of(2022, 12, 31);
        sorteo = new SorteoFechas(exposicion, fechaSorteo, fechaInicio, fechaFin);
    }

    @Test
    public void testGetFechaInicio() {
        LocalDate expected = LocalDate.of(2022, 1, 1);
        assertEquals(expected, sorteo.getFechaInicio());
    }

    @Test
    public void testSetFechaInicio() {
        LocalDate newFechaInicio = LocalDate.of(2023, 1, 1);
        sorteo.setFechaInicio(newFechaInicio);
        assertEquals(newFechaInicio, sorteo.getFechaInicio());
    }

    @Test
    public void testGetFechaFin() {
        LocalDate expected = LocalDate.of(2022, 12, 31);
        assertEquals(expected, sorteo.getFechaFin());
    }

    @Test
    public void testSetFechaFin() {
        LocalDate newFechaFin = LocalDate.of(2023, 12, 31);
        sorteo.setFechaFin(newFechaFin);
        assertEquals(newFechaFin, sorteo.getFechaFin());
    }

    @Test
    public void testGetFechaLimite() {
        LocalDate expected = LocalDate.of(2022, 12, 31);
        assertEquals(expected, sorteo.getFechaLimite());
    }
}