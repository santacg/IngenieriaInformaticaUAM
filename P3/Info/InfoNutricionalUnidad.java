package Info;

public class InfoNutricionalUnidad extends InformacionNutricional{

    private int unidad;

    public InfoNutricionalUnidad(double calorias, double hidratos, double grasasTotales, double grasasSaturadas,
            double proteinas, double azucar, double fibra, double sodio) {
        super(calorias, hidratos, grasasTotales, grasasSaturadas, proteinas, azucar, fibra, sodio);
    }

    public void setUnidad(int unidad) {
        this.unidad = unidad;
    }

    public int getUnidad() {
        return unidad;
    }

}
