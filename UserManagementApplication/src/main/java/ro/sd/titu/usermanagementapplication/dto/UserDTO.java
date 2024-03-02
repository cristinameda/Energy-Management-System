package ro.sd.titu.usermanagementapplication.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;
import ro.sd.titu.usermanagementapplication.dto.validator.annotation.AgeLimit;
import ro.sd.titu.usermanagementapplication.dto.validator.group.FullValidation;
import ro.sd.titu.usermanagementapplication.dto.validator.group.UpdateValidation;
import ro.sd.titu.usermanagementapplication.entity.Role;

import java.math.BigInteger;

public class UserDTO {
    @Id
    @NotNull(groups = UpdateValidation.class)
    private BigInteger id;
    @NotNull(groups = FullValidation.class)
    @Pattern(regexp = "^[a-z]+[0-9a-z-]*")
    private String username;
    private String password;
    @NotNull(groups = FullValidation.class)
    private String name;
    @NotNull(groups = FullValidation.class)
    private String address;
    @NotNull(groups = FullValidation.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @AgeLimit(limit = 18)
    private String dob;
    private Role role;

    public UserDTO() {
    }

    public UserDTO(BigInteger id, String username, String password, String name, String address, String dob, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.address = address;
        this.dob = dob;
        this.role = role;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", age=" + dob +
                '}';
    }
}
