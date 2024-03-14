package P3.InformacionNutricional;

public class InformacionNutricionalUnidad extends InformacionNutricional{
    private double unidades;

    public InformacionNutricionalUnidad(Integer calorias, double hidratos, double grasasTotales, double grasasSaturadas,
            double proteinas, double azucar, double fibra, double sodio, double unidades) {
        super(calorias, hidratos, grasasTotales, grasasSaturadas, proteinas, azucar, fibra, sodio);
        this.unidades = unidades;
    }

    public double getUnidades() {
        return unidades;
    }

    public void setUnidades(double unidades) {
        this.unidades = unidades;
    }
}
