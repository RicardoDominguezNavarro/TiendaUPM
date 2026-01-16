package es.upm.etsisi.poo;

import java.time.LocalDate;
import java.util.Locale;


public class Service extends Product {

    public enum Category {TRANSPORT, SHOWS, INSURANCE}

    private static int nextServiceId = 1; // Contador para generar IDs 1S, 2S...
    private LocalDate expirationDate;
    private Category category;

    public Service(Category category, LocalDate expirationDate) {
        // Genera id
        // Pasa nombre generado, precio 0.0 y categoría null al padre
        super(generateNextId(), "Service-" + nextServiceId, 0.0, null);
        if (expirationDate == null || category == null) {
            throw new IllegalArgumentException("Expiration date required");
        }
        this.expirationDate = expirationDate;
        this.category = category;
    }

    // Generar id y aumentar el contador
    private static String generateNextId() {
        String id = nextServiceId + "S";
        nextServiceId++;
        return id;
    }

    public static void setNextServiceId(int id) {
        nextServiceId = id;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public String toString() {
        // Sobrescribimos para no mostrar precio, ni nombre, ni categoría. Formato: {class:ProductService, id:1S, expiration:yyyy-MM-dd}
        return String.format(Locale.US,
                "{class:ProductService, id:%s,category:%s, expiration:%s}",
                getId_product(), category, expirationDate.toString());
    }
}
