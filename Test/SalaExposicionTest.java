package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import GUI.modelo.exposicion.SalaExposicion;
import GUI.modelo.obra.*;
import GUI.modelo.sala.Sala;

public class SalaExposicionTest {
    private SalaExposicion salaExposicion;
    private Sala sala;
    private Obra obra1;
    private Obra obra2;

    @BeforeEach
    public void setUp() {
        sala = new Sala("Sala 1", 100, 40, 20, false, 10, 20.0, 40.0);
        salaExposicion = new SalaExposicion(sala);
        obra1 = new Audiovisual("Oppenheimer", 2023, "Descripción", false, 5000.0, "67890", "3h00m00s", "Ingles");
        obra2 = new Cuadro("Mona Lisa", 1503, "Cuadro famoso", false, 1000000.0, "12345", 1.5, 1.2, 26, 20, 90, 20, "óleo");
    }


    @Test
    public void testGetSala() {
        assertEquals(sala, salaExposicion.getSala());
    }

    @Test
    public void testSetSala() {
        Sala newSala = new Sala("Sala 2", 10, 30, 15, false, null, null, null);
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

}