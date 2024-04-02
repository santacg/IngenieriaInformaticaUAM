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

    /**
     * Comprueba si este Autor es igual a otro objeto.
     * Considera iguales a dos autores si todos sus atributos (nombre, fechas y
     * lugares de nacimiento y fallecimiento, y sus obras) son iguales.
     * 
     * @param obj El objeto con el que se compara este Autor.
     * @return true si los objetos son iguales; false de lo contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Autor other = (Autor) obj;
        if (nombre == null) {
            if (other.nombre != null)
                return false;
        } else if (!nombre.equals(other.nombre))
            return false;
        if (fechaNacimiento == null) {
            if (other.fechaNacimiento != null)
                return false;
        } else if (!fechaNacimiento.equals(other.fechaNacimiento))
            return false;
        if (fechaFallecimiento == null) {
            if (other.fechaFallecimiento != null)
                return false;
        } else if (!fechaFallecimiento.equals(other.fechaFallecimiento))
            return false;
        if (lugarNacimiento == null) {
            if (other.lugarNacimiento != null)
                return false;
        } else if (!lugarNacimiento.equals(other.lugarNacimiento))
            return false;
        if (lugarFallecimiento == null) {
            if (other.lugarFallecimiento != null)
                return false;
        } else if (!lugarFallecimiento.equals(other.lugarFallecimiento))
            return false;
        if (obras == null) {
            if (other.obras != null)
                return false;
        } else if (!obras.equals(other.obras))
            return false;
        return true;
    }

    /**
     * Obtiene las obras asociadas a este autor.
     * 
     * @return Un conjunto de obras.
     */
    public Set<Obra> getObras() {
        return obras;
    }

    /**
     * Establece las obras asociadas a este autor.
     * 
     * @param obras El conjunto de obras a establecer.
     */
    public void setObras(Set<Obra> obras) {
        this.obras = obras;
    }

    /**
     * Añade una obra al conjunto de obras de este autor.
     * 
     * @param obra La obra a añadir.
     */
    public void addObra(Obra obra) {
        this.obras.add(obra);
    }

    /**
     * Elimina una obra del conjunto de obras de este autor.
     * 
     * @param obra La obra a eliminar.
     */
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
     * Obtiene el nombre del autor.
     * 
     * @return El nombre del autor.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del autor.
     * 
     * @param nombre El nuevo nombre del autor.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la fecha de nacimiento del autor.
     * 
     * @return La fecha de nacimiento.
     */
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * Establece la fecha de nacimiento del autor.
     * 
     * @param fechaNacimiento La nueva fecha de nacimiento.
     */
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * Obtiene la fecha de fallecimiento del autor.
     * 
     * @return La fecha de fallecimiento.
     */
    public LocalDate getFechaFallecimiento() {
        return fechaFallecimiento;
    }

    /**
     * Establece la fecha de fallecimiento del autor.
     * 
     * @param fechaFallecimiento La nueva fecha de fallecimiento.
     */
    public void setFechaFallecimiento(LocalDate fechaFallecimiento) {
        this.fechaFallecimiento = fechaFallecimiento;
    }

    /**
     * Obtiene el lugar de nacimiento del autor.
     * 
     * @return El lugar de nacimiento.
     */
    public String getLugarNacimiento() {
        return lugarNacimiento;
    }

    /**
     * Establece el lugar de nacimiento del autor.
     * 
     * @param lugarNacimiento El nuevo lugar de nacimiento.
     */
    public void setLugarNacimiento(String lugarNacimiento) {
        this.lugarNacimiento = lugarNacimiento;
    }

    /**
     * Obtiene el lugar de fallecimiento del autor.
     * 
     * @return El lugar de fallecimiento.
     */
    public String getLugarFallecimiento() {
        return lugarFallecimiento;
    }

    /**
     * Establece el lugar de fallecimiento del autor.
     * 
     * @param lugarFallecimiento El nuevo lugar de fallecimiento.
     */
    public void setLugarFallecimiento(String lugarFallecimiento) {
        this.lugarFallecimiento = lugarFallecimiento;
    }

    /**
     * Crea una cadena que representa un autor y su información.
     * 
     * @return La cadena de información del autor
     */
    public String toString() {
        return "Autor [nombre=" + nombre + ", fechaNacimiento=" + fechaNacimiento + ", fechaFallecimiento="
                + fechaFallecimiento + ", lugarNacimiento=" + lugarNacimiento + ", lugarFallecimiento="
                + lugarFallecimiento + ", obras=" + obras + "]";
    }
}
