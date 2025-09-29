package es.upm.etsisi.poo;
import java.util.Scanner;
public class Product {
    Scanner teclado = new Scanner(System.in);
    private Almacen perteneceAAlmacen;
    private int id;
    private String name;
    private double price;
    private String category;




    /*
    controlar excepciones
     */
    public Product(int id, String name, double price, String category) {
        while(id>200 || id<0){
            System.out.println("El id debe encontrarse entre 0 y 200");
            id = teclado.nextInt();
        }
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;

    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Almacen getPerteneceAAlmacen() {
        return perteneceAAlmacen;
    }

    public void setPerteneceAAlmacen(Almacen perteneceAAlmacen) {
        this.perteneceAAlmacen = perteneceAAlmacen;
    }


    public void printInfoProd(){
        System.out.print("{class:Product, id:" + this.getId() + ", name:" + this.getName() + ", category:" + this.getCategory() + ", price:" + this.getPrice() + "}");

    }
}