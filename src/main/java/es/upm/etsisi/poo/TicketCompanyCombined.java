package es.upm.etsisi.poo;

public class TicketCompanyCombined implements TicketPrinter{

    @Override
    public String print(Ticket ticket) {
        double services ;
        return "";
    }

    @Override
    public boolean close(Ticket ticket) {
        return false;
    }

    @Override
    public boolean acceptsProduct(Product product) {
        return true;
    }
}
