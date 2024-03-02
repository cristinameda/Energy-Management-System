package ro.sd.titu.dataapplication.entity;

import java.math.BigInteger;
import java.time.LocalDateTime;

public class Measurement {
    private LocalDateTime timestamp;
    private BigInteger deviceId;
    private Float value;

    public Measurement() {
    }

    public Measurement(LocalDateTime timestamp, BigInteger deviceId, Float value) {
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
