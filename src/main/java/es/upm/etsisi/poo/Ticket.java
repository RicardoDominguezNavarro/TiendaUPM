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
    private LocalDateTime openingDate;
    private LocalDateTime closingDate;


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
    public Ticket(String idTicket, String userId, String cashId, LocalDateTime openingDate,
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

    public String dateToIdFormat() {
        int year = LocalDateTime.now().getYear();
        int longitud = String.valueOf(year).length();
        int twoDigitYear = (int) (year % Math.pow(10, longitud - 1));
        String id_year = String.valueOf(twoDigitYear);
        String id_month = String.valueOf(LocalDateTime.now().getMonthValue());
        String id_day = String.valueOf(LocalDateTime.now().getDayOfMonth());
        String id_hour = String.valueOf(LocalDateTime.now().getHour());
        String id_minute = String.valueOf(LocalDateTime.now().getMinute());
        String date = id_year + "-" + id_month + "-" + id_day + "-" + id_hour + ":" + id_minute;
        return date;
    }

    public int generateId (){
        boolean freeId = false;
        int id = 0;
        while (freeId == false){
            int weight = 10000;
            for (int i = 0; i < 5; i++) {
                id += (int) (Math.random() * 10) * weight;
                weight /= 10;
            }

        }
        return id;
    }

    //añadir el set para que se ponga el id automatico o el q t ponga el ususiario
}
