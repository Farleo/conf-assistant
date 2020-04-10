package lms.itcluster.confassistant.dto;

import lms.itcluster.confassistant.annotation.UniqueEmail;

public class SignUpDTO extends AbstractDTO {

    @UniqueEmail
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Long getId() {
        return null;
    }
}
