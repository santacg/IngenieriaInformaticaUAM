package Obra;

import java.util.HashSet;
import java.util.Set;

/**
 * Clase Obra.
 * Esta clase abstracta representa una obra de arte con características
 * generales. Cada obra tiene un identificador único, un nombre, un año de
 * creación, una descripción, un estado, un conjunto de autores, un indicador de
 * si es externa, una cuantía de seguro y un número de seguro.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public abstract class Obra {
    private Integer ID;
    private static Integer IDcount = 0;
    private String nombre;
    private Integer anio;
    private String descripcion;
    private Boolean externa;
    private Double cuantiaSeguro;
    private String numeroSeguro;
    private Set<Autor> autores = new HashSet<>();
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
     * @param estado        El estado inicial de la obra.
     */
    public Obra(String nombre, Integer anio, String descripcion, Boolean externa, Double cuantiaSeguro,
            String numeroSeguro, Estado estado) {
        this.ID = IDcount++;
        this.nombre = nombre;
        this.anio = anio;
        this.descripcion = descripcion;
        this.externa = externa;
        this.cuantiaSeguro = cuantiaSeguro;
        this.numeroSeguro = numeroSeguro;
        this.estado = estado;
    }

    /**
     * Getters y setters que facilitan la gestión de una obra.
     */
    public Integer getID() {
        return ID;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getAnio() {
        return anio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Boolean getExterna() {
        return externa;
    }

    public Double getCuantiaSeguro() {
        return cuantiaSeguro;
    }

    public String getNumeroSeguro() {
        return numeroSeguro;
    }

    public Set<Autor> getAutores() {
        return autores;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setExterna(Boolean externa) {
        this.externa = externa;
    }

    public void setCuantiaSeguro(Double cuantiaSeguro) {
        this.cuantiaSeguro = cuantiaSeguro;
    }

    public void setNumeroSeguro(String numeroSeguro) {
        this.numeroSeguro = numeroSeguro;
    }

    public void setAutores(Set<Autor> autor) {
        this.autores = autor;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    /**
     * Cambia el estado de la obra a RETIRADA.
     */
    public void retirarObra() {
        this.estado = Estado.RETIRADA;
    }

    /**
     * Cambia el estado de la obra a PRESTADA.
     */
    public void prestarObra() {
        if (this.externa == true) {
            System.out.println("No se puede prestar una obra externa");
            return;
        }
        this.estado = Estado.PRESTADA; 
    }

    /**
     * Cambia el estado de la obra a ALMACENADA.
     */
    public void almecenarObra() {
        this.estado = Estado.ALMACENADA;
    }

    /**
     * Cambia el estado de la obra a EXPUESTA.
     */
    public void exponerObra() {
        this.estado = Estado.EXPUESTA;
    }

    /**
     * Cambia el estado de la obra a RESTAURACION.
     */
    public void restaurarObra() {
        this.estado = Estado.RESTAURACION;
    }

    /**
     * Añade un autor a la lista de autores de la obra.
     *
     * @param autor El autor a añadir.
     */
    public void addAutor(Autor autor) {
        this.autores.add(autor);
    }

    /**
     * Elimina un autor de la lista de autores de la obra.
     *
     * @param autor El autor a eliminar.
     */
    public void removeAuotor(Autor autor) {
        this.autores.remove(autor);
    }

    /**
     * Devuelve una representación en cadena de la obra, incluyendo ID, nombre, año,
     * descripción, y más.
     *
     * @return Una cadena que representa la obra.
     */
    public String toString() {
        return "Obra [ID=" + ID + ", nombre=" + nombre + ", anio=" + anio + ", descripcion=" + descripcion
                + ", externa=" + externa + ", cuantiaSeguro=" + cuantiaSeguro + ", numeroSeguro=" + numeroSeguro
                + ", autores=" + autores.toString() + ", estado=" + estado + "]";
    }
}
