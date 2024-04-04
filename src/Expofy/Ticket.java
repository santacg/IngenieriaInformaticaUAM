package Expofy;

import java.io.File;

import Exposicion.*;

import java.time.LocalDate;
import java.time.LocalTime;

import es.uam.eps.padsof.tickets.ITicketInfo;



public class Ticket implements ITicketInfo {
    private Exposicion exposicion;
    private static int nextId = 0;
    private int idTicket;
    private int numberOfTickets;
    private String ticketDayTime;
    private double payedPrice;
    
    
    public Ticket(Exposicion exposicion, double precioFinal, int nEntradas, LocalDate fecha, Hora hora){
        this.exposicion = exposicion;
        this.idTicket = nextId++;
        this.payedPrice = precioFinal;
        this.numberOfTickets = nEntradas;
        this.ticketDayTime = fecha.toString() + " " + hora.getHoraInicio().toString();
    }
    public int getIdTicket () { return this.idTicket; }
	public String getExhibitionName () { return this.exposicion.getNombre(); }
	public int getNumberOfTickets () { return this.numberOfTickets; }
	public String getTicketDateTime () { return this.ticketDayTime; }
	public double getPrice () { return this.exposicion.getPrecio(); }
	public double getDiscount () { return this.exposicion.getDescuento().getDescuento(); }
	public double getPayedPrice () { return this.payedPrice; }
}