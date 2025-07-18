package gui.controlador;

import gui.modelo.expofy.Expofy;
import gui.vistas.Ventana;

/**
 * Clase Controlador
 * Controlador de la aplicación que se encarga de gestionar la comunicación
 * entre las vistas y el modelo.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public class Controlador {

	private ControladorPantallaPrincipal controladorPantallaPrincipal;
	private ControladorRegistro controladorRegistro;
	private ControladorLoginGestor controladorLogInGestor;
	private ControladorLoginEmpleado controladorLoginEmpleado;
	private ControladorGestor controladorGestor;
	private ControladorBusquedaExposiciones controladorBusquedaExposiciones;
	private Ventana frame;
	private Expofy expofy;

	/**
	 * Constructor de la clase Controlador.
	 * 
	 * @param frame  Ventana de la aplicación.
	 * @param expofy Modelo de la aplicación.
	 */
	public Controlador(Ventana frame, Expofy expofy) {
		this.frame = frame;
		this.expofy = expofy;
		this.controladorPantallaPrincipal = new ControladorPantallaPrincipal(frame, expofy);
		this.controladorRegistro = new ControladorRegistro(frame, expofy);
		this.controladorLogInGestor = new ControladorLoginGestor(frame, expofy);
		this.controladorLoginEmpleado = new ControladorLoginEmpleado(frame, expofy);
		this.controladorBusquedaExposiciones = new ControladorBusquedaExposiciones(frame, expofy);
	}

	/**
	 * Método que devuelve el controlador de la pantalla principal.
	 * 
	 * @return Controlador de la pantalla principal.
	 */
	public ControladorPantallaPrincipal getControladorPantallaPrincipal() {
		return this.controladorPantallaPrincipal;
	}

	/**
	 * Método que devuelve el controlador del registro.
	 * 
	 * @return Controlador del registro.
	 */
	public ControladorRegistro getControladorRegistro() {
		return this.controladorRegistro;
	}

	/**
	 * Método que devuelve el controlador del login del gestor.
	 * 
	 * @return Controlador del login de gestor.
	 */
	public ControladorLoginGestor getControladorLoginGestor() {
		return this.controladorLogInGestor;
	}

	/**
	 * Método que devuelve el controlador del login del empleado.
	 * 
	 * @return Controlador del login de empleado.
	 */
	public ControladorLoginEmpleado getControladorLoginEmpleado() {
		return this.controladorLoginEmpleado;
	}

	/**
	 * Método que devuelve el controlador del gestor.
	 * 
	 * @return Controlador del gestor.
	 */
	public ControladorGestor getControladorGestor() {
		return this.controladorGestor;
	}

	/**
	 * Método que devuelve el controlador de la búsqueda de exposiciones.
	 * 
	 * @return Controlador de la búsqueda de exposiciones.
	 */
	public ControladorBusquedaExposiciones getControladorBusquedaExposiciones() {
		return this.controladorBusquedaExposiciones;
	}

}
