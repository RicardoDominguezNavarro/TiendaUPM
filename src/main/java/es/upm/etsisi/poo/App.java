package es.upm.etsisi.poo;

import java.util.Scanner;

/**
 * Hello world!
 *
 */

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


    public static void main(String [] Args) {

        App app = new App();
        app.start();
        app.run();
        app.exit();
    }

    private void run(){
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
                        String field = split[3];
                        String value = split;
                        catalog.updateProd();
                        //no sé como hacer esto porq como a veces te pueden poner un nombre y otras solo una cosa ns como poner lo del String de value

                    }else if (accion.equals("remove")){
                        catalog.prodRemove(Integer.parseInt(id));

                    }
                    break;

                case "ticket":
                    if (accion.equals("new")) {
                        ticket.newTicket();

                    } else if (accion.equals("add")) {
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

            }
        }
    }


    private void start(){
        productList = new Product[200];
        ticketList = new Ticket[100];

    }
    private void exit(){
        System.out.println(end);
        System.out.println(goodbye);
    }
}















