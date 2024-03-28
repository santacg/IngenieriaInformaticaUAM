package Obra;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.time.LocalDate;
import java.util.Set;



public class ObraTest {
    private Obra obra;

    @BeforeEach
    public void setUp() {
        Cuadro cuadro = new Cuadro(1, "Mona Lisa", 1503, "Famous painting", false, 1000000.0, 77.0, 53.0, 25.0, 18.0, 60.0, 40.0, "12345", "Olio sobre lienzo", Estado.EXPUESTA);
        obra = cuadro;
    }

    @Test
    public void testGetID() {
        assertEquals(1, obra.getID());
    }

    @Test
    public void testGetNombre() {
        assertEquals("Mona Lisa", obra.getNombre());
    }

    @Test
    public void testGetAnio() {
        assertEquals(Integer.valueOf(1503), obra.getAnio());
    }

    @Test
    public void testGetDescripcion() {
        assertEquals("Famous painting", obra.getDescripcion());
    }

    @Test
    public void testGetExterna() {
        assertFalse(obra.getExterna());
    }

    @Test
    public void testGetCuantiaSeguro() {
        assertEquals(Double.valueOf(1000000.0), obra.getCuantiaSeguro());
    }

    @Test
    public void testGetNumeroSeguro() {
        assertEquals("12345", obra.getNumeroSeguro());
    }

    @Test
    public void testGetAutores() {
        Set<Autor> autor = new HashSet<>();
        autor.add(new Autor("Leonardo da Vinci", LocalDate.of(1452, 4, 15), LocalDate.of(1519, 5, 2), "Vinci", "Amboise"));
        obra.setAutores(autor);
        assertEquals((Set<Autor>)autor, obra.getAutores());
    }

    @Test
    public void testSetID() {
        obra.setID(2);
        assertEquals(2, obra.getID());
    }

    @Test
    public void testSetNombre() {
        obra.setNombre("The Starry Night");
        assertEquals("The Starry Night", obra.getNombre());
    }

    @Test
    public void testSetAnio() {
        obra.setAnio(1889);
        assertEquals(Integer.valueOf(1889), obra.getAnio());
    }

    @Test
    public void testSetDescripcion() {
        obra.setDescripcion("Famous painting by Vincent van Gogh");
        assertEquals("Famous painting by Vincent van Gogh", obra.getDescripcion());
    }

    @Test
    public void testSetExterna() {
        obra.setExterna(true);
        assertTrue(obra.getExterna());
    }

    @Test
    public void testSetCuantiaSeguro() {
        obra.setCuantiaSeguro(500000.0);
        assertEquals(Double.valueOf(500000.0), obra.getCuantiaSeguro());
    }

    @Test
    public void testSetNumeroSeguro() {
        obra.setNumeroSeguro("54321");
        assertEquals("54321", obra.getNumeroSeguro());
    }

    @Test
    public void testSetAutores() {
        Set<Autor> newAutores = new HashSet<>();
        newAutores.add(new Autor("Andy Warhol", null, null, null, null));
        newAutores.add(new Autor("Pablo Picasso", null, null, null, null));

        obra.setAutores(newAutores);
        assertEquals(newAutores, obra.getAutores());
    }

    @Test
    public void testGetEstado() {
        assertEquals(Estado.EXPUESTA, obra.getEstado());
    }

    @Test
    public void testSetEstado() {
        obra.setEstado(Estado.RETIRADA);
        assertEquals(Estado.RETIRADA, obra.getEstado());
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

    @Test
    public void testAddAutor() {
        Autor newAutor = new Autor("Leonardo da Vinci", null, null, null, null);
        obra.addAutor(newAutor);
        assertTrue(obra.getAutores().contains(newAutor));
    }

    @Test
    public void testRemoveAutor() {
        Autor autorToRemove = new Autor("John Doe", null, null, null, null);
        obra.removeAuotor(autorToRemove);
        assertFalse(obra.getAutores().contains(autorToRemove));
    }
}