package GUI.modelo.expofy;

import java.time.LocalDate;
import es.uam.eps.padsof.tickets.ITicketInfo;
import GUI.modelo.exposicion.*;

import java.io.File;

public class Ticket implements ITicketInfo {
    private Exposicion exposicion;
    private static int nextId = 0;
    private int idTicket;
    private int numberOfTickets;
    private String ticketDayTime;
    private double payedPrice;

    public Ticket(Exposicion exposicion, double precioFinal, int nEntradas, LocalDate fecha, Hora hora) {
        this.exposicion = exposicion;
        this.idTicket = nextId++;
        this.payedPrice = precioFinal;
        this.numberOfTickets = nEntradas;
        this.ticketDayTime = fecha.toString() + " " + hora.getHoraInicio().toString();

    }

    public int getIdTicket() {
        return this.idTicket;
    }

    public String getExhibitionCenterName() {
        return "Centro de exposiciones";
    }

    public String getExhibitionName() {
        return this.exposicion.getNombre();
    }

    public int getNumberOfTickets() {
        return this.numberOfTickets;
    }

    public String getTicketDateTime() {
        return this.ticketDayTime;
    }

    public double getPrice() {
        return this.exposicion.getPrecio();
    }

    public double getDiscount() {
        return this.exposicion.getDescuento().getDescuento();
    }

    public double getPayedPrice() {
        return this.payedPrice;
    }

    public String getPicture() {
        return ("." + File.separator + "GUI" +  File.separator + "resources" + File.separator + "centrocentro.jpg");
    }

    public String dataTicket() {
        return "Ticket id: " + getIdTicket() + " Center name: " + getExhibitionCenterName() + " Expo name: "
                + getExhibitionName() + " N_tickets: " + getNumberOfTickets() + " Ticket DayTime: " + getTicketDateTime()
                + " Price: " + getPrice() + " Discount: " + getDiscount() + " PayedPrice: " + getPayedPrice();
    }
}