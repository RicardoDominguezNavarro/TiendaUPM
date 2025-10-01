package es.upm.etsisi.poo;

import java.util.ArrayList;

public class Catalog {

    public ArrayList<Product> products;

    public Catalog() {
        products = new ArrayList<>(200);

    }

    public boolean prodRemove(int id) {
        if (idLibre(id)) {
            System.out.println("El id dado no está asignado a ningún producto");
            return false;
        } else {
            int posicion = posicionProd(id);
            products.remove(posicion);
            return true;
        }
    }

    public void updateProd(int id, String campo, String value) {
        Product productoACambiar = dadoIdDevolverProducto(id);
        String campoUpper = campo.toUpperCase();
        switch (campoUpper) {
            case "NAME":
                productoACambiar.setName(value);
                break;
            case "CATEGORY":
                productoACambiar.setCategory(value);
                break;
            case "PRICE":
                try {
                    productoACambiar.setPrice(Double.parseDouble(value));
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
        boolean x;
        if (idLibre(product.getId())) {
            products.add(product);
            product.setBelongToCatalog(this);
            x = true;
        } else {
            System.out.println("El número de id asignado ya pertenece a otro producto");
            x = false;
        }
        return x;
    }


    public ArrayList<Product> getProducts() {
        return products;
    }

    public boolean idLibre(int id) {

        boolean x = true;
        if (!products.isEmpty()) {
            for (int i = 0; i < products.size(); i++) {
                if (id == products.get(i).getId()) {
                    x = false;
                }
            }
        }
        return x;
    }

    public Product dadoIdDevolverProducto(int id) {
        Product devolver = null;
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == id) {
                devolver = products.get(i);
            }
        }
        return devolver;

    }

    public int posicionProd(int id) {
        int contador = 0;
        int posicion = 0;
        for (int i = 0; i < products.size(); i++) {
            if (id == products.get(i).getId()) {
                posicion = contador;
            }
            contador++;
        }
        return posicion;
    }

    public boolean existeId(int id) {
        boolean x = false;
        for (int i = 0; i < products.size(); i++) {
            if (id == products.get(i).getId()) {
                x = true;
            }

        }
        return x;
    }


}

}
