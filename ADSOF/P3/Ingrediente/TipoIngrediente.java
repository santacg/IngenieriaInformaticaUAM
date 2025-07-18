package Ingrediente;

/**
 * Enumeración que representa los diferentes tipos de ingredientes.
 * 
 * @author Carlos García Santa y Joaquín Abad Díaz
 */
public enum TipoIngrediente {
    CARNE("Carne"),
    PESCADO("Pescado"),
    FRUTA_VERDURA("Frutas y verduras"),
    LEGUMBRE("Legumbre"),
    CEREAL("Cereal"),
    HUEVO("Huevo"),
    LACTEO("Lacteo"),
    OTROS("Otros");

    private final String tipoIngrediente;

    /**
     * Constructor de la enumeración TipoIngrediente.
     * 
     * @param tipoIngrediente el tipo de ingrediente.
     */
    private TipoIngrediente(String tipoIngrediente) {
        this.tipoIngrediente = tipoIngrediente;
    }

    /**
     * Obtiene el tipo de ingrediente.
     * 
     * @return el tipo de ingrediente.
     */
    public String getTipoIngrediente() {
        return tipoIngrediente;
    }
}
