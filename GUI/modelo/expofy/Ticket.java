package gui.modelo.expofy;

import java.time.LocalDate;
import es.uam.eps.padsof.tickets.ITicketInfo;
import gui.modelo.exposicion.*;

import java.io.File;

/**
 * Clase Ticket
 * Esta clase representa un ticket de entrada a una exposición. Incluye
 * información sobre la exposición a la que se accede, el número de entradas
 * adquiridas, el día y la hora de la visita, el precio pagado y el precio
 * original.
 * 
 * Implementa la interfaz ITicketInfo.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz, Eduardo Junoy Ortega
 * 
 */
public class Ticket implements ITicketInfo {
    private Exposicion exposicion;
    private static int nextId = 0;
    private int idTicket;
    private int numberOfTickets;
    private String ticketDayTime;
    private double payedPrice;

    /**
     * Constructor de la clase Ticket. 
     * @param exposicion exposicion de la que se quiere obtener el ticket
     * @param precioFinal precio final del ticket
     * @param nEntradas numero de entradas
     * @param fecha fecha de la visita
     * @param hora hora de la visita
     */
    public Ticket(Exposicion exposicion, double precioFinal, int nEntradas, LocalDate fecha, Hora hora) {
        this.exposicion = exposicion;
        this.idTicket = nextId++;
        this.payedPrice = precioFinal;
        this.numberOfTickets = nEntradas;
        this.ticketDayTime = fecha.toString() + " " + hora.getHoraInicio().toString();

    }

    /**
     * Devuelve el id del ticket.
     * @return id del ticket
     */
    public int getIdTicket() {
        return this.idTicket;
    }

    /**
     * Devuelve el nombre del centro de exposiciones.
     * @return nombre del centro de exposiciones
     */
    public String getExhibitionCenterName() {
        return "Centro de exposiciones";
    }

    /**
     * Devuelve el nombre de la exposición.
     * @return nombre de la exposición
     */
    public String getExhibitionName() {
        return this.exposicion.getNombre();
    }

    /**
     * Devuelve el número de entradas.
     * @return número de entradas
     */
    public int getNumberOfTickets() {
        return this.numberOfTickets;
    }

    /**
     * Devuelve el día y la hora de la visita.
     * @return día y hora de la visita
     */
    public String getTicketDateTime() {
        return this.ticketDayTime;
    }

    /**
     * Devuelve el precio de la exposición.
     * @return precio de la exposición
     */
    public double getPrice() {
        return this.exposicion.getPrecio();
    }

    /**
     * Devuelve el descuento aplicado.
     * @return descuento aplicado
     */
    public double getDiscount() {
        //return this.exposicion.getDescuento().getDescuento();
        return 0.0;
    }

    /**
     * Devuelve el precio pagado.
     * @return precio pagado
     */
    public double getPayedPrice() {
        return this.payedPrice;
    }

    /**
     * Devuelve la imagen del centro de exposiciones.
     * @return imagen del centro de exposiciones
     */
    public String getPicture() {
        return ("." + File.separator + "gui" +  File.separator + "resources" + File.separator + "centrocentro.jpg");
    }

    /**
     * Devuelve la imagen de la exposición. 
     * @return imagen de la exposición
     */
    public String dataTicket() {
        return "Ticket id: " + getIdTicket() + " Center name: " + getExhibitionCenterName() + " Expo name: "
                + getExhibitionName() + " N_tickets: " + getNumberOfTickets() + " Ticket DayTime: " + getTicketDateTime()
                + " Price: " + getPrice() + " Discount: " + getDiscount() + " PayedPrice: " + getPayedPrice();
    }
}