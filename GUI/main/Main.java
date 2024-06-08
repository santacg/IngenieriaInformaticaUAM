package gui.main;

import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import gui.controlador.Controlador;
import gui.modelo.expofy.Expofy;
import gui.vistas.Ventana;

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

					frame.addWindowListener(new WindowAdapter() {
						@Override
						public void windowClosing(WindowEvent e) {
							expofy.persistirExpofy();
						}
					});

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
