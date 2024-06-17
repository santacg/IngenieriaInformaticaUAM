package test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gui.modelo.exposicion.*;
import gui.modelo.obra.*;
import gui.modelo.sala.Sala;

public class ExposicionTest {
    private Exposicion exposicion;
    private Exposicion exposicionPermanente;
    private Set<SalaExposicion> salas;
    private SalaExposicion sala1;
    private SalaExposicion sala2;

    @BeforeEach
    public void setUp() {
        salas = new HashSet<>();
        Sala salaf1 = new Sala("Sala1", 20, true, 3, 20.0, 20.0, 10.0);
        Sala salaf2 = new Sala("Sala2", 20, false, 5, 10.0, 30.0, 1.0);
        sala1 = new SalaExposicion(salaf1);
        sala2 = new SalaExposicion(salaf2);
        salas.add(sala1);
        salas.add(sala2);
        exposicion = new Exposicion("Exposicion 1", LocalDate.now(), LocalDate.now().plusDays(7), "Descripción",
                TipoExpo.TEMPORAL, 10.0);
        exposicionPermanente = new Exposicion("Exposicion 2", LocalDate.now(), LocalDate.MAX, "Descripción",
                TipoExpo.PERMANENTE, 10.0);
    }

    @Test
    public void testGetNombre() {
        assertEquals("Exposicion 1", exposicion.getNombre());
    }

    @Test
    public void testSetNombre() {
        exposicion.setNombre("Exposicion 2");
        assertEquals("Exposicion 2", exposicion.getNombre());
    }

    @Test
    public void testGetFechaInicio() {
        assertNotNull(exposicion.getFechaInicio());
    }

    @Test
    public void testSetFechaInicio() {
        LocalDate newFechaInicio = LocalDate.now().plusDays(1);
        exposicion.setFechaInicio(newFechaInicio);
        assertEquals(newFechaInicio, exposicion.getFechaInicio());
    }

    @Test
    public void testGetFechaFin() {
        assertNotNull(exposicion.getFechaFin());
    }

    @Test
    public void testSetFechaFin() {
        LocalDate newFechaFin = LocalDate.now().plusDays(14);
        exposicion.setFechaFin(newFechaFin);
        assertEquals(newFechaFin, exposicion.getFechaFin());
    }

    @Test
    public void testGetDescripcion() {
        assertEquals("Descripción", exposicion.getDescripcion());
    }

    @Test
    public void testSetDescripcion() {
        exposicion.setDescripcion("Nueva descripción");
        assertEquals("Nueva descripción", exposicion.getDescripcion());
    }

    @Test
    public void testGetBenificios() {
        assertNull(exposicion.getBenificios());
    }

    @Test
    public void testGetPrecio() {
        assertEquals(10.0, exposicion.getPrecio());
    }

    @Test
    public void testSetPrecio() {
        exposicion.setPrecio(15.0);
        assertEquals(15.0, exposicion.getPrecio());
    }

    @Test
    public void testGetEstado() {
        assertEquals(EstadoExposicion.EN_CREACION, exposicion.getEstado());
    }

    @Test
    public void testGetSalas() {
        assertEquals(salas, exposicion.getSalas());
    }

    @Test
    public void testAddSala() {
        Sala salaf3 = new Sala("Sala3", 40, true, 5, 10.0, 20.0, 10.0);
        SalaExposicion sala3 = new SalaExposicion(salaf3);
        exposicion.addSala(sala3);
        assertTrue(exposicion.getSalas().contains(sala3));
    }

    @Test
    public void testRemoveSala() {
        exposicion.removeSala(sala1);
        assertFalse(exposicion.getSalas().contains(sala1));
    }

    @Test
    public void testGetHorario() {
        assertTrue(exposicion.getHorario().isEmpty());
    }

    @Test
    public void testGetEstadisticas() {
        assertNotNull(exposicion.getEstadisticas());
        assertTrue(exposicion.getEstadisticas() instanceof Estadisticas);
    }

    @Test
    public void testGetTipo() {
        assertEquals(TipoExpo.TEMPORAL, exposicion.getTipo());
    }

    @Test
    public void testGetDescuento() {
        assertNull(exposicion.getDescuento());
    }

    @Test
    public void testSetDescuento() {
        DescuentoDia descuento = new DescuentoDia(20.0, 10);
        assertEquals(descuento, exposicion.getDescuento());
    }

    @Test
    public void testExpoPublicarExito() {
        exposicion.setEstado(EstadoExposicion.EN_CREACION);
        assertTrue(exposicion.expoPublicar());
        assertEquals(EstadoExposicion.PUBLICADA, exposicion.getEstado());
    }

    @Test
    public void testExpoPublicarFallo() {
        exposicion.setEstado(EstadoExposicion.PUBLICADA);
        assertFalse(exposicion.expoPublicar());
    }

    @Test
    public void testExpoCancelarExito() {
        exposicion.setEstado(EstadoExposicion.PUBLICADA);
        LocalDate fechaCancelacion = LocalDate.now().plusDays(10);
        assertTrue(exposicion.expoCancelar(fechaCancelacion));
        assertEquals(EstadoExposicion.CANCELADA, exposicion.getEstado());
    }

    @Test
    public void testExpoCancelarFalloEstadoEnCreacion() {
        exposicion.setEstado(EstadoExposicion.EN_CREACION);
        assertFalse(exposicion.expoCancelar(LocalDate.now().plusDays(10)));
    }

    @Test
    public void testExpoCancelarFalloFechaAntelacion() {
        exposicion.setEstado(EstadoExposicion.PUBLICADA);
        assertFalse(exposicion.expoCancelar(LocalDate.now().plusDays(5)));
    }

    @Test
    public void testExpoCancelarFalloFechaPosteriorAFechaFin() {
        exposicion.setEstado(EstadoExposicion.PUBLICADA);
        exposicion.setFechaFin(LocalDate.now().plusDays(5));
        assertFalse(exposicion.expoCancelar(LocalDate.now().plusDays(6)));
    }

    @Test
    public void testExpoProrrogarExito() {
        exposicion.setEstado(EstadoExposicion.PUBLICADA);
        LocalDate newFechaFin = LocalDate.now().plusDays(14);
        assertTrue(exposicion.expoProrrogar(newFechaFin));
        assertEquals(EstadoExposicion.PRORROGADA, exposicion.getEstado());
        assertEquals(newFechaFin, exposicion.getFechaFin());
    }

    @Test
    public void testExpoProrrogarFalloEstadoInvalido() {
        exposicion.setEstado(EstadoExposicion.EN_CREACION);
        assertFalse(exposicion.expoProrrogar(LocalDate.now().plusDays(14)));
    }

    @Test
    public void testExpoProrrogarFalloFechaInvalida() {
        exposicion.setEstado(EstadoExposicion.PUBLICADA);
        assertFalse(exposicion.expoProrrogar(LocalDate.now()));
    }

    @Test
    public void testExpoCerrarTemporalmenteExito() {
        exposicion.setEstado(EstadoExposicion.PUBLICADA);
        LocalDate fechaInicioCierre = LocalDate.now().plusDays(1);
        LocalDate fechaFinCierre = LocalDate.now().plusDays(5);
        assertTrue(exposicion.expoCerrarTemporalmente(fechaInicioCierre, fechaFinCierre));
        assertEquals(EstadoExposicion.CERRADATEMPORALMENTE, exposicion.getEstado());
    }

    @Test
    public void testExpoCerrarTemporalmenteFalloEstadoInvalido() {
        exposicion.setEstado(EstadoExposicion.EN_CREACION);
        assertFalse(exposicion.expoCerrarTemporalmente(LocalDate.now().plusDays(1), LocalDate.now().plusDays(5)));
    }

    @Test
    public void testExpoCerrarTemporalmenteFalloFechasIncorrectas() {
        exposicion.setEstado(EstadoExposicion.PUBLICADA);
        assertFalse(exposicion.expoCerrarTemporalmente(LocalDate.now().plusDays(5), LocalDate.now().plusDays(1)));
    }

    @Test
    public void testExpoCerrarTemporalmenteFalloFechaFueraDeRango() {
        exposicion.setEstado(EstadoExposicion.PUBLICADA);
        LocalDate fechaInicioCierre = LocalDate.now().minusDays(1); 
        LocalDate fechaFinCierre = LocalDate.now().plusDays(10);
        assertFalse(exposicion.expoCerrarTemporalmente(fechaInicioCierre, fechaFinCierre));
    }

    @Test
    public void testExpoPermanente() {
        exposicion.expoPermanente();
        assertEquals(TipoExpo.PERMANENTE, exposicion.getTipo());
        assertEquals(exposicion.getFechaFin(), LocalDate.MAX);
    }

    @Test
    public void testExpoTemporal() {
        exposicionPermanente.expoTemporal(LocalDate.now().plusDays(7));
        assertEquals(TipoExpo.TEMPORAL, exposicion.getTipo());
    }

}