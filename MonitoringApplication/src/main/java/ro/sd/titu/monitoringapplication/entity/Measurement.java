package ro.sd.titu.monitoringapplication.entity;

import jakarta.persistence.*;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Entity
@Table(name = "measurement")
public class Measurement {
    @Id
    @GeneratedValue
    private BigInteger id;
    @Column(name = "timestamp", nullable = false)
    //@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime timestamp;
    @Column(name = "device_id", nullable = false)
    private BigInteger deviceId;
    @Column(name = "value", nullable = false)
    private Float value;

    public Measurement() {
    }

    public Measurement(LocalDateTime timestamp, BigInteger deviceId, Float value) {
        this.timestamp = timestamp;
        this.deviceId = deviceId;
        this.value = value;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
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
