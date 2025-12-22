package es.upm.etsisi.poo;


public class Client extends User {

    // Cadena estática para comprobar la letra del DNI (Algoritmo Módulo 23)
    private static final String DNI_LETTERS = "TRWAGMYFPDXBNJZSQVHLCKE";
    private String registeringCashierId;



    public Client(String name, String DNI, String email, String registeringCashierId) {
        super(normalizeDni(DNI), name, email);
        if (!checkDni(id)) {
            throw new IllegalArgumentException("Invalid DNI: Format incorrect or wrong letter.");
        }
        if (registeringCashierId == null) {
            this.registeringCashierId = "";
        } else {
            this.registeringCashierId = registeringCashierId;
        }
    }

    public static boolean checkDni(String dni) {
        if (dni == null || dni.trim().isEmpty()) {
            return false;
        }
        dni = dni.toUpperCase().trim();
        if (!dni.matches("^[0-9]{8}[A-Z]$")) {
            return false;
        }
        try {
            String numberPart = dni.substring(0, 8);
            char letterPart = dni.charAt(8);
            int dniNumber = Integer.parseInt(numberPart);
            int index = dniNumber % 23;
            return DNI_LETTERS.charAt(index) == letterPart;
        } catch (NumberFormatException e) {
            return false;
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
