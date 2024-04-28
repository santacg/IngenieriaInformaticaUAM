package GUI.controlador;

import GUI.modelo.expofy.Expofy;
import GUI.vistas.Ventana;

public class Controlador {
	
	private ControladorPantallaPrincipal controladorPantallaPrincipal;
	private Ventana frame;
	private Expofy expofy;

	public Controlador(Ventana frame, Expofy expofy) {
		this.frame = frame;
		this.expofy = expofy;
		this.controladorPantallaPrincipal = new ControladorPantallaPrincipal(frame, expofy);
	}

	public ControladorPantallaPrincipal getControladorPantallaPrincipal() {
		return this.controladorPantallaPrincipal;
	}

}
