package Plato;

import java.util.HashSet;
import Info.InformacionNutricional;
import Ingrediente.Alergeno;
import Ingrediente.Ingrediente;

/**
 * Representa un Plato en un menú.
 * Un objeto Plato contiene información sobre su nombre, ingredientes, información nutricional y alérgenos.
 * 
 * @author Carlos García Santa y Joaquín Abad Díaz
 */
public class Plato {
    private String nombre;
    private HashSet<Ingrediente> ingredientes;
    private InformacionNutricional informacionNutricional;
    private HashSet<Alergeno> alergenos;

    /**
     * Crea un objeto Plato con el nombre especificado.
     * Inicializa las colecciones de ingredientes, platos y alérgenos.
     * Inicializa la información nutricional con valores predeterminados.
     * @param nombre el nombre del plato
     */
    public Plato(String nombre) {
        this.nombre = nombre;
        this.ingredientes = new HashSet<>();
        this.alergenos = new HashSet<>();
        this.informacionNutricional = new InformacionNutricional(0, 0, 0, 0, 0, 0, 0, 0);
    }

    /**
     * Agrega un ingrediente al plato con la cantidad especificada.
     * Si el ingrediente ya existe en el plato, no se agrega nuevamente.
     * Se actualiza la cantidad del ingrediente y se agregan los alérgenos correspondientes.
     * Se agrega la información nutricional del ingrediente al plato.
     * @param ingrediente el ingrediente a agregar
     * @param cantidad la cantidad del ingrediente
     * @return true si el ingrediente ya existe en el plato, false de lo contrario
     */
    public boolean addIngrediente(Ingrediente ingrediente, Integer cantidad) {
        if (ingredientes.contains(ingrediente)) {
            return true;
        }
        ingredientes.add(ingrediente);
        ingrediente.setCantidad(cantidad);
        alergenos.addAll(ingrediente.getAlergeno());
        informacionNutricional.addInformacionNutricional(ingrediente.getInformacionNutricionalPorCantidad(cantidad));
        return false;
    }

    /**
     * Agrega un plato al plato actual.
     * Se agregan los ingredientes y la información nutricional del plato agregado al plato actual.
     * @param plato el plato a agregar
     */
    public void addPlato(Plato plato) {
        this.ingredientes.addAll(plato.getIngredientes());
        this.informacionNutricional.addInformacionNutricional(plato.informacionNutricional);
    }

    /**
     * Obtiene el nombre del plato.
     * @return el nombre del plato
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del plato.
     * @param nombre el nuevo nombre del plato
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene los ingredientes del plato.
     * @return los ingredientes del plato
     */
    public HashSet<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    /**
     * Obtiene la información nutricional del plato.
     * @return la información nutricional del plato
     */
    public InformacionNutricional getInformacionNutricional() {
        return informacionNutricional;
    }

    /**
     * Obtiene los alérgenos del plato.
     * @return los alérgenos del plato
     */
    public HashSet<Alergeno> getAlergeno() {
        return alergenos;
    }

    /**
     * Devuelve una representación en forma de cadena del plato.
     * @return una representación en forma de cadena del plato
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append("Plato");
        sb.append("] ");
        sb.append(nombre);
        sb.append(": INFORMACION NUTRICIONAL POR PLATO -> ");
        sb.append(informacionNutricional.toString());
        sb.append(" CONTIENE ");
        for (Ingrediente ingrediente : ingredientes) {
            for (Alergeno alergeno : ingrediente.getAlergeno()) {
                sb.append(alergeno.getTipoAlergeno());
                sb.append(", ");
            }
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 2);
        }
        return sb.toString();
    }

    /**
     * Devuelve una representación en forma de cadena del plato para ser guardada en un archivo.
     * @return una representación en forma de cadena del plato para ser guardada en un archivo
     */
    public String toFile() {
        StringBuilder sb = new StringBuilder();
        sb.append("PLATO;");
        sb.append(nombre);
        sb.append(";");
        for (Ingrediente ingrediente : ingredientes) {
            sb.append("INGREDIENTE ");
            sb.append(ingrediente.getNombre());
            sb.append(":");
            sb.append(ingrediente.getCantidad());
            sb.append(";");
        }

        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }
}