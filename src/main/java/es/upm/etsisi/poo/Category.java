package es.upm.etsisi.poo;

/**
 * Defines the different categories of products that can exists in the system.
 */
public enum Category {

    MERCH(0.0),
    STATIONERY(0.05),
    CLOTHES(0.07),
    BOOK(0.1),
    ELECTRONICS(0.03);

    private final double discount;

    private Category(double discount) {
        this.discount = discount;
    }

    // Getter
    public double getDiscount() {
        return discount;
    }
}


