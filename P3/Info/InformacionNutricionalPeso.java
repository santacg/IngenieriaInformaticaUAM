package P3.InformacionNutricional;

public class InformacionNutricionalPeso extends InformacionNutricional {
    private double peso;

    public InformacionNutricionalPeso(Integer calorias, double hidratos, double grasasTotales, double grasasSaturadas,
            double proteinas, double azucar, double fibra, double sodio, double peso) {
        super(calorias, hidratos, grasasTotales, grasasSaturadas, proteinas, azucar, fibra, sodio);
        this.peso = peso;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }
}