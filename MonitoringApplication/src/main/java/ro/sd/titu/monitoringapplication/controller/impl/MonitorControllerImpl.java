package ro.sd.titu.monitoringapplication.controller.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.sd.titu.monitoringapplication.controller.MonitorController;
import ro.sd.titu.monitoringapplication.dto.MeasurementDTO;
import ro.sd.titu.monitoringapplication.security.config.JwtService;
import ro.sd.titu.monitoringapplication.service.MonitorService;

import java.util.List;

@Controller
@RequestMapping(value = "/monitor")
@CrossOrigin("*")
public class MonitorControllerImpl implements MonitorController {

    private final MonitorService monitorService;
    private final JwtService jwtService;

    public MonitorControllerImpl(MonitorService monitorService, JwtService jwtService) {
        this.monitorService = monitorService;
        this.jwtService = jwtService;
    }

    @Override
    public ResponseEntity<List<MeasurementDTO>> getMonitoredMeasurements(String token) {
        return isAuthorized(token) ?
                new ResponseEntity<>(monitorService.findAll(), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    private boolean isAuthorized(String token) {
        return jwtService.isAuthorizeBasedOnRole(token);
    }
}
