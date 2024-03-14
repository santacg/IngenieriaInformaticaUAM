package P3.Ingrediente;

public enum Alergeno {
    GLUTEN("Gluten"),
    LACTOSA("Lactosa"),
    HUEVOS("Huevos"),
    FRUTOS_SECOS("Furutos secos");

    private final String tipoAlergeno;

    private Alergeno(String tipoAlergeno) {
         this.tipoAlergeno = tipoAlergeno;
     }

    public String getTipoAlergeno() {
        return tipoAlergeno;
    }
}
