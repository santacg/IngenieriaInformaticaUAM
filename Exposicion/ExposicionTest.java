package Exposicion;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class ExposicionTest {
    private Exposicion exposicion;
    private Set<SalaExposicion> salas;
    private SalaExposicion sala1;
    private SalaExposicion sala2;

    @BeforeEach
    public void setUp() {
        salas = new HashSet<>();
        sala1 = new SalaExposicion("Sala 1");
        sala2 = new SalaExposicion("Sala 2");
        salas.add(sala1);
        salas.add(sala2);
        exposicion = new Exposicion("Exposicion 1", LocalDate.now(), LocalDate.now().plusDays(7), "Descripción", salas, TipoExpo.TEMPORAL, 10.0);
    }

    @Test
    public void testGetID() {
        assertNotNull(exposicion.getID());
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
    public void testSetBenificios() {
        exposicion.setBenificios(1000.0);
        assertEquals(1000.0, exposicion.getBenificios());
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
    public void testSetEstado() {
        exposicion.setEstado(EstadoExposicion.PUBLICADA);
        assertEquals(EstadoExposicion.PUBLICADA, exposicion.getEstado());
    }

    @Test
    public void testGetSalas() {
        assertEquals(salas, exposicion.getSalas());
    }

    @Test
    public void testAddSala() {
        SalaExposicion sala3 = new SalaExposicion("Sala 3");
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
        assertNull(exposicion.getHorario());
    }

    @Test
    public void testAddHorario() {
        exposicion.addHorario(new Hora(10, 0));
        assertNotNull(exposicion.getHorario());
        assertEquals(1, exposicion.getHorario().size());
    }

    @Test
    public void testRemoveHorario() {
        Hora hora = new Hora(10, 0);
        exposicion.addHorario(hora);
        exposicion.removeHorario(hora);
        assertTrue(exposicion.getHorario().isEmpty());
    }

    @Test
    public void testGetEstadisticas() {
        assertNull(exposicion.getEstadisticas());
    }

    @Test
    public void testSetEstadisticas() {
        Estadisticas estadisticas = new Estadisticas();
        exposicion.setEstadisticas(estadisticas);
        assertEquals(estadisticas, exposicion.getEstadisticas());
    }

    @Test
    public void testGetTipo() {
        assertEquals(TipoExpo.TEMPORAL, exposicion.getTipo());
    }

    @Test
    public void testSetTipo() {
        exposicion.setTipo(TipoExpo.PERMANENTE);
        assertEquals(TipoExpo.PERMANENTE, exposicion.getTipo());
    }

    @Test
    public void testGetDescuento() {
        assertNull(exposicion.getDescuento());
    }

    @Test
    public void testSetDescuento() {
        Descuento descuento = new Descuento();
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
                assertTrue(obra.isAlmacenada());
            }
        }
    }

    @Test
    public void testExpoTemporal() {
        exposicion.expoTemporal();
        assertEquals(TipoExpo.TEMPORAL, exposicion.getTipo());
    }

    @Test
    public void testExpoPermanente() {
        exposicion.expoPermanente();
        assertEquals(TipoExpo.PERMANENTE, exposicion.getTipo());
        assertNull(exposicion.getFechaFin());
    }

    @Test
    public void testToString() {
        String expected = "Exposicion Details:\n" +
                          "ID: " + exposicion.getID() + "\n" +
                          "Nombre: Exposicion 1\n" +
                          "Fecha de inicio: " + exposicion.getFechaInicio() + "\n" +
                          "Fecha de fin: " + exposicion.getFechaFin() + "\n" +
                          "Descripción: Descripción\n" +
                          "Beneficios: null\n" +
                          "Precio: 10.0\n" +
                          "Estado: EN_CREACION\n" +
                          "Salas: [Sala 1, Sala 2]\n" +
                          "Horario: null\n" +
                          "Estadísticas: null\n" +
                          "Tipo: TEMPORAL\n" +
                          "Descuento: null\n";
        assertEquals(expected, exposicion.toString());
    }
}