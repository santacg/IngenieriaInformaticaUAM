package Obra;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import CentroExposicion.CentroExposicion;
import Exposicion.Exposicion;

public class LectorCSVObras {
    public static void main(String[] args, CentroExposicion centroExposicion) {
        Set<Obra> obras = new HashSet<>();
        String line = "";
        String csvSeparador = ";";

        try (BufferedReader br = new BufferedReader(new FileReader("obras.csv"))) {
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
                Double ancho = Double.parseDouble(fields[13]);
                Double alto = Double.parseDouble(fields[14]);
                Double profundidad = Double.parseDouble(fields[15]);
                String temperatura = fields[16];
                String humedad = fields[17];

                String[] rangoTemperatura = temperatura.split("-");
                String[] rangoHumedad = humedad.split("-");

                Integer temperaturaMin = Integer.parseInt(rangoTemperatura[0]);
                Integer temperaturaMax = Integer.parseInt(rangoTemperatura[1]);

                Integer humedadMin = Integer.parseInt(rangoHumedad[0]);
                Integer humedadMax = Integer.parseInt(rangoHumedad[1]);

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
                                numeroSeguro, Estado.ALMACENADA, alto, ancho, profundidad, temperaturaMax, temperaturaMin,
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
            centroExposicion.setObras(obras);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}