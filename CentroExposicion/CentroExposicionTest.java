package CentroExposicion;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Inscripcion.Inscripcion;
import Inscripcion.InscripcionTest;

public class CentroExposicionTest {
    private CentroExposicion centroExposicion;

    @BeforeEach
    public void setUp() {
        LocalTime horaApertura = LocalTime.of(9, 0);
        LocalTime horaCierre = LocalTime.of(18, 0);
        String nombre = "Centro de Exposición";
        String contraseniaEmpleado = "password";
        String contraseniaGestor = "admin";
        Gestor gestor = new Gestor("John Doe", "john.doe@example.com");
        Set<Sala> salas = new HashSet<>();
        centroExposicion = new CentroExposicion(nombre, horaApertura, horaCierre, "Madrid",
                contraseniaEmpleado, contraseniaGestor, gestor, salas);
    }

    @Test
    public void testGetID() {
        assertNotNull(centroExposicion.getID());
    }

    @Test
    public void testGetNombre() {
        String expected = "Centro de Exposición";
        String actual = centroExposicion.getNombre();
        assertEquals(expected, actual);
    }

    @Test
    public void testSetNombre() {
        String newNombre = "Nuevo Centro de Exposición";
        centroExposicion.setNombre(newNombre);
        String actual = centroExposicion.getNombre();
        assertEquals(newNombre, actual);
    }

    @Test
    public void testGetHoraApertura() {
        LocalTime expected = LocalTime.of(9, 0);
        LocalTime actual = centroExposicion.getHoraApertura();
        assertEquals(expected, actual);
    }

    @Test
    public void testSetHoraApertura() {
        LocalTime newHoraApertura = LocalTime.of(10, 0);
        centroExposicion.setHoraApertura(newHoraApertura);
        LocalTime actual = centroExposicion.getHoraApertura();
        assertEquals(newHoraApertura, actual);
    }

    @Test
    public void testGetHoraCierre() {
        LocalTime expected = LocalTime.of(18, 0);
        LocalTime actual = centroExposicion.getHoraCierre();
        assertEquals(expected, actual);
    }

    @Test
    public void testSetHoraCierre() {
        LocalTime newHoraCierre = LocalTime.of(19, 0);
        centroExposicion.setHoraCierre(newHoraCierre);
        LocalTime actual = centroExposicion.getHoraCierre();
        assertEquals(newHoraCierre, actual);
    }

    @Test
    public void testGetLocalizacion() {
        String expected = "Madrid";
        String actual = centroExposicion.getLocalizacion();
        assertEquals(expected, actual);
    }

    @Test
    public void testSetLocalizacion() {
        String newLocalizacion = "Barcelona";
        centroExposicion.setLocalizacion(newLocalizacion);
        String actual = centroExposicion.getLocalizacion();
        assertEquals(newLocalizacion, actual);
    }

    @Test
    public void testGetContraseniaEmpleado() {
        String expected = "password";
        String actual = centroExposicion.getContraseniaEmpleado();
        assertEquals(expected, actual);
    }

    @Test
    public void testSetContraseniaEmpleado() {
        String newContraseniaEmpleado = "newpassword";
        centroExposicion.setContraseniaEmpleado(newContraseniaEmpleado);
        String actual = centroExposicion.getContraseniaEmpleado();
        assertEquals(newContraseniaEmpleado, actual);
    }

    @Test
    public void testGetContraseniaGestor() {
        String expected = "admin";
        String actual = centroExposicion.getContraseniaGestor();
        assertEquals(expected, actual);
    }

    @Test
    public void testSetContraseniaGestor() {
        String newContraseniaGestor = "newadmin";
        centroExposicion.setContraseniaGestor(newContraseniaGestor);
        String actual = centroExposicion.getContraseniaGestor();
        assertEquals(newContraseniaGestor, actual);
    }

    @Test
    public void testGetSalas() {
        Set<Sala> expected = new HashSet<>();
        Set<Sala> actual = centroExposicion.getSalas();
        assertEquals(expected, actual);
    }

    @Test
    public void testSetSalas() {
        Set<Sala> newSalas = new HashSet<>();
        newSalas.add(new Sala("Sala 1"));
        newSalas.add(new Sala("Sala 2"));
        centroExposicion.setSalas(newSalas);
        Set<Sala> actual = centroExposicion.getSalas();
        assertEquals(newSalas, actual);
    }

    @Test
    public void testAddSala() {
        Sala sala = new Sala("Sala 1");
        centroExposicion.addSala(sala);
        assertTrue(centroExposicion.getSalas().contains(sala));
    }

    @Test
    public void testRemoveSala() {
        Sala sala = new Sala("Sala 1");
        centroExposicion.addSala(sala);
        centroExposicion.removeSala(sala);
        assertFalse(centroExposicion.getSalas().contains(sala));
    }

    @Test
    public void testGetExposiciones() {
        Set<Exposicion> expected = new HashSet<>();
        Set<Exposicion> actual = centroExposicion.getExposiciones();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetExposicionesPorFecha() {
        LocalDate fechaInicio = LocalDate.of(2022, 1, 1);
        LocalDate fechaFinal = LocalDate.of(2022, 12, 31);
        Set<Exposicion> expected = new HashSet<>();
        Set<Exposicion> actual = centroExposicion.getExposicionesPorFecha(fechaInicio, fechaFinal);
        assertEquals(expected, actual);
    }

    @Test
    public void testGetExposicionesTemporales() {
        Set<Exposicion> expected = new HashSet<>();
        Set<Exposicion> actual = centroExposicion.getExposicionesTemporales();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetExposicionesPermanentes() {
        Set<Exposicion> expected = new HashSet<>();
        Set<Exposicion> actual = centroExposicion.getExposicionesPermanentes();
        assertEquals(expected, actual);
    }
}