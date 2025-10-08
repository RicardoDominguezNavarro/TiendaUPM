package es.upm.etsisi.poo;
import java.util.ArrayList;

/**
 *This class represents the receipt where the products added by the user are stored. It can contains up to 100 products
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
     * @param prodId  the product ID to add
     * @param quantity the number of units to add
     */
    public void addProduct(int prodId, int quantity) {
        Product product = catalog.getProductId(prodId);
        if (product == null) {
            System.out.println("There is no product");
        } else {
            boolean found = false;
            for (int i = 0; i < numItems; i++) {
                if (products.get(i).getId() == prodId) {
                    quantities.set(i, quantities.get(i) + quantity);
                    found = true;
                }
            }
            if (!found) {
                if (numItems < maxItems) {
                    products.add(numItems, product);
                    quantities.add(numItems, quantity);
                    numItems++;
                    System.out.println(print());
                    System.out.println("ticket add: ok");
                } else {
                    System.out.println("The ticket is full");
                }
            }
        }
    }


    /**
     * Remove a product from the ticket using the ID
     * If the product exists, it is removed along with its quantity.
     * @param prodId the ID of the product to remove
     */
    public void removeProduct(int prodId) {
        Product p = catalog.getProductId(prodId);
        if (p == null) {
            System.out.println("There is no product");
        } else {
            boolean found = false;
            for (int i = products.size() - 1; i > 0; i--) {
                if (products.get(i).getId() == prodId) {
                    products.remove(i);
                    System.out.println(print());
                    System.out.println("prod remove: ok");

                }
            }
        }
    }


    /**
     * Calculate a discount for a specific product depending on its category and quantity.
     * The discount is applied only if the quantity is two or more.
     * @param product the product for which the discount is calculated
     * @param amount the quantity of the product
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
     *  Returns the discount percentage for a specific product category.
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
     * Prints the current stage of the ticket on the console
     */
    public void currentTicket() {
        System.out.println(print());
    }


    /**
     * @return a formatted string representing the ticket, showing the products,
     * their discounts and the total price.
     */
    public String print() {
        StringBuilder sb = new StringBuilder();
        double totalPrice = 0.0;
        double totalDiscount = 0.0;

        sb.append("===========  TICKET  ===========\n");
        for (int i = 0; i < numItems; i++) {
            Product p = products.get(i);
            int amount = quantities.get(i);

            for (int j = 0; j < amount; j++) {
                double discount = calculateDiscount(p, amount);
                sb.append(products.toString());
                if (discount > 0) {
                    sb.append("/ discount= -").append(discount).append("\n");
                }
                totalPrice += p.getPrice();
                totalDiscount += discount;
            }
        }
        double finalPrice = totalPrice - totalDiscount;
        sb.append("Total price =").append(totalPrice).append("\n");
        sb.append("Total discount =").append(totalDiscount).append("\n");
        sb.append("Final price =").append(finalPrice).append("\n");
        return sb.toString();
    }

}
