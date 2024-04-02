package Obra;

import java.util.HashSet;
import java.util.Set;

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

    public void retirarObra() {
        this.estado = Estado.RETIRADA; 
    }

    public void prestarObra() {
        this.estado = Estado.PRESTADA; 
    }

    public void almecenarObra() {
        this.estado = Estado.ALMACENADA; 
    }

    public void exponerObra() {
        this.estado = Estado.EXPUESTA;
    }

    public void restaurarObra() {
        this.estado = Estado.RESTAURACION;
    }

    public void addAutor(Autor autor) {
        this.autores.add(autor);
    }

    public void removeAuotor(Autor autor) {
        this.autores.remove(autor);
    }

    public String toString() {
        return "Obra [ID=" + ID + ", nombre=" + nombre + ", anio=" + anio + ", descripcion=" + descripcion
                + ", externa=" + externa + ", cuantiaSeguro=" + cuantiaSeguro + ", numeroSeguro=" + numeroSeguro
                + ", autores=" + autores.toString() + ", estado=" + estado + "]";
    }
}
