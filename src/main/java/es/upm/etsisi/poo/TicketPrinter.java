package es.upm.etsisi.poo;

public interface TicketPrinter {

    String print(Ticket ticket);

    boolean close(Ticket ticket);

    boolean acceptsProduct(Product product);
}
