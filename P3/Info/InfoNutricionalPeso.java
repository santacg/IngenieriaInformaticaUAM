package Info;

public class InfoNutricionalPeso extends InformacionNutricional {
    private double peso;

    public InfoNutricionalPeso(double calorias, double hidratos, double grasasTotales, double grasasSaturadas,
            double proteinas, double azucar, double fibra, double sodio) {
        super(calorias, hidratos, grasasTotales, grasasSaturadas, proteinas, azucar, fibra, sodio);
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getPeso() {
        return peso;
    }

}