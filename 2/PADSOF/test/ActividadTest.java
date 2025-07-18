package test;

import gui.modelo.centroExposicion.Actividad;
import gui.modelo.centroExposicion.TipoActividad;
import gui.modelo.expofy.Expofy;
import gui.modelo.sala.Sala;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para la clase Actividad.
 * 
 * @author Carlos García Santa
 */
public class ActividadTest {
    private Actividad actividad;
    private Sala salaCelebracion;

    @BeforeEach
    public void setUp() {
        salaCelebracion = new Sala("Sala Prueba", 100, true, 10, 10.0, 10.0, 10.0);
        actividad = new Actividad("Actividad de prueba", TipoActividad.CONFERENCIA, "Descripción de prueba",
                5, LocalDate.now(), LocalTime.now().plusHours(1), salaCelebracion);
    }

    @Test

    public void testGetNombre() {
        assertEquals("Actividad de prueba", actividad.getNombre());
    }

    @Test
    public void testGetTipo() {
        assertEquals(TipoActividad.CONFERENCIA, actividad.getTipo());
    }

    @Test
    public void testGetDescripcion() {
        assertEquals("Descripción de prueba", actividad.getDescripcion());
    }

    @Test
    public void testGetMaxParticipantes() {
        assertEquals(5, actividad.getMaxParticipantes());
    }

    @Test
    public void testGetFecha() {
        assertEquals(LocalDate.now(), actividad.getFecha());
    }

    @Test
    public void testGetSalaCelebracion() {
        assertEquals(salaCelebracion, actividad.getSalaCelebracion());
    }

    @Test 
    public void testGetParticipantes() {
        assertTrue(actividad.getParticipantes().isEmpty());
    }

    @Test 
    public void testSetDescripcion() {
        actividad.setDescripcion("Hola");
        assertEquals("Hola", actividad.getDescripcion());
    }

    @Test 
    public void testSetNombre() {
        actividad.setNombre("Miau");
        assertEquals("Miau", actividad.getNombre());
    }

    @Test 
    public void testSetFecha() {
        actividad.setFecha(LocalDate.now().plusDays(3));
        assertEquals(LocalDate.now().plusDays(3), actividad.getFecha());    
    } 

    @Test 
    public void testSetHora() {
        actividad.setHora(LocalTime.of(11, 0));
        assertEquals(LocalTime.of(11, 0), actividad.getHora());
    }

    @Test
    public void testSetSala() {
        Sala sala = new Sala("Sala 2", 100, true, 10, 10.0, 10.0, 10.0);
        actividad.setSalaCelebracion(sala);
        assertEquals(sala, actividad.getSalaCelebracion());
    }

    @Test
    public void testAddParticipanteFalloActividadCompleta() {
        for (int i = 0; i < actividad.getMaxParticipantes(); i++) {
            actividad.addParticipante("Cliente" + i);
        }

        assertFalse(actividad.addParticipante("NuevoCliente"));
        assertFalse(actividad.getParticipantes().contains("NuevoCliente"));
    }

    @Test
    public void testAddParticipanteFalloClienteDuplicado() {
        assertTrue(actividad.addParticipante("12345678A"));
        assertFalse(actividad.addParticipante("12345678A"));
    }

    @Test 
    public void testAddParticipanteRegistradoExito() {
        Actividad actividadPrueba = new Actividad("Actividad de prueba", TipoActividad.CONFERENCIA, "Descripción de prueba",
                5, LocalDate.now().plusDays(1), LocalTime.now().plusHours(1), salaCelebracion);
        
        Expofy expofy = Expofy.getInstance();

        expofy.registrarCliente("123", "hola", false);
        assertTrue(actividadPrueba.addParticipante("123"));
    }
}