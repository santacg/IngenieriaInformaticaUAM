package PlanificadorMenu;

import Plato.Plato;
import Menu.Menu;
import Ingrediente.Alergeno;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import Info.InformacionNutricional;

/**
 * La clase PlanificadorMenu representa un planificador de menús que ayuda a
 * seleccionar una combinación de platos
 * basada en ciertos criterios como valores nutricionales máximos y alérgenos a
 * evitar.
 * 
 * @author Carlos García Santa y Joaquín Abad Díaz
 */
public class PlanificadorMenu {
    private List<Plato> platos;
    private List<Alergeno> alergenos = new ArrayList<>();
    private Map<ElementoNutricional, Double> maximosNutricionales = new HashMap<>();

    /**
     * Crea una instancia de PlanificadorMenu con la lista de platos dada.
     *
     * @param platos la lista de platos disponibles para seleccionar.
     */
    public PlanificadorMenu(List<Plato> platos) {
        this.platos = platos;
    }

    /**
     * Establece un valor máximo para el elemento nutricional dado.
     *
     * @param elementoNutricional el elemento nutricional para el cual se
     *                            establecerá el valor máximo.
     * @param maximo              el valor máximo para el elemento nutricional.
     * @return la instancia actual de PlanificadorMenu.
     */
    public PlanificadorMenu conMaximo(ElementoNutricional elementoNutricional, double maximo) {
        maximosNutricionales.put(elementoNutricional, maximo);
        return this;
    }

    /**
     * Establece los alérgenos que se deben evitar en la selección de platos.
     *
     * @param alergenos los alérgenos a evitar.
     * @return la instancia actual de PlanificadorMenu.
     */
    public PlanificadorMenu sinAlergenos(Alergeno... alergenos) {
        for (Alergeno alergeno : alergenos) {
            this.alergenos.add(alergeno);
        }
        return this;
    }

    /**
     * Planifica un menú seleccionando una combinación de platos que cumpla con los
     * requisitos dados.
     *
     * @param minCalorias el valor mínimo de calorías que debe tener el menú.
     * @param maxCalorias el valor máximo de calorías que debe tener el menú.
     * @return el menú planificado, o null si no se pudo encontrar una combinación
     *         de platos que cumpla con los requisitos.
     */
    public Menu planificar(double minCalorias, double maxCalorias) {
        List<Plato> platosFiltrados = filtrarPorAlergenos(this.platos);
        platosFiltrados = filtrarPorMaximosNutricionales(platosFiltrados);

        List<Plato> combinacionSeleccionada = seleccionarCombinacionDePlatos(platosFiltrados, minCalorias, maxCalorias);

        if (combinacionSeleccionada.isEmpty()) {
            return null;
        }

        Menu menu = new Menu(combinacionSeleccionada.toArray(new Plato[0]));
        return menu;
    }

    /**
     * Filtra la lista de platos por los alérgenos a evitar.
     *
     * @param platos la lista de platos a filtrar.
     * @return la lista de platos filtrada.
     */
    public List<Plato> filtrarPorAlergenos(List<Plato> platos) {
        return platos.stream().filter(plato -> plato.getAlergeno().stream().noneMatch(alergenos::contains))
                .collect(Collectors.toList());
    }

    /**
     * Filtra la lista de platos por los valores máximos de los elementos
     * nutricionales.
     *
     * @param platos la lista de platos a filtrar.
     * @return la lista de platos filtrada.
     */
    private List<Plato> filtrarPorMaximosNutricionales(List<Plato> platos) {
        return platos.stream().filter(plato -> {
            InformacionNutricional info = plato.getInformacionNutricional();
            for (Map.Entry<ElementoNutricional, Double> maximo : maximosNutricionales.entrySet()) {
                switch (maximo.getKey()) {
                    case HIDRATOS:
                        if (info.getHidratos() > maximo.getValue())
                            return false;
                        break;
                    case GRASA_TOTAL:
                        if (info.getGrasasTotales() > maximo.getValue())
                            return false;
                        break;
                    case GRASA_SATURADA:
                        if (info.getGrasasSaturadas() > maximo.getValue())
                            return false;
                        break;
                    case PROTEINA:
                        if (info.getProteinas() > maximo.getValue())
                            return false;
                        break;
                    case AZUCARES:
                        if (info.getAzucar() > maximo.getValue())
                            return false;
                        break;
                    case FIBRA:
                        if (info.getFibra() > maximo.getValue())
                            return false;
                        break;
                    case SODIO:
                        if (info.getSodio() > maximo.getValue())
                            return false;
                        break;
                    default:
                        throw new IllegalArgumentException("Elemento nutricional desconocido: " + maximo.getKey());
                }
            }
            return true;
        }).collect(Collectors.toList());
    }

    /**
     * Selecciona una combinación de platos que cumpla con los requisitos de
     * calorías dados.
     *
     * @param platosFiltrados la lista de platos filtrada.
     * @param minCalorias     el valor mínimo de calorías que debe tener la
     *                        combinación de platos.
     * @param maxCalorias     el valor máximo de calorías que debe tener la
     *                        combinación de platos.
     * @return la combinación de platos seleccionada.
     */
    private List<Plato> seleccionarCombinacionDePlatos(List<Plato> platosFiltrados, double minCalorias,
            double maxCalorias) {
        List<Plato> combinacion = new ArrayList<>();
        if (buscarCombinacion(platosFiltrados, 0, combinacion, 0, minCalorias, maxCalorias)) {
            return combinacion;
        }
        return new ArrayList<>();
    }

    /**
     * Busca una combinación de platos que cumpla con los requisitos de calorías
     * dados.
     *
     * @param platos            la lista de platos disponibles.
     * @param inicio            la posición de inicio para buscar en la lista de
     *                          platos.
     * @param combinacionActual la combinación de platos actual.
     * @param caloriasActuales  el total de calorías de la combinación actual.
     * @param minCalorias       el valor mínimo de calorías que debe tener la
     *                          combinación de platos.
     * @param maxCalorias       el valor máximo de calorías que debe tener la
     *                          combinación de platos.
     * @return true si se encontró una combinación de platos que cumpla con los
     *         requisitos de calorías, false de lo contrario.
     */
    private boolean buscarCombinacion(List<Plato> platos, int inicio, List<Plato> combinacionActual,
            double caloriasActuales, double minCalorias, double maxCalorias) {
        if (caloriasActuales >= minCalorias && caloriasActuales <= maxCalorias) {
            return true;
        }
        if (inicio >= platos.size() || caloriasActuales > maxCalorias) {
            return false;
        }

        for (int i = inicio; i < platos.size(); i++) {
            Plato platoActual = platos.get(i);
            double caloriasPlato = calcularCalorias(platoActual);

            combinacionActual.add(platoActual);
            if (buscarCombinacion(platos, i + 1, combinacionActual, caloriasActuales + caloriasPlato, minCalorias,
                    maxCalorias)) {
                return true;

            }
            combinacionActual.remove(combinacionActual.size() - 1);
        }
        return false;
    }

    /**
     * Calcula el número de calorías en un plato dado.
     *
     * @param plato el plato del cual se desea calcular las calorías
     * @return el número de calorías en el plato dado
     */
    private double calcularCalorias(Plato plato) {
        return plato.getInformacionNutricional().getCalorias();
    }
}