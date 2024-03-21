package Ficheros;

import java.io.*;
import java.util.HashSet;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Map;

import Ingrediente.Ingrediente;
import Info.InformacionNutricional;
import Plato.Plato;
import Menu.Menu;

public class ManejadorFicheros {
    private List<Menu> menus;

    public static void guardarFichero(String nombre, List<Menu> menus) {
        try (PrintWriter writer = new PrintWriter(nombre)) {
            for (Menu menu : menus) {
                Set<Plato> platos = menu.getPlatos();
                for (Plato plato : platos) {
                    Set<Ingrediente> ingredientes = plato.getIngredientes();
                    for (Ingrediente ingrediente : ingredientes) {
                        writer.println(ingrediente.toFile());
                    }
                    writer.println(plato.toFile());
                }
                writer.println(menu.toFile());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void leerFichero(String nombreArchivo) {
        Map<String, Ingrediente> ingredientes = new HashMap<>();
        Map<String, Plato> platos = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (linea.startsWith("INGREDIENTE")) {
                    Ingrediente ingrediente = procesarIngrediente(linea);
                    ingredientes.put(ingrediente.getNombre(), ingrediente);
                } else if (linea.startsWith("PLATO")) {
                    Plato plato = procesarPlato(linea, ingredientes, platos);
                    platos.put(plato.getNombre(), plato);
                } else if (linea.startsWith("MENU")) {
                    Menu menu = procesarMenu(linea, platos);
                    menus.add(menu);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Ingrediente procesarIngrediente(String linea) {
    String[] partes = linea.split(";");
    String nombre = partes[1];
    // Asume un constructor de Ingrediente que tome estos parámetros directamente.
    // Convierte los valores numéricos y los flags de alérgenos adecuadamente.
    return new Ingrediente(
        nombre,
        partes[2], // tipo
        new InformacionNutricional(
            Double.parseDouble(partes[3]), // calorias
            Double.parseDouble(partes[4]), // hidratos
            Double.parseDouble(partes[5]), // grasas totales
            Double.parseDouble(partes[6]), // grasas saturadas
            Double.parseDouble(partes[7]), // proteinas
            Double.parseDouble(partes[8]), // azucares
            Double.parseDouble(partes[9]), // fibra
            Double.parseDouble;
    }

    private static Plato procesarPlato(String linea, Map<String, Ingrediente> ingredientes,
            Map<String, Plato> platosExistentes) {
        // Implementa la lógica para crear un Plato, usando los ingredientes y platos
        // existentes.
        return null; // Ejemplo, necesitas la implementación real aquí.
    }

    private static Menu procesarMenu(String linea, Map<String, Plato> platos) {
        // Implementa la lógica para crear un Menu a partir de una línea del fichero.
        return null; // Ejemplo, necesitas la implementación real aquí.
    }

    public static List<Menu> getMenus() {
        return null;
    }
}