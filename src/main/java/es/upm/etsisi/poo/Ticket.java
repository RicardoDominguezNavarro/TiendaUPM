package es.upm.etsisi.poo;


import java.time.LocalDateTime;
import java.util.ArrayList;


/**
 * This class represents the receipt where the products added by the user are stored. It can contains up to 100 products
 * and can call {@link Catalog} to check the availability of products.
 */
public class Ticket {


    /**
     * Unique ID for each ticket.
     */
    public enum TicketStatus {VACIO, ACTIVO, CERRADO}

    private String idTicket;
    private String userId;
    private String cashId;
    private TicketStatus ticketStatus;
    private String openingDate;
    private String closingDate;


    /**
     * Maximum number of products that can be included in a ticket
     */
    private final int MAXITEMS = 100;


    /**
     * Array: list of products added to the ticket
     */
    private ArrayList<Product> products;
    /**
     * Array: list of quantities to each product of the ticket
     */
    private ArrayList<Integer> quantities;
    /**
     * Array: list of quantities to each product of the ticket
     */
    private ArrayList<String> maxPers;


    /**
     * number of items in the ticket
     */
    private int numItems;
    /**
     * Reference to the catalog that contains all available products
     */
    private Catalog catalog;

    /**
     * Builder that create a new ticket
     * Initially, the ticket is empty
     *
     * @param catalog Contains the available products
     */
    public Ticket(String idTicket, String userId, String cashId, String openingDate,
                  Catalog catalog) {
        this.idTicket = idTicket;
        this.userId = userId;
        this.cashId = cashId;
        this.openingDate = openingDate;
        this.catalog = catalog;
        this.ticketStatus = TicketStatus.ACTIVO;
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

    /**
     * @return the ticket ID
     */
    public String getIdTicket() {
        return idTicket;
    }


    public void setId_ticket(String id_ticket) {
        this.idTicket = id_ticket;
    }


}
