package Info;

/**
 * La clase InformacionNutricional representa la información nutricional de un
 * alimento.
 * Contiene los valores de calorías, hidratos de carbono, grasas totales, grasas
 * saturadas,
 * proteínas, azúcar, fibra y sodio.
 */
public class InformacionNutricional {

    private double calorias, hidratos, grasasTotales, grasasSaturadas, proteinas, azucar, fibra, sodio;

    /**
     * Crea una instancia de InformacionNutricional con los valores especificados.
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

    /**
     * Obtiene el valor de las calorías del alimento.
     * 
     * @return el valor de las calorías
     */
    public double getCalorias() {
        return calorias;
    }

    /**
     * Obtiene el valor de los hidratos de carbono del alimento.
     * 
     * @return el valor de los hidratos de carbono
     */
    public double getHidratos() {
        return hidratos;
    }

    /**
     * Obtiene el valor de las grasas totales del alimento.
     * 
     * @return el valor de las grasas totales
     */
    public double getGrasasTotales() {
        return grasasTotales;
    }

    /**
     * Obtiene el valor de las grasas saturadas del alimento.
     * 
     * @return el valor de las grasas saturadas
     */
    public double getGrasasSaturadas() {
        return grasasSaturadas;
    }

    /**
     * Obtiene el valor de las proteínas del alimento.
     * 
     * @return el valor de las proteínas
     */
    public double getProteinas() {
        return proteinas;
    }

    /**
     * Obtiene el valor del azúcar del alimento.
     * 
     * @return el valor del azúcar
     */
    public double getAzucar() {
        return azucar;
    }

    /**
     * Obtiene el valor de la fibra del alimento.
     * 
     * @return el valor de la fibra
     */
    public double getFibra() {
        return fibra;
    }

    /**
     * Obtiene el valor del sodio del alimento.
     * 
     * @return el valor del sodio
     */
    public double getSodio() {
        return sodio;
    }

    /**
     * Establece el valor de las calorías del alimento.
     * 
     * @param calorias el valor de las calorías a establecer
     */
    public void setCalorias(Double calorias) {
        this.calorias = calorias;
    }

    /**
     * Establece el valor de los hidratos de carbono del alimento.
     * 
     * @param hidratos el valor de los hidratos de carbono a establecer
     */
    public void setHidratos(double hidratos) {
        this.hidratos = hidratos;
    }

    /**
     * Establece el valor de las grasas totales del alimento.
     * 
     * @param grasasTotales el valor de las grasas totales a establecer
     */
    public void setGrasasTotales(double grasasTotales) {
        this.grasasTotales = grasasTotales;
    }

    /**
     * Establece el valor de las grasas saturadas del alimento.
     * 
     * @param grasasSaturadas el valor de las grasas saturadas a establecer
     */
    public void setGrasasSaturadas(double grasasSaturadas) {
        this.grasasSaturadas = grasasSaturadas;
    }

    /**
     * Establece el valor de las proteínas del alimento.
     * 
     * @param proteinas el valor de las proteínas a establecer
     */
    public void setProteinas(double proteinas) {
        this.proteinas = proteinas;
    }

    /**
     * Establece el valor del azúcar del alimento.
     * 
     * @param azucar el valor del azúcar a establecer
     */
    public void setAzucar(double azucar) {
        this.azucar = azucar;
    }

    /**
     * Establece el valor de la fibra del alimento.
     * 
     * @param fibra el valor de la fibra a establecer
     */
    public void setFibra(double fibra) {
        this.fibra = fibra;
    }

    /**
     * Establece el valor del sodio del alimento.
     * 
     * @param sodio el valor del sodio a establecer
     */
    public void setSodio(double sodio) {
        this.sodio = sodio;
    }

    /**
     * Añade el valor especificado a las calorías del alimento.
     * 
     * @param calorias el valor a añadir a las calorías
     */
    public void addCalorias(Double calorias) {
        this.calorias += calorias;
    }

    /**
     * Añade el valor especificado a los hidratos de carbono del alimento.
     * 
     * @param hidratos el valor a añadir a los hidratos de carbono
     */
    public void addHidratos(double hidratos) {
        this.hidratos += hidratos;
    }

    /**
     * Añade el valor especificado a las grasas totales del alimento.
     * 
     * @param grasasTotales el valor a añadir a las grasas totales
     */
    public void addGrasasTotales(double grasasTotales) {
        this.grasasTotales += grasasTotales;
    }

    /**
     * Añade el valor especificado a las grasas saturadas del alimento.
     * 
     * @param grasasSaturadas el valor a añadir a las grasas saturadas
     */
    public void addGrasasSaturadas(double grasasSaturadas) {
        this.grasasSaturadas += grasasSaturadas;
    }

    /**
     * Añade el valor especificado a las proteínas del alimento.
     * 
     * @param proteinas el valor a añadir a las proteínas
     */
    public void addProteinas(double proteinas) {
        this.proteinas += proteinas;
    }

    /**
     * Añade el valor especificado al azúcar del alimento.
     * 
     * @param azucar el valor a añadir al azúcar
     */
    public void addAzucar(double azucar) {
        this.azucar += azucar;
    }

    /**
     * Añade el valor especificado a la fibra del alimento.
     * 
     * @param fibra el valor a añadir a la fibra
     */
    public void addFibra(double fibra) {
        this.fibra += fibra;
    }

    /**
     * Añade el valor especificado al sodio del alimento.
     * 
     * @param sodio el valor a añadir al sodio
     */
    public void addSodio(double sodio) {
        this.sodio += sodio;
    }

    /**
     * Añade la información nutricional especificada a la información nutricional
     * actual.
     * 
     * @param info la información nutricional a añadir
     * @return la información nutricional actualizada
     */
    public InformacionNutricional addInformacionNutricional(InformacionNutricional info) {
        this.calorias += info.getCalorias();
        this.hidratos += info.getHidratos();
        this.grasasTotales += info.getGrasasTotales();
        this.grasasSaturadas += info.getGrasasSaturadas();
        this.proteinas += info.getProteinas();
        this.azucar += info.getAzucar();
        this.fibra += info.getFibra();
        this.sodio += info.getSodio();
        return this;
    }

    /**
     * Devuelve una representación en forma de cadena de la información nutricional.
     * 
     * @return una cadena que contiene los valores de la información nutricional
     */
    public String toString() {
        return String.format(
                "Valor energético: %.2f kcal, Hidratos de carbono: %.2f g, Grasas: %.2f g, Saturadas: %.2f g, Proteínas: %.2f g, Azúcares: %.2f g, Fibra: %.2f g, Sodio: %.2f mg.",
                calorias, hidratos, grasasTotales, grasasSaturadas, proteinas, azucar, fibra, sodio);
    }

}