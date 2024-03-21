package Ingrediente;

import java.util.Arrays;
import java.util.HashSet;

import Info.InfoNutricionalPeso;
import Info.InformacionNutricional;

/**
 * La clase Ingrediente representa un ingrediente utilizado en una receta.
 */
public class Ingrediente {
    private String nombre;
    private String tipo;
    private InformacionNutricional info;
    private HashSet<Alergeno> alergenos = new HashSet<>();
    private double cantidad;

    /**
     * Obtiene la cantidad del ingrediente.
     * 
     * @return La cantidad del ingrediente.
     */
    public double getCantidad() {
        return cantidad;
    }

    /**
     * Establece la cantidad del ingrediente.
     * 
     * @param cantidad La cantidad del ingrediente.
     */
    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Crea una instancia de la clase Ingrediente.
     * 
     * @param nombre El nombre del ingrediente.
     * @param tipo   El tipo del ingrediente.
     * @param info   La información nutricional del ingrediente.
     */
    public Ingrediente(String nombre, TipoIngrediente tipo, InformacionNutricional info) {
        this.nombre = nombre;
        this.info = info;
        this.tipo = tipo.getTipoIngrediente();
    }

    /**
     * Crea una instancia de la clase Ingrediente.
     * 
     * @param nombre El nombre del ingrediente.
     * @param tipo   El tipo del ingrediente.
     * @param info   La información nutricional del ingrediente.
     */
    public Ingrediente(String nombre, String tipo, InformacionNutricional info) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.info = info;
    }

    /**
     * Obtiene el nombre del ingrediente.
     * 
     * @return El nombre del ingrediente.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el tipo del ingrediente.
     * 
     * @return El tipo del ingrediente.
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Obtiene la información nutricional del ingrediente.
     * 
     * @return La información nutricional del ingrediente.
     */
    public InformacionNutricional getInfo() {
        return info;
    }

    /**
     * Obtiene los alérgenos del ingrediente.
     * 
     * @return Los alérgenos del ingrediente.
     */
    public HashSet<Alergeno> getAlergeno() {
        return alergenos;
    }

    /**
     * Establece el nombre del ingrediente.
     * 
     * @param name El nombre del ingrediente.
     */
    public void setNombre(String name) {
        this.nombre = name;
    }

    /**
     * Establece el tipo del ingrediente.
     * 
     * @param tipo El tipo del ingrediente.
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Agrega alérgenos al ingrediente.
     * 
     * @param newAlergenos Los alérgenos a agregar.
     * @return El ingrediente actualizado.
     */
    public Ingrediente tieneAlergenos(Alergeno... newAlergenos) {
        this.alergenos.addAll(Arrays.asList(newAlergenos));
        return this;
    }

    /**
     * Obtiene la información nutricional del ingrediente.
     * 
     * @return La información nutricional del ingrediente.
     */
    public InformacionNutricional getInformacionNutricionalIngrediente() {
        return info;
    }

    /**
     * Obtiene la información nutricional del ingrediente en base a una cantidad
     * específica.
     * 
     * @param cantidad La cantidad del ingrediente.
     * @return La información nutricional del ingrediente en base a la cantidad
     *         especificada.
     */
    public InformacionNutricional getInformacionNutricionalPorCantidad(double cantidad) {
        cantidad = this.info instanceof InfoNutricionalPeso ? cantidad / 100 : cantidad;
        return new InformacionNutricional(this.info.getCalorias() * cantidad,
                this.info.getHidratos() * cantidad,
                this.info.getGrasasTotales() * cantidad,
                this.info.getGrasasSaturadas() * cantidad,
                this.info.getProteinas() * cantidad,
                this.info.getAzucar() * cantidad,
                this.info.getFibra() * cantidad,
                this.info.getSodio() * cantidad);
    }

    /**
     * Devuelve una representación en forma de cadena del ingrediente.
     * 
     * @return La representación en forma de cadena del ingrediente.
     */
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

    public String toFile() {
        String tipoIngrediente = this.info instanceof InfoNutricionalPeso ? "INGREDIENTE_PESO" : "INGREDIENTE_UNIDAD";
        String gluten = alergenos.contains(Alergeno.GLUTEN) ? "S" : "N";
        String lactosa = alergenos.contains(Alergeno.LACTOSA) ? "S" : "N";
        String frutosSecos = alergenos.contains(Alergeno.FRUTOS_SECOS) ? "S" : "N";
        String huevo = alergenos.contains(Alergeno.HUEVO) ? "S" : "N";

        return String.format("%s;%s;%s;%.1f;%.1f;%.1f;%.1f;%.1f;%.1f;%.1f;%.1f;%s;%s;%s;%s",
                tipoIngrediente, nombre, tipo, info.getCalorias(), info.getHidratos(), info.getGrasasTotales(),
                info.getGrasasSaturadas(), info.getProteinas(), info.getAzucar(), info.getFibra(), info.getSodio(),
                gluten, lactosa, frutosSecos, huevo);
    }
}