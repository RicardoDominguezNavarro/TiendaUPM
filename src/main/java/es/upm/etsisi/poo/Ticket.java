package es.upm.etsisi.poo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Locale;


public class Ticket<T extends User> {

    public enum TicketStatus {EMPTY, OPEN, CLOSE}

    private String idTicket;

    private String userId;

    private String cashId;

    private TicketStatus ticketStatus;

    private String openingDate;

    private String closingDate;

    private final int MAXITEMS = 100;

    private ArrayList<Product> products;

    private ArrayList<Integer> quantities;

    private ArrayList<String> maxPers;

    private int numItems;



    public Ticket(String idTicket, String userId, String cashId, String openingDate, Catalog catalog) {
        this.idTicket = idTicket;
        this.userId = userId;
        this.cashId = cashId;
        this.openingDate = openingDate;
        this.ticketStatus = TicketStatus.EMPTY;
        this.products = new ArrayList<>(MAXITEMS);
        this.quantities = new ArrayList<>(MAXITEMS);
        this.maxPers = new ArrayList<>(MAXITEMS);
        this.numItems = 0;
    }


    public String getCashId() {
        return cashId;
    }

    public TicketStatus getTicketStatus() {
        return ticketStatus;
    }

    public int getMAXITEMS() {
        return MAXITEMS;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public ArrayList<Integer> getQuantities() {
        return quantities;
    }

    public ArrayList<String> getMaxPers() {
        return maxPers;
    }

    public int getNumItems() {
        return numItems;
    }

    public void setNumItems(int numItems) {
        this.numItems = numItems;
    }

    public void setClosingDate(String closingDate) {
        this.closingDate = closingDate;
    }

    public String getOpeningDate() {
        return openingDate;
    }

    public String getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(String idTicket) {
        this.idTicket = idTicket;
    }

    public String getClosingDate() {
        return closingDate;
    }

    public void setTicketStatus(TicketStatus ticketStatus) {
        this.ticketStatus = ticketStatus;
    }
}
