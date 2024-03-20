package Ingrediente;

public enum Alergeno {
    GLUTEN("Gluten"),
    LACTOSA("Lactosa"),
    HUEVO("Huevo"),
    FRUTOS_SECOS("Frutos secos");

    private final String tipoAlergeno;

    private Alergeno(String tipoAlergeno) {
        this.tipoAlergeno = tipoAlergeno;
     }

    public String getTipoAlergeno() {
        return tipoAlergeno;
    }

}
