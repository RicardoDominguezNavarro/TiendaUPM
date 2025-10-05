package es.upm.etsisi.poo;

public class Product {

    private int id;
    private String name;
    private double price;
    public Category category;
    private Catalog belongToCatalog;


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

    @Override

    public String toString() {
        return String.format("{class:Product, id:%d, name:'%s', category:%s, price:%.1f}",
                id, name, category, price);
    }


    public void setBelongToCatalog(Catalog catalog) {
        this.belongToCatalog = catalog;

    }

    private Catalog getBelongToCatalog(){
        return belongToCatalog;
    }
}
