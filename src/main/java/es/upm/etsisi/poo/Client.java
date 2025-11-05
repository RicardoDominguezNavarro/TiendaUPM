package es.upm.etsisi.poo;

public class Client extends User {

    public Client(String dni, String name, String email) {
        super(normalizeDni(dni), name, email);
    }

    private static String normalizeDni(String dni) {
        if (dni == null || dni.trim().isEmpty()) {
            throw new IllegalArgumentException("DNI required");
        }
        return dni.toUpperCase();
    }

    @Override
    public String getUserType() {
        return "Client";
    }

}
