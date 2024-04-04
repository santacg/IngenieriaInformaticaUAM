package src.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import src.Exposicion.Exposicion;
import src.Exposicion.SalaExposicion;
import src.Exposicion.TipoExpo;
import src.Sala.Sala;
import src.CentroExposicion.SorteoFechas;

public class SorteoFechasTest {
    private SorteoFechas sorteo;
    private Set<SalaExposicion> salas;
    private Exposicion exposicion;

    @BeforeEach
    public void setUp() {
        salas = new HashSet<>();
        Sala salaf1 = new Sala("Sala1", 20, 20, 30, true, 3, 20.0, 20.0);
        SalaExposicion sala1 = new SalaExposicion(salaf1);
        salas.add(sala1);
        exposicion = new Exposicion("Exposicion 1", LocalDate.now(), LocalDate.now().plusDays(7), "Descripción", salas, TipoExpo.TEMPORAL, 10.0);
        LocalDate fechaSorteo = LocalDate.now();
        LocalDate fechaInicio = LocalDate.of(2022, 1, 1);
        LocalDate fechaFin = LocalDate.of(2022, 12, 31);
        sorteo = new SorteoFechas(exposicion, fechaSorteo, 2, fechaInicio, fechaFin);
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