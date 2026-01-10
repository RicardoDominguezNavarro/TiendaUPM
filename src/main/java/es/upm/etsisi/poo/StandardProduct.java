package es.upm.etsisi.poo;

import java.util.Locale;

public class StandardProduct extends Product{
    public StandardProduct(String id, String name, double price, Category category) {
        super(id, name, price, category);

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Error: name cannot be null or empty");
        }
        if (price < 0.0) {
            throw new IllegalArgumentException("Error: price may not be negative");
        }
        if (category == null) {
            throw new IllegalArgumentException("Error: category is required for standard products");
        }
    }
    @Override
    public String toString() {
        return String.format(Locale.US,
                "{class:Product, id:%s, name:'%s', category:%s, price:%.1f}",
                getId_product(), getName(), getCategory(), getPrice());
    }

}
