package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gui.modelo.sala.Sala;

import java.util.List;

public class SalaTest {
    private Sala sala;

    @BeforeEach
    public void setUp() {
        sala = new Sala("Sala de Prueba", 100, true, 10, 10.0, 10.0, 10.0);
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
    public void testAddSubsalaParametrosCorrectos() {
        // La dividimos por la mitad, los atributos de sala padre por tanto deben
        // dividirse por la mitad
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
        assertTrue(subSala.getClimatizador());
    }

    @Test
    public void testAddSubsalaParametrosNoCorrectos() {
        assertFalse(sala.addSubsala(15.0, 15.0, 15, 150));
        List<Sala> subSalas = sala.getSubSalas();
        assertEquals(0, subSalas.size());
    }

    @Test
    public void testGetSubSalas() {
        sala.addSubsala(5.0, 5.0, 5, 50);
        List<Sala> subSalas = sala.getSubSalas();
        assertEquals(1, subSalas.size());
    }

    @Test
    public void testGetSalaPadre() {
        sala.addSubsala(5.0, 5.0, 5, 50);
        List<Sala> subSalas = sala.getSubSalas();
        Sala subSala = subSalas.get(0);
        assertNotNull(subSala.getSalaPadre());
    }

    @Test
    public void testGetSalaPadreNotNUll() {
        sala.addSubsala(5.0, 5.0, 5, 50);
        Sala subSala = sala.getSubSalas().get(0);
        assertEquals(sala, subSala.getSalaPadre());
    }

    @Test
    public void testAjusteHumedadConClimatizador() {
        sala.setClimatizador(true);
        sala.setHumedad(70);
        assertEquals(70, sala.getHumedad());
    }

    @Test
    public void testAjusteHumedadSinClimatizador() {
        sala.setClimatizador(false);
        sala.setHumedad(70);
        assertNotEquals(70, sala.getHumedad());
    }

    @Test
    public void testAjusteTemperaturaConClimatizadorActivado() {
        sala.setClimatizador(true);
        sala.setTemperatura(22);
        assertEquals(22, sala.getTemperatura());
    }

    @Test
    public void testAjusteTemperaturaConClimatizadorDesactivado() {
        sala.setClimatizador(false);
        sala.setTemperatura(22);
        assertNotEquals(22, sala.getTemperatura());
    }

    @Test
    public void testRemoveSubSalaPorNombre() {
        sala.addSubsala(5.0, 5.0, 5, 50);
        sala.removeSubsalaPorNombre("Sala de Prueba subsala1");
        assertTrue(sala.getSubSalas().isEmpty());
        assertEquals(100, sala.getAforo());
        assertEquals(10.0, sala.getAncho(), 0.01);
        assertEquals(10.0, sala.getLargo(), 0.01);
    }

    @Test
    public void testRemoveAllSubsalas() {
        sala.addSubsala(5.0, 5.0, 5, 50);
        sala.removeAllSubsalas();
        assertTrue(sala.getSubSalas().isEmpty());
        assertEquals(100, sala.getAforo());
    }

    @Test
    public void testEqualsDiferentesSalasMismoNombre() {
        Sala otraSalaMismoNombre = new Sala("Sala de Prueba", 50, true, 5, 8.0, 8.0, 8.0);
        assertEquals(sala, otraSalaMismoNombre);
    }

    @Test
    public void testEqualsDiferentesSalasDiferenteNombre() {
        Sala otraSalaDiferenteNombre = new Sala("Otra Sala", 100, true, 10, 10.0, 10.0, 10.0);
        assertNotEquals(sala, otraSalaDiferenteNombre);
    }
}