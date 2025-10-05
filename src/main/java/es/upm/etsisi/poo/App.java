package es.upm.etsisi.poo;

import java.util.Arrays;
import java.util.Scanner;


public class App {
    public static void main(String[] args) {
        App app = new App();
        app.start();
        app.run();
        app.exit();
    }
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




    public void run(){
        Scanner scanner = new Scanner(System.in);
        System.out.println(welcome);
        System.out.println(welcome1);

        while (true){
            System.out.println(UPM);
            String line = scanner.nextLine();
            line = line.replace("\"",""); //quitamos las \ por lada para q al separarlo por espacios sea más facil
            String[]split = line.split(" ");

            String command = split[0];
            String accion = split[1];
            String id = split[2];


            switch (command){
                case "help":
                    control.help();
                    break;

                case "prod":
                    if (accion.equals("add")) {
                        String name = split[3] + " " + split[4];
                        String category = split[5];
                        String price = split[6];
                        Product product = new Product(Integer.parseInt(id),name,Double.parseDouble(price),Category.valueOf(category));
                        catalog.addProd(product);

                    }else if(accion.equals("list")) {
                        catalog.prodList();

                    } else if (accion.equals("update")) {
                        String idToUpdate = split[2];
                        String field = split[3];
                        String value = String.join(" ", Arrays.copyOfRange(split, 4, split.length));
                        catalog.updateProd(Integer.parseInt(idToUpdate), field, value);

                    }else if (accion.equals("remove")){
                        catalog.prodRemove(Integer.parseInt(id));

                    }
                    break;

                case "ticket":
                    if (accion.equals("new")) {
                        ticket.newTicket();

                    } else if (accion.equals("add")) {
                        if(split.length < 4) {
                            System.out.println("");
                        }
                        String prodId = split[2];
                        String quantity = split[3];
                        ticket.addProduct(Integer.parseInt(prodId), Integer.parseInt(quantity));

                    } else if (accion.equals("remove")) {
                        String prodId = split[2];
                        ticket.removeProduct(Integer.parseInt(prodId));

                    } else if (accion.equals("print")) {
                        ticket.print();

                    }
                    break;

                case "exit":
                    exit();
                    return;

                case "echo":
                    Control.echo(line);
                    break;
                default:
                    System.out.println("Unkown command");
                    break;

            }
        }
    }


    public void start(){
        productList = new Product[200];
        ticketList = new Ticket[100];
        catalog = new Catalog();
        control = new Control();
        ticket = new Ticket(catalog);

    }
    public void exit(){
        System.out.println(end);
        System.out.println(goodbye);
    }
}















