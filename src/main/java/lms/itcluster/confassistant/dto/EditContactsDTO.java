package lms.itcluster.confassistant.dto;

import lms.itcluster.confassistant.annotation.UniqueEmail;

public class EditContactsDTO extends AbstractDTO{
    private Long userId;

    @UniqueEmail()
    private String email;

    @Override
    public Long getId() {
        return userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
