package Exposicion;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Entrada.Entrada;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

class HoraTest {
    private Hora hora;

    @BeforeEach
    public void setUp() {
        hora = new Hora(LocalDate.of(2022, 1, 1), LocalTime.of(10, 0), LocalTime.of(12, 0), 100, 10.0);
    }

    @Test
    public void testGetFecha() {
        assertEquals(LocalDate.of(2022, 1, 1), hora.getFecha());
    }

    @Test
    public void testSetFecha() {
        hora.setFecha(LocalDate.of(2022, 2, 1));
        assertEquals(LocalDate.of(2022, 2, 1), hora.getFecha());
    }

    @Test
    public void testGetHoraInicio() {
        assertEquals(LocalTime.of(10, 0), hora.getHoraInicio());
    }

    @Test
    public void testSetHoraInicio() {
        hora.setHoraInicio(LocalTime.of(11, 0));
        assertEquals(LocalTime.of(11, 0), hora.getHoraInicio());
    }

    @Test
    public void testGetHoraFin() {
        assertEquals(LocalTime.of(12, 0), hora.getHoraFin());
    }

    @Test
    public void testSetHoraFin() {
        hora.setHoraFin(LocalTime.of(13, 0));
        assertEquals(LocalTime.of(13, 0), hora.getHoraFin());
    }

    @Test
    public void testGetnEntradas() {
        assertEquals(100, hora.getnEntradas());
    }

    @Test
    public void testSetnEntradas() {
        hora.setnEntradas(200);
        assertEquals(200, hora.getnEntradas());
    }

    @Test
    public void testGetPrecio() {
        assertEquals(10.0, hora.getPrecio());
    }

    @Test
    public void testSetPrecio() {
        hora.setPrecio(15.0);
        assertEquals(15.0, hora.getPrecio());
    }

    @Test
    public void testSetEntradas() {
        Set<Entrada> newEntradas = new HashSet<>();
        Entrada entrada1 = new Entrada(1);
        Entrada entrada2 = new Entrada(2);
        newEntradas.add(entrada1);
        newEntradas.add(entrada2);
        hora.setEntradas(newEntradas);
        assertEquals(newEntradas, hora.getEntradas());
    }

    @Test
    public void testAddEntrada() {
        Entrada newEntrada = new Entrada(3);
        hora.addEntrada(newEntrada);
        assertTrue(hora.getEntradas().contains(newEntrada));
    }

    @Test
    public void testRemoveEntrada() {
        Entrada entradaToRemove = new Entrada(4);
        hora.removeEntrada(entradaToRemove);
        assertFalse(hora.getEntradas().contains(entradaToRemove));
    }

    @Test
    public void testRemoveAllEntradas() {
        hora.removeAllEntradas();
        assertEquals(0, hora.getnEntradas());
        assertTrue(hora.getEntradas().isEmpty());
    }
}