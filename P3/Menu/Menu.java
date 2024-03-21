package Menu;

import java.util.HashSet;

import Plato.Plato;
import Info.InformacionNutricional;
import Ingrediente.Alergeno;

/**
 * La clase Menu representa un menú que contiene una lista de platos y su
 * información nutricional.
 * 
 * @author Carlos García Santa y Joaquín Abad Díaz 
 */
public class Menu {
    private static int count = 0;
    private int numeroMenu;
    private HashSet<Plato> platos = new HashSet<>();
    private HashSet<Alergeno> alergenos = new HashSet<>();
    private InformacionNutricional informacionNutricional = new InformacionNutricional(0, 0, 0, 0, 0, 0, 0, 0);

    /**
     * Crea un nuevo objeto Menu con los platos especificados.
     * 
     * @param platos Los platos que formarán parte del menú.
     */
    public Menu(Plato... platos) {
        numeroMenu = ++count;
        for (Plato plato : platos) {
            this.platos.add(plato);
            this.alergenos.addAll(plato.getAlergeno());
        }
        informacionNutricional = calcularInfoNutricional();
    }

    /**
     * Obtiene la lista de platos del menú.
     * 
     * @return La lista de platos del menú.
     */
    public HashSet<Plato> getPlatos() {
        return platos;
    }

    /**
     * Obtiene los alérgenos presentes en el menú.
     * 
     * @return Los alérgenos presentes en el menú.
     */
    public HashSet<Alergeno> getAlergeno() {
        return alergenos;
    }

    /**
     * Calcula la información nutricional total del menú.
     * 
     * @return La información nutricional total del menú.
     */
    public InformacionNutricional calcularInfoNutricional() {
        InformacionNutricional info = new InformacionNutricional(0, 0, 0, 0, 0, 0, 0, 0);
        for (Plato plato : platos) {
            info.addInformacionNutricional(plato.getInformacionNutricional());
        }
        return info;
    }

    /**
     * Devuelve una representación en forma de cadena del menú.
     * 
     * @return La representación en forma de cadena del menú.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Menu ");
        sb.append(numeroMenu);
        sb.append(" [");
        for (Plato platos : platos) {
            sb.append(platos.getNombre());
            sb.append(", ");
        }
        if (sb.length() > 0) {
            sb.delete(sb.length() - 2, sb.length());
        }
        sb.append("]: INFORMACION NUTRICIONAL DEL MENU -> ");
        sb.append(informacionNutricional.toString());
        sb.append(" CONTIENE ");
        for (Alergeno alergeno : alergenos) {
            sb.append(alergeno.getTipoAlergeno());
            sb.append(", ");
        }
        if (sb.length() > 0) {
            sb.delete(sb.length() - 2, sb.length());
        }
        return sb.toString();
    }

    /**
     * Devuelve una representación en formato de cadena del menú.
     * 
     * @return La representación en formato de cadena del menú.
     */
    public String toFile() {
        StringBuilder sb = new StringBuilder();
        sb.append("MENU;");
        for (Plato plato : platos) {
            sb.append(plato.getNombre());
            sb.append(";");
        }

        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);;
        }
        
        return sb.toString();
    }
}