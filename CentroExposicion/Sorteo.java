package CentroExposicion;

import java.time.LocalDate;
import java.util.Date;
import java.util.Random;
import Exposicion.Exposicion;
import Inscripcion.Inscripcion;

import java.util.Set;

import Expofy.ClienteRegistrado;
import Expofy.Notificacion;

public abstract class Sorteo {
    private Date fechaSorteo;
    private int n_entradas;
    private Exposicion exposicion;
    private Set<String> codigos;
    private Set<Inscripcion> inscripciones;

    public Sorteo(Date fechaSorteo, Exposicion exposicion) {
        this.fechaSorteo = fechaSorteo;
        this.exposicion = exposicion;
    }

    public Date getFechaSorteo() {
        return fechaSorteo;
    }

    public void setFechaSorteo(Date fechaSorteo) {
        this.fechaSorteo = fechaSorteo;
    }

    public int getN_entradas() {
        return n_entradas;
    }

    public void setN_entradas(int n_entradas) {
        this.n_entradas = n_entradas;
    }

    public Exposicion getExposicion() {
        return exposicion;
    }

    public void setExposicion(Exposicion exposicion) {
        this.exposicion = exposicion;
    }

    public Set<String> getCodigos() {
        return codigos;
    }

    public void addCodigo(String codigo) {
        if (codigo.length() != 4) {
            return;
        }
        codigos.add(codigo);
    }

    public void removeCodigo(String codigo) {
        codigos.remove(codigo);
    }

    public static <Inscripcion> Inscripcion getRandomInscripcion(Set<Inscripcion> set) {
        if (set == null || set.isEmpty()) {
            throw new IllegalArgumentException("The Set cannot be empty.");
        }
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

    public String generadorCodigo() {
        /*
         * Definir el conjunto de caracteres permitidos: números, letras mayúsculas y
         * minúsculas
         */
        String codigo, caracteresPermitidos = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        /* Crear un objeto Random */
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

    public void realizarSorteo() {
        int i, j;
        Inscripcion insc_ganadora;
        ClienteRegistrado ganador;
        Notificacion notificacion;
        String codigo;
        for (i = inscripciones.size(); n_entradas != 0 && i != 0; i--) {
            insc_ganadora = getRandomInscripcion(inscripciones);
            ganador = insc_ganadora.getCliente();
            for (j = 0; j < insc_ganadora.getnEntradas() && n_entradas >= insc_ganadora.getnEntradas(); j++) {
                codigo = generadorCodigo();
                this.addCodigo(codigo);
                notificacion = new Notificacion(codigo, LocalDate.now());
                ganador.addNotificacion(notificacion);
                n_entradas--;
            }
        }
    }
}
