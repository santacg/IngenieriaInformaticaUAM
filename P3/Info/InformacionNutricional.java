package P3.Info;

public abstract class InformacionNutricional {

    private double calorias, hidratos, grasasTotales, grasasSaturadas, proteinas, azucar, fibra, sodio;


    public InformacionNutricional(Integer calorias, double hidratos, double grasasTotales, double grasasSaturadas,
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
        return "Valor energetico: " + this.calorias + " kcal, Hidratos de carbono: " + this.hidratos + " g, Grasas: " + this.grasasTotales + "g, Saturadas: " + this.grasasSaturadas + "g, Proteinas: " + this.proteinas + "g, Azucares: " + this.azucar + "g, Fibra: " + this.fibra + "g, Sodio: " + this.sodio
        + " mg.";
    }
}