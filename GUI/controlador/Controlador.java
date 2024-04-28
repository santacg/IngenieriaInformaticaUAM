package GUI.controlador;

import GUI.modelo.expofy.Expofy;
import GUI.vistas.Ventana;

public class Controlador {
	
	private ControladorPantallaPrincipal controladorPantallaPrincipal;
	private ControladorRegistro controladorRegistro;
	private ControladorLoginGestor controladorLogInGestor;
	private ControladorLoginEmpleado controladorLoginEmpleado;
	private Ventana frame;
	private Expofy expofy;

	public Controlador(Ventana frame, Expofy expofy) {
		this.frame = frame;
		this.expofy = expofy;
		this.controladorPantallaPrincipal = new ControladorPantallaPrincipal(frame, expofy);
		this.controladorRegistro = new ControladorRegistro(frame, expofy);
		this.controladorLogInGestor	= new ControladorLoginGestor(frame, expofy);
		this.controladorLoginEmpleado = new ControladorLoginEmpleado(frame, expofy);
	}

	public ControladorPantallaPrincipal getControladorPantallaPrincipal() {
		return this.controladorPantallaPrincipal;
	}

	public ControladorRegistro getControladorRegistro() {
		return this.controladorRegistro;
	}

	public ControladorLoginGestor getControladorLoginGestor() {
		return this.controladorLogInGestor;
	}

	public ControladorLoginEmpleado getControladorLoginEmpleado() {
		return this.controladorLoginEmpleado;
	}
}
