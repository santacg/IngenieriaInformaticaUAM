package src.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import src.CentroExposicion.SorteoExpo;

import src.Exposicion.Exposicion;
import src.Exposicion.SalaExposicion;
import src.Exposicion.TipoExpo;
import src.Sala.Sala;

public class SorteoExpoTest {
    private SorteoExpo sorteo;
    private Exposicion exposicion;
    private Set<SalaExposicion> salas; 

    @BeforeEach
    public void setUp() {
        salas = new HashSet<>();
        Sala salaf1 = new Sala("Sala1", 20, 20, 30, true, 3, 20.0, 20.0);
        SalaExposicion sala1 = new SalaExposicion(salaf1);
        salas.add(sala1);
        exposicion = new Exposicion("Exposicion 1", LocalDate.now(), LocalDate.now().plusDays(7), "Descripción", salas, TipoExpo.TEMPORAL, 10.0);
        sorteo = new SorteoExpo(exposicion, LocalDate.of(2022, 10, 15), 1);
    }

    @Test
    public void testGetFechaLimite() {
        assertEquals(LocalDate.now().plusDays(7), sorteo.getFechaLimite());
    }

    @Test
    public void testGetFechaSorteo() {
        assertEquals(LocalDate.of(2022, 10, 15), sorteo.getFechaSorteo());
    }

    @Test
    public void testGetN_entradas() {
        assertEquals(1, sorteo.getN_entradas());
    }

    @Test
    public void testGetExposicion() {
        assertEquals(exposicion, sorteo.getExposicion());
    }

    @Test
    public void testSetExposicion() {
        Exposicion newExpo = new Exposicion("Exposicion 2", LocalDate.now(), LocalDate.now().plusDays(14), "Descripción 2", salas, TipoExpo.TEMPORAL, 20.0);
        sorteo.setExposicion(newExpo);
        assertEquals(newExpo, sorteo.getExposicion());
    }

}