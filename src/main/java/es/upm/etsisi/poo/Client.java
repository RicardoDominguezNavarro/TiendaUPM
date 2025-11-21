package es.upm.etsisi.poo;

public class Client extends User {
    private String DNI;
    private String registeringCashierId;

    public Client(String name, String DNI, String email, String registeringCashierId) {
        super(normalizeDni(DNI), name, email);
        this.DNI = DNI;
        if (registeringCashierId == null) {
            this.registeringCashierId = "";
        } else {
            this.registeringCashierId = registeringCashierId;
        }
    }

    private static String normalizeDni(String dni) {
        if (dni == null || dni.trim().isEmpty()) {
            throw new IllegalArgumentException("DNI required");
        }
        return dni.toUpperCase();
    }

    public String getRegisteringCashierId() {
        return registeringCashierId;
    }

    @Override
    public String getUserType() {
        return "Client";
    }

    @Override
    public String toString() {
        return "Client{identifier='" + getId() + "', name='" + getName() + "', email='" + getEmail() + "', cash=" + registeringCashierId + "}";
    }

}
