package es.upm.etsisi.poo;

import java.util.Locale;

/**
 * Represents a product with an ID, name, price and category.
 */
public class Product {
    /**
     * Unique ID for each product.
     */
    private int id_product;
    /**
     * Name of the product. Cannot be null, empty or exceed 100 characters.
     */
    private String name;
    /**
     * Price of the product.
     */
    private double price;
    /**
     * Category to which the product belongs.
     */
    public Category category;
    /**
     * Catalog to which this product belongs.
     */
    private Catalog belongToCatalog;


    /**
     * Builder that create a new product
     * @param id_p the unique ID of the product
     * @param name name of the product
     * @param price price of the product
     * @param category category of the product
     * @throws IllegalArgumentException if any argument is invalid
     */
    public Product(int id_p, String name, double price, Category category) {
        if(id_p <= 0) {
            throw new IllegalArgumentException("Id may not be negative!");
        }
        if(name == null || name.trim().isEmpty() || name.trim().length() >= 100) {
            throw new IllegalArgumentException("Invalid name!");
        }
        if(price <= 0.0) {
            throw new IllegalArgumentException("Price may not be negative!");
        }
        if(category == null) {
            throw new IllegalArgumentException("Category may not be null!");
        }
        this.id_product = id_p;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    /**
     * @return the product's ID
     */
    public int getId_product() {
        return id_product;
    }

    /**
     * @return the name of the product
     */
    public String getName() {
        return name;
    }

    /**
     * @return the price of the product
     */
    public double getPrice() {
        return price;
    }

    /**
     * @return the category of the product
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Updates the category of the product
     *
     * @param category the new category
     * @throws IllegalArgumentException if category is null
     */
    public void setCategory(Category category) {
        if(category == null) {
            throw new IllegalArgumentException("Category may not be null!");
        }
        this.category = category;
    }

    /**
     * Updates the name of the product
     * @param name the new name
     * @throws IllegalArgumentException if the name is invalid
     */
    public void setName(String name) {
        if(name == null || name.trim().isEmpty() || name.trim().length() > 100) {
            throw new IllegalArgumentException("Invalid name!");
        }
        this.name = name;
    }

    /**
     * Updates the price of the product
     * @param price the new price
     * @throws IllegalArgumentException if the price is invalid
     */
    public void setPrice(double price) {
        if(price <= 0.0) {
            throw new IllegalArgumentException("Price may not be negative!");
        }
        this.price = price;
    }

    /**
     * Updates the ID
     * @param id_product the new ID
     * @throws IllegalArgumentException if ID is invalid
     */
    public void setId_product(int id_product) {
        if(id_product <= 0) {
            throw new IllegalArgumentException("Id may not be negative!");
        }
        this.id_product = id_product;
    }

    /**
     * @return a string representation of the product in a structured format.
     */
    @Override

    public String toString() {
        return String.format(Locale.US,"{class:Product, id:%d, name:'%s', category:%s, price:%.1f}",
                id_product, name, category, price);
    }


    /**
     * Associates this product with a catalog
     * @param catalog the catalog this product belongs to
     */
    public void setBelongToCatalog(Catalog catalog) {
        this.belongToCatalog = catalog;

    }

    /**
     * @return the catalog to which this product belongs
     */
    private Catalog getBelongToCatalog(){
        return belongToCatalog;
    }
}
