package src.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import src.Obra.*;

public class ObraTest {
    private Obra obra1;
    private Obra obra2;
    private Obra obra3;
    private Obra obra4;

    @BeforeEach
    public void setUp() {
        obra1 = new Cuadro("Mona Lisa", 1503, "Cuadro famoso", false, 1000000.0, "12345", 1.5, 1.2, 26, 20, 90, 20,
                "óleo", "Leonardo da Vinci");
        obra2 = new Escultura("David", 1504, "Escultura famosa", true, 2000000.0, "54321", 5.0, 2.0, 3.0, 30, 20, 80,
                10, "mármol", "Miguel Ángel");
        obra3 = new Fotografia("La niña del aro", 2002, "Fotografía famosa", false, 500000.0, "67890", 1.0, 1.0, 25, 15,
                70, 20, true, "Javier");
        obra4 = new Audiovisual("El padrino", 1972, "Película famosa", false, 10000000.0, "13579", "2h 55m", "inglés",
                "Francis Ford Coppola, Mario Puzo");
    }

    @Test
    public void testGetNombre() {
        assertEquals("Mona Lisa", obra1.getNombre());
        assertEquals("David", obra2.getNombre());
        assertEquals("La niña del aro", obra3.getNombre());
        assertEquals("El padrino", obra4.getNombre());
    }

    @Test
    public void testGetAnio() {
        assertEquals(1503, obra1.getAnio());
        assertEquals(1504, obra2.getAnio());
        assertEquals(2002, obra3.getAnio());
        assertEquals(1972, obra4.getAnio());
    }

    @Test
    public void testGetDescripcion() {
        assertEquals("Cuadro famoso", obra1.getDescripcion());
        assertEquals("Escultura famosa", obra2.getDescripcion());
        assertEquals("Fotografía famosa", obra3.getDescripcion());
        assertEquals("Película famosa", obra4.getDescripcion());
    }

    @Test
    public void testGetExterna() {
        assertFalse(obra1.getExterna());
        assertTrue(obra2.getExterna());
        assertFalse(obra3.getExterna());
        assertFalse(obra4.getExterna());
    }

    @Test
    public void testGetCuantiaSeguro() {
        assertEquals(1000000.0, obra1.getCuantiaSeguro());
        assertEquals(2000000.0, obra2.getCuantiaSeguro());
        assertEquals(500000.0, obra3.getCuantiaSeguro());
        assertEquals(10000000.0, obra4.getCuantiaSeguro());
    }

    @Test
    public void testGetNumeroSeguro() {
        assertEquals("12345", obra1.getNumeroSeguro());
        assertEquals("54321", obra2.getNumeroSeguro());
        assertEquals("67890", obra3.getNumeroSeguro());
        assertEquals("13579", obra4.getNumeroSeguro());
    }

    @Test
    public void testGetAutores() {
        Set<String> autores1 = new HashSet<>();
        autores1.add("Leonardo da Vinci");
        assertEquals(autores1, obra1.getAutores());

        Set<String> autores2 = new HashSet<>();
        autores2.add("Miguel Ángel");
        assertEquals(autores2, obra2.getAutores());

        Set<String> autores3 = new HashSet<>();
        autores3.add("Javier");
        assertEquals(autores3, obra3.getAutores());

        Set<String> autores4 = new HashSet<>();
        autores4.add("Francis Ford Coppola");
        autores4.add("Mario Puzo");
    }

    @Test
    public void testGetEstado() {
        assertEquals(Estado.ALMACENADA, obra1.getEstado());
        assertEquals(Estado.ALMACENADA, obra2.getEstado());
        assertEquals(Estado.ALMACENADA, obra3.getEstado());
        assertEquals(Estado.ALMACENADA, obra4.getEstado());
    }

    @Test
    public void testSetNombre() {
        obra1.setNombre("La Gioconda");
        assertEquals("La Gioconda", obra1.getNombre());
    }

    @Test
    public void testSetAnio() {
        obra1.setAnio(1506);
        assertEquals(1506, obra1.getAnio());
    }

    @Test
    public void testSetDescripcion() {
        obra1.setDescripcion("Otro cuadro famoso");
        assertEquals("Otro cuadro famoso", obra1.getDescripcion());
    }

    @Test
    public void testSetExterna() {
        obra1.setExterna(true);
        assertTrue(obra1.getExterna());
    }

    @Test
    public void testSetCuantiaSeguro() {
        obra1.setCuantiaSeguro(2000000.0);
        assertEquals(2000000.0, obra1.getCuantiaSeguro());
    }

    @Test
    public void testSetNumeroSeguro() {
        obra1.setNumeroSeguro("54321");
        assertEquals("54321", obra1.getNumeroSeguro());
    }

    @Test
    public void testSetAutores() {
        Set<String> autores = new HashSet<>();
        autores.add("Leonardo da Vinci");
        autores.add("Rafael Sanzio");
        obra1.setAutores("Leonardo da Vinci", "Rafael Sanzio");
        assertEquals(autores, obra1.getAutores());
    }

    @Test
    public void testRetirarObra() {
        obra2.retirarObra();
        assertEquals(Estado.RETIRADA, obra2.getEstado());
    }

    @Test
    public void testAlmacenarObra() {
        obra1.exponerObra();
        obra1.almacenarObra();
        assertEquals(Estado.ALMACENADA, obra1.getEstado());
    }

    @Test
    public void testPrestarObra() {
        obra1.prestarObra();
        assertEquals(Estado.PRESTADA, obra1.getEstado());
    }

    @Test
    public void testExponerObra() {
        obra1.exponerObra();
        assertEquals(Estado.EXPUESTA, obra1.getEstado());
    }

    @Test
    public void testRestaurarObra() {
        obra1.restaurarObra();
        assertEquals(Estado.RESTAURACION, obra1.getEstado());
    }

    @Test
    public void testPrestarObraExterna() {
        obra2.prestarObra();
        assertEquals(Estado.ALMACENADA, obra2.getEstado());
    }

    @Test
    public void testGetTecnica() {
        assertEquals("óleo", ((Cuadro) obra1).getTecnica());
    }

    @Test
    public void testSetTecnica() {
        ((Cuadro) obra1).setTecnica("acuarela");
        assertEquals("acuarela", ((Cuadro) obra1).getTecnica());
    }

    @Test
    public void testGetMaterial() {
        assertEquals("mármol", ((Escultura) obra2).getMaterial());
    }

    @Test
    public void testSetMaterial() {
        ((Escultura) obra2).setMaterial("bronce");
        assertEquals("bronce", ((Escultura) obra2).getMaterial());
    }

    @Test
    public void testGetProfundidad() {
        assertEquals(3.0, ((Escultura) obra2).getProfundidad());
    }

    @Test
    public void testSetProfundidad() {
        ((Escultura) obra2).setProfundidad(4.0);
        assertEquals(4.0, ((Escultura) obra2).getProfundidad());
    }

    @Test
    public void testGetHumedadMaxima() {
        assertEquals(70, ((Fotografia) obra3).getHumedadMaxima());
    }

    @Test
    public void testGetColor() {
        assertTrue(((Fotografia) obra3).getColor());
    }

    @Test
    public void testSetColor() {
        ((Fotografia) obra3).setColor(false);
        assertFalse(((Fotografia) obra3).getColor());
    }

    @Test
    public void testGetDuracion() {
        assertEquals("2h 55m", ((Audiovisual) obra4).getDuracion());
    }

    @Test
    public void testSetDuracion() {
        ((Audiovisual) obra4).setDuracion("3h 10m");
        assertEquals("3h 10m", ((Audiovisual) obra4).getDuracion());
    }

    @Test
    public void testGetIdioma() {
        assertEquals("inglés", ((Audiovisual) obra4).getIdioma());
    }

    @Test
    public void testSetIdioma() {
        ((Audiovisual) obra4).setIdioma("español");
        assertEquals("español", ((Audiovisual) obra4).getIdioma());
    }

}