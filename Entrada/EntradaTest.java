package Entrada;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Expofy.ClienteRegistrado;
import TarjetaDeCredito.TarjetaDeCredito;

/**
 * Clase EntradaTest.
 * Esta clase contiene pruebas unitarias que validan la funcionalidad de los
 * métodos de la clase {@link Entrada}.
 */

public class EntradaTest {
    private Entrada entrada;

    /**
     * Inicializa un objeto Entrada antes de ejecutar cada prueba.
     * Esta configuración previa garantiza que cada prueba se ejecute con una
     * instancia fresca de Entrada.
     */
    @BeforeEach
    public void setUp() {
        Integer IDEntrada = 1;
        entrada = new Entrada(IDEntrada);
    }

    /**
     * Prueba que el método getIDEntrada retorna el valor correcto asignado en
     * setUp. Verifica que el identificador de la entrada sea el esperado.
     */
    @Test
    public void testGetIDEntrada() {
        Integer expected = 1;
        Integer actual = entrada.getIDEntrada();
        assertEquals(expected, actual);
    }

    /**
     * Prueba que el método getTarjetaDeCredito retorna null si ninguna tarjeta ha
     * sido asignada a la entrada. Verifica el estado inicial de la tarjeta de
     * crédito asociada a la entrada.
     */
    @Test
    public void testGetTarjetaDeCredito() {
        assertNull(entrada.getTarjetaDeCredito());
    }

    /**
     * Prueba la asignación de una tarjeta de crédito a la entrada y su posterior
     * recuperación. Verifica que el método setTarjetaDeCredito funcione
     * correctamente asignando y recuperando la tarjeta de crédito.
     */
    @Test
    public void testSetTarjetaDeCredito() {
        TarjetaDeCredito tarjeta = new TarjetaDeCredito("123", LocalDate.of(2021, 1, 1), 123);
        entrada.setTarjetaDeCredito(tarjeta);
        assertEquals(tarjeta, entrada.getTarjetaDeCredito());
    }

    /**
     * Prueba que el método getnEntradas retorna null si el número de entradas no ha
     * sido establecido. Verifica el estado inicial del número de entradas.
     */
    @Test
    public void testGetnEntradas() {
        assertNull(entrada.getnEntradas());
    }

    /**
     * Prueba la asignación de un número válido de entradas y su correcta
     * recuperación. Verifica que el método setnEntradas guarda y retorna
     * correctamente el número de entradas.
     */
    @Test
    public void testSetnEntradas() {
        Integer nEntradas = 5;
        entrada.setnEntradas(nEntradas);
        assertEquals(nEntradas, entrada.getnEntradas());
    }

    /**
     * Prueba la asignación de un número inválido de entradas, esperando una
     * excepción. Verifica que el método setnEntradas lanza una
     * IllegalArgumentException cuando se pasa un valor inválido.
     */
    @Test
    public void testSetnEntradasWithInvalidValue() {
        assertThrows(IllegalArgumentException.class, () -> {
            entrada.setnEntradas(-1);
        });
    }

    /**
     * Prueba que el método getClienteRegistrado retorna null si ningún cliente ha
     * sido asignado a la entrada. Verifica el estado inicial del cliente registrado
     * asociado a la entrada.
     */
    @Test
    public void testGetClienteRegistrado() {
        assertNull(entrada.getClienteRegistrado());
    }

    /**
     * Prueba la asignación de un cliente registrado a la entrada y su posterior
     * recuperación. Verifica que el método setClienteRegistrado funcione
     * correctamente asignando y recuperando el cliente registrado.
     */
    @Test
    public void testSetClienteRegistrado() {
        ClienteRegistrado cliente = new ClienteRegistrado("Carlos", null, "123", false, null, null);
        entrada.setClienteRegistrado(cliente);
        assertEquals(cliente, entrada.getClienteRegistrado());
    }
}