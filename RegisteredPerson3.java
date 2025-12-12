// Carter Rudd
// Advanced Java
// OCCC fall 2025
// PersonGUI Final Project

import java.io.Serializable;
import java.util.Objects;

public class RegisteredPerson3 extends Person3 implements Serializable {
    private static final long serialVersionUID = 1L;
    private String govID;

    // Constructor with all fields
    public RegisteredPerson3(String firstName, String lastName, String govID) {
        super(firstName, lastName);
        this.govID = govID;
    }

    // Constructor using Person and govID
    public RegisteredPerson3(Person3 p, String govID) {
        super(p);
        this.govID = govID;
    }

    // Copy constructor
    public RegisteredPerson3(RegisteredPerson3 p) {
        super(p);
        this.govID = p.govID;
    }

    // Accessor
    public String getGovernmentID() {
        return govID;
    }

    // toString method adds govID
    @Override
    public String toString() {
        return super.toString() + " [" + govID + "]";
    }

    // equals method 
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RegisteredPerson3)) return false;
        RegisteredPerson3 p = (RegisteredPerson3) o;
        return super.equals(p) &&
               Objects.equals(govID, p.govID);
    }

    // hashCode method 
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), govID);
    }

    public boolean equals(RegisteredPerson3 p) {
        return this.equals((Object) p);
    }

    public boolean equals(Person3 p) {
        return super.equals(p);
    }
}