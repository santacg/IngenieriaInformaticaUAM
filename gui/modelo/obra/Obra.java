package gui.modelo.obra;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import gui.modelo.utils.ExcepcionMensaje;

import java.io.Serializable;

/**
 * Clase Obra.
 * Esta clase abstracta representa una obra de arte con características
 * generales. Cada obra tiene un identificador único, un nombre, un año de
 * creación, una descripción, un estado, un conjunto de autores, un indicador de
 * si es externa, una cuantía de seguro y un número de seguro.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public abstract class Obra implements Serializable {
    private String nombre;
    private Integer anio;
    private String descripcion;
    private Boolean externa;
    private Double cuantiaSeguro;
    private String numeroSeguro;
    private Set<String> autores = new HashSet<>();
    private Estado estado;

    /**
     * Crea una nueva instancia de una obra de arte con los parámetros
     * especificados.
     *
     * @param nombre        El nombre de la obra.
     * @param anio          El año de creación de la obra.
     * @param descripcion   Una breve descripción de la obra.
     * @param externa       Indica si la obra es externa o no.
     * @param cuantiaSeguro El valor del seguro de la obra.
     * @param numeroSeguro  El número de la póliza del seguro.
     * @param autores       Los autores de la obra.
     */
    public Obra(String nombre, Integer anio, String descripcion, Boolean externa, Double cuantiaSeguro,
            String numeroSeguro, String... autores) {
        this.nombre = nombre;
        this.anio = anio;
        this.descripcion = descripcion;
        this.externa = externa;
        this.cuantiaSeguro = cuantiaSeguro;
        this.numeroSeguro = numeroSeguro;
        this.estado = Estado.ALMACENADA;
        this.autores.addAll(Arrays.asList(autores));
    }

    /**
     * Genera el codigo hash de la obra.
     * 
     * @return El código hash de la obra.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
        result = prime * result + ((anio == null) ? 0 : anio.hashCode());
        result = prime * result + ((autores == null) ? 0 : autores.hashCode());
        return result;
    }

    /**
     * Comprueba si este objeto {@code Obra} es igual a otro objeto.
     * Este método devuelve {@code true} solo si el objeto proporcionado es una
     * instancia de {@code Obra} y todos los atributos mencionados son iguales
     * en ambos objetos.
     * 
     * @param obj el objeto con el que se compara esta {@code Obra} para la
     *            igualdad.
     * @return {@code true} si el objeto proporcionado es igual a esta obra;
     *         {@code false} en caso contrario.
     */

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Obra other = (Obra) obj;
        if (nombre == null) {
            if (other.nombre != null)
                return false;
        } else if (!nombre.equals(other.nombre))
            return false;
        return true;
    }

    /**
     * Obtiene el nombre de la entidad.
     * 
     * @return El nombre como String.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el año asociado a la entidad.
     * 
     * @return El año como Integer.
     */
    public Integer getAnio() {
        return anio;
    }

    /**
     * Obtiene la descripción de la entidad.
     * 
     * @return La descripción como String.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Indica si la entidad es externa.
     * 
     * @return Verdadero si es externa, falso de lo contrario.
     */
    public Boolean getExterna() {
        return externa;
    }

    /**
     * Obtiene la cuantía del seguro asociada a la entidad.
     * 
     * @return La cuantía del seguro como Double.
     */
    public Double getCuantiaSeguro() {
        return cuantiaSeguro;
    }

    /**
     * Obtiene el número de seguro asociado a la entidad.
     * 
     * @return El número de seguro como String.
     */
    public String getNumeroSeguro() {
        return numeroSeguro;
    }

    /**
     * Obtiene el conjunto de autores asociados a la entidad.
     * 
     * @return El conjunto de autores como Set<String>.
     */
    public Set<String> getAutores() {
        return autores;
    }

    /**
     * Obtiene el estado actual de la entidad.
     * 
     * @return El estado como Estado.
     */
    public Estado getEstado() {
        return estado;
    }

    /**
     * Establece el nombre de la entidad.
     * 
     * @param nombre El nombre a establecer.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Establece el año asociado a la entidad.
     * 
     * @param anio El año a establecer.
     */
    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    /**
     * Establece la descripción de la entidad.
     * 
     * @param descripcion La descripción a establecer.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Establece si la entidad es externa.
     */
    public void setExterna() {
        this.externa = true;
    }

    /**
     * Establece la cuantía del seguro asociada a la entidad.
     * 
     * @param cuantiaSeguro La cuantía del seguro a establecer.
     */
    public void setCuantiaSeguro(Double cuantiaSeguro) {
        this.cuantiaSeguro = cuantiaSeguro;
    }

    /**
     * Establece el número de seguro asociado a la entidad.
     * 
     * @param numeroSeguro El número de seguro a establecer.
     */
    public void setNumeroSeguro(String numeroSeguro) {
        this.numeroSeguro = numeroSeguro;
    }

    /**
     * Establece el conjunto de autores asociados a la entidad.
     * 
     * @param autores El conjunto de autores a establecer.
     */
    public void setAutores(String... autores) {
        this.autores.addAll(Arrays.asList(autores));
    }

    /**
     * Añade un autor a la obra.
     * 
     * @param autor El autor a añadir.
     */
    public void addAutor(String autor) {
        this.autores.add(autor);
    }

    /**
     * Elimina un autor de la obra.
     * 
     * @param autor El autor a eliminar.
     */
    public void removeAutor(String autor) {
        this.autores.remove(autor);
    }

    /**
     * Cambia el estado de la obra a RETIRADA.
     * 
     */
    public void retirarObra() throws ExcepcionMensaje {

        if (this.estado == Estado.RETIRADA) {
            throw new ExcepcionMensaje("No se puede retirar una obra que ya está retirada");
        }

        if (this.estado != Estado.ALMACENADA && this.estado != Estado.EXPUESTA) {
            throw new ExcepcionMensaje("No se puede retirar una obra que no está almacenada o expuesta");
        }

        this.estado = Estado.RETIRADA;
    }

    /**
     * Cambia el estado de la obra a PRESTADA.
     */
    public void prestarObra() throws ExcepcionMensaje {

        if (this.estado == Estado.PRESTADA) {
            throw new ExcepcionMensaje("No se puede prestar una obra que ya está prestada");
        }

        if (this.externa == true) {
            throw new ExcepcionMensaje("No se puede prestar una obra externa");
        }

        if (this.estado != Estado.ALMACENADA && this.estado != Estado.EXPUESTA) {
            throw new ExcepcionMensaje("No se puede prestar una obra que no está almacenada o expuesta");
        }

        this.estado = Estado.PRESTADA;
    }

    /**
     * Cambia el estado de la obra a ALMACENADA.
     */
    public void almacenarObra() throws ExcepcionMensaje {

        if (this.estado == Estado.ALMACENADA) {
            throw new ExcepcionMensaje("No se puede almacenar una obra que ya está almacenada");
        }

        if (this.estado == Estado.RETIRADA) {
            throw new ExcepcionMensaje("No se puede almacenar una obra que está retirada");
        }

        this.estado = Estado.ALMACENADA;
    }

    /**
     * Cambia el estado de la obra a EXPUESTA.
     * 
     */
    public void exponerObra() throws ExcepcionMensaje {

        if (this.estado == Estado.EXPUESTA) {
            throw new ExcepcionMensaje("No se puede exponer una obra que ya está expuesta");
        }

        if (this.estado != Estado.ALMACENADA) {
            throw new ExcepcionMensaje("No se puede exponer una obra que no está almacenada");
        }

        this.estado = Estado.EXPUESTA;
    }

    /**
     * Cambia el estado de la obra a RESTAURACION.
     * 
     */

    public void restaurarObra() throws ExcepcionMensaje {

        if (this.estado == Estado.RESTAURACION) {
            throw new ExcepcionMensaje("No se puede restaurar una obra que ya está en restauración");
        }

        if (this.estado != Estado.ALMACENADA && this.estado != Estado.EXPUESTA) {
            throw new ExcepcionMensaje("No se puede restaurar una obra que no está almacenada o expuesta");
        }

        this.estado = Estado.RESTAURACION;
    }

    /**
     * Recibe el tipo de obra.
     * 
     * @return El tipo de obra
     */
    public String getTipoObra() {
        return this.getClass().getSimpleName();
    }

    /**
     * Devuelve una representación en cadena de la obra, incluyendo ID, nombre, año,
     * descripción, y más.
     *
     * @return Una cadena que representa la obra.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Detalles obra:\n");
        sb.append("Nombre: ").append(nombre).append("\n");
        sb.append("Año: ").append(anio).append("\n");
        sb.append("Descripción: ").append(descripcion).append("\n");
        sb.append("Externa: ").append(externa).append("\n");
        sb.append("Cuantía Seguro: ").append(cuantiaSeguro).append("\n");
        sb.append("Número Seguro: ").append(numeroSeguro).append("\n");
        sb.append("Autores: ").append(autores).append("\n");
        sb.append("Estado: ").append(estado).append("\n");

        return sb.toString();
    }
}
