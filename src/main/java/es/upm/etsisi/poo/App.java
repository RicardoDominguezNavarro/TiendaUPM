package es.upm.etsisi.poo;

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
     * Welcome message that appear when teh application starts
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
     * Reference to control class for utility commands
     */
    public Control control;
    /**
     * Reference to the catalog class that manages all products
     */
    public Catalog catalog;
    /**
     * Reference to the ticket class for the ticket operations
     */
    public Ticket ticket;


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
    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(welcome);
        System.out.println(welcome1);

        while (true) {
            System.out.print(UPM);
            String line = scanner.nextLine();
            if (line.trim().isEmpty()) {
                continue;
            }
            if (System.getenv("fileinput")!=null &&
                    System.getenv("fileinput").equals("true"))
                System.out.println(line);

            String[] split = line.split(" ");

            String command = split[0];
            String accion = (split.length > 1) ? split[1] : "";
            String id = (split.length > 2) ? split[2] : "";

            switch (command) {
                case "help":
                    if (control != null) {
                        control.help();
                        System.out.println();
                    } else {
                        System.out.println("Help not available.");
                        System.out.println();
                    }
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
                            System.out.println("The name must be between quotation marks");
                            System.out.println();
                            break;
                        }

                        try {
                            String name = nameSplit[1];
                            String category = split[split.length-2].toUpperCase();
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
                        ticket.newTicket();
                        System.out.println();

                    } else if (accion.equals("add")) {
                        if (split.length < 4) {
                            System.out.println("Invalid ticket add command. Usage: ticket add <prodId> <quantity>");
                            System.out.println();
                            break;
                        }
                        try {
                            ticket.addProduct(Integer.parseInt(split[2]), Integer.parseInt(split[3]));
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
                            ticket.removeProduct(Integer.parseInt(split[2]));
                            System.out.println();
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid prodId for ticket remove.");
                            System.out.println();
                        }
                    } else if (accion.equals("print")) {
                        System.out.println(ticket.print());
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
                    if (control != null) {
                        control.echo(line);
                        System.out.println();
                    } else {
                        System.out.println("Echo not available.");
                        System.out.println();
                    }
                    break;
                default:
                    System.out.println("Unkown command");
                    System.out.println();
                    break;

            }
        }
    }


    /**
     *This method initialize the objects with the limits needed
     */
    public void start() {
        catalog = new Catalog();
        control = new Control();
        ticket = new Ticket(catalog);

    }

    /**
     * Print the closing messages and end the application.
     */
    public void exit() {
        System.out.println(end);
        System.out.println(goodbye);
    }
}
