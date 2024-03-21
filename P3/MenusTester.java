import java.util.List;
import java.util.Map;

import Menu.Menu;
import Plato.Plato;

public class MenusTester extends PlatosTester {
    public static void main(String[] args) {
        MenusTester tester = new MenusTester();
        for (Menu menu : tester.crearMenus())
            System.out.println("* " + menu);
    }

    public List<Menu> crearMenus() {
        Map<String, Plato> platos = this.crearPlatos();
        Menu m1 = new Menu(platos.get("Macarrones"), platos.get("Tortilla"));
        Menu m2 = new Menu(platos.get("Macarrones"), platos.get("Tortilla guisada"));
        Menu m3 = new Menu(platos.get("Macarrones"));
        return List.of(m1, m2, m3);
    }
}