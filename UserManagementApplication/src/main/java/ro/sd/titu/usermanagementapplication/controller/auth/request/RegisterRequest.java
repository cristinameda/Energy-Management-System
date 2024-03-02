package ro.sd.titu.usermanagementapplication.controller.auth.request;

import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import ro.sd.titu.usermanagementapplication.dto.validator.annotation.AgeLimit;

public class RegisterRequest {
    @NotNull
    private String name;
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String address;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @AgeLimit(limit = 18)
    private String dob;

    public RegisterRequest(String name, String username, String password, String address, String dob) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.address = address;
        this.dob = dob;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
}
