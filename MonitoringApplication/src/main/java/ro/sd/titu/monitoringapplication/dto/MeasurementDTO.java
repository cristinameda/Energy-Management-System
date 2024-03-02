package ro.sd.titu.monitoringapplication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigInteger;
import java.time.LocalDateTime;

public class MeasurementDTO {

    @JsonProperty("timestamp")
    private LocalDateTime timestamp;
    @JsonProperty("deviceId")
    private BigInteger deviceId;
    @JsonProperty("value")
    private Float value;

    public MeasurementDTO() {
    }

    public MeasurementDTO(LocalDateTime timestamp, BigInteger deviceId, Float value) {
        this.timestamp = timestamp;
        this.deviceId = deviceId;
        this.value = value;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public BigInteger getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(BigInteger deviceId) {
        this.deviceId = deviceId;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }
}
