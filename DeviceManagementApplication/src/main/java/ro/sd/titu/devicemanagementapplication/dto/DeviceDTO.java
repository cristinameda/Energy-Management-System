package ro.sd.titu.devicemanagementapplication.dto;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import ro.sd.titu.devicemanagementapplication.dto.validation.SaveValidation;
import ro.sd.titu.devicemanagementapplication.dto.validation.UpdateValidation;

import java.math.BigInteger;

public class DeviceDTO {
    @Id
    @NotNull(groups = UpdateValidation.class)
    private BigInteger id;
    @NotNull(groups = {SaveValidation.class, UpdateValidation.class})
    private String description;
    @NotNull(groups = {SaveValidation.class, UpdateValidation.class})
    private String address;
    @NotNull(groups = {SaveValidation.class, UpdateValidation.class})
    private Float maxConsumption;
    @NotNull(groups = SaveValidation.class)
    private BigInteger userId;

    public DeviceDTO() {
    }

    public DeviceDTO(BigInteger id, String description, String address, Float maxConsumption, BigInteger userId) {
        this.id = id;
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
