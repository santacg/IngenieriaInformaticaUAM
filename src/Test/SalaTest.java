package src.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import src.Sala.Sala;

import java.util.List;

public class SalaTest {
    private Sala sala;

    @BeforeEach
    public void setUp() {
        sala = new Sala("Sala de Prueba", 100, 50, 25, true, 10, 10.0, 10.0);
    }

    @Test
    public void testGetNombre() {
        assertEquals("Sala de Prueba", sala.getNombre());
    }

    @Test
    public void testSetNombre() {
        sala.setNombre("Nueva Sala");
        assertEquals("Nueva Sala", sala.getNombre());
    }

    @Test
    public void testGetAforo() {
        assertEquals(100, sala.getAforo());
    }

    @Test
    public void testSetAforo() {
        sala.setAforo(200);
        assertEquals(200, sala.getAforo());
    }

    @Test
    public void testGetHumedad() {
        assertEquals(50, sala.getHumedad());
    }

    @Test
    public void testSetHumedad() {
        sala.setHumedad(60);
        assertEquals(60, sala.getHumedad());
    }

    @Test
    public void testGetTemperatura() {
        assertEquals(25, sala.getTemperatura());
    }

    @Test
    public void testSetTemperatura() {
        sala.setTemperatura(30);
        assertEquals(30, sala.getTemperatura());
    }

    @Test
    public void testGetClimatizador() {
        assertTrue(sala.getClimatizador());
    }

    @Test
    public void testSetClimatizador() {
        sala.setClimatizador(false);
        assertFalse(sala.getClimatizador());
    }

    @Test
    public void testGetTomasElectricidad() {
        assertEquals(10, sala.getTomasElectricidad());
    }

    @Test
    public void testSetTomasElectricidad() {
        sala.setTomasElectricidad(5);
        assertEquals(5, sala.getTomasElectricidad());
    }

    @Test
    public void testGetAncho() {
        assertEquals(10.0, sala.getAncho());
    }

    @Test
    public void testSetAncho() {
        sala.setAncho(15.0);
        assertEquals(15.0, sala.getAncho());
    }

    @Test
    public void testGetLargo() {
        assertEquals(10.0, sala.getLargo());
    }

    @Test
    public void testSetLargo() {
        sala.setLargo(15.0);
        assertEquals(15.0, sala.getLargo());
    }

    @Test
    public void testAddSubsala_ValidParameters() {
        assertTrue(sala.addSubsala(5.0, 5.0, 5, 50));
        List<Sala> subSalas = sala.getSubSalas();
        assertEquals(1, subSalas.size());
        Sala subSala = subSalas.get(0);
        assertEquals("Sala de Prueba subsala1", subSala.getNombre());
        assertEquals(50, subSala.getAforo());
        assertEquals(50, sala.getAforo());
        assertEquals(5.0, subSala.getAncho());
        assertEquals(5.0, sala.getAncho());
        assertEquals(5.0, subSala.getLargo());
        assertEquals(5.0, sala.getLargo());
        assertEquals(5, subSala.getTomasElectricidad());
        assertEquals(5, sala.getTomasElectricidad());
    }

    @Test
    public void testAddSubsala_InvalidParameters() {
        assertFalse(sala.addSubsala(15.0, 15.0, 15, 150));
        List<Sala> subSalas = sala.getSubSalas();
        assertEquals(0, subSalas.size());
    }

    @Test
    public void testRemoveSubsala() {
        sala.addSubsala(5.0, 5.0, 5, 50);
        sala.removeSubsala();
        List<Sala> subSalas = sala.getSubSalas();
        assertEquals(0, subSalas.size());
    }

    @Test
    public void testRemoveSubsalaCheckSala() {
        sala.addSubsala(5.0, 5.0, 5, 50);
        sala.removeSubsala();
        assertEquals(100, sala.getAforo());
        assertEquals(10.0, sala.getAncho());
        assertEquals(10.0, sala.getLargo());
        assertEquals(10, sala.getTomasElectricidad());
    }

    @Test
    public void testGetSubSalas() {
        sala.addSubsala(5.0, 5.0, 5, 50);
        List<Sala> subSalas = sala.getSubSalas();
        assertEquals(1, subSalas.size());
    }

    @Test
    public void testGetSalaPadre() {
        assertNull(sala.getSalaPadre());
    }

    @Test
    public void testGetSalaPadreNotNUll() {
        sala.addSubsala(5.0, 5.0, 5, 50);
        Sala subSala = sala.getSubSalas().get(0);
        assertEquals(sala, subSala.getSalaPadre());
    }

}