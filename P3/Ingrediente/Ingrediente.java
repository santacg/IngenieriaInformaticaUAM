package Ingrediente;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Info.InfoNutricionalPeso;
import Info.InformacionNutricional;

public class Ingrediente {
    private String nombre;
    private String tipo;
    private InformacionNutricional info;
    private List<Alergeno> alergenos = new ArrayList<>();
    private double cantidad;

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public Ingrediente(String nombre, TipoIngrediente tipo, InformacionNutricional info) {
        this.nombre = nombre;
        this.info = info;
        this.tipo = tipo.getTipoIngrediente();
    }

    public Ingrediente(String nombre, String tipo, InformacionNutricional info) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.info = info;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public InformacionNutricional getInfo() {
        return info;
    }

    public List<Alergeno> getAlergeno() {
        return alergenos;
    }

    public void setNombre(String name) {
        this.nombre = name;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Ingrediente tieneAlergenos(Alergeno... newAlergenos) {
        this.alergenos.addAll(Arrays.asList(newAlergenos));
        return this;
    }

    public InformacionNutricional getInformacionNutricionalIngrediente() {
        return info;
    }

    public InformacionNutricional getInformacionNutricionalPorCantidad(double cantidad) {
        cantidad =  this.info instanceof InfoNutricionalPeso ? cantidad / 100 : cantidad;
        return new InformacionNutricional(this.info.getCalorias() * cantidad,
            this.info.getHidratos() * cantidad,
            this.info.getGrasasTotales() * cantidad, 
            this.info.getGrasasSaturadas() * cantidad,
            this.info.getProteinas() * cantidad,
            this.info.getAzucar() * cantidad,
            this.info.getFibra() * cantidad,
            this.info.getSodio() * cantidad);
    }

    public String toString() {
        String infoNutricional = this.info instanceof InfoNutricionalPeso ? "INFORMACION NUTRICIONAL POR 100 g"
                : "INFORMACION NUTRICIONAL POR UNIDAD";
        String result = "[" + this.tipo + "] " + this.nombre + ": " + infoNutricional + " -> " + this.info.toString();
        if (!alergenos.isEmpty()) {
            result += " CONTIENE ";
            for (Alergeno alergeno : alergenos) {
                result += alergeno.getTipoAlergeno().toLowerCase() + ", ";
            }
            result = result.substring(0, result.length() - 2); // Remover la última coma y espacio
        }
        return result;
    }

}