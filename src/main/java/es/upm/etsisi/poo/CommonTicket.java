package es.upm.etsisi.poo;

import java.util.ArrayList;
import java.util.Locale;

public class CommonTicket implements TicketPrinter{
    @Override
    public String print(Ticket<?> ticket) {
        StringBuilder sb = new StringBuilder();
        double totalPrice = 0.0;
        double totalDiscount = 0.0;

        ArrayList<Product> products = ticket.getProducts();
        ArrayList<Integer> quantities = ticket.getQuantities();

        sb.append("Ticket : ").append(ticket.getIdTicket());
        if (ticket.getTicketStatus() == Ticket.TicketStatus.CLOSE) {
            sb.append("-").append(ticket.getClosingDate()).append("\n");
        } else {
            sb.append("\n");
        }
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            int amount = quantities.get(i);
            double unitDiscount = ticket.calculateDiscount(product, amount);
            if (product instanceof Events){
                Events event = (Events) product;
                if (!event.validDate()) {
                    return "The ticket cannot be closed because the " + event.getName() + " event has passed its deadline.";
                }
                sb.append("  ").append(event.toString()).append("\n");
                totalPrice += product.getPrice();
                totalDiscount += unitDiscount;
            }else {
                for (int j = 0; j < amount; j++) {
                    if (product instanceof Events) {
                        Events event = (Events) product;
                        if (!event.validDate()) {
                            return "The ticket cannot be closed because the " + event.getName() + " event has passed its deadline.";
                        }
                        sb.append(event.toString()).append("\n");

                    } else if (product instanceof PersonalizedProduct) {
                        PersonalizedProduct personalizedProd = (PersonalizedProduct) product;
                        sb.append(personalizedProd.toString());
                        if (unitDiscount > 0) {
                            sb.append(" **discount -"   ).append(unitDiscount);
                        } else {
                            sb.append("\n");
                        }
                    } else {
                        sb.append(product.toString());
                        if (unitDiscount > 0) {
                            sb.append(" **discount -").append(String.format(Locale.US, "%.2f", unitDiscount)).append("\n");
                        } else {
                            sb.append("\n");
                        }
                    }
                    totalPrice += product.getPrice();
                    totalDiscount += unitDiscount;
                }
            }
        }
        double finalPrice = totalPrice - totalDiscount;
        sb.append("  Total price: ").append(String.format(Locale.US, "%.2f", totalPrice)).append("\n");
        sb.append("  Total discount: ").append(String.format(Locale.US, "%.3f", totalDiscount)).append("\n");
        sb.append("  Final Price: ").append(String.format(Locale.US, "%.3f", finalPrice));
        return sb.toString();
    }

    @Override
    public boolean close(Ticket<?> ticket) {
        return true;
    }

    @Override
    public boolean acceptsProduct(Product product) {
        //Solo productos
        return !(product instanceof Service);
    }
}
