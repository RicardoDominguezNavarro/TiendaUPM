package es.upm.etsisi.poo;
import java.util.ArrayList;
public class Ticket {
    final int prodMax = 100;
    private int numProductsMerch;
    private int numProductsPapeleria;
    private int numProductsRopa;
    private int numProductsElectronica;
    private int numProductsLibro;
    double precioMerch;
    double precioPapeleria;
    double precioRopa;
    double precioLibro;
    double precioElectronica;
    private double precioTotal;
    private double descuentoTotal;
    private double precioFinal;
    private ArrayList<Product> ticketProducts;


    public Ticket(){
        this.precioMerch = 0;
        this.precioPapeleria = 0;
        this.precioRopa = 0;
        this.precioLibro = 0;
        this.precioElectronica =0;
        this.numProductsMerch = 0;
        this.numProductsElectronica = 0;
        this.numProductsLibro = 0;
        this.numProductsRopa = 0;
        this.numProductsPapeleria = 0;
        this.ticketProducts = new ArrayList<>(prodMax);
        this.precioTotal = 0;
        this.descuentoTotal = 0;
        this.precioFinal = 0;

    }
    public boolean ticketAdd(Product product, int cantidad){
        boolean x = false;

        if (product.getPerteneceAAlmacen().existeId(product.getId())) {
            for (int i = 0; i < cantidad; i++) {
                ticketProducts.add(product);
                precioTotal += product.getPrice();
                if(product.getCategory() == "MERCH"){
                    numProductsMerch++;
                    precioMerch += product.getPrice();
                } else if (product.getCategory() == "PAPELERIA") {
                    numProductsPapeleria++;
                    precioPapeleria += product.getPrice();
                } else if (product.getCategory() == "ELECTRONICA"){
                    numProductsElectronica++;
                    precioElectronica += product.getPrice();
                } else if (product.getCategory() == "LIBRO"){
                    numProductsLibro++;
                    precioLibro += product.getPrice();
                } else if (product.getCategory() == "ROPA"){
                    numProductsRopa++;
                    precioRopa += product.getPrice();
                }
            }
            x = true;
        }
        return x;
    }

    public boolean ticketRemove(int id){
        boolean x = false;
        for (int i = 0; i < ticketProducts.size(); i++){
            if (id == ticketProducts.get(i).getId()){
                ticketProducts.remove(i);
                x = true;
            }
        }
        return x;
    }

    public boolean ticketNew (){
        boolean x = false;
        for (int i = 0; i < ticketProducts.size(); i++) {
            ticketProducts.remove(i);
        }
        if (ticketProducts.size() == 0){
            x = true;
        }
        return x;
    }

    public void ticketPrint (){
        double descuentoMerch = 0;
        double descuentoPapeleria = 0.05;
        double descuentoRopa = 0.07;
        double descuentoLibro = 0.1;
        double descuentoElectronica = 0.03;


        precioTotal = precioElectronica + precioMerch + precioLibro + precioPapeleria + precioRopa;

        for (int i = 0; i < ticketProducts.size() ; i++) {

            switch (ticketProducts.get(i).getCategory()){
                case "Merch":
                    if (numProductsMerch > 1){
                        ticketProducts.get(i).printInfoProd();
                        System.out.println("");
                    }else{
                        ticketProducts.get(i).printInfoProd();
                        System.out.println();
                    }
            }

        }

    }


    public static void main(String[] args) {
        Ticket ticket = new Ticket();
        Almacen almacen = new Almacen();
        Product product = new Product(43, "Mesa", 7.5, "MERCH");
        Product product1 = new Product(22, "Silla", 23, "PAPELERIA");
        Product product2 = new Product(27, "Cama", 51, "ELECTRONICA");
        Product product3 = new Product(13, "Escritorio", 33, "MUEBLE");
        almacen.addProd(product);
        almacen.addProd(product1);
        almacen.addProd(product2);
        almacen.addProd(product3);
        ticket.ticketAdd(product, 5);
        ticket.ticketAdd(product1, 7);
        ticket.ticketAdd(product2, 3);
        ticket.ticketAdd(product3, 1);





    }

}
