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
        if (!dni.matches("^([0-9]{8}|[XYZ][0-9]{7})[A-Z]$")) {
            return false;
        }
        try {
            String numberPartStr = dni.substring(0, 8);
            char firstChar = dni.charAt(0);
            if (firstChar == 'X'){
                numberPartStr = "0" + dni.substring(1, 8);
            }
            else if (firstChar == 'Y'){
                numberPartStr = "1" + dni.substring(1, 8);
            }
            else if(firstChar == 'Z'){
                numberPartStr = "2" + dni.substring(1, 8);
            }
            int dniNumber = Integer.parseInt(numberPartStr);
            int index = dniNumber % 23;
            char letterPart = dni.charAt(8);
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
