package ro.sd.titu.dataapplication.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

public interface DeviceController {
    @PostMapping(value = "/transmit")
    ResponseEntity<Void> transmit(@RequestHeader("Authorization") String token);
}
