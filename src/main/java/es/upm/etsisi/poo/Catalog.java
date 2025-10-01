package es.upm.etsisi.poo;

import java.util.ArrayList;

public class Catalog {

    public ArrayList<Product> products;

    public Catalog() {
        products = new ArrayList<>(200);

    }

    public boolean prodRemove(int id) {
        if (existId(id)) {
            System.out.println("The id doesn't exist");
            return false;
        } else {
            int position = positionProd(id);
            products.remove(position);
            return true;
        }
    }

    public void updateProd(int id, String field, String value) {
        Product productToChange = getProductId(id);
        field = field.toUpperCase();
        switch (field) {
            case "NAME":
                productToChange.setName(value);
                break;
            case "CATEGORY":
                productToChange.setCategory(value);
                break;
            case "PRICE":
                try {
                    productToChange.setPrice(Double.parseDouble(value));
                } catch (NumberFormatException e) {
                    System.out.println("Precio inválido.");
                    break;
                }

        }
    }

    public void prodList() {
        System.out.println("Catalog:");
        for (int i = 0; i < products.size(); i++) {
            products.get(i).toString();
            System.out.println();
        }
    }

    public boolean addProd(Product product) {
        boolean check;
        if (existId(product.getId())) {
            products.add(product);
            product.setBelongToCatalog(this);
            check = true;
        } else {
            System.out.println("The id belongs to another product");
            check = false;
        }
        return check;
    }

    public ArrayList<Product> getProducts() {
        return products;

    }

    public int positionProd(int id) {
        int position = 0;
        for (int i = 0; i < products.size(); i++) {
            if (id == products.get(i).getId()) {
                position = i;
            }

        }
        return position;
    }

    public boolean existId(int id) {
        boolean free = true;
        if (!products.isEmpty()) {
            for (int i = 0; i < products.size(); i++) {
                if (id == products.get(i).getId()) {
                    free = false;
                }
            }
        }else {
            System.out.println("The catalog is empty!");
        }
        return free;
    }
    public Product getProductId (int id){
        Product result = null;
        for (int i = 0; i < products.size(); i++) {
            Product actual = products.get(i);
            if (actual.getId()==id){
                result = actual;
            }
        }
        return  result;
    }


}


