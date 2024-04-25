package GUI.main;

import java.awt.EventQueue;

import GUI.controlador.Controlador;
import GUI.vistas.Ventana;

public class Main {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {						
					Ventana frame = new Ventana();
					Controlador controlador = new Controlador(frame);
					frame.setControlador(controlador);
					frame.mostrarPanel(frame.getPanelPrincipal());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
