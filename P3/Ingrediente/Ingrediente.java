package Ingrediente;

import java.util.Arrays;
import java.util.List;

import Info.InfoNutricionalPeso;
import Info.InformacionNutricional;

public class Ingrediente {
    private String name;
    private String tipo;
    private InformacionNutricional info;
    private List<Alergeno> alergenos;

    public Ingrediente(String name, TipoIngrediente tipo, InformacionNutricional info) {
        this.name = name;
        this.info = info;
        this.tipo = tipo.getTipoIngrediente();
    }

    public Ingrediente(String name, String tipo, InformacionNutricional info) {
        this.name = name;
        this.tipo = tipo;
        this.info = info;
    }

    public String getName() {
        return name;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Ingrediente tieneAlergenos(Alergeno... newAlergenos) {
        this.alergenos = Arrays.asList(newAlergenos);
        return this;
    }

    public String toString() {
        String infoNutricional = this.info instanceof InfoNutricionalPeso ? "INFORMACION NUTRICIONAL POR 100 g"
                : "INFORMACION NUTRICIONAL POR UNIDAD";
        return "[" + this.tipo + "] " + this.name + " " + infoNutricional + " -> " + this.info.toString();
    }

}