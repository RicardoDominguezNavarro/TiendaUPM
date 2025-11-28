package es.upm.etsisi.poo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


/**
 * This is the main entry point of the application.
 * This class is the controller of the program, coordinating interactions
 * with the rest of the classes.
 */
public class App {
    /**
     * Command line prompt prefix displayed before each user input.
     */
    private static final String UPM = "tUPM>";
    /**
     * Welcome message that appear when the application starts
     */
    private static final String welcome = "Welcome to the ticket module App.";
    /**
     * Instruction message
     */
    private static final String welcome1 = "Ticket module. Type 'help' to see commands.";
    /**
     * Closing message
     */
    private static final String end = "Closing application. ";
    private static final String goodbye = "Goodbye! ";

    /**
     * Reference to the catalog class that manages all products
     */
    private Catalog catalog;
    /**
     * Reference to the ticket class for the ticket operations
     */
    private Ticket ticket;

    private TicketControl ticketControl;



    /**
     * The main method
     * @param args
     */
    public static void main(String[] args) {
        App app = new App();
        app.start();
        app.run();
    }

    /**
     *This method is the main execution loop of the program.
     *It continuously reads user input from the console, interprets commands,
     *and delegates actions to different components
     */
    /*
    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(welcome);
        System.out.println(welcome1);

        while (true) {
            System.out.print(UPM);
            String line = scanner.nextLine();
            if (line.trim().isEmpty()) { //si despues de eliminar los espacio sigue vacia pasa a la siguiente iteración
                continue;
            }
            if (System.getenv("fileinput")!=null && System.getenv("fileinput").equals("true")){
                System.out.println(line);
            }

            String[] split = line.split(" ");

            String command = split[0];
            String accion = (split.length > 1) ? split[1] : "";
            String id = (split.length > 2) ? split[2] : "";

            switch (command) {
                case "help":
                    help();
                    break;

                case "prod":
                    if (accion.equals("add")) {
                        if (split.length < 5) {
                            System.out.println("Invalid prod add command Usage: prod add <id> \\\"<name>\\\" <category> <price>");
                            System.out.println();
                            break;
                        }
                        String[] nameSplit = line.split("\"");
                        if (nameSplit.length < 2) {
                            System.out.println("Error: the name must be between quotation marks");
                            System.out.println();
                            break;
                        }

                        try {
                            String name = line.substring(line.indexOf("\"") + 1, line.lastIndexOf("\""));
                            String category = split[split.length-2].toUpperCase(); //length empieza a contar desde 1
                            String price = split[split.length-1];
                            Product product = new Product(Integer.parseInt(id), name, Double.parseDouble(price), Category.valueOf(category));
                            catalog.addProd(product);
                            System.out.println();
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid numeric value for id or price.");
                            System.out.println();
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid category or parameters.");
                            System.out.println();
                        }

                    } else if (accion.equals("list")) {
                        catalog.prodList();
                        System.out.println();

                    } else if (accion.equals("update")) {
                        if (split.length < 5) {
                            System.out.println("Invalid prod update command. Usage: prod update <id> <field> <value>");
                            System.out.println();
                            break;
                        }
                        String idToUpdate = split[2];
                        String field = split[3];
                        String value = String.join(" ", Arrays.copyOfRange(split, 4, split.length));
                        catalog.updateProd(Integer.parseInt(idToUpdate), field, value);
                        System.out.println();

                    } else if (accion.equals("remove")) {
                        if (split.length < 3) {
                            System.out.println("Invalid prod remove command. Usage: prod remove <id>");
                            System.out.println();
                            break;
                        }
                        try {
                            catalog.prodRemove(Integer.parseInt(split[2]));
                            System.out.println();
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid id for prod remove.");
                            System.out.println();
                        }
                    } else {
                        System.out.println("Unknown prod action");
                        System.out.println();
                    }
                    break;


                case "ticket":
                    if (accion.equals("new")) {
                        //ticket.newTicket();
                        System.out.println();

                    } else if (accion.equals("add")) {
                        if (split.length < 4) {
                            System.out.println("Invalid ticket add command. Usage: ticket add <prodId> <quantity>");
                            System.out.println();
                            break;
                        }
                        try {
                            //ticket.addProduct(Integer.parseInt(split[2]), Integer.parseInt(split[3]));
                            System.out.println();
                        } catch (NumberFormatException e) {
                            System.out.println("prodId and quantity must be integers.");
                            System.out.println();
                        }

                    } else if (accion.equals("remove")) {
                        if (split.length < 3) {
                            System.out.println("Invalid ticket remove command. Usage: ticket remove <prodId>");
                            System.out.println();
                            break;
                        }
                        try {
                            //ticket.removeProduct(Integer.parseInt(split[2]));
                            System.out.println();
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid prodId for ticket remove.");
                            System.out.println();
                        }
                    } else if (accion.equals("print")) {
                        //System.out.println(ticket.print());
                        System.out.println("ticket print: ok");
                        //ticket.newTicket();
                        System.out.println();
                    } else {
                        System.out.println("Unknown ticket action");
                        System.out.println();
                    }
                    break;

                case "exit":
                    exit();
                    return;

                case "echo":
                    echo(line);
                    break;
                default:
                    System.out.println("Unkown command");
                    System.out.println();
                    break;

            }
        }
    }
    */


    /**
     *This method initialize the objects with the limits needed
     */
    /*
    public void start() {
        catalog = new Catalog();
       // ticket = new Ticket(catalog);

    }
    */

    /**
     * Print the closing messages and end the application.
     */
    public void exit() {
        System.out.println(end);
        System.out.println(goodbye);
    }

    public static void echo(String line) {
        String[] parts = line.split(" ", 2);
        if (parts.length > 1) {
            System.out.println(parts[1]);
        } else {
            System.out.println();
        };
    }
    public  void help(){

        System.out.println("Commands:\n" +
                "  client add \"<nombre>\" <DNI> <email> <cashId>\n" +
                "  client remove <DNI>\n" +
                "  client list\n" +
                "  cash add [<id>] \"<nombre>\"<email>\n" +
                "  cash remove <id>\n" +
                "  cash list\n" +
                "  cash tickets <id>\n" +
                "  ticket new [<id>] <cashId> <userId>\n" +
                "  ticket add <ticketId><cashId> <prodId> <amount> [--p<txt> --p<txt>] \n" +
                "  ticket remove <ticketId><cashId> <prodId> \n" +
                "  ticket print <ticketId> <cashId> \n" +
                "  ticket list\n" +
                "  prod add <id> \"<name>\" <category> <price>\n" +
                "  prod update <id> NAME|CATEGORY|PRICE <value>\n" +
                "  prod addFood [<id>] \"<name>\" <price> <expiration:yyyy-MM-dd> <max_people>\n" +
                "  prod addMeeting [<id>] \"<name>\" <price> <expiration:yyyy-MM-dd> <max_people>\n" +
                "  prod list\n" +
                "  prod remove <id>\n" +
                "  help\n" +
                "  echo “<text>” \n" +
                "  exit");
        System.out.println();
        System.out.println("Categories: MERCH, STATIONERY, CLOTHES, BOOK, ELECTRONICS \n" +
                "Discounts if there are ≥2 units in the category: MERCH 0%, STATIONERY 5%, CLOTHES 7%, BOOK 10%, \n" +
                "ELECTRONICS 3%. ");
    }



    public void start() {
        this.catalog = new Catalog();
        this.ticketControl = new TicketControl(catalog);
    }

    public void run(){
        Scanner scanner = new Scanner(System.in);
        System.out.println(welcome);
        System.out.println(welcome1);
        while(true){
            System.out.print(UPM);
            if (!scanner.hasNextLine()) break;

            String line = scanner.nextLine();

            // Para pruebas con ficheros, imprimimos el comando leído
            if (System.getenv("fileinput") != null && System.getenv("fileinput").equals("true")) {
                System.out.println(line);
            }

            if (line.trim().isEmpty()) continue;

            String[] split = line.split(" ");
            String command = split[0];
            switch (command) {
                case "exit":
                    exit();
                    return;
                case "echo":
                    echo(line);
                    System.out.println();
                    break;
                case "help":
                    help();
                    System.out.println();
                    break;
                case "prod":
                    controlProdCommand(line, split);
                    System.out.println();
                    break;
                case "ticket":
                    controlTicketCommand(line, split);
                    System.out.println();
                    break;
                case "cash":
                    controlCashCommand(line, split);
                    System.out.println();
                    break;
                case "client":
                    controlClientCommand(line, split);
                    System.out.println();
                    break;
                default:
                    System.out.println("Invalid command");
                    break;

            }
        }

    }

    private void controlProdCommand(String line, String[] split){
        if(split.length < 2){
            System.out.println("Invalid prod command");
            return;
        }
        String action = split[1];
        switch (action) {
            case "list":
                catalog.prodList();
                break;
            case "remove":
                if (split.length < 3) {
                    System.out.println("Invalid prod remove command. Usage: prod remove <id>");
                    break;
                }
                try {
                    catalog.prodRemove(Integer.parseInt(split[2]));
                } catch (NumberFormatException e) {
                    System.out.println("Invalid id for prod remove.");
                }
                break;
            case "update":
                if (split.length < 5) {
                    System.out.println("Invalid prod update command. Usage: prod update <id> <field> <value>");
                    return;
                }
                String idToUpdate = split[2];
                String field = split[3];
                String value = String.join(" ", Arrays.copyOfRange(split, 4, split.length));
                catalog.updateProd(Integer.parseInt(idToUpdate), field, value);
                break;
            case "add":
                controlAddStandardProduct(line, split);
                break;
            case "addFood":
                controlAddEventProduct(line,split,action);
                break;
            case "addMeeting":
                controlAddEventProduct(line,split,action);
                break;
            default:
                System.out.println("Unknown prod command");
                break;
        }

    }

    private void controlAddStandardProduct(String line, String[] split){
        String name = getName(line);
        if(name == null || name.isEmpty()) {
            System.out.println("The name can't be empty and must be between quotation marks ");
            return;
        }
        //String[] args = getAfterName(line);
        /*
        if (name.length() < 2) {
            System.out.println("Error: the name must be between quotation marks");
            return;
        }
         */
        try {
            String[] args = getAfterName(line);
            int firstQuote = line.indexOf('"');
            String beforeName = line.substring(0, firstQuote).trim(); // desde el inicio hasta la primera comilla
            String[] partBeforeName = beforeName.split(" "); //separa por espacios

            if (args.length >= 2) {
                int id;
                if (partBeforeName.length < 3) {
                    id = catalog.generateId();
                } else{
                    id = Integer.parseInt(partBeforeName[2]);
                }
                Category category = Category.valueOf(args[0].toUpperCase());
                double price = Double.parseDouble(args[1]);
                if (args.length > 2) { //es personalizado
                    int maxPers = Integer.parseInt(args[2]);
                    PersonalizedProduct productPersonalize = new PersonalizedProduct(id, name, price, category, maxPers);
                    catalog.addProd(productPersonalize);
                } else {
                    Product product = new Product(id, name, price, category);
                    catalog.addProd(product);
                }
            }else{
                System.out.println("The category and/or price is missing.");
            }
            } catch(NumberFormatException e){
                System.out.println("Invalid numeric value for id or price.");
            } catch(IllegalArgumentException e){
                System.out.println("Invalid category or parameters.");
            }
    }

    private String getName(String line) {
        int first = line.indexOf("\"");
        int last = line.lastIndexOf("\"");
        if (first != -1 && last > first) {
            return line.substring(first + 1, last);
        }
        return null;
    }

    private String[] getAfterName(String line) {
        int lasQuote = line.lastIndexOf("\"");
        if(lasQuote == -1){
            return new String[0];
        }
        String textAfterName = line.substring(lasQuote + 1);
        String text = textAfterName.trim();
        if(text.isEmpty()) {
            return new String[0];
        }
        return text.split(" ");


    }

    private void controlAddEventProduct(String line, String[] split, String action){
        String name = getName(line);
        if(name == null) {
            System.out.println("The name can't be empty");
            return;
        }
        String[] args = getAfterName(line);
        int firstQuote = line.indexOf('"');
        String beforeName = line.substring(0, firstQuote).trim(); // desde el inicio hasta la primera comilla
        String[] partBeforeName = beforeName.split(" "); //separa por espacios
        try{
            if (args.length == 3) {
                int id;
                if (partBeforeName.length < 3) {
                    id = catalog.generateId();
                } else {
                    id = Integer.parseInt(partBeforeName[2]);
                }
                double price = Double.parseDouble(args[0]);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate expirationDate = LocalDate.parse(args[1], formatter);
                //LocalDate expirationDate = LocalDate.parse(args[1]); //Paso la fecha pasada por comando a LocalDate
                LocalDateTime expirationDateTime = expirationDate.atStartOfDay(); //Le añado la hora 00:00 a la fecha para que sea de la clase LocalDateTime para poder pasarla a Events
                int maxPeopleAllowed = Integer.parseInt(args[2]);
                //Este if puede que haya que cambiarlo en el futuro si hay más tipos de eventos
                if (action.equals("addFood")) {
                    Events foodEvent = new Events(id, name, price, expirationDateTime, Events.EventType.FOOD, maxPeopleAllowed);
                    catalog.addProd(foodEvent);
                } else if (action.equals("addMeeting")) {
                    Events meetingEvent = new Events(id, name, price, expirationDateTime, Events.EventType.MEETING, maxPeopleAllowed);
                    catalog.addProd(meetingEvent);
                }
            }else{
                System.out.println("The category, price or expiration date are missing.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid numeric value for id or price.");
            System.out.println();
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid category or parameters.");
            System.out.println();
        }catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Expected format: yyyy-MM-dd (example: 2025-03-28)");
            System.out.println();
        }

    }

    private void controlTicketCommand(String line, String[] split){

        String action = split[1];
        switch (action) {
            case "new":
                //para saber si mete el id o no. No sé si se comprueba en el ticketAdd
                String userId;
                String cashId;
                String ticketId = null;
                if (split.length == 4) {
                    // sin ticketId: ticket new <cashId> <userId>
                    cashId = split[2];
                    userId = split[3];
                } else if (split.length == 5) {
                    // con ticketId: ticket new <ticketId> <cashId> <userId>
                    ticketId = split[2];
                    cashId = split[3];
                    userId = split[4];
                } else {
                    System.out.println("Invalid ticket new command. Usage: ticket new [<ticketId>] <cashId> <userId>");
                    return;
                }

                User userNew = ticketControl.findUserById(userId);
                User cashNew = ticketControl.findUserById(cashId);
                if (cashNew == null || !(cashNew instanceof Cash)) {
                    System.out.println("Cashier with id: " + cashId + " doesn't exist.");
                    return;
                }
                if (userNew == null) {
                    System.out.println("User with id: " + userId + " doesn't exist.");
                    return;
                }
                ticketControl.newTicket(userId, cashId, ticketId);
                break;
            case "add":
                if (split.length < 6) {
                    System.out.println("Invalid ticket add command. Usage: ticket add <ticketId><cashId> <prodId> <amount> [--p<txt> --p<txt>]  ");
                    return;
                }
                String ticketIdAdd = split[2];
                String cashIdAdd = split[3];
                int prodId = Integer.parseInt(split[4]);
                int amount =  Integer.parseInt(split[5]);
                ArrayList<String> personalizedProducts = new ArrayList<>();


                User cashAdd = ticketControl.findUserById(cashIdAdd);
                if (!(cashAdd instanceof Cash)){
                    System.out.println("Cashier with id: " +cashIdAdd + " doesn't exist.");
                    return;
                }
                ArrayList<String> personalizations = new ArrayList<>();
                for (int i = 6; i < split.length; i++) {
                    String param = split[i];
                    if (param.startsWith("--p")) {
                        String text = param.substring(3);
                        personalizations.add(text);
                    }
                }
                ticketControl.addProductToTicket(ticketIdAdd, cashIdAdd,prodId,amount,personalizations);
                break;

            case "remove":
                if (split.length != 5) {
                    System.out.println("Invalid ticket remove command. Usage: ticket remove <ticketId> <cashId> <prodId>");
                    return;
                }
                String ticketIdRemove = split[2];
                String cashIdRemove = split[3];
                int prodIdRemove;
                try {
                    prodIdRemove = Integer.parseInt(split[4]);
                } catch (NumberFormatException e) {
                    System.out.println("Product ID must be an integer.");
                    return;
                }

                User cashRemove = ticketControl.findUserById(cashIdRemove);
                if (!(cashRemove instanceof Cash)) {
                    System.out.println("Cashier with id: " + cashIdRemove + " doesn't exist.");
                    return;
                }

                ticketControl.removeProduct(ticketIdRemove, cashIdRemove, prodIdRemove);
                break;

            case "print":
                if (split.length != 4) {
                    System.out.println("Invalid ticket print command. Usage: ticket print <ticketId> <cashId>");
                    return;
                }
                String ticketIdPrint = split[2];
                String cashIdPrint = split[3];
                User cashPrint = ticketControl.findUserById(cashIdPrint);
                System.out.println("ticket print " + ticketIdPrint + " " + cashIdPrint);

                if (!(cashPrint instanceof Cash)) {
                    System.out.println("Cashier with id: " + cashIdPrint + " doesn't exist.");
                    return;
                }

                ticketControl.printTicket(ticketIdPrint, cashIdPrint);
                break;

            case "list":
                if (split.length != 2) {
                    System.out.println("Invalid ticket list command. Usage: ticket list");
                    return;
                }
                System.out.println(ticketControl.listTicket());
                break;

            default:
                System.out.println("Unknown ticket command");
                break;

        }
    }

    private void controlClientCommand(String line, String[] split){
        String actionn = split[1];

        switch (actionn) {
            case "add":
                String name = getName(line);
                if(name == null) {
                    System.out.println("The name can't be empty");
                }
                String[] args = getAfterName(line);
                if(args.length < 3){
                    System.out.println("Invalid client add command. Usage: client add \"<nombre>\" <DNI> <email> <cashId> ");
                    return;
                }
                ticketControl.addClient(name, args[0], args[1], args[2]);
                break;
            case "remove":
                if(split.length < 3){
                    System.out.println("Invalid client remove command. Usage: client remove <DNI>");
                    return;
                }
                ticketControl.removeClient(split[2]);
                break;
            case "list":
                ticketControl.listClients();
                break;
            default:
                System.out.println("Unknown client command");
                break;

        }
    }

    private void controlCashCommand(String line, String[] split){
        String action = split[1];
        switch (action) {
            case "add":
                String name = getName(line);
                if(name == null) {
                    System.out.println("The name can't be empty");
                    return;
                }
                String[] args = getAfterName(line);
                if(args.length < 1){
                    System.out.println("Error: missing email");
                    return;
                }
                String email = args[0];
                String id = null;
                if(!split[2].startsWith("\"")){
                    id = split[2];
                }
                ticketControl.addCashier(id, name, email);
                break;
            case "remove":
                if(split.length < 3){
                    System.out.println("Invalid clash remove command. Usage: clash remove <DNI>");
                    return;
                }
                ticketControl.removeCashier(split[2]);
                break;
            case "list":
                ticketControl.listCashiers();
                break;
            case "tickets":
                ticketControl.listCashierTickets(split[2]);
                break;
            default:
                System.out.println("Unknown cash command");
                break;

        }
    }
}
