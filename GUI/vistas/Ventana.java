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

		// Configuración del panel principal
		panelPrincipal.setLayout(new BorderLayout(50, 50));
		
		// Agregar un título y descripción
		JLabel titleLabel = new JLabel("EXPOFY", JLabel.CENTER);
		titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
		panelPrincipal.add(titleLabel, BorderLayout.NORTH);

		// Panel para el formulario de login
		JPanel loginPanel = new JPanel(new GridLayout(2, 2, 5, 5));
		JLabel userLabel = new JLabel("Usuario:");
		JTextField userField = new JTextField();
		JLabel passwordLabel = new JLabel("Contraseña:");
		JPasswordField passwordField = new JPasswordField();
		loginPanel.add(userLabel);
		loginPanel.add(userField);
		loginPanel.add(passwordLabel);
		loginPanel.add(passwordField);
		panelPrincipal.add(loginPanel, BorderLayout.CENTER);

		// Botón de login
		JButton loginButton = new JButton("Login");
		panelPrincipal.add(loginButton, BorderLayout.SOUTH);

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
