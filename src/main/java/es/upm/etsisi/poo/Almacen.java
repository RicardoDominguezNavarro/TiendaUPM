package es.upm.etsisi.poo;
import java.util.ArrayList;
public class Almacen {
    public static void main(String[] args) {
        Almacen almacen = new Almacen();
        Product product = new Product(43, "Mesa", 7.5, "MERCH");
        Product product1 = new Product(22, "Silla", 23, "PAPELERIA");
        Product product2 = new Product(27, "Cama", 51, "ELECTRONICA");
        Product product3 = new Product(13, "Escritorio", 33, "MUEBLE");
        almacen.addProd(product);
        almacen.addProd(product1);
        almacen.addProd(product2);
        almacen.addProd(product3);
        almacen.prodList();
        Product prueba = almacen.dadoIdDevolverProducto(22);
        System.out.println(prueba.getName());
        almacen.updateProd(43, "name", "Armario");
        almacen.updateProd(22, "price", "7.9");
        almacen.prodList();
        almacen.prodRemove(22);
        almacen.prodList();
        System.out.println(products.size());
        System.out.println(product.getId());
        if (almacen.existeId(43)){
            System.out.println("vamos");
        }
        if (almacen.existeId(111)){
            System.out.println("mal");
        }


    }

    public static ArrayList<Product> products;

    public Almacen(){
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
    public boolean prodList (){
        boolean x;
        System.out.println("Catalog:");
        for (int i = 0; i < products.size() ; i++) {
            products.get(i).printInfoProd();
        }
        return true;
    }
    public boolean addProd(Product product){
        boolean x;
        if(idLibre(product.getId())) {
            products.add(product);
            product.setPerteneceAAlmacen(this);
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
        if(products.size() > 0){
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
