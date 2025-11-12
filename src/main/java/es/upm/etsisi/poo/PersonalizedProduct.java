package es.upm.etsisi.poo;

import java.util.ArrayList;
import java.util.Date;

public class PersonalizedProduct extends Product{
    private int maxText;
    private ArrayList<String> allowedTexts;

    public PersonalizedProduct(int id, String name, double price, Category category,int maxTexts, ArrayList<String> allowedTexts){
        super(id, name, price, null);
        this.maxText = maxTexts;

    }
}
