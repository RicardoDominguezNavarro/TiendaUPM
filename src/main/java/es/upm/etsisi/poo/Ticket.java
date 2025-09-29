package es.upm.etsisi.poo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Ticket {

    private final int maxItems = 100;  //es 100 porque es el máx de elementos que puede haber en un ticket
    private Product[] products;   //es array porque pueden haber hasta 100 product en un ticket
    private int[] quantities; // array de int ya que vamos a introducir un núm, y aparte es 100 el máx porque como máx solo 100 product
    private int numItems;

    public Ticket() {
        this.products = new Product[maxItems];
        this.quantities = new int[maxItems];
        this.numItems = 0;
    }

    public void addProduct(Product product, int amount) {  //Añadimos un producto nuevo al ticket
        if (product == null || amount <= 0) {
            throw new IllegalArgumentException(); //poner mensaje de salida
        }
        if (numItems < maxItems) {
            products[numItems]=product;
            quantities[numItems]=amount;
            numItems++;
        }
    }

    public void removeProduct(Product product) {
        if (product == null ) {
            throw new IllegalArgumentException(); //poner mensaje de salida
        }
        Iterator search = new Iterator() {
            @Override
            public boolean hasNext() {
                return false;
            }
            @Override
            public Object next() {
                return null;
            }
        }
        while (search.hasNext() );



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
//hacerlo con stringBuilder quedará más chulo jejeje
        double totalPrice = 0.0;
        double totalDiscount = 0.0;
        for (int i = 0; i < numItems; i++){
            products[i];
        }

    }


}
