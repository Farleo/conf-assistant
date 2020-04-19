package lms.itcluster.confassistant.dto;

import lms.itcluster.confassistant.annotation.*;

@ConfirmPassword(password = "password", userId = "userId")
@FieldMatch(first = "newPassword", second = "verifyPassword", message = "The password fields must match")
@CurrentPassword(password = "newPassword", userId = "userId")
public class EditPasswordDTO extends AbstractDTO {
    private Long userId;
    private String password;
    @EnglishLanguage
    @PasswordStrength
    private String newPassword;
    private String verifyPassword;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getVerifyPassword() {
        return verifyPassword;
    }

    public void setVerifyPassword(String verifyPassword) {
        this.verifyPassword = verifyPassword;
    }
}
