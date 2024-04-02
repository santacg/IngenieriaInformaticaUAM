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

                Double ancho = null;
                Double alto = null;
                if (fields.length >= 15 && !fields[13].isEmpty() && !fields[14].isEmpty()) {
                    ancho = Double.parseDouble(fields[13]);
                    alto = Double.parseDouble(fields[14]);
                }

                Integer temperaturaMin = null;
                Integer temperaturaMax = null;
                Integer humedadMin = null;
                Integer humedadMax = null;
                if (fields.length >= 18 && !fields[16].isEmpty() && !fields[17].isEmpty()) {
                    String[] temperatura = fields[16].split("--");
                    temperaturaMin = Integer.parseInt(temperatura[0]);
                    temperaturaMax = Integer.parseInt(temperatura[1]);
                    String[] humedad = fields[17].split("--");
                    humedadMin = Integer.parseInt(humedad[0]);
                    humedadMax = Integer.parseInt(humedad[1]);
                }

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
                                alto, ancho, temperaturaMax, temperaturaMin, humedadMax, humedadMin,
                                fields[8]);
                        break;
                    case "escultura":
                        obra = new Escultura(titulo, anio, descripcion, externa, cuantiaSeguro,
                                numeroSeguro, alto, ancho, Double.parseDouble(fields[15]), temperaturaMax,
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
                                numeroSeguro, alto, ancho, temperaturaMax, temperaturaMin,
                                humedadMax, humedadMin, color);
                        break;
                    case "audiovisual":
                        obra = new Audiovisual(titulo, anio, descripcion, externa, cuantiaSeguro,
                                numeroSeguro, fields[11], fields[12]);
                        break;
                    default:
                        break;
                }
                obra.addAutor(autor);
                obra.exponerObra();
                obras.add(obra);
            }
            sala.setObras(obras);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}