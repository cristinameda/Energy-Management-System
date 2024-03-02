package ro.sd.titu.monitoringapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.sd.titu.monitoringapplication.entity.Measurement;

import java.math.BigInteger;
import java.util.List;

public interface MonitorRepository extends JpaRepository<Measurement, BigInteger> {

    List<Measurement> findAllByDeviceId(BigInteger id);

}
