package es.upm.etsisi.poo;

public class Company extends User{
    private String registeringCashierId;

    public Company(String name, String NIF, String email, String registeringCashierId) {
        super(normalizeNIF(NIF), name, email);
        if (!checkNIF(this.id)) {
            throw new IllegalArgumentException("Invalid NIF");
        }
        if (registeringCashierId == null) {
            this.registeringCashierId = "";
        } else {
            this.registeringCashierId = registeringCashierId;
        }
    }

    public static boolean checkNIF(String nif) {
        boolean validNIF = true;
        if (nif == null || nif.length() != 9) {
            System.out.println("Invalid NIF");
            validNIF = false;
        }
        String entityLettersList = "ABCDEFGHJNPQRSUVW";
        String nifType1 = "ABEH";
        String nifType2 = "KPQS";
        String[] nifElements = nif.split("");
        String entityLetter = nifElements[0];
        String numericPart = "";
        String controlElement = nifElements[nifElements.length-1];


        for (int i = 1; i < nifElements.length - 1; i++) {
            numericPart = String.join("", numericPart, nifElements[i]);
        }

        if (numericPart.length() != 7 || !entityLettersList.contains(entityLetter)) {
            System.out.println("Invalid NIF");
            validNIF = false;
        }

        String posibleControlElements = "JABCDEFGHI";
        String [] posibleControlElementsList = posibleControlElements.split("");
        try {
            String algoResult = nifAlgorithm(numericPart.split(""));

            if (nifType1.contains(entityLetter)) {
                if (!controlElement.equals(algoResult)) {
                    validNIF = false;
                }
            } else if (nifType2.contains(entityLetter)) {
                if (!controlElement.equals(posibleControlElementsList[Integer.parseInt(algoResult)])) {
                    validNIF = false;
                }
            } else {
                if ((!controlElement.equals(algoResult)) && (!controlElement.equals(posibleControlElementsList[Integer.parseInt(algoResult)]))) {
                    validNIF = false;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return validNIF;
    }

    public static String nifAlgorithm(String[] string){
        int evenSum = 0;
        int oddSum = 0;
        int totalSumInt;
        int result;
        for (int i = 0; i < string.length; i++) {
            if (i%2 == 1){
                evenSum += Integer.parseInt(string[i]);
            }else{
                int aux = Integer.parseInt(string[i]) * 2;
                if (aux >= 10){
                    oddSum += ((aux/10) + (aux % 10));
                }else{
                    oddSum += aux;
                }
            }
        }
        totalSumInt = oddSum + evenSum;
        result = 10 - (totalSumInt % 10);
        if (result == 10) {
            result = 0;
        }
        return String.valueOf(result);
    }

    private static String normalizeNIF(String nif) {
        if (nif == null || nif.trim().isEmpty()) {
            throw new IllegalArgumentException("NIF required");
        }
        return nif.trim().toUpperCase();
    }

    public String getRegisteringCashierId() {
        return registeringCashierId;
    }

    @Override
    public String getUserType() {
        return "Company";
    }

    @Override
    public String toString() {
        return "{class:Company, id:" + getId() + ", name: '" + getName() + "', email:" + getEmail() + "}";
    }
}

