package ro.sd.titu.usermanagementapplication.controller.auth.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class AuthRequest {
    @NotNull
    @Pattern(regexp = "^[a-z]+[0-9a-z-]*")
    private String username;
    @NotNull
    private String password;

    public AuthRequest() {
    }

    public AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
