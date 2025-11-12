package es.upm.etsisi.poo;

import java.util.ArrayList;

public class Cashier extends User{
    private final ArrayList<String> createdTicketIds = new ArrayList<>();
    public Cashier(String id, String name, String email) {
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

    @Override
    public String getUserType() {
        return "Cash";
    }
}
