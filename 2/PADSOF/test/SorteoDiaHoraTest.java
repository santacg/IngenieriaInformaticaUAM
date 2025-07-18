package test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gui.modelo.centroExposicion.SorteoDiaHora;
import gui.modelo.exposicion.*;

public class SorteoDiaHoraTest {
    private SorteoDiaHora sorteo;
    private Exposicion exposicion;
    private LocalDate fechaSorteo;
    private LocalDate dia;
    private Hora hora;

    @BeforeEach
    public void setUp() {
        exposicion = new Exposicion("Exposicion 1", LocalDate.now(), LocalDate.now().plusDays(7), "Descripción", TipoExpo.TEMPORAL, 10.0);
        fechaSorteo = LocalDate.of(2022, 1, 1);
        dia = LocalDate.of(2022, 1, 2);
        hora = exposicion.getHora(fechaSorteo, 15);
        sorteo = new SorteoDiaHora(exposicion, fechaSorteo, 2, dia, hora);
    }

    @Test
    public void testGetDia() {
        assertEquals(dia, sorteo.getDia());
    }

    @Test
    public void testSetDia() {
        LocalDate newDia = LocalDate.of(2022, 1, 3);
        sorteo.setDia(newDia);
        assertEquals(newDia, sorteo.getDia());
    }

    @Test
    public void testGetFechaLimite() {
        assertEquals(dia, sorteo.getFechaLimite());
    }

    @Test
    public void testGetHora() {
        assertEquals(hora, sorteo.getHora());
    }

    @Test
    public void testSetHora() {
        Hora newHora = exposicion.getHora(fechaSorteo, 16);
        sorteo.setHora(newHora);
        assertEquals(newHora, sorteo.getHora());
    }

    @Test
    public void testGetFechaSorteo() {
        assertEquals(fechaSorteo, sorteo.getFechaSorteo());
    }

    @Test
    public void testGetN_entradas() {
        assertEquals(2, sorteo.getN_entradas());
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

    @Test
    public void testSetFechaSorteo() {
        sorteo.setFechaSorteo(LocalDate.of(2022, 1, 3));
        assertEquals(LocalDate.of(2022, 1, 3), sorteo.getFechaSorteo());
    }

    @Test
    public void testSetN_entradas() {
        sorteo.setN_entradas(3);
        assertEquals(3, sorteo.getN_entradas());
    }
}