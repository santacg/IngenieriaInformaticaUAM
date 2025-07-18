package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gui.modelo.centroExposicion.Empleado;

public class EmpleadoTest {
    private Empleado empleado;

    @BeforeEach
    public void setUp() {
        empleado = new Empleado("123456789X", "María García", "123456789", "1234567890", "Calle", true, true, true, true);

    }

    @Test
    public void testGetNombre() {
        assertEquals("María García", empleado.getNombre());
    }

    @Test
    public void testSetNombre() {
        empleado.setNombre("María García Sánchez");
        assertEquals("María García Sánchez", empleado.getNombre());
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
        assertTrue(empleado.getPermisoControl());
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
    public void testGetPermisoActivdades() {
        assertTrue(empleado.getPermisoActividades());
    }

    @Test 
    public void testSetPermisoActividades() {
        empleado.setPermisoActividades(false);
        assertFalse(empleado.getPermisoActividades());
    }

    @Test
    public void testGetDireccion() {
        assertEquals("Calle", empleado.getDireccion());
    }

    @Test
    public void testSetDireccion() {
        empleado.setDireccion("Calle del Pez, 2");
        assertEquals("Calle del Pez, 2", empleado.getDireccion());
    }

    @Test
    public void testCambiarPermisos() {
        empleado.cambiarPermisos(false, false, false, false);
        assertFalse(empleado.getPermisoVenta());
        assertFalse(empleado.getPermisoControl());
        assertFalse(empleado.getPermisoMensajes());
        assertFalse(empleado.getPermisoMensajes());
    }

    @Test
    public void testModificarDatos() {
        empleado.modificarDatos("987654321", "0987654321", "Calle del Pez, 2");
        assertEquals("987654321", empleado.getNumSS());
        assertEquals("0987654321", empleado.getNumCuenta());
        assertEquals("Calle del Pez, 2", empleado.getDireccion());
    }

}