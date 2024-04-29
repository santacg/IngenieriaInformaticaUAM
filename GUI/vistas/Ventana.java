package GUI.vistas;

import javax.swing.*;

import java.awt.*;

import GUI.controlador.*;

public class Ventana extends JFrame {

	private JPanel cartas;
	private String cartaPrevia, cartaActual;

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

	private ControladorPantallaPrincipal controladorPantallaPrincipal;
	private PantallaPrincipal vistaPantallaPrincipal;

	private LoginEmpleado vistaLoginEmpleado;
	private ControladorLoginEmpleado controladorLoginEmpleado;

	private EnviarMensajes vistaEnviarMensajes;
	private ControladorEmpleado controladorEmpleado;
	private AjustarClimatizacion AjustarClimatizacion;

	private LoginGestor vistaLoginGestor;
	private ControladorLoginGestor controladorLoginGestor;

	private RegistroUsuario vistaRegistro;
	private ControladorRegistro controladorRegistro;

	private BusquedaExposiciones vistaExposiciones;

	private EmpleadoPrincipal vistaEmpleadoPrincipal;

	private ClientePrincipal vistaClientePrincipal;
	private ControladorCliente controladorCliente;

	private GestorPrincipal vistaGestorPrincipal;
	private ControladorGestor controladorGestor;

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

		this.AjustarClimatizacion = new AjustarClimatizacion();
		cartas.add(AjustarClimatizacion, AJUSTARCLIMATIZACION);

		this.vistaLoginGestor = new LoginGestor();
		cartas.add(vistaLoginGestor, LOGINGESTOR);

		this.vistaRegistro = new RegistroUsuario();
		cartas.add(vistaRegistro, REGISTRO);

		this.vistaExposiciones = new BusquedaExposiciones();
		cartas.add(vistaExposiciones, EXPOSICIONES);

		this.vistaEmpleadoPrincipal = new EmpleadoPrincipal();
		cartas.add(vistaEmpleadoPrincipal, EMPLEADOPRINCIPAL);

		this.vistaClientePrincipal = new ClientePrincipal();
		cartas.add(vistaClientePrincipal, CLIENTEPRINCIPAL);

		setContentPane(cartas);
	}

	public String getPanelPrincipal() {
		return PANELPRINCIPAL;
	}

	public String getLogInGestor() {
		return LOGINGESTOR;
	}

	public String getLogInEmpleado() {
		return LOGINEMPLEADO;
	}

	public String getExposiciones() {
		return EXPOSICIONES;
	}

	public String getRegistro() {
		return REGISTRO;
	}

	public String getEmpleadoPrincipal() {
		return EMPLEADOPRINCIPAL;
	}

	public String getClientePrincipal() {
		return CLIENTEPRINCIPAL;
	}

	public String getGestorPrincipal() {
		return GESTORPRINCIPAL;
	}

	public PantallaPrincipal getVistaPantallaPrincipal() {
		return vistaPantallaPrincipal;
	}

	public RegistroUsuario getVistaRegistro() {
		return vistaRegistro;
	}

	public LoginGestor getVistaLogInGestor() {
		return vistaLoginGestor;
	}

	public LoginEmpleado getVistaLoginEmpleado() {
		return vistaLoginEmpleado;
	}

	public EnviarMensajes getVistaEnviarMensajes() {
		return vistaEnviarMensajes;
	}

	public AjustarClimatizacion getVistaAjustarClimatizacion() {
		return AjustarClimatizacion;
	}

	public String getEnviarMensajes() {
		return ENVIARMENSAJES;
	}

	public String getAjustarClimatizacion() {
		return AJUSTARCLIMATIZACION;
	}

	public EmpleadoPrincipal getVistaEmpleadoPrincipal() {
		return vistaEmpleadoPrincipal;
	}

	public GestorPrincipal getVistaGestorPrincipal() {
		if (this.vistaGestorPrincipal != null) {
			return vistaGestorPrincipal;
		}

		this.vistaGestorPrincipal = new GestorPrincipal();
		cartas.add(vistaGestorPrincipal, GESTORPRINCIPAL);
		return vistaGestorPrincipal;
	}

	public ClientePrincipal getVistaClientePrincipal() {
		return vistaClientePrincipal;
	}

	public void setControladorGestor(ControladorGestor controlador) {
		this.controladorGestor = controlador;
		this.vistaGestorPrincipal.setControlador(controladorGestor.getEjecutarListener(),
				controladorGestor.getAgregarListener());
	}

	public void setControladorCliente(ControladorCliente controlador) {
		this.controladorCliente = controlador;
		this.vistaClientePrincipal.setControlador(controladorCliente.getComprarListener(),
				controladorCliente.getActualizarDatos(), controladorCliente.getCerrarSesion());
	}

	public void setControladorEmpleado(ControladorEmpleado controlador) {
		this.controladorEmpleado = controlador;
		this.vistaEmpleadoPrincipal.setControlador(controlador.getEnviarMsjListener(), controlador.getClimatizacionListener());
	}

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
	}

	public void mostrarPanel(String carta) {
		cartaPrevia = cartaActual;
		cartaActual = carta;

		CardLayout l = (CardLayout) cartas.getLayout();
		l.show(cartas, carta);
	}

	public void mostrarPanelPrevio() {
		if (cartaPrevia != null) {
			mostrarPanel(cartaPrevia);
		}
	}
}
