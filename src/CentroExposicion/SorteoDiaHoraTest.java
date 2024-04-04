package CentroExposicion;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Exposicion.Exposicion;

public class SorteoDiaHoraTest {
    private SorteoDiaHora sorteo;
    private Exposicion exposicion;
    private LocalDate fechaSorteo;
    private LocalDate dia;
    private LocalTime hora;

    @BeforeEach
    public void setUp() {
        exposicion = new Exposicion(null, dia, dia, null, null, null, null);
        fechaSorteo = LocalDate.of(2022, 1, 1);
        dia = LocalDate.of(2022, 1, 2);
        hora = LocalTime.of(12, 0);
        sorteo = new SorteoDiaHora(exposicion, fechaSorteo, dia, hora);
    }

    @Test
    public void testGetDia() {
        assertEquals(dia, sorteo.getDia());
    }

    @Test
    public void testGetHora() {
        assertEquals(hora, sorteo.getHora());
    }

    @Test
    public void testSetDia() {
        LocalDate newDia = LocalDate.of(2022, 1, 3);
        sorteo.setDia(newDia);
        assertEquals(newDia, sorteo.getDia());
    }

    @Test
    public void testSetHora() {
        LocalTime newHora = LocalTime.of(14, 0);
        sorteo.setHora(newHora);
        assertEquals(newHora, sorteo.getHora());
    }

    @Test
    public void testGetFechaLimite() {
        assertEquals(dia, sorteo.getFechaLimite());
    }
}