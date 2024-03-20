package Ingrediente;

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

     private TipoIngrediente(String tipoIngrediente) {
         this.tipoIngrediente = tipoIngrediente;
     }

     public String getTipoIngrediente() {
         return tipoIngrediente;
     }
}
