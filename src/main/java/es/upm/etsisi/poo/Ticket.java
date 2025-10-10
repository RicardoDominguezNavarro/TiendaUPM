package es.upm.etsisi.poo;

import java.util.ArrayList;

/**
 * This class represents the receipt where the products added by the user are stored. It can contains up to 100 products
 * and can call {@link Catalog} to check the availability of products.
 */
public class Ticket {
    /**
     * Maximum number of products that can be included in a ticket
     */
    private final int maxItems = 100;
    /**
     * Array: list of products added to the ticket
     */
    private ArrayList<Product> products;
    /**
     * Array: list of quantities to each product of the ticket
     */
    private ArrayList<Integer> quantities;
    /**
     * number of items in the ticket
     */
    private int numItems;
    /**
     * Reference to the catalog that contains all available products
     */
    private Catalog catalog;


    /**
     * Builder that create a new ticket
     * Initially, the ticket is empty
     *
     * @param catalog Contains the available products
     */
    public Ticket(Catalog catalog) {
        this.catalog = catalog;
        this.products = new ArrayList<>(maxItems);
        this.quantities = new ArrayList<>(maxItems);
        this.numItems = 0;
    }


    /**
     * Reset the ticket starting  new one
     * The previous information is not saved
     */
    public void newTicket() {
        this.products = new ArrayList<>(maxItems);
        this.quantities = new ArrayList<>(maxItems);
        this.numItems = 0;
        System.out.println("ticket new: ok");
    }


    /**
     * Adds a new product to the ticket based on its product ID and quantity.
     * If the product already exists in the ticket, its quantity is increased.
     *
     * @param prodId   the product ID to add
     * @param quantity the number of units to add
     */
    public void addProduct(int prodId, int quantity) {
        Product product = catalog.getProductId(prodId);
        if (product == null) {
            System.out.println("There is no product");
        } else if (quantity < 0) {
            System.out.println("the amount must be positive");
        } else if (quantity > maxItems) {
            System.out.println("The ticket can only have 100 products");
        } else {
            boolean found = false;
            for (int i = 0; i < numItems; i++) {
                if (products.get(i).getId() == prodId) {
                    quantities.set(i, quantities.get(i) + quantity);
                    numItems = numItems + quantities.get(i);
                    found = true;
                }
            }
            if (!found) {
                if (numItems < maxItems) {
                    products.add(numItems, product);
                    quantities.add(numItems, quantity);
                    numItems = numItems + quantity;
                } else {
                    System.out.println("The ticket is full");
                }
            }
            System.out.println(print());
            System.out.println("ticket add: ok");

        }
    }


    /**
     * Remove a product from the ticket using the ID
     * If the product exists, it is removed along with its quantity.
     *
     * @param prodId the ID of the product to remove
     */

    public void removeProduct(int prodId) {
        boolean found = false;
        for (int i = products.size() - 1; i >= 0; i--) {
            if (products.get(i).getId() == prodId) {
                products.remove(i);
                quantities.remove(i);
                numItems--;
                found = true;
            }
        }
        if (found) {
            System.out.println(print());
            System.out.println("prod remove: ok");
        } else {
            System.out.println("There is no product in the ticket");
        }
    }




    /**
     * Calculate a discount for a specific product depending on its category and quantity.
     * The discount is applied only if the quantity is two or more.
     *
     * @param product the product for which the discount is calculated
     * @param amount  the quantity of the product
     * @return the discount value
     */
    public double calculateDiscount(Product product, int amount) {
        double result = 0.0;
        if (amount >= 2) {
            double discount = getDiscount(product.getCategory());
            result = product.getPrice() * discount;
        }
        return result;
    }


    /**
     * Returns the discount percentage for a specific product category.
     *
     * @param category The category of the product
     * @return the discount
     */
    public double getDiscount(Category category) {
        double percent = 0.0;
        switch (category) {
            case CLOTHES:
                percent = 0.07;
                break;
            case BOOK:
                percent = 0.1;
                break;
            case MERCH:
                percent = 0.0;
                break;
            case STATIONERY:
                percent = 0.05;
                break;
            case ELECTRONICS:
                percent = 0.03;
                break;
            default:
                System.out.println("Please select a valid option");
        }
        return percent;
    }

    /**
     * @return a formatted string representing the ticket, showing the products,
     * their discounts and the total price.
     */
    public String print() {
        StringBuilder sb = new StringBuilder();
        double totalPrice = 0.0;
        double totalDiscount = 0.0;

        for (int i = numItems - 1; i >= 0; i--) {
            Product product = products.get(i);
            int amount = quantities.get(i);

            for (int j = 0; j < amount; j++) {
                double discount = calculateDiscount(product, amount);
                sb.append(product.toString());
                if (discount > 0) {
                    sb.append(" **discount -").append(discount).append("\n");
                } else {
                    sb.append("\n");
                }
                totalPrice += product.getPrice();
                totalDiscount += discount;
            }
        }
        double finalPrice = totalPrice - totalDiscount;
        sb.append("Total price: ").append(totalPrice).append("\n");
        sb.append("Total discount: ").append(totalDiscount).append("\n");
        sb.append("Final price: ").append(finalPrice);

        return sb.toString();
    }

}
