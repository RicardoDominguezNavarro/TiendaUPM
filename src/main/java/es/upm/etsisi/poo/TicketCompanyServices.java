package es.upm.etsisi.poo;

import java.time.LocalDate;

public class TicketCompanyServices implements TicketPrinter{

    @Override
    public String print(Ticket<?> ticket) {
        StringBuilder sb = new StringBuilder();
        sb.append("Services Included: \n");
        for (int i = 0; i < ticket.getProducts().size(); i++) {
            Product product = ticket.getProducts().get(i);
            if (product instanceof Service) {
                Service service = (Service) product;
                // Si la fecha de caducidad es anterior a hoy, impedimos el cierre/impresión
                if (service.getExpirationDate().isBefore(LocalDate.now())) {
                    return "Error: Ticket cannot close. Service " + service.getId_product() + " has expired.";
                }
            }
            sb.append(product.toString()).append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean close(Ticket<?> ticket) {
        // En tickets de solo empresa (servicios), la de validez es en print().
        return true;
    }

    @Override
    public boolean acceptsProduct(Product product) {
        // Los tickets de servicios SOLO aceptan servicios
        return product instanceof Service;
    }
}
