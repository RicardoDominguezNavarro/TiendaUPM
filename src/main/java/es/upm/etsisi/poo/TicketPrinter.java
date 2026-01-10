package es.upm.etsisi.poo;
import java.io.Serializable;

public interface TicketPrinter extends Serializable {
    String print(Ticket<?> ticket);

    boolean close(Ticket<?> ticket);


    boolean acceptsProduct(Product product);
}
