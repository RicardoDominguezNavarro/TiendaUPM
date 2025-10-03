package es.upm.etsisi.poo;

import java.util.ArrayList;
import java.util.Scanner;

public class Control {
    private ArrayList<Product> products;
    private Product p;

    public static String echo(String s) {
        // Muestra un mensaje
        System.out.println(s);
        return s;
    }
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
    }
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



