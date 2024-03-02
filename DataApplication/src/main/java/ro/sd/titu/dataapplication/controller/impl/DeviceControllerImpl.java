package ro.sd.titu.dataapplication.controller.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.sd.titu.dataapplication.controller.DeviceController;
import ro.sd.titu.dataapplication.security.config.JwtService;
import ro.sd.titu.dataapplication.service.DeviceService;

@RestController
@RequestMapping(value = "/data")
@CrossOrigin("*")
public class DeviceControllerImpl implements DeviceController {

    private final DeviceService deviceService;
    private final JwtService jwtService;

    public DeviceControllerImpl(DeviceService deviceService, JwtService jwtService) {
        this.deviceService = deviceService;
        this.jwtService = jwtService;
    }

    @Override
    public ResponseEntity<Void> transmit(String token) {
        if (isAuthorized(token)) {
            this.deviceService.transmit();
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    private boolean isAuthorized(String token) {
        return jwtService.isAuthorizeBasedOnRole(token);
    }
}
