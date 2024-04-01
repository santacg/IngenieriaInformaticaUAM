import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LectorCSVObras {

    public static void main(String[] args) {
        String line = "";
        String csvSeparador = ";";

        try (BufferedReader br = new BufferedReader(new FileReader("ruta/a/tu/archivo.csv"))) {
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(csvSeparador);

                String tipo = fields[0];
                String origen = fields[1];
                String titulo = fields[2];
                String autor = fields[3];
                Integer anio = Integer.parseInt(fields[4]);
                String descripcion = fields[5];
                Double cuantiaSeguro = Double.parseDouble(fields[6]);
                String numeroSeguro = fields[7];
                Double ancho = Double.parseDouble(fields[13]);
                Double alto = Double.parseDouble(fields[14]);
                String temperatura = fields[15];
                String humedad = fields[16];

                String[] rangoTemperatura = temperatura.split("-");
                String[] rangoHumedad = humedad.split("-");

                Integer temperaturaMin = Integer.parseInt(rangoTemperatura[0]);
                Integer temperaturaMax = Integer.parseInt(rangoTemperatura[1]);

                Integer humedadMin = Integer.parseInt(rangoHumedad[0]);
                Integer humedadMax = Integer.parseInt(rangoHumedad[1]);
                
                Boolean externa;

                switch (tipo.toLowerCase()) {
                    case "cuadro":
                        if (origen.toLowerCase().equals("externa")) {
                            externa = true;
                        } else {
                            externa = false;
                        }
                        Cuadro cuadro = new Cuadro(titulo, anio, descripcion, externa, cuantiaSeguro, numeroSeguro, Estado.ALMACENADA, fields[8], fields[13], fields[14], temperaturaMin, temperaturaMax, humedadMax, humedadMin);
                        break;
                    case "escultura":
                        break;
                    case "fotografia":
                        break;
                    case "audiovisual":
                        break;
                    default:
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}