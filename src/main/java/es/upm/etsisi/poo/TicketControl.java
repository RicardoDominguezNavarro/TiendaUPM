package es.upm.etsisi.poo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;


public class TicketControl {

    private final ArrayList<Ticket> tickets;

    private final Catalog catalog;

    private final ArrayList<User> users;



    public TicketControl(Catalog catalog) {
        this.tickets = new ArrayList<>();
        this.catalog = catalog;
        this.users = new ArrayList<>();
    }


    public User findUserById(String id) {
        for (User user : users) {
            if (user.getId().equalsIgnoreCase(id)) {
                return user;
            }
        }
        return null;
    }


    public void addClient(String name, String DNI, String email, String cashId) {
        if (findUserById(DNI) != null) {
            System.out.println("Client with id: " + DNI + " already exists");
            return;
        }
        User creator = findUserById(cashId);
        if (creator == null) {
            System.out.println("Cashier with Id " + cashId + " doesn't exist");
            return;
        }
        try {
            Client newClient = new Client(name, DNI, email, cashId);
            System.out.println("client add " + newClient.getName() + " '" + newClient.getId() + "' " + newClient.getEmail() + " " + cashId);
            users.add(newClient);
            System.out.println(newClient.toString());
            System.out.println("client add: ok");
        } catch (IllegalArgumentException e) {
            System.out.println("Error creating client " + e.getMessage());
        }

    }


    public void addCashier(String id, String name, String email) {
        if (id != null) {
            if (findUserById(id) != null) {
                System.out.println("Cashier with id: " + id + " already exists");
                return;
            }
        } else {
            do {
                id = Cash.generateId();
            } while (findUserById(id) != null);
        }
        try {
            Cash newCashier = new Cash(id, name, email);
            System.out.println("cash add " + newCashier.getId() + " '" + newCashier.getName() + "' " + newCashier.getEmail());
            users.add(newCashier);
            System.out.println(newCashier.toString());
            System.out.println("cash add: ok");
        } catch (IllegalArgumentException e) {
            System.out.println("Error creating cashier " + e.getMessage());
        }
    }


    public void removeClient(String id) {
        User user = findUserById(id);
        if (user != null) {
            users.remove(user);
            System.out.println("Client remove " + user.getId());
            System.out.println("client remove: ok");
        } else {
            System.out.println("Client with id: " + id + " doesn't exist");
        }
    }


    public void removeCashier(String id) {
        User cashier = findUserById(id);
        if (cashier != null) {
            removeTicketsByCashier(id);
            users.remove(cashier);
            System.out.println("Cash remove " + cashier.getId());
            System.out.println("cash remove: ok");
        } else {
            System.out.println("Cashier with id: " + id + " doesn't exist");
        }
    }

    public void listClients() {
        System.out.println("client list");
        System.out.println("Client:");
        ArrayList<User> orderList = new ArrayList<>();
        for (User user : users) {
            if (user instanceof Client) {
                orderList.add(user);
            }
        }
        Collections.sort(orderList, new Comparator<User>() {
            @Override
            public int compare(User user1, User user2) {
                return user1.getName().compareToIgnoreCase(user2.getName());
            }
        });
        for (User user : orderList) {
            System.out.println(user.toString());
        }
        System.out.println("Client list: ok");
    }

    public void listCashiers() {
        System.out.println("cash list");
        System.out.println("Cash:");
        ArrayList<User> orderList = new ArrayList<>();
        for (User user : users) {
            if (user instanceof Cash) {
                orderList.add(user);
            }
        }
        Collections.sort(orderList, new Comparator<User>() {
            @Override
            public int compare(User user1, User user2) {
                return user1.getName().compareToIgnoreCase(user2.getName());
            }
        });
        for (User user : orderList) {
            System.out.println(user.toString());
        }
        System.out.println("Cash list: ok");
    }



    public void listCashierTickets(String cashId) {
        User user = findUserById(cashId);
        if (user instanceof Cash) {
            System.out.println("cash tickets " + cashId);
            System.out.println("Tickets:");
            Cash cashier = (Cash) user;
            for (String ticketId : cashier.getCreatedTicketIds()) {
                Ticket ticket = findTicketById(ticketId);
                if (ticket != null) {
                    System.out.println("  " + dateToIdFormat() + "-" + ticket.getIdTicket() + "->" + ticket.getTicketStatus());
                }
            }
            System.out.println("cash tickets: ok");
        } else {
            System.out.println("Cashier with id: " + cashId + " doesn't exist");
        }
    }



    public void newTicket(String userId, String cashId, String ticketId) {
        if (findUserById(userId) == null) {
            System.out.println("User with id: " + userId + " doesn't exist");
            return;
        }
        User cashUser = findUserById(cashId);
        if (cashUser == null || !(cashUser instanceof Cash)) {
            System.out.println("Cashier with id: " + cashId + " doesn't exist");
            return;
        }
        String date;
        String idNum;
        if (ticketId == null) {
            //ticketId = dateToIdFormat() + "-" + generateId();
            date = dateToIdFormat();
            idNum = String.valueOf(generateId());
        } else {
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



    public void addProductToTicket(String ticketId, String cashId, String prodId, int quantity, ArrayList<String> personalized) {
        Ticket ticket = findTicketById(ticketId);
        boolean valid = true;
        if (ticket == null) {
            System.out.println("Ticket with id: " + ticketId + " doesn't exist");
            valid = false;

        }
        if (!ticket.getCashId().equals(cashId) || ticket.getTicketStatus() == Ticket.TicketStatus.CLOSE) {
            System.out.println("unauthorized or closed ticket");
            valid = false;

        }
        if (ticket.getNumItems() + quantity > ticket.getMAXITEMS()) {
            System.out.println("The ticket is full");
            valid = false;
        }
        Product product = catalog.getProductId(prodId);
        if (product == null) {
            System.out.println("Product with id: " + prodId + " doesn't exist");
            valid = false;
        }
        if (product instanceof PersonalizedProduct) {
            PersonalizedProduct personalizedProduct = (PersonalizedProduct) product;
            if (personalized.size() > personalizedProduct.getMaxText()) {
                System.out.println("Exceeded maximum number of personalized texts");
                valid = false;

            }
        }
        if (product instanceof Events) {
            Events event = (Events) product;
            for (Product prod : ticket.getProducts()) {
                if (prod.getId_product() == prodId) {
                    System.out.println("Event already added");
                    valid = false;
                }
            }
            if (!event.validDate()) {
                System.out.println("Date not valid");
                valid = false;

            }
        }

        if (valid) {
            Product productToAdd = product;
            if (product instanceof Events) {
                productToAdd = new Events((Events) product, quantity);
            } else if (product instanceof PersonalizedProduct) {
                productToAdd = new PersonalizedProduct((PersonalizedProduct) product, personalized);
            }
            boolean found = false;
            ArrayList<Product> products = ticket.getProducts();
            ArrayList<Integer> quantities = ticket.getQuantities();

            if (!(productToAdd instanceof Events) && !(productToAdd instanceof PersonalizedProduct)) {
                for (int i = 0; i < products.size(); i++) {
                    if (products.get(i).getId_product() == prodId) {
                        quantities.set(i, quantities.get(i) + quantity);
                        found = true;
                        break;
                    }
                }
            }

            if (!found) {
                int index = 0;
                while (index < products.size() && products.get(index).getName().compareToIgnoreCase(productToAdd.getName()) < 0) {
                    index++;
                }
                products.add(index, productToAdd);
                quantities.add(index, quantity);

            }
            ticket.setNumItems(ticket.getNumItems() + quantity);
            if (ticket.getNumItems() > 0 && ticket.getTicketStatus() == Ticket.TicketStatus.EMPTY) {
                ticket.setTicketStatus(Ticket.TicketStatus.OPEN);
            }
            System.out.println("ticket add " + ticket.getIdTicket() + " " + cashId + " " + prodId + " " + quantity);
            System.out.println(print(ticket));
            System.out.println("ticket add: ok");
        }
    }


    public void removeProduct(String ticketId, String cashId, String prodId) {
        Ticket ticket = findTicketById(ticketId);
        if (ticket == null) {
            System.out.println("The ticket doesn´t exist");
        } else {
            if (ticket.getCashId().equals(cashId)) {
                if (ticket.getTicketStatus() == Ticket.TicketStatus.OPEN) {

                    ArrayList<Product> products = ticket.getProducts();
                    ArrayList<Integer> quantities = ticket.getQuantities();

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


    public void removeTicketsByCashier(String cashierId) {
        for (Ticket ticket : tickets) {
            if (ticket.getCashId().equals(cashierId)) {
                tickets.remove(ticket);
            }
        }
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

    private String print(Ticket ticket) {
        StringBuilder sb = new StringBuilder();
        double totalPrice = 0.0;
        double totalDiscount = 0.0;

        ArrayList<Product> products = ticket.getProducts();
        ArrayList<Integer> quantities = ticket.getQuantities();

        sb.append("Ticket : ").append(ticket.getIdTicket());
        if (ticket.getTicketStatus() == Ticket.TicketStatus.CLOSE) {
            sb.append("-").append(ticket.getClosingDate()).append("\n");
        } else {
            sb.append("\n");
        }
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            int amount = quantities.get(i);
            double unitDiscount = calculateDiscount(product, amount);
            if (product instanceof Events){
                Events event = (Events) product;
                if (!event.validDate()) {
                    return "The ticket cannot be closed because the " + event.getName() + " event has passed its deadline.";
                }
                sb.append(event.toString()).append("\n");
                totalPrice += product.getPrice();
                totalDiscount += unitDiscount;
            }else {
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
                        if (unitDiscount > 0) {
                            sb.append(" **discount -").append(String.format(Locale.US, "%.3f", unitDiscount)).append("\n");
                        } else {
                            sb.append("\n");
                        }
                    } else {
                        sb.append(product.toString());
                        if (unitDiscount > 0) {
                            sb.append(" **discount -").append(String.format(Locale.US, "%.2f", unitDiscount)).append("\n");
                        } else {
                            sb.append("\n");
                        }
                    }
                    totalPrice += product.getPrice();
                    totalDiscount += unitDiscount;
                }
            }
        }
        double finalPrice = totalPrice - totalDiscount;
        sb.append("  Total price: ").append(String.format(Locale.US, "%.2f", totalPrice)).append("\n");
        sb.append("  Total discount: ").append(String.format(Locale.US, "%.3f", totalDiscount)).append("\n");
        sb.append("  Final price: ").append(String.format(Locale.US, "%.3f", finalPrice));
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
    }
}
