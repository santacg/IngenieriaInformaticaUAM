package GUI.modelo;

import GUI.modelo.expofy.Expofy;

public class Persistencia {
    public static void main(String[] args) {
        Expofy expofy = Expofy.getInstance();
        expofy.reanudarExpofy();

        System.out.println(expofy.toString());
    }
}
