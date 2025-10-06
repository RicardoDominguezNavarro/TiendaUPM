
package es.upm.etsisi.poo;

import java.util.Arrays;
import java.util.Scanner;


public class App {
    private static final String UPM = "tUPM>";
    private static final String welcome = "Welcome to the ticket module App";
    private static final String welcome1 = "Type 'help' to see commands";
    private static final String end = "Closing application. ";
    private static final String goodbye = "Goodbye! ";
    public Control control;
    public Catalog catalog;
    public Ticket ticket;

    private Product[] productList;
    private Ticket[] ticketList;

    public static void main(String[] args) {
        App app = new App();
        app.start();
        app.run();
        app.exit();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(welcome);
        System.out.println(welcome1);

        while (true) {
            System.out.print(UPM);
            String line = scanner.nextLine();
            //line = line.replace("\"", ""); //quitamos las \ por lada para q al separarlo por espacios sea más facil
            if (line.trim().isEmpty()) {
                continue;
            }
            String[] split = line.split(" ");
            String[] onlyName = line.split("\"");

            String command = split[0];
            String accion = (split.length > 1) ? split[1] : "";
            String id = (split.length > 2) ? split[2] : "";


            switch (command) {
                case "help":
                    if (control != null) {
                        control.help();
                    } else {
                        System.out.println("Help not available.");
                    }
                    break;

                case "prod":
                    if (accion.equals("add")) {
                        if (split.length < 7) {
                            System.out.println("Invalid prod add command Usage: prod add <id> \\\"<name>\\\" <category> <price>");
                            break;
                        }
                        try {
                            String name = line.split("\"")[1];
                            String category = split[split.length-2];
                            String price = split[split.length-1];
                            Product product = new Product(Integer.parseInt(id), name, Double.parseDouble(price), Category.valueOf(category));
                            catalog.addProd(product);
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid numeric value for id or price.");
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid category or parameters.");
                        }

                    } else if (accion.equals("list")) {
                        catalog.prodList();

                    } else if (accion.equals("update")) {
                        if (split.length < 5) {
                            System.out.println("Invalid prod update command. Usage: prod update <id> <field> <value>");
                            break;
                        }
                        String idToUpdate = split[2];
                        String field = split[3];
                        String value = String.join(" ", Arrays.copyOfRange(split, 4, split.length));
                        catalog.updateProd(Integer.parseInt(idToUpdate), field, value);

                    } else if (accion.equals("remove")) {
                        if (split.length < 3) {
                            System.out.println("Invalid prod remove command. Usage: prod remove <id>");
                            break;
                        }
                        try {
                            catalog.prodRemove(Integer.parseInt(split[2]));
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid id for prod remove.");
                        }
                    } else {
                        System.out.println("Unknown prod action");
                    }
                    break;


                case "ticket":
                    if (accion.equals("new")) {
                        ticket.newTicket();

                    } else if (accion.equals("add")) {
                        if (split.length < 4) {
                            System.out.println("Invalid ticket add command. Usage: ticket add <prodId> <quantity>");
                            break;
                        }
                        try {
                            ticket.addProduct(Integer.parseInt(split[2]), Integer.parseInt(split[3]));
                        } catch (NumberFormatException e) {
                            System.out.println("prodId and quantity must be integers.");
                        }

                    } else if (accion.equals("remove")) {
                        if (split.length < 3) {
                            System.out.println("Invalid ticket remove command. Usage: ticket remove <prodId>");
                            break;
                        }
                        try {
                            ticket.removeProduct(Integer.parseInt(split[2]));
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid prodId for ticket remove.");
                        }
                    } else if (accion.equals("print")) {
                        ticket.print();
                    }
                    break;

                case "exit":
                    exit();
                    return;

                case "echo":
                    if (control != null) {
                        control.echo(line);
                    } else {
                        System.out.println("Echo not available.");
                    }
                    break;
                default:
                    System.out.println("Unkown command");
                    break;

            }
        }
    }


    public void start() {
        productList = new Product[200];
        ticketList = new Ticket[100];
        catalog = new Catalog();
        control = new Control();
        ticket = new Ticket(catalog);

    }

    public void exit() {
        System.out.println(end);
        System.out.println(goodbye);
    }
}
