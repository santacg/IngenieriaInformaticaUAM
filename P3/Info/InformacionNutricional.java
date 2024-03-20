package Info;

public abstract class InformacionNutricional {

    private double calorias, hidratos, grasasTotales, grasasSaturadas, proteinas, azucar, fibra, sodio;

    public InformacionNutricional(double calorias, double hidratos, double grasasTotales, double grasasSaturadas,
            double proteinas, double azucar, double fibra, double sodio) {
        this.calorias = calorias;
        this.hidratos = hidratos;
        this.grasasTotales = grasasTotales;
        this.grasasSaturadas = grasasSaturadas;
        this.proteinas = proteinas;
        this.azucar = azucar;
        this.fibra = fibra;
        this.sodio = sodio;
    }

    public double getCalorias() {
        return calorias;
    }

    public double getHidratos() {
        return hidratos;
    }

    public double getGrasasTotales() {
        return grasasTotales;
    }

    public double getGrasasSaturadas() {
        return grasasSaturadas;
    }

    public double getProteinas() {
        return proteinas;
    }

    public double getAzucar() {
        return azucar;
    }

    public double getFibra() {
        return fibra;
    }

    public double getSodio() {
        return sodio;
    }

    public void setCalorias(Double calorias) {
        this.calorias = calorias;
    }

    public void setHidratos(double hidratos) {
        this.hidratos = hidratos;
    }

    public void setGrasasTotales(double grasasTotales) {
        this.grasasTotales = grasasTotales;
    }

    public void setGrasasSaturadas(double grasasSaturadas) {
        this.grasasSaturadas = grasasSaturadas;
    }

    public void setProteinas(double proteinas) {
        this.proteinas = proteinas;
    }

    public void setAzucar(double azucar) {
        this.azucar = azucar;
    }

    public void setFibra(double fibra) {
        this.fibra = fibra;
    }

    public void setSodio(double sodio) {
        this.sodio = sodio;
    }

    public String toString() {
        return String.format(
                "Valor energetico: %.2f kcal, Hidratos de carbono: %.2f g, Grasas: %.2f g, Saturadas: %.2f g, Proteinas: %.2f g, Azucares: %.2f g, Fibra: %.2f g, Sodio: %.2f mg.",
                calorias, hidratos, grasasTotales, grasasSaturadas, proteinas, azucar, fibra, sodio);
    }
}