package Exposicion;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Obra.Obra;
import Sala.Sala;

public class SalaExposicionTest {
    private SalaExposicion salaExposicion;
    private Sala sala;
    private Obra obra1;
    private Obra obra2;

    @BeforeEach
    public void setUp() {
        sala = new Sala("Sala 1", true);
        salaExposicion = new SalaExposicion(sala);
        obra1 = new ObraDigital("Obra 1", "Artista 1", "URL 1");
        obra2 = new ObraNoDigital("Obra 2", "Artista 2");
    }

    @Test
    public void testGetSala() {
        assertEquals(sala, salaExposicion.getSala());
    }

    @Test
    public void testSetSala() {
        Sala newSala = new Sala("Sala 2", false);
        salaExposicion.setSala(newSala);
        assertEquals(newSala, salaExposicion.getSala());
    }

    @Test
    public void testGetObras() {
        assertTrue(salaExposicion.getObras().isEmpty());
    }

    @Test
    public void testSetObras() {
        Set<Obra> obras = new HashSet<>();
        obras.add(obra1);
        obras.add(obra2);
        salaExposicion.setObras(obras);
        assertEquals(obras, salaExposicion.getObras());
    }

    @Test
    public void testAddObra_ObraDigitalWithClimatizador() {
        assertTrue(salaExposicion.addObra(obra1));
        assertTrue(salaExposicion.getObras().contains(obra1));
    }

    @Test
    public void testAddObra_ObraNoDigitalWithoutClimatizador() {
        assertFalse(salaExposicion.addObra(obra2));
        assertFalse(salaExposicion.getObras().contains(obra2));
    }

    @Test
    public void testRemoveObra() {
        salaExposicion.addObra(obra1);
        salaExposicion.removeObra(obra1);
        assertFalse(salaExposicion.getObras().contains(obra1));
    }

    @Test
    public void testToString() {
        salaExposicion.addObra(obra1);
        salaExposicion.addObra(obra2);
        String expected = "SalaExposicion Details:\n" +
                          "Sala: Sala 1\n" +
                          "Obras: [Obra 1, Obra 2]\n";
        assertEquals(expected, salaExposicion.toString());
    }
}