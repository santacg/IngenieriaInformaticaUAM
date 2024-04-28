package GUI.vistas;

import javax.swing.*;

import java.awt.*;

import GUI.controlador.*;
import GUI.modelo.centroExposicion.Gestor;

public class Ventana extends JFrame {

	private JPanel cartas;
	private String cartaPrevia, cartaActual;

	private final static String LOGINGESTOR = "logInGestor";
	private final static String LOGINEMPLEADO = "logInEmpleado";
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

	private LoginGestor vistaLoginGestor;
	private ControladorLoginGestor controladorLoginGestor;

	private RegistroUsuario vistaRegistro;
	private ControladorRegistro controladorRegistro;

	private BusquedaExposiciones vistaExposiciones;

	private EmpleadoPrincipal vistaEmpleadoPrincipal;

	private ClientePrincipal vistaClientePrincipal;

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

		this.vistaGestorPrincipal = new GestorPrincipal();
		cartas.add(vistaGestorPrincipal, GESTORPRINCIPAL);

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

	public EmpleadoPrincipal getVistEmpleadoPrincipal() {
		return vistaEmpleadoPrincipal;
	}

	public GestorPrincipal getVistaGestorPrincipal() {
		return vistaGestorPrincipal;
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
		this.vistaLoginEmpleado.setControlador(controladorLoginEmpleado.getAcceptListener(), controladorLoginEmpleado.getAtrasListener());

		this.controladorGestor = controlador.getControladorGestor();
		this.vistaGestorPrincipal.setControlador();
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

	public void panelPrevio() {
		String carta_aux;
		CardLayout l = (CardLayout) cartas.getLayout();
		l.show(cartas, cartaPrevia);
		carta_aux = cartaPrevia;
		cartaPrevia = cartaActual;
		cartaActual = carta_aux;
	}

}
