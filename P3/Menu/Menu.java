package Menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Plato.Plato;
import Info.InformacionNutricional;
import Ingrediente.Alergeno;

public class Menu {

    private List<Plato> platos = new ArrayList<>();
    private List<Alergeno> alergenos;
    private InformacionNutricional informacionNutricional;


    public Menu(Plato... platos) {
        for (Plato plato : platos) {
            this.platos.add(plato);
            this.alergenos.add(plato.getAlergeno());
        }
        this.calcularInfoNutricional();
    }

    public List<Plato> getPlatos() {
        return platos;
    }

    public List<Alergeno> getAlergeno() {
        return alergenos;
    }

    public void calcularInfoNutricional(){
        for (Plato plato: platos) {
            informacionNutricional.addInformacionNutricional(plato.getInfo());
        }
    }

    public String toString() {
        String platosStr, result;
        InformacionNutricional info = this.informacionNutricional;
        for (Plato plato: platos) {
            platosStr += plato;
            platosStr += ", ";
        }
        platosStr = platosStr.substring(0, platosStr.length() - 2); // Remover la última coma y espacio
        result = "[" + platosStr + "]: " + "INFORMACION NUTRICIONAL DEL MENU -> " + info.toString();
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