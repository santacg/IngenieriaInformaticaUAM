package src.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import src.Obra.Autor;
import src.Obra.Cuadro;
import src.Obra.Estado;
import src.Obra.Obra;

public class ObraTest {
    private Obra obra;
    private Autor autor;

    @BeforeEach
    public void setUp() {
        Set<Obra> obras = new HashSet<>();
        LocalDate fechaNacimiento = LocalDate.of(1990, 1, 1);
        LocalDate fechaFallecimiento = LocalDate.of(2020, 12, 31);
        obra = new Cuadro("Mona Lisa", 1503, "Cuadro famoso", false, 1000000.0, "12345", 1.5, 1.2, 26, 20, 90, 20, "óleo");
        autor = new Autor("Juan Garnizo", fechaNacimiento, fechaFallecimiento, "Madrid", "Barcelona", obras);
        obra.addAutor(autor);
        obras.add(obra);
    }

    @Test
    public void testGetNombre() {
        String expectedNombre = "Mona Lisa";
        String actualNombre = obra.getNombre();
        assertEquals(expectedNombre, actualNombre);
    }

    @Test
    public void testGetAnio() {
        Integer expectedAnio = 1503;
        Integer actualAnio = obra.getAnio();
        assertEquals(expectedAnio, actualAnio);
    }

    @Test
    public void testGetDescripcion() {
        String expectedDescripcion = "Cuadro famoso";
        String actualDescripcion = obra.getDescripcion();
        assertEquals(expectedDescripcion, actualDescripcion);
    }

    @Test
    public void testGetExterna() {
        Boolean expectedExterna = false;
        Boolean actualExterna = obra.getExterna();
        assertEquals(expectedExterna, actualExterna);
    }

    @Test
    public void testGetCuantiaSeguro() {
        Double expectedCuantiaSeguro = 1000000.0;
        Double actualCuantiaSeguro = obra.getCuantiaSeguro();
        assertEquals(expectedCuantiaSeguro, actualCuantiaSeguro);
    }

    @Test
    public void testGetNumeroSeguro() {
        String expectedNumeroSeguro = "12345";
        String actualNumeroSeguro = obra.getNumeroSeguro();
        assertEquals(expectedNumeroSeguro, actualNumeroSeguro);
    }

    @Test
    public void testGetAutores() {
        Set<Autor> expectedAutores = new HashSet<>();
        expectedAutores.add(autor);
        Set<Autor> actualAutores = obra.getAutores();
        assertEquals(expectedAutores, actualAutores);
    }

    @Test
    public void testSetNombre() {
        String newNombre = "New Name";
        obra.setNombre(newNombre);
        String actualNombre = obra.getNombre();
        assertEquals(newNombre, actualNombre);
    }

    @Test
    public void testSetAnio() {
        Integer newAnio = 2022;
        obra.setAnio(newAnio);
        Integer actualAnio = obra.getAnio();
        assertEquals(newAnio, actualAnio);
    }

    @Test
    public void testSetDescripcion() {
        String newDescripcion = "New Description";
        obra.setDescripcion(newDescripcion);
        String actualDescripcion = obra.getDescripcion();
        assertEquals(newDescripcion, actualDescripcion);
    }

    @Test
    public void testSetExterna() {
        Boolean newExterna = true;
        obra.setExterna(newExterna);
        Boolean actualExterna = obra.getExterna();
        assertEquals(newExterna, actualExterna);
    }

    @Test
    public void testSetCuantiaSeguro() {
        Double newCuantiaSeguro = 2000000.0;
        obra.setCuantiaSeguro(newCuantiaSeguro);
        Double actualCuantiaSeguro = obra.getCuantiaSeguro();
        assertEquals(newCuantiaSeguro, actualCuantiaSeguro);
    }

    @Test
    public void testSetNumeroSeguro() {
        String newNumeroSeguro = "54321";
        obra.setNumeroSeguro(newNumeroSeguro);
        String actualNumeroSeguro = obra.getNumeroSeguro();
        assertEquals(newNumeroSeguro, actualNumeroSeguro);
    }

    @Test
    public void testSetAutores() {
        Set<Autor> newAutores = new HashSet<>();
        Autor autor2 = new Autor("Maria Lopez", LocalDate.of(1500, 1, 1), LocalDate.of(1550, 12, 31), "Madrid", "Barcelona", new HashSet<>());
        newAutores.add(autor2);
        obra.setAutores(newAutores);
        Set<Autor> actualAutores = obra.getAutores();
        assertEquals(newAutores, actualAutores);
    }

    @Test
    public void testRetirarObra() {
        Estado expectedEstado = Estado.RETIRADA;
        obra.retirarObra();
        Estado actualEstado = obra.getEstado();
        assertEquals(expectedEstado, actualEstado);
    }

    @Test
    public void testPrestarObra() {
        Estado expectedEstado = Estado.PRESTADA;
        obra.prestarObra();
        Estado actualEstado = obra.getEstado();
        assertEquals(expectedEstado, actualEstado);
    }

    @Test
    public void testAlmacenarObra() {
        Estado expectedEstado = Estado.ALMACENADA;
        obra.almacenarObra();
        Estado actualEstado = obra.getEstado();
        assertEquals(expectedEstado, actualEstado);
    }

    @Test
    public void testExponerObra() {
        Estado expectedEstado = Estado.EXPUESTA;
        obra.exponerObra();
        Estado actualEstado = obra.getEstado();
        assertEquals(expectedEstado, actualEstado);
    }

    @Test
    public void testRestaurarObra() {
        Estado expectedEstado = Estado.RESTAURACION;
        obra.restaurarObra();
        Estado actualEstado = obra.getEstado();
        assertEquals(expectedEstado, actualEstado);
    }

    @Test
    public void testAddAutor() {
        Autor autor2 = new Autor("Maria Lopez", LocalDate.of(1500, 1, 1), LocalDate.of(1550, 12, 31), "Madrid", "Barcelona", new HashSet<>());
        obra.addAutor(autor2);
        Set<Autor> expectedAutores = new HashSet<>();
        expectedAutores.add(autor);
        expectedAutores.add(autor2);
        Set<Autor> actualAutores = obra.getAutores();
        assertEquals(expectedAutores, actualAutores);
    }

    @Test
    public void testRemoveAutor() {
        obra.removeAutor(autor);
        Set<Autor> expectedAutores = new HashSet<>();
        Set<Autor> actualAutores = obra.getAutores();
        assertEquals(expectedAutores, actualAutores);
    }

}