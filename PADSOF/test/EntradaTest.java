package test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gui.modelo.expofy.ClienteRegistrado;
import gui.modelo.exposicion.Entrada;
import gui.modelo.tarjetaDeCredito.TarjetaDeCredito;

public class EntradaTest {
    private Entrada entrada;
    private TarjetaDeCredito tarjeta;
    private ClienteRegistrado cliente;

    @BeforeEach
    public void setUp() {
        tarjeta = new TarjetaDeCredito("12345", LocalDate.of(2025, 8, 24), 123);
        cliente = new ClienteRegistrado("Juan García", false, "123", false, null, null);
        entrada = new Entrada(cliente, tarjeta);
    }

    @Test
    public void testGetIDEntrada() {
        assertEquals(0, entrada.getIDEntrada());
    }

    @Test
    public void testGetTarjetaDeCredito() {
        TarjetaDeCredito tarjeta = entrada.getTarjetaDeCredito();
        assertEquals(tarjeta, entrada.getTarjetaDeCredito());
    }

    @Test
    public void testGetClienteRegistrado() {
        ClienteRegistrado cliente = new ClienteRegistrado("Juan García", false, "123", false, null, null);
        assertEquals(cliente, entrada.getClienteRegistrado());
    }

    @Test
    public void testGetFechaCompra() {
        assertEquals(LocalDate.now(), entrada.getFechaDeCompra());
    }
}