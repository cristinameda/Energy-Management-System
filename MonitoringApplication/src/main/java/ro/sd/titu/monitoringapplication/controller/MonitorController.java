package ro.sd.titu.monitoringapplication.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import ro.sd.titu.monitoringapplication.dto.MeasurementDTO;

import java.util.List;

public interface MonitorController {

    @GetMapping()
    ResponseEntity<List<MeasurementDTO>> getMonitoredMeasurements(@RequestHeader("Authorization") String token);
}
