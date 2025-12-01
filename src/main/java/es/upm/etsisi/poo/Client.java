package es.upm.etsisi.poo;


/**
 * Represents a client user in the system, inheriting from {@code User}.
 * A client is uniquely identified by their DNI/NIE and is registered by a cashier.
 */
public class Client extends User {
    /**
     * The client's official identification number (DNI or NIE).
     */
    private String DNI;
    /**
     * The ID of the cashier who created and registered this client account.
     */
    private String registeringCashierId;


    /**
     * Constructs a new Client object.
     * The DNI/NIE is normalized (converted to uppercase) to be used as the base {@code id}.
     *
     * @param name The name of the client.
     * @param DNI The client's identification number (DNI/NIE). This becomes the unique ID.
     * @param email The client's email address.
     * @param registeringCashierId The ID of the cashier who registered the client.
     */
    public Client(String name, String DNI, String email, String registeringCashierId) {
        super(normalizeDni(DNI), name, email);
        this.DNI = DNI;
        if (registeringCashierId == null) {
            this.registeringCashierId = "";
        } else {
            this.registeringCashierId = registeringCashierId;
        }
    }


    /**
     * Normalizes the DNI/NIE string by converting it to uppercase and validating its existence.
     * This normalized value is used as the unique ID for the {@code User} base class.
     *
     * @param dni The DNI/NIE string to normalize.
     * @return The uppercase DNI/NIE string.
     * @throws IllegalArgumentException if the DNI is null or empty.
     */
    private static String normalizeDni(String dni) {
        if (dni == null || dni.trim().isEmpty()) {
            throw new IllegalArgumentException("DNI required");
        }
        return dni.toUpperCase();
    }

    /**
     * Gets the ID of the cashier who registered this client.
     * @return The ID of the registering cashier.
     */
    public String getRegisteringCashierId() {
        return registeringCashierId;
    }

    /**
     * Returns the type of user, which is "Client".
     * @return A string literal "Client".
     */
    @Override
    public String getUserType() {
        return "Client";
    }

    /**
     * Returns a string representation of the Client object.
     * @return A formatted string containing the client's identifier (DNI/NIE), name, email, and registering cashier ID.
     */
    @Override
    public String toString() {
        return "Client{identifier='" + getId() + "', name='" + getName() + "', email='" + getEmail() + "', cash=" + registeringCashierId + "}";
    }
}
