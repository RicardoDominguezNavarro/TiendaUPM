package es.upm.etsisi.poo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Locale;


public class TicketControl {
    private final ArrayList<Ticket> tickets;
    private final Catalog catalog;

    public TicketControl(Catalog catalog) {
        this.tickets = new ArrayList<>();
        this.catalog = catalog;
    }
    //no sé si deberia añadri algo del id del cajero
    //organizar ticket y ticket control


    public void newTicket( String userId, String cashId, String ticketId) {
        Ticket ticket = new Ticket(ticketId,userId,cashId,dateToIdFormat(),catalog);
        tickets.add(ticket);

        System.out.println("ticket new: ok");
    }

    public Ticket findTicketById (String ticketId){
        for (int i = 0; i < tickets.size(); i++) {
            Ticket ticket = tickets.get(i);
            if (ticket.getIdTicket().equals(ticketId)) {
                return ticket;
            }else {
                System.out.println("Ticket not found");
            }
        }
        return null;
    }


    /**
     * Adds a new product to the ticket based on its product ID and quantity.
     * If the product already exists in the ticket, its quantity is increased.
     *
     * @param prodId   the product ID to add
     * @param quantity the number of units to add
     */
    public void addProductToTicket(String ticketId, String cashId, int prodId, int quantity, ArrayList<String> personalized) {
        //en vez de arrayList List solo???
        Ticket ticket = findTicketById (ticketId);
        boolean valid = true;

        if (!ticket.getCashId().equals(cashId) || ticket.getTicketStatus() != Ticket.TicketStatus.ACTIVO) {
            System.out.println("unauthorized or closed ticket");
            valid = false;
        }
        if (valid && quantity < 0 || ticket.getNumItems() + quantity > ticket.getMAXITEMS()) {
            System.out.println("Invalid amount or full ticket");
            valid = false;
        }else if (ticket.getNumItems() + quantity == ticket.getMAXITEMS()) {
            System.out.println("The ticket is full");
        }

        Product product = null;
        if (valid){
            product = catalog.getProductId(prodId);
            if (product == null){
                System.out.println("product not found");
                valid = false;
            }
        }
        //poner las excepciones de personalized y eventos


        if (valid) {

            ArrayList<Product> products = ticket.getProducts();
            ArrayList<Integer> quantities = ticket.getQuantities();
            ArrayList<String> personalizedText = ticket.getMaxPers();

            boolean found = false;
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getId_product() == prodId && personalizedText.get(i).equals(personalized)) {
                    quantities.set(i, quantities.get(i) + quantity);
                    ticket.setNumItems(ticket.getNumItems() + quantity) ;
                    found = true;
                }
            }
            if (!found) {

                int index = 0;
                while (index < products.size() && products.get(index).getName().compareToIgnoreCase(product.getName()) < 0){
                    index++;
                }
                products.add(index,product);
                quantities.add(index, quantity);
                personalizedText.add(index, String.valueOf(personalized)); //mirar esto porq no me cuadra
                ticket.setNumItems(ticket.getNumItems() + quantity) ;
            }
            //System.out.println(printTicket(ticketId,cashId));
            System.out.println("ticket add: ok");
        }
    }


    /**
     * Remove a product from the ticket using the ID
     * If the product exists, it is removed along with its quantity.
     *
     * @param prodId the ID of the product to remove
     */

    public void removeProduct(String ticketId, String cashId, int prodId) {
        Ticket ticket = findTicketById(ticketId);
        boolean valid = true;
        if (!ticket.getCashId().equals(cashId)){
            System.out.println("");
        }
        if (ticket.getTicketStatus() != Ticket.TicketStatus.ACTIVO) {
            System.out.println("The ticket is closed");
            valid = false;
        }
        ArrayList<Product> products = ticket.getProducts();
        ArrayList<Integer> quantities = ticket.getQuantities();
        //Habria que borrar del array de personalizados tambien los textos asociados
        if (valid) {

            boolean found = false;
            for (int i = products.size() - 1; i >= 0; i--) {
                if (products.get(i).getId_product() == prodId) {
                    int quantity = quantities.get(i);
                    products.remove(i);
                    quantities.remove(i);
                    ticket.setNumItems(ticket.getNumItems() - quantity);
                    found = true;
                }
            }
            if (found) {
                //System.out.println(print());
                System.out.println("prod remove: ok");
            } else {
                System.out.println("There is no product in the ticket");
            }
        }
    }


    /**
     * Calculate a discount for a specific product depending on its category and quantity.
     * The discount is applied only if the quantity is two or more.
     *
     * @param product the product for which the discount is calculated
     * @param amount  the quantity of the product
     * @return the discount value
     */
    public double calculateDiscount(Product product, int amount) {
        double result = 0.0;
        if (amount >= 2) {
            double discount = product.getCategory().getDiscount();
            result = product.getPrice() * discount;
        }
        return result;
    }



    /*
    public double getDiscount(Category category) {
        double percent = 0.0;
        switch (category) {
            case CLOTHES:
                percent = 0.07;
                break;
            case BOOK:
                percent = 0.1;
                break;
            case MERCH:
                percent = 0.0;
                break;
            case STATIONERY:
                percent = 0.05;
                break;
            case ELECTRONICS:
                percent = 0.03;
                break;
            default:
                System.out.println("Please select a valid option");
        }
        return percent;
    }

     */
    public void printTicket(String ticketId, String cashId) {
        Ticket ticket = findTicketById(ticketId);

        if (!ticket.getCashId().equals(cashId)) {
            System.out.println("");
            return;
        }
        if (ticket.getTicketStatus() == Ticket.TicketStatus.CERRADO) {
            System.out.println("The ticket is closed");
        }
        System.out.println(print(ticket));
    }

    /**
     * @return a formatted string representing the ticket, showing the products,
     * their discounts and the total price.
     */
    private String print(Ticket ticket) {
        StringBuilder sb = new StringBuilder();
        double totalPrice = 0.0;
        double totalDiscount = 0.0;

        ArrayList<Product> products = ticket.getProducts();
        ArrayList<Integer> quantities = ticket.getQuantities();

        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            int amount = quantities.get(i);

            for (int j = 0; j < amount; j++) {
                double discount = calculateDiscount(product, amount);
                sb.append(product.toString());
                if (discount > 0) {
                    sb.append(" **discount -").append(String.format(Locale.US,"%.2f", discount)).append("\n");
                } else {
                    sb.append("\n");
                }
                totalPrice += product.getPrice();
                totalDiscount += discount;
            }
        }
        double finalPrice = totalPrice - totalDiscount;
        sb.append("Total price: ").append(String.format(Locale.US,"%.2f", totalPrice)).append("\n");
        sb.append("Total discount: ").append(String.format(Locale.US,"%.2f", totalDiscount)).append("\n");
        sb.append("Final price: ").append(String.format(Locale.US,"%.2f", finalPrice));

        return sb.toString();
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
        int id = 0;
        int weight = 10000;
        for (int i = 0; i < 5; i++) {
            id += (int) (Math.random() * 10) * weight;
            weight /= 10;
        }
        return id;
        //se comprueba si ya existe a la hora de llamar a la funcion
    }
}
