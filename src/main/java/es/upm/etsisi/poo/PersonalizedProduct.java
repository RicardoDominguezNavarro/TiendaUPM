package es.upm.etsisi.poo;

import java.util.ArrayList;


public class PersonalizedProduct extends Product{
    private int maxText;
    private ArrayList<String> personalizationList;


    public PersonalizedProduct(int id, String name, double price, Category category){
        super(id, name, price, category);
        this.maxText = maxTextsByCategory(category);
        this.personalizationList = new ArrayList<>();
    }

    private int maxTextsByCategory(Category category){
        switch (category){
            case CLOTHES:
                return 25;
            case MERCH:
                return 15;
            case BOOK:
                return 10;
            case STATIONERY:
                return 5;
            case ELECTRONICS:
                return 20;
            default:
                return 0;
        }
    }

    public int getMaxText() {
        return this.maxText;
    }

    public boolean addPersonalization(String textPersonalization){
        boolean added = false;
        if(personalizationList.size() < this.maxText){
            personalizationList.add(textPersonalization);
            added = true;
        }
        return added;
    }

    public PersonalizedProduct(PersonalizedProduct other) {
        super(other.getId_p(), other.getName(), other.getPrice(), other.getCategory());
        this.maxText = other.maxText;
        this.personalizationList = new ArrayList<>();
    }

    public double getPricePersonalization() {
        double basePrice = super.getPrice();
        double extraPrince = basePrice * 0.10 *  personalizationList.size();
        return basePrice + extraPrince;
    }



}
