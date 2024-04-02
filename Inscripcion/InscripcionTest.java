package Inscripcion;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Clase InscripcionTest.
 * Esta clase contiene pruebas unitarias que validan la funcionalidad de los
 * métodos de la clase {@link Inscripcion}.
 */
public class InscripcionTest {
    private Inscripcion inscripcion;

    /**
     * Configura el entorno de pruebas. Este método se ejecuta antes de cada prueba.
     * Inicializa una instancia de {@link Inscripcion} con 5 entradas para ser usada
     * en los tests.
     */
    @BeforeEach
    public void setUp() {
        Integer nEntradas = 5;
        inscripcion = new Inscripcion(nEntradas);
    }

    /**
     * Prueba el método getnEntradas para asegurarse de que retorna el número
     * correcto de entradas. Espera como resultado el número de entradas con el que
     * fue inicializada la inscripción.
     */
    @Test
    public void testGetnEntradas() {
        Integer expected = 5;
        Integer actual = inscripcion.getnEntradas();
        assertEquals(expected, actual);
    }

    /**
     * Prueba el método setnEntradas para verificar que actualiza correctamente el
     * número de entradas. Cambia el número de entradas de la inscripción y verifica
     * que el cambio se refleje correctamente.
     */
    @Test
    public void testSetnEntradas() {
        Integer newNEntradas = 10;
        inscripcion.setnEntradas(newNEntradas);
        Integer actual = inscripcion.getnEntradas();
        assertEquals(newNEntradas, actual);
    }
}