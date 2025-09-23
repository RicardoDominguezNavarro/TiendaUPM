package es.upm.etsisi.poo;
import java.util.ArrayList;
import java.util.List;

public class Ticket {

    private final int maxItems = 200;
    private Product product;
    private int numItems;

    public Ticket() {
        this.product = new Product();
        this.numItems = 0;
    }



}
