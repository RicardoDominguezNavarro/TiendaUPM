package es.upm.etsisi.poo;

import java.util.Iterator;

public class Ticket {

    private final int maxItems = 100;  //es 100 porque es el máx de elementos que puede haber en un ticket
    private Product[] products;   //es array porque pueden haber hasta 100 product en un ticket
    private int[] quantities; // array de int ya que vamos a introducir un núm, y aparte es 100 el máx porque como máx solo 100 product
    private int numItems;
    private Control control;

    public Ticket(Control control) {
        this.control = control;
        this.products = new Product[maxItems];
        this.quantities = new int[maxItems];
        this.numItems = 0;
    }

    public void newTicket() {
        this.products = new Product[maxItems];
        this.quantities = new int[maxItems];
        this.numItems = 0;
    }

    public void addProduct(int prodId, int amount, Control control) {//Añadimos un producto nuevo al ticket
        Product p = control.getProductId(prodId);
        if (p == null) {
            Control.echo("There is no product");
        } else {
            boolean found = false;
            for (int i = 0; i < numItems; i++) {
                if (products[i].getId() == prodId) {
                    quantities[i] += amount;
                    found = true;
                }

            }
            if (!found) {
                if (numItems < maxItems) {
                    products[numItems] = p;
                    quantities[numItems] = amount;
                    numItems++;
                } else {
                    Control.echo("The ticket is full");
                }
            }
        }
    }

    public void removeProduct(int prodId, Control control) {
        Product p = control.getProductId(prodId);
        if (p == null) {
            Control.echo("There is no product");
        } else {
            boolean found = false;
            for (int i = 0; i < numItems; i++) {
                if (products[i].getId() == prodId) {
                    products[i + 1] = products[i];
                    found = true;
                }
                if (found) {
                    products[i + 1] = products[i];
                }
            }
        }
    }

    public double calculateDiscount(Product product, int amount) {
        double result = 0.0;
        if (amount >= 2) {
            double discount = getDiscount(product.getCategory());
            result = product.getPrice() * discount;
        }
        return result;
    }

    public double getDiscount(Product.Category category) {
        double percent = 0.0;
        switch (category) {
            case ROPA:
                percent = 0.07;
                break;
            case LIBRO:
                percent = 0.1;
                break;
            case MERCH:
                percent = 0.0;
                break;
            case PAPELERIA:
                percent = 0.05;
                break;
            case ELECTRONICA:
                percent = 0.03;
                break;
            default:
                System.out.println("Please select a valid option");
        }
        return percent;
    }

    public void currentTicket() {
        System.out.println(print());
    }

    public String print() {
        StringBuilder sb = new StringBuilder();
        double totalPrice = 0.0;
        double totalDiscount = 0.0;


        sb.append("===========  TICKET  ===========\n");
        for (int i = 0; i < numItems; i++) {
            Product p = products[i];
            int amount = quantities[i];

            for (int j = 0; j < amount; j++) {
                double discount = calculateDiscount(p, amount);
                //poner el toString del producto
                if (discount > 0) {
                    sb.append("/ discount= -").append(discount).append("\n");
                }
                totalPrice += p.getPrice();
                totalDiscount += discount;
            }
        }

        sb.append("Total price =").append(totalPrice).append("\n");
        sb.append("Total discount =").append(totalDiscount).append("\n");


        return sb.toString();
    }


}
