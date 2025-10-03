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
                    System.out.println("The price is not valid.");
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
    /*
    public void update(int id, String field, String value) {
        Product product = null;
        int position = -1;

        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == id) {
                product = products.get(i);
                position = i;
                break;
            }
        }

        if (product != null) {
            if (field == null || value == null) {
                System.out.println("The field or value is null!.");
                return;
            }
            switch (field.toLowerCase()) {
                case "name":
                    product.setName(value);
                    break;
                case "category":
                    try {
                        product.setCategory(Category.valueOf(value.toUpperCase()));
                    } catch (IllegalArgumentException e) {
                        System.out.println("The category \"" + value + "\" doesn't exist.");
                    }
                    break;
                case "price":
                    try {
                        double price = Double.parseDouble(value);
                        if (price < 0) {
                            System.out.println("The price may not be negative.");
                        } else {
                            product.setPrice(price);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("The value \"" + value + "\" is not a valid number.");
                    }
                    break;
                default:
                    System.out.println("The field \"" + field + "\" is not recognized.");
                    break;
            }
            products.set(position, product);

        } else {
            System.out.println("The product with the ID " + id + " is not found.");
        }
    }



    public void prodAdd(int id, String name, double price, Category category) {
        if(id <= 0) { //id mayor que 0
            throw new IllegalArgumentException("Id may not be negative!");
        }
        if(name == null || name.trim().isEmpty() || name.trim().length() >= 100) {
            //el nombre no sea null, quita los espacios en blanco para ver si está vacía y quita los
            // espacios y cuenta caracteres para que no haya más de 100
            throw new IllegalArgumentException("Invalid name!");
        }
        if(price <= 0.0) { //precio mayor que 0
            throw new IllegalArgumentException("Price may not be negative!");
        }
        if(category == null) { //categoria no es null
            throw new IllegalArgumentException("Category may not be null!");
        }
        Product product= new Product(id,name,price,category);
        products.add(product);
    }
    public void prodRemove(int id){
        if (!products.isEmpty()){
            int objective=0;
            if(id <= 0) { //id mayor que 0
                throw new IllegalArgumentException("Id may not be negative!");
            }
            if (products.size() >= id){
                for (int i = 0; i < products.size(); i++) {
                    if (products.get(i).getId() == id){
                        objective = i;
                    }
                }
                products.remove(objective);
            }

        }
   }
     */


}


