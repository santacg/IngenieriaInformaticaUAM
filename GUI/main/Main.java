package GUI.main;

import java.awt.EventQueue;

/*
Imports para pantalla completa:
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
*/

import GUI.controlador.Controlador;
import GUI.modelo.expofy.Expofy;
import GUI.vistas.Ventana;

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
					
					frame.setSize(600, 450); // Tamaño de la ventana

					/* 
					Las tres líneas sirven para mostrar la ventana en pantalla completa:
					GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
					GraphicsDevice device = env.getDefaultScreenDevice();
					device.setFullScreenWindow(frame);
					*/

					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
