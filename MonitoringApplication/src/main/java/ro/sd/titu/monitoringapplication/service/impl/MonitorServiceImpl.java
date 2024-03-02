package ro.sd.titu.monitoringapplication.service.impl;

import org.springframework.stereotype.Service;
import ro.sd.titu.monitoringapplication.dto.MeasurementDTO;
import ro.sd.titu.monitoringapplication.dto.builder.MeasurementBuilder;
import ro.sd.titu.monitoringapplication.entity.Measurement;
import ro.sd.titu.monitoringapplication.repository.MonitorRepository;
import ro.sd.titu.monitoringapplication.service.MonitorService;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MonitorServiceImpl implements MonitorService {

    private final MonitorRepository monitorRepository;

    public MonitorServiceImpl(MonitorRepository monitorRepository) {
        this.monitorRepository = monitorRepository;
    }

    @Override
    public BigInteger save(Measurement measurement) {
        Optional<Measurement> foundMeasurement = getByDeviceAndDateAndHour(measurement.getTimestamp(), measurement.getDeviceId());
        Measurement savedMeasurement;
        Measurement consumption;
        if (foundMeasurement.isPresent()) {
            consumption = foundMeasurement.get();
            consumption.setTimestamp(consumption.getTimestamp());
            consumption.setValue(consumption.getValue() + measurement.getValue());
            savedMeasurement = monitorRepository.save(consumption);
        } else {
            savedMeasurement = monitorRepository.save(measurement);
        }
        return savedMeasurement.getId();
    }

    @Override
    public List<MeasurementDTO> findAll() {
        return monitorRepository.findAll()
                .stream()
                .map(MeasurementBuilder::toDTO)
                .collect(Collectors.toList());
    }

    private Optional<Measurement> getByDeviceAndDateAndHour(LocalDateTime dateTime, BigInteger deviceId) {
        return monitorRepository.findAllByDeviceId(deviceId).stream()
                .filter(measurement -> measurement.getTimestamp().toLocalDate().isEqual(dateTime.toLocalDate())
                        && measurement.getTimestamp().getHour() == dateTime.getHour())
                .findFirst();
    }

}
