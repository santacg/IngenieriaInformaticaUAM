public class FrecuenciaPalabras {
  public static void main(String[] args) {
    if (args.length == 0)
      System.err.println("Se espera al menos una palabra como parametro.");
    else {
      LongitudPalabras palabras = new LongitudPalabras(args);
      imprimeFrecuencias(palabras);
    }
  }

  private static void imprimeFrecuencias(LongitudPalabras palabras) {
    System.out.println(palabras);
    for (int longitud : palabras.getLongitudesUnicas())
      System.out.println("Hay " + palabras.getFrecuencia(longitud) + " palabras de " + longitud + " letras.");
  }
}
