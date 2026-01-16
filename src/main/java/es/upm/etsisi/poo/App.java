package es.upm.etsisi.poo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class App {

    private static final String UPM = "tUPM>";
    private static final String welcome = "Welcome to the ticket module App.";
    private static final String welcome1 = "Ticket module. Type 'help' to see commands.";
    private static final String end = "Closing application. ";
    private static final String goodbye = "Goodbye! ";
    private Catalog catalog;
    private TicketControl ticketControl;
    private ProductCLIController productCLI;
    private TicketCLIController ticketCLI;
    private ClientCLIController clientCLI;
    private CashierCLIController cashierCLI;
    private String inputFilePath;


    public static void main(String[] args) {
        App app = new App();
        if (args.length > 0) {
            app.inputFilePath = args[0];
        }
        app.start();
        app.run();
    }


    public void exit() {
        ticketControl.saveState();
        System.out.println(end);
        System.out.println(goodbye);
    }


    public static void echo(String line) {
        String[] parts = line.split(" ", 2);
        if (parts.length > 1) {
            System.out.println(parts[1]);
        } else {
            System.out.println();
        }
    }

    public void start() {
        this.catalog = Catalog.getInstance();
        this.ticketControl = TicketControl.getInstance();
        this.ticketControl.loadState();
        this.productCLI = new ProductCLIController(catalog);
        this.ticketCLI = new TicketCLIController(ticketControl);
        this.clientCLI = new ClientCLIController(ticketControl);
        this.cashierCLI = new CashierCLIController(ticketControl);
    }


    public void help() {
        System.out.println("Commands:\n" +
                "  client add \"<nombre>\" (<DNI>|<NIF>) <email> <cashId>\n" +
                "  client remove <DNI>\n" +
                "  client list\n" +
                "  cash add [<id>] \"<nombre>\"<email>\n" +
                "  cash remove <id>\n" +
                "  cash list\n" +
                "  cash tickets <id>\n" +
                "  ticket new [<id>] <cashId> <userId> -[c|p|s] (default -p option)\n" +
                "  ticket add <ticketId><cashId> <prodId> <amount> [--p<txt> --p<txt>] \n" +
                "  ticket remove <ticketId><cashId> <prodId> \n" +
                "  ticket print <ticketId> <cashId> \n" +
                "  ticket list\n" +
                "  prod add ([<id>] \"<name>\" <category> <price> [<maxPers>]) || (\"<name>\" <category> )\n" +
                "  prod update <id> NAME|CATEGORY|PRICE <value>\n" +
                "  prod addFood [<id>] \"<name>\" <price> <expiration:yyyy-MM-dd> <max_people>\n" +
                "  prod addMeeting [<id>] \"<name>\" <price> <expiration:yyyy-MM-dd> <max_people>\n" +
                "  prod list\n" +
                "  prod remove <id>\n" +
                "  help\n" +
                "  echo “<text>” \n" +
                "  exit\n");
        System.out.println();
        System.out.println("Categories: MERCH, STATIONERY, CLOTHES, BOOK, ELECTRONICS \n" +
                "Discounts if there are ≥2 units in the category: MERCH 0%, STATIONERY 5%, CLOTHES 7%, BOOK 10%, ELECTRONICS 3%. ");
    }


    public void run() {
        Scanner scanner;
        boolean isFileMode = false;
        try {
            if (inputFilePath != null) {
                File file = new File(inputFilePath);
                if (file.exists()) {
                    scanner = new Scanner(file);
                    isFileMode = true; // archivo
                } else {
                    System.out.println("Error: The file '" + inputFilePath + "' does not exist.");
                    return;
                }
            } else {
                scanner = new Scanner(System.in);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return;
        }
        System.out.println(welcome);
        System.out.println(welcome1);
        try {
            while (true) {
                System.out.print(UPM);
                if (!scanner.hasNextLine()) {
                    System.out.println("Please enter a line to proceed.");
                    break;
                }

                String line = scanner.nextLine();
                if (isFileMode) {
                    System.out.println(line);
                }
                if (System.getenv("fileinput") != null && System.getenv("fileinput").equals("true")) {
                    if (!isFileMode) System.out.println(line);
                }
                String[] split = line.split(" ");
                String command = split[0];

                if (command.equals("exit")) {
                    return;
                }

                switch (command) {
                    case "echo":
                        echo(line);
                        break;
                    case "help":
                        help();
                        System.out.println();
                        break;
                    case "prod":
                        productCLI.handleCommand(line, split);
                        System.out.println();
                        break;
                    case "ticket":
                        ticketCLI.handleCommand(line, split);
                        System.out.println();
                        break;
                    case "cash":
                        cashierCLI.handleCommand(line, split);
                        System.out.println();
                        break;
                    case "client":
                        clientCLI.handleCommand(line, split);
                        System.out.println();
                        break;
                    default:
                        System.out.println("Invalid command");
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            exit();
        }
    }
}
