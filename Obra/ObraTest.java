package Obra;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ObraTest {
    private Obra obra;
    private Autor autor1;

    @BeforeEach
    public void setUp() {
        Set<Obra> obrasDeAutor = new HashSet<>();
        obra = new Cuadro("Mona Lisa", 1503, "Cuadro famoso", false, 1000000.0, "12345", 1.5, 1.2, 26, 20, 90, 20, "óleo");
        obrasDeAutor.add(obra);
        Autor autor1 = new Autor("Juan Garcia", LocalDate.of(1485, 5, 19), LocalDate.of(1524, 12, 12), "Budapest", "Viena", obrasDeAutor);
    }

    @Test
    public void testAddAutor() {
        obra.addAutor(autor1);
        Set<Autor> autores = obra.getAutores();
        assertTrue(autores.contains(autor1));
    }

    @Test
    public void testRemoveAutor() {
        obra.addAutor(autor1);
        obra.removeAutor(autor1);
        Set<Autor> autores = obra.getAutores();
        assertFalse(autores.contains(autor1));
    }

    @Test
    public void testRetirarObra() {
        obra.retirarObra();
        assertEquals(Estado.RETIRADA, obra.getEstado());
    }

    @Test
    public void testPrestarObra() {
        obra.prestarObra();
        assertEquals(Estado.PRESTADA, obra.getEstado());
    }

    @Test
    public void testAlmacenarObra() {
        obra.almecenarObra();
        assertEquals(Estado.ALMACENADA, obra.getEstado());
    }

    @Test
    public void testExponerObra() {
        obra.exponerObra();
        assertEquals(Estado.EXPUESTA, obra.getEstado());
    }

    @Test
    public void testRestaurarObra() {
        obra.restaurarObra();
        assertEquals(Estado.RESTAURACION, obra.getEstado());
    }
}