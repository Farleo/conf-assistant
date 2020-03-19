package lms.itcluster.confassistant.dto;

import lms.itcluster.confassistant.entity.User;

public class SpeakerDTO {
    private String firstName;
    private String lastName;
    private String photo;
    private String info;
    private String email;

    public SpeakerDTO(User user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.photo = user.getPhoto();
        this.info = user.getInfo();
        this.email = user.getEmail();
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
