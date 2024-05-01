package GUI.vistas;

import javax.swing.*;

import java.awt.*;

import GUI.controlador.*;

/**
 * Clase Ventana
 * Esta clase es la encargada de gestionar las distintas ventanas de la
 * aplicación.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public class Ventana extends JFrame {
	// Cartas
	private JPanel cartas;
	private String cartaPrevia, cartaActual;

	// Nombres de las cartas
	private final static String LOGINGESTOR = "logInGestor";
	private final static String LOGINEMPLEADO = "logInEmpleado";
	private final static String ENVIARMENSAJES = "enviarMensajes";
	private final static String AJUSTARCLIMATIZACION = "ajustarClimatizacion";
	private final static String EXPOSICIONES = "exposiciones";
	private final static String REGISTRO = "registro";
	private final static String PANELPRINCIPAL = "panelPrincipal";
	private final static String GESTORPRINCIPAL = "gestorPrincipal";
	private final static String EMPLEADOPRINCIPAL = "empleadoPrincipal";
	private final static String CLIENTEPRINCIPAL = "clientePrincipal";

	// Vistas y controladores
	private ControladorPantallaPrincipal controladorPantallaPrincipal;
	private PantallaPrincipal vistaPantallaPrincipal;

	private LoginEmpleado vistaLoginEmpleado;
	private ControladorLoginEmpleado controladorLoginEmpleado;

	private BusquedaExposiciones vistaBusquedaExposiciones;
	private ControladorBusquedaExposiciones controladorBusquedaExposiciones;

	private LoginGestor vistaLoginGestor;
	private ControladorLoginGestor controladorLoginGestor;

	private RegistroUsuario vistaRegistro;
	private ControladorRegistro controladorRegistro;

	private EmpleadoPrincipal vistaEmpleadoPrincipal;

	private ClientePrincipal vistaClientePrincipal;
	private ControladorCliente controladorCliente;

	private GestorPrincipal vistaGestorPrincipal;
	private ControladorGestor controladorGestor;

	private ControladorEnviarMensajes controladorEnviarMensajes;
	private EnviarMensajes vistaEnviarMensajes;

	private ControladorEmpleado controladorEmpleado;

	private ControladorAjustarClimatizacion controladorAjustarClimatizacion;
	private AjustarClimatizacion vistaAjustarClimatizacion;

	/**
	 * Constructor de la clase Ventana
	 */
	public Ventana() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		cartas = new JPanel();
		cartas.setLayout(new CardLayout());

		this.vistaPantallaPrincipal = new PantallaPrincipal();
		cartas.add(vistaPantallaPrincipal, PANELPRINCIPAL);

		this.vistaLoginEmpleado = new LoginEmpleado();
		cartas.add(vistaLoginEmpleado, LOGINEMPLEADO);

		this.vistaEnviarMensajes = new EnviarMensajes();
		cartas.add(vistaEnviarMensajes, ENVIARMENSAJES);

		this.vistaAjustarClimatizacion = new AjustarClimatizacion();
		cartas.add(vistaAjustarClimatizacion, AJUSTARCLIMATIZACION);

		this.vistaLoginGestor = new LoginGestor();
		cartas.add(vistaLoginGestor, LOGINGESTOR);

		this.vistaRegistro = new RegistroUsuario();
		cartas.add(vistaRegistro, REGISTRO);

		this.vistaBusquedaExposiciones = new BusquedaExposiciones();
		cartas.add(vistaBusquedaExposiciones, EXPOSICIONES);

		this.vistaEmpleadoPrincipal = new EmpleadoPrincipal();
		cartas.add(vistaEmpleadoPrincipal, EMPLEADOPRINCIPAL);

		this.vistaClientePrincipal = new ClientePrincipal();
		cartas.add(vistaClientePrincipal, CLIENTEPRINCIPAL);

		setContentPane(cartas);
	}

	/**
	 * Devuelve el nombre del panel principal
	 */
	public String getPanelPrincipal() {
		return PANELPRINCIPAL;
	}

	/**
	 * Devuelve el nombre del panel de logInGestor
	 */
	public String getLogInGestor() {
		return LOGINGESTOR;
	}

	/**
	 * Devuelve el nombre del panel de logInEmpleado
	 */
	public String getLogInEmpleado() {
		return LOGINEMPLEADO;
	}

	/**
	 * Devuelve el nombre del panel de exposiciones
	 */
	public String getExposiciones() {
		return EXPOSICIONES;
	}

	/**
	 * Devuelve el nombre del panel de registro
	 */
	public String getRegistro() {
		return REGISTRO;
	}

	/**
	 * Devuelve el nombre del panel de empleado principal
	 */
	public String getEmpleadoPrincipal() {
		return EMPLEADOPRINCIPAL;
	}

	/**
	 * Devuelve el nombre del panel de cliente principal
	 */
	public String getClientePrincipal() {
		return CLIENTEPRINCIPAL;
	}

	/**
	 * Devuelve el nombre del panel de gestor principal
	 */
	public String getGestorPrincipal() {
		return GESTORPRINCIPAL;
	}

	/**
	 * Devuelve el nombre del panel de enviar mensajes
	 */
	public String getEnviarMensajes() {
		return ENVIARMENSAJES;
	}

	/**
	 * Devuelve el nombre del panel de ajustar climatización
	 */
	public String getAjustarClimatizacion() {
		return AJUSTARCLIMATIZACION;
	}

	/**
	 * Devuelve la vista de la pantalla principal
	 */
	public PantallaPrincipal getVistaPantallaPrincipal() {
		return vistaPantallaPrincipal;
	}

	/**
	 * Devuelve la vista de registro
	 */
	public RegistroUsuario getVistaRegistro() {
		return vistaRegistro;
	}

	/**
	 * Devuelve la vista de logInGestor
	 */
	public LoginGestor getVistaLogInGestor() {
		return vistaLoginGestor;
	}

	/**
	 * Devuelve la vista de logInEmpleado
	 */
	public LoginEmpleado getVistaLoginEmpleado() {
		return vistaLoginEmpleado;
	}

	/**
	 * Devuelve la vista de enviar mensajes
	 */
	public EnviarMensajes getVistaEnviarMensajes() {
		return vistaEnviarMensajes;
	}

	/**
	 * Devuelve la vista de ajustar climatización
	 */
	public AjustarClimatizacion getVistaAjustarClimatizacion() {
		return vistaAjustarClimatizacion;
	}

	/**
	 * Devuelve la vista de empleado principal
	 */
	public EmpleadoPrincipal getVistaEmpleadoPrincipal() {
		return vistaEmpleadoPrincipal;
	}

	/**
	 * Devuelve la vista de gestor principal
	 */
	public GestorPrincipal getVistaGestorPrincipal() {
		if (this.vistaGestorPrincipal != null) {
			return vistaGestorPrincipal;
		}

		this.vistaGestorPrincipal = new GestorPrincipal();
		cartas.add(vistaGestorPrincipal, GESTORPRINCIPAL);
		return vistaGestorPrincipal;
	}

	/**
	 * Devuelve la vista de cliente principal
	 */
	public ClientePrincipal getVistaClientePrincipal() {
		return vistaClientePrincipal;
	}

	/**
	 * Devuelve la vista de busqueda de exposiciones
	 */
	public BusquedaExposiciones getVistaBusquedaExposiciones() {
		return vistaBusquedaExposiciones;
	}

	/**
	 * Establece el controlador de la vista de gestor principal
	 * 
	 * @param controlador ControladorGestor
	 */
	public void setControladorGestor(ControladorGestor controlador) {
		this.controladorGestor = controlador;
		this.vistaGestorPrincipal.setControlador(controladorGestor.getObraEjecutarListener(),
				controladorGestor.getObraAgregarListener(), controladorGestor.getObraLeerCSVListener(), controladorGestor.getSalaEjecutarListener(),
				controladorGestor.getExposicionEjecutarListener(), controladorGestor.getExposicionAgregarListener(), controladorGestor.getCerrarSesionListener());
	}

	/**
	 * Establece el controlador de la vista de cliente principal
	 * 
	 * @param controlador ControladorCliente
	 */
	public void setControladorCliente(ControladorCliente controlador) {
		this.controladorCliente = controlador;
		this.vistaClientePrincipal.setControlador(controladorCliente.getComprarListener(),
				controladorCliente.getActualizarDatos(), controladorCliente.getCerrarSesion(),
				controladorCliente.getInscribirse());
	}


	/**
	 * Establece el controlador de la vista de empleado principal
	 * 
	 * @param controlador ControladorEmpleado
	 */
	public void setControladorEmpleado(ControladorEmpleado controlador) {
		this.controladorEmpleado = controlador;
		this.vistaEmpleadoPrincipal.setControlador(controlador.getEnviarMsjListener(),
				controlador.getClimatizacionListener(), controlador.getCerrarSesionListener());
	}

	/**
	 * Establece el controlador para la busqueda de exposiciones
	 * 
	 * @param controlador ControladorBusquedaExposiciones
	 */
	public void setControladorBusquedaExposiciones(ControladorBusquedaExposiciones controlador) {
		this.controladorBusquedaExposiciones = controlador;
		this.vistaBusquedaExposiciones.setControlador(controlador.getAtrasListener());
	}

	/**
	 * Establece el controlador para enviar mensajes
	 * 
	 * @param controlador ControladorEnviarMensajes
	 */
	public void setControladorEnviarMensajes(ControladorEnviarMensajes controlador) {
		this.controladorEnviarMensajes = controlador;
		this.vistaEnviarMensajes.setControlador(controlador.getEnviarListener(), controlador.getAtrasListener());
	}

	/**
	 * Establece el controlador para ajustar climatización
	 * 
	 * @param controlador ControladorAjustarClimatizacion
	 */
	public void setControladorAjustarClimatizacion(ControladorAjustarClimatizacion controlador) {
		this.controladorAjustarClimatizacion = controlador;
		this.vistaAjustarClimatizacion.setControlador(controlador.getAtrasListener());
	}

	/**
	 * Establece el controlador general de la aplicación
	 * 
	 * @param controlador El controlador de Expofy
	 */
	public void setControlador(Controlador controlador) {
		this.controladorPantallaPrincipal = controlador.getControladorPantallaPrincipal();
		this.vistaPantallaPrincipal.setControlador(
				controladorPantallaPrincipal.getBuscaListener(),
				controladorPantallaPrincipal.getAcceptListener(),
				controladorPantallaPrincipal.getGestorListener(),
				controladorPantallaPrincipal.getEmpleadoListener(),
				controladorPantallaPrincipal.getRegistrarListener());

		this.controladorRegistro = controlador.getControladorRegistro();
		this.vistaRegistro.setControlador(controladorRegistro.getRegistrarListener(),
				controladorRegistro.getCancelarListener());

		this.controladorLoginGestor = controlador.getControladorLoginGestor();
		this.vistaLoginGestor.setControlador(controladorLoginGestor.getAceptarListener(),
				controladorLoginGestor.getAtrasListener());

		this.controladorLoginEmpleado = controlador.getControladorLoginEmpleado();
		this.vistaLoginEmpleado.setControlador(controladorLoginEmpleado.getAcceptListener(),
				controladorLoginEmpleado.getAtrasListener());

		this.controladorBusquedaExposiciones = controlador.getControladorBusquedaExposiciones();
		this.vistaBusquedaExposiciones.setControlador(controladorBusquedaExposiciones.getAtrasListener());
	}

	/**
	 * Muestra el panel indicado
	 * 
	 * @param carta El nombre del panel a mostrar
	 */
	public void mostrarPanel(String carta) {
		cartaPrevia = cartaActual;
		cartaActual = carta;

		CardLayout l = (CardLayout) cartas.getLayout();
		l.show(cartas, carta);
	}

	/**
	 * Muestra el panel previo, util para ir hacia atrás.
	 */
	public void mostrarPanelPrevio() {
		if (cartaPrevia != null) {
			mostrarPanel(cartaPrevia);
		}
	}
}
