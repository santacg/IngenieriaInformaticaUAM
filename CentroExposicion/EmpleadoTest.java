package CentroExposicion;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EmpleadoTest {
    private Empleado empleado;

    @BeforeEach
    public void setUp() {
        empleado = new Empleado("1234567890", "John Doe", "123456789", "1234567890", true, false, true, "123 Main St");
    }

    @Test
    public void testGetNombre() {
        assertEquals("John Doe", empleado.getNombre());
    }

    @Test
    public void testSetNombre() {
        empleado.setNombre("Jane Smith");
        assertEquals("Jane Smith", empleado.getNombre());
    }

    @Test
    public void testGetNumSS() {
        assertEquals("123456789", empleado.getNumSS());
    }

    @Test
    public void testSetNumSS() {
        empleado.setNumSS("987654321");
        assertEquals("987654321", empleado.getNumSS());
    }

    @Test
    public void testGetNumCuenta() {
        assertEquals("1234567890", empleado.getNumCuenta());
    }

    @Test
    public void testSetNumCuenta() {
        empleado.setNumCuenta("0987654321");
        assertEquals("0987654321", empleado.getNumCuenta());
    }

    @Test
    public void testGetPermisoVenta() {
        assertTrue(empleado.getPermisoVenta());
    }

    @Test
    public void testSetPermisoVenta() {
        empleado.setPermisoVenta(false);
        assertFalse(empleado.getPermisoVenta());
    }

    @Test
    public void testGetPermisoControl() {
        assertFalse(empleado.getPermisoControl());
    }

    @Test
    public void testSetPermisoControl() {
        empleado.setPermisoControl(true);
        assertTrue(empleado.getPermisoControl());
    }

    @Test
    public void testGetPermisoMensajes() {
        assertTrue(empleado.getPermisoMensajes());
    }

    @Test
    public void testSetPermisoMensajes() {
        empleado.setPermisoMensajes(false);
        assertFalse(empleado.getPermisoMensajes());
    }

    @Test
    public void testGetDireccion() {
        assertEquals("123 Main St", empleado.getDireccion());
    }

    @Test
    public void testSetDireccion() {
        empleado.setDireccion("456 Elm St");
        assertEquals("456 Elm St", empleado.getDireccion());
    }

    @Test
    public void testCambiarPermisos() {
        empleado.cambiarPermisos(false, true, false);
        assertFalse(empleado.getPermisoVenta());
        assertTrue(empleado.getPermisoControl());
        assertFalse(empleado.getPermisoMensajes());
    }

    @Test
    public void testModificarDatos() {
        empleado.modificarDatos("987654321", "0987654321", "456 Elm St");
        assertEquals("987654321", empleado.getNumSS());
        assertEquals("0987654321", empleado.getNumCuenta());
        assertEquals("456 Elm St", empleado.getDireccion());
    }

}