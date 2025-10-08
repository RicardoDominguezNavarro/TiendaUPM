package es.upm.etsisi.poo;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 */
public class Control {

    /**
     * List that stores all available products
     */
    private ArrayList<Product> products;
    /**
     * Represent the current product
     */
    private Product product;


    /**
     * Builder.
     * The list starts empty
     */
    public Control() {
        this.products = new ArrayList<>();
    }

    /**
     * Prints and returns the specified message.
     *
     * @param s the message to display
     * @return the same message that was printed
     */
    public static String echo(String s) {
        System.out.println(s);
        return s;
    }

    /**
     * Print the list of commands and category information.
     * It provides guidance on how to use product-related and ticket-related commands.
     */
    public  void help(){
        products.clear();
        System.out.println("Commands:\n" +
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
        System.out.println();
        System.out.println("Categories: MERCH, STATIONERY, CLOTHES, BOOK, ELECTRONICS \n" +
                "Discounts if there are ≥2 units in the category: MERCH 0%, STATIONERY 5%, CLOTHES 7%, BOOK 10%, \n" +
                "ELECTRONICS 3%. ");
    }

    /**
     *
     * @param keyboard used to read user input
     * @param message the message displayed to the user before reading input
     * @param min the minimum valid value
     * @param max the maximum valid value
     * @return
     */
    public static int readNumber(Scanner keyboard, String message, int min, int max) {
        // Muestra un mensaje y lee un número por teclado (si no es un número, vuelve a solicitar uno)
        int number;
        while (true) {
            try {
                System.out.println(message);
                number = Integer.parseInt(keyboard.nextLine().trim());
                if (number >= min && number <= max) {
                    return number;
                }
                System.out.println("Please select a number between " + min + " and " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}