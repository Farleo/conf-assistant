package lms.itcluster.confassistant.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class EditProfileDTO extends AbstractDTO {

    private Long userId;

    @Size(min = 1, max = 45)
    private String firstName;

    @Size(min = 1, max = 45)
    private String lastName;

    @NotNull
    @NotBlank
    private String info;

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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public Long getId() {
        return userId;
    }
}
