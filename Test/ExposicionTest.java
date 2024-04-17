package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import src.centroExposicion.DescuentoDia;
import src.exposicion.Estadisticas;
import src.exposicion.EstadoExposicion;
import src.exposicion.Exposicion;
import src.exposicion.Hora;
import src.exposicion.SalaExposicion;
import src.exposicion.TipoExpo;
import src.obra.Estado;
import src.obra.Obra;
import src.sala.Sala;

public class ExposicionTest {
    private Exposicion exposicion;
    private Set<SalaExposicion> salas;
    private SalaExposicion sala1;
    private SalaExposicion sala2;

    @BeforeEach
    public void setUp() {
        salas = new HashSet<>();
        Sala salaf1 = new Sala("Sala1", 20, 20, 30, true, 3, 20.0, 20.0);
        Sala salaf2 = new Sala("Sala2", 30, 25, 25, false, 5, 10.0, 30.0);
        sala1 = new SalaExposicion(salaf1);
        sala2 = new SalaExposicion(salaf2);
        salas.add(sala1);
        salas.add(sala2);
        exposicion = new Exposicion("Exposicion 1", LocalDate.now(), LocalDate.now().plusDays(7), "Descripción", salas,
                TipoExpo.TEMPORAL, 10.0);
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
        Sala salaf3 = new Sala("Sala3", 40, 20, 30, true, 5, 10.0, 20.0);
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
    public void testAddHorario() {
        exposicion.addHorario(new Hora(LocalDate.now(), LocalTime.now(), LocalTime.now().plusHours(1), 10, 10.0));
        assertNotNull(exposicion.getHorario());
        assertEquals(1, exposicion.getHorario().size());
    }

    @Test
    public void testRemoveHorario() {
        Hora hora = new Hora(LocalDate.now(), LocalTime.now(), LocalTime.now().plusHours(1), 10, 10.0);
        exposicion.addHorario(hora);
        exposicion.removeHorario(hora);
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
        exposicion.setDescuento(descuento);
        assertEquals(descuento, exposicion.getDescuento());
    }

    @Test
    public void testExpoCrear() {
        exposicion.expoCrear();
        assertEquals(EstadoExposicion.EN_CREACION, exposicion.getEstado());
    }

    @Test
    public void testExpoPublicar() {
        exposicion.expoPublicar();
        assertEquals(EstadoExposicion.PUBLICADA, exposicion.getEstado());
    }

    @Test
    public void testExpoCancelar() {
        exposicion.expoCancelar();
        assertEquals(EstadoExposicion.CANCELADA, exposicion.getEstado());
    }

    @Test
    public void testExpoProrrogar() {
        LocalDate newFechaFin = LocalDate.now().plusDays(14);
        exposicion.expoProrrogar(newFechaFin);
        assertEquals(EstadoExposicion.PRORROGADA, exposicion.getEstado());
        assertEquals(newFechaFin, exposicion.getFechaFin());
    }

    @Test
    public void testExpoCerrarTemporalmente() {
        exposicion.expoCerrarTemporalmente();
        assertEquals(EstadoExposicion.CERRADATEMPORALMENTE, exposicion.getEstado());
        for (SalaExposicion sala : exposicion.getSalas()) {
            for (Obra obra : sala.getObras()) {
                assertTrue(obra.getEstado().equals(Estado.ALMACENADA));
            }
        }
    }


    @Test
    public void testExpoPermanente() {
        exposicion.expoPermanente();
        assertEquals(TipoExpo.PERMANENTE, exposicion.getTipo());
        assertNull(exposicion.getFechaFin());
    }

}