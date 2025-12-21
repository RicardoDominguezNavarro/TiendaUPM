package es.upm.etsisi.poo;
import java.util.Objects;


/**
 * Abstract base class representing a generic user in the system.
 * Users can be either clients or cashiers.
 * All users have an ID, a name, and an email.
 */
public abstract class User {

    protected final String id;

    protected String name;

    protected String email;


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


    public String getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name){
        if(name == null) {
            throw new IllegalArgumentException("name must not be null");
        }
        this.name = name;
    }


    public String getEmail() {
        return email;
    }


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
