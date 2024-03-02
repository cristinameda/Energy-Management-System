package ro.sd.titu.devicemanagementapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.sd.titu.devicemanagementapplication.entity.Device;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, BigInteger> {
    Optional<Device> findByUserId(BigInteger id);

    List<Device> findAllByUserId(BigInteger userId);

    void deleteAllByUserId(BigInteger userId);
}
