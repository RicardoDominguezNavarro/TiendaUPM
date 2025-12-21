package es.upm.etsisi.poo;
import java.time.LocalDate;
import java.util.Locale;


public class Service extends Product {
    private static int nextServiceId = 1; // Contador para generar IDs 1S, 2S...
    private LocalDate expirationDate;

    public Service(LocalDate expirationDate) {
        // Genera id
        // Pasa nombre generado, precio 0.0 y categoría null al padre
        super(generateNextId(), "Service-" + nextServiceId, 0.0, null);

        if (expirationDate == null) {
            throw new IllegalArgumentException("Expiration date required");
        }
        this.expirationDate = expirationDate;
    }

    // Generar el id y aumentar el contador
    private static String generateNextId() {
        String id = nextServiceId + "S";
        nextServiceId++;
        return id;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public String toString() {
        // Formato específico para servicios (sin precio ni nombre)
        return String.format(Locale.US,
                "{class:Service, id:%s, expiration:%s}",
                getId_product(), expirationDate.toString());
    }
}
