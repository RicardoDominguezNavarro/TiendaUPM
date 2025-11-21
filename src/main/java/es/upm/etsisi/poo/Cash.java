package es.upm.etsisi.poo;

import java.util.ArrayList;

public class Cash extends User{
    private final ArrayList<String> createdTicketIds = new ArrayList<>();
    public Cash(String id, String name, String email) {
        super(validateAndNormalizeId(id), name, email);
    }

    private static String validateAndNormalizeId(String id) {
        if (id == null) {
            throw new IllegalArgumentException("cash id required");
        }
        String cleaned = id.toUpperCase();
        if (cleaned.length() != 9) {
            throw new IllegalArgumentException("cash id must follow pattern UW#######");
        }
        if (cleaned.charAt(0) != 'U' || cleaned.charAt(1) != 'W') {
            throw new IllegalArgumentException("cash id must start with UW");
        }
        for (int i = 2; i < 9; i++) {
            char c = cleaned.charAt(i);
            if (c < '0' || c > '9') {
                throw new IllegalArgumentException("cash id must follow pattern UW#######");
            }
        }
        return cleaned;
    }

    public static String generateId(){
        String id = "UW";
        int idIntegerPart = 0;
        int weigth = 1000000;
        for (int i = 0; i < 7; i++) {
            idIntegerPart += (int) (Math.random() * 10) * weigth;
            weigth /= 10;
        }
        id += String.valueOf(idIntegerPart);
        return id;
        //se comprueba si ya existe a la hora de llamar a la funcion
    }
    public ArrayList<String> getCreatedTicketIds() {
        return createdTicketIds;
    }

    public void addCreatedTicket(String ticketId) {
        this.createdTicketIds.add(ticketId);
    }

    @Override
    public String getUserType() {
        return "Cash";
    }

    @Override
    public String toString() {
        return "Cash{identifier='" + getId() + "', name='" + getName() + "', email='" + getEmail() + "'}";
    }
}
