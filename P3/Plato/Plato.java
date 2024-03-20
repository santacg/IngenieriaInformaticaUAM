package Plato;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import Info.InformacionNutricional;
import Ingrediente.Alergeno;
import Ingrediente.Ingrediente;

/**
 * The `Plato` class represents a dish or a plate in a menu.
 * It contains information about the name, ingredients, nutritional information,
 * and allergens of the dish.
 */
public class Plato {
    private String nombre;
    private HashSet<Ingrediente> ingredientes;
    private HashSet<Plato> plato;
    private InformacionNutricional informacionNutricional;
    private List<Alergeno> alergenos;

    public Plato(String nombre) {
        this.nombre = nombre;
        this.ingredientes = new HashSet<>();
        this.plato = new HashSet<>();
        this.alergenos = new ArrayList<>();
        this.informacionNutricional = new InformacionNutricional(0, 0, 0, 0, 0, 0, 0, 0);
    }

    public boolean addIngrediente(Ingrediente ingrediente, double cantidad) {
        if (ingredientes.contains(ingrediente)) {
            return true;
        }
        ingredientes.add(ingrediente);
        ingrediente.setCantidad(cantidad);
        alergenos.addAll(ingrediente.getAlergeno());
        informacionNutricional.addInformacionNutricional(ingrediente.getInformacionNutricionalPorCantidad(cantidad));
        return false;
    }

    public void addPlato(Plato plato) {
        this.ingredientes.addAll(plato.getIngredientes());
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public HashSet<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public HashSet<Plato> getPlato() {
        return plato;
    }

    public List<Alergeno> getAlergeno() {
        return alergenos;
    }

    public InformacionNutricional calcularInfoNutricional() {
        InformacionNutricional info = new InformacionNutricional(0, 0, 0, 0, 0, 0, 0, 0);
        InformacionNutricional infoIngrediente;
        for (Ingrediente ingrediente : ingredientes) {
            infoIngrediente = ingrediente.getInfo();
            info.addCalorias(infoIngrediente.getCalorias());
            info.addHidratos(infoIngrediente.getHidratos());
            info.addGrasasTotales(infoIngrediente.getGrasasTotales());
            info.addGrasasSaturadas(infoIngrediente.getGrasasSaturadas());
            info.addProteinas(infoIngrediente.getProteinas());
            info.addAzucar(infoIngrediente.getAzucar());
            info.addFibra(infoIngrediente.getFibra());
            info.addSodio(infoIngrediente.getSodio());
        }
        return info;
    }

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

}