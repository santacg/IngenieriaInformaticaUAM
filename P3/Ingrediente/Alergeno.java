package Ingrediente;

/**
 * Enumeración que representa los posibles alérgenos de un ingrediente.
 */
public enum Alergeno {
    GLUTEN("Gluten"),
    LACTOSA("Lactosa"),
    HUEVO("Huevo"),
    FRUTOS_SECOS("Frutos secos");

    private final String tipoAlergeno;

    /**
     * Constructor de la enumeración Alergeno.
     * 
     * @param tipoAlergeno el tipo de alérgeno.
     */
    private Alergeno(String tipoAlergeno) {
        this.tipoAlergeno = tipoAlergeno;
    }

    /**
     * Obtiene el tipo de alérgeno.
     * 
     * @return el tipo de alérgeno.
     */
    public String getTipoAlergeno() {
        return tipoAlergeno;
    }

}
