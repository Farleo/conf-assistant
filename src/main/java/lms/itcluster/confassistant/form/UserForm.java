package lms.itcluster.confassistant.form;

import java.io.Serializable;
import java.util.Set;

public class UserForm implements Serializable {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String info;
    private Set<String> roles;

    public UserForm(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserForm() {
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        if (password == null) {
            return "";
        }
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
