package es.upm.etsisi.poo;

import java.util.ArrayList;
import java.util.Locale;


public class PersonalizedProduct extends Product{
    private int maxText;
    private ArrayList<String> personalizationList;


    public PersonalizedProduct(int id_product, String name, double price, Category category){
        super(id_product, name, price, category);
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

    @Override
    public double getPrice() {
        return getPricePersonalization();
    }

    public boolean addPersonalization(String textPersonalization){
        boolean added = false;
        if(personalizationList.size() < this.maxText){
            personalizationList.add(textPersonalization);
            added = true;
        }
        return added;
    }

    //Se hace una copia del producto porque no queremos que tdo el producto se modifique en el catalogo sino q solo uno en el ticket
    public PersonalizedProduct(PersonalizedProduct other) {
        super(other.getId_product(), other.getName(), other.getPrice(), other.getCategory());
        this.maxText = other.maxText;
        this.personalizationList = new ArrayList<>();
    }

    public double getPricePersonalization() {
        double basePrice = super.getPrice();
        double extraPrince = basePrice * 0.10 *  personalizationList.size();
        return basePrice + extraPrince;
    }

    @Override
    public String toString() {
        double currentPrice = getPricePersonalization();
        if (personalizationList.isEmpty()) {
            return String.format(Locale.US,
                    "{class:ProductPersonalized, id:%d, name:'%s', category:%s, price:%.1f, maxPersonal:%d}",
                    getId_product(), getName(), getCategory(), currentPrice, maxText);
        }else {
            return String.format(Locale.US,
                    "{class:ProductPersonalized, id:%d, name:'%s', category:%s, price:%.1f, maxPersonal:%d, personalizationList:%s}",
                    getId_product(), getName(), getCategory(), currentPrice, maxText, personalizationList.toString());
        }
    }



}
