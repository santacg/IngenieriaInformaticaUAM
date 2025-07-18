package test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import gui.modelo.exposicion.SalaExposicion;
import gui.modelo.obra.*;
import gui.modelo.sala.Sala;


public class SalaExposicionTest {
    private SalaExposicion salaExposicionClimatizada;
    private SalaExposicion salaExposicionNoClimatizada;
    private SalaExposicion salaExposicionSinTomas;
    private SalaExposicion salaExposicionUnaToma;
    private Sala salaClimatizada;
    private Sala salaNoClimatizada;
    private Sala sinTomas;
    private Sala unaToma;
    private Obra obra;
    private Audiovisual audiovisual;
    private Audiovisual audiovisual2;

    @BeforeEach
    public void setUp() {
        salaClimatizada = new Sala("Sala climatizada", 100, true, 10, 10.0, 10.0, 10.0);
        salaExposicionClimatizada = new SalaExposicion(salaClimatizada);

        salaNoClimatizada = new Sala("Sala no climatizada", 100, false, 10, 10.0, 10.0, 10.0);
        salaExposicionNoClimatizada = new SalaExposicion(salaNoClimatizada);
        
        sinTomas = new Sala("Sala sin tomas", 100, true, 0, 10.0, 10.0, 10.0);
        salaExposicionSinTomas = new SalaExposicion(sinTomas);

        unaToma = new Sala("Sala con una toma", 100, true, 1, 10.0, 10.0, 10.0);
        salaExposicionUnaToma = new SalaExposicion(unaToma);

        obra = new Cuadro("Mona Lisa", 1503, "Cuadro famoso", false, 1000000.0, "12345", 1.5, 1.2, 26, 20, 90, 20,
                "óleo", "Leonardo da Vinci");
        audiovisual = new Audiovisual("El padrino", 1972, "Película famosa", false, 10000000.0, "13579", "2h 55m", "inglés",
                "Francis Ford Coppola, Mario Puzo");
        audiovisual2 = new Audiovisual("El padrino 2", 1977, "Película famosa", false, 10000000.0, "13579", "2h 55m", "inglés",
                "Francis Ford Coppola, Mario Puzo");
    }

    @Test
    public void testGetSala() {
        assertEquals(salaClimatizada, salaExposicionClimatizada.getSala());
    }

    @Test
    public void testSetSala() {
        salaExposicionClimatizada.setSala(salaNoClimatizada);
        assertEquals(salaNoClimatizada, salaExposicionClimatizada.getSala());
    }

    @Test
    public void testGetObras() {
        assertTrue(salaExposicionClimatizada.getObras().isEmpty());
    }

    @Test
    public void testRemoveObra() {
        salaExposicionClimatizada.addObra(obra);
        salaExposicionClimatizada.removeObra(obra);
        assertTrue(salaExposicionClimatizada.getObras().isEmpty());
    }

    @Test
    public void testSetObras() {
        Set<Obra> nuevasObras = new HashSet<>();
        nuevasObras.add(obra);
        assertTrue(salaExposicionClimatizada.setObras(nuevasObras));
    }

    @Test 
    public void testAddObraConClimatizador() {
        assertTrue(salaExposicionClimatizada.addObra(obra));
    }

    @Test
    public void testAddObraSinClimatizador() {
        assertFalse(salaExposicionNoClimatizada.addObra(obra));
    } 

    @Test
    public void testAddObraSinTomas() {
        assertFalse(salaExposicionSinTomas.addObra(audiovisual));
    }

    @Test
    public void testAddObraConUnaToma() {
        assertTrue(salaExposicionUnaToma.addObra(audiovisual));
    }

    @Test
    public void testAdd2ObrasConUnaToma() {
        salaExposicionUnaToma.addObra(audiovisual);
        assertFalse(salaExposicionUnaToma.addObra(audiovisual2));
    }

    @Test
    public void testAddObraRepetida() {
        salaExposicionClimatizada.addObra(obra);
        assertFalse(salaExposicionClimatizada.addObra(obra));
    }

}
