package Obra;

import java.util.Set;

public abstract class Obra {
    private String ID;
    private String nombre;
    private Integer anio;
    private String descripcion;
    private Boolean externa;
    private Double cuantiaSeguro;
    private String numeroSeguro;
    private Set<Autor> autores;
    private Estado estado;

    public Obra(String ID, String nombre, Integer anio, String descripcion, Boolean externa, Double cuantiaSeguro,
            String numeroSeguro, Estado estado) {
        this.ID = ID;
        this.nombre = nombre;
        this.anio = anio;
        this.descripcion = descripcion;
        this.externa = externa;
        this.cuantiaSeguro = cuantiaSeguro;
        this.numeroSeguro = numeroSeguro;
        this.estado = estado;
    }
    
    public String getID() {
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

    public Set<Autor> getAutor() {
        return autores;
    }

    public void setID(String ID) {
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

    public void setAutor(Set<Autor> autor) {
        this.autores = autor;
    } 

    public Estado getEstado() {
        return estado;
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

    public void removeAuotr(Autor autor) {
        this.autores.remove(autor);
    }
}
