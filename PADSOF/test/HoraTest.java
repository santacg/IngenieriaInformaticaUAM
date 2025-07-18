package test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gui.modelo.exposicion.Hora;

public class HoraTest {
    private Hora hora;

    @BeforeEach
    public void setUp() {
        LocalDate fecha = LocalDate.of(2022, 1, 1);
        LocalTime horaInicio = LocalTime.of(10, 0);
        LocalTime horaFin = LocalTime.of(12, 0);
        Integer nEntradas = 10;

        hora = new Hora(fecha, horaInicio, horaFin, nEntradas);
    }


    @Test 
    public void testGetEntradas() {
        assertEquals(10, hora.getnEntradas());
    }

    @Test
    public void testGetFecha() {
        LocalDate expected = LocalDate.of(2022, 1, 1);
        assertEquals(expected, hora.getFecha());
    }

    @Test
    public void testSetFecha() {
        LocalDate newFecha = LocalDate.of(2022, 2, 1);
        hora.setFecha(newFecha);
        assertEquals(newFecha, hora.getFecha());
    }

    @Test
    public void testGetHoraInicio() {
        LocalTime expected = LocalTime.of(10, 0);
        assertEquals(expected, hora.getHoraInicio());
    }

    @Test
    public void testSetHoraInicio() {
        LocalTime newHoraInicio = LocalTime.of(11, 0);
        hora.setHoraInicio(newHoraInicio);
        assertEquals(newHoraInicio, hora.getHoraInicio());
    }

    @Test
    public void testGetHoraFin() {
        LocalTime expected = LocalTime.of(12, 0);
        assertEquals(expected, hora.getHoraFin());
    }

    @Test
    public void testSetHoraFin() {
        LocalTime newHoraFin = LocalTime.of(13, 0);
        hora.setHoraFin(newHoraFin);
        assertEquals(newHoraFin, hora.getHoraFin());
    }

    @Test
    public void testGetCountEntradas() {
        assertEquals(10, hora.getCountEntradas());
    }

    @Test
    public void testGetnEntradas() {
        assertEquals(10, hora.getnEntradas());
    }

    @Test
    public void testEntradaVendida() {
        hora.entradaVendida();
        assertEquals(9, hora.getCountEntradas());
    }

    @Test
    public void testGetnEntradasDisp() {
        assertEquals(10, hora.getnEntradasDisp());
    }

}