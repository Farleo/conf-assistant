package lms.itcluster.confassistant.form;

import java.io.Serializable;

public class UserForm implements Serializable {
    private String email;
    private String password;
    private String referer;

    public UserForm(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserForm() {
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
