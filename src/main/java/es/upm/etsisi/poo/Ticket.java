package es.upm.etsisi.poo;

import java.util.ArrayList;
import java.util.stream.IntStream;
import java.io.Serializable;

public class Ticket implements Serializable {

    protected TicketPrinter ticketPrinter;
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
    private static final long serialVersionUID = 1L;

    public Ticket(String idTicket, String userId, String cashId, String openingDate, Catalog catalog, String tipoTicket){
        this.idTicket = idTicket;
        this.userId = userId;
        this.cashId = cashId;
        this.openingDate = openingDate;
        this.ticketStatus = TicketStatus.EMPTY;
        this.products = new ArrayList<>(MAXITEMS);
        this.quantities = new ArrayList<>(MAXITEMS);
        this.maxPers = new ArrayList<>(MAXITEMS);
        this.numItems = 0;
        boolean isCompany = Company.checkNIF(userId);
        setPrinter(tipoTicket, isCompany);
        //El nif de una empresa empieza siempre por una letra y suele ser la A
    }



   /* public Ticket(String idTicket, String userId, String cashId, String openingDate, Catalog catalog) {
        this.idTicket = idTicket;
        this.userId = userId;
        this.cashId = cashId;
        this.openingDate = openingDate;
        this.ticketStatus = TicketStatus.EMPTY;
        this.products = new ArrayList<>(MAXITEMS);
        this.quantities = new ArrayList<>(MAXITEMS);
        this.maxPers = new ArrayList<>(MAXITEMS);
        this.numItems = 0;
    }*/

    private void setPrinter(String tipo, boolean itsACompany) {
        if (!itsACompany) {
            ticketPrinter = new CommonTicket();
        } else if ("-s".equals(tipo)) {
            ticketPrinter = new TicketCompanyServices();
        } else {
            ticketPrinter = new TicketCompanyCombined();
        }
    }
    public boolean addProduct(Product product, int quantity, ArrayList<String> personalized) {
        if (this.ticketStatus == TicketStatus.CLOSE) {
            System.out.println("unauthorized or closed ticket");
            return false;
        }
        if (this.numItems + quantity > MAXITEMS) {
            System.out.println("The ticket is full");
            return false;
        }

        if (!acceptsProduct(product)) {
            System.out.println("Invalid product type for this ticket");
            return false;
        }

        if (product instanceof PersonalizedProduct) {
            PersonalizedProduct personalizedProduct = (PersonalizedProduct) product;
            if (personalized != null && personalized.size() > personalizedProduct.getMaxText()) {
                System.out.println("Exceeded maximum number of personalized texts");
                return false;
            }
        }

        if (product instanceof Events) {
            Events event = (Events) product;
            for (Product prod : this.products) { //comprobar si existe el evento en el ticket
                if (prod.getId_product().equals(product.getId_product())) {
                    System.out.println("Event already added");
                    return false;
                }
            }
            if (!event.validDate()) {
                System.out.println("Date not valid");
                return false;
            }
        }

        Product productToAdd = product;
        if (product instanceof Events) {
            productToAdd = new Events((Events) product, quantity);
        } else if (product instanceof PersonalizedProduct) {
            productToAdd = new PersonalizedProduct((PersonalizedProduct) product, personalized);
        }

        boolean found = false;
        if (!(productToAdd instanceof Events) && !(productToAdd instanceof PersonalizedProduct)) {
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getId_product().equals(product.getId_product())) {
                    quantities.set(i, quantities.get(i) + quantity);
                    found = true;
                    break;
                }
            }
        }

        if (!found) {
            String nameToAdd = productToAdd.getName();
            int index = IntStream.range(0, products.size())
                    .filter(i -> products.get(i).getName().compareToIgnoreCase(nameToAdd) > 0)
                    .findFirst()
                    .orElse(products.size());

            products.add(index, productToAdd);
            quantities.add(index, quantity);
        }

        this.numItems += quantity;
        if (this.numItems > 0 && this.ticketStatus == TicketStatus.EMPTY) {
            this.ticketStatus = TicketStatus.OPEN;
        }

        System.out.println("ticket add " + this.idTicket + " " + this.cashId + " " + product.getId_product() + " " + quantity);
        System.out.println("ticket add: ok");
        return true;
    }

    public boolean removeProduct(String prodId, String cashIdRequest) {
        if (!this.cashId.equals(cashIdRequest)) {
            System.out.println("The cashier didn't create this ticket.");
            return false;
        }

        if (this.ticketStatus == TicketStatus.CLOSE) {
            System.out.println("This ticket cannot be modified.");
            return false;
        }

        if (this.ticketStatus == TicketStatus.EMPTY) {
            System.out.println("This ticket is empty.");
            return false;
        }

        boolean found = false;
        for (int i = products.size() - 1; i >= 0; i--) {
            if (products.get(i).getId_product().equals(prodId)) {
                int quantity = quantities.get(i);
                products.remove(i);
                quantities.remove(i);
                this.numItems -= quantity;
                found = true;
            }
        }

        if (found) {
            if (this.numItems == 0) {
                this.ticketStatus = TicketStatus.EMPTY;
            }
            System.out.println("ticket remove " + this.idTicket + " " + this.cashId + " " + prodId);
            System.out.println(print());
            System.out.println("ticket remove: ok");
            return true;
        } else {
            System.out.println("There is no product in the ticket.");
            return false;
        }
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

    public double calculateDiscount(Product product, int amount) {
        double result = 0.0;
        if (product.getCategory() == null) {
            return 0.0;
        }
        double discount = product.getCategory().getDiscount();
        if (amount >= 2) {
            result = product.getPrice() * discount;
        }
        return result;
    }

    public String print(){
        return ticketPrinter.print(this);
    }

    public boolean close() {
        return ticketPrinter.close(this);
    }

    public boolean acceptsProduct(Product product) {
        return ticketPrinter.acceptsProduct(product);
    }

}
