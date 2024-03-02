package ro.sd.titu.monitoringapplication.dto.builder;

import ro.sd.titu.monitoringapplication.dto.MeasurementDTO;
import ro.sd.titu.monitoringapplication.entity.Measurement;

public class MeasurementBuilder {

    public static MeasurementDTO toDTO(Measurement measurement) {
        return new MeasurementDTO(
                measurement.getTimestamp(),
                measurement.getDeviceId(),
                measurement.getValue());
    }
}
