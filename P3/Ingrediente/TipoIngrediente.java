package P3.Ingrediente;

public enum TipoIngrediente {
     CARNE("Carne"),
     PESCADO("Pescado"),
     VERDURA_FRUTA("Frutas y Verduras"),
     LEGUMBRE("Legumbre"),
     CEREAL("Cereal"),
     HUEVO("Huevo"),
     LACTEO("Lacteo"),
     OTROS("Otros");

     private final String tipoIngrediente;

     private TipoIngrediente(String tipoIngrediente) {
         this.tipoIngrediente = tipoIngrediente;
     }

     public String getTipoIngrediente() {
         return tipoIngrediente;
     }
}
