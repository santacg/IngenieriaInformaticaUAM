package Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import GUI.modelo.expofy.ClienteRegistrado;
import GUI.modelo.exposicion.Entrada;
import GUI.modelo.tarjetaDeCredito.TarjetaDeCredito;

public class EntradaTest {
    private Entrada entrada;
    private TarjetaDeCredito tarjeta;
    private ClienteRegistrado cliente;

    @BeforeEach
    public void setUp() {
        entrada = new Entrada();
        tarjeta = new TarjetaDeCredito("12345", LocalDate.of(2025, 8, 24), 123);
        cliente = new ClienteRegistrado("Juan García", false, "123", false, null, null);
    }

    @Test
    public void testGetIDEntrada() {
        assertNotNull(entrada.getIDEntrada());
    }

    @Test
    public void testGetTarjetaDeCredito() {
        assertNull(entrada.getTarjetaDeCredito());
    }

    @Test
    public void testSetTarjetaDeCredito() {
        entrada.setTarjetaDeCredito(tarjeta);
        assertEquals(tarjeta, entrada.getTarjetaDeCredito());
    }

    @Test
    public void testGetClienteRegistrado() {
        assertNull(entrada.getClienteRegistrado());
    }

    @Test
    public void testAddClienteRegistrado() {
        entrada.addClienteRegistrado(cliente);
        assertEquals(cliente, entrada.getClienteRegistrado());
    }

    @Test
    public void testEquals() {
        Entrada entrada1 = new Entrada();
        Entrada entrada2 = new Entrada();
        assertNotEquals(entrada1, entrada2);
    }

}