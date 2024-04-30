package GUI.main;

import java.awt.EventQueue;
import GUI.controlador.Controlador;
import GUI.modelo.expofy.Expofy;
import GUI.vistas.Ventana;

/**
 * Clase Main
 * Esta clase es la encargada de lanzar la aplicación Expofy.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public class Main {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Expofy expofy = Expofy.getInstance();
					expofy.reanudarExpofy();

					Ventana frame = new Ventana();
					Controlador controlador = new Controlador(frame, expofy);
					frame.setControlador(controlador);
					frame.mostrarPanel(frame.getPanelPrincipal());
					frame.setLocation(150, 50);
					frame.setSize(1280, 720);
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
