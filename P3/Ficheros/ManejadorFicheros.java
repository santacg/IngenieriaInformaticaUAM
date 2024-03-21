package Ficheros;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Map;

import Ingrediente.Ingrediente;
import Ingrediente.Alergeno;
import Info.InfoNutricionalPeso;
import Info.InfoNutricionalUnidad;
import Info.InformacionNutricional;
import Plato.Plato;
import Menu.Menu;

/**
 * La clase ManejadorFicheros proporciona métodos para guardar y leer información de un archivo.
 * 
 * @author Carlos García Santa y Joaquín Abad Díaz 
 */
public class ManejadorFicheros {
    private static ArrayList<Menu> menus = new ArrayList<>();

    /**
     * Guarda la información de los menús en un archivo.
     * 
     * @param nombre el nombre del archivo
     * @param menus la lista de menús a guardar
     */
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

    /**
     * Lee la información de un archivo y la carga en las estructuras de datos correspondientes.
     * 
     * @param nombreArchivo el nombre del archivo a leer
     */
    public static void leerFichero(String nombreArchivo) {
        Map<String, Ingrediente> ingredientes = new HashMap<>();
        Map<String, Plato> platos = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (linea.startsWith("INGREDIENTE")) {
                    Ingrediente ingrediente = procesarIngrediente(linea, ingredientes);
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


    /**
     * Procesa un ingrediente.
     *
     * @param nombre el nombre del ingrediente
     * @param categoria la categoría del ingrediente
     * @param infoNutricional la información nutricional del ingrediente
     *
     * @return una instancia de la clase Ingrediente
     */
    private static Ingrediente procesarIngrediente(String linea, Map<String, Ingrediente> ingredientes) {
        String[] partes = linea.split(";");
        String tipo = partes[0];
        String nombre = partes[1];
        if (ingredientes.containsKey(nombre)) {
            return ingredientes.get(nombre); 
        }
        String categoria = partes[2];
        double calorias = Double.parseDouble(partes[3]);
        double hidratos = Double.parseDouble(partes[4]);
        double grasasTotales = Double.parseDouble(partes[5]);
        double grasasSaturadas = Double.parseDouble(partes[6]);
        double proteinas = Double.parseDouble(partes[7]);
        double azucares = Double.parseDouble(partes[8]);
        double fibra = Double.parseDouble(partes[9]);
        double sodio = Double.parseDouble(partes[10]);
        boolean gluten = partes[11].equals("S");
        boolean lactosa = partes[12].equals("S");
        boolean frutosSecos = partes[13].equals("S");
        boolean huevo = partes[14].equals("S");

        InformacionNutricional infoNutricional;
        if ("INGREDIENTE_PESO".equals(tipo)) {
            infoNutricional = new InfoNutricionalPeso(calorias, hidratos, grasasTotales, grasasSaturadas, proteinas,
                    azucares, fibra, sodio);
        } else {
            infoNutricional = new InfoNutricionalUnidad(calorias, hidratos, grasasTotales, grasasSaturadas, proteinas,
                    azucares, fibra, sodio);
        }

        Ingrediente ingrediente = new Ingrediente(nombre, categoria, infoNutricional);
        if (gluten)
            ingrediente.tieneAlergenos(Alergeno.GLUTEN);
        if (lactosa)
            ingrediente.tieneAlergenos(Alergeno.LACTOSA);
        if (frutosSecos)
            ingrediente.tieneAlergenos(Alergeno.FRUTOS_SECOS);
        if (huevo)
            ingrediente.tieneAlergenos(Alergeno.HUEVO);

        return ingrediente;
    }

    /**
     * Procesa un plato.
     *
     * @param linea la línea de texto que contiene la información del plato
     * @param ingredientes un mapa que contiene los ingredientes disponibles
     * @param platosExistentes un mapa que contiene los platos existentes
     * @return el objeto Plato creado a partir de la línea de texto
     */
    private static Plato procesarPlato(String linea, Map<String, Ingrediente> ingredientes,
            Map<String, Plato> platosExistentes) {
        String[] partes = linea.split(";");
        String nombrePlato = partes[1];
        Plato plato = new Plato(nombrePlato);

        for (int i = 2; i < partes.length; i++) {
            String[] detalle = partes[i].split(" ");
            String tipo = detalle[0];
            String nombre = detalle[1].split(":")[0];
            Integer cantidad = Integer.parseInt(detalle[1].split(":")[1]);

            if ("INGREDIENTE".equals(tipo)) {
                Ingrediente ingrediente = ingredientes.get(nombre);
                plato.addIngrediente(ingrediente, cantidad);
            } else { 
                Plato subPlato = platosExistentes.get(nombre);
                plato.addPlato(subPlato);
            }
        }

        return plato;
    }

    /**
        * Crea un objeto de tipo Menu a partir de una lista de platos.
        * 
        * @param platosDelMenu La lista de platos que formarán parte del menú.
        * @return El objeto de tipo Menu creado.
        */
    private static Menu procesarMenu(String linea, Map<String, Plato> platos) {
        String[] partes = linea.split(";");
        List<Plato> platosDelMenu = new ArrayList<>();

        for (int i = 1; i < partes.length; i++) {
            String nombrePlato = partes[i];
            platosDelMenu.add(platos.get(nombrePlato));
        }

        Menu menu = new Menu(platosDelMenu.toArray(new Plato[0]));
        return menu;
    }

    /**
     * Obtiene la lista de menús cargados desde el archivo.
     * 
     * @return la lista de menús
     */
    public static ArrayList<Menu> getMenus() {
        return menus;
    }
}