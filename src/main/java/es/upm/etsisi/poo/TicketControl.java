package es.upm.etsisi.poo;

import java.util.ArrayList;

public class TicketControl {
    private final ArrayList<Ticket> tickets;
    private final Catalog catalog;

    public TicketControl(Catalog catalog) {
        this.catalog = catalog;
        this.tickets = new ArrayList<>();

        //no sé si deberia añadri algo del id del cajero
        //organizar ticket y ticket control
    }


}
