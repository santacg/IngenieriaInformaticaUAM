import java.util.*;

import sun.awt.image.ImageWatched.Link;

/**
 * Esta clase calcula la longitudes de palabras y las almacena en un mapa.
 */
public class LongitudPalabras {
  private Map<String, Integer> palabras = new LinkedHashMap<>();

  /**
   * Constructor con las palabras.
   * 
   * @param palabras Palabras de las que se quiere calcular las longitudes.
   */
  public LongitudPalabras(String[] palabras) {
    for (String palabra : palabras)
      this.palabras.put(palabra, palabra.length());
  }

  /**
   * @return conjunto de palabras incluidas en el objeto.
   */
  public Set<String> getPalabras() {
    return this.palabras.keySet();
  }

  /**
   * @return conjunto de longitudes de las palabras.
   */
  public Collection<Integer> getLongitudes() {
    return this.palabras.values();
  }

  /**
   * Devuelve la longitud de una palabra, o -1 si no existe
   * 
   * @param palabra
   * @return su longitud
   */
  public int longitud(String palabra) {
    if (!this.palabras.containsKey(palabra))
      return -1;
    return this.palabras.get(palabra);
  }
  
  /**
   * Devuelve la longitud de las palabras sin contar mas de una vez una misma palabra
   *
   * @param none
   * @return HashSet with unique word lengths 
   */
  public LinkedHashMap<Integer> getLongitudesUnicas() {
    Collections<Integer> longitudes = getLongitudes();
    LinkedHashMap<Integer> linkedHash = new LinkedHashMap<>(longitudes);
    
    return linkedHash;
  }

  public int getFrecuencia(Int longitud) {
    LinkedHashMap.forEach       
    
  }
  /**
   * @return cadena de texto que representa este objeto.
   */
  @Override
  public String toString() {
    String str = "Las longitudes de las palabras son: \n";
    for (String palabra : this.palabras.keySet())
      str += "- " + palabra + " (" + this.palabras.get(palabra) + " caracteres).\n";
    return str;
  }

  /**
   * Punto de entrada a la aplicación.
   * Este método imprime las longitudes de palabras proporcionadas por la línea de
   * comandos
   * 
   * @param args Los argumentos de la línea de comando. Se esperan palabras, como
   *             cadenas
   */
  public static void main(String[] args) {
    if (args.length == 0)
      System.err.println("Se espera al menos una palabra como parametro.");
    else {
      LongitudPalabras palabras = new LongitudPalabras(args);
      System.out.println(palabras);
      System.out.println("Palabras almacenadas: " + palabras.getPalabras());
      System.out.println("Longitud de 'No_almacenada': " + palabras.longitud("No_almacenada"));
    }
  }
}

