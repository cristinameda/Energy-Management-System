package ro.sd.titu.dataapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.sd.titu.dataapplication.entity.Device;

import java.math.BigInteger;

public interface DeviceRepository extends JpaRepository<Device, BigInteger> {
}
