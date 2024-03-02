package ro.sd.titu.devicemanagementapplication.entity;

import jakarta.persistence.*;

import java.math.BigInteger;

@Entity
@Table(name = "devices")
public class Device {

    @Id
    @GeneratedValue
    private BigInteger id;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "address", nullable = false)
    private String address;
    @Column(name = "max_consumption", nullable = false)
    private Float maxConsumption;
    @Column(name = "user_id", nullable = false)
    private BigInteger userId;

    public Device() {
    }

    public Device(String description, String address, Float maxConsumption, BigInteger userId) {
        this.description = description;
        this.address = address;
        this.maxConsumption = maxConsumption;
        this.userId = userId;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Float getMaxConsumption() {
        return maxConsumption;
    }

    public void setMaxConsumption(Float maxConsumption) {
        this.maxConsumption = maxConsumption;
    }

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }
}
