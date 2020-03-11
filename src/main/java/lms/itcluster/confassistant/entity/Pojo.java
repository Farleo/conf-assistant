package lms.itcluster.confassistant.entity;

public class Pojo {
    private String firstName;
    private String lastName;

    public Pojo(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Pojo() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
