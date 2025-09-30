package es.upm.etsisi.poo;
import java.util.ArrayList;
public class Ticket {

    final int prodMax = 100;
    private int numProductsMerch;
    private int numProductsPapeleria;
    private int numProductsRopa;
    private int numProductsElectronica;
    private int numProductsLibro;
    private double precioTotal;
    private double descuentoTotal;
    private double precioFinal;
    private ArrayList<Product> ticketProducts;


    public Ticket(){
        this.numProductsMerch = 0;
        this.numProductsElectronica = 0;
        this.numProductsLibro = 0;
        this.numProductsRopa = 0;
        this.numProductsPapeleria = 0;
        this.ticketProducts = new ArrayList<>(prodMax);
        this.precioTotal = 0;
        this.descuentoTotal = 0;


    }
    public boolean ticketAdd(Product product, int cantidad){
        boolean x;

        if (product.getPerteneceAAlmacen().existeId(product.getId())) {
            for (int i = 0; i < cantidad; i++) {
                ticketProducts.add(product);
                precioTotal += product.getPrice();
                if(product.getCategory().equals("MERCH")){
                    numProductsMerch++;
                } else if (product.getCategory().equals("STATIONERY")) {
                    numProductsPapeleria++;
                } else if (product.getCategory().equals("ELECTRONICS")){
                    numProductsElectronica++;
                } else if (product.getCategory().equals("BOOK")){
                    numProductsLibro++;
                } else if (product.getCategory().equals("CLOTHES")){
                    numProductsRopa++;
                }
            }
            this.ticketPrint(false);
            x = true;
        }else{
            System.out.println("The given id doesn't exist");
            x = false;
        }

        return x;
    }
    public boolean idLibre(int id){

        boolean x = true;
        if(!ticketProducts.isEmpty()){
            for (int i = 0; i < ticketProducts.size(); i++) {
                if(id == ticketProducts.get(i).getId()){
                    x = false;
                }
            }
        }
        return x;
    }

    public boolean ticketRemove(int id){
        boolean x = false;
        if (!idLibre(id)) {
            for (int i = ticketProducts.size() - 1; i >= 0; i--) {
                if (id == ticketProducts.get(i).getId()) {
                    precioTotal -= ticketProducts.get(i).getPrice();
                    if (ticketProducts.get(i).getCategory().equals("MERCH")) {
                        numProductsMerch--;

                    } else if (ticketProducts.get(i).getCategory().equals("STATIONERY")) {
                        numProductsPapeleria--;

                    } else if (ticketProducts.get(i).getCategory().equals("CLOTHES")) {
                        numProductsRopa--;

                    } else if (ticketProducts.get(i).getCategory().equals("BOOK")) {
                        numProductsLibro--;

                    } else if (ticketProducts.get(i).getCategory().equals("ELECTRONICS")) {
                        numProductsElectronica--;
                    }
                    ticketProducts.remove(i);
                    x = true;
                }
            }
        }else {
            System.out.println("The id given doesn't exist");
        }
        return x;
    }

    public boolean ticketNew (){
        boolean x = false;
        while (!ticketProducts.isEmpty()){
            ticketProducts.remove(0);
        }
        numProductsElectronica = 0;
        numProductsLibro = 0;
        numProductsRopa = 0;
        numProductsPapeleria = 0;
        numProductsMerch = 0;
        precioTotal = 0;
        descuentoTotal = 0;
        x = true;
        return x;
    }

    public boolean ticketPrint ( boolean reset) {
        final double descuentoMerch = 0;
        final double descuentoPapeleria = 0.05;
        final double descuentoRopa = 0.07;
        final double descuentoLibro = 0.1;
        final double descuentoElectronica = 0.03;
        boolean x = false;

        if (!ticketProducts.isEmpty()) {

            for (int i = ticketProducts.size() - 1; i >= 0; i--) {

                if (ticketProducts.get(i).getCategory().equals("MERCH")) {
                    if (numProductsMerch > 1 && descuentoMerch > 0) {
                        ticketProducts.get(i).printInfoProd();
                        System.out.printf(" **discount -%.2f%n", descuentoMerch * ticketProducts.get(i).getPrice());
                        descuentoTotal += descuentoMerch * ticketProducts.get(i).getPrice();
                    } else {
                        ticketProducts.get(i).printInfoProd();
                        System.out.println();
                    }

                } else if (ticketProducts.get(i).getCategory().equals("STATIONERY")) {
                    if (numProductsPapeleria > 1 && descuentoPapeleria > 0) {
                        ticketProducts.get(i).printInfoProd();
                        System.out.printf(" **discount -%.2f%n", descuentoPapeleria * ticketProducts.get(i).getPrice());
                        descuentoTotal += descuentoPapeleria * ticketProducts.get(i).getPrice();
                    } else {
                        ticketProducts.get(i).printInfoProd();
                        System.out.println();
                    }

                } else if (ticketProducts.get(i).getCategory().equals("CLOTHES")) {
                    if (numProductsRopa > 1 && descuentoRopa > 0) {
                        ticketProducts.get(i).printInfoProd();
                        System.out.printf(" **discount -%.2f%n", descuentoRopa * ticketProducts.get(i).getPrice());
                        descuentoTotal += descuentoRopa * ticketProducts.get(i).getPrice();
                    } else {
                        ticketProducts.get(i).printInfoProd();
                        System.out.println();
                    }

                } else if (ticketProducts.get(i).getCategory().equals("BOOK")) {
                    if (numProductsLibro > 1 && descuentoLibro > 0) {
                        ticketProducts.get(i).printInfoProd();
                        System.out.printf(" **discount -%.2f%n", descuentoLibro * ticketProducts.get(i).getPrice());
                        descuentoTotal += descuentoLibro * ticketProducts.get(i).getPrice();
                    } else {
                        ticketProducts.get(i).printInfoProd();
                        System.out.println();
                    }

                } else if (ticketProducts.get(i).getCategory().equals("ELECTRONICS")) {
                    if (numProductsElectronica > 1 && descuentoElectronica > 0) {
                        ticketProducts.get(i).printInfoProd();
                        System.out.printf(" **discount -%.2f%n", descuentoElectronica * ticketProducts.get(i).getPrice());
                        descuentoTotal += descuentoElectronica * ticketProducts.get(i).getPrice();
                    } else {
                        ticketProducts.get(i).printInfoProd();
                        System.out.println();
                    }
                }
            }
            precioFinal = precioTotal - descuentoTotal;
            System.out.printf("Total price: %.2f%n", precioTotal);
            System.out.printf("Total discount: %.2f%n", descuentoTotal);
            System.out.printf("Final Price: %.2f%n", precioFinal);
            if (reset){
                this.ticketNew();
            }
        }else{
            System.out.println("The ticket is empty");
        }

        x = true;
        return x;

    }



    public static void main(String[] args) {

        Ticket ticket = new Ticket();
        Almacen almacen = new Almacen();
        Product product = new Product(43, "Merch", 7.5, "MERCH");
        Product product1 = new Product(22, "Boli", 23, "STATIONERY");
        Product product2 = new Product(27, "Ordenador", 51, "ELECTRONICS");
        Product product3 = new Product(13, "Libro", 33, "BOOK");
        almacen.addProd(product);
        almacen.addProd(product1);
        almacen.addProd(product2);
        almacen.addProd(product3);
        ticket.ticketAdd(product, 1);
        ticket.ticketAdd(product1, 2);
        ticket.ticketAdd(product2, 1);
        ticket.ticketAdd(product3, 3);
        System.out.println("a");
        ticket.ticketPrint(true);
        ticket.ticketAdd(product1, 2);
        ticket.ticketAdd(product2, 1);
        ticket.ticketAdd(product1, 2);
        System.out.println(ticket.ticketProducts.size());
        ticket.ticketRemove(22);
        System.out.println("a");
        ticket.ticketPrint(false);
        System.out.println("a");
        ticket.ticketNew();
        ticket.ticketPrint(false);

        System.out.println(ticket.ticketProducts.size());
        ticket.ticketAdd(product3, 3);

    }

}
