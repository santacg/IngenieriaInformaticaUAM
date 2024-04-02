package Obra;

import java.util.HashSet;
import java.util.Set;
import java.time.LocalDate;

/**
 * Clase Autor.
 * Es una clase que representa a un autor de obras de arte dentro del sistema.
 * Cada autor está caracterizado por su nombre, fechas de nacimiento y
 * fallecimiento, lugares de nacimiento y fallecimiento, así como un conjunto de
 * obras de arte asociadas a él o ella.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public class Autor {
    private String nombre;
    private LocalDate fechaNacimiento;
    private LocalDate fechaFallecimiento;
    private String lugarNacimiento;
    private String lugarFallecimiento;
    private Set<Obra> obras = new HashSet<>();

    public Set<Obra> getObras() {
        return obras;
    }

    public void setObras(Set<Obra> obras) {
        this.obras = obras;
    }

    public void addObra(Obra obra) {
        this.obras.add(obra);
    }

    public void removeObra(Obra obra) {
        this.obras.remove(obra);
    }

    /**
     * Constructor de un autor.
     *
     * @param nombre             el nombre del autor
     * @param fechaNacimiento    la fecha de nacimiento del autor
     * @param fechaFallecimiento la fecha de fallecimiento del autor
     * @param lugarNacimiento    el lugar de nacimiento del autor
     * @param lugarFallecimiento el lugar de fallecimiento del autor
     */
    public Autor(String nombre, LocalDate fechaNacimiento, LocalDate fechaFallecimiento, String lugarNacimiento,
            String lugarFallecimiento) {
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaFallecimiento = fechaFallecimiento;
        this.lugarNacimiento = lugarNacimiento;
        this.lugarFallecimiento = lugarFallecimiento;
    }

    /**
     * Getters y setters de la clase Autor.
     */
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public LocalDate getFechaFallecimiento() {
        return fechaFallecimiento;
    }

    public void setFechaFallecimiento(LocalDate fechaFallecimiento) {
        this.fechaFallecimiento = fechaFallecimiento;
    }

    public String getLugarNacimiento() {
        return lugarNacimiento;
    }

    public void setLugarNacimiento(String lugarNacimiento) {
        this.lugarNacimiento = lugarNacimiento;
    }

    public String getLugarFallecimiento() {
        return lugarFallecimiento;
    }

    public void setLugarFallecimiento(String lugarFallecimiento) {
        this.lugarFallecimiento = lugarFallecimiento;
    }

    /**
     * Crea una cadena que representa un autor.
     * 
     * @return La cadena de representación del autor
     */
    public String toString() {
        return "Autor [nombre=" + nombre + ", fechaNacimiento=" + fechaNacimiento + ", fechaFallecimiento="
                + fechaFallecimiento + ", lugarNacimiento=" + lugarNacimiento + ", lugarFallecimiento="
                + lugarFallecimiento + ", obras=" + obras + "]";
    }
}
