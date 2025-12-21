package es.upm.etsisi.poo;

import java.util.Locale;


public abstract class Product {


    private String id_product;
    private String name;
    private double price;
    private Category category;

    protected Product(String id_product, String name, double price, Category category) {
        if (id_product == null || id_product.trim().isEmpty()) {
            throw new IllegalArgumentException("Id required");
        }
        this.id_product = id_product;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public String getId_product() {
        return id_product;
    }

    public void setId_product(String id_product) {
        if (id_product == null || id_product.trim().isEmpty()) {
            throw new IllegalArgumentException("Id required");
        }
        this.id_product = id_product;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "{class:%s, id:%s, name:'%s', category:%s, price:%.1f}",
                this.getClass().getSimpleName(), id_product, name, category, price);
    }

}
