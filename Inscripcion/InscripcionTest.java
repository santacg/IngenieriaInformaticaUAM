package Inscripcion;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Expofy.ClienteRegistrado;

/**
 * Clase InscripcionTest.
 * Esta clase contiene pruebas unitarias que validan la funcionalidad de los
 * métodos de la clase {@link Inscripcion}.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 * 
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
        int nEntradas = 5;
        ClienteRegistrado cliente1 = new ClienteRegistrado("55025166N", false, "12345", false, LocalDate.of(2020, 3, 14), null);
        inscripcion = new Inscripcion(nEntradas, cliente1);
    }

    /**
     * Prueba el método getnEntradas para asegurarse de que retorna el número
     * correcto de entradas. Espera como resultado el número de entradas con el que
     * fue inicializada la inscripción.
     */
    @Test
    public void testGetnEntradas() {
        int expected = 5;
        int actual = inscripcion.getnEntradas();
        assertEquals(expected, actual);
    }

    /**
     * Prueba el método setnEntradas para verificar que actualiza correctamente el
     * número de entradas. Cambia el número de entradas de la inscripción y verifica
     * que el cambio se refleje correctamente.
     */
    @Test
    public void testSetnEntradas() {
        int newNEntradas = 10;
        inscripcion.setnEntradas(newNEntradas);
        int actual = inscripcion.getnEntradas();
        assertEquals(newNEntradas, actual);
    }
}