package src.Test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import src.Obra.Autor;
import src.Obra.Cuadro;
import src.Obra.Escultura;
import src.Obra.Obra;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class AutorTest {

    private Autor autor;
    private Obra obra1;
    private Obra obra2;

    @BeforeEach
    public void setUp() {
        Set<Obra> obras = new HashSet<>();
        LocalDate fechaNacimiento = LocalDate.of(1990, 1, 1);
        LocalDate fechaFallecimiento = LocalDate.of(2020, 12, 31);
        Escultura obra1 = new Escultura("MA", 
                        2025, 
                        "Marco Antonio", 
                        false, 
                        3000000.0, 
                        "5678", 
                        5.2, 
                        2.8, 
                        2.6, 
                        24, 
                        17, 
                        80, 
                        10, 
                        "cuarzo");
        Cuadro obra2 = new Cuadro("El Guernica",
                        1937,
                        "Una pintura de Picasso",
                        false,
                        2000000.0,
                        "123456789",
                        349.3,
                        776.6,
                        25,
                        15,
                        60,
                        40,
                        "Óleo");
        obras.add(obra1);
        obras.add(obra2);
        autor = new Autor("Juan Garnizo", fechaNacimiento, fechaFallecimiento, "Madrid", "Barcelona", obras);
    }

    @Test
    public void testGetNombre() {
        assertEquals("Juan Garnizo", autor.getNombre());
    }

    @Test
    public void testSetNombre() {
        autor.setNombre("Carlos García");
        assertEquals("Carlos García", autor.getNombre());
    }

    @Test
    public void testGetFechaNacimiento() {
        assertEquals(LocalDate.of(1990, 1, 1), autor.getFechaNacimiento());
    }

    @Test
    public void testSetFechaNacimiento() {
        LocalDate newFechaNacimiento = LocalDate.of(1985, 5, 10);
        autor.setFechaNacimiento(newFechaNacimiento);
        assertEquals(newFechaNacimiento, autor.getFechaNacimiento());
    }

    @Test
    public void testGetFechaFallecimiento() {
        assertEquals(LocalDate.of(2020, 12, 31), autor.getFechaFallecimiento());
    }

    @Test
    public void testSetFechaFallecimiento() {
        LocalDate newFechaFallecimiento = LocalDate.of(2022, 3, 15);
        autor.setFechaFallecimiento(newFechaFallecimiento);
        assertEquals(newFechaFallecimiento, autor.getFechaFallecimiento());
    }

    @Test
    public void testGetLugarNacimiento() {
        assertEquals("Madrid", autor.getLugarNacimiento());
    }

    @Test
    public void testSetLugarNacimiento() {
        autor.setLugarNacimiento("Barcelona");
        assertEquals("Barcelona", autor.getLugarNacimiento());
    }

    @Test
    public void testGetLugarFallecimiento() {
        assertEquals("Barcelona", autor.getLugarFallecimiento());
    }

    @Test
    public void testSetLugarFallecimiento() {
        autor.setLugarFallecimiento("Madrid");
        assertEquals("Madrid", autor.getLugarFallecimiento());
    }


    @Test
    public void testSetObras() {
        Set<Obra> newObras = new HashSet<>();
        Escultura obra3 = new Escultura("Escultura 3",
                        2023,
                        "Escultor 3",
                        false,
                        1500000.0,
                        "987654321",
                        3.5,
                        1.8,
                        1.2,
                        10,
                        8,
                        30,
                        5,
                        "mármol");
        newObras.add(obra3);
        autor.setObras(newObras);
        Set<Obra> obras = autor.getObras();
        assertEquals(1, obras.size());
        assertTrue(obras.contains(obra3));
    }

    @Test
    public void testAddObra() {
        Escultura obra3 = new Escultura("Escultura 3",
                        2023,
                        "Escultor 3",
                        false,
                        1500000.0,
                        "987654321",
                        3.5,
                        1.8,
                        1.2,
                        10,
                        8,
                        30,
                        5,
                        "mármol");
        autor.addObra(obra3);
        Set<Obra> obras = autor.getObras();
        assertEquals(3, obras.size());
        assertTrue(obras.contains(obra3));
    }

    @Test
    public void testRemoveObra() {
        autor.removeObra(obra1);
        Set<Obra> obras = autor.getObras();
        assertEquals(2, obras.size());
        assertFalse(obras.contains(obra1));

    }
}