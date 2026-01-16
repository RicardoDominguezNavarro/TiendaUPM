package es.upm.etsisi.poo;

import java.util.ArrayList;
import java.util.Random;


public class Catalog {
    private static Catalog instance;

    private final int MAXPRODUCTS = 200;

    private int numProducts;

    public ArrayList<Product> products;

    private Catalog() {
        products = new ArrayList<>(200);
    }

    public static Catalog getInstance() {
        if (instance == null){
            instance = new Catalog();
        }
        return instance;
    }

    public void prodRemove(String id) {
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

    public void updateProd(String id, String field, String value) {
        if (field == null || value == null) {
            System.out.println("Error: field or value is null");
            return;
        }
        Product productToChange = getProductId(id);
        if (productToChange == null) {
            System.out.println("Error: the product with the ID " + id + " doesn't exist");
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
                    // Si es Event o Service no se puede cambiar la categoría
                    if (productToChange instanceof Events){
                        System.out.println("Error: Product type: Event. Events can not change category.");
                    } else if (productToChange instanceof Service) {
                        System.out.println("Error: Product type: Service. Services do not use category.");
                    } else {
                        Category category = Category.valueOf(value.trim().toUpperCase());
                        productToChange.setCategory(category);
                        System.out.println(productToChange.toString());
                        System.out.println("prod update: ok");
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: The category " + value + " doesn't exist.");
                }
                break;
            case "PRICE":
                try {
                    double price = Double.parseDouble(value);
                    productToChange.setPrice(Double.parseDouble(value));
                    if (price <= 0.0) {
                        System.out.println("Error: The price may not be negative or zero.");
                    } else {
                        productToChange.setPrice(price);
                        System.out.println(productToChange.toString());
                        System.out.println("prod update: ok");
                    }

                } catch (NumberFormatException e) {
                    System.out.println("Error: The price is not valid.");
                }

                break;
            default:
                System.out.println("Error: Invalid field. Valid fields are NAME, CATEGORY, or PRICE.");
                break;
        }
    }

    public void prodList() {
        System.out.println("prod list");
        System.out.println("Catalog:");
        for (int i = 0; i < products.size(); i++) {
            System.out.println("  " + products.get(i).toString());
        }
        System.out.println("prod list: ok");
    }

    public void addProd(Product product) {
        if (numProducts >= MAXPRODUCTS) {
            System.out.println("Cannot add product: catalog max capacity reached.");
            return;
        }
        if (isIdFree(product.getId_product())) {

            System.out.println("prod add " + product.getId_product() + " '" + product.getName() + "' " +  product.getCategory() + " " + product.getPrice());
            products.add(product);

            System.out.println(product.toString());
            numProducts++;

            if (product instanceof Events){
                Events event = (Events) product;
                if (event.getEventType() == Events.EventType.MEETING){
                    System.out.println("prod addMeeting: ok");
                }else {
                    System.out.println("prod addFood: ok");
                }
            } else if (product instanceof Service) {
                System.out.println("prod add: ok");
            } else {
                System.out.println("prod add: ok");
            }
        } else {
            System.out.println("The id belongs to another product");
        }

    }

    public ArrayList<Product> getProducts() {
        return products;
    }


    public int positionProd(String id) {
        for (int i = 0; i < products.size(); i++) {
            if (id.equals(products.get(i).getId_product())) {
                return i;
            }
        }
        return -1;
    }

    public boolean isIdFree(String id) {
        if (!products.isEmpty()) {
            for (Product product : products) {
                if (id.equals(product.getId_product())) {
                    return false;
                }
            }
        }
        return true;
    }


    public Product getProductId(String id) {
        for (Product actual : products) {
            if (actual.getId_product().equals(id)) {
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
        } while (!isIdFree(String.valueOf(id))); // Convertimos a String para comprobar si está libre
        return id;
    }
}