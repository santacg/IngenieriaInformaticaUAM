package GUI.vistas;

import javax.swing.*;
import java.awt.*;
import GUI.controlador.*;

public class Ventana extends JFrame {
	
	private JPanel cartas;
	private final static String LOGINGESTOR = "logInGestor";
	private final static String LOGINEMPLEADO = "logInEmpleado";
	private final static String EXPOSICIONES = "exposiciones";
	private final static String SIGNUP = "signUp";
	private final static String PANELPRINCIPAL = "panelPrincipal";
	
	public Ventana() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cartas = new JPanel();
		cartas.setLayout(new CardLayout());

		cartas.add(new PantallaPrincipal(), PANELPRINCIPAL);
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

	public String getSignUp() {
		return SIGNUP;
	}

	public void setControlador(Controlador controlador) {
	}
	
	public void mostrarPanel(String carta) {
		CardLayout l = (CardLayout)cartas.getLayout();
		l.show(cartas, carta);
	}

}
