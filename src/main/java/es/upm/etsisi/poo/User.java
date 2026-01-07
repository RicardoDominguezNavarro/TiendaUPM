package es.upm.etsisi.poo;
import java.util.Objects;
import java.io.Serializable;

public abstract class User implements Serializable {
    private static final long serialVersionUID = 1L;
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

    public abstract String getUserType();

    @Override
    public abstract String toString();

}
