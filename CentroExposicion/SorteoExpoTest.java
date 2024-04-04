package CentroExposicion;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Exposicion.EstadoExposicion;

public class SorteoExpoTest {
    private SorteoExpo sorteo;
    private EstadoExposicion exposicion;

    @BeforeEach
    public void setUp() {
        exposicion = new Exposicion("Exposición de Arte", LocalDate.of(2022, 10, 1), LocalDate.of(2022, 10, 31));
        sorteo = new SorteoExpo(exposicion, LocalDate.of(2022, 10, 15));
    }

    @Test
    public void testGetFechaLimite() {
        LocalDate expectedFechaLimite = LocalDate.of(2022, 10, 31);
        assertEquals(expectedFechaLimite, sorteo.getFechaLimite());
    }
}