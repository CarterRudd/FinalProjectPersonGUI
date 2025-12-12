// Carter Rudd
// Advanced Java
// OCCC fall 2025
// PersonGUI Final Project

import java.io.Serializable;
import java.util.Objects;

public class Person3 implements Serializable {
    private static final long serialVersionUID = 1L;
    private String firstName;
    private String lastName;

    // Empty constructor
    public Person3() {
        this.firstName = "";
        this.lastName = "";
    }

    // Constructor with first and last name
    public Person3(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Copy constructor
    public Person3(Person3 p) {
        this.firstName = p.firstName;
        this.lastName = p.lastName;
    }

    // Accessors
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }

    // Mutators
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    // toString method
    @Override
    public String toString() {
        return lastName + ", " + firstName;
    }

    // equals method 
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person3)) return false;
        Person3 p = (Person3) o;
        return firstName.equalsIgnoreCase(p.firstName) &&
               lastName.equalsIgnoreCase(p.lastName);
    }

    // hashCode method
    @Override
    public int hashCode() {
        return Objects.hash(firstName.toLowerCase(), lastName.toLowerCase());
    }

    // Behavior methods 
    public void eat() {
        System.out.println(firstName + " " + lastName + " is eating...");
    }

    public void sleep() {
        System.out.println(firstName + " " + lastName + " is sleeping...");
    }

    public void play() {
        System.out.println(firstName + " " + lastName + " is playing...");
    }

    public void run() {
        System.out.println(firstName + " " + lastName + " is running...");
    }
}