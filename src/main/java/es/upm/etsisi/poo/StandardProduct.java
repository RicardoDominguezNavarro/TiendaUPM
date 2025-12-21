package es.upm.etsisi.poo;

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

}
