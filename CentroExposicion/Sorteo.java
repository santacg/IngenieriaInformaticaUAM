package CentroExposicion;

import java.time.LocalDate;
import java.util.Random;
import Exposicion.Exposicion;
import Inscripcion.Inscripcion;

import java.util.HashSet;
import java.util.Set;

import Expofy.ClienteRegistrado;
import Expofy.Notificacion;

/**
 * Clase Sorteo.
 * Esta clase abstracta gestiona la información básica de un sorteo, incluyendo
 * la fecha del sorteo, la exposición relacionada, los códigos generados para
 * los ganadores, y las inscripciones de los participantes.
 * Proporciona funcionalidades para manipular estos datos, como añadir o remover
 * códigos,y añadir inscripciones, así como realizar el sorteo en sí.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 *
 */
public abstract class Sorteo {
    private LocalDate fechaSorteo;
    private int n_entradas;
    private Exposicion exposicion;
    private Set<Inscripcion> inscripciones;

    /**
     * Construye una instancia de Sorteo asignando una fecha y una exposicion
     * 
     * @param fechaSorteo La fecha del sorteo
     * @param exposicion  La exposición relacionada con el sorteo
     */
    public Sorteo(LocalDate fechaSorteo, Exposicion exposicion) {
        this.fechaSorteo = fechaSorteo;
        this.exposicion = exposicion;
        this.inscripciones = new HashSet<Inscripcion>();
    }

    /**
     * Obtiene la fecha del sorteo.
     * 
     * @return la fecha en la que se realizará el sorteo.
     */
    public LocalDate getFechaSorteo() {
        return fechaSorteo;
    }

    /**
     * Establece la fecha del sorteo.
     * 
     * @param fechaSorteo la nueva fecha para el sorteo.
     */
    public void setFechaSorteo(LocalDate fechaSorteo) {
        this.fechaSorteo = fechaSorteo;
    }

    /**
     * Retorna el número de entradas disponibles para el sorteo.
     * 
     * @return el número de entradas.
     */
    public int getN_entradas() {
        return n_entradas;
    }

    /**
     * Establece el número de entradas disponibles para el sorteo.
     * 
     * @param n_entradas el nuevo número de entradas.
     */
    public void setN_entradas(int n_entradas) {
        this.n_entradas = n_entradas;
    }

    /**
     * Obtiene la exposición asociada al sorteo.
     * 
     * @return la exposición vinculada al sorteo.
     */
    public Exposicion getExposicion() {
        return exposicion;
    }

    /**
     * Vincula una exposición al sorteo.
     * 
     * @param exposicion la exposición a vincular.
     */
    public void setExposicion(Exposicion exposicion) {
        this.exposicion = exposicion;
    }

    public Set<Inscripcion> getInscripciones(){
        return inscripciones;
    }

    /**
     * Añade una inscripción al conjunto de inscripciones del sorteo.
     * 
     * @param inscripcion La inscripción a ser añadida.
     */
    public void addInscripcion(Inscripcion inscripcion) {
        inscripciones.add(inscripcion);
    }

    public void removeInscripcion(Inscripcion inscripcion) {
        inscripciones.remove(inscripcion);
    }

    /**
     * Selecciona aleatoriamente una inscripción de un conjunto de inscripciones.
     * 
     * @param set El conjunto de inscripciones entre las cuales elegir.
     * @return La inscripción seleccionada aleatoriamente.
     * @throws IllegalArgumentException Si el conjunto está vacío.
     */
    public static Inscripcion getRandomInscripcion(Set<Inscripcion> set) {
        int rand = new Random().nextInt(set.size());
        int i = 0;
        for (Inscripcion inscripcion : set) {
            if (i == rand) {
                return inscripcion;
            }
            i++;
        }
        throw new IllegalStateException("Something went wrong while picking a random element.");
    }

    /**
     * Genera un código aleatorio compuesto por letras y números.
     * 
     * @return El código generado.
     */
    public String generadorCodigo() {
        /*
         * Definir el conjunto de caracteres permitidos: números, letras mayúsculas y
         * minúsculas
         */
        String codigo, caracteresPermitidos = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        /* Crear un objeto aleatorio */
        Random random = new Random();

        /* StringBuilder para construir la cadena resultante */
        StringBuilder sb = new StringBuilder(4);

        /*
         * Generar 4 veces un carácter aleatorio del conjunto y añadirlo al
         * StringBuilder
         */
        for (int i = 0; i < 4; i++) {
            int indexRandom = random.nextInt(caracteresPermitidos.length());
            sb.append(caracteresPermitidos.charAt(indexRandom));
        }
        codigo = sb.toString();
        return codigo;
    }

    /**
     * Realiza el sorteo, asignando códigos a los ganadores y generando las
     * notificaciones correspondientes.
     * Este método determina los ganadores basándose en las inscripciones y la
     * cantidad de entradas disponibles. Para cada ganador, se genera un código
     * único y se crea una notificación que incluye el código y detalles de la
     * exposición asociada.
     */
    public void realizarSorteo() {
        int i, j;
        Inscripcion insc_ganadora;
        ClienteRegistrado ganador;
        Notificacion notificacion;
        String codigo, mensaje = "¡ENHORABUENA! tu participación al sorteo para la exposición \""
                + exposicion.getNombre() +
                "\" ha sido elegida, canjea los siguientes códigos al comprar tus etradas para que estas te salgan ¡GRATIS!: ";
        for (i = inscripciones.size(); n_entradas != 0 && i != 0; i--) {

            insc_ganadora = getRandomInscripcion(inscripciones);
            ganador = insc_ganadora.getCliente();
            if (insc_ganadora.getnEntradas() <= n_entradas) {
                for (j = 0; j < insc_ganadora.getnEntradas(); j++) {
                    codigo = generadorCodigo();
                    insc_ganadora.addCodigo(codigo);
                    mensaje = mensaje + codigo + " ";
                    n_entradas--;
                }
                notificacion = new Notificacion(mensaje, LocalDate.now());
                ganador.addNotificacion(notificacion);
            }
        }
    }

    public abstract LocalDate getFechaLimite();

}
