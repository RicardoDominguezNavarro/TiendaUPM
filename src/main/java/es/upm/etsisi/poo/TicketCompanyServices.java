package es.upm.etsisi.poo;

public class TicketCompanyServices implements TicketPrinter{

    @Override
    public String print(Ticket ticket) {
        StringBuilder sb = new StringBuilder();
        sb.append("Services Included: \n");
        for (int i = 0; i < ticket.getProducts().size(); i++) {
            sb.append(ticket.getProducts().get(i).toString()).append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean close(Ticket ticket) {
        return true;
    }

    @Override
    public boolean acceptsProduct(Product product) {
        //los acepta solo si son servicios
        return product instanceof Service;
    }
}
