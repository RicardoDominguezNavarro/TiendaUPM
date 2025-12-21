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

    private Catalog catalog;

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
