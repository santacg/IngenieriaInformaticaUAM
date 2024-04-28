package GUI.vistas;

import javax.swing.*;

import org.apache.commons.logging.Log;

import java.awt.*;
import java.awt.event.ActionListener;

import GUI.controlador.*;

public class Ventana extends JFrame {

	private JPanel cartas;
	String cartaPrevia, cartaActual;
	private final static String LOGINGESTOR = "logInGestor";
	private final static String LOGINEMPLEADO = "logInEmpleado";
	private final static String EXPOSICIONES = "exposiciones";
	private final static String SIGNUP = "signUp";
	private final static String PANELPRINCIPAL = "panelPrincipal";

	private ControladorPantallaPrincipal controladorPantallaPrincipal;
	private PantallaPrincipal vistaPantallaPrincipal;
	
	private LoginEmpleado vistaLoginEmpleado;

	private LoginGestor vistaLoginGestor;

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
		setContentPane(cartas);
	}

	public String getPanelPrincipal() {
		return PANELPRINCIPAL;
	}

	public PantallaPrincipal getVistaPantallaPrincipal() {
		return vistaPantallaPrincipal;
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

	public String getSignUp() {
		return SIGNUP;
	}

	public JPanel getCardByName(String name) {
		for (Component comp : cartas.getComponents()) {
			if (comp.getName() != null && comp.getName().equals(name)) {
				return (JPanel) comp;
			}
		}
		return null;
	}

	public void setControlador(Controlador controlador) {
		this.controladorPantallaPrincipal = controlador.getControladorPantallaPrincipal();
		this.vistaPantallaPrincipal.setControlador(
			controladorPantallaPrincipal.getBuscaListener(),
			controladorPantallaPrincipal.getAcceptListener(),
			controladorPantallaPrincipal.getGestorListener(),
			controladorPantallaPrincipal.getEmpleadoListener(),
			controladorPantallaPrincipal.getRegistrarListener()
		);
	}

	public void mostrarPanel(String carta) {
		CardLayout l = (CardLayout) cartas.getLayout();
		l.show(cartas, carta);
	}

	public void panelPrevio() {
		String carta_aux;
		CardLayout l = (CardLayout)cartas.getLayout();
		l.show(cartas, cartaPrevia);
		carta_aux = cartaPrevia;
		cartaPrevia = cartaActual;
		cartaActual = carta_aux;
	}
}
