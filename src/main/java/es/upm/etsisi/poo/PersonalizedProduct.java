package es.upm.etsisi.poo;

import java.util.ArrayList;
import java.util.Locale;


public class PersonalizedProduct extends Product{
    private int maxText;
    private ArrayList<String> personalizationList;



    public PersonalizedProduct(int id_product, String name, double price, Category category, int maxText){
        super(id_product, name, price, category);
        this.maxText = maxText;
        this.personalizationList = new ArrayList<>();
    }

    //Se hace una copia del producto porque no queremos que tdo el producto se modifique en el catalogo sino q solo uno en el ticket
    public PersonalizedProduct(PersonalizedProduct other, ArrayList<String> initialText){
        super(other.getId_product(), other.getName(), other.getPrice(), other.getCategory());
        this.maxText = other.getMaxText();
        this.personalizationList = new ArrayList<>();
        if(initialText != null){
            this.personalizationList.addAll(initialText);
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

    public double getPricePersonalization() {
        double basePrice = super.getPrice();
        double extraPrince = basePrice * 0.10 *  personalizationList.size();
        return basePrice + extraPrince;
    }

    public ArrayList<String> getPersonalizationList() {
        return personalizationList;
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
