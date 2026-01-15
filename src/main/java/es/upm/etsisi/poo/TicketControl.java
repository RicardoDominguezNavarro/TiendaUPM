package es.upm.etsisi.poo;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class TicketControl {

    private static TicketControl instance;
    private ArrayList<Ticket<?>> tickets;
    private final Catalog catalog = Catalog.getInstance();
    private ArrayList<User> users;

    public TicketControl() {
        this.tickets = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    public static TicketControl getInstance() {
        if (instance == null){
            instance = new TicketControl();
        }
        return instance;
    }

    public User findUserById(String id) {
        for (User user : users) {
            if (user.getId().equalsIgnoreCase(id)) {
                return user;
            }
        }
        return null;
    }

    public void addClient(String name, String id, String email, String cashId) {
        if (findUserById(id) != null) {
            System.out.println("Client /Company with id: " + id + " already exists");
            return;
        }
        User creator = findUserById(cashId);
        if (creator == null) {
            System.out.println("Cashier with Id " + cashId + " doesn't exist");
            return;
        }
        try {
            User newUser;
            // Probamos primero si es NIF de empresa
            if (Company.checkNIF(id)) {
                newUser = new Company(name, id, email, cashId);
            } else {
                // Si no es NIF, asumimos Cliente (se valida el DNI y lanzará excepción si falla)
                newUser = new Client(name, id, email, cashId);
            }
            System.out.println("client add " + newUser.getName() + " '" + newUser.getId() + "' " + newUser.getEmail() + " " + cashId);
            users.add(newUser);
            System.out.println(newUser.toString());
            System.out.println("client add: ok");

        } catch (IllegalArgumentException e) {
            System.out.println("Error creating user: " + e.getMessage());
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

        users.stream()
                .filter(user -> user instanceof Client)
                .sorted((u1, u2) -> u1.getName().compareToIgnoreCase(u2.getName()))
                .forEach(System.out::println);

        System.out.println("Client list: ok");
    }

    public void listCashiers() {
        System.out.println("cash list");
        System.out.println("Cash:");

        users.stream()
                .filter(user -> user instanceof Cash)
                .sorted((u1, u2) -> u1.getName().compareToIgnoreCase(u2.getName()))
                .forEach(System.out::println);

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
                    //System.out.println("  " + dateToIdFormat() + "-" + ticket.getIdTicket() + "->" + ticket.getTicketStatus());
                    System.out.println("  " + ticket.getOpeningDate() + "-" + ticket.getIdTicket() + "->" + ticket.getTicketStatus());
                }
            }
            System.out.println("cash tickets: ok");
        } else {
            System.out.println("Cashier with id: " + cashId + " doesn't exist");
        }
    }


    public void newTicket(String userId, String cashId, String ticketId,String type) {
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
            if (findTicketById(ticketId) != null) {
                System.out.println("Ticket with id: " + ticketId + " already exists");
                return;
            }
        }
        Ticket<?> ticket = new Ticket(idNum, userId, cashId, date, type);
        ticket.setTicketStatus(Ticket.TicketStatus.EMPTY);
        System.out.println("ticket new " + idNum + " " + cashId + " " + userId);

        tickets.add(ticket);

        Cash cashier = (Cash) cashUser;
        cashier.getCreatedTicketIds().add(ticket.getIdTicket());

        System.out.println(ticket.print());
        System.out.println("ticket new: ok");
    }


    public Ticket<?> findTicketById(String ticketId) {
        for (Ticket<?> ticket : tickets) {
            if (ticket.getIdTicket().equals(ticketId)) {
                return ticket;
            }
        }
        System.out.println("Ticket not found");
        return null;
    }



    public void addProductToTicket(String ticketId, String cashId, String prodId, int quantity, ArrayList<String> personalized) {
        Ticket<?> ticket = findTicketById(ticketId);
        if (ticket == null) {
            return;
        }

        if (!ticket.getCashId().equals(cashId)) {
            System.out.println("unauthorized or closed ticket");
            return;
        }

        Product product = catalog.getProductId(prodId);
        if (product == null) {
            System.out.println("Product with id: " + prodId + " doesn't exist");
            return;
        }

        try {
            boolean added = ticket.addProduct(product, quantity, personalized);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeProduct(String ticketId, String cashId, String prodId) {
        Ticket<?> ticket = findTicketById(ticketId);
        if (ticket != null) {
            ticket.removeProduct(prodId, cashId);
        }
    }


    public void removeTicketsByCashier(String cashierId) {
        tickets.removeIf(ticket -> ticket.getCashId().equals(cashierId));
    }

    public void printTicket(String ticketId, String cashId) {
        Ticket<?> ticket = findTicketById(ticketId);

        if (ticket == null) {
            System.out.println("The ticket doesn´t exist");
        } else if (!ticket.close()) {
            System.out.println("Cannot close this ticket type");
        } else {
            if (ticket.getCashId().equals(cashId)) {
                if (ticket.getTicketStatus() != Ticket.TicketStatus.CLOSE) {
                    String closingDate = dateToIdFormat();
                    ticket.setClosingDate(closingDate);
                    ticket.setTicketStatus(Ticket.TicketStatus.CLOSE);
                    System.out.println(ticket.print());
                    System.out.println("ticket print: ok");

                }
            } else {
                System.out.println("The cashier didn't create this ticket.");
            }
        }
    }


    public String listTicket() {
        StringBuilder sb = new StringBuilder();
        System.out.println("ticket list");
        sb.append("Ticket List:\n");
        for (Ticket<?> ticket : tickets) {
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

    public void saveState() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("tienda_upm_data.dat"))) {
            out.writeObject(users);
            out.writeObject(tickets);
            out.writeObject(catalog.getProducts());
            System.out.println("Persistence: Data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void loadState() {
        File file = new File("tienda_upm_data.dat");
        if (!file.exists()) {
            return;
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            this.users = (ArrayList<User>) in.readObject();
            this.tickets = (ArrayList<Ticket<?>>) in.readObject();
            ArrayList<Product> loadedProducts = (ArrayList<Product>) in.readObject();

            if (loadedProducts != null) {
                catalog.getProducts().clear();
                catalog.getProducts().addAll(loadedProducts);
                int maxServiceId = 0;
                for (Product p : loadedProducts) {
                    if (p instanceof Service) {
                        String idStr = p.getId_product().replace("S", "");
                        try {
                            int idVal = Integer.parseInt(idStr);
                            if (idVal > maxServiceId) {
                                maxServiceId = idVal;
                            }
                        } catch (NumberFormatException e) {
                            // Ignoramos si el ID no tiene el formato esperado
                        }
                    }
                }
                Service.setNextServiceId(maxServiceId + 1);
            }
            System.out.println("Persistence: Data loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }
}
