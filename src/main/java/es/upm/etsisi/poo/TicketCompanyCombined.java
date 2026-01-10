package es.upm.etsisi.poo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;

public class TicketCompanyCombined implements TicketPrinter{

    @Override
    public String print(Ticket<?> ticket) {
        StringBuilder sb = new StringBuilder();
        ArrayList<Product> products = ticket.getProducts();
        ArrayList<Integer> quantities = ticket.getQuantities();
        // Contar servicios para calcular el descuento
        long numServices = products.stream().filter(p -> p instanceof Service).count();
        double discountPercentage = numServices * 0.15; // 15% por servicio

        double totalPrice = 0.0;
        double totalDiscount = 0.0;

        sb.append("Ticket Company Combined: ").append(ticket.getIdTicket());
        if (ticket.getTicketStatus() == Ticket.TicketStatus.CLOSE) {
            sb.append("-").append(ticket.getClosingDate()).append("\n");
        } else {
            sb.append("\n");
        }
        // Imprimir productos y servicios
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            int amount = quantities.get(i);

            if (product instanceof Service) {
                // verificar fecha y NO imprimir precio
                Service service = (Service) product;
                if (service.getExpirationDate().isBefore(LocalDate.now())) {
                    return "Error: Service " + service.getId_product() + " has expired.";
                }
                sb.append(service.toString()).append("\n");
            } else {
                // para los productos aplicar descuento acumulado
                double itemPrice = product.getPrice() * amount;
                double itemDiscount = itemPrice * discountPercentage;

                // Formato de salida del producto
                sb.append(product.toString());
                if (itemDiscount > 0) {
                    sb.append(" **discount -").append(String.format(Locale.US, "%.2f", itemDiscount));
                }
                sb.append("\n");

                totalPrice += itemPrice;
                totalDiscount += itemDiscount;
            }
        }

        double finalPrice = totalPrice - totalDiscount;
        sb.append("  Total price: ").append(String.format(Locale.US, "%.2f", totalPrice)).append("\n");
        sb.append("  Total discount: ").append(String.format(Locale.US, "%.3f", totalDiscount)).append("\n");
        sb.append("  Final price: ").append(String.format(Locale.US, "%.3f", finalPrice));

        return sb.toString();
    }

    @Override
    public boolean close(Ticket<?> ticket) {
        // Debe haber al menos un Producto y un Servicio
        boolean hasProduct = false;
        boolean hasService = false;

        for (Product p : ticket.getProducts()) {
            if (p instanceof Service) {
                hasService = true;
            } else {
                hasProduct = true;
            }
        }
        return hasProduct && hasService;
    }

    @Override
    public boolean acceptsProduct(Product product) {
        return true;
    }
}
