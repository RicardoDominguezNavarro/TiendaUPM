package es.upm.etsisi.poo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Locale;


/**
 * This class represents the receipt where the products added by the user are stored. It can contains up to 100 products
 * and can call {@link Catalog} to check the availability of products.
 */
public class Ticket {

    /**
     * Defines the possible states of a ticket in its lifecycle.
     */
    public enum TicketStatus {EMPTY, OPEN, CLOSE}
    /**
     * Unique ID for each ticket.
     */
    private String idTicket;
    /**
     * The ID of the user (client) associated with this ticket.
     */
    private String userId;
    /**
     * The ID of the cashier who opened and manages this ticket.
     */
    private String cashId;
    /**
     * The current status of the ticket (EMPTY, OPEN, or CLOSE).
     */
    private TicketStatus ticketStatus;
    /**
     * The date the ticket was created and opened.
     */
    private String openingDate;
    /**
     * The date the ticket was closed and processed.
     */
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
     */
    public Ticket(String idTicket, String userId, String cashId, String openingDate, Catalog catalog) {
        this.idTicket = idTicket;
        this.userId = userId;
        this.cashId = cashId;
        this.openingDate = openingDate;
        this.catalog = catalog;
        this.ticketStatus = TicketStatus.EMPTY;
        this.products = new ArrayList<>(MAXITEMS);
        this.quantities = new ArrayList<>(MAXITEMS);
        this.maxPers = new ArrayList<>(MAXITEMS);
        this.numItems = 0;
    }


    /**
     * Gets the ID of the cashier associated with this ticket.
     * @return The cashier's ID.
     */
    public String getCashId() {
        return cashId;
    }

    /**
     * Gets the current status of the ticket.
     * @return The {@code TicketStatus} (EMPTY, OPEN, or CLOSE).
     */
    public TicketStatus getTicketStatus() {
        return ticketStatus;
    }

    /**
     * Gets the maximum number of distinct items allowed in the ticket.
     * @return The maximum number of items (100).
     */
    public int getMAXITEMS() {
        return MAXITEMS;
    }

    /**
     * Gets the list of products currently in the ticket.
     * @return The {@code ArrayList} of {@code Product} objects.
     */
    public ArrayList<Product> getProducts() {
        return products;
    }

    /**
     * Gets the list of quantities for each corresponding product in the ticket.
     * @return The {@code ArrayList} of integer quantities.
     */
    public ArrayList<Integer> getQuantities() {
        return quantities;
    }

    /**
     * Gets the list of custom/personalized data associated with each product (e.g., maximum people, personalized text).
     * @return The {@code ArrayList} of custom data strings.
     */
    public ArrayList<String> getMaxPers() {
        return maxPers;
    }


    /**
     * Gets the current number of distinct items in the ticket.
     * @return The count of items.
     */
    public int getNumItems() {
        return numItems;
    }

    /**
     * Sets the number of distinct items in the ticket.
     * @param numItems The new number of items.
     */
    public void setNumItems(int numItems) {
        this.numItems = numItems;
    }

    /**
     * Sets the closing date of the ticket.
     * @param closingDate The date the ticket was closed.
     */
    public void setClosingDate(String closingDate) {
        this.closingDate = closingDate;
    }

    /**
     * Gets the opening date of the ticket.
     * @return The ticket opening date string.
     */
    public String getOpeningDate() {
        return openingDate;
    }

    /**
     * @return the ticket ID
     */
    public String getIdTicket() {
        return idTicket;
    }

    /**
     * Sets a new ticket ID.
     * @param idTicket The new unique identifier for the ticket.
     */
    public void setIdTicket(String idTicket) {
        this.idTicket = idTicket;
    }

    /**
     * Gets the closing date of the ticket.
     * @return The ticket closing date string.
     */
    public String getClosingDate() {
        return closingDate;
    }

    /**
     * Sets the status of the ticket.
     * @param ticketStatus The new {@code TicketStatus}.
     */
    public void setTicketStatus(TicketStatus ticketStatus) {
        this.ticketStatus = ticketStatus;
    }
}
