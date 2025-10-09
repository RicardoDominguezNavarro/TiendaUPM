package es.upm.etsisi.poo;

import java.util.ArrayList;

/**
 *This class manage a collection of products objects.
 */
public class Catalog {
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
     * @return true if the product is removed succeeded, false if the id does not exist.
     */
    public boolean prodRemove(int id) {
        if (isIdFree(id)) {
            System.out.println("The id doesn't exist");
            return false;
        } else {
            int position = positionProd(id);
            if (position < 0) {
                System.out.println("The id doesn't exist");
                return false;
            }
            Product productRemove = products.get(position);
            System.out.println(productRemove.toString());
            products.remove(position);
            System.out.println("prod remove: ok");
            return true;
        }
    }

    /**
     * Update a field of the product identified by id
     *
     * @param id product identifier to update
     * @param field
     * @param value
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
                    Category category = Category.valueOf(value.trim().toUpperCase());
                    productToChange.setCategory(category);
                    System.out.println(productToChange.toString());
                    System.out.println("prod update: ok");
                } catch (IllegalArgumentException e) {
                    System.out.println("The category \"" + value + "\" doesn't exist.");
                }
                break;
            case "PRICE":
                try {
                    double price = Double.parseDouble(value);
                    productToChange.setPrice(Double.parseDouble(value));
                    if (price <= 0.0) {
                        System.out.println("The price may not be negative or zero.");
                    } else {
                        productToChange.setPrice(price);
                        System.out.println(productToChange.toString());
                        System.out.println("prod update: ok");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("The price is not valid.");
                    break;
                }
        }
    }

    /**
     *
     */
    public void prodList() {
        System.out.println("Catalog:");
        for (int i = 0; i < products.size(); i++) {
            System.out.println(products.get(i).toString());
        }
        System.out.println("prod list: ok");
    }

    /**
     * @param product
     * @return
     */
    public boolean addProd(Product product) {
        boolean check;
        if (isIdFree(product.getId())) {
            products.add(product);
            product.setBelongToCatalog(this);
            check = true;
            System.out.println(product.toString());
            System.out.println("prod add: ok");
        } else {
            System.out.println("The id belongs to another product");
            check = false;
        }
        return check;
    }

    public ArrayList<Product> getProducts() {
        return products;

    }

    /**
     * @param id
     * @return
     */
    public int positionProd(int id) {
        for (int i = 0; i < products.size(); i++) {
            if (id == products.get(i).getId()) {
                return i;
            }
        }
        return -1;
    }

    /**
     * @param id
     * @return
     */
    public boolean isIdFree(int id) {
        if (!products.isEmpty()) {
            for (int i = 0; i < products.size(); i++) {
                if (id == products.get(i).getId()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @param id
     * @return
     */
    public Product getProductId(int id) {
        Product result = null;
        for (int i = 0; i < products.size(); i++) {
            Product actual = products.get(i);
            if (actual.getId() == id) {
                result = actual;
            }
        }
        return result;
    }
}


