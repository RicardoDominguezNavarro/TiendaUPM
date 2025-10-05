package es.upm.etsisi.poo;

import java.util.ArrayList;

public class Catalog {

    public ArrayList<Product> products;

    public Catalog() {
        products = new ArrayList<>(200);

    }

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

public void updateProd(int id, String field, String value) {
        if(field == null || value == null) {
            System.out.println("field or value is null");
            return;
        }
        Product productToChange = getProductId(id);
        if(productToChange == null){
            System.out.println("The product with the ID " + id + " doesn't exist");
            return;
        }
        field = field.toUpperCase();
        switch (field) {
            case "NAME":
                productToChange.setName(value);
                System.out.println("product update: ok");
                break;
            case "CATEGORY":
                try {
                    // convertir a mayúsculas por si el usuario pasa minúsculas
                    Category category = Category.valueOf(value.trim().toUpperCase());
                    productToChange.setCategory(category);
                    System.out.println("product update: ok");
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
                        System.out.println("product update: ok");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("The price is not valid.");
                    break;
                }

        }
    }



    public void prodList() {
        System.out.println("Catalog:");
        for (int i = 0; i < products.size(); i++) {
            System.out.println(products.get(i).toString());
            System.out.println();
        }
    }

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

    public int positionProd(int id) {
        for (int i = 0; i < products.size(); i++) {
            if (id == products.get(i).getId()) {
                return i;
            }

        }
        return -1;
    }

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


