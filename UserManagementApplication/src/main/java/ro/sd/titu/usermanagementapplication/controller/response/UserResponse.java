package ro.sd.titu.usermanagementapplication.controller.response;

import java.math.BigInteger;

public class UserResponse {
    private BigInteger id;
    private String username;
    private String name;
    private String address;
    private String dob;
    private String role;

    public UserResponse() {
    }

    public UserResponse(BigInteger id, String username, String name, String address, String dob, String role) {
        this.id = id;
        this.username = username;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
