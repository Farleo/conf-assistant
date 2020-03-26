package lms.itcluster.confassistant.dto;

import lms.itcluster.confassistant.entity.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserDTO {

    private Long userId;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String info;
    private Set<String> roles = new HashSet<>();

    public UserDTO() {
    }

public UserDTO(Long userId, String firstName, String lastName, String password, String email, String info, Set<String> roles) {
    this.userId = userId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.password = password;
    this.email = email;
    this.info = info;
    this.roles = roles;
}

public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
