package GUI.modelo;

import GUI.modelo.expofy.Expofy;

/**
 * Clase Persistencia.
 * Se encarga de reanudar la aplicación Expofy mediante la persistencia
 * de datos y de mostrar por pantalla el estado.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public class Persistencia {
    public static void main(String[] args) {
        Expofy expofy = Expofy.getInstance();
        expofy.reanudarExpofy();

        System.out.println(expofy.toString());
    }
}
