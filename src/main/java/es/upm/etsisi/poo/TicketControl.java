package es.upm.etsisi.poo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;


public class TicketControl {
    private final ArrayList<Ticket> tickets;
    private final Catalog catalog;
    private final ArrayList<User>  users;


    public TicketControl(Catalog catalog) {
        this.tickets = new ArrayList<>();
        this.catalog = catalog;
        this.users = new ArrayList<>();
    }

    public User findUserById(String id) {
        for(User user : users) {
            if(user.getId().equalsIgnoreCase(id)) {
                return user;
            }
        }
        return null;
    }

    public void addClient(String name, String DNI, String email, String cashId){
        if(findUserById(DNI) != null) {
            System.out.println("Client with id: " + DNI + " already exists");
            return;
        }
        User creator = findUserById(cashId);
        if(creator == null) {
            System.out.println("Cashier with Id " + cashId + " doesn't exist");
            return;
        }
        try{
            Client newClient = new Client(name, DNI, email, cashId);
            System.out.println("client add " + newClient.getName() + " '" + newClient.getId() + "' " + newClient.getEmail() + " " + cashId);
            users.add(newClient);
            System.out.println(newClient.toString());
            System.out.println("client add: ok");
        }catch(IllegalArgumentException e){
            System.out.println("Error creating client " + e.getMessage());
        }

    }

    public void addCashier(String id, String name, String email){
        if(id != null) {
            if(findUserById(id) != null) {
                System.out.println("Cashier with id: " + id + " already exists");
                return;
            }
        }else{
            do{
                id = Cash.generateId();
            }while (findUserById(id) != null);
        }
        try{
            Cash newCashier = new Cash(id, name, email);
            System.out.println("cash add " + newCashier.getId() + " '" + newCashier.getName() + "' " + newCashier.getEmail());
            users.add(newCashier);
            System.out.println(newCashier.toString());
            System.out.println("cash add: ok");
        }catch(IllegalArgumentException e){
            System.out.println("Error creating cashier " + e.getMessage());
        }
    }

    public void removeClient(String id){
        User user = findUserById(id);
        if(user != null) {
            users.remove(user);
            System.out.println("Client remove " + user.getId());
            System.out.println("client remove: ok");
        }else{
            System.out.println("Client with id: " + id + " doesn't exist");
        }
    }

    public void removeCashier(String id){
        User cashier = findUserById(id);
        if(cashier != null) {
            removeTicketsByCashier(id);
            users.remove(cashier);
            System.out.println("Cash remove " + cashier.getId());
            System.out.println("cash remove: ok");
        }else{
            System.out.println("Cashier with id: " + id + " doesn't exist");
        }
    }

    public void listClients(){
        System.out.println("client list");
        System.out.println("Client:");
        ArrayList<User> orderList = new ArrayList<>();
        for(User user : users) {
            if(user instanceof Client) {
                orderList.add(user);
            }
        }
        Collections.sort(orderList, new Comparator<User>() {
            @Override
            public int compare(User user1, User user2) {
                return user1.getName().compareToIgnoreCase(user2.getName());
            }
        });
        for(User user : orderList) {
            System.out.println(user.toString());
        }
        System.out.println("Client list: ok");
    }

    public void listCashiers(){
        System.out.println("cash list");
        System.out.println("Cash:");
        ArrayList<User> orderList = new ArrayList<>();
        for(User user : users) {
            if(user instanceof Cash) {
                orderList.add(user);
            }
        }
        Collections.sort(orderList, new Comparator<User>() {
            @Override
            public int compare(User user1, User user2) {
                return user1.getName().compareToIgnoreCase(user2.getName());
            }
        });
        for(User user : orderList) {
            System.out.println(user.toString());
        }


        System.out.println("Cash list: ok");
    }



    public void listCashierTickets(String cashId){
        User user = findUserById(cashId);
        if(user instanceof Cash) {
            System.out.println("cash tickets " +  cashId);
            System.out.println("Tickets:");
            Cash cashier = (Cash) user;
            for(String ticketId : cashier.getCreatedTicketIds()){
                Ticket ticket = findTicketById(ticketId);
                if(ticket != null) {
                    System.out.println("  " +dateToIdFormat() + "-"+ ticket.getIdTicket() + "->" + ticket.getTicketStatus());
                }
            }
            System.out.println("cash tickets: ok");
        }else{
            System.out.println("Cashier with id: " + cashId + " doesn't exist");
        }
        /**if(user == null) {
            System.out.println("Cashier with id: " + cashId + " doesn't exist");
            return;
        }
        if (!(user instanceof Cash)) {
            System.out.println("User with id: " + cashId + " is not a cashier.");
            return;
        }
        Cash cashier = (Cash) user;
        System.out.println("Tickets: ");
        if (cashier.getCreatedTicketIds() == null || cashier.getCreatedTicketIds().isEmpty()) {
            System.out.println("No tickets found for cashier " + cashId);
        } else {
            for(String ticketId: cashier.getCreatedTicketIds()){
                System.out.println("Checking ticketId: " + ticketId);
                Ticket ticket = findTicketById(ticketId);
                if(ticket != null) {
                    System.out.println("  " + ticket.getIdTicket() + "->" + ticket.getTicketStatus());
                } else {
                    System.out.println("Ticket with id " + ticketId + " not found.");
                }
            }
        }
        System.out.println("cash tickets: ok");*/

    }



    public void newTicket(String userId, String cashId, String ticketId) {
        if(findUserById(userId) == null) {
            System.out.println("User with id: " + userId + " doesn't exist");
            return;
        }
        User cashUser = findUserById(cashId);
        if(cashUser == null || !(cashUser instanceof Cash) ) {
            System.out.println("Cashier with id: " + cashId + " doesn't exist");
            return;
        }
        String date;
        String idNum;
        if(ticketId == null) {
            //ticketId = dateToIdFormat() + "-" + generateId();
            date = dateToIdFormat();
            idNum = String.valueOf(generateId());
        }else {
            idNum = ticketId;
            date = dateToIdFormat();

        }
        Ticket ticket = new Ticket(idNum, userId, cashId, date, catalog);
        ticket.setTicketStatus(Ticket.TicketStatus.EMPTY);
        System.out.println("ticket new " + idNum + " " + cashId + " " + userId);
        tickets.add(ticket);

        Cash cashier = (Cash) cashUser;
        cashier.getCreatedTicketIds().add(ticket.getIdTicket());

        System.out.println(print(ticket));
        System.out.println("ticket new: ok");
    }

    public Ticket findTicketById(String ticketId) {
        for (Ticket ticket : tickets) {
            if (ticket.getIdTicket().equals(ticketId)) {
                return ticket;
            }
        }
        System.out.println("Ticket not found");
        return null;
    }


    /**
     * Adds a new product to the ticket based on its product ID and quantity.
     * If the product already exists in the ticket, its quantity is increased.
     *
     * @param prodId   the product ID to add
     * @param quantity the number of units to add
     */
/*
    public void addProductToTicket(String ticketId, String cashId, int prodId, int quantity, ArrayList<String> personalized) {
        Ticket ticket = findTicketById(ticketId);
        boolean valid = true;
        if (ticket == null) {
            System.out.println("The ticketId doesn´t exist");
            return;
        } else {
            if (!ticket.getCashId().equals(cashId) || ticket.getTicketStatus() == Ticket.TicketStatus.CLOSE) {
                System.out.println("unauthorized or closed ticket");
                valid = false;
            }
            //los eventos cuenta como 1 solo item o segun las personas que sean??
            if (valid && quantity <= 0 || ticket.getNumItems() + quantity > ticket.getMAXITEMS()) {
                System.out.println("Invalid amount or full ticket");
                valid = false;
            } else if (ticket.getNumItems() + quantity == ticket.getMAXITEMS()) {
                System.out.println("The ticket is full");
            }

            Product product = null;
            if (valid) {
                product = catalog.getProductId(prodId);
                if (product == null) {
                    System.out.println("product not found");
                    valid = false;
                }
            }

            if (valid && product instanceof PersonalizedProduct) {
                PersonalizedProduct personalizedProduct = (PersonalizedProduct) product;
                if (personalizedProduct == null) {
                    personalized = new ArrayList<>();
                }
                if (personalized.size() > personalizedProduct.getMaxText()) {
                    System.out.println("Exceeded max number of personalized texts");
                    valid = false;
                }
                for (int i = 0; i < personalized.size(); i++) {
                    String text = personalized.get(i);
                    if (!personalizedProduct.getPersonalizationList().contains(text)) {
                        System.out.println("Personalized text not allowed");
                        valid = false;
                        break;
                    }
                }
            }
            if (valid && product instanceof Events) {
                Events event = (Events) product;
                ArrayList<Product> products = ticket.getProducts();
                for (int i = 0; i < products.size(); i++) {
                    Product prod = products.get(i);
                    if (prod instanceof Events && prod.getId_product() == prodId) {
                        System.out.println("Event already added");
                        valid = false;
                        break;
                    }
                }
                if (valid && !event.validDate()) {
                    System.out.println("Event must be planned at least " + event.getEventType().getMinPlanningHours() + "hours ahead");
                    valid = false;
                }
            }

            if (valid) {
                if (product instanceof Events) {
                    product = new Events((Events) product, quantity);
                }else if (product instanceof PersonalizedProduct) {// 2. Si es Personalizado, creamos la copia para fijar los textos
                    product = new PersonalizedProduct((PersonalizedProduct) product, personalized);
                }

                ArrayList<Product> products = ticket.getProducts();
                ArrayList<Integer> quantities = ticket.getQuantities();
                ArrayList<String> personalizedText = ticket.getMaxPers();

                boolean found = false;
                for (int i = 0; i < products.size(); i++) {
                    if (products.get(i).getId_product() == prodId && personalizedText.get(i).equals(personalized)) {
                        quantities.set(i, quantities.get(i) + quantity);
                        ticket.setNumItems(ticket.getNumItems() + quantity);
                        found = true;
                    }
                }
                if (!found) {

                    int index = 0;
                    while (index < products.size() && products.get(index).getName().compareToIgnoreCase(product.getName()) < 0) {
                        index++;
                    }
                    products.add(index, product);
                    quantities.add(index, quantity);
                    personalizedText.add(index, String.valueOf(personalized)); //mirar esto porq no me cuadra
                    ticket.setNumItems(ticket.getNumItems() + quantity);
                }
                System.out.println(print(ticket));
                System.out.println("ticket add: ok");
            }
        }
    }
*/

    public void addProductToTicket(String ticketId, String cashId, int prodId, int quantity, ArrayList<String> personalized) {
        Ticket ticket = findTicketById(ticketId);
        boolean valid = true;
        if(ticket == null) {
            System.out.println("Ticket with id: " + ticketId + " doesn't exist");
            valid = false;
            return;
        }
        if (!ticket.getCashId().equals(cashId) || ticket.getTicketStatus() == Ticket.TicketStatus.CLOSE) {
            System.out.println("unauthorized or closed ticket");
            valid = false;
            return;
        }
        if (ticket.getNumItems() + quantity == ticket.getMAXITEMS()) {
            System.out.println("The ticket is full");
            valid = false;
            return;
        }
        Product product = catalog.getProductId(prodId);
        if (product == null) {
            System.out.println("Product with id: " + prodId + " doesn't exist");
            valid = false;
            return;
        }
        if(product instanceof PersonalizedProduct) {
            PersonalizedProduct personalizedProduct = (PersonalizedProduct) product;
            if(personalized.size() > personalizedProduct.getMaxText()) {
                System.out.println("Exceeded maximum number of personalized texts");
                valid = false;
                return;
            }
        }
        if(product instanceof Events) {
            Events event = (Events) product;
            for(Product prod : ticket.getProducts()) {
                if(prod.getId_product() == prodId) {
                    System.out.println("Event already added");
                    valid = false;
                    return;
                }
            }
            if(!event.validDate()) {
                System.out.println("Date not valid");
                valid = false;
                return;
            }
        }

        if(valid) {
            Product productToAdd = product;
            if(product instanceof Events) {
                productToAdd = new Events((Events) product, quantity);
            }else if(product instanceof PersonalizedProduct) {
                productToAdd = new PersonalizedProduct((PersonalizedProduct) product, personalized);
            }
            boolean found = false;
            ArrayList<Product> products = ticket.getProducts();
            ArrayList<Integer> quantities = ticket.getQuantities();

            if(!(productToAdd instanceof Events) && !(productToAdd instanceof PersonalizedProduct)) {
                for(int i = 0; i < products.size(); i++) {
                    if(products.get(i).getId_product() == prodId) {
                        quantities.set(i, quantities.get(i) + quantity);
                        found = true;
                        break;
                    }
                }
            }
            if(!found) {
                int index = 0;
                while(index < products.size()&& products.get(index).getName().compareToIgnoreCase(productToAdd.getName()) < 0) {
                    index++;
                }
                products.add(index, productToAdd);
                quantities.add(index, quantity);

            }
            ticket.setNumItems(ticket.getNumItems() + quantity);
            if (ticket.getNumItems() > 0 && ticket.getTicketStatus() == Ticket.TicketStatus.EMPTY) {
                ticket.setTicketStatus(Ticket.TicketStatus.OPEN);
            }
            System.out.println(print(ticket));
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
        if (ticket == null) {
            System.out.println("The ticket doesn´t exist");
        } else {
            if (ticket.getCashId().equals(cashId)) {
                if (ticket.getTicketStatus() == Ticket.TicketStatus.OPEN) {

                    ArrayList<Product> products = ticket.getProducts();
                    ArrayList<Integer> quantities = ticket.getQuantities();
                    //Habria que borrar del array de personalizados tambien los textos asociados

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
                        System.out.println("ticket remove " + ticketId + " " + cashId + " " + prodId);
                        System.out.println(print(ticket));
                        System.out.println("ticket remove: ok");
                    } else {
                        System.out.println("There is no product in the ticket.");
                    }
                } else if (ticket.getTicketStatus() == Ticket.TicketStatus.CLOSE) {
                    System.out.println("This ticket cannot be modified.");
                } else {
                    System.out.println("This ticket is empty.");
                }
            } else {
                System.out.println("The cashier didn't create this ticket.");
            }
        }

    }

    //Si se elimina un cajero se elimina los tickets creados por el
    public void removeTicketsByCashier(String cashierId) {
        for (Ticket ticket : tickets) {
            if (ticket.getCashId().equals(cashierId)) {
                tickets.remove(ticket);
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
        if(product.getCategory() == null) {
            return 0.0;
        }
        if (amount >= 2) {
            double discount = product.getCategory().getDiscount();
            result = product.getPrice() * discount;
        }
        return result;
    }

    public void printTicket(String ticketId, String cashId) {
        Ticket ticket = findTicketById(ticketId);
        if (ticket == null) {
            System.out.println("The ticket doesn´t exist");
        } else {
            if (ticket.getCashId().equals(cashId)) {
                if (ticket.getTicketStatus() != Ticket.TicketStatus.CLOSE) {
                    String closingDate = dateToIdFormat();
                    ticket.setClosingDate(closingDate);
                    ticket.setTicketStatus(Ticket.TicketStatus.CLOSE);
                    System.out.println(print(ticket));
                    System.out.println("ticket print: ok");

                }
            } else {
                System.out.println("The cashier didn't create this ticket.");
            }
        }
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
        System.out.println("ticket print " + ticket.getIdTicket() + " " + ticket.getCashId());
        sb.append("Ticket : ").append(ticket.getIdTicket());
        if (ticket.getTicketStatus() == Ticket.TicketStatus.CLOSE) {
            sb.append("-").append(ticket.getClosingDate()).append("\n");
        } else {
            sb.append("\n");
        }
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            int amount = quantities.get(i);
            double discount = 0.0;
            for (int j = 0; j < amount; j++) {
                if (product instanceof Events) {
                    Events event = (Events) product;
                    if (!event.validDate()) {
                        return "The ticket cannot be closed because the " + event.getName() + " event has passed its deadline.";
                    }
                    sb.append(event.toString()).append("\n");

                } else if (product instanceof PersonalizedProduct) {
                    PersonalizedProduct personalizedProd = (PersonalizedProduct) product;
                    sb.append(personalizedProd.toString());
                    discount = calculateDiscount(product, amount);
                    if (discount > 0) {
                        sb.append(" **discount -").append(String.format(Locale.US, "%.2f", discount)).append("\n");
                    } else {
                        sb.append("\n");
                    }
                } else {
                    discount = calculateDiscount(product, amount);
                    sb.append(product.toString());
                    if (discount > 0) {
                        sb.append(" **discount -").append(String.format(Locale.US, "%.2f", discount)).append("\n");
                    } else {
                        sb.append("\n");
                    }
                }
                totalPrice += product.getPrice();
                totalDiscount += discount;
            }
        }
        double finalPrice = totalPrice - totalDiscount;
        sb.append("Total price: ").append(String.format(Locale.US, "%.2f", totalPrice)).append("\n");
        sb.append("Total discount: ").append(String.format(Locale.US, "%.2f", totalDiscount)).append("\n");
        sb.append("Final price: ").append(String.format(Locale.US, "%.2f", finalPrice));
        return sb.toString();
    }

    public String listTicket() {
        StringBuilder sb = new StringBuilder();
        System.out.println("ticket list");
        sb.append("Ticket List:\n");
        for (Ticket ticket : tickets) {
            sb.append(" ");
            switch (ticket.getTicketStatus()) {
                case EMPTY:
                    sb.append(ticket.getOpeningDate()).append("-").append(ticket.getIdTicket());
                    break;
                case OPEN:
                    sb.append(ticket.getIdTicket());
                    break;
                case CLOSE:
                    sb.append(ticket.getIdTicket()).append("-").append(ticket.getClosingDate());
                    break;
            }
            sb.append(" - ").append(ticket.getTicketStatus()).append("\n");

        }
        sb.append("ticket list: ok\n");

        return sb.toString();
    }

    public String dateToIdFormat() {
        int year = LocalDateTime.now().getYear();
        int longitud = String.valueOf(year).length();
        int twoDigitYear = (int) (year % Math.pow(10, longitud - 1));
        String id_year = String.format("%02d", twoDigitYear);
        String id_month = String.format("%02d", LocalDateTime.now().getMonthValue());
        String id_day = String.format("%02d", LocalDateTime.now().getDayOfMonth());
        String id_hour = String.format("%02d", LocalDateTime.now().getHour());
        String id_minute = String.format("%02d", LocalDateTime.now().getMinute());
        String date = id_year + "-" + id_month + "-" + id_day + "-" + id_hour + ":" + id_minute;
        return date;
    }

    public int generateId() {
        int id = 0;
        int weight = 10000;
        for (int i = 0; i < 5; i++) {
            id += (int) (Math.random() * 10) * weight;
            weight /= 10;
        }
        return id;
        //se comprueba si ya existe a la hora de llamar a la función
    }
}
