package es.upm.etsisi.poo;

import java.util.ArrayList;
import java.util.Date;

public class PersonalizedProduct extends Product {
    private int maxText;

    public PersonalizedProduct(int id, String name, double price, Category category, int maxText) {
        super(id, name, price, category);
        this.maxText = maxText;

    }

}
