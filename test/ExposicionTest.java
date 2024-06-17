package test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gui.modelo.exposicion.*;
import gui.modelo.obra.*;
import gui.modelo.sala.Sala;

public class ExposicionTest {
    private Exposicion exposicion;
    private Exposicion exposicionSinSalas;
    private Exposicion exposicionSinObras;
    private Exposicion exposicionPermanente;
    private DescuentoDia descuento;
    private Set<SalaExposicion> salas;
    private SalaExposicion sala1;
    private SalaExposicion sala2;
    private Obra obra1;

    @BeforeEach
    public void setUp() {
        salas = new HashSet<>();
        Sala salaf1 = new Sala("Sala1", 20, true, 3, 20.0, 20.0, 10.0);

        sala1 = new SalaExposicion(salaf1);
        salas.add(sala1);

        exposicion = new Exposicion("Exposicion 1", LocalDate.now(), LocalDate.now().plusDays(30), "Descripción",
                TipoExpo.TEMPORAL, 10.0);
        exposicionPermanente = new Exposicion("Exposicion 2", LocalDate.now(), LocalDate.MAX, "Descripción",
                TipoExpo.PERMANENTE, 10.0);
        exposicionSinObras = new Exposicion("Exposicion 3", LocalDate.now(), LocalDate.now().plusDays(30),
                "Descripción",
                TipoExpo.TEMPORAL, 10.0);
        exposicionSinSalas = new Exposicion("Exposicion 4", LocalDate.now(), LocalDate.now().plusDays(30),
                "Descripción",
                TipoExpo.TEMPORAL, 10.0);

        exposicion.addSala(sala1);
        exposicionSinObras.addSala(sala2);

        obra1 = new Cuadro("Mona Lisa", 1503, "Cuadro famoso", false, 1000000.0, "12345", 1.5, 1.2, 26, 20, 90, 20,
                "óleo", "Leonardo da Vinci");
        sala1.addObra(obra1);

        descuento = new DescuentoDia(20.0, 10);
        exposicion.setDescuento(descuento);
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
    public void testRemoveSalaSinObras() {
        sala1.removeObra(obra1);
        exposicion.removeSala(sala1);
        assertFalse(exposicion.getSalas().contains(sala1));
    }

    @Test
    public void testRemoveSalaConObras() {
        assertFalse(exposicion.removeSala(sala1));
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
    public void testGetTipoPermanente() {
        assertEquals(TipoExpo.PERMANENTE, exposicionPermanente.getTipo());
    }

    @Test
    public void testGetDescuento() {
        Descuento descuento = new DescuentoDia(20.00, 10);
        assertEquals(descuento, exposicion.getDescuento());
    }

    @Test
    public void testSetDescuento() {
        DescuentoDia descuento = new DescuentoDia(20.0, 10);
        exposicion.setDescuento(descuento);
        assertEquals(descuento, exposicion.getDescuento());
    }

    @Test
    public void testExpoPublicarExito() {
        exposicion.setEstado(EstadoExposicion.EN_CREACION);
        try {
            exposicion.expoPublicar();
            assertTrue(EstadoExposicion.PUBLICADA == exposicion.getEstado());
        } catch (Exception e) {
        }
    }

    @Test
    public void testExpoSinSalasPublicarFallo() {
        try {
            exposicionSinSalas.expoPublicar();
        } catch (Exception e) {
        }

        assertFalse(EstadoExposicion.PUBLICADA == exposicionSinSalas.getEstado());
    }

    @Test
    public void testExpoSinObrasPublicarFallo() {
        try {
            exposicionSinObras.expoPublicar();
        } catch (Exception e) {
        }

        assertFalse(EstadoExposicion.PUBLICADA == exposicionSinObras.getEstado());
    }

    @Test
    public void testExpoCancelarExito() {
        try {
            exposicion.expoPublicar();
            LocalDate fechaCancelacion = LocalDate.now().plusDays(10);
            exposicion.expoCancelar(fechaCancelacion);
        } catch (Exception e) {
        }

        assertTrue(EstadoExposicion.CANCELADA == exposicion.getEstado());
    }

    @Test
    public void testExpoCancelarFalloFecha() {
        try {
            exposicion.expoPublicar();
            LocalDate fechaCancelacion = LocalDate.now().plusDays(4);
            exposicion.expoCancelar(fechaCancelacion);
        } catch (Exception e) {
        }

        assertFalse(EstadoExposicion.CANCELADA == exposicion.getEstado());
        assertEquals(EstadoExposicion.PUBLICADA, exposicion.getEstado());
    }

    @Test
    public void testExpoCancelarFalloEstadoEnCreacion() {
        try {
            exposicionSinObras.expoCancelar(LocalDate.now().plusDays(10));
            assertFalse(EstadoExposicion.CANCELADA == exposicionSinObras.getEstado());
        } catch (Exception e) {
        }

        assertEquals(EstadoExposicion.EN_CREACION, exposicionSinObras.getEstado());
    }

    @Test
    public void testExpoProrrogarExito() {
        try {
            exposicion.expoPublicar();
            LocalDate newFechaFin = LocalDate.now().plusDays(31);
            exposicion.expoProrrogar(newFechaFin);
            assertTrue(EstadoExposicion.PRORROGADA == exposicion.getEstado());
            assertEquals(newFechaFin, exposicion.getFechaFin());
        } catch (Exception e) {
        }
    }

    @Test
    public void testExpoProrrogarFalloEstadoInvalido() {
        try {
            exposicionSinSalas.expoProrrogar(LocalDate.now().plusDays(34));
            assertFalse(EstadoExposicion.PRORROGADA == exposicionSinSalas.getEstado());
        } catch (Exception e) {
        }
    }

    @Test
    public void testExpoProrrogarFalloFechaInvalida() {
        try {
            exposicion.expoPublicar();
            LocalDate newFechaFin = LocalDate.now().plusDays(29);
            exposicion.expoProrrogar(newFechaFin);
            assertFalse(EstadoExposicion.PRORROGADA == exposicion.getEstado());
        } catch (Exception e) {
        }
    }

    @Test
    public void testExpoCerrarTemporalmenteExito() {
        try {
            exposicion.expoPublicar();
            LocalDate fechaInicioCierre = LocalDate.now().plusDays(1);
            LocalDate fechaFinCierre = LocalDate.now().plusDays(5);
            exposicion.expoCerrarTemporalmente(fechaInicioCierre, fechaFinCierre);
            assertTrue(EstadoExposicion.CERRADATEMPORALMENTE == exposicion.getEstado());
        } catch (Exception e) {
        }
    }

    @Test
    public void testExpoCerrarTemporalmenteFalloEstadoInvalido() {
        try {
            exposicionSinSalas.expoCerrarTemporalmente(LocalDate.now().plusDays(1), LocalDate.now().plusDays(5));
            assertFalse(EstadoExposicion.CERRADATEMPORALMENTE == exposicionSinSalas.getEstado());
        } catch (Exception e) {
        }
    }

    @Test
    public void testExpoCerrarTemporalmenteFalloFechasIncorrectas() {
        try {
            exposicion.expoPublicar();
            LocalDate fechaInicioCierre = LocalDate.now().plusDays(5);
            LocalDate fechaFinCierre = LocalDate.now().plusDays(1);
            exposicion.expoCerrarTemporalmente(fechaInicioCierre, fechaFinCierre);
            assertFalse(EstadoExposicion.CERRADATEMPORALMENTE == exposicion.getEstado());
        } catch (Exception e) {
        }
    }

    @Test
    public void testExpoCerrarTemporalmenteFalloFechaFueraDeRango() {
        try {
            exposicion.expoPublicar();
            LocalDate fechaInicioCierre = LocalDate.now().plusDays(31);
            LocalDate fechaFinCierre = LocalDate.now().plusDays(35);
            exposicion.expoCerrarTemporalmente(fechaInicioCierre, fechaFinCierre);
            assertFalse(EstadoExposicion.CERRADATEMPORALMENTE == exposicion.getEstado());
        } catch (Exception e) {
        }
    }

    @Test
    public void testExpoPermanente() {
        try {
            exposicion.expoPermanente();
            assertEquals(TipoExpo.PERMANENTE, exposicion.getTipo());
            assertEquals(exposicion.getFechaFin(), LocalDate.MAX);
        } catch (Exception e) {
        }
    }

    @Test
    public void testExpoTemporal() {
        try {
            exposicionPermanente.expoTemporal(LocalDate.now().plusDays(7));
            assertEquals(TipoExpo.TEMPORAL, exposicion.getTipo());
        } catch (Exception e) {
        }
    }

}