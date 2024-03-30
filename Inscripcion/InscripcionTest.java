package Inscripcion;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InscripcionTest {
    private Inscripcion inscripcion;

    @BeforeEach
    public void setUp() {
        Integer nEntradas = 5;
        inscripcion = new Inscripcion(nEntradas);
    }

    @Test
    public void testGetnEntradas() {
        Integer expected = 5;
        Integer actual = inscripcion.getnEntradas();
        assertEquals(expected, actual);
    }

    @Test
    public void testSetnEntradas() {
        Integer newNEntradas = 10;
        inscripcion.setnEntradas(newNEntradas);
        Integer actual = inscripcion.getnEntradas();
        assertEquals(newNEntradas, actual);
    }
}