package Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import Exposicion.SalaExposicion;
import Obra.*;

/**
 * Clase LectorCSVObras.
 * Es una clase que proporciona funcionalidades para leer obras de arte desde un
 * archivo CSV y cargarlas en una sala de exposición.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */

public class LectorCSVObras {
    /**
     * Lee obras de arte desde un archivo CSV y las añade a una sala de exposición
     * especificada.
     * El formato esperado del archivo CSV incluye campos para el tipo de obra, el
     * origen, el título, el nombre del autor, el año, la descripción, la cuantía
     * del seguro, el número de seguro, y detalles específicos dependiendo del tipo
     * de obra (por ejemplo, dimensiones, color, etc.).
     *
     * @param sala La sala de exposición a la que se añadirán las obras leídas.
     */
    public static void leerObras(SalaExposicion sala) {
        Set<Obra> obras = new HashSet<>();
        String line = ""; // Variable para almacenar cada línea leída del archivo.
        String csvSeparador = ";"; // Define el separador utilizado en el archivo CSV.

        try (BufferedReader br = new BufferedReader(new FileReader("obras.csv"))) {
            br.readLine(); // Salta la primera línea del archivo CSV, generalmente encabezados.
            while ((line = br.readLine()) != null) { // Itera sobre cada línea del archivo hasta el final.
                String[] fields = line.split(csvSeparador); // Divide la línea en campos usando el separador.

                // Asigna los valores de cada campo a variables locales.
                String tipo = fields[0];
                String origen = fields[1];
                String titulo = fields[2];
                String autorName = fields[3];
                Integer anio = Integer.parseInt(fields[4]);
                String descripcion = fields[5];
                Double cuantiaSeguro = Double.parseDouble(fields[6]);
                String numeroSeguro = fields[7];

                // Inicializa variables para dimensiones, que pueden no estar presentes.
                Double ancho = null;
                Double alto = null;
                // Verifica si las dimensiones están presentes y las asigna.
                if (fields.length >= 15 && !fields[13].isEmpty() && !fields[14].isEmpty()) {
                    ancho = Double.parseDouble(fields[13]);
                    alto = Double.parseDouble(fields[14]);
                }

                // Inicializa variables para temperatura y humedad, que pueden no estar
                // presentes.
                Integer temperaturaMin = null;
                Integer temperaturaMax = null;
                Integer humedadMin = null;
                Integer humedadMax = null;
                // Verifica si los rangos de temperatura y humedad están presentes y los asigna.
                if (fields.length >= 18 && !fields[16].isEmpty() && !fields[17].isEmpty()) {
                    String[] temperatura = fields[16].split("--");
                    temperaturaMin = Integer.parseInt(temperatura[0]);
                    temperaturaMax = Integer.parseInt(temperatura[1]);
                    String[] humedad = fields[17].split("--");
                    humedadMin = Integer.parseInt(humedad[0]);
                    humedadMax = Integer.parseInt(humedad[1]);
                }

                // Determina si la obra es de origen externo basándose en el campo
                // correspondiente.
                Boolean externa = origen.toLowerCase().equals("externa");

                // Crea un autor con el nombre leído, sin especificar fechas o lugares.
                Autor autor = new Autor(autorName, null, null, null, null, null);
                Obra obra = null; // Inicializa la referencia de Obra como null.
                // Crea una instancia de Obra según el tipo leído del archivo.
                switch (tipo.toLowerCase()) {
                    case "cuadro":
                        obra = new Cuadro(titulo, anio, descripcion, externa, cuantiaSeguro, numeroSeguro,
                                alto, ancho, temperaturaMax, temperaturaMin, humedadMax, humedadMin,
                                fields[8]);
                        break;
                    case "escultura":
                        obra = new Escultura(titulo, anio, descripcion, externa, cuantiaSeguro,
                                numeroSeguro, alto, ancho, Double.parseDouble(fields[15]), temperaturaMax,
                                temperaturaMin, humedadMax, humedadMin, fields[9]);
                        break;
                    case "fotografia":
                        Boolean color = fields[10].toLowerCase().equals("color");
                        obra = new Fotografia(titulo, anio, descripcion, externa, cuantiaSeguro,
                                numeroSeguro, alto, ancho, temperaturaMax, temperaturaMin,
                                humedadMax, humedadMin, color);
                        break;
                    case "audiovisual":
                        obra = new Audiovisual(titulo, anio, descripcion, externa, cuantiaSeguro,
                                numeroSeguro, fields[11], fields[12]);
                        break;
                    default:
                        break; // No hace nada si el tipo de obra no coincide con los conocidos.
                }
                if (obra != null) { // Si se creó una obra, añade el autor y la marca como expuesta.
                    obra.addAutor(autor);
                    obra.exponerObra();
                    obras.add(obra); // Añade la obra al conjunto de obras.
                }
            }
            sala.setObras(obras); // Asigna el conjunto de obras leídas a la sala de exposición.
        } catch (IOException e) { // Captura cualquier excepción de E/S que pueda ocurrir.
            e.printStackTrace(); // Imprime el rastreo de la pila de excepciones.
        }
    }
}