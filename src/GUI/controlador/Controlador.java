package src.GUI.controlador;

import src.expofy.*; 
import src.GUI.vistas.*;

public class Controlador {
	
	private ControlAltaTarea contAltaTarea;
	private ControlDetalleProyecto contDetalleProyecto;
	private Ventana frame;
	private Proyecto modelo;

	public Controlador(Ventana frame, Proyecto modelo) {
		this.frame = frame;
		this.modelo = modelo;
		this.contAltaTarea = new ControlAltaTarea(frame, modelo);
		this.contDetalleProyecto = new ControlDetalleProyecto(frame, modelo);
	}

	public ControlAltaTarea getControlAltaTarea() {
		return this.contAltaTarea;
	}
	
	public ControlDetalleProyecto getControlDetalleProyecto() {
		return this.contDetalleProyecto;
	}

}
