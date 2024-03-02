package ro.sd.titu.devicemanagementapplication.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ro.sd.titu.devicemanagementapplication.dto.DeviceDTO;
import ro.sd.titu.devicemanagementapplication.dto.validation.SaveValidation;
import ro.sd.titu.devicemanagementapplication.dto.validation.UpdateValidation;

import java.math.BigInteger;
import java.util.List;

public interface DeviceController {
    @PostMapping()
    ResponseEntity<BigInteger> save(@RequestHeader("Authorization") String token, @Validated(SaveValidation.class) @RequestBody DeviceDTO deviceDTO);

    @PutMapping()
    ResponseEntity<BigInteger> update(@RequestHeader("Authorization") String token, @Validated(UpdateValidation.class) @RequestBody DeviceDTO deviceDTO);

    @DeleteMapping(value = "/{id}")
    ResponseEntity<Void> deleteById(@RequestHeader("Authorization") String token, @PathVariable("id") BigInteger id);

    @GetMapping()
    ResponseEntity<List<DeviceDTO>> findAll(@RequestHeader("Authorization") String token);

    @GetMapping(value = "/{id}")
    ResponseEntity<DeviceDTO> findById(@RequestHeader("Authorization") String token, @PathVariable("id") BigInteger id);

    @GetMapping(value = "/user/{userId}")
    ResponseEntity<List<DeviceDTO>> findAllByUserId(@PathVariable("userId") BigInteger userId);

    @DeleteMapping(value = "/user/{userId}")
    ResponseEntity<Void> deleteAllByUserId(@RequestHeader("Authorization") String token, @PathVariable("userId") BigInteger userId);
}
