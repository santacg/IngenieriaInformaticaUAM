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
		setBounds(100, 100, 450, 300);
		cartas = new JPanel();
		cartas.setLayout(new CardLayout());
		JPanel panelPrincipal = new JPanel();
		JPanel logInGestor = new JPanel();
		JPanel logInEmpleado = new JPanel();
		JPanel exposiciones = new JPanel();
		JPanel signUp = new JPanel();

		cartas.add(logInGestor, LOGINGESTOR);
		cartas.add(logInEmpleado, LOGINEMPLEADO);
		cartas.add(exposiciones, EXPOSICIONES); 
		cartas.add(signUp, SIGNUP);
		cartas.add(panelPrincipal, PANELPRINCIPAL);

		JPasswordField password = new JPasswordField(10);
		panelPrincipal.add(password);
		setContentPane(cartas);
	}

	public String getPanelPrincipal() {
		return PANELPRINCIPAL;
	}

	public void setControlador(Controlador controlador) {
	}
	
	public void mostrarPanel(String carta) {
		CardLayout l = (CardLayout)cartas.getLayout();
		l.show(cartas, carta);
	}

}
