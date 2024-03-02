package ro.sd.titu.monitoringapplication.service;

import ro.sd.titu.monitoringapplication.dto.MeasurementDTO;
import ro.sd.titu.monitoringapplication.entity.Measurement;

import java.math.BigInteger;
import java.util.List;

public interface MonitorService {
    BigInteger save(Measurement measurement);

    List<MeasurementDTO> findAll();
}
