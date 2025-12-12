// Carter Rudd
// Advanced Java
// OCCC fall 2025
// PersonGUI Final Project

import java.io.Serializable;
import java.util.Objects;

public class OCCCPerson3 extends RegisteredPerson3 implements Serializable {
    private static final long serialVersionUID = 1L;
    private String studentID;

    // Constructor using RegisteredPerson and studentID
    public OCCCPerson3(RegisteredPerson3 p, String studentID) {
        super(p);
        this.studentID = studentID;
    }

    // Copy constructor
    public OCCCPerson3(OCCCPerson3 p) {
        super(p);
        this.studentID = p.studentID;
    }

    // Accessor
    public String getStudentID() {
        return studentID;
    }

    // toString method adds studentID
    @Override
    public String toString() {
        return super.toString() + " {" + studentID + "}";
    }

    // equals method
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OCCCPerson3)) return false;
        OCCCPerson3 p = (OCCCPerson3) o;
        return super.equals(p) &&
               Objects.equals(studentID, p.studentID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), studentID);
    }

    public boolean equals(OCCCPerson3 p) {
        return this.equals((Object) p);
    }

    public boolean equals(RegisteredPerson3 p) {
        return super.equals(p);
    }

    public boolean equals(Person3 p) {
        return super.equals(p);
    }
}