package es.upm.etsisi.poo;

import java.util.ArrayList;
import java.util.Scanner;

public class Control {
    private ArrayList<Product> products;
    private Product p;
    public void productAdd(int id, String name, double price, Product.Category category) {
        if(id <= 0) { //id mayor que 0
            throw new IllegalArgumentException("Id may not be negative!");
        }
        if(name == null || name.trim().isEmpty() || name.trim().length() >= 100) {
            //el nombre no sea null, quita los espacios en blanco para ver si está vacía y quita los
            // espacios y cuenta caracteres para que no haya más de 100
            throw new IllegalArgumentException("Invalid name!");
        }
        if(price <= 0.0) { //precio mayor que 0
            throw new IllegalArgumentException("Price may not be negative!");
        }
        if(category == null) { //categoria no es null
            throw new IllegalArgumentException("Category may not be null!");
        }
        Product product= new Product(id,name,price,category);
        products.add(product);
    }
    public static String echo(String s) {
        // Muestra un mensaje
        return s;
    }

    public void help(){
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
    public void update(int id, String field, String value) {
        Product product = null;
        int position = -1;

        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == id) {
                product = products.get(i);
                position = i;
                break;
            }
        }

        if (product != null) {
            if (field == null || value == null) {
                echo("Error: campo o valor nulo.");
                return;
            }
            switch (field.toLowerCase()) {
                case "nombre":
                    product.setName(value);
                    break;
                case "categoria":
                    try {
                        product.setCategory(Product.Category.valueOf(value.toUpperCase()));
                    } catch (IllegalArgumentException e) {
                        echo("Error: la categoría \"" + value + "\" no existe.");
                    }
                    break;
                case "precio":
                    try {
                        double price = Double.parseDouble(value);
                        if (price < 0) {
                            echo("Error: el precio no puede ser negativo.");
                        } else {
                            product.setPrice(price);
                        }
                    } catch (NumberFormatException e) {
                        echo("Error: El valor \"" + value + "\" no es un número válido.");
                    }
                    break;
                default:
                    echo("Error: campo \"" + field + "\" no reconocido.");
                    break;
            }
            products.set(position, product);

        } else {
            echo("Error: producto con ID " + id + " no encontrado.");
        }
    }

}



