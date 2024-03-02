package ro.sd.titu.devicemanagementapplication.controller.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.sd.titu.devicemanagementapplication.controller.DeviceController;
import ro.sd.titu.devicemanagementapplication.dto.DeviceDTO;
import ro.sd.titu.devicemanagementapplication.security.config.JwtService;
import ro.sd.titu.devicemanagementapplication.service.DeviceService;

import java.math.BigInteger;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/devices")
public class DeviceControllerImpl implements DeviceController {

    private final DeviceService deviceService;
    private final JwtService jwtService;

    public DeviceControllerImpl(DeviceService deviceService, JwtService jwtService) {
        this.deviceService = deviceService;
        this.jwtService = jwtService;
    }

    @Override
    public ResponseEntity<BigInteger> save(String token, DeviceDTO deviceDTO) {
        return isAuthorized(token) ?
                new ResponseEntity<>(deviceService.save(deviceDTO), HttpStatus.CREATED) :
                new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<BigInteger> update(String token, DeviceDTO deviceDTO) {
        return isAuthorized(token) ?
                new ResponseEntity<>(deviceService.update(deviceDTO), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.FORBIDDEN);

    }

    @Override
    public ResponseEntity<Void> deleteById(String token, BigInteger id) {
        if (isAuthorized(token)) {
            deviceService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<List<DeviceDTO>> findAll(String token) {
        return isAuthorized(token) ?
                new ResponseEntity<>(deviceService.findAll(), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<DeviceDTO> findById(String token, BigInteger id) {
        return isAuthorized(token) ?
                new ResponseEntity<>(deviceService.findById(id), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<List<DeviceDTO>> findAllByUserId(BigInteger userId) {
        return new ResponseEntity<>(deviceService.findAllByUserId(userId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteAllByUserId(String token, BigInteger userId) {
        if (isAuthorized(token)) {
            deviceService.deleteAllByUserId(userId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);

    }

    private boolean isAuthorized(String token) {
        return jwtService.isAuthorizeBasedOnRole(token);
    }
}
