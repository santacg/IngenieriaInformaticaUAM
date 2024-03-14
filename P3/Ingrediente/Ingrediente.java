package P3.Ingrediente;

import java.util.*;
import P3.Info.InformacionNutricional;
import P3.Ingrediente.*;

public class Ingrediente {
    private String name;
    private String tipo;
    private InformacionNutricional info;
    private String alergenos;

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
    
    public String getAlergeno() {
        return alergenos;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String tieneAlergenos(String alergeno) {
        this.alergenos;
    }

    public String toString() {
        return "[" + this.tipo + "] " + this.name + " INFORMACION NUTRICIONAL DEL PLATO -> " + this.info.toString() + this.tieneAlergenos(null);
    }
}