package es.upm.etsisi.poo;
import java.util.Objects;


/**
 * Abstract base class representing a generic user in the system.
 * Users can be either clients or cashiers.
 * All users have an ID, a name, and an email.
 */
public abstract class User {
    /**
     * Unique identifier for the user. Cannot be null after construction.
     */
    protected final String id;
    /**
     * The full name of the user.
     */
    protected String name;
    /**
     * The email address of the user. Can be an empty string if not provided.
     */
    protected String email;


    /**
     * Constructs a new User object.
     *
     * @param id The unique identifier for the user.
     * @param name The name of the user.
     * @param email The email of the user.
     * @throws IllegalArgumentException if id or name is null.
     */
    protected User(String id, String name, String email) {
        if (id == null){
            throw new IllegalArgumentException("id must not be null");
        }
        if (name == null){
            throw new IllegalArgumentException("name must not be null");
        }
        this.id = id;
        this.name = name.trim();
        if (email == null) {
            this.email = "";
        } else {
            this.email = email;
        }
    }

    /**
     * Gets the unique identifier of the user.
     * @return The user's ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the name of the user.
     * @return The user's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets a new name for the user.
     * @param name The new name to set.
     * @throws IllegalArgumentException if the provided name is null.
     */
    public void setName(String name){
        if(name == null) {
            throw new IllegalArgumentException("name must not be null");
        }
        this.name = name;
    }

    /**
     * Gets the email address of the user.
     * @return The user's email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets a new email address for the user.
     * @param email The new email to set. If null, the email is set to an empty string.
     */
    public void setEmail(String email) {
        if (email == null) {
            this.email = "";
        } else {
            this.email = email;
        }
    }


    /**
     * Gets the specific type of the user (e.g., "Client" or "Cashier").
     * This method must be implemented by concrete subclasses.
     * @return A string representing the user type.
     */
    public abstract String getUserType();

    /**
     * Returns a string representation of the user object.
     * This method must be implemented by concrete subclasses.
     * @return A formatted string representation of the user.
     */
    @Override
    public abstract String toString();

}
