
package es.upm.etsisi.poo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Control {
    private ArrayList<Product> products;
    private final int maxNumProducts = 200;
    private final int maxNumProductsTicket = 100;
    private int numProducts;
    private int numproductsTicket;
    private Map<Integer, Product> catalog = new LinkedHashMap<>();


    public int geNumProducts() {
        return numProducts;
    }

    public int getNumProductsTicket() {
        return numproductsTicket;
    }

    public void prodAdd(int id, String name, double price, Product.Category category) {
        Product product = new Product(id, name, price, category);
        /*
        Este no sé hacerlo
         */
    }

    public String prodList() {
        if (catalog.isEmpty()) { //Comprueba si está vacío
            return "Catalog is empty.\nprod list: ok";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Catalog:\n");
        for (Product p : catalog.values()) { //imprime los valores del catálogo
            sb.append(p.toString()).append("\n");
        }
        sb.append("prod list: ok");
        return sb.toString();
    }

    public static String echo(String s) {
        // Muestra un mensaje
        return s;
    }

    public void help() {
        products.clear();
        echo("Commands:\n" +
                " prod add <id> \"<name>\" <category> <price>\n" +
                " prod list\n" +
                " prod update <id> NAME|CATEGORY|PRICE <value>\n" +
                " prod remove <id>\n" +
                " ticket new\n" +
                " ticket add <prodId> <quantity>\n" +
                " ticket remove <prodId>\n" +
                " ticket print\n" +
                " echo \"<texto>\"\n" +
                " help\n" +
                " exit");
    }

    public void update() {

    }

    public static int readNumber(Scanner keyboard, String message, int min, int max) {
        // Muestra un mensaje y lee un número por teclado (si no es un número, vuelve a solicitar uno)
        int number;
        while (true) {
            try {
                echo(message);
                number = Integer.parseInt(keyboard.nextLine().trim());
                if (number >= min && number <= max) {
                    return number;
                }
                echo("Please select a number between " + min + " and " + max + ".");
            } catch (NumberFormatException e) {
                echo("Please enter a valid number.");
            }
        }
    }








}
