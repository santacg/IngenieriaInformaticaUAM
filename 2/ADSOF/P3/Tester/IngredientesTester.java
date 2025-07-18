package Tester;
import Ingrediente.Alergeno;
import Ingrediente.Ingrediente;
import Ingrediente.TipoIngrediente;
import Info.InfoNutricionalPeso;
import Info.InfoNutricionalUnidad;
import java.util.LinkedHashMap;
import java.util.Map;

public class IngredientesTester {
    public static void main(String args[]) {
        IngredientesTester tester = new IngredientesTester();
        for (Ingrediente ingrediente : tester.crearIngredientes().values())
            System.out.println("* " + ingrediente);
    }

    public Map<String, Ingrediente> crearIngredientes() {
        Map<String, Ingrediente> ingredientes = new LinkedHashMap<>();
        ingredientes.put("Pasta", new Ingrediente("Pasta", TipoIngrediente.CEREAL,
                new InfoNutricionalPeso(372, 74, 1.8, 0.277, 12, 2.6, 2.9, 6))
                .tieneAlergenos(Alergeno.GLUTEN, Alergeno.HUEVO));
        ingredientes.put("Tomate", new Ingrediente("Tomate", TipoIngrediente.FRUTA_VERDURA,
                new InfoNutricionalUnidad(14, 2.2, 0.2, 0, 0.7, 2.04, 1, 4)));
        ingredientes.put("Aceite", new Ingrediente("Aceite", "Grasa" /* otro tipo */,
                new InfoNutricionalPeso(885, 0, 100, 12.81, 0, 0, 0, 2)));
        ingredientes.put("Huevo", new Ingrediente("Huevo", TipoIngrediente.HUEVO,
                new InfoNutricionalUnidad(84.6, 0.35, 6.3, 0.2, 6.6, 0.6, 0, 0.1))
                .tieneAlergenos(Alergeno.HUEVO));
        ingredientes.put("Chorizo", new Ingrediente("Chorizo", TipoIngrediente.CARNE,
                new InfoNutricionalPeso(203, 5, 14.3, 4.6, 13.6, 0, 2, 800))
                .tieneAlergenos(Alergeno.LACTOSA));
        ingredientes.put("Patata", new Ingrediente("Patata", TipoIngrediente.FRUTA_VERDURA,
                new InfoNutricionalPeso(85, 17.6, 0.1, 0, 2, 0, 2.6, 2)));
        ingredientes.put("Caldo", new Ingrediente("Caldo", "Caldo" /* otro tipo */,
                new InfoNutricionalPeso(267, 18, 14, 3.4, 17, 17, 0, 23.875)));
        return ingredientes;
    }
}