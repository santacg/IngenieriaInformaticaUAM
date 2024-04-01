package Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import Obra.*;
import Sala.SalaExposicion;

public class LectorCSVObras {
    public static void leerObras(SalaExposicion sala) {
        Set<Obra> obras = new HashSet<>();
        String line = "";
        String csvSeparador = ";";

        try (BufferedReader br = new BufferedReader(new FileReader("obras.csv"))) {
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(csvSeparador);

                String tipo = fields[0];
                String origen = fields[1];
                String titulo = fields[2];
                String autorName = fields[3];
                Integer anio = Integer.parseInt(fields[4]);
                String descripcion = fields[5];
                Double cuantiaSeguro = Double.parseDouble(fields[6]);
                String numeroSeguro = fields[7];

                Boolean externa;
                if (origen.toLowerCase().equals("externa")) {
                    externa = true;
                } else {
                    externa = false;
                }

                Autor autor = new Autor(autorName, null, null, null, null);
                Obra obra = null;
                switch (tipo.toLowerCase()) {
                    case "cuadro":
                        obra = new Cuadro(titulo, anio, descripcion, externa, cuantiaSeguro, numeroSeguro,
                                Estado.ALMACENADA, alto, ancho, temperaturaMax, temperaturaMin, humedadMax, humedadMin,
                                fields[8]);
                    case "escultura":
                        obra = new Escultura(titulo, anio, descripcion, externa, cuantiaSeguro,
                                numeroSeguro, Estado.ALMACENADA, alto, ancho, profundidad, temperaturaMax,
                                temperaturaMin,
                                humedadMax, humedadMin, fields[9]);
                        break;
                    case "fotografia":
                        Boolean color;
                        if (fields[10].toLowerCase().equals("color")) {
                            color = true;
                        } else {
                            color = false;
                        }
                        obra = new Fotografia(titulo, anio, descripcion, externa, cuantiaSeguro,
                                numeroSeguro, Estado.ALMACENADA, alto, ancho, temperaturaMax, temperaturaMin,
                                humedadMax, humedadMin, color);
                        break;
                    case "audiovisual":
                        obra = new Audiovisual(titulo, anio, descripcion, externa, cuantiaSeguro,
                                numeroSeguro, Estado.ALMACENADA, Integer.parseInt(fields[11]), fields[12]);
                        break;
                    default:
                        break;
                }
                obra.addAutor(autor);
                obras.add(obra);
            }
            sala.setObras(obras);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}