package Exposicion;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Entrada.Entrada;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
/**
 * Clase HoraTest.
 * Esta clase contiene pruebas unitarias que validan la funcionalidad de los
 * métodos de la clase {@link Notificacion}.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
class HoraTest {
    private Hora hora;
/**
 * Prepara el entorno de prueba creando una instancia de {@code Hora}
 * con valores iniciales para realizar las pruebas.
 */
    @BeforeEach
    public void setUp() {
        hora = new Hora(LocalDate.of(2022, 1, 1), LocalTime.of(10, 0), LocalTime.of(12, 0), 100, 10.0);
    }
/**
 * Verifica que el método getFecha() de la clase Hora retorne la fecha correcta
 * que se estableció durante la configuración inicial.
 */
    @Test
    public void testGetFecha() {
        assertEquals(LocalDate.of(2022, 1, 1), hora.getFecha());
    }
/**
 * Prueba que el método setFecha() actualiza correctamente la fecha de una instancia de Hora.
 */
    @Test
    public void testSetFecha() {
        hora.setFecha(LocalDate.of(2022, 2, 1));
        assertEquals(LocalDate.of(2022, 2, 1), hora.getFecha());
    }
/**
 * Comprueba que getHoraInicio() devuelve correctamente la hora de inicio establecida.
 */
    @Test
    public void testGetHoraInicio() {
        assertEquals(LocalTime.of(10, 0), hora.getHoraInicio());
    }
/**
 * Verifica que setHoraInicio() actualiza correctamente la hora de inicio en la instancia de Hora.
 */
    @Test
    public void testSetHoraInicio() {
        hora.setHoraInicio(LocalTime.of(11, 0));
        assertEquals(LocalTime.of(11, 0), hora.getHoraInicio());
    }
/**
 * Prueba que getHoraFin() devuelve la hora de fin esperada para la instancia de Hora.
 */
    @Test
    public void testGetHoraFin() {
        assertEquals(LocalTime.of(12, 0), hora.getHoraFin());
    }
/**
 * Comprueba que setHoraFin() cambia con éxito la hora de fin de la instancia de Hora.
 */
    @Test
    public void testSetHoraFin() {
        hora.setHoraFin(LocalTime.of(13, 0));
        assertEquals(LocalTime.of(13, 0), hora.getHoraFin());
    }
/**
 * Verifica que getnEntradas() retorna el número correcto de entradas asignadas.
 */
    @Test
    public void testGetnEntradas() {
        assertEquals(100, hora.getnEntradas());
    }
/**
 * Prueba que setnEntradas() actualiza el número de entradas disponibles correctamente.
 */
    @Test
    public void testSetnEntradas() {
        hora.setnEntradas(200);
        assertEquals(200, hora.getnEntradas());
    }
/**
 * Comprueba que getPrecio() devuelve el precio establecido para la instancia de Hora.
 */
    @Test
    public void testGetPrecio() {
        assertEquals(10.0, hora.getPrecio());
    }
/**
 * Verifica que setPrecio() actualiza correctamente el precio en la instancia de Hora.
 */
    @Test
    public void testSetPrecio() {
        hora.setPrecio(15.0);
        assertEquals(15.0, hora.getPrecio());
    }
/**
 * Prueba la funcionalidad de setEntradas() para asegurar que las entradas se asignan correctamente.
 */
    @Test
    public void testSetEntradas() {
        Set<Entrada> newEntradas = new HashSet<>();
        Entrada entrada1 = new Entrada(1);
        Entrada entrada2 = new Entrada(2);
        newEntradas.add(entrada1);
        newEntradas.add(entrada2);
        hora.setEntradas(newEntradas);
        assertEquals(newEntradas, hora.getEntradas());
    }
/**
 * Comprueba que addEntrada() incluye una nueva entrada en el conjunto de entradas.
 */
    @Test
    public void testAddEntrada() {
        Entrada newEntrada = new Entrada(3);
        hora.addEntrada(newEntrada);
        assertTrue(hora.getEntradas().contains(newEntrada));
    }
/**
 * Verifica que removeEntrada() elimina con éxito una entrada del conjunto de entradas.
 */
    @Test
    public void testRemoveEntrada() {
        Entrada entradaToRemove = new Entrada(4);
        hora.removeEntrada(entradaToRemove);
        assertFalse(hora.getEntradas().contains(entradaToRemove));
    }
/**
 * Comprueba que removeAllEntradas() limpia todas las entradas y restablece el contador a 0.
 */
    @Test
    public void testRemoveAllEntradas() {
        hora.removeAllEntradas();
        assertEquals(0, hora.getnEntradas());
        assertTrue(hora.getEntradas().isEmpty());
    }
}