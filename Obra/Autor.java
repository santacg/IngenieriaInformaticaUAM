package Obra;

import java.util.HashSet;
import java.util.Set;
import java.time.LocalDate;

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

    public Autor(String nombre, LocalDate fechaNacimiento, LocalDate fechaFallecimiento, String lugarNacimiento,
            String lugarFallecimiento) {
                this.nombre = nombre;
                this.fechaNacimiento =  fechaNacimiento;
                this.fechaFallecimiento = fechaFallecimiento;
                this.lugarNacimiento = lugarNacimiento;
                this.lugarFallecimiento = lugarFallecimiento;
    }

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

}
