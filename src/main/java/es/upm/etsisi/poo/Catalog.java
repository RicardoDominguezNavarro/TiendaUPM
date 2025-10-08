package es.upm.etsisi.poo;

import java.util.ArrayList;

/**
 *
 */
public class Catalog {

    public ArrayList<Product> products;

    /**
     *
     */
    public Catalog() {
        products = new ArrayList<>(200);

    }

    /**
     * @param id
     * @return
     */
    public boolean prodRemove(int id) {
        if (isIdFree(id)) {//Si el id no está free == no existe
            System.out.println("The id doesn't exist");
            return false;
        } else {
            int position = positionProd(id);
            if (position < 0) {
                System.out.println("The id doesn't exist");
                return false;
            }
            products.remove(position);
            System.out.println("prod remove: ok");
            return true;
        }
    }

    /**
     * @param id
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
                productToChange.setName(value);
                System.out.println(productToChange.toString());
                System.out.println("prod update: ok");
                break;
            case "CATEGORY":
                try {
                    // convertir a mayúsculas por si el usuario pasa minúsculas
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
        if (isIdFree(product.getId())) { //si el id está libre se puede añadir el producto
            products.add(product);
            product.setBelongToCatalog(this); //añade el producto al catálogo
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
        /*
        Devuelve true si NO existe el id
         */
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


