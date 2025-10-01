package es.upm.etsisi.poo;

import java.util.ArrayList;

public class Catalog {
package es.upm.etsisi.poo;
import java.util.ArrayList;
    public class Catalog {
        public static void main(String[] args) {
            es.upm.etsisi.poo.Catalog catalog = new es.upm.etsisi.poo.Catalog();
            Product product = new Product(43, "Merch", 7.5, "MERCH");
            Product product1 = new Product(22, "Boli", 23, "STATIONERY");
            Product product2 = new Product(27, "Ordenador", 51, "ELECTRONICS");
            Product product3 = new Product(13, "Libro", 33, "BOOK");
            catalog.addProd(product);
            catalog.addProd(product1);
            catalog.addProd(product2);
            catalog.addProd(product3);
            catalog.prodList();
            Product prueba = catalog.dadoIdDevolverProducto(22);
            System.out.println(prueba.getName());
            catalog.updateProd(43, "name", "Armario");
            catalog.updateProd(22, "price", "7.9");
            catalog.prodList();
            catalog.prodRemove(22);
            catalog.prodList();
            System.out.println(products.size());
            System.out.println(product.getId());
            if (catalog.existeId(43)){
                System.out.println("vamos");
            }
            if (catalog.existeId(111)){
                System.out.println("mal");
            }


        }

        public static ArrayList<Product> products;

        public Catalog(){
            products = new ArrayList<>(200);

        }
        public boolean prodRemove(int id){
            if(idLibre(id)){
                System.out.println("El id dado no está asignado a ningún producto");
                return false;
            }else{
                int posicion = posicionProd(id);
                products.remove(posicion);
                return true;
            }
        }

        public void updateProd(int id, String campo, String value){
            Product productoACambiar = dadoIdDevolverProducto(id);
            String campoUpper = campo.toUpperCase();
            switch (campoUpper){
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
        public void prodList (){
            System.out.println("Catalog:");
            for (int i = 0; i < products.size() ; i++) {
                products.get(i).printInfoProd();
                System.out.println();
            }
        }
        public boolean addProd(Product product){
            boolean x;
            if(idLibre(product.getId())) {
                products.add(product);
                product.setBelongsToCatalog(this);
                x = true;
            }else{
                System.out.println("El número de id asignado ya pertenece a otro producto");
                x = false;
            }
            return x;
        }


        public ArrayList<Product> getProducts(){
            return products;
        }

        public boolean idLibre(int id){

            boolean x = true;
            if(!products.isEmpty()){
                for (int i = 0; i < products.size(); i++) {
                    if(id == products.get(i).getId()){
                        x = false;
                    }
                }
            }
            return x;
        }

        public Product dadoIdDevolverProducto(int id){
            Product devolver = null;
            for (int i = 0; i < products.size(); i++) {
                if(products.get(i).getId() == id){
                    devolver = products.get(i);
                }
            }
            return devolver;

        }

        public int posicionProd(int id){
            int contador = 0;
            int posicion = 0;
            for (int i = 0; i < products.size(); i++) {
                if (id == products.get(i).getId()){
                    posicion = contador;
                }
                contador++;
            }
            return posicion;
        }

        public boolean existeId (int id){
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
