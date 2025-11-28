package es.upm.etsisi.poo;

import java.util.ArrayList;
import java.util.Random;

/**
 *This class manage a collection of products objects.
 */
public class Catalog {
    /**
     * Maximum number of products in a catalog
     */
    private final int MAXPRODUCTS = 200;
    /**
     * number of products in a catalog
     */
    private int numProducts;
    /**
     *List that stores products in this catalog.
     */
    public ArrayList<Product> products;
    /**
     *Construct an empty catalog.
     * Initializes the internal ArrayList with an initial capacity of 200
     */
    public Catalog() {
        products = new ArrayList<>(200);
    }
    /**
     * Remove the product with the given id from the catalog.
     *
     * @param id product identifier to remove.
     */
    public void prodRemove(int id) {
        if (isIdFree(id)) {
            System.out.println("The id doesn't exist");
        } else {
            int position = positionProd(id);
            if (position < 0) {
                System.out.println("The id doesn't exist");
            }else{
                System.out.println("prod remove " + id);
                Product productRemove = products.get(position);
                System.out.println(productRemove.toString());
                products.remove(position);
                numProducts--;
                System.out.println("prod remove: ok");
            }
        }
    }

    /**
     * Update a field of the product identified by id.
     *
     * @param id product identifier to update.
     * @param field The field name to modify ("NAME", "CATEGORY", or "PRICE").
     * @param value The new value for the specified field.
     */
    public void updateProd(int id, String field, String value) {
        if (field == null || value == null) {
            System.out.println("field or value is null");
            return;
        }
        Product productToChange = getProductId(id);
        if (productToChange == null) {
            System.out.println("The product with the ID " + id + " doesn't exist");
            return;
        }
        System.out.println("prod update " + productToChange.getId_product() + " '" +  productToChange.getName() + "' " + productToChange.getPrice());
        field = field.toUpperCase();
        switch (field) {
            case "NAME":
                value = value.trim();
                if (value.startsWith("\"") && value.endsWith("\"")) {
                    value = value.substring(1, value.length() - 1);
                }
                productToChange.setName(value);
                System.out.println(productToChange.toString());
                System.out.println("prod update: ok");
                break;
            case "CATEGORY":
                try {
                    if (productToChange instanceof Events){
                        System.out.println("Product type: Event. Events can not change category.");
                    } else {
                        Category category = Category.valueOf(value.trim().toUpperCase());
                        productToChange.setCategory(category);
                        System.out.println(productToChange.toString());
                        System.out.println("prod update: ok");
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("The category " + value + " doesn't exist.");
                }
                break;
            case "PRICE":
                try {
                    double price = Double.parseDouble(value);
                    //productToChange.setPrice(Double.parseDouble(value));
                    if (price <= 0.0) {
                        System.out.println("The price may not be negative or zero.");
                    } else {
                        productToChange.setPrice(price);
                        System.out.println(productToChange.toString());
                        System.out.println("prod update: ok");
                    }

                } catch (NumberFormatException e) {
                    System.out.println("The price is not valid.");
                }

                break;
            default:
                System.out.println("Invalid field. Valid fields are NAME, CATEGORY, or PRICE.");
                break;
        }
    }

    /**
     * Display all products currently in the catalog.
     */
    public void prodList() {
        System.out.println("prod list");
        System.out.println("Catalog:");
        for (int i = 0; i < products.size(); i++) {
            System.out.println(products.get(i).toString());
        }
        System.out.println("prod list: ok");
    }

    /**
     * Adds a new product to the catalog if the id is not already in use.
     * @param product The product object to be added.
     */
    public void addProd(Product product) {
        if (numProducts >= MAXPRODUCTS) {
            System.out.println("Cannot add product: catalog max capacity reached.");
            return;
        }
        if (isIdFree(product.getId_product())) {
            System.out.println("prod add " + product.getId_product() + " " + product.getName() + " " +  product.getCategory() + " " + product.getPrice());
            products.add(product);
            product.setBelongToCatalog(this);
            System.out.println(product.toString());
            numProducts++;
            System.out.println("prod add: ok");
        } else {
            System.out.println("The id belongs to another product");
        }
    }

    /**
     * @return the list of all products in the catalog
     */
    public ArrayList<Product> getProducts() {
        return products;

    }

    /**
     * @param id the id to search for.
     * @return the position of the product if found, or -1 if not present.
     */
    public int positionProd(int id) {
        for (int i = 0; i < products.size(); i++) {
            if (id == products.get(i).getId_product()) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Checks whether an ID is free (not associated with any product).
     * @param id of the product to verify.
     * @return true if no product with the id exists, false otherwise.
     */
    public boolean isIdFree(int id) {
        if (!products.isEmpty()) {
            for (Product product : products) {
                if (id == product.getId_product()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Search for a product in the catalog by its id.
     * @param id the id of the product to find.
     * @return the product that matches the given id, or null if not match is found.
     */
    public Product getProductId(int id) {
        for (Product actual : products) {
            if (actual.getId_product() == id) {
                return actual;
            }
        }
        return null;
    }


    public int generateId() {
        Random random = new Random();
        int id;
        do {
            id = random.nextInt(1000) + 1;
        } while (!isIdFree(id)); // Repite si el ID ya existe
        return id;
    }
}