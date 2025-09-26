package es.upm.etsisi.poo;

public class Product {

    private int id;
    private String name;
    private double price;
    private int numProducts = 0;
    private Category category;

    public enum Category{
        MERCH, PAPELERIA, ROPA, LIBRO, ELECTRONICA
    }
    /*
    controlar excepciones
     */
    public Product(int id, String name, double price, Category category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        numProducts +=1;
    }
    public Product() {
        this.id = -1;
        /*no sé si esto está bien pero para controlar que no se guarde ningún producto
        vacío(luego hay que comprobar que el id sea positivo).
        Mirar también como asignar los id, no sé si hay que hacerlo automáticamente en plan con una función o
        lo mete el usuario

         */
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
    public  int getNumProducts() {
        return numProducts;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
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
}
