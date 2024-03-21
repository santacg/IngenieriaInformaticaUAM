package Info;

/**
 * La clase InfoNutricionalPeso representa la información nutricional de un
 * alimento
 * que incluye el peso del alimento en gramos.
 * 
 * Esta clase hereda de la clase InformacionNutricional y agrega el atributo
 * peso.
 */
public class InfoNutricionalPeso extends InformacionNutricional {
    private double peso;

    /**
     * Crea una instancia de InfoNutricionalPeso con los valores de las propiedades
     * de la información nutricional y el peso del alimento.
     * 
     * @param calorias        Las calorías del alimento.
     * @param hidratos        Los hidratos de carbono del alimento.
     * @param grasasTotales   Las grasas totales del alimento.
     * @param grasasSaturadas Las grasas saturadas del alimento.
     * @param proteinas       Las proteínas del alimento.
     * @param azucar          El azúcar del alimento.
     * @param fibra           La fibra del alimento.
     * @param sodio           El sodio del alimento.
     */
    public InfoNutricionalPeso(double calorias, double hidratos, double grasasTotales, double grasasSaturadas,
            double proteinas, double azucar, double fibra, double sodio) {
        super(calorias, hidratos, grasasTotales, grasasSaturadas, proteinas, azucar, fibra, sodio);
    }

    /**
     * Establece el peso del alimento.
     * 
     * @param peso El peso del alimento en gramos.
     */
    public void setPeso(double peso) {
        this.peso = peso;
    }

    /**
     * Obtiene el peso del alimento.
     * 
     * @return El peso del alimento en gramos.
     */
    public double getPeso() {
        return peso;
    }
}