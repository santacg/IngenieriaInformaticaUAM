package Entrada;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Expofy.ClienteRegistrado;
import TarjetaDeCredito.TarjetaDeCredito;

public class EntradaTest {
    private Entrada entrada;

    @BeforeEach
    public void setUp() {
        Integer IDEntrada = 1;
        entrada = new Entrada(IDEntrada);
    }

    @Test
    public void testGetIDEntrada() {
        Integer expected = 1;
        Integer actual = entrada.getIDEntrada();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetTarjetaDeCredito() {
        assertNull(entrada.getTarjetaDeCredito());
    }

    @Test
    public void testSetTarjetaDeCredito() {
        TarjetaDeCredito tarjeta = new TarjetaDeCredito("123", LocalDate.of(2021, 1, 1), 123);
        entrada.setTarjetaDeCredito(tarjeta);
        assertEquals(tarjeta, entrada.getTarjetaDeCredito());
    }

    @Test
    public void testGetnEntradas() {
        assertNull(entrada.getnEntradas());
    }

    @Test
    public void testSetnEntradas() {
        Integer nEntradas = 5;
        entrada.setnEntradas(nEntradas);
        assertEquals(nEntradas, entrada.getnEntradas());
    }

    @Test
    public void testSetnEntradasWithInvalidValue() {
        assertThrows(IllegalArgumentException.class, () -> {
            entrada.setnEntradas(-1);
        });
    }

    @Test
    public void testGetClienteRegistrado() {
        assertNull(entrada.getClienteRegistrado());
    }

    @Test
    public void testSetClienteRegistrado() {
        ClienteRegistrado cliente = new ClienteRegistrado("Carlos", null, "123", false, null, null);
        entrada.setClienteRegistrado(cliente);
        assertEquals(cliente, entrada.getClienteRegistrado());
    }
}