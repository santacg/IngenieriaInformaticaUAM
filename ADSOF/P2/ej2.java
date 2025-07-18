public class Lector extends Usuario {
    private PlanPrecio plan;
    private List<Publicacion> publicacionesAdquiridas;

    // Constructor y otros métodos...

    // Método para calcular el adeudo mensual
    public double calcularAdeudoMensual() {
        double adeudo = plan.calcularTarifaBase(); // Costo base del plan
        
        for (Publicacion publicacion : publicacionesAdquiridas) {
            adeudo += plan.calcularCostoPublicacion(publicacion); // Costo por publicación
        }
        return adeudo;
    }
}
