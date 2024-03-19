package Obra;

import java.util.Date;

public class Autor {
    private String nombre;
    private Date fechaNacimiento;
    private Date fechaFallecimiento;
    private String lugarNacimiento;
    private String lugarFallecimiento;

    public Autor(String nombre, Date fechaNacimiento, Date fechaFallecimiento, String lugarNacimiento,
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

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Date getFechaFallecimiento() {
        return fechaFallecimiento;
    }

    public void setFechaFallecimiento(Date fechaFallecimiento) {
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
