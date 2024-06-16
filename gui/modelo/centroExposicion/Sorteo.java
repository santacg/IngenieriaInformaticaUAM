package gui.modelo.centroExposicion;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Random;
import java.util.HashSet;
import java.util.Set;

import gui.modelo.expofy.ClienteRegistrado;
import gui.modelo.expofy.Expofy;
import gui.modelo.exposicion.Exposicion;
import gui.modelo.inscripcion.Inscripcion;

/**
 * Clase Sorteo.
 * Esta clase abstracta gestiona la información básica de un sorteo, incluyendo
 * la fecha del sorteo, la exposición relacionada, los códigos generados para
 * los ganadores, y las inscripciones de los participantes.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 *
 */
public abstract class Sorteo implements Serializable {
    private LocalDate fechaSorteo;
    private int n_entradas;
    private Exposicion exposicion;
    private Set<Inscripcion> inscripciones;
    private Boolean realizado = false;

    /**
     * Construye una instancia de Sorteo asignando una fecha y una exposicion
     * 
     * @param fechaSorteo   La fecha del sorteo
     * @param exposicion    La exposición relacionada con el sorteo
     * @param inscripciones Set con las inscripciones al sorteo
     */
    public Sorteo(LocalDate fechaSorteo, Exposicion exposicion, int n_entradas) {
        this.fechaSorteo = fechaSorteo;
        this.exposicion = exposicion;
        this.inscripciones = new HashSet<Inscripcion>();
        this.n_entradas = n_entradas;
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

    /**
     * Obtiene el conjunto de inscripciones asociadas.
     * 
     * @return Un conjunto de {@link Inscripcion} que representa todas las
     *         inscripciones actuales.
     */
    public Set<Inscripcion> getInscripciones() {
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

    /**
     * Elimina una inscripción específica del conjunto de inscripciones.
     * 
     * @param inscripcion La inscripción a eliminar.
     */
    public void removeInscripcion(Inscripcion inscripcion) {
        inscripciones.remove(inscripcion);
    }

    public Boolean clienteInscristo(String nif){
        for (Inscripcion inscripcion : inscripciones) {
            if (inscripcion.getCliente().getNIF().equals(nif)) {
                return true;
            }
        }
        return false;
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

        throw new IllegalArgumentException("El conjunto de inscripciones está vacío.");
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
     * notificaciones correspondientes. Para cada ganador, se genera un código
     * único y se crea una notificación que incluye el código y detalles de la
     * exposición asociada.
     */
    public void realizarSorteo() {
        int i, j;
        Expofy expofy = Expofy.getInstance();
        Inscripcion insc_ganadora = null;
        ClienteRegistrado ganador;
        String codigo, mensaje = "¡ENHORABUENA! tu participación al sorteo para la exposición \""
                + exposicion.getNombre() +
                "\" ha sido elegida, canjea los siguientes códigos al comprar tus etradas para que estas te salgan ¡GRATIS!: ";
        for (i = inscripciones.size(); n_entradas != 0 && i != 0; i--) {
            // Seleccionamos aleatoriamente una inscripción de la lista de inscripciones.
            insc_ganadora = getRandomInscripcion(inscripciones);
            ganador = insc_ganadora.getCliente();

            // Verificamos la condicion del número de entradas solicitadas
            if (insc_ganadora.getnEntradas() <= n_entradas) {
                // Asignamos un código único por cada entrada solicitada en la inscripción
                // ganadora.
                for (j = 0; j < insc_ganadora.getnEntradas(); j++) {
                    // Generamos un código único.
                    codigo = generadorCodigo();
                    insc_ganadora.addCodigo(codigo);
                    // Añadimos el código al mensaje de notificación.
                    mensaje = mensaje + codigo + " ";
                    n_entradas--;
                }
                expofy.enviarNotificacionUsuario(mensaje, ganador);
            }
        }
        mensaje = "Más suerte la próxima vez tu participación al sorteo para la exposición \""
                + exposicion.getNombre() +
                "\" no ha sido elegida";

        for (Inscripcion inscripcion : inscripciones) {
            if (!inscripcion.equals(insc_ganadora)) {
                expofy.enviarNotificacionUsuario(mensaje, inscripcion.getCliente());
            }
        }

        setRealizado();
    }

    /**
     * Comprueba si el sorteo ya ha sido realizado.
     * 
     * @return {@code true} si el sorteo ya ha sido realizado, {@code false} en caso
     *         contrario.
     */
    public Boolean isRealizado() {
        return realizado;
    }

    /**
     * Establece si el sorteo a realizado.
     */
    public void setRealizado() {
        this.realizado = true;
    }

    /**
     * Método abstracto para obtener la fecha límite asociada.
     * 
     * @return La fecha límite
     */
    public abstract LocalDate getFechaLimite();

    /**
     * Genera el códigi hash del sorteo.
     * 
     * @return Código hash del sorteo.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((fechaSorteo == null) ? 0 : fechaSorteo.hashCode());
        result = prime * result + ((exposicion == null) ? 0 : exposicion.hashCode());
        return result;
    }

    /**
     * Comprueba si este sorteo es igual al objeto proporcionado.
     * Dos sorteos se consideran iguales si tienen la misma fecha y exposición.
     * 
     * @param obj El objeto con el que comparar este {@code Sorteo}.
     * @return {@code true} si los objetos son iguales, {@code false} en caso
     *         contrario.
     * 
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Sorteo other = (Sorteo) obj;
        if (fechaSorteo == null) {
            if (other.fechaSorteo != null)
                return false;
        } else if (!fechaSorteo.equals(other.fechaSorteo))
            return false;
        if (exposicion == null) {
            if (other.exposicion != null)
                return false;
        } else if (!exposicion.equals(other.exposicion))
            return false;
        return true;
    }

}
