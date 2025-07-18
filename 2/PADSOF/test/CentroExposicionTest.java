package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gui.modelo.centroExposicion.*;
import gui.modelo.exposicion.Exposicion;
import gui.modelo.exposicion.Hora;
import gui.modelo.exposicion.SalaExposicion;
import gui.modelo.exposicion.TipoExpo;
import gui.modelo.sala.Sala;
import gui.modelo.obra.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Clase test para la clase CentroExposicion.
 *
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 * 
 */
public class CentroExposicionTest {

    private CentroExposicion centroExposicion;
    private Gestor gestor;
    private Set<Sala> salas;

    @BeforeEach
    public void setUp() {
        gestor = new Gestor("12345678A");
        salas = new HashSet<>();

        salas.add(new Sala("Sala prueba", 50, true, 10, 25.0, 25.0, 10.0));
        salas.add(new Sala("Sala 2", 100, false, 20, 50.0, 50.0, 20.0));

        centroExposicion = new CentroExposicion("Museo de Prueba", LocalTime.of(9, 0),
                LocalTime.of(18, 0),
                "Calle de Prueba, 123", "passwordEmpleado", "passwordGestor", gestor, salas);
    }

    @Test
    public void testGetNombre() {
        assertEquals("Museo de Prueba", centroExposicion.getNombre());
    }

    @Test
    public void testGetHoraApertura() {
        assertEquals(LocalTime.of(9, 0), centroExposicion.getHoraApertura());
    }

    @Test
    public void testSetHoraApertura() {
        gestor.logIn();
        centroExposicion.setHoraApertura(LocalTime.of(10, 0));
        assertEquals(LocalTime.of(10, 0), centroExposicion.getHoraApertura());
    }

    @Test
    public void testGetHoraCierre() {
        assertEquals(LocalTime.of(18, 0), centroExposicion.getHoraCierre());
    }

    @Test
    public void testSetHoraCierre() {
        gestor.logIn();
        centroExposicion.setHoraCierre(LocalTime.of(19, 0));
        assertEquals(LocalTime.of(19, 0), centroExposicion.getHoraCierre());
    }

    @Test
    public void testGetLocalizacion() {
        assertEquals("Calle de Prueba, 123", centroExposicion.getLocalizacion());
    }

    @Test
    public void testGetContraseniaEmpleado() {
        gestor.logIn();
        assertEquals("passwordEmpleado", centroExposicion.getContraseniaEmpleado());
    }

    @Test
    public void testSetContraseniaEmpleado() {
        gestor.logIn();
        centroExposicion.setContraseniaEmpleado("nuevaContrasenia");
        assertEquals("nuevaContrasenia", centroExposicion.getContraseniaEmpleado());
    }

    @Test
    public void testGetContraseniaGestor() {
        gestor.logIn();
        assertEquals("passwordGestor", centroExposicion.getContraseniaGestor());
    }

    @Test
    public void testGetSancion() {
        assertEquals(0, centroExposicion.getSancion());
    }

    @Test
    public void testSetSancion() {
        centroExposicion.setSancion(10);
        assertEquals(10, centroExposicion.getSancion());
    }

    @Test
    public void testGetSalas() {
        assertEquals(salas, centroExposicion.getSalas());
    }

    @Test
    public void testSetSalas() {
        gestor.logIn();
        Set<Sala> nuevasSalas = new HashSet<>();
        nuevasSalas.add(new Sala("Sala de Prueba", 100, true, 10, 10.0, 10.0, 10.0));
        centroExposicion.setSalas(nuevasSalas);
        assertEquals(nuevasSalas, centroExposicion.getSalas());
    }

    @Test
    public void testAddSala() {
        gestor.logIn();
        Sala nuevaSala = new Sala("Sala de Prueba", 100, true, 10, 10.0, 10.0, 10.0);
        assertTrue(centroExposicion.addSala(nuevaSala));
        assertTrue(centroExposicion.getSalas().contains(nuevaSala));
    }

    @Test
    public void testRemoveSala() {
        gestor.logIn();
        Sala salaAEliminar = salas.iterator().next();
        assertTrue(centroExposicion.removeSala(salaAEliminar));
        assertFalse(centroExposicion.getSalas().contains(salaAEliminar));
    }

    @Test
    public void testGetSalaPorNombre() {
        assertEquals(salas.iterator().next(), centroExposicion.getSalaPorNombre("Sala prueba"));
    }

    @Test
    public void testGetExposiciones() {
        assertTrue(centroExposicion.getExposiciones().isEmpty());
    }

    @Test
    public void testGetExposicionesPorFecha() {
        gestor.logIn();
        Exposicion exposicion1 = new Exposicion("Exposicion 1", LocalDate.now(), LocalDate.now().plusDays(30),
                "Descripción",
                TipoExpo.TEMPORAL, 10.0);

        Exposicion exposicion2 = new Exposicion("Exposicion 2", LocalDate.now().plusMonths(2), LocalDate.MAX,
                "Descripción",
                TipoExpo.PERMANENTE, 10.0);
        Sala sala = new Sala("Sala expo 1", 100, true, 10, 10.0, 10.0, 10.0);
        Sala sala2 = new Sala("Sala expo 2", 100, true, 10, 10.0, 10.0, 10.0);

        SalaExposicion salaExpo = new SalaExposicion(sala);
        SalaExposicion salaExpo2 = new SalaExposicion(sala2);

        exposicion1.addSala(salaExpo);
        exposicion2.addSala(salaExpo2);

        Cuadro cuadro1 = new Cuadro("Mona Lisa", 1503, "Cuadro famoso", false, 1000000.0, "12345", 1.5, 1.2, 26, 20,
                90, 20, "óleo", "Leonardo da Vinci");

        Cuadro cuadro2 = new Cuadro("La última cena", 1498, "Cuadro famoso", false, 1000000.0, "12345", 1.5, 1.2, 26,
                20, 90, 20, "óleo", "Leonardo da Vinci");

        salaExpo.addObra(cuadro1);
        salaExpo2.addObra(cuadro2);

        centroExposicion.addExposicion(exposicion1);
        centroExposicion.addExposicion(exposicion2);

        try {
            exposicion1.expoPublicar();
            exposicion2.expoPublicar();
        } catch (Exception e) {

        }

        LocalDate fechaInicio = LocalDate.of(2024, 1, 1);
        LocalDate fechaFin = LocalDate.of(2024, 7, 1);

        Set<Exposicion> exposicionesPorFecha = centroExposicion.getExposicionesPorFecha(fechaInicio,
                fechaFin);

        assertNotNull(exposicionesPorFecha);
        assertEquals(1, exposicionesPorFecha.size());
    }

    @Test
    public void testGetExposicionesPublicadas() {
        gestor.logIn();
        Exposicion exposicion1 = new Exposicion("Exposicion 1", LocalDate.now(), LocalDate.now().plusDays(30),
                "Descripción",
                TipoExpo.TEMPORAL, 10.0);

        Sala sala = new Sala("Sala expo 1", 100, true, 10, 10.0, 10.0, 10.0);

        SalaExposicion salaExpo = new SalaExposicion(sala);

        exposicion1.addSala(salaExpo);

        Cuadro cuadro1 = new Cuadro("Mona Lisa", 1503, "Cuadro famoso", false, 1000000.0, "12345", 1.5, 1.2, 26, 20,
                90, 20, "óleo", "Leonardo da Vinci");

        salaExpo.addObra(cuadro1);

        centroExposicion.addExposicion(exposicion1);

        try {
            exposicion1.expoPublicar();
        } catch (Exception e) {

        }

        assertTrue(centroExposicion.getExposicionesPublicadas().contains(exposicion1));
    }

    @Test
    public void testGetExposicionesTemporales() {
        gestor.logIn();

        Exposicion exposicion1 = new Exposicion("Exposicion 1", LocalDate.now(), LocalDate.now().plusDays(30),
                "Descripción",
                TipoExpo.TEMPORAL, 10.0);

        Exposicion exposicion2 = new Exposicion("Exposicion 2", LocalDate.now().plusMonths(2), LocalDate.MAX,
                "Descripción",
                TipoExpo.PERMANENTE, 10.0);
        Sala sala = new Sala("Sala expo 1", 100, true, 10, 10.0, 10.0, 10.0);
        Sala sala2 = new Sala("Sala expo 2", 100, true, 10, 10.0, 10.0, 10.0);

        SalaExposicion salaExpo = new SalaExposicion(sala);
        SalaExposicion salaExpo2 = new SalaExposicion(sala2);

        exposicion1.addSala(salaExpo);
        exposicion2.addSala(salaExpo2);

        Cuadro cuadro1 = new Cuadro("Mona Lisa", 1503, "Cuadro famoso", false, 1000000.0, "12345", 1.5, 1.2, 26, 20,
                90, 20, "óleo", "Leonardo da Vinci");

        Cuadro cuadro2 = new Cuadro("La última cena", 1498, "Cuadro famoso", false, 1000000.0, "12345", 1.5, 1.2, 26,
                20, 90, 20, "óleo", "Leonardo da Vinci");

        salaExpo.addObra(cuadro1);
        salaExpo2.addObra(cuadro2);

        centroExposicion.addExposicion(exposicion1);
        centroExposicion.addExposicion(exposicion2);

        try {
            exposicion1.expoPublicar();
            exposicion2.expoPublicar();
        } catch (Exception e) {

        }

        assertFalse(centroExposicion.getExposicionesTemporales().contains(exposicion2));
        assertTrue(centroExposicion.getExposicionesTemporales().contains(exposicion1));
    }

    @Test
    public void testGetExposicionesPermanentes() {
        gestor.logIn();

        Exposicion exposicion1 = new Exposicion("Exposicion 1", LocalDate.now(), LocalDate.now().plusDays(30),
                "Descripción",
                TipoExpo.TEMPORAL, 10.0);

        Exposicion exposicion2 = new Exposicion("Exposicion 2", LocalDate.now().plusMonths(2), LocalDate.MAX,
                "Descripción",
                TipoExpo.PERMANENTE, 10.0);
        Sala sala = new Sala("Sala expo 1", 100, true, 10, 10.0, 10.0, 10.0);
        Sala sala2 = new Sala("Sala expo 2", 100, true, 10, 10.0, 10.0, 10.0);

        SalaExposicion salaExpo = new SalaExposicion(sala);
        SalaExposicion salaExpo2 = new SalaExposicion(sala2);

        exposicion1.addSala(salaExpo);
        exposicion2.addSala(salaExpo2);

        Cuadro cuadro1 = new Cuadro("Mona Lisa", 1503, "Cuadro famoso", false, 1000000.0, "12345", 1.5, 1.2, 26, 20,
                90, 20, "óleo", "Leonardo da Vinci");

        Cuadro cuadro2 = new Cuadro("La última cena", 1498, "Cuadro famoso", false, 1000000.0, "12345", 1.5, 1.2, 26,
                20, 90, 20, "óleo", "Leonardo da Vinci");

        salaExpo.addObra(cuadro1);
        salaExpo2.addObra(cuadro2);

        centroExposicion.addExposicion(exposicion1);
        centroExposicion.addExposicion(exposicion2);

        try {
            exposicion1.expoPublicar();
            exposicion2.expoPublicar();
        } catch (Exception e) {

        }

        assertFalse(centroExposicion.getExposicionesPermanentes().contains(exposicion1));
        assertTrue(centroExposicion.getExposicionesPermanentes().contains(exposicion2));

    }

    @Test
    public void testAddExposicion() {
        Exposicion exposicion1 = new Exposicion("Exposicion 1", LocalDate.now(), LocalDate.now().plusDays(30),
                "Descripción",
                TipoExpo.TEMPORAL, 10.0);

        assertTrue(centroExposicion.addExposicion(exposicion1));
        assertTrue(centroExposicion.getExposiciones().contains(exposicion1));
    }

    @Test
    public void testRemoveExposicion() {
        Exposicion exposicion1 = new Exposicion("Exposicion 1", LocalDate.now(), LocalDate.now().plusDays(30),
                "Descripción",
                TipoExpo.TEMPORAL, 10.0);

        centroExposicion.addExposicion(exposicion1);
        try {
            centroExposicion.removeExposicion(exposicion1);
        } catch (Exception e) {
        }

        assertFalse(centroExposicion.getExposiciones().contains(exposicion1));
    }

    @Test
    public void testRemoveExposicionPublicada() {
        Exposicion exposicion1 = new Exposicion("Exposicion 1", LocalDate.now(), LocalDate.now().plusDays(30),
                "Descripción",
                TipoExpo.TEMPORAL, 10.0);

        centroExposicion.addExposicion(exposicion1);
        try {
            exposicion1.expoPublicar();
            centroExposicion.removeExposicion(exposicion1);
        } catch (Exception e) {
        }

        assertTrue(centroExposicion.getExposiciones().contains(exposicion1));
    }

    @Test
    public void testRemoveExposicionTerminada() {
        Exposicion exposicion1 = new Exposicion("Exposicion 1", LocalDate.now(), LocalDate.now().minusDays(1),
                "Descripción",
                TipoExpo.TEMPORAL, 10.0);

        centroExposicion.addExposicion(exposicion1);
        try {
            centroExposicion.removeExposicion(exposicion1);
        } catch (Exception e) {
        }

        assertFalse(centroExposicion.getExposiciones().contains(exposicion1));
    }

    @Test
    public void testGetSorteos() {
        assertTrue(centroExposicion.getSorteos().isEmpty());
    }

    @Test
    public void testSetSorteos() {
        gestor.logIn();
        Set<Sorteo> sorteos = new HashSet<>();
        sorteos.add(new SorteoExpo(null, null, 10));
        centroExposicion.setSorteos(sorteos);
        assertEquals(sorteos, centroExposicion.getSorteos());
    }

    @Test
    public void testConfgiurarSorteoDiaHora() {
        Exposicion exposicion1 = new Exposicion("Exposicion 1", LocalDate.now(), LocalDate.now().plusDays(30),
                "Descripción",
                TipoExpo.TEMPORAL, 10.0);

        centroExposicion.addExposicion(exposicion1);
        LocalDate fechaSorteo = LocalDate.now().plusDays(5);
        LocalDate dia = LocalDate.now().plusDays(6);
        Hora hora = exposicion1.getHora(fechaSorteo, 15);

        assertTrue(centroExposicion.confgiurarSorteoDiaHora(exposicion1, fechaSorteo, 2, dia, hora));
    }

    @Test
    public void testConfgiurarSorteoDiaHoraFalloDia() {
        Exposicion exposicion1 = new Exposicion("Exposicion 1", LocalDate.now(), LocalDate.now().plusDays(30),
                "Descripción",
                TipoExpo.TEMPORAL, 10.0);

        centroExposicion.addExposicion(exposicion1);
        LocalDate fechaSorteo = LocalDate.now().plusDays(5);
        LocalDate dia = LocalDate.now().plusDays(4);
        Hora hora = exposicion1.getHora(fechaSorteo, 15);

        assertTrue(centroExposicion.confgiurarSorteoDiaHora(exposicion1, fechaSorteo, 2, dia, hora));
    }

    @Test
    public void testConfgiurarSorteoDiaHoraFalloFechaSorteo() {
        Exposicion exposicion1 = new Exposicion("Exposicion 1", LocalDate.now(), LocalDate.now().plusDays(30),
                "Descripción",
                TipoExpo.TEMPORAL, 10.0);

        centroExposicion.addExposicion(exposicion1);
        LocalDate fechaSorteo = LocalDate.now().minusDays(5);
        LocalDate dia = LocalDate.now().plusDays(4);
        Hora hora = exposicion1.getHora(fechaSorteo, 15);

        assertFalse(centroExposicion.confgiurarSorteoDiaHora(exposicion1, fechaSorteo, 2, dia, hora));
    }

    @Test
    public void testConfgiurarSorteoExposicion() {
        Exposicion exposicion1 = new Exposicion("Exposicion 1", LocalDate.now(), LocalDate.now().plusDays(30),
                "Descripción",
                TipoExpo.TEMPORAL, 10.0);
        centroExposicion.addExposicion(exposicion1);
        assertTrue(centroExposicion.confgiurarSorteoExposicion(exposicion1, LocalDate.now().plusDays(5),
                2));
    }

    @Test
    public void testConfgiurarSorteoExposicionFalloFecha() {
        Exposicion exposicion1 = new Exposicion("Exposicion 1", LocalDate.now(), LocalDate.now().plusDays(30),
                "Descripción",
                TipoExpo.TEMPORAL, 10.0);
        centroExposicion.addExposicion(exposicion1);
        assertFalse(centroExposicion.confgiurarSorteoExposicion(exposicion1, LocalDate.now().minusDays(5),
                2));
    }

    @Test
    public void testConfgiurarSorteoFechas() {
        Exposicion exposicion1 = new Exposicion("Exposicion 1", LocalDate.now(), LocalDate.now().plusDays(30),
                "Descripción",
                TipoExpo.TEMPORAL, 10.0);
        centroExposicion.addExposicion(exposicion1);
        assertTrue(centroExposicion.confgiurarSorteoFechas(exposicion1, LocalDate.now().plusDays(5),
                2, LocalDate.now().plusDays(6),
                LocalDate.now().plusDays(9)));
    }

    @Test
    public void testRemoveSorteo() {
        gestor.logIn();

        Exposicion exposicion1 = new Exposicion("Exposicion 1", LocalDate.now(), LocalDate.now().plusDays(30),
                "Descripción",
                TipoExpo.TEMPORAL, 10.0);

        Sorteo sorteo = new SorteoExpo(exposicion1, LocalDate.now().plusDays(2), 10);
        centroExposicion.addSorteo(sorteo);
        centroExposicion.removeSorteo(sorteo);
        assertFalse(centroExposicion.getSorteos().contains(sorteo));
    }

    @Test
    public void testGetObras() {
        assertTrue(centroExposicion.getObras().isEmpty());
    }

    @Test
    public void testGetObrasAlmacenadas() {
        Obra obra1 = new Cuadro("Mona Lisa", 1503, "Cuadro famoso", false, 1000000.0, "12345", 1.5, 1.2, 26, 20, 90, 20,
                "óleo", "Leonardo da Vinci");
        centroExposicion.addObra(obra1);
        assertTrue(centroExposicion.getObrasAlmacenadas().contains(obra1));
    }

    @Test
    public void testGetObraPorNombre() {
        Obra obra1 = new Cuadro("Mona Lisa", 1503, "Cuadro famoso", false, 1000000.0, "12345", 1.5, 1.2, 26, 20, 90, 20,
                "óleo", "Leonardo da Vinci");
        centroExposicion.addObra(obra1);
        assertEquals(obra1, centroExposicion.getObraPorNombre("Mona Lisa"));
    }

    @Test
    public void testGetObraPorNombreFallo() {
        Obra obra1 = new Cuadro("Mona Lisa", 1503, "Cuadro famoso", false, 1000000.0, "12345", 1.5, 1.2, 26, 20, 90, 20,
                "óleo", "Leonardo da Vinci");
        centroExposicion.addObra(obra1);
        assertNull(centroExposicion.getObraPorNombre("miau"));
    }

    @Test
    public void testSetObras() {
        gestor.logIn();
        Set<Obra> obras = new HashSet<>();
        obras.add(new Cuadro("Mona Lisa", 1503, "Cuadro famoso", false, 1000000.0, "12345", 1.5, 1.2, 26, 20, 90, 20,
                "óleo", "Leonardo da Vinci"));
        centroExposicion.setObras(obras);
        assertEquals(obras, centroExposicion.getObras());
    }

    @Test
    public void testAddObra() {
        Obra obra1 = new Cuadro("Mona Lisa", 1503, "Cuadro famoso", false, 1000000.0, "12345", 1.5, 1.2, 26, 20, 90, 20,
                "óleo", "Leonardo da Vinci");
        assertTrue(centroExposicion.addObra(obra1));
        assertTrue(centroExposicion.getObras().contains(obra1));
    }

    @Test
    public void testRemoveObra() {
        gestor.logIn();
        Obra obra1 = new Cuadro("Mona Lisa", 1503, "Cuadro famoso", false, 1000000.0, "12345", 1.5, 1.2, 26, 20, 90, 20,
                "óleo", "Leonardo da Vinci");
        centroExposicion.addObra(obra1);
        assertTrue(centroExposicion.removeObra(obra1));
        assertFalse(centroExposicion.getObras().contains(obra1));
    }

    @Test
    public void testGetEmpleados() {
        assertTrue(centroExposicion.getEmpleados().isEmpty());
    }

    @Test
    public void testSetEmpleados() {
        gestor.logIn();
        Set<Empleado> empleados = new HashSet<>();
        empleados.add(new Empleado("11111111A", "Pepe", "1234", "1234123412341234", "Calle", true, true, true, true));
        centroExposicion.setEmpleados(empleados);
        assertEquals(empleados, centroExposicion.getEmpleados());
    }

    @Test
    public void testAddEmpleado() {
        Empleado empleado = new Empleado("11111111A", "Pepe", "1234", "1234123412341234", "Dir 1", true, true, true,
                true);
        assertTrue(centroExposicion.addEmpleado(empleado));
        assertTrue(centroExposicion.getEmpleados().contains(empleado));
    }

    @Test
    public void testRemoveEmpleado() {
        gestor.logIn();
        Empleado empleado = new Empleado("11111111A", "Pepe", "1234", "1234123412341234", "Dir 1", true, true, true,
                true);
        centroExposicion.addEmpleado(empleado);
        assertTrue(centroExposicion.removeEmpleado(empleado));
        assertFalse(centroExposicion.getEmpleados().contains(empleado));
    }

    @Test
    public void testSetSalaTemperatura() {
        gestor.logIn();
        Empleado empleado = new Empleado("11111111A", "Pepe", "1234", "1234123412341234", "Dir 1", true, true, true,
                true);
        centroExposicion.addEmpleado(empleado);
        centroExposicion.loginEmpleado("11111111A", "passwordEmpleado");
        Sala sala = centroExposicion.getSalas().iterator().next();
        empleado.setPermisoControl(true);
        empleado.logIn();
        assertTrue(centroExposicion.setSalaTemperatura(sala, 25, empleado));
        assertEquals(25, sala.getTemperatura());
    }

    @Test
    public void testLoginEmpleado() {
        Empleado empleado = new Empleado("11111111A", "Pepe", "1234", "1234123412341234", "Dir 1", true, true, true,
                true);
        centroExposicion.addEmpleado(empleado);
        assertTrue(centroExposicion.loginEmpleado("1234", "passwordEmpleado"));
        assertTrue(empleado.isLoged());
    }

    @Test
    public void testVenderEntrada() {
        gestor.logIn();
        Exposicion exposicion1 = new Exposicion("Exposicion 1", LocalDate.now(), LocalDate.now().plusDays(30),
                "Descripción",
                TipoExpo.TEMPORAL, 10.0);

        Sala sala = new Sala("Sala expo 1", 100, true, 10, 10.0, 10.0, 10.0);

        SalaExposicion salaExpo = new SalaExposicion(sala);

        exposicion1.addSala(salaExpo);

        Cuadro cuadro1 = new Cuadro("Mona Lisa", 1503, "Cuadro famoso", false, 1000000.0, "12345", 1.5, 1.2, 26, 20,
                90, 20, "óleo", "Leonardo da Vinci");

        salaExpo.addObra(cuadro1);

        centroExposicion.addExposicion(exposicion1);

        try {
            exposicion1.expoPublicar();
        } catch (Exception e) {

        }
        Hora horaPrueba = exposicion1.getHora(LocalDate.now(), 15);

        Integer numEntradas = 2;
        assertTrue(centroExposicion.venderEntrada(exposicion1, horaPrueba, numEntradas));
    }

    @Test
    public void testLoginGestor() {
        assertFalse(centroExposicion.loginGestor("contraseñaIncorrecta"));
        assertFalse(centroExposicion.getGestor().isLoged());
        assertTrue(centroExposicion.loginGestor("passwordGestor"));
        assertTrue(centroExposicion.getGestor().isLoged());
    }

    @Test
    public void testGetSubSalaPorNombreNoExistente() {
        assertNull(centroExposicion.getSubSalaPorNombre("SubSala Inexistente"));
    }

    @Test
    public void testGetExposicionPorNombreExistente() {
        Exposicion exposicion1 = new Exposicion("Exposicion 1", LocalDate.now(), LocalDate.now().plusDays(30),
                "Descripción", TipoExpo.TEMPORAL, 10.0);
        centroExposicion.addExposicion(exposicion1);
        assertEquals(exposicion1, centroExposicion.getExposicionPorNombre("Exposicion 1"));
    }

    @Test
    public void testGetExposicionPorNombreNoExistente() {
        assertNull(centroExposicion.getExposicionPorNombre("Exposicion Inexistente"));
    }

    @Test
    public void testGetExposicionesPorTipoObraConObras() {
        gestor.logIn();
        Exposicion exposicion1 = new Exposicion("Exposicion 1", LocalDate.now(), LocalDate.now().plusDays(30),
                "Descripción", TipoExpo.TEMPORAL, 10.0);

        SalaExposicion salaExpo = new SalaExposicion(salas.iterator().next());
        exposicion1.addSala(salaExpo);

        Cuadro cuadro1 = new Cuadro("Mona Lisa", 1503, "Cuadro famoso", false, 1000000.0, "12345", 1.5, 1.2, 26, 20, 90,
                20, "óleo", "Leonardo da Vinci");
        salaExpo.addObra(cuadro1);
        centroExposicion.addExposicion(exposicion1);

        try {
            exposicion1.expoPublicar();
        } catch (Exception e) {
        }

        Set<Exposicion> exposicionesPorTipoObra = centroExposicion.getExposicionesPorTipoObra("Cuadro");
        assertTrue(exposicionesPorTipoObra.contains(exposicion1));
    }

    @Test
    public void testInscribirClienteActividadExito() {
        Sala sala = salas.iterator().next();
        Actividad actividad = new Actividad("Actividad 1", TipoActividad.CONFERENCIA, "Descripción", 20,
                LocalDate.now(), LocalTime.now().plusHours(1), sala);

        try {
            centroExposicion.addActividad(actividad.getNombre(), actividad.getTipo(), actividad.getDescripcion(),
                    actividad.getMaxParticipantes(), actividad.getFecha(), actividad.getHora(),
                    actividad.getSalaCelebracion());
        } catch (Exception e) {
        }

        assertTrue(centroExposicion.inscribirClienteActividad(actividad, "12345678A"));
        assertTrue(actividad.getParticipantes().contains("12345678A"));
    }

    @Test
    public void testInscribirClienteActividadFracasoActividadPasada() {
        Sala sala = salas.iterator().next();
        Actividad actividad = new Actividad("Actividad 1", TipoActividad.CONFERENCIA, "Descripción", 20,
                LocalDate.now().minusDays(1), LocalTime.of(10, 0), sala);
        try {
            centroExposicion.addActividad(actividad.getNombre(), actividad.getTipo(), actividad.getDescripcion(),
                    actividad.getMaxParticipantes(), actividad.getFecha(), actividad.getHora(),
                    actividad.getSalaCelebracion());
        } catch (Exception e) {
        }

        assertFalse(centroExposicion.inscribirClienteActividad(actividad, "12345678A"));
        assertFalse(actividad.getParticipantes().contains("12345678A"));
    }

    @Test
    public void testInscribirClienteActividadLlena() {
        Sala sala = salas.iterator().next();
        Actividad actividad = new Actividad("Actividad 1", TipoActividad.CONFERENCIA, "Descripción", 1,
                LocalDate.now(), LocalTime.now().plusHours(1), sala);

        try {
            centroExposicion.addActividad(actividad.getNombre(), actividad.getTipo(), actividad.getDescripcion(),
                    actividad.getMaxParticipantes(), actividad.getFecha(), actividad.getHora(),
                    actividad.getSalaCelebracion());
        } catch (Exception e) {
        }

        assertTrue(centroExposicion.inscribirClienteActividad(actividad, "12345678A"));
        assertFalse(centroExposicion.inscribirClienteActividad(actividad, "87654321B"));
    }

    @Test
    public void testVenderEntradaExposicionNoPublicada() {
        Exposicion exposicion1 = new Exposicion("Exposicion 1", LocalDate.now(), LocalDate.now().plusDays(30),
                "Descripción", TipoExpo.TEMPORAL, 10.0);
        centroExposicion.addExposicion(exposicion1);
        Hora horaPrueba = exposicion1.getHora(LocalDate.now(), 15);
        Integer numEntradas = 2;
        assertFalse(centroExposicion.venderEntrada(exposicion1, horaPrueba, numEntradas));
    }

    @Test
    public void testVenderEntradaExposicionFechaIncorrecta() {
        gestor.logIn();
        Exposicion exposicion1 = new Exposicion("Exposicion 1", LocalDate.now(), LocalDate.now().plusDays(30),
                "Descripción",
                TipoExpo.TEMPORAL, 10.0);
        centroExposicion.addExposicion(exposicion1);
        try {
            exposicion1.expoPublicar();
        } catch (Exception e) {

        }
        Hora horaPrueba = exposicion1.getHora(LocalDate.now().plusDays(40), 15);
        Integer numEntradas = 2;
        assertFalse(centroExposicion.venderEntrada(exposicion1, horaPrueba, numEntradas));
    }

    @Test
    public void testGetActividadPorNombreExistente() {
        Sala sala = salas.iterator().next();
        Actividad actividad = new Actividad("Actividad 1", TipoActividad.CONFERENCIA, "Descripción", 20,
                LocalDate.now().plusDays(1), LocalTime.of(10, 0), sala);
        try {
            centroExposicion.addActividad(actividad.getNombre(), actividad.getTipo(), actividad.getDescripcion(),
                    actividad.getMaxParticipantes(), actividad.getFecha(), actividad.getHora(),
                    actividad.getSalaCelebracion());
        } catch (Exception e) {
        }

        assertEquals(actividad, centroExposicion.getActividadPorNombre("Actividad 1"));
    }

    @Test
    public void testGetActividadPorNombreNoExistente() {
        assertNull(centroExposicion.getActividadPorNombre("Actividad Inexistente"));
    }

    @Test
    public void testSetSalaHumedadExito() {
        gestor.logIn();
        Empleado empleado = new Empleado("11111111A", "Pepe", "1234", "1234123412341234", "Dir 1", true, true, true,
                true);
        centroExposicion.addEmpleado(empleado);
        centroExposicion.loginEmpleado("1234", "passwordEmpleado");
        Sala sala = centroExposicion.getSalas().iterator().next();
        empleado.setPermisoControl(true);
        empleado.logIn();
        assertTrue(centroExposicion.setSalaHumedad(sala, 60, empleado));
        assertEquals(60, sala.getHumedad());
    }

    @Test
    public void testSetSalaHumedadFracasoNoLogueado() {
        gestor.logIn();
        Empleado empleado = new Empleado("11111111A", "Pepe", "1234", "1234123412341234", "Dir 1", true, true, true,
                true);
        centroExposicion.addEmpleado(empleado);
        Sala sala = centroExposicion.getSalas().iterator().next();
        empleado.setPermisoControl(true);
        assertFalse(centroExposicion.setSalaHumedad(sala, 60, empleado));
        assertNotEquals(60, sala.getHumedad());
    }

    @Test 
    public void testSetSalaHumedadFracasoSinPermisos() {
        gestor.logIn();
        Empleado empleado = new Empleado("11111111A", "Pepe", "1234", "1234123412341234", "Dir 1", true, false, true,
                true);
        centroExposicion.addEmpleado(empleado);
        centroExposicion.loginEmpleado("1234", "passwordEmpleado");
        Sala sala = centroExposicion.getSalas().iterator().next();
        empleado.logIn();
        assertFalse(centroExposicion.setSalaHumedad(sala, 60, empleado));
        assertNotEquals(60, sala.getHumedad());
    }

    @Test
    public void testSetSalaTemperaturaExito() {
        gestor.logIn();
        Empleado empleado = new Empleado("11111111A", "Pepe", "1234", "1234123412341234", "Dir 1", true, true, true, true);
        centroExposicion.addEmpleado(empleado);
        centroExposicion.loginEmpleado("1234", "passwordEmpleado");
        Sala sala = centroExposicion.getSalas().iterator().next();
        empleado.setPermisoControl(true);
        empleado.logIn();
        assertTrue(centroExposicion.setSalaTemperatura(sala, 22, empleado));
        assertEquals(22, sala.getTemperatura());
    }

    @Test
    public void testSetSalaTemperaturaFracasoNoLogueado() {
        gestor.logIn();
        Empleado empleado = new Empleado("11111111A", "Pepe", "1234", "1234123412341234", "Dir 1", true, true, true, true);
        centroExposicion.addEmpleado(empleado);
        Sala sala = centroExposicion.getSalas().iterator().next();
        assertFalse(centroExposicion.setSalaTemperatura(sala, 22, empleado));
        assertNotEquals(22, sala.getTemperatura());
    }

    @Test
    public void testSetSalaTemperaturaFracasoSinPermisos() {
        gestor.logIn();
        Empleado empleado = new Empleado("11111111A", "Pepe", "1234", "1234123412341234", "Dir 1", true, false, true, true);
        centroExposicion.addEmpleado(empleado);
        centroExposicion.loginEmpleado("1234", "passwordEmpleado");
        Sala sala = centroExposicion.getSalas().iterator().next();
        empleado.logIn(); 
        assertFalse(centroExposicion.setSalaTemperatura(sala, 22, empleado));
        assertNotEquals(22, sala.getTemperatura());
    }

}