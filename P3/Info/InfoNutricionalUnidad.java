package Info;

/**
 * Esta clase representa la información nutricional de una unidad de un
 * alimento.
 * Hereda de la clase InformacionNutricional.
 */
public class InfoNutricionalUnidad extends InformacionNutricional {

    private int unidad;

    /**
     * Crea una instancia de InfoNutricionalUnidad con los valores de las
     * propiedades nutricionales.
     * 
     * @param calorias        las calorías del alimento
     * @param hidratos        los hidratos de carbono del alimento
     * @param grasasTotales   las grasas totales del alimento
     * @param grasasSaturadas las grasas saturadas del alimento
     * @param proteinas       las proteínas del alimento
     * @param azucar          el azúcar del alimento
     * @param fibra           la fibra del alimento
     * @param sodio           el sodio del alimento
     */
    public InfoNutricionalUnidad(double calorias, double hidratos, double grasasTotales, double grasasSaturadas,
            double proteinas, double azucar, double fibra, double sodio) {
        super(calorias, hidratos, grasasTotales, grasasSaturadas, proteinas, azucar, fibra, sodio);
    }

    /**
     * Establece la unidad del alimento.
     * 
     * @param unidad la unidad del alimento
     */
    public void setUnidad(int unidad) {
        this.unidad = unidad;
    }

    /**
     * Obtiene la unidad del alimento.
     * 
     * @return la unidad del alimento
     */
    public int getUnidad() {
        return unidad;
    }

}
