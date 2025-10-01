package es.upm.etsisi.poo;

public class Product {

    private int id;
    private String name;
    private double price;
    public Category category;



    public Product(int id, String name, double price, Category category) {
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
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        if(category == null) {
            throw new IllegalArgumentException("Category may not be null!");
        }
        this.category = category;
    }

    public void setName(String name) {
        if(name == null || name.trim().isEmpty() || name.trim().length() > 100) {
            throw new IllegalArgumentException("Invalid name!");
        }
        this.name = name;
    }

    public void setPrice(double price) {
        if(price <= 0.0) {
            throw new IllegalArgumentException("Price may not be negative!");
        }
        this.price = price;
    }

    public void setId(int id) {
        if(id <= 0) {
            throw new IllegalArgumentException("Id may not be negative!");
        }
        this.id = id;
    }
}
