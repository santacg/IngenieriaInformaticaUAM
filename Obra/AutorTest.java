package Obra;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

class AutorTest {
    private Autor autor;
    private Set<Obra> obras;

    @BeforeEach
    public void setUp() {
        obras = new HashSet<>();
        autor = new Autor("Leonardo da Vinci", LocalDate.of(1452, 4, 15), LocalDate.of(1519, 5, 2),
                "Vinci", "Amboise");
        Cuadro obra1 = new Cuadro("Mona Lisa", 1503, "Retrato de Lisa Gherardini", false, 1000000.0, 77.0, 53.0,
                25.0, 18.0, 60.0, 40.0, "123456789", "Olio sobre lienzo", Estado.EXPUESTA);
        obras.add(obra1);
        autor.addObra(obra1);
    }

    @Test
    public void testGetNombre() {
        assertEquals("Leonardo da Vinci", autor.getNombre());
    }

    @Test
    public void testSetNombre() {
        autor.setNombre("Pablo Picasso");
        assertEquals("Pablo Picasso", autor.getNombre());
    }

    @Test
    public void testGetFechaNacimiento() {
        assertEquals(LocalDate.of(1452, 4, 15), autor.getFechaNacimiento());
    }

    @Test
    public void testSetFechaNacimiento() {
        autor.setFechaNacimiento(LocalDate.of(1881, 10, 25));
        assertEquals(LocalDate.of(1881, 10, 25), autor.getFechaNacimiento());
    }

    @Test
    public void testGetFechaFallecimiento() {
        assertEquals(LocalDate.of(1519, 5, 2), autor.getFechaFallecimiento());
    }

    @Test
    public void testSetFechaFallecimiento() {
        autor.setFechaFallecimiento(LocalDate.of(1973, 4, 8));
        assertEquals(LocalDate.of(1973, 4, 8), autor.getFechaFallecimiento());
    }

    @Test
    public void testGetLugarNacimiento() {
        assertEquals("Vinci", autor.getLugarNacimiento());
    }

    @Test
    public void testSetLugarNacimiento() {
        autor.setLugarNacimiento("Malaga");
        assertEquals("Malaga", autor.getLugarNacimiento());
    }

    @Test
    public void testGetLugarFallecimiento() {
        assertEquals("Amboise", autor.getLugarFallecimiento());
    }

    @Test
    public void testSetLugarFallecimiento() {
        autor.setLugarFallecimiento("Paris");
        assertEquals("Paris", autor.getLugarFallecimiento());
    }

    @Test
    public void testGetObras() {
        assertEquals(obras, autor.getObras());
    }

    @Test
    public void testSetObras() {
        Set<Obra> newObras = new HashSet<>();
        Cuadro obra2 = new Cuadro("el Guernica", 1937, "Pintura de Pablo Picasso", false, 1000000.0, 349.3, 776.6,
                25.0, 18.0, 60.0, 40.0, "123456789", "Olio sobre lienzo", Estado.EXPUESTA);
        Cuadro obra3 = new Cuadro("La persistencia de la memoria", 1931, "Pintura de Salvador Dalí", false, 1000000.0,
                24.1, 33.0, 25.0, 18.0, 60.0, 40.0, "123456789", "Olio sobre lienzo", Estado.EXPUESTA);
        newObras.add(obra2);
        newObras.add(obra3);
        autor.setObras(newObras);
        assertEquals(newObras, autor.getObras());
    }

    @Test
    public void testAddObra() {
        Cuadro newObra = new Cuadro("La noche estrellada", 1889, "Pintura de Vincent van Gogh", false, 1000000.0,
                73.7, 92.1, 25.0, 18.0, 60.0, 40.0, "123456789", "Olio sobre lienzo", Estado.EXPUESTA);
        autor.addObra(newObra);
        assertTrue(autor.getObras().contains(newObra));
    }

    @Test
    public void testRemoveObra() {
        Cuadro obraToRemove = new Cuadro("El grito", 1893, "Pintura de Edvard Munch", false, 1000000.0, 91.0, 73.5,
                25.0, 18.0, 60.0, 40.0, "123456789", "Olio sobre lienzo", Estado.EXPUESTA);
        autor.removeObra(obraToRemove);
        assertFalse(autor.getObras().contains(obraToRemove));
    }
}